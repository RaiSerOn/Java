package Finale;

public class Main {

    public static void main(String[] args) {
        ErrorMsg err = new ErrorMsg();
        System.out.println(err.getErrorMag(err.OUTER));
        System.out.println(err.getErrorMag(err.DISKERR));
    }
}

class ErrorMsg{
    final int OUTER = 0;
    final int INNER = 1;
    final int DISKERR = 2;
    final int INDEXERR = 3;

    String msgs[] = {
            "Ошибка вывода",
            "Ошибка ввода",
            "Отсутствует место на диске",
            "Выход индекса за границы диапазона"
    };
    String getErrorMag(int i){
        if(i >= 0 & i < msgs.length) return msgs[i];
        else return "Несуществующий код ошибки";
    }
}