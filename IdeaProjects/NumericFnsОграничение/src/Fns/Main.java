package Fns;

public class Main {

    public static void main(String[] args) {
        NumericFns<Integer> iOb = new NumericFns<Integer>(5);
        NumericFns<Long> lOb = new NumericFns<Long>(5l);
        System.out.println("Обратная величина iOb - " + iOb.reciprocal());
        System.out.println("Дробная часть iOb - " + iOb.fraction() + "\n");
        NumericFns<Double> dOb = new NumericFns<Double>(5.25);
        System.out.println("Обратная величина dOb - " + dOb.reciprocal());
        System.out.println("Дробная часть dOb - " + dOb.fraction() + "\n");
        if(iOb.absEqual(dOb)) System.out.println("Абсолютные значения совпадают");
        else System.out.println("Абсолютные значения не совпадают");
        if(iOb.absEqual(lOb)) System.out.println("Абсолютные значения совпадают");
        else System.out.println("Абсолютные значения не совпадают");
    }
}

class NumericFns<T extends Number>{ // Верхняя граница допустимых классов - Number, а значит все допустимые классы - числовые
    T num; // можно написать T, V extends T - это означает, что оба типа должны быть одинаковыми, либо быть наследниками одного общего класса Т
    NumericFns(T n){
        num = n;
    }
    double reciprocal(){
        return 1 / num.doubleValue();
    }
    double fraction(){
        return num.doubleValue() - num.intValue();
    }
    boolean absEqual(NumericFns<?> ob){ // Вопрос означает шаблон - используется тут, чтобы не совпадали типы, можно использовать extens для ограничения подобно типу параметров класса выше
        if(Math.abs(ob.num.doubleValue()) == Math.abs(num.doubleValue())) return true;
        return false;
    }
}