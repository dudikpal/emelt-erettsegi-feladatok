package _2019okt;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EUtazoTest {

    @Test
    void testCreateUtazoBerlettel() {
        EUtazo eUtazo = new EUtazo(0, "20190326-0700", "6654545", "FEB", "20210101");

        //System.out.println(eUtazo);
        assertEquals(0, eUtazo.getMegalloId());
        assertEquals(LocalDateTime.of(2019, 03, 26, 07, 00), eUtazo.getFelszallasiIdo());
        assertEquals("6654545", eUtazo.getBerletId());
        assertEquals(BerletTipus.FEB, eUtazo.getBerletTipus());
        assertEquals(LocalDate.of(2021, 01, 01), eUtazo.getErvenyessegiIdo());
    }

    @Test
    void testCreateUtazoJeggyel() {
        EUtazo eUtazo2 = new EUtazo(0, "20190326-0700", "6654545", "JGY", 7);

        //System.out.println(eUtazo2);
        assertEquals(0, eUtazo2.getMegalloId());
        assertEquals(LocalDateTime.of(2019, 03, 26, 07, 00), eUtazo2.getFelszallasiIdo());
        assertEquals("6654545", eUtazo2.getBerletId());
        assertEquals(BerletTipus.JGY, eUtazo2.getBerletTipus());
        assertEquals(7, eUtazo2.getJegyekSzama());
    }

}