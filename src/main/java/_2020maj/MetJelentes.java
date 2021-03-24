package _2020maj;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MetJelentes {
    
    private List<City> cities = new ArrayList<>();
    private List<Telegram> telegrams = new ArrayList<>();
    
    // 2. feladat
    public String getCityLastMeasureTime(String cityName) {
    
        for (City city : cities) {
            if (cityName.equals(city.getCityName())) {
                return city.getLastMeasureTime();
            }
        }
        return "2. feladat  \n" +
                   "Az adott város nincs a listában!\n";
    }
    
    
    private City cityFindByName(String name) {
        for (City city : cities) {
            if (city.getCityName().equals(name)) {
                return city;
            }
        }
        return null;
    }
    
    
    public void readFromFile() {
        InputStream is = this.getClass().getResourceAsStream("tavirathu13.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String cityName = parts[0];
                Telegram telegram = createTelegram(parts);
                addTelegramToNewOrExistCity(cityName, telegram);
                telegrams.add(telegram);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    
    private int citiesContainsCity(String name) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getCityName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
    
    
    private void addTelegramToNewOrExistCity(String cityName, Telegram telegram) {
        int index;
        if ((index = citiesContainsCity(cityName)) != -1) {
            cities.get(index).addTelegram(telegram);
        } else {
            City city = new City(cityName);
            city.addTelegram(telegram);
            cities.add(city);
        }
    }
    
    private Telegram createTelegram(String[] parts) {
        String cityName = parts[0];
        LocalTime time = LocalTime.of(Integer.parseInt(parts[1].substring(0, 2)), Integer.parseInt(parts[1].substring(2)));
        String wind = parts[2];
        int degree = Integer.parseInt(parts[3]);
        return new Telegram(cityName, time, wind, degree);
    }
    
    
    // 3. feladat
    public String minMaxMeasure() {
        int minTemp = Integer.MAX_VALUE;
        int maxTemp = 0;
        String minCityName = "", maxCityName = "";
        LocalTime minCityTime = null, maxCityTime = null;
        for (Telegram telegram : telegrams) {
            if (minTemp > telegram.getTemperature()) {
                minTemp = telegram.getTemperature();
                minCityName = telegram.getCityName();
                minCityTime = telegram.getTime();
            }
            if (maxTemp < telegram.getTemperature()) {
                maxTemp = telegram.getTemperature();
                maxCityName = telegram.getCityName();
                maxCityTime = telegram.getTime();
            }
        }
        String minMeasurement = minCityName + " " + minCityTime + " " + minTemp;
        String maxMeasurement = maxCityName + " " + maxCityTime + " " + maxTemp;
        return "3. feladat  \n" +
                   "A legalacsonyabb hőmérséklet: " +
                     minMeasurement + " fok. A legmagasabb hőmérséklet: " +
                    maxMeasurement + " fok.\n";
    }
    
    
    // 4. feladat
    public String zeroWind() {
        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (Telegram telegram : telegrams) {
            if (telegram.getWind().equals("00000")) {
                sb.append(separator);
                sb.append(telegram.getCityName() + " " + telegram.getTime());
                separator = " ";
            }
        }
        if (sb.length() == 0) {
            return "Nem volt szélcsend a mérések idején.";
        }
        return sb.toString() + "\n";
    }
    
    
    // 5. feladat
    public String temperatureAverageAndFluctuation() {
        StringBuilder sb = new StringBuilder();
        for (City city : cities) {
            sb.append(city.avgTemp() + city.tempFluctuation());
        }
        return sb.toString();
    }
    
    
    // 6. feladat
    public String createWindFilesByCities() {
        for (City city : cities) {
            String file = "src/main/resources/_2020maj/" + city.getCityName() + ".txt";
            
            try (BufferedWriter bw = Files.newBufferedWriter(Path.of(file))){
                bw.write(city.getCityName() + "\n");
                bw.write(city.windReport());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return "Fájlok kiírva.";
    }
    
    
    public static void main(String[] args) {
        MetJelentes met = new MetJelentes();
        met.readFromFile();
        System.out.println(met.getCityLastMeasureTime("SM"));
        System.out.println(met.minMaxMeasure());
        System.out.println(met.zeroWind());
        System.out.println(met.temperatureAverageAndFluctuation());
        System.out.println(met.createWindFilesByCities());
    }
    
}
