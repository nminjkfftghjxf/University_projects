package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Comanda;
import util.ConexiuneBazaDate;

public class ComandaDAO implements BazaDateInterfata<Comanda> {

    @Override
    public void adauga(Comanda comanda) {
        String sql = "INSERT INTO Comanda (id_comanda, produse, suma, status) VALUES (?, ?, ?, ?)";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
        	statement.setInt(1, comanda.getIdComanda());
            statement.setString(2, comanda.getProduse());
            statement.setDouble(3, comanda.getSuma());
            statement.setString(4, "in asteptare");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizeaza(Comanda comanda) {
        String sql = "UPDATE Comanda SET status=?, timp_estimat_finalizare=? WHERE id_comanda=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setString(1, comanda.getStatus());
            statement.setInt(2, comanda.getTimpEstimativ());
            statement.setInt(3, comanda.getIdComanda());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizareStatus(int id, String status) {
        String sql = "UPDATE Comanda SET status=? WHERE id_comanda=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge(int id) {
        String sql = "DELETE FROM Comanda WHERE id_comanda=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Comanda obtine(int id) {
        String sql = "SELECT * FROM comanda WHERE id_comanda=?";
        Comanda comanda = null;
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                comanda = new Comanda();
                comanda.setIdComanda(result.getInt("id_comanda"));
                comanda.setProduse(result.getString("produse"));
                comanda.setSuma(result.getDouble("suma"));
                comanda.setStatus(result.getString("status"));
                comanda.setTimpEstimativ(result.getInt("timp_estimat_finalizare"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comanda;
    }

    @Override
    public List<Comanda> obtineTot() {
        String sql = "SELECT * FROM Comanda";
        List<Comanda> comenzi = new ArrayList<>();
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Comanda comanda = new Comanda();
                comanda.setIdComanda(result.getInt("id_comanda"));
                comanda.setProduse(result.getString("produse"));
                comanda.setSuma(result.getDouble("suma"));
                comanda.setStatus(result.getString("status"));
                comanda.setTimpEstimativ(result.getInt("timp_estimat_finalizare"));
                comenzi.add(comanda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comenzi;
    }
    
    public void actualizareTimpEstimativ(int idComanda, int timpEstimativ) {
        String sql = "UPDATE Comanda SET timp_estimat_finalizare=? WHERE id_comanda=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setInt(1, timpEstimativ);
            statement.setInt(2, idComanda);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void clearDatabase() {
    	String sql = "DELETE FROM comanda";
    	try(Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.executeUpdate();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
}
