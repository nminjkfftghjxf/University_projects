package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Utilizator;
import util.ConexiuneBazaDate;

public class UtilizatorDAO implements BazaDateInterfata<Utilizator> {

    @Override
    public void adauga(Utilizator utilizator) {
        String sql = "INSERT INTO Utilizator (id_utilizator, username, parola) VALUES (?, ?, ?)";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
        	statement.setInt(1, utilizator.getIdUtilizator());
            statement.setString(2, utilizator.getUsername());
            statement.setString(3, utilizator.getParola());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru actualizarea unui utilizator existent
    @Override
    public void actualizeaza(Utilizator utilizator) {
        String sql = "UPDATE Utilizator SET parola=? WHERE id_utilizator=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setString(1, utilizator.getParola());
            statement.setInt(2, utilizator.getIdUtilizator());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru ștergerea unui utilizator existent
    @Override
    public void sterge(int id) {
        String sql = "DELETE FROM Utilizator WHERE id_utilizator=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru obținerea unui utilizator după id
    @Override
    public Utilizator obtine(int id) {
        String sql = "SELECT * FROM Utilizator WHERE id_utilizator=?";
        Utilizator utilizator = null;
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                utilizator = new Utilizator();
                utilizator.setIdUtilizator(result.getInt("id_utilizator"));
                utilizator.setUsername(result.getString("username"));
                utilizator.setParola(result.getString("parola"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilizator;
    }

    // Metoda pentru obținerea tuturor utilizatorilor
    @Override
    public List<Utilizator> obtineTot() {
        String sql = "SELECT * FROM Utilizator";
        List<Utilizator> utilizatori = new ArrayList<>();
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Utilizator utilizator = new Utilizator();
                utilizator.setIdUtilizator(result.getInt("id_utilizator"));
                utilizator.setUsername(result.getString("username"));
                utilizator.setParola(result.getString("parola"));
                utilizatori.add(utilizator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilizatori;
    }
    
    public Utilizator obtineUtilizatorDupaUsername(String username) {
        String sql = "SELECT * FROM Utilizator WHERE username=?";
        Utilizator utilizator = null;
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                utilizator = new Utilizator();
                utilizator.setIdUtilizator(result.getInt("id_utilizator"));
                utilizator.setUsername(result.getString("username"));
                utilizator.setParola(result.getString("parola"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilizator;
    }
    
    public void clearDatabase() {
    	String sql = "DELETE FROM utilizator";
    	try(Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.executeUpdate();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
}
