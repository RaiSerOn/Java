package Help;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        Help hlpobj = new Help("/home/maxim/IdeaProjects/FileHelp/src/Help/helpfile.txt");
        String topic;
        System.out.println("Воспользуйтесь справочной системой. \n" + "Для выхода из системы введите 'stop'.");
        do{
            topic = hlpobj.getSelection();
            if(!hlpobj.helpon(topic)) System.out.println("Тема не найдена.\n");
        } while (topic.compareTo("stop") != 0);
    }
}

class Help{
    String helpfile;
    Help(String fname){
        helpfile = fname;
    }
    boolean helpon(String what){
        int ch;
        String topic, info;
        try(BufferedReader reader = new BufferedReader(new FileReader(helpfile))){
            do{
                ch = reader.read();
                if(ch == '#'){
                    topic = reader.readLine();
                    if(what.compareTo(topic) == 0){
                        do{
                            info = reader.readLine();
                            if(info != null) System.out.println(info);
                        } while ((info != null) && (info.compareTo("") != 0));
                        return true;
                    }
                }
            } while (ch != -1);
        } catch (IOException exc){
            System.out.println(exc);
            return false;
        }
        return false;
    }
    String getSelection(){
        String topic = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Укажите тему: ");
        try{
            topic = reader.readLine();
        } catch (IOException exc){
            System.out.println(exc);
        }
        return topic;
    }
}