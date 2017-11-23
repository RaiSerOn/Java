package StarbuzzCoffee;


public class Decafe extends Beverage{
    public Decafe(int size){
        description = "Decafe";
        this.size = size;
    }

    public double cost(){
        return 1.05;
    }

    public void setSize(int size){
        this.size = size;
    }

}
