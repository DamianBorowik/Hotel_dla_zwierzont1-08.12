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
        System.out.println("\n--- REZERWACJA POKOJU ---");
        System.out.println("Dostępne wolne pokoje:");

        boolean czySaWolne = false;
        // 1. Wyświetlanie wolnych pokoi z cenami
        for (Pokoje p : pokoje) {
            if (!p.zajety) {
                int cena = pobierzCeneJakoLiczbe(p.cenaPokoju);
                System.out.println("Nr: " + p.Nr + " | Typ: " + p.typPokoju +
                        " | Rodzaj: " + p.rodzajPokoju +
                        " | Cena za noc: " + cena + " zł");
                czySaWolne = true;
            }
        }

        if (!czySaWolne) {
            System.out.println("PRZEPRASZAMY! Brak wolnych pokoi.");
            return; // Przerywamy, bo nie ma gdzie spać
        }

        // 2. Wybór pokoju
        System.out.print("\nWpisz NUMER pokoju, który chcesz wynająć: ");
        int wybranyNr = scanner.nextInt();
        scanner.nextLine();

        // Szukamy pokoju w tablicy (pamiętając, że index to nr-1)
        if (wybranyNr < 1 || wybranyNr > 80) {
            System.out.println("Nie ma takiego pokoju!");
            return;
        }

        Pokoje wybranyPokoj = pokoje[wybranyNr - 1];

        if (wybranyPokoj.zajety) {
            System.out.println("Ten pokój jest już zajęty! Spróbuj ponownie.");
            return;
        }

        // 3. Pytanie o liczbę nocy
        System.out.print("Na ile nocy chcesz wynająć pokój?: ");
        int noce = scanner.nextInt();
        scanner.nextLine();

        if (noce <= 0) {
            System.out.println("Musisz wynająć na minimum 1 noc.");
            return;
        }

        // 4. Obliczanie ceny
        int cenaZaDobe = pobierzCeneJakoLiczbe(wybranyPokoj.cenaPokoju);
        int kosztCalkowity = cenaZaDobe * noce;

        System.out.println("\n--- PODSUMOWANIE ---");
        System.out.println("Klient: " + zwierze.gatunek);
        System.out.println("Pokój nr: " + wybranyPokoj.Nr);
        System.out.println("Długość pobytu: " + noce + " nocy");
        System.out.println("Koszt całkowity: " + kosztCalkowity + " zł");
        System.out.println("Stan portwela klienta: " + zwierze.portfel + " zł");

        // 5. Sprawdzenie portfela i finalizacja
        if (zwierze.portfel >= kosztCalkowity) {
            System.out.println("\nCzy potwierdzasz rezerwację? (1-TAK, 2-NIE)");
            int decyzja = scanner.nextInt();
            scanner.nextLine();

            if (decyzja == 1) {
                // Pobieramy pieniądze
                zwierze.portfel -= kosztCalkowity;
                // Zajmujemy pokój
                wybranyPokoj.zajety = true;
                wybranyPokoj.gosc = zwierze;
                // Dodajemy do listy gości
                listaGosci.add(zwierze);

                System.out.println("SUKCES! Pokój zarezerwowany. Zostało w portwelu: " + zwierze.portfel + " zł");
            } else {
                System.out.println("Anulowano rezerwację.");
            }
        } else {
            System.out.println("BŁĄD: Klienta nie stać na ten pokój! Brakuje: " + (kosztCalkowity - zwierze.portfel) + " zł");
        }
    }

    // --- METODY DODAWANIA (Zaktualizowane, nie dodają do listy same, robi to zajmijPokoj) ---

    private static void dodajSsaka(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        Ssaki ssak = new Ssaki();
        ssak.wiek = wiek;
        if (rodzaj == 1) ssak.typSsaka = Ssaki.rodzaj.miesozerca;
        else ssak.typSsaka = Ssaki.rodzaj.roslinozerca;
        ssak.waga = waga;
        ssak.gatunek = gatunek;
        ssak.index = index;
        ssak.portfel = portwel;

        // Przekazujemy gotowe zwierzę do systemu rezerwacji
        zajmijPokoj(ssak);
    }

    private static void dodajRoślinę(int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        rośliny roślina = new rośliny();
        if (rodzaj == 1) roślina.typrośliny = rośliny.rodzaj.cieplolube;
        else if (rodzaj == 2) roślina.typrośliny = rośliny.rodzaj.zimnolubne;
        else if (rodzaj == 3) roślina.typrośliny = rośliny.rodzaj.cieniolubne;
        else roślina.typrośliny = rośliny.rodzaj.swiatlolubne;
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
                    System.out.println("Pokoje (Wyświetlam tylko zajęte i pierwsze 5 wolnych)");
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
                        System.out.println("od 0kg do 0,1kg jest maly");
                        System.out.println("od 0,11kg do 0,3kg jest sredni");
                        System.out.println("od 0,31kg do 1kg jest duzy");
                        System.out.println("od 1kg+ jest ogromny");
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
                        System.out.println("od 0kg do 0,1kg jest maly");
                        System.out.println("od 0,11kg do 0,5kg jest sredni");
                        System.out.println("od 0,51kg do 2kg jest duzy");
                        System.out.println("od 2kg+ jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajPtaka(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 5) { // Płaz
                        System.out.println("Podaj wiek"); int w = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Jaki Rodzaj (1-3)"); int r = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj wage");
                        System.out.println("od 0kg do 0,1kg jest maly");
                        System.out.println("od 0,11kg do 0,5kg jest sredni");
                        System.out.println("od 0,51kg do 2kg jest duzy");
                        System.out.println("od 2kg+ jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajPłaza(w, r, wa, g, index, p);
                    }
                    else if (Wybor2 == 6) { // Roślina
                        System.out.println("Podaj Rodzaj (1.cieniolubne 2. światłolubne 3. ciepłolubne 4. zimnolubne)"); int r = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj wysokosc");
                        System.out.println("od 0m do 0,2m jest maly");
                        System.out.println("od 0,21m do 1m jest sredni");
                        System.out.println("od 1,01m do 2kg jest duzy");
                        System.out.println("od 2m+ jest ogromny");
                        int wa = scanner.nextInt(); scanner.nextLine();
                        System.out.println("Podaj Gatunek"); String g = scanner.nextLine();
                        System.out.println("Podaj BUDŻET (portwel)"); int p = scanner.nextInt();
                        dodajRoślinę(r, wa, g, index, p);
                    }
                    else {
                        System.out.println("Nieprawidłowy wybór");
                    }
                    break;

                case 3:
                    System.out.println("--- LISTA GOŚCI ---");
                    for(Zwierzeta z : listaGosci) {
                        System.out.println(z.gatunek + " (" + z.index + ") - Portwel: " + z.portfel);
                    }
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