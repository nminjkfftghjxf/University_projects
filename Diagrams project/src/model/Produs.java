package model;

public class Produs {
    private int idProdus;
    private String nume;
    private String categorie;
    private double pret;
    private int disponibil;
    private String descriere;

    public Produs() {}

    public Produs(int idProdus, String nume, String categorie, double pret, int disponibil, String descriere) {
        this.idProdus = idProdus;
        this.nume = nume;
        this.categorie = categorie;
        this.pret = pret;
        this.disponibil = disponibil;
        this.setDescriere(descriere);
    }

    // Getters and setters
    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getDisponibil() {
        return disponibil;
    }

    public void setDisponibil(int disponibil) {
        this.disponibil = disponibil;
    }

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}

	@Override
	public String toString() {
		return "Produs [idProdus=" + idProdus + ", nume=" + nume + ", categorie=" + categorie + ", pret=" + pret
				+ ", disponibil=" + disponibil + ", descriere=" + descriere + "]";
	}
	
	
}
