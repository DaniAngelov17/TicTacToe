package GUI;

import Entities.*;
import GUI.SubPanels.Component;
import GUI.SubPanels.GameFieldPanel;
import GUI.SubPanels.StartMenuPanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
public class MainWindow extends JFrame {

    private final Subject<Move> onRequested;
    private final Subject<StartAction> onStart;
    private List<Component> componentList;
    private GameFieldPanel gameField;
    private StartMenuPanel startMenuPanel;
    private int mainWidth = 900;
    private int mainHeight = 600;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainWindow() {
        onRequested = new Subject<>();
        onStart = new Subject<>();
        onStart.subscribe(this::showGameField);
        componentList = new ArrayList<>();

        changeSize(mainWidth, mainHeight);
        initComponents();
        setLayout();
        this.setBackground(Color.white);
        this.setVisible(true);
    }


    private void initComponents() {
        gameField = new GameFieldPanel(onRequested::execute, mainHeight);
        startMenuPanel = new StartMenuPanel(onStart::execute, mainWidth, mainHeight);

        componentList.add(gameField);
        componentList.add(startMenuPanel);
    }

    public void changeSize(int mainWidth, int mainHeight) {
        this.setSize(new Dimension(mainWidth, mainHeight));
        for (Component c : componentList) {
            c.changeSize(mainWidth, mainHeight);
        }
    }

    private void setLayout() {
        // Initialize CardLayout and JPanel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add the panels to the card panel
        cardPanel.add(startMenuPanel, "StartMenu");
        cardPanel.add(gameField, "GameField");

        // Add the card panel to the JFrame
        this.add(cardPanel);

        // Optionally, show the start menu first
        cardLayout.show(cardPanel, "StartMenu");
    }

    // Method to switch to the game field panel
    public void showGameField(StartAction startAction) {
        cardLayout.show(cardPanel, "GameField");
    }

    public void EndGame(){
        cardPanel.remove(1);
        componentList.remove(gameField);
        cardLayout.show(cardPanel, "StartMenu");
        gameField = new GameFieldPanel(onRequested::execute, mainHeight);
        componentList.add(gameField);
        cardPanel.add(gameField, "GameField");
    }

    public void winnerPopUp(Player p) {
        // Customize the dialog box font and color
        UIManager.put("OptionPane.messageFont", new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        UIManager.put("OptionPane.messageForeground", java.awt.Color.DARK_GRAY);


        String message;
        if (p != null) {
            message = "<html><div style='text-align: center;'>"
                    + "<span style='font-size:20px; color: #4CAF50;'>Congratulations!</span><br>"
                    + "<span style='font-size:18px;'>Player <b>" + p.getName() + "</b> has won the game!</span>"
                    + "</div></html>";
        } else {
            message = "<html><div style='text-align: center;'>"
                    + "<span style='font-size:20px; color: #FF5722;'>It's a Draw!</span><br>"
                    + "<span style='font-size:18px;'>No winner this time!</span>"
                    + "</div></html>";
        }

        // Display the customized dialog
        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateGUIAfterMove(Response response) {
        gameField.update((MoveResponse) response);
    }

    public void updateGUIEndGame(Response response){
        EndGame();
        winnerPopUp(((EndGameResponse)response).getWinner());
    }

    public IObservable<Move> getMove() {
        return onRequested;
    }

    public IObservable<StartAction> getStart(){
        return onStart;
    }

}
