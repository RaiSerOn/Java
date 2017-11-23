package GenQueue;

public class Main {

    public static void main(String[] args) {
        Integer iStore[] = new Integer[10];
        GenQueue<Integer> q = new GenQueue<Integer>(iStore);
        Integer iVal;
        System.out.println("Демонстрация очереди Integer");
        try{
            for(int i = 0; i < 5; i ++){
                System.out.println("Добавление " + i + " в очередь q");
                q.put(i);
            }
        } catch (QueueFullException exc){
            System.out.println(exc);
        }
        System.out.println();
        try{
            for(int i = 0; i < 5; i ++){
                System.out.print("Получение следующего числа типа Integer из очереди q: ");
                iVal = q.get();
                System.out.println(iVal);
            }
        } catch (QueueEmptyException exc){
            System.out.println(exc);
        }
    }
}

interface IGen<T>{
    void put(T ch) throws QueueFullException;
    T get() throws QueueEmptyException;
}

class QueueFullException extends Exception{
    int size;
    QueueFullException(int s){
        size = s;
    }
    public String toString(){
        return "\nОчередь заполнена. Максимальный размер очереди " + size;
    }
}
class QueueEmptyException extends Exception{
    public String toString(){
        return "\nОчередь пуста";
    }
}

class GenQueue<T> implements IGen <T>{
    private T q[];
    private int putloc, getloc;
    public GenQueue(T[] aRef){
        q = aRef;
        putloc = getloc = 0;
    }
    public void put(T obj) throws QueueFullException{
        if(putloc == q.length - 1) throw new QueueFullException(q.length);
        q[putloc++] = obj;
    }
    public T get() throws QueueEmptyException{
        if(putloc == getloc) throw new QueueEmptyException();
        return q[getloc++];
    }
}