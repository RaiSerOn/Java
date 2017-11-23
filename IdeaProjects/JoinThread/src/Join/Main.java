package Join;

public class Main {

    public static void main(String[] args) {
        System.out.println("Запуск основного потока");
        MyThread mt1 = new MyThread("Child # 1");
        MyThread mt2 = new MyThread("Child # 2");
        MyThread mt3 = new MyThread("Child # 3");
        try{
            mt1.thrd.join();
            System.out.println("Child #1 - присоединён");
            mt2.thrd.join();
            System.out.println("Child #2 - присоединён");
            mt3.thrd.join();
            System.out.println("Child #3 - присоединён");
        } catch (InterruptedException exc){
            System.out.println(exc);
        }
        System.out.println("Завершение основного потока");
    }
}

class MyThread implements Runnable{
    Thread thrd;
    MyThread(String name){
        thrd = new Thread(this, name);
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
        System.out.println(thrd.getName() + " - завершение");
    }
}
