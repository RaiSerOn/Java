package WriterInFile;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Признак конца ввода - строка 'stop'");
        try(FileWriter fw = new FileWriter("/home/maxim/IdeaProjects/FileWriterMethod/src/WriterInFile/TEXT.txt")){
            do{
                System.out.print(": ");
                str = reader.readLine();
                if(str.compareTo("stop") == 0) break;
                str = str + "\r\n";
                fw.write(str);
            } while (str.compareTo("stop") != 0);
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
