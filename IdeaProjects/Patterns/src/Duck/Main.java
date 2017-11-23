package Duck;

public class Main {

    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();
        mallard.swim();

        System.out.println();
        Duck modelDuck = new ModelDuck();
        modelDuck.performFly();
        modelDuck.setFlyBehavior(new FlyRocketPowered());
        modelDuck.performFly();
        modelDuck.performQuack();
        modelDuck.setQuackBehavior(new MuteDuck());
        modelDuck.performQuack();

    }
}

interface FlyBehavior{
    public void fly();
}

interface QuackBehavior{
    public void quack();
}

class FlyWithWings implements FlyBehavior{
    public void fly(){
        System.out.println("I can fly!");
    }
}

class FlyNoWay implements FlyBehavior{
    public void fly(){
        System.out.println("I can't fly");
    }
}

class FlyRocketPowered implements FlyBehavior{
    public void fly(){
        System.out.println("I'm flying with a rocket");
    }
}

class Quack implements QuackBehavior{
    public void quack(){
        System.out.println("Quack!");
    }
}

class MuteDuck implements QuackBehavior{
    public void quack(){
        System.out.println("«Silence»");
    }
}

class Squeak implements QuackBehavior{
    public void quack(){
        System.out.println("Squeck");
    }
}

abstract class Duck{
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public Duck(){

    }
    public abstract void display();
    public void performFly(){
        flyBehavior.fly();
    }
    public void performQuack(){
        quackBehavior.quack();
    }
    public void swim(){
        System.out.println("All ducks float, even decoys!");
    }
    public void setFlyBehavior(FlyBehavior fb){
        flyBehavior = fb;
    }
    public void setQuackBehavior(QuackBehavior qb){
        quackBehavior = qb;
    }
}

class MallardDuck extends Duck{
    public MallardDuck(){
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }
    public void display(){
        System.out.println("I'm can fly and MallardDuck");
    }
}

class ModelDuck extends Duck{
    public ModelDuck(){
        flyBehavior = new FlyNoWay();
        quackBehavior = new Quack();
    }
    public void display(){
        System.out.println("I'm a model duck");
    }
}