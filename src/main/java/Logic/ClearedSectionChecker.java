package Logic;

import java.util.HashMap;

public class ClearedSectionChecker {
    private HashMap<String, int[][]> locations;

    public ClearedSectionChecker() {
        locations = new HashMap<>();
        locations.put("0_0", new int[][]{{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}});
        locations.put("0_1", new int[][]{{0, 3}, {0, 4}, {0, 5}, {1, 3}, {1, 4}, {1, 5}, {2, 3}, {2, 4}, {2, 5}});
        locations.put("0_2", new int[][]{{0, 6}, {0, 7}, {0, 8}, {1, 6}, {1, 7}, {1, 8}, {2, 6}, {2, 7}, {2, 8}});
        locations.put("1_0", new int[][]{{3, 0}, {3, 1}, {3, 2}, {4, 0}, {4, 1}, {4, 2}, {5, 0}, {5, 1}, {5, 2}});
        locations.put("1_1", new int[][]{{3, 3}, {3, 4}, {3, 5}, {4, 3}, {4, 4}, {4, 5}, {5, 3}, {5, 4}, {5, 5}});
        locations.put("1_2", new int[][]{{3, 6}, {3, 7}, {3, 8}, {4, 6}, {4, 7}, {4, 8}, {5, 6}, {5, 7}, {5, 8}});
        locations.put("2_0", new int[][]{{6, 0}, {6, 1}, {6, 2}, {7, 0}, {7, 1}, {7, 2}, {8, 0}, {8, 1}, {8, 2}});
        locations.put("2_1", new int[][]{{6, 3}, {6, 4}, {6, 5}, {7, 3}, {7, 4}, {7, 5}, {8, 3}, {8, 4}, {8, 5}});
        locations.put("2_2", new int[][]{{6, 6}, {6, 7}, {6, 8}, {7, 6}, {7, 7}, {7, 8}, {8, 6}, {8, 7}, {8, 8}});
    }

    public static int[] checkForClearedSections(int[][] representation){
        int saved_i = -1;
        int saved_j = -1;
        boolean first = true;
        for (int k = 0; k < 9; k+=3) {
            int[][]section = new int[3][3];

            for (int i = k, f = 0; i < k + 3; i++,f++) {
                for (int j = 0, r = 0; j < 3; j++,r++) {
                    if(first){
                        saved_i = i;
                        saved_j = j;
                        first = false;
                    }
                    section[f][r] = representation[i][j];
                }
            }
            if(checkForClearedSection(section)){
                return new int[]{saved_i, saved_j};
            }
            saved_i = -1;
            saved_j = -1;
            first = true;
            for (int i = k, f = 0; i < k + 3; i++,f++) {
                for (int j = 3, r = 0; j < 6; j++,r++) {
                    if(first){
                        saved_i = i;
                        saved_j = j;
                        first = false;
                    }
                    section[f][r] = representation[i][j];
                }
            }
            if(checkForClearedSection(section)){
                return new int[]{saved_i, saved_j};
            }
            saved_i = -1;
            saved_j = -1;
            first = true;
            for (int i = k, f = 0; i < k + 3; i++,f++) {
                for (int j = 6, r = 0; j < 9; j++,r++) {
                    if(first){
                        saved_i = i;
                        saved_j = j;
                        first = false;
                    }
                    section[f][r] = representation[i][j];
                }
            }
            if(checkForClearedSection(section)){
                return new int[]{saved_i, saved_j};
            }
            saved_i = -1;
            saved_j = -1;
        }
        return new int[]{saved_i, saved_j};
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
