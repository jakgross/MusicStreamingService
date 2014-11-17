package Common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Definiert das Objekt Playliste. 
 * Im Objekt Playlist wird definiert welche Informationen enthalten sein müssen.
 * Die Playliste enthält:
 * - eine PlaylistID, welche diese identifiziert, 
 * - den Namen der Playliste
 * - eine Liste von in der Playliste gespeicherten Liedern
 * - eine boolsche Variable "clientLock", die angibt, ob die Playliste von einem Client gerade abgespielt wird
 * - eine boolsche Variable "serverLock", die zeigt, ob die Playliste gerade im Server bearbeitet wird.
 * 
 * @author teamJES
 */
public class Playlist implements Serializable {

	
	private static final long serialVersionUID = 7657185140506177218L;
	private int playlistID;
	private String playlistName;
	private ArrayList<Song> songs;
	@SuppressWarnings("unused")
	private boolean clientLock;
	@SuppressWarnings("unused")
	private boolean serverLock;
	
	/**
	 * Konstruktor für Speicherung der Playliste.
	 * Beim Speichern wird ID von der Datenbank generiert, also vor dem Speichern noch nicht bekannt.
	 * @param playlistName Name der Playliste
	 * @param songs Liste von der Playliste zugehöriger Lieder
	 * @param clientLock boolean ob Playliste von Client gesperrt
	 * @param serverLock boolean ob Playliste von Server gesperrt
	 */
	public Playlist(String playlistName, ArrayList<Song> songs, boolean clientLock, 
			boolean serverLock){
		this.playlistName = playlistName;
		this.songs = songs;
		this.clientLock = clientLock;
		this.serverLock = serverLock;
	}
	
	/**
	 * Konstruktor, der komplette Playliste definiert.
	 * @param playlistID ID, identifiziert Playliste eindeutig
	 * @param playlistName Name der Playliste
	 * @param songs Liste von der Playliste zugehöriger Lieder
	 * @param clientLock boolean ob Playliste von Client gesperrt
	 * @param serverLock boolean ob Playliste von Server gesperrt
	 */
	public Playlist(int playlistID, String playlistName, ArrayList<Song> songs, boolean clientLock, 
			boolean serverLock){
		this.playlistID = playlistID;
		this.playlistName = playlistName;
		this.songs = songs;
		this.clientLock = clientLock;
		this.serverLock = serverLock;
	}
	

	public Playlist(int playlistID) {
		super();
		this.playlistID = playlistID;
	}

	public int getPlaylistID() {
		return playlistID;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	
	/**
	 * Vergleicht Playlisten miteinander auf Gleichheit
	 */
	public boolean equals(Object object){
		if(object instanceof Playlist){
			Playlist p =(Playlist)object;
			return p.getPlaylistID()== this.getPlaylistID();
		} else {
		return false;
		}
	}

}//end Playlist