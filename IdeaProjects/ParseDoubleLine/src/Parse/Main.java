package Parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n;
        String str;
        double sum = 0.0, avg, t;
        System.out.print("Сколько чисел вы введёте? ");
        str = reader.readLine();
        try{
            n = Integer.parseInt(str);
        } catch (NumberFormatException exc){
            System.out.println("Формат не совпадает!");
            n = 0;
        }
        System.out.println("Ввод " + n + " значений");
        for(int i = 0; i < n; i ++){
            System.out.print(": ");
            str = reader.readLine();
            try{
                t = Double.parseDouble(str);
            } catch (NumberFormatException exc){
                System.out.println("Неверный формат");
                t = 0.0;
            }
            sum += t;
        }
        avg = sum / n;
        System.out.println("Среднее значение - " + avg);
    }
}
