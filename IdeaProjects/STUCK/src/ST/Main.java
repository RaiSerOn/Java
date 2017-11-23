package ST;

public class Main {

    public static void main(String[] args) {
        String [] arrStr = new String[2];
        Stuck<String> arr = new Stuck<String>(arrStr);
        try{
            for (int i = 0; i < 11; i ++){
                System.out.println("Поместить элемент 1 + " + i);
                arr.put("1" + i);
            }
        } catch (StuckFullException exc){
            System.out.println(exc = new StuckFullException(arrStr.length));
        }
        try{
            for (int i = 0; i < 6; i ++){
                System.out.print("Извлекаем элемент 1 + i ");
                System.out.println(arr.get());
            }
        } catch (StuckEmptyException exc){
            System.out.println(exc);
        }
    }
}

interface  StuckMethods <T>{
    void put(T ch) throws StuckFullException;
    T get() throws StuckEmptyException;
}

class StuckFullException extends Exception{
    int size;
    StuckFullException(int s){
        size = s;
    }
    public String toString(){
        return "Максимальный размер очереди - " + size + " элементов";
    }
}

class StuckEmptyException extends Exception{
    public String toString(){
        return "Очередь пуста";
    }
}

class Stuck <T> implements StuckMethods<T>{
    T[] s;
    int putloc;
    Stuck(T[] q){
        s = q;
        putloc = -1;
    }
    public void put(T ch) throws StuckFullException{
        if(++putloc == s.length){
            putloc--;
            throw new StuckFullException(s.length);
        }
        s[putloc] = ch;
    }
    public T get() throws StuckEmptyException{
        if(putloc < 0) throw new StuckEmptyException();
        return s[putloc--];
    }
}