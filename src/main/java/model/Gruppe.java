package model;

import java.util.Set;

public class Gruppe {
    private final long id;
    private final String name;
    private final Set<Rolle> rollen;

    public Gruppe(long id, String name, Set<Rolle> rollen) {
        this.id = id;
        this.name = name;
        this.rollen = rollen;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Rolle> getRollen() {
        return rollen;
    }

    @Override
    public String toString() {
        return "Gruppe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rollen=" + rollen +
                '}';
    }
}