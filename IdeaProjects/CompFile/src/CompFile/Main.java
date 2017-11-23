package CompFile;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int i = 0, j = 0, number = 1;
        try (FileInputStream f1 = new FileInputStream("/home/maxim/IdeaProjects/CompFile/src/CompFile/TEXT.txt");
             FileInputStream f2 = new FileInputStream("/home/maxim/IdeaProjects/CompFile/src/CompFile/TEXT2.txt")) {
            do {
                i = f1.read();
                j = f2.read();
                if (i < 97 && i > 64) i = i + 32;
                if (j < 97 && j > 64) j = j + 32;
                if (i != j) {
                    System.out.println("Символы несовпали на позиции " + number);
                    break;
                }
                number++;
            } while (i != -1 & j != -1);
            if (i != j) System.out.println("Содержимое файлов отличается");
            else System.out.println("Содержимое файлов совпадает");
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
}