package Entities;

public class Player {
    private String name;
    private int score;
    private int sign;
    public Player(String name){
        this.name = name;
        score = 0;
    }

    public String getName(){
        return this.name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score += 1;
    }

    public int getSign(){
        return this.sign;
    }
    public void setSign(int sign){
        this.sign = sign;
    }
}
