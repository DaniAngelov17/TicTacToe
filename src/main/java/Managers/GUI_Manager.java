package Managers;

import Entities.Move;
import Entities.MoveResponse;
import Entities.StartAction;
import GUI.MainWindow;

import javax.swing.*;

public class GUI_Manager {

    private final MainWindow window;
    private final ApplicationManager manager;
    public GUI_Manager(ApplicationManager manager){
        this.manager = manager;
        this.window = new MainWindow();
        window.getMove().subscribe(this::handleRequest);
        window.getStart().subscribe(this::handleStart);
    }

    private void handleRequest(Move request) {
        MoveResponse mr = null;
        try {
            mr = manager.handleRequest(request); //todo use error method
        }catch (Exception ex){
            popUpError(ex.getMessage());
        }
        if(mr != null){
            window.updateGUI(mr);
        }
    }

    private void handleStart(StartAction startAction){
        try {
            manager.handleStart(startAction); //todo use error method
        }catch (Exception ex){
            popUpError(ex.getMessage());
        }
    }

    private static void popUpError(String errorMessage)
    {
        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Error Message", JOptionPane.ERROR_MESSAGE);
        return;
    }
}
