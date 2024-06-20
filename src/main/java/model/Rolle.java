package model;

public class Rolle {
    private final long id;
    private final String name;
    private final Berechtigung berechtigung;

    public Rolle(long id, String name, Berechtigung berechtigung) {
        this.id = id;
        this.name = name;
        this.berechtigung = berechtigung;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Berechtigung getBerechtigung() {
        return berechtigung;
    }

    @Override
    public String toString() {
        return "Rolle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", berechtigung=" + berechtigung +
                '}';
    }
}