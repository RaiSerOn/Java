package Synchronized;

public class Main {

    public static void main(String[] args) {
        int a[] = {10, 20, 40, 4444, 50, 5};
        MyThread mt1 = new MyThread("Child # 1", a);
        MyThread mt2 = new MyThread("Child # 2", a);
    }
}

class SumArray{
    private  int sum;
    synchronized int sumArray(int nums[]){
        sum = 0;
        for(int i = 0; i < nums.length; i ++){
            sum += nums[i];
            System.out.println("Текущее значение суммы для " + Thread.currentThread().getName() + " : " + sum);
            try{
                Thread.sleep(10);
            } catch (InterruptedException exc){
                System.out.println(exc);
            }
        }
        return sum;
    }
}

class MyThread implements Runnable{
    Thread thrd;
    static SumArray sa = new SumArray();
    int a[];
    int answer;
    MyThread(String name, int[] nums){
        thrd = new Thread(this, name);
        a = nums;
        thrd.start();
    }
    public  void run(){
        int sum;
        System.out.println("Запуск - " + thrd.getName());
        answer = sa.sumArray(a);
        System.out.println("СУММА для " + thrd.getName() + ": " + answer);
        System.out.println("Завершение - " + thrd.getName());
    }
}