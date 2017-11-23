package SmartControl;


public class Light {
    String location = "";

    public Light(String type){
        this.location = type;
    }

    public void on(){
        System.out.println("Light is on!");
    }
    public void off(){
        System.out.println("Light is off!");
    }
}
