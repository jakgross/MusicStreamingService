package serverlogic;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Common.Client;
import Common.Playlist;
import Common.Song;
import Common.SongPackage;

/**
 * Definiert die Methoden, die in der {@link ServerFacade} implementiert sind
 *
 * @author teamJES
 * @version 1.0
 */
public interface ServerFacadeInterface extends Remote {

	/**
	 * Gets the client list.
	 *
	 * @return the client list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Client> getClientList() throws RemoteException;

	/**
	 * Play.
	 *
	 * @param ort the ort
	 * @param song the song
	 * @param playlist the playlist
	 * @return the song package
	 * @throws RemoteException the remote exception
	 */
	public SongPackage play(String ort, Song song, Playlist playlist) throws RemoteException;

	/**
	 * Prev.
	 *
	 * @param ort the ort
	 * @return the song package
	 * @throws RemoteException the remote exception
	 */
	public SongPackage prev(String ort) throws RemoteException;

	/**
	 * Pause.
	 *
	 * @param ort the ort
	 * @throws RemoteException the remote exception
	 */
	public void pause(String ort) throws RemoteException;

	/**
	 * Resume.
	 *
	 * @param ort the ort
	 * @throws RemoteException the remote exception
	 */
	public void resume(String ort) throws RemoteException;

	/**
	 * Forward.
	 *
	 * @param ort the ort
	 * @return the song package
	 * @throws RemoteException the remote exception
	 */
	public SongPackage forward(String ort) throws RemoteException;

	/**
	 * Synchronize.
	 *
	 * @param ort the ort
	 * @param clientList the client list
	 * @throws RemoteException the remote exception
	 */
	public void synchronize(String ort, ArrayList<Client> clientList) throws RemoteException;

	/**
	 * Login.
	 *
	 * @param clientIP the client ip
	 * @param ort the ort
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean login(String clientIP, String ort) throws RemoteException;

	/**
	 * Check ort.
	 *
	 * @param ort the ort
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean checkOrt(String ort) throws RemoteException;

	/**
	 * Stop synchronisation.
	 *
	 * @param ort the ort
	 * @throws RemoteException the remote exception
	 */
	public void stopSynchronisation(String ort) throws RemoteException;

	/**
	 * Gets the all playlists.
	 *
	 * @return the all playlists
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Playlist> getAllPlaylists() throws RemoteException;

	/**
	 * Open playlist.
	 *
	 * @param playlist the playlist
	 * @param ort the ort
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Song> openPlaylist(Playlist playlist, String ort) throws RemoteException;

	/**
	 * Search song client.
	 *
	 * @param inputSearch the input search
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Playlist> searchSongClient(String inputSearch) throws RemoteException;

	/**
	 * Logout client.
	 *
	 * @param Ort the ort
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean logoutClient(String Ort) throws RemoteException, IOException;

	/**
	 * Change ort.
	 *
	 * @param oldOrt the old ort
	 * @param newOrt the new ort
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean changeOrt(String oldOrt, String newOrt) throws RemoteException;
}