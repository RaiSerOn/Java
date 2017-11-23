package GumBalls;

public class SoldOutState implements State {

    transient GumballMachine gumballMachine;

    public SoldOutState(GumballMachine gumballMachine){
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter(){
        System.out.println("Sorry, we sold out all gumballs!");
    }

    public void ejectQuarter(){
        System.out.println("Sorry, we sold out all gumballs!");
    }

    public void turnCrank(){
        System.out.println("Sorry, we sold out all gumballs!");
    }

    public void dispense(){
        System.out.println("Sorry, we sold out all gumballs!");
    }

}