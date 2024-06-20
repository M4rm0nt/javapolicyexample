package model;

import java.util.Set;

public class Benutzer {
    private final long id;
    private final String name;
    private final Set<Gruppe> gruppen;

    public Benutzer(long id, String name, Set<Gruppe> gruppen) {
        this.id = id;
        this.name = name;
        this.gruppen = gruppen;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Gruppe> getGruppen() {
        return gruppen;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gruppen=" + gruppen +
                '}';
    }
}