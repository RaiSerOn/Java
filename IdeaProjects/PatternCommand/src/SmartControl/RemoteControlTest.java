package SmartControl;


public class RemoteControlTest {
    public static void main(String args[]){

        RemoteControl remoteControl = new RemoteControl();
        Light livingRoomLight = new Light("Living Room");
        Light kitchenRoomLight = new Light("Kitchen Room");
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        GarageDoor garageDoor = new GarageDoor("");
        Stereo stereo = new Stereo("Living Room");
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand kitchenRoomLightOn = new LightOnCommand(kitchenRoomLight);
        LightOffCommand kitchenRoomLightOff = new LightOffCommand(kitchenRoomLight);
        CeilingFanOnCommand ceilingFanOnCommand = new CeilingFanOnCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOffCommand = new CeilingFanOffCommand(ceilingFan);
        GarageDoorOpenCommand garageDoorOpenCommand = new GarageDoorOpenCommand(garageDoor);
        GarageDoorCloseCommand garageDoorCloseCommand = new GarageDoorCloseCommand(garageDoor);
        StereoOnWithCdCommand stereoOnWithCdCommand = new StereoOnWithCdCommand(stereo);
        StereoOffCommand stereoOffCommand = new StereoOffCommand(stereo);
        Command[] commands = {livingRoomLightOn, kitchenRoomLightOn, ceilingFanOnCommand, garageDoorOpenCommand, stereoOnWithCdCommand};
        Command[] commandsOff = {livingRoomLightOff, kitchenRoomLightOff, ceilingFanOffCommand, garageDoorCloseCommand, stereoOffCommand};
        MacroCommands macroCommandsOn = new MacroCommands(commands);
        MacroCommands macroCommandsOff = new MacroCommands(commandsOff);
        remoteControl.setCommand(5, macroCommandsOn, macroCommandsOff);
        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setCommand(1, kitchenRoomLightOn, kitchenRoomLightOff);
        remoteControl.setCommand(2, ceilingFanOnCommand, ceilingFanOffCommand);
        remoteControl.setCommand(3, garageDoorOpenCommand, garageDoorCloseCommand);
        remoteControl.setCommand(4, stereoOnWithCdCommand, stereoOffCommand);
        System.out.println(remoteControl);
        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);
        remoteControl.undoButtonWasPushed();
        remoteControl.onButtonWasPushed(1);
        remoteControl.undoButtonWasPushed();
        remoteControl.offButtonWasPushed(1);
        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);
        remoteControl.onButtonWasPushed(3);
        remoteControl.offButtonWasPushed(3);
        remoteControl.onButtonWasPushed(4);
        remoteControl.offButtonWasPushed(4);
        remoteControl.undoButtonWasPushed();
        System.out.println("\n PATTY ON");
        remoteControl.onButtonWasPushed(5);
        remoteControl.offButtonWasPushed(5);
//        SimpleRemoteControl remote = new SimpleRemoteControl();
//        Light light = new Light();
//        GarageDoor door = new GarageDoor();
//        LightOnCommand lightOn = new LightOnCommand(light);
//        GarageDoorOpenCommand doorOpenCommand = new GarageDoorOpenCommand(door);
//        remote.setCommand(lightOn);
//        remote.ButtonWasPressed();
//        remote.setCommand(doorOpenCommand);
//        remote.ButtonWasPressed();



    }
}