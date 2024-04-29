package erronka3;

import java.util.ArrayList;

/**
 * Kontinenteak kudeatzen dituen klasea.
 */
public class BDKontinente {
    private ArrayList<Kontinente> kontinenteLista;

    public BDKontinente() {
        kontinenteLista = new ArrayList<Kontinente>();
    }

    public void kontinenteKargatu(Kontinente k) {
        kontinenteLista.add(k);
    }

    @Override
	public String toString() {
		return "BDKontinente [kontinenteLista=" + kontinenteLista + "]";
	}   
}
