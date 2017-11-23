package SmartControl;

/**
 * Created by maxim on 23.10.17.
 */
public class GarageDoorOpenCommand implements Command {
    GarageDoor door;
    public GarageDoorOpenCommand(GarageDoor door){
        this.door = door;
    }
    public void execute(){
        door.up();
    }
    public void undo(){door.down();}
}
