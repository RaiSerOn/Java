package SystemIn;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
        byte data[] = new byte[10];
        System.out.println("Введите строку");
        System.in.read(data);
        System.out.print("Вы ввели - ");
        for(int i = 0; i < data.length; i ++){
            System.out.print((char)data[i]);
        }
    }
}
