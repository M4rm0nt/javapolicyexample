package model;

import java.util.Set;

public class Notiz {
    private final long id;
    private final String text;
    private final Set<Berechtigung> leseBerechtigungen;

    public Notiz(long id, String text, Set<Berechtigung> leseBerechtigungen) {
        this.id = id;
        this.text = text;
        this.leseBerechtigungen = leseBerechtigungen;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Set<Berechtigung> getLeseBerechtigungen() {
        return leseBerechtigungen;
    }

    @Override
    public String toString() {
        return "Notiz: " + text + " (Leseberechtigungen: " + leseBerechtigungen + ")";
    }
}