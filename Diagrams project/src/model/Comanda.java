package model;

public class Comanda {
    private int idComanda;
    private int timpEstimativ;
    private String status;
    private String produse;
    private double suma;

    // Constructor fără argumente
    public Comanda() {}

    // Constructor cu argumente
    public Comanda(int idComanda, String produse, String status, int timpEstimativ, double suma) {
        this.idComanda = idComanda;
        this.produse = produse;
        this.status = status;
        this.timpEstimativ = timpEstimativ;
        this.suma = suma;
    }

	public int getIdComanda() {
		return idComanda;
	}

	public void setIdComanda(int idComanda) {
		this.idComanda = idComanda;
	}

	public int getTimpEstimativ() {
		return timpEstimativ;
	}

	public void setTimpEstimativ(int timpEstimativ) {
		this.timpEstimativ = timpEstimativ;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProduse() {
		return produse;
	}

	public void setProduse(String produse) {
		this.produse = produse;
	}

	public double getSuma() {
		return suma;
	}

	public void setSuma(double suma) {
		this.suma = suma;
	}

	@Override
	public String toString() {
		return idComanda + ", timpEstimativ=" + timpEstimativ + ", status=" + status
				+ ", produse=" + produse + ", " + suma;
	}

    
}
