package Exercise;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Запуск основного потока");
        MyThread2 mt2 = new MyThread2("Child # 2");
        for(int i = 0; i < 50; i ++){
            System.out.print(".");
            try {
                Thread.sleep(100);
            } catch (InterruptedException exc){
                System.out.println(exc);
            }
        }
        System.out.println("Завершение основного потока");
    }
}

class MyThread2 extends Thread{
    MyThread2(String name){
        super(name);
        start();
    }
    public void run(){
        System.out.println(getName() + " - запуск");
        try{
            for(int i = 0; i < 10; i ++){
                Thread.sleep(400);
                System.out.println("В " + getName() + ", счётчик: " + i);
            }
        }catch (InterruptedException exc){
            System.out.println(exc);
        }
    }
}
