package StarbuzzCoffee;


public abstract class Beverage {
    public final int STANDART = 15, SMALL = 10, BIG = 20;
    String description = "Unknown Beverage";
    int size = STANDART;

    public String getDescription(){
        return description;
    }

    public abstract double cost();

    public int getSize(){ return size; }

    public void setSize(int size){ this.size = size; }


}
