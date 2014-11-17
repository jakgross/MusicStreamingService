package serverlogic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import serverdata.DBConnector;
import serverdata.DBPlaylistHandler;

import Common.Playlist;
import Common.Song;

public class ServerFacadeTest {

	ServerFacade sf;
	DBPlaylistHandler dbph = new DBPlaylistHandler();
	@Before
	public void init(){
		
	 
	try {
		sf = new ServerFacade();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
		
	}
/*	
	@Test
	public void testServerFacade() {
		fail("Not yet implemented");
	}
*/
/*
	@Test
	public void testGetClientByOrt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPlaylistBySong() {
		fail("Not yet implemented");
	}
*/
/*
	@Test
	public void testClose() {
		
		sf.close(); 
		Assert.assertNull(sf.getClientList());
		
	}
*/  
	/**
	 * Wenn echter Ort angegeben True - bei falschem Ort oder nichts False
	 */
	@Test//Wenn echter Ort angegeben True - bei falschem Ort oder nichts False
	public void testCheckOrt() throws RemoteException {
		ServerFacade sf = new ServerFacade();
		Assert.assertTrue(sf.checkOrt("Wohnzimmer"));
		Assert.assertFalse(sf.checkOrt(null));
	}
/*
	@Test
	public void testGetClientList() {
		fail("Not yet implemented");
	}
*/
	/**
	 * True zurück wenn richtiger ort angegeben ist - False unter anderem wenn facade = null
	 */
	@Test// True zurück wenn richtiger ort angegeben ist - False unter anderem wenn facade = null
	public void testLogin() throws RemoteException {
		
		Assert.assertFalse(sf.login("127.0.0.1", "Wohnzimmer"));
		try {//Versuchen sich anzumelden "192.168.0.1" - bekomme die Liste zurück
			
			sf.login("192.168.0.1", "Wohnzimmer");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(sf.getClientList());
	}
/**
 * False unter anderem wenn client = null
 * @throws IOException
 */
	@Test//  False unter anderem wenn client = null
	public void testLogoutClient() throws IOException {
		Assert.assertFalse(sf.logoutClient("Wohnzimmer"));
		//Versuchen sich anzumelden "192.168.0.1" - bekomme die Liste zurück
		try {
			sf.login("127.0.0.1", "Wohnzimmer");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(sf.getClientList());
		//Versuchen sich abzumelden "Wohnzimmer" - bekomme die Liste zurück
		//Hier nur Ort angegeben
		try {
			sf.logoutClient("Wohnzimmer");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNull(sf.getClientByOrt("Wohnzimmer"));
	}

	@Test
	public void testStopSynchronisation() {//Noch nicht geschrieben
		fail("Not yet implemented");
	}
/*
	@Test
	public void testGetAllPlaylists() {
		fail("Not yet implemented");
	}
*/
	/**
	 * Server Facade erstellt - Playliste erstellt - Playliste mit open Playlist geöffnet
	 * Inhalt der des Songsarrays = inhalt der geöffneten Playliste
	 */
	@Test//Server Facade erstellt - Playliste erstellt - Playliste mit open Playlist geöffnet
		//Inhalt der des Songsarrays = inhalt der geöffneten Playliste
	public void testOpenPlaylist() throws RemoteException {
		ServerFacade sf = new ServerFacade();
		ArrayList<Song> songs = new ArrayList<Song>();
		Playlist a = new Playlist("abc", songs, false, false);
	//	dbph.createPlaylist();
	//	dbph.savePlaylist(new Playlist("abc", songs, false, false));
	//	ArrayList<Song> songsread =  sf.openPlaylist(a);
		sf.openPlaylist(a, "Wohnzimmer");
		
		Assert.assertNotNull(sf.openPlaylist(a, "Küche"));
		
		
		
	}
/**
 * Wenn suche leer - getAllPlaylists - wenn was drin search SongClient
 * @throws RemoteException
 */
	@Test//Wenn suche leer - getAllPlaylists - wenn was drin search SongClient
	public void testSearchSongClient() throws RemoteException {
//		DBConnector.deleteDB();
//		DBConnector.connectDB();
		ServerFacade sf = new ServerFacade();
/* 		Playlist as = new Playlist(0);
		ArrayList<Song> songs = DBPlaylistHandler.getAllSongs();
		
		DBPlaylistHandler.savePlaylist(new Playlist("name", songs, false, false));
		
		ArrayList<Playlist> a = DBPlaylistHandler.getAllPlaylists();
		Assert.assertEquals(a, sf.searchSongClient("name"));
*/		
		Assert.assertEquals(dbph.getAllPlaylists(), sf.searchSongClient(""));
		Assert.assertEquals(dbph.searchSongClient("song a"), sf.searchSongClient("song a"));
		
	}
/**
 * Bei unterschiedlichen angaben true - bei verschiedenen False
 * @throws RemoteException
 */
	@Test//Bei unterschiedlichen angaben true - bei verschiedenen False
	public void testChangeOrt() throws RemoteException {
		ServerFacade sf = new ServerFacade();
		
		Assert.assertFalse(sf.changeOrt("a", "b"));
		Assert.assertTrue(sf.changeOrt("a", "a"));
		
	}

/*	@Test
	public void testPlay() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrev() {
		fail("Not yet implemented");
	}

	@Test
	public void testForward() {
		fail("Not yet implemented");
	}
*/
	
	
	
	@Test
	public void testSynchronize() {//Noch nicht geschrieben
		fail("Not yet implemented");
	}
	
//-------------------------------------------------------------
/*
	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnicastRemoteObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnicastRemoteObjectInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnicastRemoteObjectIntRMIClientSocketFactoryRMIServerSocketFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportObjectRemote() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportObjectRemoteInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportObjectRemoteIntRMIClientSocketFactoryRMIServerSocketFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnexportObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoteServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoteServerRemoteRef() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClientHost() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLog() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLog() {
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
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoteObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoteObjectRemoteRef() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRef() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStub() {
		fail("Not yet implemented");
	}
*/
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
