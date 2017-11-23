package PizzaProduction;


public class NYPizzaStore extends PizzaStore{

    public Pizza createPizza(String item){
        if(item.equals("cheese")){
            return new NYStyleCheesePizza();
        } else if(item.equals("veggie")){
            return new NYStyleVeggiePizza();
        }  else return null;
    }

}
