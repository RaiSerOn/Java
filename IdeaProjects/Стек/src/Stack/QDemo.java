package Stack;

class Queue{
    private int q[];
    private int putloc, getloc;

    Queue(int size){
        q = new int[size + 1];
        putloc = getloc = 0;
    }

    Queue(Queue ob){
        putloc = ob.putloc;
        getloc = ob.getloc;
        q = new int[ob.q.length];

        for(int i = getloc + 1; i <= putloc; i++) q[i] = ob.q[i];
    }

    Queue(int a[]){
        putloc = 0;
        getloc = 0;
        q = new int[a.length + 1];

        for(int i = 0; i < a.length; i++) put(a[i]);
    }

    void put(int ch){
        if(putloc == q.length - 1){
            System.out.println(" - Очередь заполнена");
            return;
        }
        putloc ++;
        q[putloc] = ch;
    }

    int get(){
        if(getloc == putloc){
            System.out.println(" - Очередь пуста");
            return 0;
        }
        getloc ++;
        return q[getloc];
    }
}

public class QDemo {

    public static void main(String[] args) {
        Queue bigQ = new Queue(100);
        Queue smallQ = new Queue(4);
        int ch;
        Queue q1 = new Queue(10);
        int name[] = {1, 2, 3};

        Queue q2 = new Queue(name);
        int i;

        for(i = 0; i < 10; i ++) q1.put(i);

        Queue q3 = new Queue(q1);

        System.out.println("Содержимое q1 :");
        for(i = 0; i < 10; i ++){
            ch = q1.get();
            System.out.print(ch + " ");
        }
        System.out.println("\n");

        System.out.println("Содержимое q2 :");
        for(i = 0; i < 3; i ++){
            ch = q2.get();
            System.out.print(ch + " ");
        }
        System.out.println("\n");

        System.out.println("Содержимое q3 :");
        for(i = 0; i < 10; i ++){
            ch = q3.get();
            System.out.print(ch + " ");
        }
        System.out.println("\n");

        System.out.println("Использование очереди bigQ для сохранения алфавита.");

        for(int h = 0; h < 26; h++){
            bigQ.put(h+1);
        }

        System.out.println("Содержимое очереди bigQ");

        for(int h = 0; h < 26; h++){
            ch = bigQ.get();
            if(ch !=  0) System.out.println(ch);
        }

        System.out.println("\n");

        System.out.println("Использование очереди smallQ для генерации ошибок.");

        for(int h = 0; h < 5; h++){
            System.out.print("Попытка сохранения " + (h + 1));

            smallQ.put(h+1);
            System.out.println();
        }
        System.out.println();

        for(int h = 0; h < 5; h++){
            ch = smallQ.get();
            if(ch != 0) System.out.print(ch);
        }
    }
}
