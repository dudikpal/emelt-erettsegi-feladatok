package _2020okt;

public class Episode {
    private String premierDate;
    private String name;
    private int season;
    private String episode;
    private int length;
    private boolean watched;
    
    public Episode(String premierDate, String name, int season, String episode, int length, boolean watched) {
        this.premierDate = premierDate;
        this.name = name;
        this.season = season;
        this.episode = episode;
        this.length = length;
        this.watched = watched;
    }
    
    @Override
    public String toString() {
        return premierDate + "\n" +
            name + "\n" +
            season + "x" + episode + "\n" +
            length + "\n" +
            (watched ? "1" : "0") + "\n";
    }
    
    public String getPremierDate() {
        return premierDate;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSeason() {
        return season;
    }
    
    public String getEpisode() {
        return episode;
    }
    
    public int getLength() {
        return length;
    }
    
    public boolean isWatched() {
        return watched;
    }
}
