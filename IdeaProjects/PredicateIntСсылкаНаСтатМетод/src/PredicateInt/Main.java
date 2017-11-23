package PredicateInt;

import java.util.function.Predicate;

public class Main {

    static boolean numTest(IntPredicate p, int v){
        return p.test(v);
    }

    public static void main(String[] args) {
        if(numTest(MyIntPredicates::isPrime, 17)) System.out.println("17 - простое число");
        if(numTest(MyIntPredicates::isEven, 12)) System.out.println("12 - чётное число");
        if(numTest(MyIntPredicates::isPositive, 11)) System.out.println("11 - положительное число");
        MyIntNum myNum = new MyIntNum(12);
        MyIntNum myNum2 = new MyIntNum(16);
        IntPredicate ip = myNum::isFactor;
        if (ip.test(3)) System.out.println("\n3 является делителем " + myNum.getNum());
        ip = myNum2::isFactor;
        if(!ip.test(3)) System.out.println("3 не является делителем " + myNum2.getNum() + "\n");
        MyIntNumPredicate inp = MyIntNum::isFactor;
        if(inp.test(myNum, 3)) System.out.println("3 является делителем " + myNum.getNum());
        if(!inp.test(myNum2, 3)) System.out.println("3 не является делителем " + myNum2.getNum());

        MYYYY wq = MyyyC::ret;
        System.out.println("\n" + wq.test(5) + "\n");

        SomeTest<Integer> test = MyClass::<Integer>myGenMeth;
        System.out.println(test.test(6, 7));
        //ccылка но кнструктор класса
        MyFunc myClassCons = MyClassG::new;
        MyClassG mc = myClassCons.func();
        System.out.println("Строка str в mc: " + mc.getStr() + "\n");
        Predicate<Integer> isEven = (n) -> (n % 2) == 0;
        if(isEven.test(4)) System.out.println("4 - чётное число");
        if(!isEven.test(5)) System.out.println("5 - нечётное число");
    }
}

interface IntPredicate{
    boolean test(int n);
}

interface MyIntNumPredicate{
    boolean test(MyIntNum mv, int n);
}

class MyIntPredicates{
    static boolean isPrime(int n){
        if(n < 2) return  false;
        for (int i = 2; i <= n/i; i ++){
            if ((n %  i) == 0) return false;
        }
        return true;
    }
    static boolean isEven(int n){
        return (n % 2) == 0;
    }
    static boolean isPositive(int n){
        return n > 0;
    }
}

class MyIntNum{
    private int v;
    MyIntNum(int x){
        v = x;
    }
    int getNum(){
        return v;
    }
    boolean isFactor(int n){
        return (v % n) == 0;
    }
}

interface MYYYY{
    int test(int n);
}

class MyyyC{
    static int ret(int m){
        int h = m;
        return h;
    }
}

//interface  Hello <T>{
//    T Method(T ch);
//}
//
//class  MyClass {
//    static <T> MyMethod1  (T ch){
//        return ch;
//    }
//    static T MyMethod2 (T ch){
//        return ch;
//    }
//}
//
//class Main{
//    public static void main(String [] args){
//        Hello myInt = MyClass::<String>MyMethod1;
//        Hello myInt = MyClass::<String>MyMethod2;
//        System.out.println(myInt.Method("Hello"));
//        System.out.println(myInt.Method(22.0));
//    }
//}

interface SomeTest<T>{
    boolean test(T n, T m);
}

class MyClass{
    static <T> boolean myGenMeth(T x, T y){
        return true;
    }
}

interface MyFunc{
    MyClassG func();;

}
class MyClassG{
    private String str;
    MyClassG(String s){
        str = s;
    }
    MyClassG(){
        str = "";
    }
    static String getStr(){
        return str;
    }
}