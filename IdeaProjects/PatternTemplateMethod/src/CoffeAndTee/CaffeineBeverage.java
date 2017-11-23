package CoffeAndTee;

public abstract class CaffeineBeverage {
    final void prepareRecipe(){

        boilWater();

        brew();

        pourInCup();
        if(hook()) {
            addCondiments();
        }
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater(){
        System.out.println("Add water in cup");
    }

    void pourInCup(){
        System.out.println("Pour in cup");
    }

    boolean hook(){ // Это перехватчик, он либо не делает ничего, либо определяетс клиентом для своих нужд, например, для проверки условия
        return true;
    }
}
