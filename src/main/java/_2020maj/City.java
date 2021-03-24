package _2020maj;

import java.time.LocalTime;
import java.util.*;

public class City {
    
    private List<Telegram> telegrams = new ArrayList<>();
    private String cityName;
    
    public City(String cityName) {
        this.cityName = cityName;
    }
    
    
    public String windReport() {
        Map<LocalTime, String> windMap = new TreeMap<>();
        for (Telegram telegram : telegrams) {
            int windStrength = Integer.parseInt(telegram.getWind().substring(3));
            String winsString = "#".repeat(windStrength) + "\n";
            windMap.put(telegram.getTime(), winsString);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : windMap.entrySet()) {
            sb.append(entry.getKey() + " " + entry.getValue());
        }
        return sb.toString();
    }
    
    
    public String tempFluctuation() {
        int minTemp = Integer.MAX_VALUE;
        int maxTemp = 0;
        for (Telegram telegram : telegrams) {
            if (minTemp > telegram.getTemperature()) {
                minTemp = telegram.getTemperature();
            }
            if (maxTemp < telegram.getTemperature()) {
                maxTemp = telegram.getTemperature();
            }
        }
        return "Hőmérséklet ingadozás: " + (maxTemp - minTemp) + "\n";
    }
    
    
    public String avgTemp() {
        int counter = 0;
        int sumTemp = 0;
        Set<Integer> controlHours = new HashSet<>();
        for (int i = 1; i < 24; i += 6) {
            LocalTime controlHour = LocalTime.of(i, 0);
            for (Telegram telegram : telegrams) {
                if (controlHour.getHour() == telegram.getTime().getHour()) {
                    sumTemp += telegram.getTemperature();
                    counter++;
                    controlHours.add(i);
                }
            }
        }
        if (controlHours.size() < 4) {
            return cityName + " NA; ";
        }
        return cityName + " Középhőmérséklet: " + sumTemp / counter + "; ";
    }
    
    
    public String getLastMeasureTime() {
        LocalTime getLastMeasureTime = telegrams.get(0).getTime();
        for (Telegram telegram : telegrams) {
            if (telegram.getTime().isAfter(getLastMeasureTime)) {
                getLastMeasureTime = telegram.getTime();
            }
        }
        return "2. feladat  \n" +
                   "Adja meg egy település kódját! Település: " + cityName +
                   "\n Az utolsó mérési adat a megadott településről " +
                   getLastMeasureTime + "-kor érkezett.\n";
    }
    
    
    public void addTelegram(Telegram telegram) {
        telegrams.add(telegram);
    }
    
    public List<Telegram> getTelegrams() {
        return telegrams;
    }
    
    public String getCityName() {
        return cityName;
    }
}
