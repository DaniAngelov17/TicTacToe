package Logic;

import Entities.ClearedSection;
import Entities.MoveResponse;
import Entities.Response;
import java.util.List;

public class EndGameChecker {

    public boolean checkEndgame(Response mr) {
        // Create a 3x3 grid for the cleared sections
        int[][] clearedGrid = new int[3][3];

        // Populate the clearedGrid based on the cleared sections in MoveResponse
        List<ClearedSection> clearedSections = ((MoveResponse)mr).getClearedSections();
        if(clearedSections != null){
            for (ClearedSection section : clearedSections) {
                int rowStart = section.getRowStart();
                int colStart = section.getColStart();
                int playerSign = section.getClearedBy().getSign();

                // Map the rowStart and colStart to the 3x3 clearedGrid
                int gridRow = rowStart / 3;
                int gridCol = colStart / 3;
                clearedGrid[gridRow][gridCol] = playerSign;
            }
        }
        // Use ClearedSectionChecker to check for a cleared section in the 3x3 grid
        return ClearedSectionChecker.checkForClearedSection(clearedGrid);
    }
}
