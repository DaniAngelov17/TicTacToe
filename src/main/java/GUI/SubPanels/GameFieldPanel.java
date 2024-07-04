package GUI.SubPanels;
import Entities.IAction;
import Entities.Move;
import Entities.MoveResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameFieldPanel extends JPanel implements Component {
    private IAction<Move> onGameMove;
    private JButton[][] buttons;
    private int[][] fieldState;
    private static final int GRID_SIZE = 9;
    private static final int BUTTON_SIZE = 50; // Fixed button size for consistent look
    private static final int PADDING = 10; // Padding around the grid
    private JPanel gridPanel;
    private List<int[]> clearedSections;
    private List<Integer> clearedSectionPlayers;

    public GameFieldPanel(IAction<Move> onGameMove, int mainHeight) {
        this.onGameMove = onGameMove;
        this.buttons = new JButton[GRID_SIZE][GRID_SIZE];
        this.fieldState = new int[GRID_SIZE][GRID_SIZE];
        this.clearedSections = new ArrayList<>();
        this.clearedSectionPlayers = new ArrayList<>();
        setLayout(new GridBagLayout());
        this.setBackground(Color.LIGHT_GRAY);
        initializeButtons(mainHeight);
    }

    private void initializeButtons(int mainHeight) {
        gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, PADDING, PADDING)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.GRAY);

                // Draw thicker lines to divide the grid into 9 larger squares
                int gridWidth = getWidth();
                int gridHeight = getHeight();
                int subSquareSize = gridWidth / 3;

                // Draw horizontal lines
                for (int i = 1; i < 3; i++) {
                    int y = i * subSquareSize;
                    g2d.setStroke(new BasicStroke(3)); // Thicker lines
                    g2d.drawLine(0, y, gridWidth, y);
                }

                // Draw vertical lines
                for (int i = 1; i < 3; i++) {
                    int x = i * subSquareSize;
                    g2d.setStroke(new BasicStroke(3)); // Thicker lines
                    g2d.drawLine(x, 0, x, gridHeight);
                }

                // Draw cleared sections
                drawClearedSectionOverlay(g2d);
            }
        };

        gridPanel.setOpaque(false); // Make the grid panel transparent
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = createButton(row, col);
                buttons[row][col] = button;
                gridPanel.add(button);
            }
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(PADDING, PADDING, PADDING, PADDING);
        add(gridPanel, gbc);

        int size = Math.min(mainHeight, GRID_SIZE * (BUTTON_SIZE + PADDING));
        setPreferredSize(new Dimension(size, size));
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
        this.fieldState = mr.occupiedRepresentation();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = mr.occupiedRepresentation()[row][col];
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

        highlightUsableSquares(mr.allowedMovesRepresentation());

        // Detailed debug output for cleared sections
        System.out.println("Cleared Sections location first cell (update method):");
        for (int coordinate : mr.clearedSection()) {
            System.out.println(coordinate);
        }

        updateClearedSections(mr.clearedSection(), mr.occupiedRepresentation());
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

    private void updateClearedSections(int[] clearedSections, int[][] occupiedRepresentation) {
        System.out.println("Cleared Sections:");
        for (int i = 0; i < clearedSections.length && clearedSections[0] != -1 && clearedSections[1] != -1; i += 2) {
            int rowStart = clearedSections[i];
            int colStart = clearedSections[i + 1];

            System.out.println("Cleared section at: (" + rowStart + ", " + colStart + ")");

            int playerSign = 0;
            // Determine the player who cleared the section
            for (int row = rowStart; row < rowStart + 3; row++) {
                for (int col = colStart; col < colStart + 3; col++) {
                    if (occupiedRepresentation[row][col] != 0) {
                        playerSign = occupiedRepresentation[row][col];
                        break;
                    }
                }
                if (playerSign != 0) break;
            }

            if (playerSign == 0) continue;

            this.clearedSections.add(new int[] { rowStart, colStart });
            clearedSectionPlayers.add(playerSign);
        }

        // Repaint to show the overlays
        gridPanel.repaint();
    }

    private void drawClearedSectionOverlay(Graphics2D g2d) {
        for (int i = 0; i < clearedSections.size(); i++) {
            int[] section = clearedSections.get(i);
            int rowStart = section[0];
            int colStart = section[1];
            int playerSign = clearedSectionPlayers.get(i);

            // Determine the position and size of the overlay
            int x = colStart * (BUTTON_SIZE + PADDING) + PADDING;
            int y = rowStart * (BUTTON_SIZE + PADDING) + PADDING;
            int size = BUTTON_SIZE * 3 + PADDING * 2;

            // Set transparency
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2d.setColor(playerSign == 1 ? Color.BLUE : Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, size));

            // Draw the sign
            g2d.drawString(playerSign == 1 ? "X" : "O", x, y + size - PADDING);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension(mainWidth, mainHeight));
    }
}

