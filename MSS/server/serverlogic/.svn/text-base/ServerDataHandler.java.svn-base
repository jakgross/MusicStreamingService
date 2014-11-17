package serverlogic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import serverdata.DataManager;

import Common.Playlist;
import Common.Song;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;


/**
 * Stellt Methoden zur Verfügung um mit der Datenbank zu arbeiten.
 * Die Methoden werden zu der Datenschicht delegiert.
 * 
 * @author JES
 * @version 1.0
 */
public class ServerDataHandler implements ServerGUIInterface {

	private DataManager dataManager = new DataManager();
	
	public ServerDataHandler() {
	}

	/**
	 * erstellt eine neue Playliste
	 * {@link serverdata.DBPlaylistHandler#createPlaylist()}
	 */
	@Override
	public void createPlaylist() {
		dataManager.createPlaylist();
	}

	/**
	 * speichert den übergebenen Basisordner und löscht den alten
	 * {@link serverdata.DBBasisordnerHandler#changeBasisordner(String)}
	 * 
	 * @param basisordner
	 */
	@Override
	public boolean gchangeBasisordner(String basisordner) {
		try {
			return dataManager.changeBasisordner(basisordner);
		} catch (UnsupportedTagException e) {
			return false;
		} catch (InvalidDataException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * speichert den übergebenen Basisordner
	 * {@link serverdata.DBBasisordnerHandler#saveBasisordner(String)}
	 * 
	 * @param basisordner
	 */
	@Override
	public boolean gsaveBasisordner(String basisordner) {
		try {
			return dataManager.saveBasisordner(basisordner);
		} catch (UnsupportedTagException e) {
			return false;
		} catch (InvalidDataException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * gibt dem Suchbegriff entsprechende Songs zurück
	 * {@link serverdata.DBPlaylistHandler#searchSongAdmin(String)}
	 * 
	 * @param inputSearch
	 */
	@Override
	public ArrayList<Song> gsearchSongAdmin(String inputSearch) {
		return dataManager.searchSongAdmin(inputSearch);
	}

	/**
	 * aktualisiert gespeicherten Basisordner
	 * {@link serverdata.DBBasisordnerHandler#refreshBasisordner(String)}
	 * 
	 */
	@Override
	public boolean grefreshBasisordner() {
		return dataManager.refreshBasisordner(dataManager.getBasisordner());
	}

	/**
	 * Speichert übergebene Playliste in Datenbank
	 * {@link serverdata.DBPlaylistHandler#savePlaylist(Playlist)}
	 * 
	 * @param playlist
	 */
	@Override
	public boolean gsavePlaylist(Playlist playlist) {
		return dataManager.savePlaylist(playlist);
	}

	/**
	 * Löscht übergebene Playliste
	 * {@link serverdata.DBPlaylistHandler#deletePlaylist(int)}
	 * 
	 * @param playlistid
	 */
	public void gdeletePlaylist(int playlistid) {
		dataManager.deletePlaylist(playlistid);
	}

	/**
	 * Gibt Basisordner als File zurück
	 * {@link serverdata.DBBasisordnerHandler#getBasisordner()}
	 * 
	 * @return File
	 */
	public File ggetBasisordner() {
		return new File(dataManager.getBasisordner());
	}
	
	/**
	 * Überprüft, ob Basisordner vorhanden ist
	 * {@link serverdata.DBBasisordnerHandler#checkBasisordner()}
	 * 
	 * @return boolean
	 */
	public boolean checkBasisordner() {
		return dataManager.checkBasisordner();
	}
	
	/**
	 * gibt alle verfügbaren Playlists zurück 
	 * {@link serverdata.DBPlaylistHandler#getAllPlaylists()}
	 * 
	 * @return ArrayList<Playlist>
	 */
	public ArrayList<Playlist> ggetAllPlaylists() {
		return dataManager.getAllPlaylists();
	}

	/**
	 * öffnet eine Playliste über ihren Namen
	 * {@link serverdata.DBPlaylistHandler#openPlaylist(String)}
	 * @param playlistName
	 * @return ArrayList<Song>
	 */
	public ArrayList<Song> openPlaylist(String playlistName) {
		return dataManager.openPlaylist(playlistName);
	}
	
	/**
	 * sucht dem Suchbegriff ensprechende Songs, 
	 * und gibt die Playlisten zurück, die die Songs enthalten
	 * {@link serverdata.DBPlaylistHandler#searchSongClient(String)}
	 * @param inputSearch
	 * @return ArrayList<Playlist>
	 */
	public ArrayList<Playlist> searchSongClient(String inputSearch) {
		return dataManager.searchSongClient(inputSearch);
	}

	/**
	 * gibt alle vorhandenen Songs zurück
	 * {@link serverdata.DBPlaylistHandler#getAllSongs()}
	 * @return ArrayList<Song>
	 */
	public ArrayList<Song> getAllSongs() {
		return dataManager.getAllSongs();
	}

}