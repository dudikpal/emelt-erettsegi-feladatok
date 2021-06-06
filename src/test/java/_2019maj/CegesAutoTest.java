package _2019maj;

import _2019maj.controller.CegesAutoController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CegesAutoTest {

    @Test
    void test() {
        CegesAutoController cegesAutoController = new CegesAutoController();
        String expected = """
                2. feladat
                30. nap rendszám: CEG300
                3. feladat
                Nap: 4
                Forgalom a(z) 4. napon:
                12:50 CEG303 561 ki
                19:17 CEG308 552 be
                4. feladat
                A hónap végén 4 autót nem hoztak vissza.
                5. feladat
                CEG300 6751 km
                CEG301 5441 km
                CEG302 5101 km
                CEG303 7465 km
                CEG304 6564 km
                CEG305 5232 km
                CEG306 7165 km
                CEG307 6489 km
                CEG308 6745 km
                CEG309 1252 km
                6. feladat
                Leghosszabb út: 1551 km, személy: 506
                7. feladat
                Rendszám: CEG304
                Menetlevél kész.""";
        assertEquals(expected, cegesAutoController.solutionTester());
    }

}