/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.gamespotstoremanager.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ebwhi
 */
public class DatabaseConnectorTest {
    
    @Test
    void connectionShouldNotBeNull() {
        try {
            Connection c = DatabaseConnector.getConnection();
            assertNotNull(c);
        } catch (SQLException e) {
            System.out.println("");
        }
    }
    
}
