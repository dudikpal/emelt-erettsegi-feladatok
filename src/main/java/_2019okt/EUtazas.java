/*
2. feladat
A buszra 699 utas akart felszállni.
3. feladat
A buszra 21 utas nem szállhatott fel.
4. feladat
A legtöbb utas (39 fő) a 8. megállóban próbált felszállni.
5. feladat
Ingyenesen utazók száma: 133 fő
A kedvezményesen utazók száma: 200 fő
*/

package _2019okt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EUtazas {

    public static final String LINE_SEPARATOR = " ";
    public static final String JEGGYEL_UTAZOK = "JGY";
    private List<EUtazo> utazok = new ArrayList<>();
    /*private int jeggyelUtazok = 0;
    private int nullaJeggyelUtazok = 0;*/
    private int berlettelKedvezmennyelUtazok = 0;
    /*private int berlettelKedvezmenyNelkulUtazok = 0;*/
    private int ingyenesenUtazok = 0;


    public void beolvas(InputStream is) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(LINE_SEPARATOR);
                int megallo = Integer.parseInt(words[0]);
                String felszallasIdeje = words[1];
                String berletId = words[2];
                String berletTipus = words[3];
                int jegyekszama = Integer.parseInt(words[4]);
                if (!berletTipus.equals(JEGGYEL_UTAZOK)) {
                    utazok.add(new EUtazo(megallo, felszallasIdeje, berletId, berletTipus, words[4]));
                    if (!berletTipus.equals("FEB")) {
                        if (!(berletTipus.equals("TAB") || berletTipus.equals("NYB"))) {
                            ingyenesenUtazok++;
                        } else {
                            berlettelKedvezmennyelUtazok++;
                        }
                    }
                } else {
                    utazok.add(new EUtazo(megallo, felszallasIdeje, berletId, berletTipus, jegyekszama));
                }
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Cannot read file");
        }
    }

    //1. feladat
    public int utasokSzama() {
        return utazok.size();
    }

    //2. feladat
    public int sumErvenytelenUtazok() {
        int counter = 0;
        for (EUtazo utazo: utazok) {
            if (utazo.getBerletTipus() == BerletTipus.JGY && utazo.getJegyekSzama() == 0) {
                counter++;
            } else if (utazo.getBerletTipus() != BerletTipus.JGY && utazo.getErvenyessegiIdo().isBefore(utazo.getFelszallasiIdo().toLocalDate())) {
                counter++;
            }
        }
        return counter;
    }

    //3. feladat (8, és 12-es megállóban 39 utas)
    public int megalloALegtöbbUtassal() {
        int utas = 0;
        int maxUtas = 0;
        int maxMegallo = 0;
        int megallo = 0;
        for (EUtazo utazo: utazok) {
            if (utazo.getMegalloId() == megallo) {
                utas++;
            } else {
                if (utas > maxUtas) {
                    maxUtas = utas;
                    maxMegallo = megallo;
                }
                megallo++;
                utas = 0;
            }
        }
        return maxMegallo;
    }

    //5. feladat
    public String ervenyesKedvezmenyesenEsIngyenesenUtazokSzama() {
        int counter = 0;
        for (EUtazo utazo: utazok) {
            if (utazo.getBerletTipus() != BerletTipus.JGY && utazo.getBerletTipus() != BerletTipus.FEB && utazo.getErvenyessegiIdo().isBefore(utazo.getFelszallasiIdo().toLocalDate())) {
                counter++;
            }
        }
        return "Ingyenesen utazók: " + ingyenesenUtazok + "\n" +
                "Kedvezménnyel utazók: " + (berlettelKedvezmennyelUtazok - counter);
    }

    //7.feladat
    public void figyelmeztetesKuldes() {
        List<EUtazo> figyelmeztetettUtazok = new ArrayList<>();
        for (EUtazo utazo: utazok) {
            if (utazo.getBerletTipus() != BerletTipus.JGY) {
                if (utazo.getFelszallasiIdo().toLocalDate().minusDays(1).isBefore(utazo.getErvenyessegiIdo()) && utazo.getFelszallasiIdo().toLocalDate().plusDays(4).isAfter(utazo.getErvenyessegiIdo())) {
                    figyelmeztetettUtazok.add(utazo);
                }
            }
        }
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of("src/main/resources/2019okt/figyelmeztetes.txt")))){
            for (EUtazo utazo: figyelmeztetettUtazok) {
                writer.print(utazo.getBerletId());
                writer.print(" ");
                writer.println(utazo.getErvenyessegiIdo());
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Cannot write file, ", ioe);
        }
    }

    public static void main(String[] args) {
        EUtazas eUtazas = new EUtazas();
        eUtazas.beolvas(eUtazas.getClass().getClassLoader().getResourceAsStream("2019okt/utasadat.txt"));
        System.out.println("2. feladat");
        System.out.println("A buszra " + eUtazas.utasokSzama() + "utas akart felszállni\n");
        System.out.println("3. feladat");
        System.out.println("A buszra " + eUtazas.sumErvenytelenUtazok() + "utas nem szállhatott fel.\n");
        System.out.println("4. feladat");
        System.out.println("A legtöbb utas a " + eUtazas.megalloALegtöbbUtassal() + ". megállóban nem szállhatott föl.\n");
        System.out.println("5. feladat");
        System.out.println(eUtazas.ervenyesKedvezmenyesenEsIngyenesenUtazokSzama());
        eUtazas.figyelmeztetesKuldes();
    }

    public List<EUtazo> getUtazok() {
        return utazok;
    }

    public int getBerlettelKedvezmennyelUtazok() {
        return berlettelKedvezmennyelUtazok;
    }
}
