package TwoGen;

public class Main {

    public static void main(String[] args) {
        TwoGen<Integer, String> tgObj = new TwoGen<Integer, String>(88, "TEXT");
        tgObj.showTypes();
        System.out.println("значение: " + tgObj.getOb1());
        System.out.println("значение: " + tgObj.getOb2());
    }
}

class TwoGen<T, V>{
    T ob1;
    V ob2;
    TwoGen(T o1, V o2){
        ob1 = o1;
        ob2 = o2;
    }
    void showTypes(){
        System.out.println("Тип Т - это " + ob1.getClass().getName());
        System.out.println("Тип V - это " + ob2.getClass().getName());
    }
    T getOb1(){
        return ob1;
    }
    V getOb2(){
        return ob2;
    }
}