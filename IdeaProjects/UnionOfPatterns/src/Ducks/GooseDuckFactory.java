package Ducks;

public class GooseDuckFactory extends AbstractGooseFactory {

    public Quackable createGoose(){
        return new GooseAdapter(new Goose());
    }

}