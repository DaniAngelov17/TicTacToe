package Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClearedSectionChecker {

    public ClearedSectionChecker() {
    }

    /**
     *
     * @param representation of the board
     * @return Cleared section
     * Partitions the board and calls helper functions to check the partitions
     */
    public static List<int[]> checkForClearedSections(int[][] representation) {
        List<int[]> clearedSections = new ArrayList<>();
        for (int k = 0; k < 9; k += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {
                int[][] section = new int[3][3];
                for (int i = k, f = 0; i < k + 3; i++, f++) {
                    for (int j = startCol, r = 0; j < startCol + 3; j++, r++) {
                        section[f][r] = representation[i][j];
                    }
                }
                if (checkForClearedSection(section)) {
                    clearedSections.add(new int[]{k, startCol});
                }
            }
        }
        return clearedSections;
    }


    public static boolean checkForClearedSection(int[][] board) {
        int size = board.length;

        // Check rows and columns in a single loop
        for (int i = 0; i < size; i++) {
            // Check row i
            if (allEqual(board[i])) {
                return true;
            }
            // Check column i
            if (allEqual(getColumn(board, i))) {
                return true;
            }
        }

        // Check diagonals
        if (allEqual(getPrimaryDiagonal(board)) || allEqual(getSecondaryDiagonal(board))) {
            return true;
        }

        // No win condition met
        return false;
    }

    // Helper method to check if all elements in the array are equal and not zero
    private static boolean allEqual(int[] array) {
        int first = array[0];
        if (first == 0) return false; // Skip if first element is zero (empty)
        for (int element : array) {
            if (element != first) {
                return false;
            }
        }
        return true;
    }

    // Helper method to get a column from the board
    private static int[] getColumn(int[][] board, int index) {
        int size = board.length;
        int[] column = new int[size];
        for (int i = 0; i < size; i++) {
            column[i] = board[i][index];
        }
        return column;
    }

    // Helper method to get the primary diagonal
    private static int[] getPrimaryDiagonal(int[][] board) {
        int size = board.length;
        int[] diagonal = new int[size];
        for (int i = 0; i < size; i++) {
            diagonal[i] = board[i][i];
        }
        return diagonal;
    }

    // Helper method to get the secondary diagonal
    private static int[] getSecondaryDiagonal(int[][] board) {
        int size = board.length;
        int[] diagonal = new int[size];
        for (int i = 0; i < size; i++) {
            diagonal[i] = board[i][size - i - 1];
        }
        return diagonal;
    }
}
