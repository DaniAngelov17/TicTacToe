package Entities;

public record MoveResponse(int[][] occupiedRepresentation, boolean[][] allowedMovesRepresentation, int[] clearedSection) {
}
