package CharFile;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int i;
        try(FileReader fin = new FileReader("/home/maxim/IdeaProjects/CopyFileChar/src/CharFile/TEXT.txt"); FileWriter fout = new FileWriter("/home/maxim/IdeaProjects/CopyFileChar/src/CharFile/TEXT2.txt")){
            do{
                i = fin.read();
                if((char)i == ' ') i = '-';
                if(i != -1) fout.write(i);
            } while (i != -1);
        }catch (IOException exc){
            System.out.println(exc);
        }
    }
}
