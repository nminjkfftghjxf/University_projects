package dao;

import model.Utilizator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtilizatorDAOTest {

    private UtilizatorDAO utilizatorDAO;

    @BeforeEach
    public void setUp() {
        utilizatorDAO = new UtilizatorDAO();
        // Assuming there's a method to clear the database for testing
        utilizatorDAO.clearDatabase();
    }

    @AfterEach
    public void tearDown() {
        utilizatorDAO.clearDatabase();
    }

    @Test
    public void testAdaugaUtilizator() {
        Utilizator utilizator = new Utilizator(1, "username1", "password1");
        utilizatorDAO.adauga(utilizator);
        Utilizator result = utilizatorDAO.obtine(1);
        assertEquals(utilizator.getUsername(), result.getUsername());
    }

    @Test
    public void testActualizeazaUtilizator() {
        Utilizator utilizator = new Utilizator(1, "username1", "password1");
        utilizatorDAO.adauga(utilizator);
        utilizator.setParola("newpassword");
        utilizatorDAO.actualizeaza(utilizator);
        Utilizator result = utilizatorDAO.obtine(1);
        assertEquals("newpassword", result.getParola());
    }

    @Test
    public void testStergeUtilizator() {
        Utilizator utilizator = new Utilizator(1, "username1", "password1");
        utilizatorDAO.adauga(utilizator);
        utilizatorDAO.sterge(1);
        Utilizator result = utilizatorDAO.obtine(1);
        assertNull(result);
    }

    @Test
    public void testObtineUtilizator() {
        Utilizator utilizator = new Utilizator(1, "username1", "password1");
        utilizatorDAO.adauga(utilizator);
        Utilizator result = utilizatorDAO.obtine(1);
        assertNotNull(result);
    }

    @Test
    public void testObtineTot() {
        Utilizator utilizator1 = new Utilizator(1, "username1", "password1");
        Utilizator utilizator2 = new Utilizator(2, "username2", "password2");
        utilizatorDAO.adauga(utilizator1);
        utilizatorDAO.adauga(utilizator2);
        List<Utilizator> result = utilizatorDAO.obtineTot();
        assertEquals(2, result.size());
    }
}
