package Code;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(Kata.HighAndLow("8999 -10"));
    }
}



class Kata {
    public static String HighAndLow(String numbers) {

        int min = Arrays.stream(numbers.split(" "))
                .mapToInt(i -> Integer.parseInt(i))
                .min()
                .getAsInt();

        int max = Arrays.stream(numbers.split(" "))
                .mapToInt(i -> Integer.parseInt(i))
                .max()
                .getAsInt();

        return String.format("%d %d", max, min);
    }
}
