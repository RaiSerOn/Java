package Interface;

public class Main {

    public static void main(String [] args){
        ByTwos ob = new ByTwos();
        for(int i = 0; i < 5; i ++){
            System.out.println("Следующее значение - " + ob.getNext());
        }
        System.out.println("\nСброс");
        ob.reset();
        for(int i = 0; i < 5; i ++){
            System.out.println("Следующее значение - " + ob.getNext());
        }
        System.out.println("\nНачальное значение - 100");
        ob.setStart(100);
        for (int i = 0; i < 5; i ++){
            System.out.println("Следующее значение - " + ob.getNext());
        }
        int [] arr = ob.getNextArray(2);
        for (int i = 0; i < arr.length; i ++){
            System.out.println("Массив - " + arr[i]);
        }
    }
}

interface Series{
    int getNext();
    default int[] getNextArray(int n){
        int[] vals = new int [n];
        for(int i = 0; i < n; i++) vals[i] = getNext();
        return vals;
    }
    void reset();
    void setStart(int x);
}


class ByTwos implements Series{
    private int start;
    private int val;

    ByTwos(){
        start = 0;
        val = 0;
    }
    public int getNext(){
        val += 2;
        return val;
    }
    public void reset(){
        start = 0;
        val = 0;
    }
    public void setStart(int x){
        start = x;
        val = x;
    }
}

