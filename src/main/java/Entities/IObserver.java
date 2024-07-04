package Entities;

public interface IObserver<T>
{
    public void execute(T args);
}