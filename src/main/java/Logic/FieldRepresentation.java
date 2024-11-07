package Logic;

import Entities.ClearedSection;
import Entities.MoveResponse;
import Entities.Request;
import Exceptions.NotAllowedMoveException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FieldRepresentation {
    private int[][] representation;

    private boolean[][] allowedMoves;

    private HashMap<String, int[][]> locations;

    public FieldRepresentation(){
        representation = new int[9][9];
        allowedMoves = new boolean[9][9];
        locations = new HashMap<>();
        for (int i = 0; i < allowedMoves.length; i++) {
            for (int j = 0; j < allowedMoves[i].length; j++) {
                allowedMoves[i][j] = true;
            }
        }

        locations.put("0_0", new int[][]{{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}});
        locations.put("0_1", new int[][]{{0,3},{0,4},{0,5},{1,3},{1,4},{1,5},{2,3},{2,4},{2,5}});
        locations.put("0_2", new int[][]{{0,6},{0,7},{0,8},{1,6},{1,7},{1,8},{2,6},{2,7},{2,8}});
        locations.put("1_0", new int[][]{{3,0},{3,1},{3,2},{4,0},{4,1},{4,2},{5,0},{5,1},{5,2}});
        locations.put("1_1", new int[][]{{3,3},{3,4},{3,5},{4,3},{4,4},{4,5},{5,3},{5,4},{5,5}});
        locations.put("1_2", new int[][]{{3,6},{3,7},{3,8},{4,6},{4,7},{4,8},{5,6},{5,7},{5,8}});
        locations.put("2_0", new int[][]{{6,0},{6,1},{6,2},{7,0},{7,1},{7,2},{8,0},{8,1},{8,2}});
        locations.put("2_1", new int[][]{{6,3},{6,4},{6,5},{7,3},{7,4},{7,5},{8,3},{8,4},{8,5}});
        locations.put("2_2", new int[][]{{6,6},{6,7},{6,8},{7,6},{7,7},{7,8},{8,6},{8,7},{8,8}});
    }

    public int[][] getFieldRepresentation(){
        return this.representation;
    }
    public boolean[][] getFieldAllowedMoves(){
        return this.allowedMoves;
    }

    public MoveResponse executeMove(Request request) throws NotAllowedMoveException {
        // Check if the move is allowed and the cell is empty
        if ((!allowedMoves[request.move().y()][request.move().x()]) || representation[request.move().y()][request.move().x()] != 0) {
            throw new NotAllowedMoveException();
        }

        // Place the player's sign in the representation
        representation[request.move().y()][request.move().x()] = request.playerToTakeTurn().getSign();

        // Update the allowed moves based on the new move
        createNewMoves(request.move().y(), request.move().x());

        // Get the cleared sections from ClearedSectionChecker
        List<int[]> clearedSectionCoordinates = (new ClearedSectionChecker()).checkForClearedSections(representation);

        // If no sections are cleared, return a MoveResponse without any cleared sections
        if (clearedSectionCoordinates.isEmpty()) {
            return new MoveResponse(this.representation, this.allowedMoves, null);
        }

        // Create a list of ClearedSection objects for each cleared section found
        List<ClearedSection> clearedSections = new ArrayList<>();
        for (int[] coords : clearedSectionCoordinates) {
            clearedSections.add(new ClearedSection(coords[0], coords[1], request.playerToTakeTurn()));
        }

        // Return a MoveResponse with the cleared sections
        return new MoveResponse(this.representation, this.allowedMoves, clearedSections);
    }


    private void createNewMoves(int y, int x) {
        int index1 = y % 3; // row index for the subgrid
        int index2 = x % 3; // column index for the subgrid

        // Use the formatted key for lookup
        String key = index1 + "_" + index2;
        int[][] nextAllowedLocations = locations.get(key);
        boolean filledSection = true;

        if (nextAllowedLocations != null) {
            for (int[] location : nextAllowedLocations) {
                int row = location[0];
                int col = location[1];
                if (representation[row][col] == 0){
                    filledSection = false;
                    break;
                }
            }
        }
        // Initialize all cells in allowedMoves to false
        for (int i = 0; i < allowedMoves.length; i++) {
            for (int j = 0; j < allowedMoves[i].length; j++) {
                allowedMoves[i][j] = filledSection;
            }
        }


        // Update allowedMoves based on nextAllowedLocations
        if (nextAllowedLocations != null) {
            for (int[] location : nextAllowedLocations) {
                int row = location[0];
                int col = location[1];
                allowedMoves[row][col] = true; // Mark these specific cells as allowed
            }
        }
    }


}
