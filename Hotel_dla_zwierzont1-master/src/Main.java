import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    // 1. Scanner jest teraz tutaj, żeby był widoczny we wszystkich metodach
    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Zwierzeta> listaGosci = new ArrayList<>();
    static Pokoje[] pokoje = new Pokoje[80];

    private static void menu()
    {
        System.out.println("\n=== WITAMY W HOTELU DLA ZWIERZOT ===");
        System.out.println("1. Sprawdzenie pokoi");
        System.out.println("2. Dodanie gościa (Rejestracja)");
        System.out.println("3. Lista gości");
        System.out.println("4. Wymeldowanie gościa");
        System.out.println("5. Zamknij");
    }

    // --- METODY POMOCNICZE ---

    // Ta metoda zamienia dziwne Enumy (doba300zl) na konkretną liczbę (300)
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

    // --- GŁÓWNA LOGIKA REZERWACJI ---

    private static void zajmijPokoj(Zwierzeta zwierze) {
        System.out.println("\n--- AUTOMATYCZNE PRZYDZELANIE POKOJU ---");
        System.out.println("Szukam pokoju dla: " + zwierze.gatunek + " (Wielkość: " + zwierze.wielkosc + ")");

        // Określamy wymagany typ pokoju
        Typ_pokoju.TypPokoju wymaganyTyp = Typ_pokoju.TypPokoju.lodowy; // Domyślnie lodowy
        if (zwierze instanceof Ryby) {
            wymaganyTyp = Typ_pokoju.TypPokoju.wodny; // Ryby muszą mieć wodny
        }
        // Można dodać też wodny dla ssaków wodnych czy gadów wodnych jeśli trzeba

        // Konwersja wielkosci zwierzecia na rodzaj pokoju
        Typ_pokoju.RodzajPokoju wymaganyRodzaj = Typ_pokoju.RodzajPokoju.maly;
        if (zwierze.wielkosc == Zwierzeta.wielkosc.sredni) wymaganyRodzaj = Typ_pokoju.RodzajPokoju.sredni;
        if (zwierze.wielkosc == Zwierzeta.wielkosc.duzy) wymaganyRodzaj = Typ_pokoju.RodzajPokoju.duzy;
        if (zwierze.wielkosc == Zwierzeta.wielkosc.ogromy) wymaganyRodzaj = Typ_pokoju.RodzajPokoju.ogromny;

        Pokoje znalezionyPokoj = null;

        // Szukanie pasującego pokoju
        for (Pokoje p : pokoje) {
            if (!p.zajety && p.typPokoju == wymaganyTyp && p.rodzajPokoju == wymaganyRodzaj) {
                znalezionyPokoj = p;
                break; // Znaleziono pierwszy wolny pasujący
            }
        }

        if (znalezionyPokoj == null) {
            System.out.println("PRZEPRASZAMY! Brak wolnych pokoi o wymaganych parametrach (" + wymaganyTyp + ", " + wymaganyRodzaj + ").");
            return;
        }

        System.out.println("Znaleziono pokój nr: " + znalezionyPokoj.Nr + " (" + znalezionyPokoj.rodzajPokoju + ", " + znalezionyPokoj.typPokoju + "," + znalezionyPokoj.cenaPokoju + ")");

        // Pytanie o liczbę nocy
        System.out.print("Na ile nocy chcesz wynająć pokój?: ");
        int noce = scanner.nextInt();
        scanner.nextLine();

        if (noce <= 0) {
            System.out.println("Musisz wynająć na minimum 1 noc.");
            return;
        }

        // Obliczanie ceny
        int cenaZaDobe = pobierzCeneJakoLiczbe(znalezionyPokoj.cenaPokoju);
        int kosztCalkowity = cenaZaDobe * noce;

        System.out.println("\n--- PODSUMOWANIE ---");
        System.out.println("Koszt całkowity: " + kosztCalkowity + " zł");
        System.out.println("Stan portwela klienta: " + zwierze.portfel + " zł");

        // Sprawdzenie portfela i finalizacja
        if (zwierze.portfel >= kosztCalkowity) {
            System.out.println("\nCzy potwierdzasz rezerwację? (1-TAK, 2-NIE)");
            int decyzja = scanner.nextInt();
            scanner.nextLine();

            if (decyzja == 1) {
                zwierze.portfel -= kosztCalkowity;
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
    private static void wymeldujGoscia()
    {
        System.out.println("Podaj numer pokoju do wymeldowania:");
        int nr = scanner.nextInt();
        scanner.nextLine(); // czyszczenie bufora

        // Sprawdzenie czy pokoj istnieje
        if (nr < 1 || nr > 80) {
            System.out.println("Nie ma takiego pokoju!");
            return;
        }

        Pokoje pokoj = pokoje[nr - 1]; // indeks tablicy to nr - 1

        if (pokoj.zajety) {
            System.out.println("Wymeldowano gościa: " + pokoj.gosc.gatunek);

            // Usunięcie z listy gosci
            listaGosci.remove(pokoj.gosc);

            // Zwolnienie pokoju
            pokoj.zajety = false;
            pokoj.gosc = null;

            System.out.println("Pokój nr " + nr + " jest teraz wolny.");
        } else {
            System.out.println("Ten pokój nie jest zajęty!");
        }
    }

    // --- METODY DODAWANIA (Zaktualizowane, nie dodają do listy same, robi to zajmijPokoj) ---

    private static void dodajSsaka(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        Ssaki ssak = new Ssaki();
        ssak.wiek = wiek;
        if (rodzaj == 1) ssak.typSsaka = Ssaki.rodzaj.miesozerca;
        else ssak.typSsaka = Ssaki.rodzaj.roslinozerca;

        // Logika rozmiaru dla ssaka
        if (waga <= 2) ssak.wielkosc = Zwierzeta.wielkosc.maly;
        else if (waga <= 10) ssak.wielkosc = Zwierzeta.wielkosc.sredni;
        else if (waga <= 50) ssak.wielkosc = Zwierzeta.wielkosc.duzy;
        else ssak.wielkosc = Zwierzeta.wielkosc.ogromy;

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

        // Logika rozmiaru dla rośliny (waga tu robi za wysokość w cm/m - uproszczenie int)
        // Zakładam waga jako cm dla uproszczenia intów lub m * 100
        if (waga <= 20) roślina.wielkosc = Zwierzeta.wielkosc.maly; // do 0.2m (20cm)
        else if (waga <= 100) roślina.wielkosc = Zwierzeta.wielkosc.sredni; // do 1m
        else if (waga <= 200) roślina.wielkosc = Zwierzeta.wielkosc.duzy; // do 2m
        else roślina.wielkosc = Zwierzeta.wielkosc.ogromy;

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

        // Logika rozmiaru dla ryby (waga w gramach * 10 lub kg - dostosowane do int)
        // Zakładam waga jako jednostka umowna zgodna z textem w menu
        if (waga <= 100) ryba.wielkosc = Zwierzeta.wielkosc.maly; // 0.1kg = 100g
        else if (waga <= 300) ryba.wielkosc = Zwierzeta.wielkosc.sredni;
        else if (waga <= 1000) ryba.wielkosc = Zwierzeta.wielkosc.duzy;
        else ryba.wielkosc = Zwierzeta.wielkosc.ogromy;

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

        // Logika rozmiaru dla gada (przyjąłem te same co ssaka bo w tekscie były gwiazdki ***)
        if (waga <= 2) gad.wielkosc = Zwierzeta.wielkosc.maly;
        else if (waga <= 10) gad.wielkosc = Zwierzeta.wielkosc.sredni;
        else if (waga <= 50) gad.wielkosc = Zwierzeta.wielkosc.duzy;
        else gad.wielkosc = Zwierzeta.wielkosc.ogromy;

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

        // Logika rozmiaru dla płaza
        if (waga <= 100) płaz.wielkosc = Zwierzeta.wielkosc.maly;
        else if (waga <= 500) płaz.wielkosc = Zwierzeta.wielkosc.sredni;
        else if (waga <= 2000) płaz.wielkosc = Zwierzeta.wielkosc.duzy;
        else płaz.wielkosc = Zwierzeta.wielkosc.ogromy;

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

        // Logika rozmiaru dla ptaka
        if (waga <= 100) ptak.wielkosc = Zwierzeta.wielkosc.maly;
        else if (waga <= 500) ptak.wielkosc = Zwierzeta.wielkosc.sredni;
        else if (waga <= 2000) ptak.wielkosc = Zwierzeta.wielkosc.duzy;
        else ptak.wielkosc = Zwierzeta.wielkosc.ogromy;

        ptak.waga = waga;
        ptak.gatunek = gatunek;
        ptak.index = index;
        ptak.portfel = portwel;

        zajmijPokoj(ptak);
    }

    // --- MAIN ---

    public static void main(String[] args)
    {
        // Inicjalizacja pokoi (Twoja logika)
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
        int index = 0;

        while(Start == true)
        {
            menu();
            int Wybur1 = scanner.nextInt();
            scanner.nextLine();

            switch (Wybur1)
            {
                case 1:
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

                case 2:
                    System.out.println("Dodanie Goscia");
                    index++;
                    System.out.println("1. Dodaj ssaka");
                    System.out.println("2. Dodaj rybę");
                    System.out.println("3. Dodaj gada");
                    System.out.println("4. Dodaj ptaka");
                    System.out.println("5. Dodaj płaza");
                    System.out.println("6. Dodaj rośline");

                    int Wybor2 = scanner.nextInt();
                    scanner.nextLine();

                    // Obsługa dodawania
                    if (Wybor2 == 1) { // Ssak
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1.Miesozerca 2.Roslinozerca)"); int r = scanner.nextInt(); scanner.nextLine();
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
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1.Slodkowodna 2.Slonowodna)"); int r = scanner.nextInt(); scanner.nextLine();
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
                        System.out.println("Podaj wage");
                        System.out.println("od 0kg do 2kg jest maly");
                        System.out.println("od 2,01kg do 10kg jest sredni");
                        System.out.println("od 10kg do 50kg jest duzy");
                        System.out.println("od 50kg+ jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajGada(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 4) { // Ptak
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1.papuga 2.kruk 3.gołąb 4. sokół 5.sowa 6.kanarek)"); int r = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj wage");
                        System.out.println("od 0g do 100g jest maly");
                        System.out.println("od 101g do 500g jest sredni");
                        System.out.println("od 501g do 2000g jest duzy");
                        System.out.println("od 2001g+ jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajPtaka(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 5) { // Płaz
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1-3)"); int r = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj wage");
                        System.out.println("od 0g do 100g jest maly");
                        System.out.println("od 101g do 500g jest sredni");
                        System.out.println("od 501g do 2000g jest duzy");
                        System.out.println("od 2001g+ jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajPłaza(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 6) { // Roślina
                        System.out.println("Podaj Rodzaj (1.cieniolubne 2. światłolubne 3. ciepłolubne 4. zimnolubne)"); int r = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj wysokosc");
                        System.out.println("od 0m do 20cm jest maly");
                        System.out.println("od 21cm do 100cm jest sredni");
                        System.out.println("od 101cm do 200cm jest duzy");
                        System.out.println("od 201cm jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();

                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajRoślinę(r, wa, g, index, p);
                    }
                    else {
                        System.out.println("Nieprawidłowy wybór");
                    }
                    break;
                        //to jest do naprawy i trzeba zrobic tak aby program wyswietlal numer pokoju w ktorym znajduje sie gosc
                case 3:
                    System.out.println("--- LISTA GOŚCI ---");
                    for(Zwierzeta z : listaGosci) {
                        System.out.println(aktualnypokoj"wiek" + " " + z.wiek + " " + "waga"+ " " + z.waga + " "+ "gatunek" + " " + z.gatunek + " " + "index" + " (" + z.index + ") - Pozostałe środki: " + z.portfel);
                    }
                    break;
                case 4:
                    wymeldujGoscia();
                    break;

                case 5:
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