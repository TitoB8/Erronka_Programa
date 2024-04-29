package erronka3;

import java.util.ArrayList;

public class BDLangile {
	private ArrayList<Langile> langileLista;

	public BDLangile() {
		langileLista= new ArrayList<Langile>();
	}

	public void langileKargatu(Langile l) {
		langileLista.add(l);
	}

	public ArrayList<Langile> getLangileLista() {
		return langileLista;
	}

	@Override
	public String toString() {
		return "BDLangile [langileLista=" + langileLista + "]";
	}
}