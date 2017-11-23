package SmartControl;

public class Stereo {
    String location = "";
    public Stereo(String location){this.location = location;}
    public void on(){
        System.out.println("Stereo system is on!");
    }
    public  void off(){
        System.out.println("Stereo system is off!");
    }
    public void setCd(){
        System.out.println("CD was placed!");
    }
    public void setDvd(){
        System.out.println("DVD was placed!");
    }
    public void setRadio(){
        System.out.println("Radio was seted!");
    }
    public void setVolume(int volume){ System.out.println("Volume is " + volume + "!");}
}
