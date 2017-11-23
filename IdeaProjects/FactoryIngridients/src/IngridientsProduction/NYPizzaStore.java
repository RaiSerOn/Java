package IngridientsProduction;

/**
 * Created by maxim on 18.10.17.
 */
public class NYPizzaStore extends PizzaStore {
    public NYPizzaStore() {
    }

    protected Pizza createPizza(String item) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();
        if(item.equals("cheese")) {
            pizza = new CheesePizza(ingredientFactory);
            (pizza).setName("New York Style Cheese Pizza");
        } else if(item.equals("veggie")) {
            pizza = new VeggiePizza(ingredientFactory);
            ((Pizza)pizza).setName("New York Style Veggie Pizza");
        } else if(item.equals("clam")) {
            pizza = new ClamPizza(ingredientFactory);
            ((Pizza)pizza).setName("New York Style Clam Pizza");
        } else if(item.equals("pepperoni")) {
            pizza = new PepperoniPizza(ingredientFactory);
            ((Pizza)pizza).setName("New York Style Pepperoni Pizza");
        }

        return (Pizza)pizza;
    }
}
