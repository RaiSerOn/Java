package Applet;

import java.applet.*;
import java.awt.*;

/*
<applet code="SimpleApplet" width = "200" height = "60">
</applet>
*/

public class SimpleApplet extends Applet implements Runnable{
    String msg = "Java rules the WEB";
    Thread t;
    boolean stopFlag;
    public void init(){
        t = null;
    }
    public void start(){
        t = new Thread(this);
        stopFlag = false;
        t.start();
    }
    public void run(){
        for(;;){
            try{
                repaint();
                Thread.sleep(250);
                if(stopFlag) break;
            } catch (InterruptedException exc){
                System.out.println(exc);
            }
        }
    }
    public void stop(){
        stopFlag = true;
        t = null;
    }
    public void paint(Graphics g){
        char ch;
        ch = msg.charAt(0);
        msg = msg.substring(1,msg.length());
        msg += ch;
        g.drawString(msg, 50, 50);
    }
}
