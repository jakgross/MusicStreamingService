package serverdata;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Common.Song;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * Die Klasse DBBasisordnerHandler kümmert sich um die Verwaltung des Basisordners.
 * Sie stellt Methoden bereit, um den Basisordner zu speichern, 
 * die im Basisordner vorhandenen Lieder auszulesen und in der Datenbank zu speichern, 
 * die vorhandenen  Lieder neu einzulesen und 
 * um die gespeicherten Daten bereitzustellen
 * 
 * @author teamJES
 * 
 */
public class DBBasisordnerHandler /*extends DataManager*/ {

	private static File dir;
	private static ID3v1 mp3tag;
	private static Statement statement;
	private static PreparedStatement updateSong = null;
	private static ResultSet resultSet;
	static int anzahl = 0;
	static String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
	private static ArrayList<String> mp3Path = new ArrayList<String>();
	DBPlaylistHandler DBPlaylistHandler = new DBPlaylistHandler();
	
	/**
	 * Konstruktor, erstellt einen neuen DBConnector für die Verbindung mit der Datenbank
	 */
	public DBBasisordnerHandler() {
		new DBConnector();
	}

	/**
	 * Löscht alle Daten aus der Datenbank und speichert den übergebenen
	 * Basisordner in der Datenbank
	 *
	 * @param basisordner Pfad zu Basisordner auf Festplatte
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 */
	public boolean changeBasisordner(String basisordner) throws UnsupportedTagException, InvalidDataException, IOException {
		String basisordnerPath = basisordner;
		if (new File(basisordnerPath).exists()){
			String delBasisordner = "DELETE FROM BASISORDNER";
			String delPlaylists = "DELETE FROM PLAYLISTS";
			String delSongs = "DELETE FROM SONGS";
			String delP2S = "DELETE FROM PLAYLIST2SONG";
			statement = null;
			try {
				statement = DBConnector.getConnection().createStatement();
				statement.executeUpdate(delP2S);
				statement.executeUpdate(delBasisordner);
				statement.executeUpdate(delPlaylists);
				statement.executeUpdate(delSongs);
			} catch (SQLException ex) {
				System.out.println(message + ex.getMessage());
				return false;
			} catch (NullPointerException ex) {
				System.out.println(message + ex.getMessage());
				return false;
			} finally {
				closeResources();
				DBConnector.disconnect();
			} 
			if (checkBasisordner() == false) {
				saveBasisordner(basisordnerPath);
			} else {
				System.out.println("kann neuen Basisordner nicht anlegen");
				return false;
			}
			return true;
		} else {
			return false;
		}
		
	}
	
	
	/**
	 * Speichert den eingegebenen Basisordner in der Datenbank
	 * 
	 * @return boolean true, wenn erfolgreich ausgeführt; false wenn nicht erfolgreich
	 * @param basisordner Pfad zu Basisordner auf Festplatte
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 */
		public boolean saveBasisordner(String basisordner) throws UnsupportedTagException, InvalidDataException, IOException {
			String basisordnerPath = basisordner.replaceAll("'", "''");
			if (DBConnector.connectDB()==true){
			if (new File(basisordner).exists()){
				String updtbasis = "INSERT INTO BASISORDNER(BORDNER)" + "VALUES('"
					+ basisordnerPath + "')";
				System.out.println("Basisordner \"" + basisordner + "\" erstellt.");
				try {
					statement = DBConnector.getConnection().createStatement();
					statement.executeUpdate(updtbasis);
					locateSongsi(basisordner);
					
				} catch (SQLException ex) {
					System.out.println(message + ex.getMessage());
				} finally {
					DBConnector.disconnect();
					closeResources();
				}
				return true;
		} else {
			return false;
		}}
			else { 
				return false;
			}
	}

	/**
	 * Überprüft ob Basisordner in der Datenbank vorhanden ist
	 * 
	 * @return boolean true, wenn Basisordner vorhanden; false, wenn Basisordner nicht gespeichert
	 */
	public boolean checkBasisordner() throws NullPointerException {
		if (DBConnector.connectDB() == true) {
			String query = "SELECT * FROM BASISORDNER";
			
			Statement statement = null;
			ResultSet resultSet = null;
			
			try {
				statement = DBConnector.getConnection().createStatement();
				resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
					if (resultSet.getString(1) != null) {
						System.out.println("Basisordner \""
								+ resultSet.getString(1) + "\" ist vorhanden.");
						return true;
					} else {
						System.out.println("Basisordner ist nicht vorhanden");
						return false;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBConnector.disconnect();
				closeResources();
			}
			System.out.println("Basisordner ist nicht vorhanden");
			return false;
		} else {
			System.out.println("Verbindung zur Datenbank kann nicht hergestellt werden!");
			return false;
		}
	}

	/**
	 * Hilfsmethode um statement und resultSet zu schliessen
	 */
	public void closeResources() {
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
		if (updateSong != null) {
			try {
				updateSong.close();
			} catch (SQLException ex) {
				System.out.println(message + ex.getMessage());
			}
		}
		
	}

	/**
	 * Startet die Lokalisierung der Musikstücke im übergebenen Ordner
	 * Geschwindigkeit: ~ 500/min
	 * 
	 * @param basisordner Pfad zu Basisordner auf Festplatte
	 * @throws IOException
	 * @throws UnsupportedTagException
	 * @throws InvalidDataException
	 */
	public void locateSongsi(String basisordner) throws IOException,
			UnsupportedTagException, InvalidDataException {
		dir = new File(basisordner);
		
		long zeitVorher = System.currentTimeMillis();
		locateSongsAndSave(dir);
		long zeitNachher = System.currentTimeMillis();
		System.out.println(anzahl+" Songs in "+((zeitNachher-zeitVorher)/1000) + " Sekunden erfolgreich eingelesen");
		anzahl = 0;
	}

	/**
	 * Überprüft, ob übergebene mp3-Datei wirklich MPEG Layer 3-kodierte Datei ist
	 * @param fileArray Array mit ausgelesenen Dateien von Festplatte
	 * @param i Position in Array fileArray
	 * @return boolean true, wenn File mp3-Datei ist; false wenn nicht
	 */
	private boolean checkMP3(File[] fileArray, int i) {
		try {
			new Mp3File(fileArray[i].getAbsolutePath());
		} catch (UnsupportedTagException e) {
			return false;
		} catch (InvalidDataException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	

	/**
	 * Lokalisiert Musikstücke im übergebenen Ordner und speichert sie in der Datenbank ab
	 * @param dir File, das alle Dateien im Basisordner enthält
	 * @return boolean true, wenn erfolgreich gespeichert; false wenn Fehler beim Speichern aufgetreten
	 * @throws IOException
	 * @throws UnsupportedTagException
	 * @throws InvalidDataException
	 */
	private boolean locateSongsAndSave(File dir) throws IOException,
			UnsupportedTagException, InvalidDataException {
		File[] fileArray = dir.listFiles();
		
		try {
			for (int i = 0; i < fileArray.length; i++) {
				
				/** 
				 * Überprüft ob Datei oder Ordner, 
				 * wenn Ordner, rufe locateSongs mit diesem rekursiv auf
				 */
				if (fileArray[i].isDirectory()) {
					locateSongsAndSave(fileArray[i]);
				} else {
					if (fileArray[i].getName().toUpperCase().endsWith(".MP3")) {
						while (checkMP3(fileArray, i) == false) {
							i++;
							checkMP3(fileArray, i);
						}
						saveSong(fileArray[i].getAbsolutePath());
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("zu viele Dateien");
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		finally {
			DBConnector.disconnect();
			closeResources();
		}
		return true;
	}
	
	/**
	 * Lokalisiert Musikstücke in übergebenem Basisordner und speichert sie in ArrayList
	 * @param basisordner Pfad des Basisordners als String
	 * @return ArrayList<String> alle Songs, die aktuell im Basisordner vorhanden sind
	 * @throws IOException
	 * @throws UnsupportedTagException
	 * @throws InvalidDataException
	 */
	private ArrayList<String> locateSongs(String basisordner) throws IOException,UnsupportedTagException, InvalidDataException {
		dir = new File(basisordner);
		File[] fileArray = dir.listFiles();
		try {
			for (int i = 0; i < fileArray.length; i++) {
				/** 
				 * Überprüft ob Datei oder Ordner, 
				 * wenn Ordner, rufe locateSongs mit diesem rekursiv auf
				 */
				if (fileArray[i].isDirectory()) {
					locateSongs(fileArray[i].getAbsolutePath());
				} else {
					if (fileArray[i].getName().toUpperCase().endsWith(".MP3")) {
						while (checkMP3(fileArray, i) == false) {
							i++;
							checkMP3(fileArray, i);
						}
						mp3Path.add(fileArray[i].getAbsolutePath());
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("zu viele Dateien");
		} finally {
			anzahl = 0;
			DBConnector.disconnect();
			closeResources();
		}
		return mp3Path;
	}
	
	/**
	 * Speichert ein auf der Festplatte gefundenes Musikstück
	 * mit seinem ID3-Tag in der Datenbank
	 * @param mp3pfad Pfad des zu speichernden Liedes als String
	 */
	private void saveSong(String mp3pfad) {
		String album = null;
		String pfad = null;
		String titel = null;
		String genre = null;
		String interpret = null;
		String name = null;
		
		try {
			Mp3File mp3file = new Mp3File(mp3pfad);
			File file = new File(mp3pfad);
			anzahl++;
		
			DBConnector.getConnection().setAutoCommit(false);
		
			if (mp3file.hasId3v1Tag() == true) {
				mp3tag = mp3file.getId3v1Tag();
				interpret = mp3tag.getArtist().toString().replaceAll("'", "''");
				titel = mp3tag.getTitle().toString().replaceAll("'", "''");
				album = mp3tag.getAlbum().toString().replaceAll("'", "''");
				genre = mp3tag.getGenreDescription().toString().replaceAll("'", "''");
				}
				pfad = mp3pfad.replaceAll("'", "''");
				name = file.getName().toString().replaceAll("'", "''");
			
				String addSong = "INSERT INTO SONGS(filename, songpath, interpret, id3titel, id3album, id3genre)"
						+"VALUES('"+name+"','"+pfad+"','"+interpret+"','"
						+titel+"','"+album+"','"+genre+"')";
			
				updateSong = DBConnector.getConnection().prepareStatement(addSong);
				updateSong.execute();
				DBConnector.getConnection().commit();
				DBConnector.getConnection().setAutoCommit(true);	
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Speichern nicht erfolgreich: SQL-Exception!");
		} catch (UnsupportedTagException e1) {
			e1.printStackTrace();
			System.err.println("Speichern nicht erfolgreich: Nicht unterstützter ID3-Tag!");
		} catch (InvalidDataException e1) {
			e1.printStackTrace();
			System.err.println("Speichern nicht erfolgreich: ungültige Datei!");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Speichern nicht erfolgreich: IO-Exception!");
		} finally {
			DBConnector.disconnect();
			closeResources();
		}
	}

	/**
	 * Liest Musikdateien neu in die Datenbank ein
	 * @param basisordner Pfad des Basisordners als String
	 * @return boolean true, wenn Lieder erfolgreich neu eingelesen; false, wenn Neu-Einlesen nicht erfolgreich
	 */
	private boolean renewSongs(String basisordner){
		
		dir = new File(basisordner);
		
		ArrayList<String> songs = null;
		ArrayList<String> mp3Paths = null;
		
		songs = getSongs(songs);
		
		try {
			mp3Paths = locateSongs(basisordner);
		} catch (UnsupportedTagException e1) {
			e1.printStackTrace();
			return false;
		} catch (InvalidDataException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		
		/*
		System.out.println(songs.size());
		System.out.println(mp3Paths.size());
		*/
		
		/**
		 * Überprüft, ob Songs aus Basisordner in DB vorhanden
		 * falls nicht speichert den Song in DB
		 * mp3Path: ArrayList mit Liedern in Datenbank
		 * songs: ArrayList mit Liedern in Basisordner 
		 */

		for (int intpath = 0; intpath < mp3Paths.size(); intpath++) {
			for (int intdb = 0; intdb < songs.size(); intdb++) {
				if (mp3Paths.get(intpath).compareTo(songs.get(intdb))!=0 && intdb == songs.size()-1) {
					saveSong(mp3Paths.get(intpath));
				} else if (mp3Paths.get(intpath).compareTo(songs.get(intdb)) == 0) {
					intdb = songs.size();
				} 
			}
		}
		mp3Path = new ArrayList<String>();
		songs = null;
		songs = getSongs(songs);
		try {
			mp3Paths = locateSongs(basisordner);
		} catch (UnsupportedTagException e1) {
			e1.printStackTrace();
			return false;
		} catch (InvalidDataException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		/**
		 * Überprüft ob in Datenbank Songs vorhanden, 
		 * die nicht mehr im Basisordner sind.
		 * Falls falscher Datenbankeintrag gefunden, lösche diesen
		 * mp3Path: ArrayList mit Liedern in Datenbank
		 * songs: ArrayList mit Liedern in Basisordner 
		 */
		for (int intdb = 0; intdb < songs.size(); intdb++) {
			for (int intpath = 0; intpath < mp3Paths.size(); intpath++) {
				if (songs.get(intdb).compareTo(mp3Paths.get(intpath))!=0 && intpath == mp3Paths.size()-1) {
					System.out.println("gelöscht: "+songs.get(intdb));
					deleteSong(songs.get(intdb).toString());
				} else if (mp3Paths.get(intpath).compareTo(songs.get(intdb)) == 0) {
					intpath = mp3Paths.size();
				} 
			}
		}
		return true;
	}
	
	/**
	 * Speichert in übergebene ArrayList<String> alle Pfade zu den Liedern in der Datenbank
	 * @param songs übergebene leere ArrayList<String>
	 * @return ArrayList<String> Liste mit allen Pfaden zu den gespeicherten Liedern
	 */
	public ArrayList<String> getSongs(ArrayList<String> songs){
		songs = new ArrayList<String>();
		if (DBConnector.connectDB() == true) {
			String query = 	"SELECT * FROM SONGS";
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				statement = DBConnector.getConnection().createStatement();
				resultSet = statement.executeQuery(query);
				while (resultSet.next()){
					System.out.println(resultSet.getString(3));
					songs.add(resultSet.getString(3)/*.replaceAll("''", "'")*/);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				DBConnector.disconnect();
				closeResources();
			}
		}
		return songs;
	}
	
	/**
	 * Löscht übergebenen Song aus der Datenbank
	 * @param songpath Pfad zu dem zu löschenden Lied
	 */
	private void deleteSong(String songpath) {
		String path = songpath;
		if (DBConnector.connectDB() == true) {
			ArrayList<Song> songs = DBPlaylistHandler.getAllSongs();
			int id = -1;
			for (Song s : songs) {
				if (s.getSongPath().compareTo(songpath)==0){
					id = s.getSongID();
				}
			}
		try {
			// TODO PREPARED STATEMENTS VERWENDEN, PROBLEM MIT SEMIKOLON 
//			DBConnector.getConnection().setAutoCommit(false);
			String deleteSong = null;
			String deleteSong2play = null;
			deleteSong2play = "DELETE FROM PLAYLIST2SONG WHERE songid = "+id;
			deleteSong = "DELETE FROM SONGS WHERE songpath = '"+path.replaceAll("'", "''")+"'";
			statement = DBConnector.getConnection().createStatement();
//			updateSong.clearParameters();
			
			statement.executeUpdate(deleteSong2play);			
			statement.executeUpdate(deleteSong);
//			updateSong.executeUpdate();
////			DBConnector.getConnection().commit();
//			DBConnector.getConnection().setAutoCommit(true);
			deleteSong = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnector.disconnect();
			closeResources();
		}
		}
	}

	/**
	 * aktualisiert die Datenbank 
	 * überprüft ob Basisordner korrekt ist, 
	 * startet neu-Indizierung der Lieder
	 * @param basisordner Pfad zu dem neu zu indizierenden Basisordner
	 * @return boolean true, wenn erfolgreich ausgeführt; false wenn nicht erfolgreich
	 */
	public boolean refreshBasisordner(String basisordner) {
		if (new File(basisordner).exists()){
			String openBasisordner = "SELECT * FROM BASISORDNER";
			try {
//				DBConnector.getConnection().setAutoCommit(false);
				Statement statement = null;
				ResultSet resultSet = null;
				statement = DBConnector.getConnection().createStatement();
				resultSet = statement.executeQuery(openBasisordner);
				while (resultSet.next()) {
					System.out.println(resultSet.getString(1));
					if (resultSet.getString(1) != null && 
							resultSet.getString(1).compareTo(basisordner)==0) {
//							DBConnector.getConnection().setAutoCommit(true);
							renewSongs(basisordner);
							return true;
					} else {
						System.out.println("Basisordner ist nicht vorhanden");
						return false;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} finally {
				DBConnector.disconnect();
				closeResources();
			}
		}
		return true;
	}

	/**
	 * Methode um den in der Datenbank gespeicherten Basisordner auszulesen
	 * @return String gespeicherter Basisordner
	 */
	public String getBasisordner() {
		String openBasisordner = "SELECT * FROM BASISORDNER";
		try {
			DBConnector.getConnection().setAutoCommit(false);
			Statement statement = null;
			ResultSet resultSet = null;
			statement = DBConnector.getConnection().createStatement();
			resultSet = statement.executeQuery(openBasisordner);
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
				if (resultSet.getString(1) != null) { 
					 return resultSet.getString(1);
				} else {
					System.out.println("Basisordner ist nicht vorhanden");
					return "not available";
				}
			}
			DBConnector.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			return "not available";
		} finally {
			DBConnector.disconnect();
			closeResources();
		}
		return "not available";
	}
}
