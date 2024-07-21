package model;


public class Chitanta {
    private int idChitanta;
    private int idComanda;
    private String numarChitanta;
    private double suma;
    private String metodaPlata;

    // Constructor fără argumente
    public Chitanta() {}

    // Constructor cu argumente
    public Chitanta(int idChitanta, int idComanda, String numarChitanta, double suma, String metodaPlata) {
        this.idChitanta = idChitanta;
        this.idComanda = idComanda;
        this.numarChitanta = numarChitanta;
        this.suma = suma;
        this.metodaPlata = metodaPlata;
    }

    // Getters and setters
    public int getIdChitanta() {
        return idChitanta;
    }

	public int getIdComanda() {
		return idComanda;
	}

	public void setIdComanda(int idComanda) {
		this.idComanda = idComanda;
	}

	public void setIdChitanta(int idChitanta) {
        this.idChitanta = idChitanta;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public String getMetodaPlata() {
        return metodaPlata;
    }

    public void setMetodaPlata(String metodaPlata) {
        this.metodaPlata = metodaPlata;
    }

	public String getNumarChitanta() {
		return numarChitanta;
	}

	public void setNumarChitanta(String numarChitanta) {
		this.numarChitanta = numarChitanta;
	}
	
	@Override
	public String toString() {
		return "Chitanta [idChitanta=" + idChitanta + ", idComanda=" + idComanda + ", numarChitanta=" + numarChitanta
				+ ", suma=" + suma + ", metodaPlata=" + metodaPlata + "]";
	}
}
