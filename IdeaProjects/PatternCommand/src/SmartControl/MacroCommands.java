package SmartControl;

/**
 * Created by maxim on 23.10.17.
 */
public class MacroCommands implements Command {
    Command[] commands;
    public MacroCommands(Command[] commands){
        this.commands = commands;
    }
    public void execute(){
        for(int i = 0; i < commands.length; i++){
            commands[i].execute();
        }
    }

    public void undo(){
        for(int i = 0; i < commands.length; i++){
            commands[i].undo();
        }
    }
}
