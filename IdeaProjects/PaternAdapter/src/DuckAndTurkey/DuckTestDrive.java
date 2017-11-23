package DuckAndTurkey;

public class DuckTestDrive {
    public static void main(String args[]){
        MallardDuck duck = new MallardDuck();
        WildTurey turkey = new WildTurey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey);
        System.out.println("The Turkey Says...");
        turkey.gobble();
        turkey.fly();
        System.out.println("The Duck Says...");
        duck.quack();
        duck.fly();
        System.out.println("The TurkeyAdapter Says...");
        turkeyAdapter.quack();
        turkeyAdapter.fly();
    }
}
