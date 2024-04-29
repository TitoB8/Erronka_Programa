package erronka3;

import java.util.ArrayList;

public class BDKategoria {
	private ArrayList<Kategoria> kategoriaLista;

	public BDKategoria() {
		kategoriaLista=new ArrayList<Kategoria>();
	}

	public void kategoriaKargatu(Kategoria k) {
		kategoriaLista.add(k);
	}

	@Override
	public String toString() {
		return "BDKategoria [kategoriaLista=" + kategoriaLista + "]";
	}	
}
