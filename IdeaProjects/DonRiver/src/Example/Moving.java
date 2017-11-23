package Example;

public class Moving {
    public int[] retInt(int steps, int [] arr){
        int first, buffer;
        steps = steps % arr.length;
        System.out.println(steps + "--");
        for(int i = 0; i < steps; i++){
            first = arr[0];
            for(int j = 1; j < arr.length; j++){
                buffer = arr[j];
                arr[j] = first;
                first = buffer;
            }
            arr[0] = first;
        }
        return arr;
    }









    public int[] retInt2(int steps, int [] arr){
        int first, buff;
        steps = steps % arr.length;
        for(int i = 0; i < steps; i ++){
            first = arr[0];
            for(int j = 0; j < arr.length; j++){
                buff = arr[j];
                arr[j] = first;
                first = buff;
            }
            arr[0] = first;
        }
        return arr;
    }

    public int[] revers(int steps, int [] arr){
        int first, buff;
        steps = steps % arr.length;
        for(int i = 0; i < steps; i ++){
            first = arr[arr.length - 1];
            for(int j = arr.length - 2; j >= 0; j--){
                buff = arr[j];
                arr[j] = first;
                first = buff;
            }
            arr[arr.length - 1] = first;
        }
        return arr;
    }
}


