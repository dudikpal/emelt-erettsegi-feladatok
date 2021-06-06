package _2019maj.entity;

import java.time.LocalTime;

public class Mozgas {

    private int nap;
    private LocalTime ido;
    private String rendszam;
    private String dolgozoId;
    private int kmOra;
    private boolean kihajtas;

    public Mozgas(int nap, LocalTime ido, String rendszam, String dolgozoId, int kmOra, boolean kihajtas) {
        this.nap = nap;
        this.ido = ido;
        this.rendszam = rendszam;
        this.dolgozoId = dolgozoId;
        this.kmOra = kmOra;
        this.kihajtas = kihajtas;
    }

    public int getNap() {
        return nap;
    }

    public LocalTime getIdo() {
        return ido;
    }

    public String getRendszam() {
        return rendszam;
    }

    public String getDolgozoId() {
        return dolgozoId;
    }

    public int getKmOra() {
        return kmOra;
    }

    public boolean isKihajtas() {
        return kihajtas;
    }
}
