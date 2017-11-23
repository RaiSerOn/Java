package TypesOfQueue;

public interface ICharQ{
    void put(char ch);
    char get();
    void reset();
}

class FixedQueue implements  ICharQ{
    private char [] q;
    private int putloc, getloc;
    public FixedQueue(int size){
        q = new char[size + 1];
        putloc = getloc = 0;
    }
    static FixedQueue copy(FixedQueue que){
        FixedQueue newT = new FixedQueue(que.q.length);
        for(int i = 0; i < que.q.length; i++){
            newT.q[i] = que.q[i];
        }
        newT.putloc = que.putloc;
        return newT;
    }
    public void put(char ch){
        if(putloc == q.length - 1) {
            System.out.println("Очередь заполнена");
            return;
        }
        putloc++;
        q [putloc] = ch;

    }
    public char get(){
        if (getloc == putloc){
            System.out.println(" - Очередь пуста");
            return (char) 0;
        }
        getloc++;
        return q[getloc];
    }
    public void reset(){
        putloc = getloc = 0;
    }
}

class CircularQueue implements ICharQ{
    private char q [];
    private int putloc, getloc;
    public CircularQueue(int size){
        q = new char[size + 1];
        putloc = getloc = 0;
    }
    public void put(char ch){
        if(putloc + 1 == getloc | ((putloc == q.length-1) & (getloc == 0))){
            System.out.println(" - Очередь заполнена");
            return;
        }
        putloc++;
        if(putloc == q.length) putloc = 0;
        q[putloc] = ch;
    }
    public char get(){
        if(getloc == putloc){
            System.out.println(" - Очередь пуста");
            return (char) 0;
        }
        getloc++;
        if(getloc == q.length) getloc = 0;
        return q[getloc];
    }
    public void reset(){
        putloc = getloc = 0;
    }
}

class DynQueue implements ICharQ{
    private char q[];
    private int putloc, getloc;

    public DynQueue(int size){
        q = new char[size + 1];
        putloc = getloc = 0;
    }
    public void put(char ch){
        if(putloc == q.length - 1){
            char t[] = new char [q.length * 2];
            for(int i = 0; i < q.length; i ++) t[i] = q[i];
            q = t;
        }
        putloc ++;
        q[putloc] = ch;
    }
    public char get(){
        if(getloc == putloc){
            System.out.println(" - Очередь пуста");
            return (char) 0;
        }
        getloc ++;
        return q[getloc];
    }
    public void reset(){
        putloc = getloc = 0;
    }
}

class CircularDynQueue implements ICharQ{
    private char q [];
    private int putloc, getloc;
    public CircularDynQueue(int size){
        q = new char[size + 1];
        putloc = getloc = 0;
    }
    public void put(char ch){
        if(putloc + 1 == getloc | ((putloc == q.length-1) & (getloc == 0))){
            char t [] = new char [q.length * 2];
            for(int i = 0; i < q.length; i ++) t[i] = q[i];
            q = t;
        }
        putloc++;
        q[putloc] = ch;
    }
    public char get(){
        if(getloc == putloc){
            System.out.println(" - Очередь пуста");
            return (char) 0;
        }
        getloc++;
        if(getloc == q.length) getloc = 0;
        return q[getloc];
    }
    public void reset(){
        putloc = getloc = 0;
    }
}

class Main{
    public static void main(String args[]){
        CircularDynQueue q4 = new CircularDynQueue(10);
        FixedQueue q1 = new FixedQueue(10);
        DynQueue q2 = new DynQueue(5);
        CircularQueue q3 = new CircularQueue(10);

        char ch;
        int i;
        for(i = 0; i < 10; i ++){
            q1.put((char)('A' + i));
        }
        FixedQueue q5 = q1.copy(q1);
        System.out.print("Содержимое фиксированной очереди : ");
        for(i = 0; i < 10; i ++){
            ch = q1.get();
            System.out.print(ch);
        }

        System.out.println();

        System.out.print("Содержимое копированной фиксированной очереди : ");
        for(i = 0; i < 10; i ++){
            ch = q5.get();
            System.out.print(ch);
        }

        System.out.println();

        for(i = 0; i < 10; i ++){
            q2.put((char)('Z' - i));
        }
        System.out.print("Содержимое динамической очереди : ");
        for(i = 0; i < 10; i ++){
            ch = q2.get();
            System.out.print(ch);
        }

        System.out.println();

        for(i = 0; i < 10; i ++){
            q3.put((char)('A' + i));
        }
        System.out.print("Содержимое кольцевой очереди : ");
        for(i = 0; i < 10; i ++){
            ch = q3.get();
            System.out.print(ch);
        }

        System.out.println();

        for(i = 10; i < 20; i ++){
            q3.put((char)('A' + i));
        }
        System.out.print("Содержимое кольцевой очереди : ");
        for(i = 0; i < 10; i ++){
            ch = q3.get();
            System.out.print(ch);
        }

        System.out.println("\nСохранение и использование данных кольцевой очереди");
        for(i = 0; i < 20; i ++){
            q3.put((char)('A' + i));
            ch = q3.get();
            System.out.print(ch);
        }

        System.out.println();

        for(i = 0; i < 10; i ++){
            q4.put((char)('A' + i));
        }
        for(i = 0; i < 10; i ++){
            q4.put((char)('K' + i));
        }
        System.out.print("Содержимое динамической круговой очереди : ");
        for(i = 0; i < 30; i ++){
            ch = q4.get();
            System.out.print(ch);
        }
    }
}