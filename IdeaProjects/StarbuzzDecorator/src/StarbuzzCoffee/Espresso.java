package StarbuzzCoffee;


public class Espresso extends Beverage{
    public Espresso(int size){
        this.size = size;
        description = "Espresso";
    }

    public double cost(){
        return 1.99;
    }

    public void setSize(int size){
        this.size = size;
    }

}
