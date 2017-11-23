package SortingFastMethod;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int [] arr = {1,2,3,4,5,6,7,8,9};
        char a[] = {'d', 'x', 'a', 'r', 'p', 'j', 'i'};
        int i;
        System.out.print("Исходный массив: ");
        for(i = 0; i < a.length; i++) System.out.print(a[i] + " ");
        System.out.println();
        Quicksort.qsort(a);
        System.out.print("Отсортированный массив: ");
        for(i = 0; i < a.length; i++) System.out.print(a[i] + " ");
        System.out.println();
        char b[] = {'d', 'x', 'a', 'r', 'p', 'j', 'i', 'i', 'i'};
        sorting.sort(b);
        for(char iter : b){
            System.out.println(iter);
        }
        Queue link = new Queue(4);
        link.getArrValue();
        link.setArr(1);
        link.setArr(1);
        link.setArr(1);
        link.setArr(1);
        link.setArr(1);
        System.out.println(link.getArrValue());
        System.out.println(link.getArrValue());
        System.out.println(link.getArrValue());
        System.out.println(link.getArrValue());
        System.out.println(link.getArrValue());
        shift.shifting(arr, 5);
        System.out.println();
        Arrays.stream(arr).forEach(iter -> System.out.println(iter));
    }
}

class Quicksort{
    static void qsort(char items[]){
        qs(items, 0, items.length - 1);
    }
    private static void qs(char items[], int left, int right){
        int i, j;
        char x, y;
        i = left;
        j = right;
        x = items[(left+right)/2];
        System.out.println(x);
        do {
            while ((items[i] < x) && (i < right)) i++;
            while ((x < items[j]) && (j > left)) j--;
            if(i <= j){
                y = items[i];
                items[i] = items[j];
                items[j] = y;
                i++; j--;
            }
        } while(i <= j);
        if(left < j) qs(items, left, j);
        if(i < right) qs(items, i, right);
    }
}

class shift{
    public static int[] shifting(int[] arr, int shift){
        int buff = 0, first;
        shift = shift % arr.length;
        if((shift == arr.length) || (arr.length < 2)) return arr;
        else {
            for (int i = 0; i < shift; i++) {
                first = arr[0];
                for (int j = 1; j < arr.length; j++) {
                    buff = arr[j];
                    arr[j] = first;
                    first = buff;
                }
                arr[0] = buff;
            }
        }
        return arr;
    }
}

class sorting{
    static void sort(char[] arr){
        sort2(arr, 0, arr.length - 1);
    }
    private static void sort2(char[] arr, int left, int right){
        char x, y;
        int i, j;
        i = left;
        j = right;
        x = arr[(left+right)/2];
        do{
            while((arr[i] < x)&&(i < right)) i++;
            while((arr[j] > x)&&(j > left)) j--;
            if(i <= j){
                y = arr[i];
                arr[i] = arr[j];
                arr[j] = y;
                i++;
                j--;
            }
        } while(i <= j);
        if(i < right)sort2(arr, i, right);
        if(j > left)sort2(arr, left, j);
    }
}

class Queue{
    private int arr[];
    private int pointer;
    private int point;

    Queue(int length){
        arr = new int[length];
        point = pointer = 0;
    }

    void setArr(int value){
        if(pointer == arr.length) {
            System.out.println("Очередь израсходована!");
            return;
        }
        arr[pointer++] = value;
    }

    int getArrValue(){
        if(point == pointer) {
            System.out.println("Элементов нет!");
            return 0;
        }
        return arr[point++];
    }

}