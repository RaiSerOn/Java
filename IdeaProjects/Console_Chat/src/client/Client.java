package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import main.Const;

/**
 * Created by maxim on 06.09.17.
 */
public class Client {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public Client() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите IP для подключения к серверу.");
        System.out.println("Формат: xxx.xxx.xxx.xxx");
        String ip = sc.nextLine();
        try {
            socket = new Socket(ip, Const.Port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Введите свой ник:");
            out.println(sc.nextLine());

            // Запускаем вывод всех входящих сообщений в консоль
            Resender resend = new Resender();
            resend.start();

            // Пока пользователь не введёт "exit" отправляем на сервер всё, что
            // введено из консоли
            String str = "";
            while (!str.equals("exit")) {
                str = sc.nextLine();
                out.println(str);
            }
            resend.setStop();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close();
        }
    }

        private void close() {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (Exception e) {
                System.err.println("Потоки не были закрыты!");
            }
        }

    private class Resender extends Thread {

        private boolean stoped;

        /**
         * Прекращает пересылку сообщений
         */
        public void setStop() {
            stoped = true;
        }

        /**
         * Считывает все сообщения от сервера и печатает их в консоль.
         * Останавливается вызовом метода setStop()
         *
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            try {
                while (!stoped) {
                    String str = in.readLine();
                    System.out.println(str);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при получении сообщения.");
                e.printStackTrace();
            }
        }
    }
}
