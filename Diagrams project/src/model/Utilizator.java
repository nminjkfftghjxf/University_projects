package model;

public class Utilizator {
    private int idUtilizator;
    private String username;
    private String parola;

    // Constructor fără argumente
    public Utilizator() {}

    // Constructor cu argumente
    public Utilizator(int idUtilizator, String username, String parola) {
        this.idUtilizator = idUtilizator;
        this.username = username;
        this.parola = parola;
    }

    // Getters and setters
    public int getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

	@Override
	public String toString() {
		return "Utilizator [idUtilizator=" + idUtilizator + ", username=" + username + ", parola=" + parola + "]";
	}
    
    

}
