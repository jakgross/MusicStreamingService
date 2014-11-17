package serverlogic;

import java.io.File;
import java.util.ArrayList;

import Common.*;

/**
 * Definiert Methoden, die der ServerGUI den Zugriff auf dei Datenschicht erlauben.
 *
 * @author JES
 * @version 1.0
 */
public interface ServerGUIInterface {

	/**
	 * erstellt Playliste
	 */
	public void createPlaylist();

	/**
	 * wechselt den Basisordner
	 *
	 * @param basisordner the basisordner
	 * @return true, if successful
	 */
	public boolean gchangeBasisordner(String basisordner);
	
	/**
	 * speichert den Basisordner
	 *
	 * @param basisordner the basisordner
	 * @return true, if successful
	 */
	public boolean gsaveBasisordner(String basisordner);

	/**
	 * sucht nach entsprechenden Songs
	 *
	 * @param inputSearch the input search
	 * @return the array list
	 */
	public ArrayList<Song> gsearchSongAdmin(String inputSearch);

	/**
	 * aktualisiert Basisordner
	 *
	 * @return true, if successful
	 */
	public boolean grefreshBasisordner();

	/**
	 * speichert Playliste
	 *
	 * @param playlist the playlist
	 * @return true, wenn speichern der Playliste erfolgreich
	 */
	public boolean gsavePlaylist(Playlist playlist);

	/**
	 * l�scht Playliste
	 *
	 * @param playlistid the playlistid
	 */
	public void gdeletePlaylist(int playlistid);
	
	/**
	 * gibt Basisordner zur�ck
	 *
	 * @return the file
	 */
	public File ggetBasisordner();
	
	/**
	 * �berpr�ft, ob Basisordner vorhanden ist
	 * 
	 * @return true, wenn Basisordner vorhanden ist
	 */
	public boolean checkBasisordner();
	
	/**
	 * gibt alle Playlisten zur�ck
	 * 
	 * @return ArrayList<Playlist> alle in der Datenbank gespeicherten Playlists
	 */
	public ArrayList<Playlist> ggetAllPlaylists();

	/**
	 * gibt Songs der �bergebenen Playlist zur�ck
	 * 
	 * @param playlistName
	 * @return ArrayList<Song> alle zu der Playliste geh�renden Songs
	 */
	public ArrayList<Song> openPlaylist(String playlistName);
	
	/**
	 * gibt dem Suchbegriff entsprechende Songs, deren Playlisten zur�ck
	 * 
	 * @param inputSearch
	 * @return ArrayList<Playlist>
	 */
	public ArrayList<Playlist> searchSongClient(String inputSearch);
	
	/**
	 * gibt alle vorhandenen Songs zur�ck
	 * @return ArrayList<Song>
	 */
	public ArrayList<Song> getAllSongs();
}