package Abstract;

public class Main {

    public static void main(String[] args) {
        TwoDShape shapes[] = new TwoDShape[3];
        shapes[0] = new Triangle("контурный", 8.0, 12.0);
        shapes[1] = new Triangle(7.0);
        shapes[2] = new Triangle((Triangle) shapes[0]);
        for(int i = 0; i < shapes.length; i ++){
            System.out.println("Объект - " + shapes[i].getName());
            System.out.println("Площадь - " + shapes[i].area());
            System.out.println();
        }
    }
}

abstract class TwoDShape{
    private double width;
    private double height;
    private String name;
    TwoDShape(){
        width = height = 0.0;
        name = "none";
    }
    TwoDShape(double w, double h, String n){
        width = w;
        height = h;
        name = n;
    }
    TwoDShape(double x, String n){
        width = height = x;
        name = n;
    }
    TwoDShape(TwoDShape ob){
        width = ob.width;
        height = ob.height;
        name = ob.name;
    }
    double getWidth(){
        return width;
    }
    double getHeight(){
        return width;
    }
    void setWidth(double w){
        width = w;
    }
    void setHeight(double h){
        height = h;
    }
    String getName(){
        return name;
    }
    void showDim(){
        System.out.println("Ширина и высота - " + width + " и " + height);
    }
    abstract double area();
}

class Triangle extends TwoDShape{
    private String style;
    Triangle(){
        super();
        style = "none";
    }
    Triangle(String s, double w, double h){
        super(w, h, "треугольник");
        style = s;
    }
    Triangle(double x){
        super(x, "треугольник");
        style = "закрашенный";
    }
    Triangle(Triangle ob){
        super(ob);
        style = ob.style;
    }
    double area(){
        return getWidth() * getHeight() / 2;
    }
    void showStyle(){
        System.out.println("Треугольник " + style);
    }
}