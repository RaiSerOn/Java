package Example;

public class Main {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        int[] arr2 = {1, 2, 3, 4, 5, 6};
        int[] arr3 = {1, 2, 3, 4, 5, 6};
        Moving mov = new Moving();
        for(int i = 0; i < arr.length; i ++){
            System.out.println(arr[i]);
        }
        System.out.println();
        mov.retInt( 2, arr);
        for(int i = 0; i < arr2.length; i ++){
            System.out.println(arr2[i]);
        }
        System.out.println();
        Moving mov1 = new Moving();
        for(int i = 0; i < arr.length; i ++){
            System.out.println(arr[i]);
        }

        System.out.println();
        mov1.retInt2( 12, arr2);
        for(int i = 0; i < arr2.length; i ++){
            System.out.println(arr2[i]);
        }
        System.out.println();

        for(int i = 0; i < arr3.length; i ++){
            System.out.println(arr3[i]);
        }

        System.out.println();
        mov1.revers( 3, arr3);
        for(int i = 0; i < arr3.length; i ++){
            System.out.println(arr3[i]);
        }
    }
}
