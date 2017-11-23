package Generic;

public class Main {

    public static void main(String[] args) {
        GenericMethodDemo gen = new GenericMethodDemo();
        Integer num[] = {1, 2, 3, 4, 5};
        Integer num1[] = {1, 2, 3, 4 ,5};
        Integer num2[] = {1, 2, 3, 4, 5};
        Integer num3[] = {1, 2, 3, 4, 5, 6};
        if(gen.arraysEqual(num, num1)) System.out.println("num эквивалентен num1");
        if(gen.arraysEqual(num, num)) System.out.println("num эквивалентен num");
        if(gen.arraysEqual(num, num2)) System.out.println("num эквивалентен num2");
        if(gen.arraysEqual(num, num3)) System.out.println("num эквивалентен num3");
        Double dval[] = {1.1, 2.2, 3.3, 4.4, 5.5};
        //Нельзя сравнивать dval, потому что не эквиваленты типы массивов

        Summation ob = new Summation(4.0);
        System.out.println("Сумма целых чисел от 0 до 4.0 равна " + ob.getSum());

        Integer[] x = {1, 2, 3};
        MyClass<Integer> ob2 = new MyClass<Integer>(x);
        if(ob2.contains(2)) System.out.println("2 содержится в ob");
        else System.out.println("2 не содержится в ob");
        // нельзя применять метод для сравнения с, например, 9.0, т. к. типы не совпадают(Integer и Double);
    }
}

class GenericMethodDemo{
    static <T extends Comparable<T>, V extends T> boolean arraysEqual(T[] x, V[] y){
        if(x.length != y.length) return  false;
        for (int i = 0; i < x.length; i++){
            if (!x[i].equals(y[i]))return false;
        }
        return true;
    }
}

class Summation{
    private int sum;
    <T extends Number> Summation(T arg){
        sum = 0;
        for(int i = 0; i <= arg.intValue(); i ++){
            sum += i;
        }
    }
    int getSum(){
        return sum;
    }
}

interface Containment<T>{
    boolean contains(T o);
}

class MyClass<T> implements Containment<T>{
    T[] arrayRef;
    MyClass(T[] o){
        arrayRef = o;
    }
    public boolean contains(T o){
        for(T x : arrayRef) if(x.equals(o)) return true;
        return false;
    }
}