package RandomAccess;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {

    public static void main(String[] args) {
        double data[] = {19.4, 10.2, 123.54, 33.0, 87.9, 74.25};
        double d;
        try(RandomAccessFile raf = new RandomAccessFile("/home/maxim/IdeaProjects/RandomAccessDemo/src/RandomAccess/TEXT.txt", "rw")){
            for(int i = 0; i < data.length; i ++){
                raf.writeDouble(data[i]);
            }
            raf.seek(0);
            d = raf.readDouble();
            System.out.println("Первое значение " + d);
            raf.seek(8);
            d = raf.readDouble();
            System.out.println("Второе значение " + d);
            raf.seek(16);
            d = raf.readDouble();
            System.out.println("Третье значение " + d);
            System.out.println();
            System.out.println("Чтение значений с нечётным порядковым номером");
            for(int i = 0; i < data.length; i+=2){
                raf.seek(8*i);
                d = raf.readDouble();
                System.out.print(d + " ");
            }
        }catch (IOException exc){
            System.out.println(exc);
        }
    }
}
