package geekbains.guess_the_number;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Ваша задача угадать число");
        int range = 100;
        int number = (int)(Math.random() * range);
        play_game_number( number, range );
        scanner.close();
    }

    private static void play_game_number( int number , int range){
        System.out.println("Угадайте число от 0 до " + range);
        while(true){
            int our_number = scanner.nextInt();
            if(our_number == number){
                System.out.println("Вы угадали");
                break;
            } else if(our_number > number){
                System.out.println("Загаданное число меньше");
            } else {
                System.out.println("Загаданное число больше");
            }
        }
    }

}
