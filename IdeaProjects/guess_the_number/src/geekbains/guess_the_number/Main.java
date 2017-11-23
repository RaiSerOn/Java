package geekbains.guess_the_number;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ваша задача угадать число");
        int range = 10;
        int number = (int)(Math.random() * 10);
        while(true){
            System.out.println("Угадайте число от 0 до " + range);
            int our_number = scanner.nextInt();
            if(our_number == number){
                System.out.println("Вы угадали");
                break;
            } else if(our_number > number){
                System.out.println("Загаданное число меньше");
                break;
            } else {
                System.out.println("Загаданное число больше");
                break;
            }
        }
        scanner.close();
    }
}
