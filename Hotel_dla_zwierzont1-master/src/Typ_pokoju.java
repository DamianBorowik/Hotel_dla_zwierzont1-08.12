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

    public enum CenaPokoju
    {
        doba300zl, doba500zl, doba700zl, doba1000zl, doba1500zl
    }

    public RodzajPokoju rodzajPokoju;

    public TypPokoju typPokoju;

    public  CenaPokoju cenaPokoju;


    public Typ_pokoju(TypPokoju typPokoju, RodzajPokoju rodzajPokoju, CenaPokoju cenaPokoju)
    {
        this.typPokoju = typPokoju;
        this.rodzajPokoju = rodzajPokoju;
        this.cenaPokoju = cenaPokoju;
    }






}
