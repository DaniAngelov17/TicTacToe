package main;

import Logic.FieldRepresentation;
import Managers.ApplicationManager;
import Managers.GUI_Manager;
import Managers.PlayerManager;

public class Program {
    public static void main(String[] args) {
        PlayerManager playerManager = new PlayerManager();
        FieldRepresentation fieldRepresentation = new FieldRepresentation();
        ApplicationManager applicationManager = new ApplicationManager(playerManager, fieldRepresentation);
        GUI_Manager guiManager = new GUI_Manager(applicationManager);
    }
}