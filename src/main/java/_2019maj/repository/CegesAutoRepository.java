package _2019maj.repository;

import _2019maj.entity.Mozgas;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class CegesAutoRepository {

    private List<Mozgas> mozgasok = new ArrayList<>();

    public List<Mozgas> adatokFajlbol() {
        try (BufferedReader br = Files.newBufferedReader(Path.of("src/main/resources/_2019maj/autok.txt"))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                String[] time = words[1].split(":");
                boolean kihajtas;
                if (words[5].equals("0")) {
                    kihajtas = true;
                } else {
                    kihajtas = false;
                }
                mozgasok.add(new Mozgas(
                        Integer.parseInt(words[0]),
                        LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1])),
                        words[2],
                        words[3],
                        Integer.parseInt(words[4]),
                        kihajtas));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mozgasok;
    }

    public Mozgas utolsoKihajtoAuto() {
        return mozgasok.stream()
                .filter(Mozgas::isKihajtas)
                .max(Comparator.comparing(Mozgas::getNap).thenComparing(Mozgas::getIdo))
                .get();
    }

    public List<Mozgas> adottNapiKivetEsVisszahozatal(int day) {
        return mozgasok.stream()
                .filter(x -> x.getNap() == 4)
                .collect(Collectors.toList());
    }

    public Map<String, Mozgas> hoVegenNemVoltBent() {
        Map<String, Mozgas> result = new HashMap<>();
        for (Mozgas mozgas : mozgasok) {
            result.put(mozgas.getRendszam(), mozgas);
        }
        return result;
    }

    public Set<String> kmStatisztika() {
        return mozgasok.stream()
                .map(Mozgas::getRendszam)
                .sorted()
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<String> leghosszabbEgyUt() {
        return mozgasok.stream()
                .map(Mozgas::getDolgozoId)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public List<Mozgas> autoStatFajlba(String auto) {
        return mozgasok.stream()
                .filter(x -> x.getRendszam().equals(auto))
                .collect(Collectors.toList());
    }
}
