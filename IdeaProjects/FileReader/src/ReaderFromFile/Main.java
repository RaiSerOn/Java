package ReaderFromFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String s;
        try(BufferedReader reader = new BufferedReader(new FileReader("/home/maxim/IdeaProjects/FileReader/src/ReaderFromFile/TEXT.txt"))){
            while ((s = reader.readLine()) != null){
                System.out.println(s);
            }
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
