package FactorialRecursiveMethod;

public class Main {

    public static void main(String[] args) {
        Recursive rec = new Recursive();
        System.out.println(rec.factorial(4));
    }
}

class Recursive{
    public int factorial(int num){
        if(num == 1) return 1;
        return factorial(num - 1) * num;
    }
}
