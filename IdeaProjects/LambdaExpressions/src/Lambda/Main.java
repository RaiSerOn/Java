package Lambda;

public class Main {

    public static void main(String[] args) {
        MyValue myVal = () -> 98.1;
        MyParamValue myPval = n -> 1.0 / n;
        System.out.println("Постоянное значение " + myVal.getValue());
        System.out.println("Обратная величина 4 равна " + myPval.getValue(4.0));
        System.out.println("Обратная величина 8 равна " + myPval.getValue(8.0));
        myVal = () -> 1.0;
        System.out.println("Постоянное значение " + myVal.getValue() + "\n");

        NumericTest isFactor = (n , d) -> (n % d) == 0;
        if(isFactor.test(10, 2)) System.out.println("2 является делителем 10");
        if(!isFactor.test(10, 3)) System.out.println("3 не является делителем 10\n");
        NumericTest lessThan = (n, m) -> (n < m);
        if(lessThan.test(2, 10)) System.out.println("2 меньше 10");
        if(!lessThan.test(10, 2)) System.out.println("10 не меньше 2\n");
        NumericTest absEqual = (n, m) -> (n < 0 ? -n : n) == (m < 0 ? -m : m);
        if(absEqual.test(4, -4)) System.out.println("Абсолютные величины 4 и -4 равны");
        if(!absEqual.test(4, -5)) System.out.println("Абсолютные величины 4 и -5 не равны\n");

        StringTest isIn = (a, b) -> a.contains(b);
        String str = "Это тест";
        System.out.println("Тестируемая строка - " + str);
        if(isIn.test(str, "Это")) System.out.println("'Это' найдено");
        else System.out.println("'Это' не найдено");
        if(isIn.test(str, "Znt")) System.out.println("'Znt' найдено");
        else System.out.println("'Znt' не найдено");

        NumericFunc smallestF = n -> {
            int result = 1;
            n = n < 0 ? -n : n;
            for(int i = 2; i <= n/i; i ++){
                if((n % i) == 0){
                    result = i;
                    break;
                }
            }
            return result;
        };
        System.out.println("\nНаименьшим делителем 12 является " + smallestF.func(12));
        System.out.println("Наименьшим делителем 11 является " + smallestF.func(11) + "\n");

        SomeTest<Integer> isFactorial = (n, d) -> (n % d) == 0;
        if(isFactorial.test(10, 2)) System.out.println("2 является делителем 10\n");
        SomeTest<String> isInStr = (a, b) -> a.contains(b);
        if(isInStr.test("Обобщённый функциональный интерфейс", "фейс")) System.out.println("'фейс' найдено");

    }
}

interface MyValue{//функциональный интерфейс
    double getValue();
}

interface MyParamValue{//функциональный интерфейс
    double getValue(double v);
}

interface NumericTest{//функциональный интерфейс
    boolean test(int n, int m);
}

interface StringTest{//функциональный интерфейс
    boolean test(String aStr, String bStr);
}

interface NumericFunc{
    int func(int n);
}

interface SomeTest <T>{//обобщённый функциональный интерфес
    boolean test(T n, T m);
}