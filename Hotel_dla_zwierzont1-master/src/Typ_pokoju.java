public class Typ_pokoju
{



    public enum TypPokoju
    {
        lodowy, wodny
    }


    public enum RodzajPokoju
    {
        maly, sredni, duzy, ogromny
    }

    public RodzajPokoju rodzajPokoju;

    public TypPokoju typPokoju;



    public Typ_pokoju(TypPokoju typPokoju, RodzajPokoju rodzajPokoju)
    {
        this.typPokoju = typPokoju;
        this.rodzajPokoju = rodzajPokoju;
    }






}
