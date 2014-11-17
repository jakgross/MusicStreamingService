package serverdata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import Common.Playlist;
import Common.Song;

/**
 * Definiert Interface, welches die Methoden für Datenbankoperationen bereitstellt.
 * @author teamJES
 */
public interface DataManagerInterface {


	/**
	 * {@link serverdata.DataManager#changeBasisordner(String)}
	 * @param basisordner
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException
	 * @return boolean 
	 */
	boolean changeBasisordner(String basisordner) throws UnsupportedTagException, InvalidDataException, IOException;
	
	/**
	 * {@link serverdata.DataManager#saveBasisordner(String)}
	 * @param basisordner
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 * @return boolean 
	 */
	boolean saveBasisordner(String basisordner) throws UnsupportedTagException, InvalidDataException, IOException;
	
	/**
	 * {@link serverdata.DataManager#checkBasisordner()}
	 * @return boolean
	 */
	boolean checkBasisordner();
	
	/**
	 * {@link serverdata.DataManager#getSongs(ArrayList)}
	 * @param songs
	 * @return ArrayList<String>
	 */
	ArrayList<String> getSongs(ArrayList<String> songs);
	
	/**
	 * {@link serverdata.DataManager#refreshBasisordner(String)}
	 * @param basisordner
	 * @return boolean
	 */
	boolean refreshBasisordner(String basisordner);

	/**
	 * {@link serverdata.DataManager#getBasisordner()}
	 * @return String
	 */
	String getBasisordner();
	
	/**
	 * {@link serverdata.DataManager#searchSongClient(String)}
	 * @param inputSearch
	 * @return ArrayList<Playlist>
	 */
	ArrayList<Playlist> searchSongClient(String inputSearch);
	
	/**
	 * {@link serverdata.DataManager#searchSongAdmin(String)}
	 * @param inputSearch
	 * @return ArrayList<Song>
	 */
	ArrayList<Song> searchSongAdmin(String inputSearch);


	/**
	 * {@link serverdata.DataManager#getAllPlaylists()}
	 * @return ArrayList<Playlist>
	 */
	ArrayList<Playlist> getAllPlaylists();

	/**
	 * {@link serverdata.DataManager#getPlaylistSongs(int)}
	 * @param playlistid
	 * @return ArrayList<Song>
	 */
	ArrayList<Song> getPlaylistSongs(int playlistid);
	
	/**
	 * {@link serverdata.DataManager#openPlaylist(String)}
	 * @param playlist
	 * @return ArrayList<Playlist>
	 */
	ArrayList<Song> openPlaylist(String playlist);

	/**
	 * {@link serverdata.DataManager#openPlaylist(int)}
	 * @param playlistID
	 * @return Playlist
	 */
	Playlist openPlaylist(int playlistID) throws SQLException;
	
	/**
	 * {@link serverdata.DataManager#savePlaylist(Playlist)}
	 * @param playlist
	 * @return boolean
	 */
	boolean savePlaylist(Playlist playlist);

	/**
	 * {@link serverdata.DataManager#savePlaylistSongs(int, ArrayList)}
	 * @param playlistid
	 * @param songs
	 */
	void savePlaylistSongs(int playlistid, ArrayList<Song> songs);
	
	/**
	 * {@link serverdata.DataManager#getAllSongs()}
	 * @return ArrayList<Song>
	 */
	ArrayList<Song> getAllSongs();

	/**
	 * {@link serverdata.DataManager#createPlaylist()}
	 */
	void createPlaylist();
	
	/**
	 * {@link serverdata.DataManager#deletePlaylist(int)}
	 * @param playlistid
	 */
	void deletePlaylist(int playlistid);


}