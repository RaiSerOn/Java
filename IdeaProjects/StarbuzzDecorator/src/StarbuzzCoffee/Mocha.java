package StarbuzzCoffee;


public class Mocha extends CondimentDecorator{
    Beverage beverage;

    public Mocha(Beverage beverage){
        this.beverage = beverage;
    }

    public int getSize(){
        return beverage.getSize();
    }

    public String getDescription(){
        return beverage.getDescription() + ", Mocha";
    }

    public double cost(){
        double cost = beverage.cost();
        if(getSize() == SMALL){
            cost += .10;
        } else if(getSize() == STANDART){
            cost += .15;
        } else if(getSize() == BIG){
            cost += .20;
        }
        return cost;
    }

}
