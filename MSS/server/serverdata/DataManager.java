package serverdata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import Common.Playlist;
import Common.Song;

/**
 * Diese Klasse bietet Zugriff auf Funktionen der Datenschicht für andere Schichten
 * @author teamJES
 *
 */
public class DataManager implements DataManagerInterface {
	
	/**
	 * {@link serverdata.DBBasisordnerHandler#DBBasisordnerHandler()}
	 */
	public serverdata.DBBasisordnerHandler DBBasisordnerHandler = new DBBasisordnerHandler();
	
	/**
	 * {@link serverdata.DBPlaylistHandler#DBPlaylistHandler()}
	 */
	public serverdata.DBPlaylistHandler DBPlaylistHandler = new DBPlaylistHandler();

	/**
	 * {@link serverdata.DBBasisordnerHandler#changeBasisordner(String)}
	 */
	@Override
	public boolean changeBasisordner(String basisordner)
			throws UnsupportedTagException, InvalidDataException, IOException {
		return DBBasisordnerHandler.changeBasisordner(basisordner);
	}
	
	/**
	 * {@link serverdata.DBBasisordnerHandler#saveBasisordner(String)}
	 */
	@Override
	public boolean saveBasisordner(String basisordner)
			throws UnsupportedTagException, InvalidDataException, IOException {
		return DBBasisordnerHandler.saveBasisordner(basisordner);
	}

	/**
	 * {@link serverdata.DBBasisordnerHandler#checkBasisordner()}
	 */
	@Override
	public boolean checkBasisordner() {
		return DBBasisordnerHandler.checkBasisordner();
	}
	
	/**
	 * {@link serverdata.DBBasisordnerHandler#getSongs(ArrayList)}
	 */
	@Override
	public ArrayList<String> getSongs(ArrayList<String> songs) {
		return DBBasisordnerHandler.getSongs(songs);
	}
	
	/**
	 * {@link serverdata.DBBasisordnerHandler#refreshBasisordner(String)}
	 */
	@Override
	public boolean refreshBasisordner(String basisordner) {
		return DBBasisordnerHandler.refreshBasisordner(basisordner);
	}
	
	/**
	 * {@link serverdata.DBBasisordnerHandler#getBasisordner()}
	 */
	@Override
	public String getBasisordner() {
		return DBBasisordnerHandler.getBasisordner();
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#searchSongClient(String)}
	 */
	@Override
	public ArrayList<Playlist> searchSongClient(String inputSearch) {
		return DBPlaylistHandler.searchSongClient(inputSearch);
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#searchSongAdmin(String)}
	 */
	@Override
	public ArrayList<Song> searchSongAdmin(String inputSearch) {
		return DBPlaylistHandler.searchSongAdmin(inputSearch);
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#getAllPlaylists()}
	 */
	@Override
	public ArrayList<Playlist> getAllPlaylists() {
		return DBPlaylistHandler.getAllPlaylists();
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#getPlaylistSongs(int)}
	 */
	@Override
	public ArrayList<Song> getPlaylistSongs(int playlistid) {
		return DBPlaylistHandler.getPlaylistSongs(playlistid);
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#openPlaylist(String)}
	 */
	@Override
	public ArrayList<Song> openPlaylist(String playlistName) {
		return DBPlaylistHandler.openPlaylist(playlistName);
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#openPlaylist(int)}
	 */
	@Override
	public Playlist openPlaylist(int playlistID) throws SQLException {
		return DBPlaylistHandler.openPlaylist(playlistID);
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#savePlaylist(Playlist)}
	 */
	@Override
	public boolean savePlaylist(Playlist playlist) {
		return DBPlaylistHandler.savePlaylist(playlist);
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#savePlaylistSongs(int, ArrayList)}
	 */
	@Override
	public void savePlaylistSongs(int playlistid, ArrayList<Song> songs) {
		DBPlaylistHandler.savePlaylistSongs(playlistid, songs);
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#getAllSongs()}
	 */
	@Override
	public ArrayList<Song> getAllSongs() {
		return DBPlaylistHandler.getAllSongs();
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#createPlaylist()}
	 */
	@Override
	public void createPlaylist() {
		DBPlaylistHandler.createPlaylist();
	}
	
	/**
	 * {@link serverdata.DBPlaylistHandler#deletePlaylist(int)}
	 */
	@Override
	public void deletePlaylist(int playlistid) {
		DBPlaylistHandler.deletePlaylist(playlistid);
	}
}
