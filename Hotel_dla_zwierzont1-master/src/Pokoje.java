public class Pokoje extends Typ_pokoju
{
    int Nr;
    // Dodałem te dwa pola, żeby pokój działał
    public boolean zajety = false;
    public Zwierzeta gosc = null;

    public Pokoje(int Nr,TypPokoju typPokoju, RodzajPokoju rodzajPokoju, CenaPokoju  cenaPokoju)
    {
        super(typPokoju, rodzajPokoju, cenaPokoju);
        this.Nr = Nr;
    }

    @Override
    public String toString() {
        // Zmieniłem wyświetlanie, żeby pokazywało czy zajęte
        String status = zajety ? (" [ZAJĘTY przez: " + gosc.gatunek + "]") : " [WOLNY]";
        return  "Pokój " + Nr + " " + rodzajPokoju + " " + typPokoju + " " + cenaPokoju + status;
    }
}