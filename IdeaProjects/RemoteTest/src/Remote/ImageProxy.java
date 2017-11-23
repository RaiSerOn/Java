package Remote;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageProxy implements Icon {
    ImageIcon imageIcon;
    URL url;
    Thread retrievalThread;
    boolean retrieving = false;

    public ImageProxy(URL url){
        this.url = url;
    }

    public int getIconWidth(){
        if(imageIcon != null){
            return imageIcon.getIconWidth();
        } else {
            return 800;
        }
    }

    public int getIconHeight(){
        if(imageIcon != null){
            return imageIcon.getIconHeight();
        } else {
            return 600;
        }
    }

    public void paintIcon(final Component c, Graphics g, int x, int y){
        if(imageIcon != null){
            imageIcon.paintIcon(c, g, x, y);
        } else {
            g.drawString("Loading CD cover, please wait...", x + 300, y + 190);
            if(!retrieving){
                retrieving = true;
                retrievalThread = new Thread(new Runnable(){
                    public void run(){
                        try{
                            imageIcon = new ImageIcon(url, "CD cover");
                            c.repaint();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                retrievalThread.start();
            }
        }
    }

}
