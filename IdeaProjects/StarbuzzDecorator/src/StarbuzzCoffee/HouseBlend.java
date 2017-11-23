package StarbuzzCoffee;

public class HouseBlend extends Beverage{

    public HouseBlend(int size){
        this.size = size;
        description = "House Blend Coffee";
    }

    public double cost(){
        return .89;
    }

    public void setSize(int size){
        this.size = size;
    }

}
