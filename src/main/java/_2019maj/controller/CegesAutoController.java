package _2019maj.controller;

import _2019maj.repository.CegesAutoRepository;
import _2019maj.service.CegesAutoService;

import java.util.Scanner;

public class CegesAutoController {

    private CegesAutoService cegesAutoService = new CegesAutoService(new CegesAutoRepository());

    public String solutionTester() {
        adatokFajlbol();
        StringBuilder sb = new StringBuilder();
        sb.append(utolsoKihajtoAuto());
        sb.append(adottNapiKivetEsVisszahozatal(4));
        sb.append(hoVegenNemVoltBent());
        sb.append(kmStatisztika());
        sb.append(leghosszabbEgyUt());
        sb.append(autoStatFajlba("CEG304"));
        System.out.println(sb.toString());
        return sb.toString();
    }

    private String autoStatFajlba(String auto) {
        cegesAutoService.autoStatFajlba(auto);
        return String.format("7. feladat\nRendszám: %s\nMenetlevél kész.", auto);
    }

    private String leghosszabbEgyUt() {
        int maxKm = cegesAutoService.leghosszabbEgyUt().getKey();
        String dolgozo = cegesAutoService.leghosszabbEgyUt().getValue();
        return String.format("6. feladat\nLeghosszabb út: %d km, személy: %s\n", maxKm, dolgozo);
    }

    private String kmStatisztika() {
        return String.format("5. feladat\n%s\n", cegesAutoService.kmStatisztika());
    }

    private String hoVegenNemVoltBent() {
        return String.format("4. feladat\nA hónap végén %d autót nem hoztak vissza.\n", cegesAutoService.hoVegenNemVoltBent());
    }

    public void adatokFajlbol() {
        cegesAutoService.adatokFajlbol();
    }

    public String utolsoKihajtoAuto() {
        return String.format("2. feladat\n%s\n", cegesAutoService.utolsoKihajtoAuto());
    }

    public String adottNapiKivetEsVisszahozatal(int day) {
        return String.format("3. feladat\nNap: %d\nForgalom a(z) %d. napon:\n%s\n", day, day, cegesAutoService.adottNapiKivetEsVisszahozatal(day));
    }

}
