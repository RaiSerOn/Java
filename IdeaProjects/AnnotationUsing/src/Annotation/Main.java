package Annotation;

public class Main {

    public static void main(String[] args) {
        MyClass myObj = new MyClass("TEST");
        System.out.println(myObj.getMsg());
    }
}

@Deprecated
class MyClass{
    private String msg;
    MyClass(String m){
        msg = m;
    }
    @Deprecated String getMsg(){
        return msg;
    }
}