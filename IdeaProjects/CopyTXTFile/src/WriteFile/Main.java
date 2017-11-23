package WriteFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
        int i;


        try(FileInputStream fin = new FileInputStream("/home/maxim/IdeaProjects/TextFileRead/src/ReadFile/TEXT.txt");FileOutputStream fout = new FileOutputStream("/home/maxim/IdeaProjects/TextFileRead/src/ReadFile/TEXT2.txt")){
            do{
                i = fin.read();
                if(i != -1) fout.write(i);
            }   while (i != -1);
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
