package _2020maj;

import java.time.LocalTime;

public class Telegram {
    
    private String cityName;
    private LocalTime time;
    private String wind;
    private int temperature;
    
    public Telegram(String cityName, LocalTime time, String wind, int temperature) {
        this.cityName = cityName;
        this.time = time;
        this.wind = wind;
        this.temperature = temperature;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public LocalTime getTime() {
        return time;
    }
    
    public String getWind() {
        return wind;
    }
    
    public int getTemperature() {
        return temperature;
    }
}
