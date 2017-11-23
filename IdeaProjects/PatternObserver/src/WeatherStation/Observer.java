package WeatherStation;

/**
 * Created by maxim on 08.10.17.
 */
public interface Observer {
    public void update(float temp, float humidity, float pressure);
}
