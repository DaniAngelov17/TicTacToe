package Entities;

import GUI.MainWindow;

public class EndGameResponse extends Response {
    private final Player winner;
    private final Response lastMove;

    public EndGameResponse(Player winner, Response lastMove) {
        this.winner = winner;
        this.lastMove = lastMove;
    }

    public Player getWinner() {
        return winner;
    }

    public Response getLastMove() {
        return lastMove;
    }

    @Override
    public void updateGUI(MainWindow window) {

        lastMove.updateGUI(window);
        window.updateGUIEndGame(this);
    }
}
