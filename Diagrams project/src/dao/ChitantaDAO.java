package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Chitanta;
import util.ConexiuneBazaDate;

public class ChitantaDAO implements BazaDateInterfata<Chitanta> {

    @Override
    public void adauga(Chitanta chitanta) {
        String sql = "INSERT INTO chitanta (id_chitanta, numar_chitanta, suma, metoda_plata, id_comanda) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            	statement.setInt(1, chitanta.getIdChitanta());
            	statement.setString(2, chitanta.getNumarChitanta());
            	statement.setDouble(3, chitanta.getSuma());
            	statement.setString(4, chitanta.getMetodaPlata());
            	statement.setInt(5, chitanta.getIdComanda());
            	statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizeaza(Chitanta chitanta) {
        String sql = "UPDATE chitanta SET numar_chitanta=?, suma=?, metoda_plata=? WHERE id_chitanta=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
	        	statement.setString(1, chitanta.getNumarChitanta());
	        	statement.setDouble(2, chitanta.getSuma());
	        	statement.setString(3, chitanta.getMetodaPlata());
	        	statement.setInt(4, chitanta.getIdChitanta());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge(int id) {
        String sql = "DELETE FROM chitanta WHERE id_chitanta=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Chitanta obtine(int id) {
        String sql = "SELECT * FROM chitanta WHERE id_chitanta=?";
        Chitanta chitanta = null;
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                chitanta = new Chitanta();
                chitanta.setIdChitanta(result.getInt("id_chitanta"));
                chitanta.setNumarChitanta(result.getString("numar_chitanta"));
                chitanta.setSuma(result.getDouble("suma"));
                chitanta.setMetodaPlata(result.getString("metoda_plata"));
                chitanta.setIdComanda(result.getInt("id_comanda"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chitanta;
    }

    @Override
    public List<Chitanta> obtineTot() {
        String sql = "SELECT * FROM chitanta";
        List<Chitanta> chitante = new ArrayList<>();
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Chitanta chitanta = new Chitanta();
                chitanta.setIdChitanta(result.getInt("id_chitanta"));
                chitanta.setNumarChitanta(result.getString("numar_chitanta"));
                chitanta.setSuma(result.getDouble("suma"));
                chitanta.setMetodaPlata(result.getString("metoda_plata"));
                chitante.add(chitanta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chitante;
    }
    
    public void actualizareMetodaPlata(int idComanda, String metodaPlata) {
        String sql = "UPDATE chitanta SET metoda_plata = ? WHERE id_comanda = ?";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, metodaPlata);
            stmt.setInt(2, idComanda);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void adaugaChitanta(Chitanta chitanta) {
        String sql = "INSERT INTO chitanta (id_comanda, metoda_plata, suma, numar_chitanta) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chitanta.getIdComanda());
            stmt.setString(2, chitanta.getMetodaPlata());
            stmt.setDouble(3, chitanta.getSuma());
            stmt.setString(4, chitanta.getNumarChitanta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void clearDatabase() {
    	String sql = "DELETE FROM chitanta";
    	try(Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.executeUpdate();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
}
