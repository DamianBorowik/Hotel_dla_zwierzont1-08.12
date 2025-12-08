public abstract class Zwierzeta
{

    int wiek;
    int waga; // Moim zdaniem trzeba zrobić wielkość względem wagi (wtedy enum chyba nie potrzebny bo w innym miejscu)
    String gatunek;
    int index;

    public enum wielkosc
    {
        maly, sredni, duzy, ogromy
    }

    protected wielkosc wielkosc;




    int porcwel;
    // Portwel osoby (można nie trzeba)?



    //Comparable lub Comparator dla wagi

    // Dodać nowe klasy zwierząt


}
