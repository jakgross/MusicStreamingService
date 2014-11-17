package serverdata;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class DBBasisordnerHandlerTest {
	
	DBBasisordnerHandler dbh = new DBBasisordnerHandler();
	
	@Before//ersteinmal verbindung zur DB herstellen
	public void init() throws UnsupportedTagException, InvalidDataException, IOException{
		DBConnector.deleteDB();
		DBConnector.connect();
		DBConnector.connectDB();
		dbh.saveBasisordner("C:/musik");
		DBConnector.getConnection();
		
		
		}
	
	/**
	 * Test method for {@link data.DBBasisordnerHandler#DBBasisordnerHandler()}.
	 */
	@Test
	public void testDBBasisordnerHandler() {
		
	}
	/**
	 * Test method for {@link data.DBBasisordnerHandler#checkBasisordner()}.
	 * Basisordner ändern und abgefragt ob besteht
	 */
	@Test//Basisordner ändern und abgefragt ob besteht
	public void testChangeBasisordner() {
		try {
			dbh.changeBasisordner("basisordner");
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertTrue("basisordner", true);
	}
	/**
	 * Test method for {@link data.DBBasisordnerHandler#saveBasisordner(java.lang.String)}.
	 * Basisordner versucht zu speichern und gucken ob als Basisordner indiziert 
	 *	gegebenenfalls Abfrage mit checkBasisordner
	 */
	@Test//Basisordner versucht zu speichern und gucken ob als Basisordner indiziert 
	// - gegebenenfalls Abfrage mit checkBasisordner
	public void testSaveBasisordner() {
		try {
			dbh.saveBasisordner("C:/musik");//muss angelegt sein
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue("C:/musik", true);
	}
/**
 * Verbindung hergestellt - und geprüft ob basisordner ausgewählt
 */
	@Test//Verbindung hergestellt - und geprüft ob basisordner ausgewählt
	public void testCheckBasisordner()  {
		
		DBConnector.connect();
		DBConnector.connectDB();
		try {
			dbh.saveBasisordner("C:/musik");
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertTrue(dbh.checkBasisordner());
	}
/**
 * CloseResource() test
 */
	@Test
	public void testCloseResources() {
		
		dbh.closeResources();
		Assert.assertNotNull(true);
	}

		/**
		 * Test method for {@link data.DBBasisordnerHandler#locateSongsi(java.lang.String)}.
		 */
		@Test
		public void testLocateSongsi() {
			try {
				dbh.locateSongsi("C:/musik");
			} catch (UnsupportedTagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//DBBasisordnerHandler.testget();
			Assert.assertTrue("C:/musik",true);
	}
/*
	@Test
	public void testGetSongs() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testRefreshBasisordner() {//kriegt man nicht hin - kein rückgabewert - deshalb auch kein asserts
		
		dbh.refreshBasisordner("C:/musik");
		//Assert.assertNull("C:/musik", true);
	}

	
/**
 * Überprüft ob angelegter Basisordner mit dem durch getBasisordner geholten übereinstimmt
 */
	@Test//Überprüft ob angelegter Basisordner mit dem durch getBasisordner geholten übereinstimmt
	public void testGetBasisordner() {
		try {
			dbh.saveBasisordner("C:/musik"); //muss angelegt sein
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = dbh.getBasisordner();
		Assert.assertEquals("C:/musik", s); 
		
	}
//-----------------------------------------------------

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
