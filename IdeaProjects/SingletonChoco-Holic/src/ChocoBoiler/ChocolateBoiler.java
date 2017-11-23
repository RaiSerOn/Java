package ChocoBoiler;

public class ChocolateBoiler {
    private boolean empty;
    private boolean boiled;
    public static ChocolateBoiler uniqesingleton;

    private ChocolateBoiler() {
        empty = true;
        boiled = false;
    }

    public static synchronized ChocolateBoiler getUniqesingleton(){
        if(uniqesingleton == null){
            uniqesingleton = new ChocolateBoiler();
        }
        return uniqesingleton;
    }

    public void fill() {
        if (isEmpty()) {
            empty = false;
            boiled = false;
            System.out.println("Ура");
        } else{
            System.out.println("Ещё не пустой");
        }
    }

    public void drain() {
        if(isEmpty()){
            System.out.println("Ещё пустой");
        }
        if(!isBoiled()){
            System.out.println("Ещё не разогрет");
        }
        if (!isEmpty() && isBoiled()) {
            empty = true;
            System.out.println("Ура");
        }

    }

    public void boil() {
        if(isEmpty()){
            System.out.println("Ещё пустой");
        }
        if(isBoiled()){
            System.out.println("Ещё не разогрет");
        }
        if (!isEmpty() && !isBoiled()) {
            boiled = true;
            System.out.println("Ура");
        }
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isBoiled()

    {
        return boiled;
    }
}