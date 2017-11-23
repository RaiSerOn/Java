package ReadFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int i;
        FileInputStream fin = null;
//        if(args.length != 1){
//            System.out.println("Использовать : ShowFile имя_файла");
//            return;
//        }
        try{
            fin = new FileInputStream("/home/maxim/IdeaProjects/TextFileRead/src/ReadFile/TEXT.txt");

            do{
                i = fin.read();
                if(i != -1) System.out.println((char)i);
            } while(i != -1);
        }catch (IOException exc){
            System.out.print(exc);
        }
        finally {
            try{
                if(fin != null) fin.close();
            }catch (IOException exc){
                System.out.println(exc);
            }
        }
    }
}
