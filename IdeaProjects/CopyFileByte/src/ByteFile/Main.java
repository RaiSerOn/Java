package ByteFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int i;
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try{
            fin = new FileInputStream("/home/maxim/IdeaProjects/CopyFileByte/src/ByteFile/TEXT.txt");
            fout = new FileOutputStream("/home/maxim/IdeaProjects/CopyFileByte/src/ByteFile/TEXT2.txt");
            do{
                i = fin.read();
                if((char)i == ' ') i = '-';
                if(i != -1) fout.write(i);
            } while (i != -1);
        } catch (IOException exc){
            System.out.println(exc);
        } finally {
            try{
                if(fin != null) fin.close();
            }catch (IOException exc){
                System.out.println(exc);
            }
            try{
                if(fin != null) fout.close();
            }catch (IOException exc){
                System.out.println(exc);
            }
        }
    }
}