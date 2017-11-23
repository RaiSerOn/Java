package GumBalls;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote{
    State soldOutState;
    State noQuarterState;
    State hasQuarterState;
    State soldState;
    State winnerState;

    State state = soldOutState;
    String location;
    int count = 0;

    public GumballMachine(int numberOfGumballs, String location) throws RemoteException{
        noQuarterState = new NoQuarterState(this);
        soldOutState = new SoldOutState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);
        winnerState = new WinnerState(this);
        this.location = location;
        this.count = numberOfGumballs;
        if(count > 0){
            state = noQuarterState;
        }
    }

    public void insertQuarter(){
        state.insertQuarter();
    }

    public void ejectQuarter(){
        state.ejectQuarter();
    }

    public void turnCrank(){
        state.turnCrank();
        state.dispense();
    }

    void setState(State state){
        this.state = state;
    }

    void releaseBall(){
        System.out.println("A gumball is rolling out...");
        if(count != 0){
            count--;
        }
    }

    public void refill(int count){
        if(this.count == 0){
            this.count = count;
            state = noQuarterState;
        } else {
            this.count += count;
        }
    }

    public State getSoldOutState(){
        return soldOutState;
    }

    public State getNoQuarterState(){
        return noQuarterState;
    }

    public State getHasQuarterState(){
        return hasQuarterState;
    }

    public State getSoldState(){
        return soldState;
    }

    public State getWinnerState(){ return winnerState;}

    public State getState(){
        return state;
    }

    public int getCount(){
        return count;
    }

    public String getLocation(){
        return location;
    }

    public String toString(){
        return count + " left!";
    }

}
