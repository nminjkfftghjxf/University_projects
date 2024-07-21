package dao;

import model.Comanda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComandaDAOTest {

    private ComandaDAO comandaDAO;

    @BeforeEach
    public void setUp() {
        comandaDAO = new ComandaDAO();
        comandaDAO.clearDatabase();
    }

    @AfterEach
    public void tearDown() {
        comandaDAO.clearDatabase();
    }

    @Test
    public void testAdaugaComanda() {
        Comanda comanda = new Comanda(1, "Produse", "in asteptare", 150, 30);
        comandaDAO.adauga(comanda);
        Comanda result = comandaDAO.obtine(1);
        assertEquals(comanda.getProduse(), result.getProduse());
    }

    @Test
    public void testActualizeazaComanda() {
        Comanda comanda = new Comanda(1, "Produse", "in asteptare", 150, 30);
        comandaDAO.adauga(comanda);
        comandaDAO.actualizareStatus(1, "finalizata");
        Comanda result = comandaDAO.obtine(1);
        assertEquals("finalizata", result.getStatus());
    }

    @Test
    public void testStergeComanda() {
        Comanda comanda = new Comanda(1, "Produse", "in asteptare", 150, 30);
        comandaDAO.adauga(comanda);
        comandaDAO.sterge(1);
        Comanda result = comandaDAO.obtine(1);
        assertNull(result);
    }

    @Test
    public void testObtineComanda() {
    	Comanda comanda = new Comanda(1, "Produse", "in asteptare", 150, 30);
        comandaDAO.adauga(comanda);
        Comanda result = comandaDAO.obtine(1);
        assertNotNull(result);
    }

    @Test
    public void testObtineTot() {
    	Comanda comanda1 = new Comanda(1, "Produse1", "in asteptare", 150, 30);
    	Comanda comanda2 = new Comanda(2, "Produse2", "in preparare", 200, 40);
        comandaDAO.adauga(comanda1);
        comandaDAO.adauga(comanda2);
        List<Comanda> result = comandaDAO.obtineTot();
        assertEquals(2, result.size());
    }
}
