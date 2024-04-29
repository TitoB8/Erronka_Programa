package erronka3;

import java.util.ArrayList;

public class BDLanpostu {
    private ArrayList<Lanpostu> lanpostuLista;

    public BDLanpostu() {
        lanpostuLista = new ArrayList<Lanpostu>();
    }

    public void lanpostuKargatu(Lanpostu l) {
        lanpostuLista.add(l);
    }
    
    @Override
	public String toString() {
		return "BDLanpostu [lanpostuLista=" + lanpostuLista + "]";
	}

}
