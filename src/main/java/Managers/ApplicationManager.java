package Managers;

import Entities.Move;
import Entities.MoveResponse;
import Entities.Request;
import Entities.StartAction;
import Exceptions.CannotStartException;
import Exceptions.NotAllowedMoveException;
import Logic.FieldRepresentation;

public class ApplicationManager {

    private final PlayerManager playerManager;
    private final FieldRepresentation fieldRepresentation;

    public ApplicationManager(PlayerManager playerManager, FieldRepresentation fieldRepresentation){
        this.playerManager = playerManager;
        this.fieldRepresentation = fieldRepresentation;
        //todo implement
    }

    public MoveResponse handleRequest(Move move) throws NotAllowedMoveException {
        MoveResponse mr = null;
        try{
            mr = fieldRepresentation.executeMove(new Request(playerManager.getNextPlayer(), move));
        }catch (NotAllowedMoveException ex){
            playerManager.changeTurn();
            throw ex;
        }
        return mr;
    }

    public void handleStart(StartAction startAction) throws CannotStartException {
        playerManager.addPlayers(startAction.player1(), startAction.player2());//todo change this if needed in the future
    }

    public int[][] getFieldRepresentation(){
        return this.fieldRepresentation.getFieldRepresentation();
    }
}
