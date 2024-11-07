package Managers;

import Entities.*;
import Exceptions.CannotStartException;
import Exceptions.NotAllowedMoveException;
import Logic.EndGameChecker;
import Logic.FieldRepresentation;

public class ApplicationManager {

    private final PlayerManager playerManager;
    private final FieldRepresentation fieldRepresentation;
    private final EndGameChecker endGameChecker;

    public ApplicationManager(PlayerManager playerManager, FieldRepresentation fieldRepresentation, EndGameChecker endGameChecker){
        this.playerManager = playerManager;
        this.fieldRepresentation = fieldRepresentation;
        //todo implement
        this.endGameChecker = endGameChecker;
    }

    public Response handleRequest(Move move) throws NotAllowedMoveException {
        Response response = null;
        Player playerToMove = playerManager.getNextPlayer();
        try{
            response = fieldRepresentation.executeMove(new Request(playerToMove, move));
        }catch (NotAllowedMoveException ex){
            playerManager.changeTurn();
            throw ex;
        }
        if(endGameChecker.checkEndgame(response)){
            response = new EndGameResponse(playerToMove, response);
        }
        return response;
    }

    public void handleStart(StartAction startAction) throws CannotStartException {
        playerManager.addPlayers(startAction.player1(), startAction.player2());//todo change this if needed in the future
    }

    public int[][] getFieldRepresentation(){
        return this.fieldRepresentation.getFieldRepresentation();
    }
}
