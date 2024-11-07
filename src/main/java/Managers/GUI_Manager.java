package Managers;

import Entities.Move;
import Entities.Response;
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
        Response response = null;
        try {
            response = manager.handleRequest(request); //todo use error method
        }catch (Exception ex){
            popUpError(ex.getMessage());
        }
        if(response != null){
            response.updateGUI(window);
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
