package serverdata;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
//Nicht benötigt da Methoden nur weiter übergeben
public class DataManagerTest {

//Muss nicht getestet werden	
	DataManager dm ;
	DBBasisordnerHandler dbh;
	
	@Test
	public void testChangeBasisordner() throws UnsupportedTagException, InvalidDataException, IOException {
	//	datamanager = new DataManager();
	//	datamanager.changeBasisordner();
		dbh.saveBasisordner("basisordner");
		Assert.assertEquals(dbh.changeBasisordner("basisordner"),dm.changeBasisordner("basisordner"));
	}
/*
	@Test
	public void testSaveBasisordner() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testCheckBasisordner() {
		
		
		boolean a = dbh.checkBasisordner();
		boolean b = dm.checkBasisordner();
		
		Assert.assertEquals( a, b);
		
	}

	@Test
	public void testLocateSongsi() {
		
		 
		
	}
/*
	@Test
	public void testGetSongs() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testRefreshBasisordner() {
		fail("Not yet implemented");
	}
/*
	@Test
	public void testGetBasisordner() {
		fail("Not yet implemented");
	}
*/
/*
	@Test
	public void testSearchSongClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchSongAdmin() {
		fail("Not yet implemented");
	}
*/
/*
	@Test
	public void testGetAllPlaylists() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPlaylistSongs() {
		fail("Not yet implemented");
	}
*/
/*
	@Test
	public void testOpenPlaylistString() {
		fail("Not yet implemented");
	}

	@Test
	public void testOpenPlaylistInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSavePlaylist() {
		fail("Not yet implemented");
	}

	@Test
	public void testSavePlaylistSongs() {
		fail("Not yet implemented");
	}
*/
/*
	@Test
	public void testGetAllSongs() {
		fail("Not yet implemented");
	}
*/
/*
	@Test
	public void testCreatePlaylist() {
	//	Playlist rock ;
	//	DataManger.CreatePlaylist(rock);
	//	Assert.assertEquals(rock,DataManager.CreatePlaylist(rock));
	}

	@Test
	public void testDeletePlaylist() {
		fail("Not yet implemented");
	}
*/
//---------------------------------------------
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
