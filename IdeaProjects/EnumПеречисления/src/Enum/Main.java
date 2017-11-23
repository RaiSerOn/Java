package Enum;

public class Main{

    public static void main(String[] args) {
        Transport tp2, tp3;
        Transport tp = Transport.AIRPLANE;
        System.out.println("Значение tp: " + tp);
        System.out.println();
        tp = Transport.TRAIN;
        if(tp == Transport.TRAIN) System.out.println("tp содержит TRAIN\n");
        switch (tp){
            case CAR:
                System.out.println("Автомобиль перевозит людей");
                break;
            case TRUCK:
                System.out.println("Грузовик перевозит груз");
                break;
            case AIRPLANE:
                System.out.println("Самолёт летит");
                break;
            case TRAIN:
                System.out.println("Поезд движется по рельсам");
                break;
            case BOAT:
                System.out.println("Лодка плывёт по воде");
                break;
        }
        System.out.println("\nКонстанты Transport:");
        Transport allTransport[] = Transport.values();
        for(Transport t : allTransport){
            System.out.println(t);
        }
        System.out.println();
        tp = Transport.valueOf("AIRPLANE");
        System.out.println("tp содержит " + tp + "\n");
        System.out.println("Типичная скорость самолёта - " + Transport.AIRPLANE.getSpeed() + " миль в час\n");
        System.out.println("Типичные скорости движения транспортных средств");
        for(Transport t : Transport.values()){
            System.out.println(t + ": " + t.getSpeed() + " миль в час");
        }
        System.out.println("\nКонстанты перечисления Transport и их порядковые значения:");
        for(Transport t : Transport.values()){
            System.out.println(t + " " + t.ordinal());
        }
        tp = Transport.AIRPLANE;
        tp2 = Transport.TRAIN;
        tp3 = Transport.AIRPLANE;
        System.out.println();
        if (tp.compareTo(tp2) < 0) System.out.println(tp + " идёт перед " + tp2);
        if (tp.compareTo(tp2) > 0) System.out.println(tp + " идёт после " + tp2);
        if (tp.compareTo(tp3) == 0) System.out.println(tp + " совпадает с " + tp3);
    }
}

enum Transport{
    CAR(65), TRUCK(55), AIRPLANE(600), TRAIN(70), BOAT(22);
    private int speed;
    Transport(int s){
        speed = s;
    }
    int getSpeed(){
        return speed;
    }
}