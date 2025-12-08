import java.util.Scanner;

public class Main
{


    private static void menu()
    {
        System.out.println("WITAMY W HOTELU DLA ZWIERZOT");
        System.out.println("1.Sprawdzenie pokoi");
        System.out.println("2.Dodanie gościa");
        System.out.println("3.Usunięcie gościa");
        System.out.println("4.Daty zakończenia");
        System.out.println("5.Zamknij");
    }

    private static void dodajSsaka(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        Ssaki ssak = new Ssaki();

        ///////////////// Dane
        ssak.wiek = wiek;

        Ssaki.rodzaj typSsaka;
        if (rodzaj == 1)
        {
            typSsaka = Ssaki.rodzaj.miesozerca;
        }
        else
        {
            typSsaka = Ssaki.rodzaj.roslinozerca;
        }

        ssak.waga = waga;
        ssak.gatunek = gatunek;
        ssak.index = index;
        ssak.porcwel = portwel;

    }

    private static void dodajGada(int wiek, int rodzaj, int waga, String gatunek, int index, int portwel)
    {
        Gady gad = new Gady();

        ///////////////// Dane
        gad.wiek = wiek;

        Gady.rodzaj typGada;
        if (rodzaj == 1)
        {
            typGada = Gady.rodzaj.woz;
        }

        if (rodzaj == 2)
        {
            typGada = Gady.rodzaj.jaszczurka;
        }

        if (rodzaj == 2)
        {
            typGada = Gady.rodzaj.zulw;
        }

        if (rodzaj == 2)
        {
            typGada = Gady.rodzaj.krokodyl;
        }

        gad.waga = waga;
        gad.gatunek = gatunek;
        gad.index = index;
        gad.porcwel = portwel;

    }



    public static void main(String[] args)
    {

        Scanner scanner = new Scanner(System.in);  //Dodanie scannera







    ///////////////////////////////testwe (co powinno być)
    //menu
    //1 Sprawdzenie pokoi
    //2 Dodanie gościa
    //3 usunięcie gościa
    //4 daty zakończenia wypożyczania na podstawie portwela? (do możliwego portwela)


        /*
        Pokoje[] pokoje =
        {
                new Pokoje(1,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.maly),
                new Pokoje(2,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.duzy),
                new Pokoje(3,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.ogromny),
                new Pokoje(4,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.sredni)
        };
        */

    /// /////////////////////////// Dodawanie pokoi (nie w funkci bo nie ma sensu)

        // Trzeba dodać możliwość zajęcia pokoi (enum obecność: Zajęte, wolne)

        Pokoje[] pokoje = new Pokoje[80];

        for(int i = 1; i<81; i++)
        {

            if(i < 11)
            {

                pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.maly);

            }

            else if(i<21)
            {

                pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.maly);

            }

            else if(i<31)
            {

                pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.sredni);

            }

            else if(i<41)
            {

                pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.sredni);

            }

            else if(i<51)
            {

                pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.duzy);

            }

            else if(i<61)
            {

                pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.duzy);

            }

            else if(i<71)
            {

                pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.lodowy, Typ_pokoju.RodzajPokoju.ogromny);

            }

            else
            {

                pokoje[i-1] = new Pokoje(i,Typ_pokoju.TypPokoju.wodny, Typ_pokoju.RodzajPokoju.ogromny);

            }



        }


        // pokoje

        /*

        for(Pokoje pokoj : pokoje)
        {
        System.out.println(pokoj);
        }

        */




        ////////////////////////////////////////////////////////////////////////////////////////////////////Menu
        boolean Start = true;

        int index = 0;

        // Trzeba będzie dodać bibliotekę dla wszystkich zwierząt i możliwość połączenia z pokojami
        // Dodać portwele gościom i napisać jak i kiedy muszą wyjść (za ile dni (można nawet daty dopisać))



        while(Start == true)
    {
        menu();


        int Wybur1 = scanner.nextInt();
        scanner.nextLine();

        switch (Wybur1)
        {
            case 1:
                System.out.println("Pokoje");

                for(Pokoje pokoj : pokoje)
                {
                    System.out.println(pokoj);
                }

                // trzeba to pozminiać (prwadopodobnie pokazać tylko zajęte pokoje)

                break;

            case 2:
                System.out.println("Dodanie Goscia");

                index++;

                System.out.println("1. Dodaj ssaka");
                System.out.println("2. Dodaj rybę");
                System.out.println("3. Dodaj gada");

                int Wybor2 = scanner.nextInt();
                scanner.nextLine();

                    switch (Wybor2)
                    {
                        case 1:

                            System.out.println("Podaj wiek");
                            int wiek = scanner.nextInt();

                            scanner.nextLine();

                            System.out.println("Jaki Rodzaj");
                            System.out.println("1.Miesozerca");
                            System.out.println("2.Roslinozerca");

                            int rodzaj = scanner.nextInt();


                            scanner.nextLine();

                            while(rodzaj != 1 && rodzaj != 2)
                            {
                                System.out.println("Podaj 1 lub 2");
                                rodzaj = scanner.nextInt();

                                scanner.nextLine();
                            }

                            System.out.println("Podaj wage");
                            System.out.println("od *** do *** jest maly");
                            System.out.println("od *** do *** jest sredni");
                            System.out.println("od *** do *** jest duzy");
                            System.out.println("od *** do *** jest ogromny");
                            int waga = scanner.nextInt();

                            scanner.nextLine();

                            System.out.println("Podaj Gatunek");
                            String gatunek = scanner.nextLine();

                            scanner.nextLine();

                            System.out.println("Podaj cenę pobytu");
                            int portwel = scanner.nextInt();



                            dodajSsaka(wiek,rodzaj,waga,gatunek,index,portwel);
                            break;


                        case 2:
                            //dodajRybe();
                            break;


                        case 3:
                            System.out.println("Podaj wiek");
                            wiek = scanner.nextInt();

                            scanner.nextLine();

                            System.out.println("Jaki Rodzaj");
                            System.out.println("1.woz");
                            System.out.println("2.jaszczurka");
                            System.out.println("3.zulw");
                            System.out.println("4.krokodyl");
                            rodzaj = scanner.nextInt();

                            scanner.nextLine();

                            while(rodzaj != 1 && rodzaj != 2 && rodzaj != 3 && rodzaj != 4)
                            {
                                System.out.println("Podaj 1, 2, 3 lub 4");
                                rodzaj = scanner.nextInt();

                                scanner.nextLine();
                            }

                            System.out.println("Podaj wage");
                            System.out.println("od *** do *** jest maly");
                            System.out.println("od *** do *** jest sredni");
                            System.out.println("od *** do *** jest duzy");
                            System.out.println("od *** do *** jest ogromny");
                            waga = scanner.nextInt();

                            scanner.nextLine();

                            System.out.println("Podaj Gatunek");
                            gatunek = scanner.nextLine();
                            scanner.nextLine();

                            System.out.println("Podaj budzet na zostanie");
                            portwel = scanner.nextInt();

                            dodajGada(wiek,rodzaj,waga,gatunek,index,portwel);

                            break;


                        default:
                            System.out.println("Nieprawidłowy wybór");
                            break;


                    }
            break;


            case 3:
                System.out.println("Usuniecie Goscia");
                // nie robić na początkut to może nie działać poprawnie

                break;


            case 4:
                System.out.println("Daty Zakończenia");
                // To jest odnośnie tego jeżeli dodamy wartość pokoi i portwel gościa
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
