package geekbains.java_Rush;

import java.io.BufferedReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
Один большой массив и два маленьких

1. Создать массив на 20 чисел.
2. Ввести в него значения с клавиатуры.
3. Создать два массива на 10 чисел каждый.
4. Скопировать большой массив в два маленьких: половину чисел в первый маленький,
вторую половину во второй маленький.
5. Вывести второй маленький массив на экран, каждое значение выводить с новой строки.

*/

public class Main {
    public static void main(String[] args) throws Exception {
        int[] array_int_big = new int[20];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i < array_int_big.length; i ++) {
            array_int_big[i] = Integer.parseInt(reader.readLine());
        }
        int[] array_small1 = Arrays.copyOfRange(array_int_big,0,array_int_big.length / 2);
        for(int i = 0; i < array_small1.length; i++){
            System.out.println(array_small1[i]);
        }
        System.out.println();
        int[] array_small2 = Arrays.copyOfRange(array_int_big,array_int_big.length / 2,array_int_big.length);
        for(int i = 0; i < array_small2.length; i++){
            System.out.println(array_int_big[array_int_big.length / 2 + i] + " ==== ");
            System.out.println(array_small2[i]);
        }
        System.out.println(array_small2.length);
//         for(int i = 0; i < array_small2.length; i ++){
//             System.out.println(array_small2[i]);
//         }
    }
}
