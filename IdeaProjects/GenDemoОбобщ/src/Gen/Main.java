package Gen;

public class Main {

    public static void main(String[] args) {
        Gen <Integer> iOb;
        iOb = new Gen<Integer>(88);
        iOb.showType();
        int v = iOb.getOb();
        System.out.println("значение: " + v);
        System.out.println();
        Gen<String> strOb = new Gen<String>("Тестирование сообщения");
        strOb.showType();
        System.out.println("значение: " + strOb.getOb());
    }
}

class Gen<T>{
    T ob;
    Gen(T o){
        ob = o;
    }
    T getOb(){
        return ob;
    }
    void showType(){
        System.out.println("Тип Т - это " + ob.getClass().getName());
    }
}