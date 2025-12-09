public abstract class Zwierzeta {
    int wiek;
    double waga; // Waga może być ułamkowa (np. 0.5 kg)
    String gatunek;
    int index;
    int portfel; // Poprawiona nazwa

    // Enum wielkości
    public enum Wielkosc {
        maly, sredni, duzy, ogromy
    }
    protected  Wielkosc  wielkosc;

    // Metoda ustawiająca wielkość automatycznie na podstawie wagi
    public void ustalWielkoscNaPodstawieWagi() {
        if (waga <= 2) wielkosc = Wielkosc.maly;
        else if (waga <= 10) wielkosc = Wielkosc.sredni;
        else if (waga <= 50) wielkosc = Wielkosc.duzy;
        else wielkosc = Wielkosc.ogromy;
    }

    @Override
    public String toString() {
        return "Gatunek: " + gatunek + ", Wiek: " + wiek + ", Waga: " + waga + "kg (" + wielkosc + ")";
    }
}
