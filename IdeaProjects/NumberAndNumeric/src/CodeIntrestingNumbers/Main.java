package CodeIntrestingNumbers;

public class Main {

    public static void main(String[] args) {
        System.out.println(DigPow.digPow(89,1));
    }
}

class DigPow {

    public static long digPow(int n, int p) {
        String intString = String.valueOf(n);
        long sum = 0;
        for (int i = 0; i < intString.length(); ++i, ++p)
            sum += Math.pow(Character.getNumericValue(intString.charAt(i)), p);
        return (sum % n == 0) ? sum / n : -1;
    }

}
