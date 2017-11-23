package ChocoBoiler;

/**
 * Created by maxim on 18.10.17.
 */
public class TestBoiler {
    public static void main(String args []){
        ChocolateBoiler boiler1 = ChocolateBoiler.getUniqesingleton();
        ChocolateBoiler boiler2 = ChocolateBoiler.getUniqesingleton();
        boiler1.fill();
        boiler2.boil();
    }
}
