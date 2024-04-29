package erronka3;

import java.util.ArrayList;

public class BDSaltzaile {
    private ArrayList<Saltzaile> saltzaileLista;

    public BDSaltzaile(){
        saltzaileLista=new ArrayList<Saltzaile>();
    }

    public void gehituSaltzaile(Saltzaile s) {
        saltzaileLista.add(s);
    }

    @Override
	public String toString() {
		return "BDSaltzaile [saltzaileLista=" + saltzaileLista + "]";
	}
    
    public void setLista(ArrayList<Saltzaile> lista) {
		this.saltzaileLista = lista;
	}
    
    public void kenduSaltzaile(Saltzaile saltzaile) {
		saltzaileLista.remove(saltzaile);
	}
}
