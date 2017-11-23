package CharReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{
        char c;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ввести символы! Конец - '.'");
        do{
            c = (char)reader.read();
            System.out.println(c);
        } while (c != '.');
    }
}
