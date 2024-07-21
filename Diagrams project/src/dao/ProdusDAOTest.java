package dao;

import model.Produs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProdusDAOTest {

    private ProdusDAO produsDAO;

    @BeforeEach
    public void setUp() {
        produsDAO = new ProdusDAO();
        produsDAO.clearDatabase();
    }

    @AfterEach
    public void tearDown() {
        produsDAO.clearDatabase();
    }

    @Test
    public void testAdaugaProdus() {
        Produs produs = new Produs(1, "Produs1", "Categorie1", 50.0, 10, "Descriere1");
        produsDAO.adauga(produs);
        Produs result = produsDAO.obtine(1);
        assertEquals(produs.getNume(), result.getNume());
    }

    @Test
    public void testActualizeazaProdus() {
        Produs produs = new Produs(1, "Produs1", "Categorie1", 50.0, 10, "Descriere1");
        produsDAO.adauga(produs);
        produs.setPret(60.0);
        produsDAO.actualizeaza(produs);
        Produs result = produsDAO.obtine(1);
        assertEquals(60.0, result.getPret());
    }

    @Test
    public void testStergeProdus() {
        Produs produs = new Produs(1, "Produs1", "Categorie1", 50.0, 10, "Descriere1");
        produsDAO.adauga(produs);
        produsDAO.sterge(1);
        Produs result = produsDAO.obtine(1);
        assertNull(result);
    }

    @Test
    public void testObtineProdus() {
        Produs produs = new Produs(1, "Produs1", "Categorie1", 50.0, 10, "Descriere1");
        produsDAO.adauga(produs);
        Produs result = produsDAO.obtine(1);
        assertNotNull(result);
    }

    @Test
    public void testObtineTot() {
        Produs produs1 = new Produs(1, "Produs1", "Categorie1", 50.0, 10, "Descriere1");
        Produs produs2 = new Produs(2, "Produs2", "Categorie2", 100.0, 20, "Descriere2");
        produsDAO.adauga(produs1);
        produsDAO.adauga(produs2);
        List<Produs> result = produsDAO.obtineTot();
        assertEquals(2, result.size());
    }
}
