package GumBalls;

import java.rmi.Naming;

public class Main {

    public static void main(String[] args) {
//        GumballMachine gumballMachine = new GumballMachine(3, "Aiova");
//        gumballMachine.insertQuarter();
//        gumballMachine.turnCrank();
//        System.out.println();
//        System.out.println(gumballMachine.toString());
//        gumballMachine.refill(10);
//        System.out.println(gumballMachine.toString());
//        gumballMachine.insertQuarter();
//        gumballMachine.ejectQuarter();
//        gumballMachine.turnCrank();
//        System.out.println();
//        gumballMachine.ejectQuarter();
//        gumballMachine.insertQuarter();
//        gumballMachine.turnCrank();
//        System.out.println();
//        //gumballMachine.turnCrank();
//        gumballMachine.insertQuarter();
//        gumballMachine.turnCrank();
//        gumballMachine.ejectQuarter();
//        System.out.println(gumballMachine.toString());
//        System.out.println();
//        gumballMachine.refill(10);
//        System.out.println(gumballMachine.toString());
        GumballMachineRemote gumballMachine = null;
        int count;
        if(args.length < 2){
            System.out.println("GumballMachine <name> <inventory>");
            //System.exit(1);
        }

        try{
            count = 100; //Integer.parseInt(args[1]);
            gumballMachine = new GumballMachine(count, args[0] + "/gumball"); //передавал localhost
            Naming.rebind("//" + args[0] + "/gumball", gumballMachine);
            System.out.println("Server ready");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
