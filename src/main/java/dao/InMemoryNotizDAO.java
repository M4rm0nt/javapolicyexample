package dao;

import model.Notiz;
import model.Berechtigung;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryNotizDAO implements NotizDAO {
    private final List<Notiz> notizen = new ArrayList<>();
    private long idCounter = 1;

    @Override
    public void speichern(Notiz notiz) {
        notizen.add(new Notiz(idCounter++, notiz.getText(), notiz.getLeseBerechtigungen()));
    }

    @Override
    public List<Notiz> findeAlle() {
        return new ArrayList<>(notizen);
    }

    @Override
    public List<Notiz> findeNachBerechtigung(Berechtigung berechtigung) {
        List<Notiz> result = new ArrayList<>();
        for (Notiz notiz : notizen) {
            if (notiz.getLeseBerechtigungen().contains(berechtigung)) {
                result.add(notiz);
            }
        }
        return result;
    }
}