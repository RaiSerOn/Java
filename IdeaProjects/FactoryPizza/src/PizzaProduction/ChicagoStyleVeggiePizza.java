package PizzaProduction;


public class ChicagoStyleVeggiePizza extends Pizza{

    public ChicagoStyleVeggiePizza() {
        name = "Chicago Style Sauce and Veggie Pizza";
        dough = "Extra Thick Crust Dough";
        sauce = "Plum Garlic Sauce";
        toppings.add("Shredded Mozzarella Cheese");
    }

    void box() {
        System.out.println("Boxing the pizza in oval box");
    }

}
