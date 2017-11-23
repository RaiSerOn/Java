package Runnable;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Запуск основного потока");
	    //MyThread mt = new MyThread("Child # 1");
	    MyThread2 mt2 = new MyThread2("Child # 2");
	    //Thread newThrd = new Thread(mt);
	    //newThrd.start();
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

class MyThread implements Runnable{
    String thrdName;
    MyThread(String name){
        thrdName = name;
    }
    public void run(){
        System.out.println("Start");
        try{
            for(int i = 0; i < 10; i ++){
                Thread.sleep(400);
                System.out.println("В " + thrdName + ", счётчик: " + i);
            }
        }catch (InterruptedException exc){
            System.out.println(exc);
        }
        System.out.println("End");
    }
}

class MyThread2 implements Runnable{
    Thread thrd;
    MyThread2(String name){
        thrd = new Thread(this,name);
        thrd.start();
    }
    public void run(){
        System.out.println(thrd.getName() + " - запуск");
        try{
            for(int i = 0; i < 10; i ++){
                Thread.sleep(400);
                System.out.println("В " + thrd.getName() + ", счётчик: " + i);
            }
        }catch (InterruptedException exc){
            System.out.println(exc);
        }
    }
}
