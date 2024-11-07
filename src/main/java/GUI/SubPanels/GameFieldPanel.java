package GUI.SubPanels;

import Entities.ClearedSection;
import Entities.IAction;
import Entities.Move;
import Entities.MoveResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GameFieldPanel extends JPanel implements Component {
    private IAction<Move> onGameMove;
    private JButton[][] buttons;
    private int[][] fieldState;
    private static final int GRID_SIZE = 9;
    private static final int BUTTON_SIZE = 50; // Fixed button size for consistent look
    private static final int PADDING = 10; // Padding around the grid
    private JPanel gridPanel;
    private List<ClearedSection> clearedSections;
    private JLayeredPane layeredPane;
    private JPanel overlayPanel;

    public GameFieldPanel(IAction<Move> onGameMove, int mainHeight) {
        this.onGameMove = onGameMove;
        this.buttons = new JButton[GRID_SIZE][GRID_SIZE];
        this.fieldState = new int[GRID_SIZE][GRID_SIZE];
        this.clearedSections = new ArrayList<>();
        setLayout(new GridBagLayout());
        this.setBackground(Color.LIGHT_GRAY);
        initializeButtons(mainHeight);
    }

    private void initializeButtons(int mainHeight) {
        int totalSize = GRID_SIZE * (BUTTON_SIZE + PADDING);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(totalSize, totalSize));

        gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, PADDING, PADDING));
        gridPanel.setOpaque(false);
        gridPanel.setBounds(0, 0, totalSize, totalSize);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = createButton(row, col);
                buttons[row][col] = button;
                gridPanel.add(button);
            }
        }

        overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Clear the background
                g.setColor(new Color(0, 0, 0, 0)); // Transparent color
                g.fillRect(0, 0, getWidth(), getHeight());

                super.paintComponent(g);
                drawClearedSectionOverlay((Graphics2D) g);
            }
        };
        overlayPanel.setOpaque(false);
        overlayPanel.setBounds(0, 0, totalSize, totalSize);

        layeredPane.add(gridPanel, Integer.valueOf(0));
        layeredPane.add(overlayPanel, Integer.valueOf(1));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(PADDING, PADDING, PADDING, PADDING);
        add(layeredPane, gbc);

        setPreferredSize(new Dimension(totalSize, totalSize));
    }


    private JButton createButton(int row, int col) {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(row, col);
            }
        });

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Store the original color before changing it
                Color originalColor = button.getBackground();
                button.putClientProperty("originalColor", originalColor);
                button.setBackground(Color.LIGHT_GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Restore the original color on mouse exit
                Color originalColor = (Color) button.getClientProperty("originalColor");
                button.setBackground(originalColor != null ? originalColor : Color.WHITE);
            }
        });

        return button;
    }

    private void handleButtonClick(int row, int col) {
        Move move = new Move(row, col);
        if (onGameMove != null) {
            onGameMove.execute(move);
        }
    }

    public void update(MoveResponse mr) {
        this.fieldState = mr.getOccupiedRepresentation();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = mr.getOccupiedRepresentation()[row][col];
                JButton button = buttons[row][col];
                switch (value) {
                    case 1:
                        button.setText("X");
                        button.setForeground(Color.BLUE);
                        break;
                    case 2:
                        button.setText("O");
                        button.setForeground(Color.RED);
                        break;
                    default:
                        button.setText("");
                        break;
                }
            }
        }

        highlightUsableSquares(mr.getAllowedMovesRepresentation());

        // Detailed debug output for cleared sections

        updateClearedSections(mr.getClearedSections());

        System.out.println("Cleared Sections location first cell (update method):");
        for (ClearedSection c:
                clearedSections) {
            System.out.println(c.getRowStart() + ", " + c.getColStart());
        }
    }

    public void highlightUsableSquares(boolean[][] usableSquares) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = buttons[row][col];
                if (usableSquares[row][col]) {
                    button.setBackground(Color.WHITE.brighter());
                } else {
                    button.setBackground(Color.GRAY); // Set non-usable squares to gray
                }
                // Store the color to maintain it during hover
                button.putClientProperty("originalColor", button.getBackground());
            }
        }
    }

    /**
    This function updates the clearedSection that is used to draw in the overlay
     */
    private void updateClearedSections(List<ClearedSection> clearedSections) {
        if (clearedSections != null) {
            List<ClearedSection> newSections = new ArrayList<>();
            for (ClearedSection clearedSection : clearedSections) {
                if (clearedSection != null && this.clearedSections.stream().noneMatch(clearedSection::equals)) {
                    newSections.add(clearedSection);
                }
            }
            // Add all new sections to the clearedSections list after iteration
            this.clearedSections.addAll(newSections);
        }


        // Trigger repaint to show all cleared sections
        overlayPanel.repaint();
    }


    /**
    Update the Overlay
     */
    private void drawClearedSectionOverlay(Graphics2D g2d) {
        for (ClearedSection section: clearedSections) {

            int rowStart = section.getRowStart();
            int colStart = section.getColStart();
            int playerSign = section.getClearedBy().getSign();

            // Calculate overlay dimensions
            Rectangle rectStart = buttons[rowStart][colStart].getBounds();
            Rectangle rectEnd = buttons[rowStart + 2][colStart + 2].getBounds();
            int x = rectStart.x;
            int y = rectStart.y;
            int width = rectEnd.x + rectEnd.width - rectStart.x;
            int height = rectEnd.y + rectEnd.height - rectStart.y;

            // Set a translucent color based on the player
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2d.setColor(playerSign == 1 ? new Color(0, 0, 255, 75) : new Color(255, 0, 0, 75)); // Transparent blue or red

            // Draw filled rectangle overlay for cleared section
            g2d.fillRect(x, y, width, height);

            // Draw the player's symbol in the center of the cleared section
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2d.setFont(new Font("Arial", Font.BOLD, height));
            String symbol = playerSign == 1 ? "X" : "O";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(symbol);
            int textHeight = fm.getAscent();
            int textX = x + (width - textWidth) / 2;
            int textY = y + (height + textHeight) / 2 - fm.getDescent();
            g2d.drawString(symbol, textX, textY);
        }
    }

    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension(mainWidth, mainHeight));
    }
}

