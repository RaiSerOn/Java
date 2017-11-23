package Que;

public class Main {

    public static void main(String[] args) {
        String [] array = new String [10];
        QueueChat arr = new QueueChat(array);
        for(int i = 0; i < 2; i ++){
            arr.put("Ghbdtn");
        }
        arr.put("FRRRRRR");
        arr.put("FRRRRRR22");
        for(int i = 0; i < 10; i ++){
            System.out.println(arr.get());
        }
        System.out.println();
        String string = arr.get();
        //System.out.println()
        while (string != null){
            System.out.println(1);
            string = arr.get();
        }
    }
}

class QueueChat{
    String [] str;
    int putloc, getloc;
    QueueChat(String[] q){
        str = q;
        putloc = getloc = 0;
    }
    void put(String string){
        if(putloc == str.length){
            for(int i = 0; i < str.length - 1; i ++){
                str[i] = str[i + 1];
            }
            str[str.length - 1] = string;
            return;
        }
        str[putloc++] = string;
    }
    String get(){
        if(getloc == str.length) getloc = 0;
        return str[getloc++];
    }
}