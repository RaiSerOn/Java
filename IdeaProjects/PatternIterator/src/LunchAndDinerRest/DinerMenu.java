package LunchAndDinerRest;


public class DinerMenu implements Menu{
    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;

    public DinerMenu(){
        menuItems = new MenuItem[MAX_ITEMS];
        addItem("Vegetarian blt", "Bacon with lettuce", true, 2.98);
        addItem("Vegetarian blt2", "Bacon with lettuce2", false, 2);
        addItem("Vegetarian blt3", "Bacon with lettuce3", true, 2.1);
        addItem("Vegetarian blt4", "Bacon with lettuce4", false, 1.7);
    }

    public void addItem(String name, String description, boolean vegeterian, double price){
        MenuItem menuItem = new MenuItem(name, description, vegeterian, price);
        if(numberOfItems >= MAX_ITEMS){
            System.out.println("Sorry! Menu is full.");
        } else {
            menuItems[numberOfItems] = menuItem;
            numberOfItems ++;
        }
    }

    public java.util.Iterator createIterator(){
        return new DinnerMenuIterator(menuItems);
    }

}
