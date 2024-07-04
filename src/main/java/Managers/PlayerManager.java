package Managers;

import Entities.Player;

public class PlayerManager {
    private Player player1;
    private Player player2;
    private boolean isPlayer1Turn;

    // Constructor initializes the turn to player1 by default
    public PlayerManager() {
        resetTurns();
    }

    // Method to add players
    public void addPlayers(Player player1, Player player2) {
        this.player1 = player1;
        player1.setSign(1);
        player2.setSign(2);
        this.player2 = player2;
    }

    // Method to get the next player to take a turn
    public Player getNextPlayer() {
        if (player1 == null || player2 == null) {
            throw new IllegalStateException("Both players must be added before getting the next player.");
        }

        if (isPlayer1Turn) {
            changeTurn();
            return player1;
        } else {
            changeTurn();
            return player2;
        }
    }

    public void changeTurn(){
        if (isPlayer1Turn) {
            isPlayer1Turn = false;
        } else {
            isPlayer1Turn = true;
        }
    }

    // Optional: Method to reset the turn to the first player
    public void resetTurns() {
        this.isPlayer1Turn = true;
    }
}
