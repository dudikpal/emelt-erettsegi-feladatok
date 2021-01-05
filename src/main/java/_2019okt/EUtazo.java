package _2019okt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EUtazo {
    private int megalloId;
    private LocalDateTime felszallasiIdo;
    private String berletId;
    private BerletTipus berletTipus;
    private LocalDate ervenyessegiIdo;
    private int jegyekSzama;

    public EUtazo(int megalloId, String felszallasiIdo, String berletId, String berletTipus, String ervenyessegiIdo) {
        this.megalloId = megalloId;
        this.felszallasiIdo = LocalDateTime.parse(felszallasiIdo, DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));
        this.berletId = berletId;
        this.berletTipus = BerletTipus.valueOf(berletTipus);
        this.ervenyessegiIdo = LocalDate.parse(ervenyessegiIdo, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public EUtazo(int megalloId, String felszallasiIdo, String berletId, String berletTipus, int jegyekSzama) {
        this.megalloId = megalloId;
        this.felszallasiIdo = LocalDateTime.parse(felszallasiIdo, DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));;
        this.berletId = berletId;
        this.berletTipus = BerletTipus.valueOf(berletTipus);
        this.jegyekSzama = jegyekSzama;
    }

    public int getMegalloId() {
        return megalloId;
    }

    public LocalDateTime getFelszallasiIdo() {
        return felszallasiIdo;
    }

    public String getBerletId() {
        return berletId;
    }

    public BerletTipus getBerletTipus() {
        return berletTipus;
    }

    public LocalDate getErvenyessegiIdo() {
        return ervenyessegiIdo;
    }

    public int getJegyekSzama() {
        return jegyekSzama;
    }
}
