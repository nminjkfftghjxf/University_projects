package dao;

import model.Chitanta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.Assert.*;

public class ChitantaDAOTest {

    private ChitantaDAO chitantaDAO;

    @BeforeEach
    public void setUp() {
        chitantaDAO = new ChitantaDAO();
        chitantaDAO.clearDatabase();
    }
    
    @AfterEach
    public void tearDown() {
    	chitantaDAO.clearDatabase();
    }

    @Test
    public void testAdaugaChitanta() {
        Chitanta chitanta = new Chitanta(1, 1, "12345", 150.0, "Card");
        chitantaDAO.adauga(chitanta);
        Chitanta result = chitantaDAO.obtine(1);
        assertEquals(chitanta.getNumarChitanta(), result.getNumarChitanta());
    }

    @Test
    public void testActualizeazaChitanta() {
        Chitanta chitanta = new Chitanta(1, 1, "12345", 150.0, "Card");
        chitantaDAO.adauga(chitanta);
        chitanta.setSuma(200.0);
        chitantaDAO.actualizeaza(chitanta);
        Chitanta result = chitantaDAO.obtine(1);
        assertEquals(200.0, result.getSuma(), 0);
    }

    @Test
    public void testStergeChitanta() {
        Chitanta chitanta = new Chitanta(1, 1, "12345", 150.0, "Card");
        chitantaDAO.adauga(chitanta);
        chitantaDAO.sterge(1);
        Chitanta result = chitantaDAO.obtine(1);
        assertNull(result);
    }

    @Test
    public void testObtineChitanta() {
        Chitanta chitanta = new Chitanta(1, 1, "12345", 150.0, "Card");
        chitantaDAO.adauga(chitanta);
        Chitanta result = chitantaDAO.obtine(1);
        assertNotNull(result);
    }

    @Test
    public void testObtineTot() {
        Chitanta chitanta1 = new Chitanta(1, 1, "12345", 150.0, "Card");
        Chitanta chitanta2 = new Chitanta(2, 2, "67890", 300.0, "Cash");
        chitantaDAO.adauga(chitanta1);
        chitantaDAO.adauga(chitanta2);
        List<Chitanta> result = chitantaDAO.obtineTot();
        assertEquals(2, result.size());
    }
}
