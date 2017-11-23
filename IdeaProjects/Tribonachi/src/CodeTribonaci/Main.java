package CodeTribonaci;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Xbonacci variabonacci = new Xbonacci();
        double precision = 1e-10;
        assertArrayEquals(new double []{1,1,1,3,5,9,17,31,57,105}, variabonacci.tribonacci(new double []{1,1,1},10), precision);
    }

    private static void assertArrayEquals(double[] doubles, double[] tribonacci, double precision) {
    }

}



class Xbonacci {
    public double[] tribonacci(double[] s, int n) {

        double[] tritab = Arrays.copyOf(s, n);
        for(int i=3;i<n;i++){
            tritab[i]=tritab[i-1]+tritab[i-2]+tritab[i-3];
        }
        return tritab;

    }
}
