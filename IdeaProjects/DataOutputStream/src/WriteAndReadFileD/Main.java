package WriteAndReadFileD;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        int i = 10;
        double d = 1023.56;
        boolean b = true;
        try(DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("/home/maxim/IdeaProjects/TextFileRead/src/ReadFile/TEXT.txt"))){
            System.out.println("Записано: " + i);
            dataOut.writeInt(i);
            System.out.println("Записано: " + d);
            dataOut.writeDouble(d);
            System.out.println("Записано: " + b);
            dataOut.writeBoolean(b);
            System.out.println("Записано: " + 12.2 * 7.4);
            dataOut.writeDouble(12.2 * 7.4);
        } catch (IOException exc){
            System.out.println(exc);
            return;
        }
        System.out.println();
        try(DataInputStream dataIn = new DataInputStream(new FileInputStream("/home/maxim/IdeaProjects/TextFileRead/src/ReadFile/TEXT.txt"))){
            i = dataIn.readInt();
            System.out.println("i = " + i);
            d = dataIn.readDouble();
            System.out.println("d = " + d);
            b = dataIn.readBoolean();
            System.out.println("b = " + b);
            d = dataIn.readDouble();
            System.out.println("d = " + d);
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
