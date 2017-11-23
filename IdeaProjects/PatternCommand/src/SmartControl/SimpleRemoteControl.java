package SmartControl;

/**
 * Created by maxim on 23.10.17.
 */
public class SimpleRemoteControl {
    Command slot;

    public SimpleRemoteControl(){}

    public void setCommand(Command command){
        slot = command;
    }

    public void ButtonWasPressed(){
        slot.execute();
    }
}
