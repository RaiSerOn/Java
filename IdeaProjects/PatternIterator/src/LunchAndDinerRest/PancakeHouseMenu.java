package LunchAndDinerRest;

import java.util.ArrayList;
import java.util.Iterator;

public class PancakeHouseMenu implements Menu{
    ArrayList menuItems;

    public PancakeHouseMenu(){
        menuItems = new ArrayList();
        addItem("K&B's pancake breakfast", "Pancakes with scrambles eggs, and toast", true, 2.99);
        addItem("K&B's pancake breakfast2", "Pancakes with scrambles eggs, and toast2", false, 3.99);
        addItem("K&B's pancake breakfast3", "Pancakes with scrambles eggs, and toast3", true, 1.99);
        addItem("K&B's pancake breakfast4", "Pancakes with scrambles eggs, and toast4", false, 1);
    }

    public void addItem(String name, String description, boolean vegetarian, double price){
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.add(menuItem);
    }

    public Iterator createIterator(){
        //return new PancakeHouseMenuIterator(menuItems);
        return menuItems.iterator();
    }

}
