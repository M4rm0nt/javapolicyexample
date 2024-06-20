package dao;

import model.Notiz;
import model.Berechtigung;

import java.util.List;

public interface NotizDAO {
    void speichern(Notiz notiz);
    List<Notiz> findeAlle();
    List<Notiz> findeNachBerechtigung(Berechtigung berechtigung);
}