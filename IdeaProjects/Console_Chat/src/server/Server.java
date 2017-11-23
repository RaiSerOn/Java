package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import main.Const;

class QueueChat{
    String [] str;
    int putloc, getloc;
    int flag = 0;
    QueueChat(String[] q){
        str = q;
        putloc = getloc = 0;
    }
    void put(String string){
        if(flag == 0) flag++;
        if(putloc == str.length){
            for(int i = 0; i < str.length - 1; i ++){
                str[i] = str[i + 1];
            }
            str[str.length - 1] = string;
            return;
        }
        str[putloc++] = string;
    }
    String get(){
        if(getloc == str.length) getloc = 0;
        return str[getloc++];
    }
}

/**
 * Обеспечивает работу программы в режиме сервера
 *
 * @author Влад
 */
public class Server {

    private String [] arr = new String[10];
    private QueueChat chatArr = new QueueChat(arr);
    private String string;

    /**
     * Специальная "обёртка" для ArrayList, которая обеспечивает доступ к
     * массиву из разных нитей
     */
    private List<Connection> connections =
            Collections.synchronizedList(new ArrayList<Connection>());
    private ServerSocket server;

    /**
     * Конструктор создаёт сервер. Затем для каждого подключения создаётся
     * объект Connection и добавляет его в список подключений.
     */
    public Server() {
        try {
            server = new ServerSocket(Const.Port);

            while (true) {
                Socket socket = server.accept();

                // Создаём объект Connection и добавляем его в список
                Connection con = new Connection(socket);
                connections.add(con);

                // Инициализирует нить и запускает метод run(),
                // которая выполняется одновременно с остальной программой
                con.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Закрывает все потоки всех соединений а также серверный сокет
     */
    private void closeAll() {
        try {
            server.close();

            // Перебор всех Connection и вызов метода close() для каждого. Блок
            // synchronized {} необходим для правильного доступа к одним данным
            // их разных нитей
            synchronized(connections) {
                Iterator<Connection> iter = connections.iterator();
                while(iter.hasNext()) {
                    ((Connection) iter.next()).close();
                }
            }
        } catch (Exception e) {
            System.err.println("Потоки не были закрыты!");
        }
    }

    /**
     * Класс содержит данные, относящиеся к конкретному подключению:
     * <ul>
     * <li>имя пользователя</li>
     * <li>сокет</li>
     * <li>входной поток BufferedReader</li>
     * <li>выходной поток PrintWriter</li>
     * </ul>
     * Расширяет Thread и в методе run() получает информацию от пользователя и
     * пересылает её другим
     *
     */
    private class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

        private String name = "";

        /**
         * Инициализирует поля объекта и получает имя пользователя
         *
         * @param socket
         *            сокет, полученный из server.accept()
         */
        public Connection(Socket socket) {
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        /**
         * Запрашивает имя пользователя и ожидает от него сообщений. При
         * получении каждого сообщения, оно вместе с именем пользователя
         * пересылается всем остальным.
         *
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            try {
                name = in.readLine();
                // Отправляем всем клиентам сообщение о том, что зашёл новый пользователь
                synchronized(connections) {
                    Iterator<Connection> iter = connections.iterator();

                    //Получаю последнего приконнектившегося пользователя
                    Iterator<Connection> iter_last = connections.iterator();
                    Connection connect_last = (Connection) iter_last.next();
                    while(iter_last.hasNext()) {
                        connect_last = iter_last.next();
                    }

                    while(iter.hasNext()) {
                        Connection connect = (Connection) iter.next();
                        connect.out.println(name + " cames now");
                    }

                    //Отправляю последнему добавившемуся пользователю последние 10 сообщений из диалога
                    connect_last.out.println("///////////////////////////");
                    connect_last.out.println("Последние сообщения диалога:");
                    if(chatArr.flag == 0) connect_last.out.println("Сообщений ещё нет!");
                    else {
                        for (int i = 0; i < 10; i++) {
                            string = chatArr.get();
                            if (string == null) break;
                            connect_last.out.println(string);
                        }
                        chatArr.getloc = 0;
                    }
                    connect_last.out.println("///////////////////////////\n");
                }

                String str = "";
                while (true) {
                    str = in.readLine();
                    chatArr.put(name + ": " + str);
                    if(str.equals("exit")) break;

                    // Отправляем всем клиентам очередное сообщение
                    synchronized(connections) {
                        Iterator<Connection> iter = connections.iterator();
                        while(iter.hasNext()) {
                            ((Connection) iter.next()).out.println(name + ": " + str);
                        }
                    }
                }

                synchronized(connections) {
                    Iterator<Connection> iter = connections.iterator();
                    while(iter.hasNext()) {
                        ((Connection) iter.next()).out.println(name + " has left");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        /**
         * Закрывает входной и выходной потоки и сокет
         */
        public void close() {
            try {
                in.close();
                out.close();
                socket.close();

                // Если больше не осталось соединений, закрываем всё, что есть и
                // завершаем работу сервера
                connections.remove(this);
                if (connections.size() == 0) {
                    Server.this.closeAll();
                    System.exit(0);
                }
            } catch (Exception e) {
                System.err.println("Потоки не были закрыты!");
            }
        }
    }
}