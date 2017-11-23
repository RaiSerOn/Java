package VarCapture;

import java.io.IOException;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        int num = 10;
        MyFunc myLambda = (n) -> {
            int v = num + n;
            //num++; - некорректна, т.к. нельзя менять значение захваченных переменных
            return v;
        };
        System.out.println(myLambda.func(8));
        //num = 9; - данное выражение недопустимо, т.к. оно меняло бы статус final перменной num
        double[] values = {1.0, 2.0, 3.0, 4.0};
        MyIOAction myIO = (rdr) -> {
            int ch = rdr.read();
            return true;
        };
    }
}

interface MyFunc{ //захват перменных
    int func(int n);
}

interface MyIOAction{ //генерация исключений функциональным интерфейсом
    boolean ioAction(Reader rdr) throws IOException;
}

interface Doub<T>{
    void transform(T[] a);
}