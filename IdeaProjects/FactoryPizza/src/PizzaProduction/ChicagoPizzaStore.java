package PizzaProduction;


public class ChicagoPizzaStore extends PizzaStore{

    public Pizza createPizza(String item){
        if(item.equals("cheese")){
            return new ChicagoStyleCheesePizza();
        } else if(item.equals("veggie")){
            return new ChicagoStyleVeggiePizza();
        }  else return null;
    }

}
