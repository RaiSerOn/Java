package Auto;

public class Main {
    static void m(Integer v){
        System.out.println("m() получил " + v);
    }
    static int m2(){
        return 10;
    }
    static Integer m3(){
        return 99;
    }
    public static void main(String[] args) {
        m(199);
        Integer iOb2, iOb3, iOb = m2();
        System.out.println("Значение, возвращаемое из m2(): " + iOb);
        int i = m3();
        System.out.println("Значение, возвращённое из m3(): " + i);
        iOb = 100;
        System.out.println("Корень квадратный из iOb: " + Math.sqrt(iOb));
        iOb2 = 99;
        System.out.println("Исходное значение iOb2: " + iOb2);
        ++iOb2;
        System.out.println("После ++iOb2: " + iOb2);
        iOb2 += 10;
        System.out.println("После iOb2 += 10: " + iOb2);
        iOb3 = iOb2 + (iOb2/3);
        System.out.println("iOb3 после после вычисления выражения: " + iOb3);
        i = iOb2 + (iOb2/3);
        System.out.println("i после после вычисления выражения: " + i);
    }
}