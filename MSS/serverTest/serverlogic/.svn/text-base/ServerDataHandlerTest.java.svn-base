package serverlogic;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import serverdata.DBBasisordnerHandler;
import serverdata.DBConnector;
import serverdata.DBPlaylistHandler;


public class ServerDataHandlerTest {
	ServerDataHandler dh;
	
	@Before
	public void init(){
		DBConnector.deleteDB();
		DBConnector dbc = new DBConnector();
		dbc.connectDB();
		dh = new ServerDataHandler();
	
		
	}
/*
	@Test
	public void testServerDataHandler() {
		fail("Not yet implemented");
	}
*/
	/**
	 * Funktion ruft nur DBPlaylistHandler.createPlaylist() auf 
	 */
  //Funktion ruft nur DBPlaylistHandler.createPlaylist() auf 
	@Test  //Playlist Date als Name aber so nicht abfragbar - aber durch size in db abfragbar
	public void testCreatePlaylist() {
		DBPlaylistHandler dbph = new DBPlaylistHandler();
		
//		dh.createPlaylist();
		ServerDataHandler sdh = new ServerDataHandler();
//		Assert.assertNotNull(DBPlaylistHandler.getPlaylistID());
		sdh.createPlaylist();
		int playsizebefore = dbph.getAllPlaylists().size();
		
		sdh.createPlaylist();
//		DBPlaylistHandler.createPlaylist();
		int playsizeafter = dbph.getAllPlaylists().size();
		Assert.assertEquals(playsizebefore,playsizeafter-1);
		
	}

/*
	@Test //Hier nur weitergegeben Methode
	public void testGchangeBasisordner() {
		fail("Not yet implemented");
	}

	@Test
	public void testGaddSong() {
		fail("Not yet implemented");
	}

	@Test
	public void testGsaveBasisordner() {
		fail("Not yet implemented");
	}

	@Test
	public void testGremoveSong() {
		fail("Not yet implemented");
	}

	@Test
	public void testGsearchSongAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testGrefreshBasisordner() {
		fail("Not yet implemented");
	}

	@Test
	public void testGsavePlaylist() {
		fail("Not yet implemented");
	}

	@Test
	public void testGdeletePlaylist() {
		fail("Not yet implemented");
	}

	@Test
	public void testGeditPlaylist() {
		fail("Not yet implemented");
	}

	@Test
	public void testGgetBasisordner() {
		fail("Not yet implemented");
	}
*/
//-------------------------------------------
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
