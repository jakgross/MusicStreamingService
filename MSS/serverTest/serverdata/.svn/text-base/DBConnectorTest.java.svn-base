package serverdata;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class DBConnectorTest {

	
	
	DBConnector m_dbcon;
	 
	static Connection con;
	Statement statement ;
/*	 Normalerweise benötigt zum aufbauen
	@SuppressWarnings("static-access")
	@Before
	public void init(){
		m_dbcon =  new DBConnector();
		con = m_dbcon.getConnection();
		try {
			statement = con.createStatement();
			//statement.execute("Selct jjfj");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
*/
	@Before//ertmal DB gelöscht
	public void loeschen(){
		DBConnector.deleteDB();		
	}
	
//Statement DB aufbauen testen
/*	@Test
	public void testPlaylist(){
	
		try {
			statement.executeUpdate("INSERT INTO PLAYLISTS(playlistname,Clientlock,Serverlock) VALUES ( 'ARCHITECTURE', 'False','False')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
*/	
/*	
	@Test
	public void testGetConnection() {
		assertNull(DBConnector.getConnection());
	}
*/
	/**
	 * Versuch verbindung herzustellen
	 */
	@Test//Versuch verbindung herzustellen
	public void testConnect() {
		assertFalse(DBConnector.connect());
	}
/**
 * Verbindung herstellung - muss true sein
 */
	@Test//Verbindung herstellung - muss true sein
	public void testCreate() {
		
	 assertTrue(DBConnector.create());
			 
	}
/**
 * Versuch verbindung zu trennen 
 */
	@Test//Versuch verbindung zu trennen 
	public void testDisconnect() {
		DBConnector.disconnect();
		assertNull(DBConnector.connection);
	}
/**
 * Verbindung mit der DB herstellen - muss erfolgreich hergestellt werden
 */
	@Test//Verbindung mit der DB herstellen - muss erfolgreich hergestellt werden
	public void testConnectDB() {
		assertTrue(DBConnector.connectDB());
	}
//--------------------------------------------------
/*
	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}
*/
}
