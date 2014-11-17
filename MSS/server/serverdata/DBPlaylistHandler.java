package serverdata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import Common.Playlist;
import Common.Song;

/**
 * Diese Klasse k�mmert sich um die Verwaltung der Playlisten und deren Songs
 * 
 * @author teamJES
 */
public class DBPlaylistHandler {

	private static Statement statement;
	private static ResultSet resultSet;
	static String message = "Datenbankfehler. Die Fehlermeldung lautet: ";

	public DBPlaylistHandler() {
		new DBConnector();
	}

	/**
	 * Gibt alle Playlisten zur�ck, die Songs enthalten, welche die eingegebenen
	 * Suchbegriffe enthalten
	 * 
	 * @param inputSearch
	 * @return ArrayList<Playlist>
	 */
	public ArrayList<Playlist> searchSongClient(String inputSearch) {

		ArrayList<Song> songs = searchSongAdmin(inputSearch);
		ArrayList<Playlist> playlists = new ArrayList<Playlist>();
		ArrayList<Playlist> allPlaylists = getAllPlaylists();
		for (Playlist playlist : allPlaylists) {
			for (Song song : songs) {
				if (playlist.getSongs().contains(song)) {
					playlists.add(playlist);
					break;
				}
			}
		}
		return playlists;
	}

	/**
	 * Gibt alle Songs, die den Suchbegriff inputSearch enthalten als ArrayListe
	 * zur�ck.
	 * 
	 * @param inputSearch
	 *            String, zu suchendes (Teil-)Wort
	 * @return ArrayList<Song> alle Lieder, die den Suchbegriff enthalten
	 */
	public ArrayList<Song> searchSongAdmin(String inputSearch) {
		ArrayList<Song> songs = new ArrayList<Song>();

		String allplaylists = "SELECT * FROM SONGS WHERE "
				+ "UPPER(filename) LIKE UPPER('%" + inputSearch + "%') OR "
				+ "UPPER(interpret) LIKE UPPER('%" + inputSearch + "%') OR "
				+ "UPPER(id3titel) LIKE UPPER('%" + inputSearch + "%') OR "
				+ "UPPER(id3album) LIKE UPPER('%" + inputSearch + "%') OR "
				+ "UPPER(id3genre) LIKE UPPER('%" + inputSearch + "%')";
		try {
			statement = DBConnector.getConnection().createStatement();
			resultSet = statement.executeQuery(allplaylists);
			while (resultSet.next()) {

				Song song = new Song(resultSet.getInt(1), resultSet
						.getString(2), resultSet.getString(3), resultSet
						.getString(4), resultSet.getString(5), resultSet
						.getString(6), resultSet.getString(7));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Song>();
		} finally {
			closeResources();
		}
		return songs;
	}

	/**
	 * Gibt alle Playlisten aus der Datenbank zur�ck
	 * 
	 * @return ArrayList<Playlist>
	 */
	public ArrayList<Playlist> getAllPlaylists() {
		ArrayList<Playlist> playlists = new ArrayList<Playlist>();
		String allplaylists = "SELECT * FROM PLAYLISTS";
		statement = null;
		try {
			DBConnector.getConnection().setAutoCommit(false);
			statement = DBConnector.getConnection().createStatement();

			ResultSet resultSet2 = statement.executeQuery(allplaylists);

			while (resultSet2.next()) {

				int playlistid = resultSet2.getInt(1);
				String playlistName = resultSet2.getString(2);
				boolean clientLock = resultSet2.getBoolean(3);
				boolean serverLock = resultSet2.getBoolean(4);

				ArrayList<Song> songs = getPlaylistSongs(playlistid);
				Playlist playlist = new Playlist(playlistid, playlistName,
						songs, clientLock, serverLock);
				playlists.add(playlist);

			}
			DBConnector.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Playlist>();
		} finally {
			closeResources();
		}

		return playlists;
	}

	/**
	 * Gibt alle zu einer Playliste (ID) geh�renden Songs zur�ck
	 * 
	 * @param playlistid
	 *            ID der Playliste
	 * @return ArrayList<Song> Liste mit allen Liedern, die zur Playliste
	 *         geh�ren
	 */
	public ArrayList<Song> getPlaylistSongs(int playlistid) {
		ArrayList<Song> songs = new ArrayList<Song>();

		String allplaylists = "SELECT SONGS.songid, filename, songpath, interpret, id3titel, "
				+ "id3album, id3genre FROM SONGS, PLAYLIST2SONG "
				+ "WHERE (SONGS.songid = PLAYLIST2SONG.songid "
				+ "AND PLAYLIST2SONG.playlistid = " + playlistid + ")";
		try {
			statement = DBConnector.getConnection().createStatement();
			resultSet = statement.executeQuery(allplaylists);
			while (resultSet.next()) {
				Song song = new Song(resultSet.getInt(1), resultSet
						.getString(2), resultSet.getString(3), resultSet
						.getString(4), resultSet.getString(5), resultSet
						.getString(6), resultSet.getString(7));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Song>();
		} finally {
			closeResources();
		}
		return songs;
	}

	/**
	 * schlie�t alle verwendeten Statements wieder
	 */
	private void closeResources() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException ex) {
				System.out.println(message + ex.getMessage());
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
				System.out.println(message + ex.getMessage());
			}
		}
	}

	/**
	 * Gibt alle zu einer Playliste (Playlist-Name) zugeh�rigen Songs zur�ck
	 * 
	 * @param playlistname
	 *            Name der Playliste
	 * @return ArrayList<Song> alle Lieder, die zur Playliste geh�ren
	 */
	public ArrayList<Song> openPlaylist(String playlistname) {

		String allplaylists = "SELECT playlistid FROM PLAYLISTS "
				+ "WHERE playlistname = '" + playlistname + "'";
		try {
			statement = DBConnector.getConnection().createStatement();
			resultSet = statement.executeQuery(allplaylists);
			while (resultSet.next()) {
				int playlistid = resultSet.getInt(1);

				if (playlistid == -1) {
					return new ArrayList<Song>();
				} else {
					return getPlaylistSongs(playlistid);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Song>();
		} finally {
			closeResources();
		}
		return new ArrayList<Song>();
	}

	/**
	 * �ffnet eine Playliste durch ihre ID gibt Playliste als Objekt zur�ck
	 * 
	 * @param playlistID ID der Playliste
	 * @return playlist Playlist-Objekt {@link Common.Playlist#Playlist(int, String, ArrayList, boolean, boolean)}
	 *         zu der ID
	 * @throws SQLException
	 */
	public Playlist openPlaylist(int playlistID) throws SQLException {
		int playlistid = playlistID;
		String playlistName = null;
		ArrayList<Song> songs = null;
		boolean clientLock = false;
		boolean serverLock = false;
		String allplaylists = "SELECT * FROM PLAYLISTS "
				+ "WHERE playlistid = " + playlistID;
		try {
			DBConnector.getConnection().setAutoCommit(false);
			statement = DBConnector.getConnection().createStatement();
			ResultSet resultSet2 = statement.executeQuery(allplaylists);
			while (resultSet2.next()) {
				playlistName = resultSet2.getString(2);
				songs = getPlaylistSongs(playlistid);
				clientLock = resultSet2.getBoolean(3);
				serverLock = resultSet2.getBoolean(4);
			}
			if (playlistid == -1) {
				return new Playlist(-1, null, new ArrayList<Song>(), false,
						false);
			} else {
				return new Playlist(playlistID, playlistName, songs,
						clientLock, serverLock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Playlist(-1, null, new ArrayList<Song>(), false,
					false);
		} finally {
			DBConnector.getConnection().setAutoCommit(true);
			closeResources();
		}
	}

	/**
	 * Speichert eine Playliste in der Datenbank. �berpr�ft, ob Playliste schon
	 * in Datenbank gespeichert ist mit Hilfe der PlaylistID
	 * 
	 * Wenn Name schon vorhanden, aber ID nicht gleich ist, muss anderer Name
	 * gefunden werden
	 * 
	 * Wenn Name schon vorhanden und ID gleich ist, wird die Playliste geupdatet
	 * 
	 * Wenn PlaylistID nicht vorhanden ist und Name eindeutig, wird die
	 * Playliste neu hinzugef�gt
	 * 
	 * @param playlist
	 *            Playlist-Objekt
	 *            {@link Common.Playlist#Playlist(int, String, ArrayList, boolean, boolean)}
	 * @return boolean true wenn erfolgreich, false wenn Fehler aufgetreten
	 */
	public boolean savePlaylist(Playlist playlist) {
		String name = playlist.getPlaylistName();
		boolean clientLock = false;
		boolean serverLock = false;
		ArrayList<Song> songs = playlist.getSongs();

		/*
		 * 1.Fall:
		 * 
		 * �berpr�fung, ob schon eine Playliste mit dem Namen existiert wie der
		 * Name, den wir gerade speichern wollen
		 */
		if (DBConnector.connectDB() == true) {
			String findPlaylist = "SELECT playlistid FROM PLAYLISTS WHERE playlistname = '"
					+ playlist.getPlaylistName() + "'";

			Statement statement = null;
			ResultSet result = null;

			boolean first = false;//zapamietujemy czy znalezlismy playliste o tej samej nazwie
			try {
				statement = DBConnector.getConnection().createStatement();
				result = statement.executeQuery(findPlaylist);
				first = result.next();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeResources();
			}

			/*
			 * Fall 1a:
			 * 
			 * Wenn wir eine Playliste mit dem gleichen Namen gefunden haben und
			 * diese hat ebenso die gleiche Playlistid, machen wir ein Update
			 * von Name und Songs
			 */
			try {
				if (first == true && playlist.getPlaylistID() == result.getInt("playlistid")) {
					return updatePlaylist(playlist);
				} else if (first == true) {
					System.out.println("Solche Playliste schon existiert: false");
					return false;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				return false;

			} finally {
				closeResources();
			}
			/*
			 * 2.Fall: Wenn Playliste mit dem Namen noch nicht existiert, man
			 * muss sie hinzuf�gen
			 */
			String delPlaylist = "DELETE FROM PLAYLISTS WHERE playlistid = "
					+ playlist.getPlaylistID();
			String deleteP2S = "DELETE FROM PLAYLIST2SONG WHERE playlistid = "
					+ playlist.getPlaylistID();
			String savePllist = "INSERT INTO PLAYLISTS(playlistname, clientLock, serverLock)"
					+ "VALUES('"
					+ name
					+ "','"
					+ clientLock
					+ "','"
					+ serverLock + "')";
			statement = null;
			try {
				statement = DBConnector.getConnection().createStatement();
				statement.execute(deleteP2S);
				statement.execute(delPlaylist);
				statement.executeUpdate(savePllist);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} finally {
				closeResources();
			}
		} else {
			System.out.println("Konnte nicht zur Datenbank verbinden");
			return false;
		}
		int playlistid = getPlaylistID(name);
		savePlaylistSongs(playlistid, songs);

		System.out.println("neue Playliste gespeichert");
		return true;
	}

	/**
	 * Gibt PlaylistID aus einer gespeicherten Playliste aus der Datenbank
	 * zur�ck.
	 * 
	 * @param playlistname, Name der Playliste
	 * @return playlistID, ID der Playliste, -1 bei Fehler
	 */
	private int getPlaylistID(String playlistname) {
		if (DBConnector.connectDB() == true) {
			String query = "SELECT playlistid FROM PLAYLISTS WHERE "
					+ "playlistname = '" + playlistname + "'";
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				statement = DBConnector.getConnection().createStatement();
				resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
					int playlistid = resultSet.getInt(1);
					return playlistid;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			} finally {
				closeResources();
			}
		} else {
			System.out.println("Konnte nicht zur Datenbank verbinden");
			return -1;
		}
		return -1;
	}

	/**
	 * Speichert alle zu einer Playliste geh�renden Lieder in der DB
	 * 
	 * @param playlistid
	 *            ID der Playliste
	 * @param songs
	 *            {@link Common.Playlist#getSongs()} Liste mit Songs, die zu der
	 *            Playliste geh�ren
	 */
	public void savePlaylistSongs(int playlistid, ArrayList<Song> songs) {
		int anzahlSongs = songs.size();
		for (int i = 0; i < anzahlSongs; i++) {

			int songid = songs.get(i).getSongID();

			String saveS2P = "INSERT INTO PLAYLIST2SONG(playlistid, songid)"
					+ "VALUES(" + playlistid + "," + songid + ")";
			Statement statement = null;
			try {
				statement = DBConnector.getConnection().createStatement();
				statement.executeUpdate(saveS2P);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Speichern hat nicht funktioniert");
			} finally {
				closeResources();
			}
		}

	}

	/**
	 * Gibt alle in der Datenbank gespeicherten Songs zur�ck
	 * 
	 * @return ArrayList<Song> alle verf�gbaren Lieder in der Datenbank
	 */
	public ArrayList<Song> getAllSongs() {
		ArrayList<Song> songs = new ArrayList<Song>();
		if (DBConnector.connectDB() == true) {
			String query = "SELECT * FROM SONGS";
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				statement = DBConnector.getConnection().createStatement();
				resultSet = statement.executeQuery(query);

				int anz = 0;
				while (resultSet.next()) {
					anz++;

					Song song = new Song(resultSet.getInt(1), resultSet
							.getString(2), resultSet.getString(3), resultSet
							.getString(4), resultSet.getString(5), resultSet
							.getString(6), resultSet.getString(7));

					songs.add(song);
				}
				System.out.println("Anzahl eingelesener Songs:" + anz);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<Song>();
			} finally {
				closeResources();
			}
		} else {
			System.out.println("Konnte nicht zur Datenbank verbinden");
			return new ArrayList<Song>();
		}
		return songs;
	}

	/**
	 * Erstellt eine leere Playliste mit generischem Namen und speichert diese
	 * in der Datenbank
	 */
	public void createPlaylist() {
		if (DBConnector.connectDB() == true) {

//			String genName = getDate();
			savePlaylist(new Playlist("Playlist" + System.currentTimeMillis(),
					new ArrayList<Song>(), false, false));
		} else {
			System.out.println("Speichern hat nicht funktioniert");
		}
	}

	/**
	 * L�scht eine Playliste komplett aus der Datenbank
	 * 
	 * @param playlistID
	 *            ID der Playliste
	 */
	public void deletePlaylist(int playlistID) {
		String deletePlaylist = "DELETE FROM PLAYLISTS WHERE playlistid = "+ playlistID;
		String deleteP2S = "DELETE FROM PLAYLIST2SONG WHERE playlistid = "+ playlistID;
		Statement statement = null;
		try {
			statement = DBConnector.getConnection().createStatement();
			statement.executeUpdate(deleteP2S);
			statement.executeUpdate(deletePlaylist);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("L�schen hat nicht funktioniert");
		} finally {
			closeResources();
		}
	}


	/**
	 * Die Methode f�hrt ein Update einer gespeicherten Playliste durch
	 * 
	 * @param playlist
	 *            Playlist-Objekt
	 *            {@link Common.Playlist#Playlist(int, String, ArrayList, boolean, boolean)}
	 *            , welches geupdatet werden soll
	 * @return boolean true, wenn Speichervorgang erfolgreich, false wenn nicht
	 *         erfolgreich
	 */
	private boolean updatePlaylist(Playlist playlist) {
		int playlistid = playlist.getPlaylistID();
		String playlistname = playlist.getPlaylistName();

		ArrayList<Song> songs = playlist.getSongs();

		String deleteP2S = "DELETE FROM PLAYLIST2SONG WHERE playlistid = "
				+ playlistid;

		String updatePName = "UPDATE PLAYLISTS SET playlistname = '"
				+ playlistname + "' WHERE playlistid = " + playlistid;
		Statement statement = null;
		try {
			statement = DBConnector.getConnection().createStatement();
			statement.executeUpdate(updatePName);
			statement.executeUpdate(deleteP2S);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("L�schen hat nicht funktioniert");
			return false;
		} finally {
			closeResources();

			savePlaylistSongs(playlistid, songs);
		}
		return true;
	}
}