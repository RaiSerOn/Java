package WeatherStation;

import java.util.*;

/**
 * Created by maxim on 08.10.17.
 */
public class WeatherData extends Observable{

    private float temperature;
    private float pressure;
    private float humidity;

    public WeatherData(){
    }

    public void measurementsChanged(){
        setChanged();
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getTemperature(){
        return temperature;
    }

    public float getHumidity(){
        return humidity;
    }

    public float getPressure(){
        return pressure;
    }

}
