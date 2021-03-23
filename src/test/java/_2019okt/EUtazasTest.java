package _2019okt;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class EUtazasTest {

    @Test
    void testCreate() {
        EUtazas eUtazas = new EUtazas();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("_2019okt/utasadat.txt");
        eUtazas.beolvas(is);

        assertEquals(699, eUtazas.getUtazok().size());
    }

}