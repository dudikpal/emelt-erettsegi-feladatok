package _2020okt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sorozatok {
    
    private List<Episode> episodes = new ArrayList<>();
    
    // 1. feladat
    public void readFileToList() {
        InputStream is = this.getClass().getResourceAsStream("lista.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String[] episodeStringArray = new String[5];
            
            while ((episodeStringArray[0] = br.readLine()) != null) {
                
                for (int i = 1; i < 5; i++) {
                    episodeStringArray[i] = br.readLine();
                }
                episodes.add(createEpisode(episodeStringArray));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    // 2. feladat
    private Episode createEpisode(String[] datas) {
        String premierDate = datas[0];
        String name = datas[1];
        int serie = Integer.parseInt(datas[2].split("x")[0]);
        String episode = datas[2].split("x")[1];
        int length = Integer.parseInt(datas[3]);
        boolean watched = datas[4].equals("1") ? true : false;
        return new Episode(premierDate, name, serie, episode, length, watched);
    }
    
    
    public int episodesNumberWhereKnownPremierDate() {
        int counter = 0;
        
        for (Episode episode : episodes) {
            if (!episode.getPremierDate().equals("NI")) {
                counter++;
            }
        }
        return counter;
    }
    
    // 3. feladat
    public String watchedRate() {
        double allEpisodes = episodes.size() * 1.0;
        double watchedEpisodes = 0.0;
        
        for (Episode episode : episodes) {
            if (episode.isWatched()) {
                watchedEpisodes += 1.0;
            }
        }
        
        return String.format("%.2f", watchedEpisodes / allEpisodes * 100);
    }
    
    // 4. feladat
    public String watchedTimeSum() {
        int sumLengthInMinutes = 0;
        
        for (Episode episode : episodes) {
            if (episode.isWatched()) {
                sumLengthInMinutes += episode.getLength();
            }
        }
        return "4. feladat Sorozatnézéssel " +
                   sumLengthInMinutes / 1440 + " napot " +
                   sumLengthInMinutes / 60 % 24 + " órát és " +
                   sumLengthInMinutes % 60 + " percet töltött. ";
    }
    
    // 5. feladat
    public String unwatchedTillDate() {
        LocalDate tillDate = LocalDate.of(2017, 10, 18);
        StringBuilder sb = new StringBuilder();
        sb.append("5. feladat Adjon meg egy dátumot! Dátum= 2017.10.18\n");
        String SEPARATOR = "";
        
        for (Episode episode : episodes) {
            if (!episode.getPremierDate().equals("NI")) {
                LocalDate episodeDate = createLocalDate(episode.getPremierDate());
                if (!episodeDate.isAfter(tillDate) && !episode.isWatched()) {
                    sb.append(SEPARATOR);
                    sb.append(episode.getSeason() + "x" + episode.getEpisode() + "\t" + episode.getName());
                    SEPARATOR = "\n";
                }
            }
        }
        return sb.toString();
    }
    
    
    private LocalDate createLocalDate(String date) {
        String[] dateParts = date.split("\\.");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        return LocalDate.of(year, month, day);
    }
    
    
    // 6. feladat
    public String dayName(int year, int month, int day) {
        String[] days = {"v", "h", "k", "sze", "cs", "p", "szo"};
        int[] months = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        if (month < 3) {
            year -= 1;
        }
        String dayName = days[(year + year / 4 - year / 100 + year / 400 + months[month - 1] + day) % 7];
        return dayName;
    }
    
    
    // 7. feladat
    public String episodesOnThisDay(String nameOfDay) {
        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (Episode episode : episodes) {
            if (!episode.getPremierDate().equals("NI") && !sb.toString().contains(episode.getName())) {
                LocalDate episodeDate = createLocalDate(episode.getPremierDate());
                int year = episodeDate.getYear();
                int month = episodeDate.getMonthValue();
                int day = episodeDate.getDayOfMonth();
                if (dayName(year, month, day).equals(nameOfDay)) {
                    sb.append(separator);
                    sb.append(episode.getName());
                    separator = "\n";
                }
            }
        }
        return sb.toString();
    }
    
    
    // 8. feladat
    public void sumTimeToFile() {
        String actualName = episodes.get(0).getName();
        int sumTime = 0;
        int episodesSum = 0;
        StringBuilder sb = new StringBuilder();
        for (Episode episode : episodes) {
            if (!actualName.equals(episode.getName())) {
                sb.append(actualName).append("\t" + sumTime).append("\t" + episodesSum + "\n");
                actualName = episode.getName();
                sumTime = 0;
                episodesSum = 0;
            }
            sumTime += episode.getLength();
            episodesSum++;
        }
        writeToFile(sb.toString());
    }
    
    private void writeToFile(String result) {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Path.of("src/main/resources/_2020okt/summa.txt")))){
            pw.write(result);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        Sorozatok sorozatok = new Sorozatok();
        sorozatok.readFileToList();
        System.out.println("2. feladat A listában " + sorozatok.episodesNumberWhereKnownPremierDate() +" db vetítési dátummal rendelkező epizód van.\n");
        System.out.println("3. feladat A listában lévő epizódok " + sorozatok.watchedRate() + "%-át látta.\n");
        System.out.println(sorozatok.watchedTimeSum() + "\n");
        System.out.println(sorozatok.unwatchedTillDate() + "\n");
        System.out.println("7. feladat Adja meg a hét egy napját (például cs)! Nap= cs\n\n" + sorozatok.episodesOnThisDay("cs"));
        System.out.println("8. feladat sikeresen kiírva a summa.txt fájlba");
        sorozatok.sumTimeToFile();
    }
}
