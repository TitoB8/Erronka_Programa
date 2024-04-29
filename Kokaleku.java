package erronka3;

public class Kokaleku {
    private int id;
    private String helbide;
    private String postakodea;
    private String udalerri;
    private String probintzia;
    private String idHerrialde;

    public Kokaleku(int id, String helbide, String postakodea, String udalerri, String probintzia, String idHerrialde) {
        this.id = id;
        this.helbide = helbide;
        this.postakodea = postakodea;
        this.udalerri = udalerri;
        this.probintzia = probintzia;
        this.idHerrialde = idHerrialde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHelbide() {
        return helbide;
    }

    public void setHelbide(String helbide) {
        this.helbide = helbide;
    }

    public String getPostakodea() {
        return postakodea;
    }

    public void setPostakodea(String postakodea) {
        this.postakodea = postakodea;
    }

    public String getUdalerri() {
        return udalerri;
    }

    public void setUdalerri(String udalerri) {
        this.udalerri = udalerri;
    }

    public String getProbintzia() {
        return probintzia;
    }

    public void setProbintzia(String probintzia) {
        this.probintzia = probintzia;
    }

    public String getIdHerrialde() {
        return idHerrialde;
    }

    public void setIdHerrialde(String idHerrialde) {
        this.idHerrialde = idHerrialde;
    }

    @Override
    public String toString() {
        return "- id: " + id + ", helbide: " + helbide + ", postakodea: " + postakodea + ", udalerri: " + udalerri
                + ", probintzia: " + probintzia + ", idHerrialde: " + idHerrialde + "\n";
    }
    
    
}