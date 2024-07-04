package Exceptions;

public class NotAllowedMoveException extends Exception{
    public NotAllowedMoveException(){
        super("This move is not possible!");
    }
}
