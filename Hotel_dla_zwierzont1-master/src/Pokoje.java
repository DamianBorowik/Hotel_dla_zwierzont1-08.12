public class Pokoje extends Typ_pokoju
{

// cena za typ pokoju

//Dać konkretną ilość pokoi i przypisać im typy (tabela poki)


    int Nr;


    public Pokoje(int Nr,TypPokoju typPokoju, RodzajPokoju rodzajPokoju)
    {
        super(typPokoju, rodzajPokoju);
        this.Nr = Nr;

    }


    @Override
    public String toString() {
        return  Nr + " " + rodzajPokoju + " " + typPokoju;
    }

}
