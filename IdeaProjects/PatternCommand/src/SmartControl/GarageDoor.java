package SmartControl;


public class GarageDoor {
    String location = "";
    public GarageDoor(String location){this.location = location;}
    public void up(){
        System.out.println("Garage door is up!");
    }
    public void down(){
        System.out.println("Garage door is down!");
    }
    public void stop(){
        System.out.println("Garage door is stop!");
    }
    public void lightOn(){
        System.out.println("Garage light is on!");
    }
    public void lightOff(){
        System.out.println("Garage light is off!");
    }
}