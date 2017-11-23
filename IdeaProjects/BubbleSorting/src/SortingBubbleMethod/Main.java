package SortingBubbleMethod;

public class Main {

    public static void main(String[] args) {
        int [] arr = {99,10,-2,-9999,20,11111111,1,0,-3,1};
        int buff;

        for(int i = 0; i < 10; i ++)    System.out.println(arr[i]);
        System.out.println("\n");
        for(int i = 0; i < arr.length - 1; i ++){
            for(int j = arr.length - 1; j > i; j --){
                if(arr[j - 1] > arr[j]){
                    buff = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = buff;
                }
            }
        }
        for(int i = 0; i < 10; i ++)    System.out.println(arr[i]);

        for(int i = 0; i < arr.length - 1; i++){
            for(int j = arr.length - 1; j > i; j--){
                if(arr[j - 1] > arr[j]){
                    buff = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = buff;
                }
            }
        }
    }
}
