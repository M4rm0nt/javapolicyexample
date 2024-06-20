package service;

import dao.NotizDAO;
import model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotizService {
    private final NotizDAO notizDAO;

    public NotizService(NotizDAO notizDAO) {
        this.notizDAO = notizDAO;
    }

    public void erstelleNotiz(Benutzer benutzer, Notiz notiz) {
        if (hatBerechtigung(benutzer, Berechtigung.SUPERUSER)) {
            notizDAO.speichern(notiz);
        } else {
            throw new SecurityException("Keine Berechtigung, eine Notiz zu erstellen.");
        }
    }

    public List<Notiz> findeAlleNotizen(Benutzer benutzer) {
        if (hatBerechtigung(benutzer, Berechtigung.SUPERUSER)) {
            return notizDAO.findeAlle();
        } else {
            Set<Berechtigung> berechtigungen = getBerechtigungenFuerBenutzer(benutzer);
            List<Notiz> notizen = new ArrayList<>();
            for (Berechtigung berechtigung : berechtigungen) {
                notizen.addAll(notizDAO.findeNachBerechtigung(berechtigung));
            }
            return new ArrayList<>(new HashSet<>(notizen));
        }
    }

    public Set<Berechtigung> getBerechtigungenFuerBenutzer(Benutzer benutzer) {
        Set<Berechtigung> berechtigungen = new HashSet<>();
        for (Gruppe gruppe : benutzer.getGruppen()) {
            for (Rolle rolle : gruppe.getRollen()) {
                berechtigungen.add(rolle.getBerechtigung());
            }
        }
        return berechtigungen;
    }

    public boolean hatBerechtigung(Benutzer benutzer, Berechtigung berechtigung) {
        return getBerechtigungenFuerBenutzer(benutzer).contains(berechtigung);
    }
}