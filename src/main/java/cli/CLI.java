package cli;

import dao.InMemoryNotizDAO;
import dao.NotizDAO;
import model.*;
import service.NotizService;
import util.Logger;

import java.util.*;

public class CLI {
    private final NotizService notizService;
    private final Scanner scanner;
    private Benutzer aktuellerBenutzer;

    public CLI() {
        NotizDAO notizDAO = new InMemoryNotizDAO();
        this.notizService = new NotizService(notizDAO);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        clearScreen();
        Logger.info("Willkommen zum Notizverwaltungssystem");
        login();

        while (true) {
            clearScreen();
            zeigeHauptmenu();
            int auswahl = scanner.nextInt();
            scanner.nextLine();

            switch (auswahl) {
                case 1 -> listeNotizen();
                case 2 -> {
                    if (hatBerechtigung()) erstelleNotiz();
                    else Logger.error("Keine Berechtigung, eine Notiz zu erstellen.");
                }
                case 3 -> login();
                case 4 -> {
                    Logger.info("Auf Wiedersehen!");
                    System.exit(0);
                }
                default -> Logger.error("Ungültige Auswahl. Bitte versuchen Sie es erneut.");
            }
        }
    }

    private void login() {
        Logger.info("Bitte wählen Sie einen Benutzer:");
        Logger.info("1. Admin");
        Logger.info("2. HR");
        Logger.info("3. SCM");

        int auswahl = scanner.nextInt();
        scanner.nextLine();

        Rolle adminRolle = new Rolle(1, "Administrator", Berechtigung.SUPERUSER);
        Rolle hrRolle = new Rolle(2, "HR_User", Berechtigung.HR_USER);
        Rolle scmRolle = new Rolle(3,"SCM_User", Berechtigung.SCM_USER);

        Gruppe adminGruppe = new Gruppe(1, "Admin Gruppe", Set.of(adminRolle));
        Gruppe hrGruppe = new Gruppe(2, "HR Gruppe", Set.of(hrRolle));
        Gruppe scmGruppe = new Gruppe(3, "SCM Gruppe", Set.of(scmRolle));

        switch (auswahl) {
            case 1 -> aktuellerBenutzer = new Benutzer(1, "Admin Benutzer", Set.of(adminGruppe));
            case 2 -> aktuellerBenutzer = new Benutzer(2, "HR Benutzer", Set.of(hrGruppe));
            case 3 -> aktuellerBenutzer = new Benutzer(3, "SCM Benutzer", Set.of(scmGruppe));
            default -> Logger.error("Ungültige Auswahl. Bitte versuchen Sie es erneut.");
        }

        Logger.info("Angemeldet als: " + aktuellerBenutzer.getName());
    }

    private void zeigeHauptmenu() {
        Logger.info("\nHauptmenü:");
        Logger.info("1. Notizen auflisten");
        if (hatBerechtigung()) {
            Logger.info("2. Neue Notiz erstellen");
        }
        Logger.info("3. Benutzer wechseln");
        Logger.info("4. Beenden");
        Logger.info("Bitte wählen Sie eine Option:");
    }

    private void listeNotizen() {
        clearScreen();
        try {
            List<Notiz> notizen = notizService.findeAlleNotizen(aktuellerBenutzer);
            for (Notiz notiz : notizen) {
                Logger.info(notiz.toString());
            }
        } catch (SecurityException e) {
            Logger.error("Keine Berechtigung, Notizen anzuzeigen.");
        }
    }

    private void erstelleNotiz() {
        clearScreen();
        Logger.info("Bitte geben Sie den Text der Notiz ein:");
        String text = scanner.nextLine();

        Logger.info("Für welche Rollen ist die Notiz bestimmt? (Komma-getrennt: SUPERUSER,HR_USER,SCM_USER)");
        String[] rollenString = scanner.nextLine().toUpperCase().split(",");
        Set<Berechtigung> leseBerechtigungen = new HashSet<>();
        for (String rolle : rollenString) {
            leseBerechtigungen.add(Berechtigung.valueOf(rolle.trim()));
        }

        Notiz notiz = new Notiz(0, text, leseBerechtigungen);
        try {
            notizService.erstelleNotiz(aktuellerBenutzer, notiz);
            Logger.info("Notiz erfolgreich erstellt.");
        } catch (SecurityException e) {
            Logger.error("Keine Berechtigung, eine Notiz zu erstellen.");
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private boolean hatBerechtigung() {
        return notizService.hatBerechtigung(aktuellerBenutzer, Berechtigung.SUPERUSER);
    }
}