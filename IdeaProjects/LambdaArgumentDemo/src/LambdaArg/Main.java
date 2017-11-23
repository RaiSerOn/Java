package LambdaArg;

public class Main {

    public static void main(String[] args) {
        String inStr = "Лямбда-выражения расширяют Java";
        String outStr;
        System.out.println("Входная страка : " + inStr);
        StringFunc reverse = (str) -> {
            String result = "";
            for(int i = str.length() - 1; i >= 0; i--){
                result += str.charAt(i);
            }
            return result;
        };
        outStr = LambdaArgumentDemo.changeStr(reverse, inStr);
        System.out.println("Обращённая строка: " + outStr);
        outStr = LambdaArgumentDemo.changeStr((str) ->{
                String result = "";
                char ch;
                for(int i = 0; i < str.length(); i ++){
                    ch = str.charAt(i);
                    if(Character.isUpperCase(ch)) result += Character.toLowerCase(ch);
                    else result += Character.toUpperCase(ch);
                }
                return  result;
        }, inStr);
        System.out.println("Строка с обращённым регистром букв: " + outStr);
        outStr = LambdaArgumentDemo.changeStr((str) -> str.replace(" ", "-"), inStr);
        System.out.println("Строка с заменёнными символами: " + outStr);
    }
}

interface StringFunc{
    String func(String str);
}

class LambdaArgumentDemo{
    static String changeStr(StringFunc sf, String s){
        return sf.func(s);
    }
}