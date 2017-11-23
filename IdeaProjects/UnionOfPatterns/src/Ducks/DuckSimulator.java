package Ducks;

public class DuckSimulator {

    public static void main(String args[]){
        DuckSimulator duckSimulator = new DuckSimulator();
        AbstractDuckFactory countingDuckFactory = new CountingDuckFactory();
        AbstractGooseFactory abstractGooseFactory = new GooseDuckFactory();
        duckSimulator.simulate(countingDuckFactory, abstractGooseFactory);
    }

    void simulate(AbstractDuckFactory countingDuckFactory,
                  AbstractGooseFactory abstractGooseFactory){
        Quackable duckCall = countingDuckFactory.createDuckCall();
        Quackable mallardDuck = countingDuckFactory.createMallardDuck();
        Quackable redHeadDuck = countingDuckFactory.createRedHeadDuck();
        Quackable rubberDuck = countingDuckFactory.createRubberDuck();
        Quackable gooseDuck = abstractGooseFactory.createGoose();

        Flock flockOfDucks = new Flock();
        flockOfDucks.add(duckCall);
        flockOfDucks.add(gooseDuck);
        flockOfDucks.add(redHeadDuck);
        flockOfDucks.add(rubberDuck);

        Flock flockOfMallardDucks = new Flock();

        Quackable mallardDuckOne = countingDuckFactory.createMallardDuck();
        Quackable mallardDuckTwo = countingDuckFactory.createMallardDuck();
        Quackable mallardDuckThree = countingDuckFactory.createMallardDuck();
        Quackable mallardDuckFour = countingDuckFactory.createMallardDuck();

        flockOfMallardDucks.add(mallardDuckOne);
        flockOfMallardDucks.add(mallardDuck);
        flockOfMallardDucks.add(mallardDuckTwo);
        flockOfMallardDucks.add(mallardDuckThree);
        flockOfMallardDucks.add(mallardDuckFour);
        flockOfDucks.add(flockOfMallardDucks);

        System.out.println("Duck Simulator: with Composite - Flocks\n");

        System.out.println("Duck Simulator: Whole Flock Simulator");
        simulate(flockOfDucks);
        System.out.println();

        System.out.println("Duck Simulator: Mallard Flock Simulator");
        simulate(flockOfMallardDucks);
        System.out.println();

        System.out.println("\nThe ducks quacked " + QuackCounter.getNumberOfQuack() + " times");

    }

    void simulate(Quackable duck){
        duck.quack();
    }

}