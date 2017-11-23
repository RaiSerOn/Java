package StringReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        System.out.println("Введите строки! Конец - слово 'stop'");
        do{
            str = reader.readLine();
            System.out.println(str);
        } while (!str.equals("stop"));
    }
}
