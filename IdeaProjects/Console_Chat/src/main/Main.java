package main;

import java.util.Scanner;

import server.Server;
import client.Client;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите режим работы - клиентский или серверный: c(client) / s(server)");
        while (true){
            char answer = Character.toLowerCase(sc.nextLine().charAt(0));
            if(answer == 's'){
                new Server();
                break;
            } else if(answer == 'c'){
                new Client();
                break;
            } else {
                System.out.println("Некорректный ввод. Повторите пожалуйста с учётом инструкции!");
            }
        }
    }
}
