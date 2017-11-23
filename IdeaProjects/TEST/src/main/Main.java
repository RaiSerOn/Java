package main;

import java.util.ArrayList;
import  java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Integer [] myIntArray = {1, 2 ,3 ,4 ,5 ,6 ,7 ,8};
        List<Integer> myList = Arrays.asList(myIntArray);
        ArrayList <Integer> myArray = new ArrayList<>(myList);
//        myList.addAll(myArray);
        myList.stream().peek(iterator -> System.out.println(iterator)).count();

        int [] arr = {1, 2 ,3 ,4 ,5 ,6 ,7 ,8 ,9};
        Expression exp = Exp::returnEvn;
        Exp exp2 = new Exp();
        Expression exp3 = exp2::returnMax;
        System.out.println(sum(exp, arr));
        System.out.println(sum(exp3, arr));
        User user4 = new User("Hello");
        UserBuilder user = User::new;
        //System.out.println(user.getName());
        User user1 = user.create("Hello");
        System.out.println(user1.getName());
        char [] arr2 = {'c', 'a', 'x', 'm', 'r'};
        Sorting.start(arr2);
        System.out.println();
        for(char iterator : arr2){
            System.out.println(iterator);
        }
    }

    private static int sum(Expression exp, int [] mass){
        int sum = 0;
        for(int iterator : mass){
            if(exp.regExp(iterator)){
                sum += iterator;
            }
        }
        return sum;
    }
}

interface Expression{
    boolean regExp(int h);
}

class Exp{
    static boolean returnEvn(int h){
        return h % 2 == 0 ? true : false;
    }

    boolean returnMax(int h){
        return h > 0 ? true : false;
    }
}



interface UserBuilder{
    User create(String name);
}

class User{

    private String name;
    String getName(){
        return name;
    }

    User(String n){
        this.name=n;
    }

    static User create(String name){
        User user = new User(name);
        return user;
    }
}

class Sorting{
    public static void start(char[] arr){sort(arr, 0, arr.length - 1);}
    private static void sort(char[] arr, int left, int right){
        char x, y;
        int i = left, j = right;
        x = arr[(left + right) / 2];
        do{

        while ((x > arr[i]) && (i < right)) i++;
        while ((x < arr[j]) && (j > left)) j--;
            if(i <= j){
                y = arr[i];
                arr[i] = arr[j];
                arr[j] = y;
                i++; j--;
            }
        }while (i <= j);
        if(left < j) sort(arr, left, j);
        if(right > i) sort(arr, i, right);
    }
}