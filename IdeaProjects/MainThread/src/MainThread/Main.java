package MainThread;

public class Main {

    public static void main(String[] args) {
        Thread mainThrd = Thread.currentThread();
        System.out.println("Имя основного потока - " + mainThrd.getName());
        System.out.println("Приоритет основного потока - " + mainThrd.getPriority());
        System.out.println();
        System.out.println("Установка имени потока и приоритета\n");
        mainThrd.setName("Thread # 1");
        mainThrd.setPriority(Thread.NORM_PRIORITY + 3);
        System.out.println("Новое имя основного потока - " + mainThrd.getName());
        System.out.println("Новый приоритет основного потока - " + mainThrd.getPriority());
    }
}
