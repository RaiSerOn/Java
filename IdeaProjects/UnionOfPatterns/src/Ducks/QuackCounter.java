package Ducks;

public class QuackCounter implements Quackable{

    Quackable duck;
    static int numberOfQuack;

    public QuackCounter(Quackable duck){
        this.duck = duck;
    }

    public void quack(){
        duck.quack();
        numberOfQuack++;
    }

    public static int getNumberOfQuack(){
        return numberOfQuack;
    }

}
