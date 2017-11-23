package geekbains.lesson_5;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Ваша задача угадать число");
        for( int i = 10; i <= 30; i += 10) play_game_number( i );
        System.out.println("Вы выиграли!");
        scanner.close();
    }

    private static void play_game_number( int range){
        System.out.println("Угадайте число от 0 до " + range);
        int number = (int)( Math.random() * range) ;
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
