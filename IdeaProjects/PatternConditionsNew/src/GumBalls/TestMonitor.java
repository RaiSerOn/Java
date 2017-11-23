package GumBalls;

import java.rmi.*;

public class TestMonitor {
    public static void main(String args[]){
        String[] location = {
                "rmi://localhost/gumball"
        };
        GumballMonitor[] monitors = new GumballMonitor[location.length];
        for(int i = 0; i < monitors.length; i ++){
            try{
                GumballMachineRemote gumballMachineRemote =
                        (GumballMachineRemote) Naming.lookup(location[i]);
                monitors[i] = new GumballMonitor(gumballMachineRemote);
                System.out.println(monitors[i]);
                //System.out.print(Naming.lookup(location[i]));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        for(int i = 0; i < monitors.length; i++){
            monitors[i].report();
        }
    }
}
