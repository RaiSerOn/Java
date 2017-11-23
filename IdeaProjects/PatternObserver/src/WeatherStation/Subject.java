package WeatherStation;


/**
 * Created by maxim on 08.10.17.
 */
public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}
