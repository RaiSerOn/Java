package geekbains.lesson_2;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

    //        System.out.println("Введите число !!");
    //        Scanner scanner = new Scanner(System.in);
    //        int a = scanner.nextInt();
    //        System.out.println("a = " + a);

        System.out.println("Введите операцию !!");
        System.out.println("1. Сложение");
        System.out.println("2. Вычитание");
        System.out.println("3. Умножение");
        System.out.println("4. Деление");
        Scanner scanner = new Scanner(System.in);
        int operation = scanner.nextInt();
        System.out.println("Введите первое число!");
        int first = scanner.nextInt();
        System.out.println("Введите второе число число!");
        int second = scanner.nextInt();
        int result = 0;
        if( operation == 1 ){
            result = first + second;
        } else if( operation == 2 ){
            result = first - second;
        } else if( operation == 3 ){
            result = first * second;
        } else if( operation == 4 ){
            result = first / second;
        } else {
            System.out.println("Неверная операция !!");
            System.exit(0);
        }
        System.out.println(" Результат = " + result);
    }
}


