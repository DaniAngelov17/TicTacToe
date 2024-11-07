package Entities;
import GUI.MainWindow;

import java.util.List;
import java.util.List;

public class MoveResponse extends Response {
    private final int[][] occupiedRepresentation;
    private final boolean[][] allowedMovesRepresentation;
    private final List<ClearedSection> clearedSections;

    public MoveResponse(int[][] occupiedRepresentation, boolean[][] allowedMovesRepresentation, List<ClearedSection> clearedSections) {
        this.occupiedRepresentation = occupiedRepresentation;
        this.allowedMovesRepresentation = allowedMovesRepresentation;
        this.clearedSections = clearedSections;
    }

    public int[][] getOccupiedRepresentation() {
        return occupiedRepresentation;
    }

    public boolean[][] getAllowedMovesRepresentation() {
        return allowedMovesRepresentation;
    }

    public List<ClearedSection> getClearedSections() {
        return clearedSections;
    }

    @Override
    public void updateGUI(MainWindow window) {
        window.updateGUIAfterMove(this);
    }
}
