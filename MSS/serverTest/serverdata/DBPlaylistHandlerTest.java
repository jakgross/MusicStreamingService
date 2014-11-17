package serverdata;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import Common.Playlist;
import Common.Song;

public class DBPlaylistHandlerTest {

	DBBasisordnerHandler dbbh = new DBBasisordnerHandler();
	DBPlaylistHandler m_plh = new DBPlaylistHandler();

	/**
	 * erstmal löschen(leeren) der DB und Verbindung herstellen -
	 * einenBasisordner Speichern hier standard-verzeichnis mit Musik
	 */
	// erstmal löschen(leeren) der DB und Verbindung herstellen -
	// einenBasisordner Speichern
	// - hier standard-verzeichnis mit Musik
	public void testdel() {
		DBConnector.deleteDB();
		DBConnector.connectDB();
		try {
			dbbh.saveBasisordner("C:/Users/Public/Music/Sample Music");
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
	}

	public void init2() {
		testdel();
	}

	@Before
	public void init() throws UnsupportedTagException, InvalidDataException,
			IOException {
		// Löscht die Daten in der Daten Bank
		// Fügt daten ein und zwar immer die Gleichen

		// m_plh = new DBPlaylistHandler();

	}

	// @After
	//	
	// public void delete (){
	//		
	// m_plh.deletePlaylist(0);
	//		
	// }

	/*
	 * @Test public void testDBPlaylistHandler() { fail("Not yet implemented");
	 * }
	 */
	/**
	 * Suche getestet client
	 */
	@Test
	// Suche kann nicht richtig getestet werden - da durch momentanes datum
	// festgelegt - und da keine get und set Methode dafür
	// Umgehung mit Playlist erstellen und später mit der suche Vergleichen -
	// doch geklappt
	public void testSearchSongClient() throws UnsupportedTagException,
			InvalidDataException, IOException {
		testdel();
		// DBBasisordnerHandler.saveBasisordner("C:/Users/Public/Music/Sample Music");
		ArrayList<Song> songs = m_plh.getAllSongs();

		m_plh.savePlaylist(new Playlist("name", songs, false, false));

		ArrayList<Playlist> a = m_plh.getAllPlaylists();
		Assert.assertEquals(a, m_plh.searchSongClient("ka"));
	}

	/**
	 * Suche getestet Admin
	 */
	@Test
	// Selbe hier
	public void testSearchSongAdmin() { // searchSongClient kann verwendet
										// werden da es searchSong Admin benutzt

		testdel();
		ArrayList<Song> songs = m_plh.getAllSongs();

		m_plh.savePlaylist(new Playlist("name", songs, false, false));

		ArrayList<Playlist> a = m_plh.getAllPlaylists();
		Assert.assertEquals(a, m_plh.searchSongClient("ka"));

		/*
		 * DBPlaylistHandler.createPlaylist(); ArrayList<Song> songs =
		 * DBPlaylistHandler.getAllSongs(); for (Song s : songs) {
		 * System.out.println(s.getSongID()); } //
		 * DBPlaylistHandler.savePlaylistSongs(15, songs);
		 * 
		 * Assert.assertTrue(DBPlaylistHandler.searchSongAdmin("a"));
		 */
	}

	/*
	 * @Test public void testGetAllPlaylists() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetPlaylistSongs() { fail("Not yet implemented"); }
	 */
	/**
	 * Playlist erstellt - Playlist gelesen und getestet ob erstellt gleich
	 * gelesen
	 */
	@Test
	// Playlist erstellt - Playlist gelesen und getestet ob erstellt gleich
	// gelesen
	public void testOpenPlaylistString() {
		ArrayList<Song> songs = new ArrayList<Song>();
		m_plh.savePlaylist(new Playlist("abc", songs, false, false));
		ArrayList<Song> songsread = m_plh.openPlaylist("abc");
		Assert.assertEquals(songs, songsread);
	}

	/**
	 * Open Playlist mit int von OpenPlaylist mit string benutzt 2. test zeigen
	 * das nicht immer gleiche Playlist erstellt und öffnet
	 * 
	 * @throws SQLException
	 */
	@Test
	// Open Playlist mit int von OpenPlaylist mit string benutzt
	// 2. test zeigen das nicht immer gleiche Playlist erstellt und öffnet
	public void testOpenPlaylistInt() throws SQLException {

		Playlist pl = new Playlist("abc", new ArrayList<Song>(), false, false);
		m_plh.savePlaylist(pl);
		ArrayList<Playlist> playlists = m_plh.getAllPlaylists();
		int totestid = -1;

		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getPlaylistName().equals("abc")) {
				totestid = playlists.get(i).getPlaylistID();
			}
		}
		String nm = m_plh.openPlaylist(totestid).getPlaylistName();
		Assert.assertEquals("abc", nm);

		/*
		 * Playlist s ; try { DBPlaylistHandler.openPlaylist(0); } catch
		 * (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } Assert.assertSame(0, true);
		 */
	}

	/**
	 * Playlisten gespeichert hier 100 und geguckt ob playlistgröße vor
	 * erstellen = Playlistgröße nach erstellen - 100
	 */
	@Test
	// Playlisten gespeichert hier 100 und geguckt ob playlistgröße vor
	// erstellen = Playlistgröße
	// nach erstellen - 100
	public void testSavePlaylist() {
		int before = m_plh.getAllPlaylists().size();
		for (int i = 0; i < 100; i++) {
			m_plh.savePlaylist(new Playlist("pname" + i, new ArrayList<Song>(),
					false, false));
		}
		int after = m_plh.getAllPlaylists().size();
		// DBPlaylistHandler.savePlaylist(null);
		Assert.assertEquals(before, after - 100);
	}

	@Test
	public void testSavePlaylistSongs() throws UnsupportedTagException,
			InvalidDataException, IOException {

		ArrayList<Song> songs2save = m_plh.getAllSongs();
		m_plh.createPlaylist();
		m_plh.savePlaylist(new Playlist("name2", new ArrayList<Song>(), false,
				false));

		ArrayList<Playlist> playlists = m_plh.getAllPlaylists();
		int totestid = -1;

		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getPlaylistName().equals("abc")) {
				totestid = playlists.get(i).getPlaylistID();
			}
		}

		// int createdPlaylist = DBPlaylistHandler.getPlaylistID("name2");
		m_plh.savePlaylistSongs(totestid, songs2save);
		ArrayList<Song> savedPlaylistSongs = m_plh.getPlaylistSongs(totestid);
		Assert.assertEquals(savedPlaylistSongs.get(0).getSongID(), songs2save
				.get(0).getSongID());
		Assert.assertEquals(savedPlaylistSongs.get(1).getSongID(), songs2save
				.get(1).getSongID());
		Assert.assertEquals(savedPlaylistSongs.get(2).getSongID(), songs2save
				.get(2).getSongID());
	}

	/*
	 * @Test public void testGetAllSongs() { fail("Not yet implemented"); }
	 */
	@Test
	// Durch größe abgefragt ob zuvor erstellte playlist - gleich neu erstellte
	// Playlist anzahl
	public void testCreatePlaylist() {
		int playsizebefore = m_plh.getAllPlaylists().size();
		m_plh.createPlaylist();
		int playsizeafter = m_plh.getAllPlaylists().size();
		Assert.assertEquals(playsizebefore, playsizeafter - 1);
	}

	@Test
	// Wieder durch größe bzw menge abgefragt - nur hier gelöscht
	public void testDeletePlaylist() {
		int playsizebefore = m_plh.getAllPlaylists().size();
		m_plh.deletePlaylist(m_plh.getAllPlaylists().get(1).getPlaylistID());
		int playsizeafter = m_plh.getAllPlaylists().size();
		Assert.assertEquals(playsizebefore, playsizeafter + 1);
		// DBPlaylistHandler.createPlaylist();
		// ArrayList<Playlist> b = DBPlaylistHandler.getAllPlaylists();
		// System.out.println("Die ids sind:" + b.get(0).getPlaylistID()+
		// DBPlaylistHandler.getPlaylistID(b.get(0).getPlaylistName())); //geht
		// nicht da id private
		// DBPlaylistHandler.deletePlaylist(b.get(2).getPlaylistID());
		// b = DBPlaylistHandler.getAllPlaylists();
		// Assert.assertEquals(2 ,b.size() );

	}
	// --------------------------------------------
	/*
	 * @Test public void testObject() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetClass() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testHashCode() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testEquals() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testClone() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testToString() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testNotify() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testNotifyAll() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testWaitLong() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testWaitLongInt() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testWait() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testFinalize() { fail("Not yet implemented"); }
	 */
}
