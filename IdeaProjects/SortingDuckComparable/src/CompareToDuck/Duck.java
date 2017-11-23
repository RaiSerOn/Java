package CompareToDuck;

public class Duck implements Comparable {
    String name;
    int weight;
    public Duck(String name, int weight){
        this.name = name;
        this.weight = weight;
    }
    public String toString(){
        return name + " weight " + weight;
    }

    public int compareTo(Object object){
        Duck otherDuck = (Duck) object;
        if(this.weight < otherDuck.weight){
            return -1;
        } else if(this.weight == otherDuck.weight){
            return 0;
        } else if(this.weight > otherDuck.weight){
            return 1;
        }
        return -2;
    }
}
