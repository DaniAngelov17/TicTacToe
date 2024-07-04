package GUI;

import Entities.*;
import GUI.SubPanels.Component;
import GUI.SubPanels.GameFieldPanel;
import GUI.SubPanels.StartMenuPanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        onStart.subscribe(this::showGameField); // Corrected line
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

    // Method to switch to the start menu panel
    public void showStartMenu() {
        cardLayout.show(cardPanel, "StartMenu");
    }

    public void updateGUI(MoveResponse response) {
        gameField.update(response);
        // Additional GUI update logic can go here
    }

    public IObservable<Move> getMove() {
        return onRequested;
    }

    public IObservable<StartAction> getStart(){
        return onStart;
    }

}
