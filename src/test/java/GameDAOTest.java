/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.gamespotstoremanager.GameDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.lang.IllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ebwhi
 */
public class GameDAOTest {
    
    @Test
    void idToESRBIsCorrect() {
        assertThrows(IllegalArgumentException.class, () -> {
            GameDAO.getEsrbFromId(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GameDAO.getEsrbFromId(6);
        });
        assertTrue(GameDAO.getEsrbFromId(1).equals("NR"));
        assertTrue(GameDAO.getEsrbFromId(2).equals("E"));
        assertTrue(GameDAO.getEsrbFromId(3).equals("E10+"));
        assertTrue(GameDAO.getEsrbFromId(4).equals("T"));
        assertTrue(GameDAO.getEsrbFromId(5).equals("M"));
    }
    
}
