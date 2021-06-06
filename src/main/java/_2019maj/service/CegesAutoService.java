package _2019maj.service;

import _2019maj.entity.Mozgas;
import _2019maj.repository.CegesAutoRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CegesAutoService {

    private CegesAutoRepository cegesAutoRepository;
    private List<Mozgas> mozgasok;

    public CegesAutoService(CegesAutoRepository cegesAutoRepository) {
        this.cegesAutoRepository = cegesAutoRepository;
    }

    public void adatokFajlbol() {
        mozgasok = cegesAutoRepository.adatokFajlbol();
    }

    public String utolsoKihajtoAuto() {
        Mozgas utolso = cegesAutoRepository.utolsoKihajtoAuto();
        return String.format("%d. nap rendszám: %s", utolso.getNap(), utolso.getRendszam());
    }

    public String adottNapiKivetEsVisszahozatal(int day) {
        StringBuilder sb = new StringBuilder();
        for (Mozgas mozgas : cegesAutoRepository.adottNapiKivetEsVisszahozatal(day)) {
            sb.append(mozgas.getIdo()).append(" ")
                    .append(mozgas.getRendszam()).append(" ")
                    .append(mozgas.getDolgozoId()).append(" ")
                    .append(mozgas.isKihajtas() ? "ki" : "be").append("\n");
        }
        return sb.toString().trim();
    }

    public int hoVegenNemVoltBent() {
        int counter = 0;
        Map<String, Mozgas> rendszamok = cegesAutoRepository.hoVegenNemVoltBent();
        for (Mozgas mozgas : rendszamok.values()) {
            if (mozgasok.get(mozgasok.lastIndexOf(mozgas)).isKihajtas()) {
                counter++;
            }
        }
        return counter;
    }

    public String kmStatisztika() {
        StringBuilder sb = new StringBuilder();
        for (String rendszam : cegesAutoRepository.kmStatisztika()) {
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < mozgasok.size(); i++) {
                if (mozgasok.get(i).getRendszam().equals(rendszam)) {
                    indexes.add(i);
                }
            }
            int lastIndex = indexes.get(indexes.size() - 1);
            int firstIndex = indexes.get(0);
            int sumKm = mozgasok.get(lastIndex).getKmOra() - mozgasok.get(firstIndex).getKmOra();
            sb.append(rendszam).append(" ")
                    .append(sumKm).append(" km\n");
        }
        return sb.toString().trim();
    }

    public Map.Entry<Integer, String> leghosszabbEgyUt() {
        int maxDistance = 0;
        String dolgozoId = "";
        for (String dolgozo : cegesAutoRepository.leghosszabbEgyUt()) {
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < mozgasok.size(); i++) {
                if (mozgasok.get(i).getDolgozoId().equals(dolgozo)) {
                    indexes.add(i);
                }
            }
            for (int i = 0; i < indexes.size() - 1; i += 2) {
                int actualDistance = mozgasok.get(indexes.get(i + 1)).getKmOra() - mozgasok.get(indexes.get(i)).getKmOra();
                if (maxDistance < actualDistance) {
                    maxDistance = actualDistance;
                    dolgozoId = dolgozo;
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(maxDistance, dolgozoId);
    }

    public void autoStatFajlba(String auto) {
        String file = "src/main/resources/_2019maj/" + auto + "_menetlevel.txt";
        List<Mozgas> autoMozgasa = cegesAutoRepository.autoStatFajlba(auto);
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of(file))){
            for (int i = 0; i <= autoMozgasa.size() - 1; i += 2) {
                bw.write(String.format("%s\t%3d. %s\t%7d km",
                        autoMozgasa.get(i).getDolgozoId(),
                        autoMozgasa.get(i).getNap(),
                        autoMozgasa.get(i).getIdo().format(DateTimeFormatter.ofPattern("HH:mm")),
                        autoMozgasa.get(i).getKmOra()));
                if (i + 1 != autoMozgasa.size()) {
                    bw.write(String.format("\t%s\t%3d. %s\t%7d km",
                            autoMozgasa.get(i + 1).getDolgozoId(),
                            autoMozgasa.get(i + 1).getNap(),
                            autoMozgasa.get(i + 1).getIdo().format(DateTimeFormatter.ofPattern("HH:mm")),
                            autoMozgasa.get(i + 1).getKmOra()));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
