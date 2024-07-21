package dao;

import model.Produs;
import util.ConexiuneBazaDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdusDAO implements BazaDateInterfata<Produs> {

    @Override
    public void adauga(Produs produs) {
        String sql = "INSERT INTO produs (idProdus, nume, categorie, pret, disponibil, descriere) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, produs.getIdProdus());
            stmt.setString(2, produs.getNume());
            stmt.setString(3, produs.getCategorie());
            stmt.setDouble(4, produs.getPret());
            stmt.setInt(5, produs.getDisponibil());
            stmt.setString(6, produs.getDescriere());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizeaza(Produs produs) {
        String sql = "UPDATE produs SET nume = ?, categorie = ?, pret = ?, disponibil = ?, descriere = ? WHERE idProdus = ?";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produs.getNume());
            stmt.setString(2, produs.getCategorie());
            stmt.setDouble(3, produs.getPret());
            stmt.setInt(4, produs.getDisponibil());
            stmt.setString(5, produs.getDescriere());
            stmt.setInt(6, produs.getIdProdus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge(int id) {
        String sql = "DELETE FROM produs WHERE idProdus = ?";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Produs obtine(int id) {
        String sql = "SELECT * FROM produs WHERE idProdus = ?";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Produs(
                    rs.getInt("idProdus"),
                    rs.getString("nume"),
                    rs.getString("categorie"),
                    rs.getDouble("pret"),
                    rs.getInt("disponibil"),
                    rs.getString("descriere")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Produs obtineProdusDupaNume(String nume) {
        String sql = "SELECT * FROM produs WHERE nume = ?";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nume);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Produs(
                    rs.getInt("idProdus"),
                    rs.getString("nume"),
                    rs.getString("categorie"),
                    rs.getDouble("pret"),
                    rs.getInt("disponibil"),
                    rs.getString("descriere")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Produs> obtineTot() {
        List<Produs> produse = new ArrayList<>();
        String sql = "SELECT * FROM produs";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                produse.add(new Produs(
                    rs.getInt("idProdus"),
                    rs.getString("nume"),
                    rs.getString("categorie"),
                    rs.getDouble("pret"),
                    rs.getInt("disponibil"),
                    rs.getString("descriere")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produse;
    }
    
    public List<Produs> obtineProduseCategorie(String categorie) {
        List<Produs> produse = new ArrayList<>();
        String sql = "SELECT * FROM produs WHERE categorie = ?";
        try (Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categorie);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produse.add(new Produs(
                    rs.getInt("idProdus"),
                    rs.getString("nume"),
                    rs.getString("categorie"),
                    rs.getDouble("pret"),
                    rs.getInt("disponibil"),
                    rs.getString("descriere")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produse;
    }
    
    public static void actualizareDisponibil(int idProdus, int nouaDisponibilitate) {
        String sql = "UPDATE Produs SET disponibil=? WHERE idProdus=?";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             PreparedStatement statement = conexiune.prepareStatement(sql)) {
            statement.setInt(1, nouaDisponibilitate);
            statement.setInt(2, idProdus);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Produs> obtineToateProdusele() {
        List<Produs> listaProduse = new ArrayList<>();
        String sql = "SELECT * FROM Produs";
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             Statement statement = conexiune.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idProdus");
                String nume = resultSet.getString("nume");
                String categorie = resultSet.getString("categorie");
                double pret = resultSet.getDouble("pret");
                int disponibil = resultSet.getInt("disponibil");
                String descriere = resultSet.getString("descriere");
                Produs produs = new Produs(id, nume, categorie, pret, disponibil, descriere);
                listaProduse.add(produs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProduse;
    }
    
    public void clearDatabase() {
    	String sql = "DELETE FROM produs";
    	try(Connection conn = ConexiuneBazaDate.obtineConexiune(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.executeUpdate();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
}
