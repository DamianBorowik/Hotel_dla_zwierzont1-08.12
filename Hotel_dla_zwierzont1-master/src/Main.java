import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
 // 1. Scanner jest teraz tutaj, żeby był widoczny we wszystkich metodach
    static Scanner scanner = new Scanner(System.in);

    // Lista przechowująca wszystkie zarejestrowane zwierzęta
    static ArrayList<Zwierzeta> listaGosci = new ArrayList<>();

    // Tablica reprezentująca hotel - 80 pokoi
    static Pokoje[] pokoje = new Pokoje[80];

    private static void menu()
    {
        System.out.println(" WITAMY W HOTELU DLA ZWIERZONT ");
        System.out.println("1. Sprawdzenie pokoi");
        System.out.println("2. Dodanie gościa (Rejestracja)");
        System.out.println("3. Lista gości");
        System.out.println("4. Wymeldowanie gościa");
        System.out.println("5. Zamknij");
    }

   // nie przechdzily mi enumy jak byly samymi liczbami to dodalem dobaxzl i returny i smiga legancko

    private static int pobierzCeneJakoLiczbe(Typ_pokoju.CenaPokoju cenaEnum) {
        switch (cenaEnum) {
            case doba300zl: return 300;
            case doba500zl: return 500;
            case doba700zl: return 700;
            case doba1000zl: return 1000;
            case doba1500zl: return 1500;
            default: return 0;
        }
    }


    //szuka pokoju sprawdza kasę i melduje.
    private static void zajmijPokoj(Zwierzeta zwierze) {
        System.out.println(" AUTOMATYCZNE PRZYDZELANIE POKOJU ");
        System.out.println("Szukam pokoju dla: " + zwierze.gatunek + " (Wielkość: " + zwierze.wielkosc + ")");

        //Określenie wymagań zwierzęcia
        Typ_pokoju.TypPokoju wymaganyTyp = Typ_pokoju.TypPokoju.lodowy;
        if (zwierze instanceof Ryby) {
            wymaganyTyp = Typ_pokoju.TypPokoju.wodny;
        }

        // Dopasowanie wielkości pokoju do wielkości zwierzęcia
        Typ_pokoju.RodzajPokoju wymaganyRodzaj = Typ_pokoju.RodzajPokoju.maly;
        if (zwierze.wielkosc == Zwierzeta.Wielkosc.sredni) wymaganyRodzaj = Typ_pokoju.RodzajPokoju.sredni;
        if (zwierze.wielkosc == Zwierzeta.Wielkosc.duzy) wymaganyRodzaj = Typ_pokoju.RodzajPokoju.duzy;
        if (zwierze.wielkosc == Zwierzeta.Wielkosc.ogromy) wymaganyRodzaj = Typ_pokoju.RodzajPokoju.ogromny;

        Pokoje znalezionyPokoj = null;

        //Przeszukanie hotelu
        for (Pokoje p : pokoje) {
            // Szukamy pokoju, który jest pusty
            if (!p.zajety && p.typPokoju == wymaganyTyp && p.rodzajPokoju == wymaganyRodzaj) {
                znalezionyPokoj = p;
                break;
            }
        }

        if (znalezionyPokoj == null) {
            System.out.println("PRZEPRASZAMY! Brak wolnych pokoi o wymaganych parametrach (" + wymaganyTyp + ", " + wymaganyRodzaj + ").");
            return;
        }

        System.out.println("Znaleziono pokój nr: " + znalezionyPokoj.Nr + " (" + znalezionyPokoj.rodzajPokoju + ", " + znalezionyPokoj.typPokoju + "," + znalezionyPokoj.cenaPokoju + ")");

        //długość pobytu
        System.out.print("Na ile nocy chcesz wynająć pokój?: ");
        int noce = scanner.nextInt();
        scanner.nextLine();

        if (noce <= 0) {
            System.out.println("Musisz wynająć na minimum 1 noc.");
            return;
        }

        //Obliczenie
        int cenaZaDobe = pobierzCeneJakoLiczbe(znalezionyPokoj.cenaPokoju);
        int kosztCalkowity = cenaZaDobe * noce;

        System.out.println(" PODSUMOWANIE ");
        System.out.println("Koszt całkowity: " + kosztCalkowity + " zł");
        System.out.println("Stan portwela klienta: " + zwierze.portfel + " zł");

        if (zwierze.portfel >= kosztCalkowity) {
            System.out.println("\nCzy potwierdzasz rezerwację? (1-TAK, 2-NIE)");
            int decyzja = scanner.nextInt();
            while(decyzja != 1 && decyzja != 2)
            {
                System.out.println("Podaj 1 lub 2");
                decyzja = scanner.nextInt();

                scanner.nextLine();
            }
            scanner.nextLine();

            if (decyzja == 1) {
                zwierze.portfel -= kosztCalkowity;

                // Aktualizacja pokoju
                znalezionyPokoj.zajety = true;
                znalezionyPokoj.gosc = zwierze;

                listaGosci.add(zwierze);

                System.out.println("SUKCES! Pokój nr " + znalezionyPokoj.Nr + " zarezerwowany.");
                System.out.println("Pozostałe środki na koncie klienta" + " " + zwierze.portfel );

            } else {
                System.out.println("Anulowano rezerwację.");
            }
        } else {
            System.out.println("BŁĄD: Klienta nie stać na ten pokój! Brakuje: " + (kosztCalkowity - zwierze.portfel) + " zł");
        }
    }

    //do zwalniania pokoju
    private static void wymeldujGoscia()
    {
        System.out.println("Podaj numer pokoju do wymeldowania:");
        int nr = scanner.nextInt();
        scanner.nextLine();

        if (nr < 1 || nr > 80) {
            System.out.println("Nie ma takiego pokoju!");
            return;
        }

        // info o pokoju
        Pokoje pokoj = pokoje[nr - 1];

        if (pokoj.zajety) {
            System.out.println("Wymeldowano gościa: " + pokoj.gosc.gatunek);

            //Usuwamy gościa z listy
            listaGosci.remove(pokoj.gosc);

            //pokój do stanu "wolny"
            pokoj.zajety = false;
            pokoj.gosc = null;

            System.out.println("Pokój nr " + nr + " jest teraz wolny.");
        } else {
            System.out.println("Ten pokój nie jest zajęty!");
        }
    }

//dla wiekszych bydlakow jednostka bedzie w kg dla mniejszych bydlakow bedzie w gramach a dla roslin bedzie w cm.
    //cos sie sra jak sie chce dac 0,2kg wiec lepiej zeby byl 200g
    private static void dodajSsaka(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        Ssaki ssak = new Ssaki();
        ssak.wiek = wiek;
        if (rodzaj == 1) ssak.typSsaka = Ssaki.rodzaj.miesozerca;
        else ssak.typSsaka = Ssaki.rodzaj.roslinozerca;

        // Logika przypisania wielkości na podstawie wagi
        if (waga <= 2) ssak.wielkosc = Zwierzeta.Wielkosc.maly;
        else if (waga <= 10) ssak.wielkosc = Zwierzeta.Wielkosc.sredni;
        else if (waga <= 50) ssak.wielkosc = Zwierzeta.Wielkosc.duzy;
        else ssak.wielkosc = Zwierzeta.Wielkosc.ogromy;

        ssak.waga = waga;
        ssak.gatunek = gatunek;
        ssak.index = index;
        ssak.portfel = portwel;

        zajmijPokoj(ssak);
    }

    private static void dodajRoślinę(int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        rośliny roślina = new rośliny();
        if (rodzaj == 1) roślina.typrośliny = rośliny.rodzaj.cieplolube;
        else if (rodzaj == 2) roślina.typrośliny = rośliny.rodzaj.zimnolubne;
        else if (rodzaj == 3) roślina.typrośliny = rośliny.rodzaj.cieniolubne;
        else roślina.typrośliny = rośliny.rodzaj.swiatlolubne;

// roslinka to w wysokosci musi byc a dziala na zasadzie wagi to zostaje jako waga
        if (waga <= 20) roślina.wielkosc = Zwierzeta.Wielkosc.maly;
        else if (waga <= 100) roślina.wielkosc = Zwierzeta.Wielkosc.sredni;
        else if (waga <= 200) roślina.wielkosc = Zwierzeta.Wielkosc.duzy;
        else roślina.wielkosc = Zwierzeta.Wielkosc.ogromy;

        roślina.waga = waga;
        roślina.gatunek = gatunek;
        roślina.index = index;
        roślina.portfel = portwel;

        zajmijPokoj(roślina);
    }

    private static void dodajRybe(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        Ryby ryba = new Ryby();
        ryba.wiek = wiek;
        if (rodzaj == 1) ryba.typRyby = Ryby.rodzaj.slodko_wodne;
        else ryba.typRyby = Ryby.rodzaj.słonowodne;

        if (waga <= 100) ryba.wielkosc = Zwierzeta.Wielkosc.maly;
        else if (waga <= 300) ryba.wielkosc = Zwierzeta.Wielkosc.sredni;
        else if (waga <= 1000) ryba.wielkosc = Zwierzeta.Wielkosc.duzy;
        else ryba.wielkosc = Zwierzeta.Wielkosc.ogromy;

        ryba.waga = waga;
        ryba.gatunek = gatunek;
        ryba.index = index;
        ryba.portfel = portwel;

        zajmijPokoj(ryba);
    }

    private static void dodajGada(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        Gady gad = new Gady();
        gad.wiek = wiek;
        if (rodzaj == 1) gad.typGada = Gady.rodzaj.woz;
        else if (rodzaj == 2) gad.typGada = Gady.rodzaj.jaszczurka;
        else if (rodzaj == 3) gad.typGada = Gady.rodzaj.zulw;
        else gad.typGada = Gady.rodzaj.krokodyl;

        if (waga <= 2) gad.wielkosc = Zwierzeta.Wielkosc.maly;
        else if (waga <= 10) gad.wielkosc = Zwierzeta.Wielkosc.sredni;
        else if (waga <= 50) gad.wielkosc = Zwierzeta.Wielkosc.duzy;
        else gad.wielkosc = Zwierzeta.Wielkosc.ogromy;

        gad.waga = waga;
        gad.gatunek = gatunek;
        gad.index = index;
        gad.portfel = portwel;

        zajmijPokoj(gad);
    }

    private static void dodajPłaza(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        płazy płaz= new płazy();
        płaz.wiek = wiek;
        if (rodzaj == 1) płaz.typpłaza = płazy.rodzaj.ogoniaste;
        else if (rodzaj == 2) płaz.typpłaza = płazy.rodzaj.bezogonowe;
        else płaz.typpłaza = płazy.rodzaj.beznogie;

        if (waga <= 100) płaz.wielkosc = Zwierzeta.Wielkosc.maly;
        else if (waga <= 500) płaz.wielkosc = Zwierzeta.Wielkosc.sredni;
        else if (waga <= 2000) płaz.wielkosc = Zwierzeta.Wielkosc.duzy;
        else płaz.wielkosc = Zwierzeta.Wielkosc.ogromy;

        płaz.waga = waga;
        płaz.gatunek = gatunek;
        płaz.index = index;
        płaz.portfel = portwel;

        zajmijPokoj(płaz);
    }

    private static void dodajPtaka(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        ptaki ptak= new ptaki();
        ptak.wiek = wiek;
        if (rodzaj == 1) ptak.typPtaka = ptaki.rodzaj.papuga;
        else if (rodzaj == 2) ptak.typPtaka = ptaki.rodzaj.kruk;
        else if (rodzaj == 3) ptak.typPtaka = ptaki.rodzaj.sowa;
        else if (rodzaj == 4) ptak.typPtaka = ptaki.rodzaj.gołąb;
        else if (rodzaj == 5) ptak.typPtaka = ptaki.rodzaj.sokół;
        else ptak.typPtaka = ptaki.rodzaj.kanarek;

        if (waga <= 100) ptak.wielkosc = Zwierzeta.Wielkosc.maly;
        else if (waga <= 500) ptak.wielkosc = Zwierzeta.Wielkosc.sredni;
        else if (waga <= 2000) ptak.wielkosc = Zwierzeta.Wielkosc.duzy;
        else ptak.wielkosc = Zwierzeta.Wielkosc.ogromy;

        ptak.waga = waga;
        ptak.gatunek = gatunek;
        ptak.index = index;
        ptak.portfel = portwel;

        zajmijPokoj(ptak);
    }

    //PUNKT STARTOWY PROGRAMU

    public static void main(String[] args)
    {
        //tworzenie POKOI
        for(int i = 1; i<81; i++) {
            if(i < 11) pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.maly, Typ_pokoju.CenaPokoju.doba500zl);
            else if(i<21) pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.maly, Typ_pokoju.CenaPokoju.doba300zl);
            else if(i<31) pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.sredni, Typ_pokoju.CenaPokoju.doba700zl);
            else if(i<41) pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.sredni, Typ_pokoju.CenaPokoju.doba500zl);
            else if(i<51) pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.duzy, Typ_pokoju.CenaPokoju.doba1000zl);
            else if(i<61) pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.duzy, Typ_pokoju.CenaPokoju.doba700zl);
            else if(i<71) pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.ogromny, Typ_pokoju.CenaPokoju.doba1500zl);
            else pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.ogromny, Typ_pokoju.CenaPokoju.doba1000zl);
        }

        boolean Start = true;
        int index = 0; // nadawanie id

        //PĘTLA PROGRAMU (działa dopóki Start == true)
        while(Start == true)
        {
            menu();
            int Wybur1 = scanner.nextInt();
            scanner.nextLine();

            switch (Wybur1)
            {
                case 1: // Wyświetlanie pokoi
                    System.out.println("Pokoje (Wyświetlam stan pokoi)");
                    int licznikWolnych = 0;
                    for(Pokoje pokoj : pokoje) {
                        if (pokoj.zajety) System.out.println(pokoj);
                        else if (licznikWolnych < 80) {
                            System.out.println(pokoj);
                            licznikWolnych++;
                        }
                    }
                    break;

                case 2: // Dodawanie gościa
                    System.out.println("Dodanie Goscia");
                    index++; // Zwiększamy licznik ID

                    System.out.println("1. Dodaj ssaka");
                    System.out.println("2. Dodaj rybę");
                    System.out.println("3. Dodaj gada");
                    System.out.println("4. Dodaj ptaka");
                    System.out.println("5. Dodaj płaza");
                    System.out.println("6. Dodaj rośline");

                    int Wybor2 = scanner.nextInt();
                    scanner.nextLine();

                    if (Wybor2 == 1) { // Ssak
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1.Miesozerca 2.Roslinozerca)"); int r = scanner.nextInt(); scanner.nextLine();
                        while(r!= 1 && r!= 2)
                        {
                            System.out.println("TYLKO 1 lub 2");
                            r= scanner.nextInt();

                            scanner.nextLine();
                        }
                        System.out.println("Podaj wage");
                        System.out.println("od 0kg do 2kg jest maly");
                        System.out.println("od 2,01kg do 10kg jest sredni");
                        System.out.println("od 10kg do 50kg jest duzy");
                        System.out.println("od 50kg+ jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajSsaka(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 2) { // Ryba
                        //pobieranie danych dla ryby
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1.Slodkowodna 2.Slonowodna)"); int r = scanner.nextInt(); scanner.nextLine();
                        while(r!= 1 && r!= 2)
                        {
                            System.out.println("TYLKO 1 lub 2");
                            r = scanner.nextInt();

                            scanner.nextLine();
                        }
                        System.out.println("Podaj wage");
                        System.out.println("od 0kg do 100g jest maly");
                        System.out.println("od 101g do 300g jest sredni");
                        System.out.println("od 301g do 1000g jest duzy");
                        System.out.println("od 1000g+ jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajRybe(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 3) { // Gad
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1.Woz 2.Jaszczurka 3.Zulw 4.Krokodyl)"); int r = scanner.nextInt(); scanner.nextLine();
                        while(r!= 1 && r!= 2&& r!= 3&& r!= 4)
                        {
                            System.out.println("TYLKO od 1 do 4");
                            r = scanner.nextInt();

                            scanner.nextLine();
                        }
                        System.out.println("Podaj wage");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajGada(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 4) { // Ptak
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1.papuga 2.kruk 3.gołąb 4. sokół 5.sowa 6.kanarek)"); int r = scanner.nextInt(); scanner.nextLine();
                        while(r!= 1 && r != 2&& r!= 3&& r!= 4&& r!= 5&& r!= 6)
                        {
                            System.out.println("TYLKO od 1 do 6");
                            r = scanner.nextInt();

                            scanner.nextLine();
                        }
                        System.out.println("Podaj wage");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajPtaka(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 5) { // Płaz
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1.Bezogonowe 2.Ogoniaste 3.Beznogie)"); int r = scanner.nextInt(); scanner.nextLine();
                        while(r != 1 && r != 2&& r!= 3)
                        {
                            System.out.println("TYLKO 1 lub 2 lub 3");
                            r = scanner.nextInt();

                            scanner.nextLine();
                        }
                        System.out.println("Podaj wage");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajPłaza(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 6) { // Roślina
                        System.out.println("Podaj Rodzaj (1.cieniolubne 2. światłolubne 3. ciepłolubne 4. zimnolubne)"); int r = scanner.nextInt(); scanner.nextLine();
                        while(r != 1 && r != 2&& r!= 3&& r!= 4)
                        {
                            System.out.println("TYLKO od 1 do 4");
                            r = scanner.nextInt();

                            scanner.nextLine();
                        }
                        System.out.println("Podaj wysokosc");
                        int wa = scanner.nextInt(); scanner.nextLine();

                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajRoślinę(r, wa, g, index, p);
                    }
                    else {
                        System.out.println("Nieprawidłowy wybór");
                    }
                    break;

                case 3: // Lista Gości
                    System.out.println("LISTA GOŚCI");
                    // Pętla po wszystkich gościach
                    for(Zwierzeta z : listaGosci) {

                        //szuka w którym pokoju mieszka gość
                        int numerPokojuGoscia = -1; // -1 = "nie znaleziono"
                        for(Pokoje p : pokoje) {
                            if(p.zajety && p.gosc == z) {
                                numerPokojuGoscia = p.Nr;
                                break;
                            }
                        }

                        String infoPokoj = (numerPokojuGoscia != -1) ? "Pokój nr: " + numerPokojuGoscia : "Brak pokoju";

                        System.out.println(
                                infoPokoj +
                                        " Wiek: " + z.wiek + ", " +
                                        " Waga: " + z.waga + ", " +
                                        " Gatunek: " + z.gatunek + ", " +
                                        " Index: (" + z.index + "), " +
                                        " Zapas finsnsowy: " + z.portfel + ", "
                        );
                    }
                    break;

                case 4: // Wymeldowanie
                    wymeldujGoscia();
                    break;

                case 5: // Zakończenie
                    Start = false;
                    System.out.println("Do zobaczenia");
                    break;

                default:
                    System.out.println("Nieprawidłowy wybór");
                    break;
            }
        }
    }
}
