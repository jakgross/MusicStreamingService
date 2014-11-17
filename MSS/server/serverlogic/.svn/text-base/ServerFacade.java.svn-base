package serverlogic;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import servergui.MainButtonPanel;

import Common.Client;
import Common.Playlist;
import Common.Song;
import Common.SongPackage;

import clientlogic.ClientFacadeInterface;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

/**
 * Die Klasse ServerFacade 
 *
 * @author JES
 * @version 1.0
 */
public class ServerFacade extends UnicastRemoteObject implements ServerFacadeInterface {

	private static final long serialVersionUID = 1856820241643737241L;
	private ServerDataHandler dataHandler;
	private ArrayList<Client> clients;
	private Map<Client, Song> songListeners;
	private Map<Client, Playlist> playlistListeners;
	private Map<Client, AudioInputStream> streamListeners;
	private Map<Client, ArrayList<Client>> synchronizationTeams;
	private static HashMap<String, Integer> openedPlaylists = new HashMap<String, Integer>();

	/**
	 * Instanziiert ServerFacade
	 *
	 * @throws RemoteException bei RMI-Fehlern geworfen
	 */
	protected ServerFacade() throws RemoteException {
		super();

		dataHandler = new ServerDataHandler();
		clients = new ArrayList<Client>();
		songListeners = new HashMap<Client, Song>();
		playlistListeners = new HashMap<Client, Playlist>();
		streamListeners = new HashMap<Client, AudioInputStream>();
		synchronizationTeams = new HashMap<Client, ArrayList<Client>>();
	}

	/**
	 * Gibt Client-Objekt über seinen Ort zurück
	 *
	 * @param ort Ort des Clients
	 * @return Client-Objekt
	 */
	public Client getClientByOrt(String ort) {
		for (Client client : clients)
			if (client.getOrt().equals(ort))
				return client;

		return null;
	}

	/**
	 * Überprüft, ob Client synchronisiert, oder synchronisiert wird
	 *
	 * @param client zu überprüfender Client
	 * @return true, wenn Client synchronisert wird oder synchronisiert
	 */
	public boolean belongsToSynchronizationTeam(Client client) {
		for (Client c : synchronizationTeams.keySet())
			if (c.equals(client))
				return true;

		for (ArrayList<Client> list : synchronizationTeams.values())
			for (Client c : list)
				if (c.equals(client))
					return true;

		return false;
	}

	/**
	 * Überprüft, ob Client andere Clients synchronisiert
	 *
	 * @param client der zu überprüfende Client
	 * @return true, wenn Client andere Clients synchronisiert
	 */
	public boolean isSynchronizationMaster(Client client) {
		for (Client c : synchronizationTeams.keySet())
			if (c.equals(client))
				return true;

		return false;
	}

	/**
	 * Gibt Liste aller Clients zurück, die in der gleichen Synchronistionsgruppe sind wie der übergebene Client.
	 * Wird benötigt, um Client bei logout aus der Liste auszutragen.
	 *
	 * @param client der zu überprüfende Client
	 * @return Liste von mit dem Client synchronisierten Clients
	 */
	public ArrayList<Client> getSynchronizationListBySlave(Client client) {
		for (ArrayList<Client> slaves : synchronizationTeams.values())
			for (Client slave : slaves)
				if (slave.equals(client))
					return slaves;

		return null;
	}

	/**
	 * überprüft, ob Client synchronisiert wird
	 *
	 * @param client der zu überpüfende Client
	 * @return true, wenn Client synchronisiert wird
	 */
	public boolean isSynchronizationSlave(Client client) {
		for (ArrayList<Client> list : synchronizationTeams.values())
			for (Client c : list)
				if (c.equals(client))
					return true;

		return false;
	}

	/**
	 * Informiert alle verbundenen Clients über Beenden des Servers und trennt die Clients vom Server
	 */
	public void close() {
		for (Client c : clients) {
			try {
				c.getClientFacade().serverClosed();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		clients.clear();
	}

	/**
	 * Überprüft ob Client mit dem Ort vorhanden, oder übergebener Ort = null
	 * @return true, wenn Client mit dem Ort noch nicht vorhanden
	 * @see ServerFacadeInterface#checkOrt(java.lang.String)
	 */
	@Override
	public boolean checkOrt(String ort) throws RemoteException {
		if (ort == null)
			return false;

		for (int x = 0; x < clients.size(); x++) {
			if (clients.get(x).getOrt().equals(ort)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gibt Liste mit allen am Server verbundenen Clients zurück
	 */
	@Override
	public ArrayList<Client> getClientList() {
		return clients;
	}

	/**
	 * führt Client aus, um sich im Server zu registrieren. 
	 * Wenn Ort noch nicht vorhanden und Client über RMI verbunden, füge diesen in ClientList hinzu.
	 * @return true, wenn Client erfolgreich angemeldet ist 
	 */
	@Override
	public boolean login(String clientIP, String ort) throws RemoteException {
		// überprüft ob Ort schon vorhanden ist
		if (checkOrt(ort)) {// gibt true zurück, wenn Ort nicht vorhanden
			Client client = new Client(clientIP, ort);
			// hole Client-Facade
			ClientFacadeInterface facade = client.getClientFacade();  

			if (facade != null) {// wenn ClientFacade verfügbar ist, füge Client in ClientListe hinzu
				clients.add(client);
				return true;
			} else {
				// Client nicht akzeptiert
				return false;
			}
		} else
			return false;
	}

	/** 
	 * Schließt alle Verbindungen zum Client und entfernt ihn aus allen Listen im Server
	 * @param ort Ort des abzumeldenden Clients
	 * @return true, wenn erfolgreich abgemeldet
	 */
	@Override
	public boolean logoutClient(String ort) throws RemoteException {
		Client client = getClientByOrt(ort); // gibt Client über seinen Ort als Objekt zurück

		if (client != null) {
			clients.remove(client);
			songListeners.remove(client); // lösche Client aus Liste der Clients die Songs abspielen
			playlistListeners.remove(client); // lösche Client aus Liste der Clients die eine Playliste abspielen

			AudioInputStream din = streamListeners.get(client);	// falls Stream vorhanden, lösche diesen
			if (din != null) {
				try {
					din.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				streamListeners.remove(client); // lösche Client aus Liste die Streams empfangen
			}
			// falls Client andere synchronisiert, stoppe die Synchronisation
			if (isSynchronizationMaster(client)) {
				ArrayList<Client> slaves = synchronizationTeams.get(client);

				for (Client slave : slaves) {
					slave.getClientFacade().stopSynchronisation();
				}

				synchronizationTeams.remove(client); // lösche Client aus der Liste der synchronisierten Clients
			} else if (isSynchronizationSlave(client)) {
				ArrayList<Client> slaves = getSynchronizationListBySlave(client);
				slaves.remove(client); // lösche alle Sklaven!!!!
			}
		}

		return false;
	}

	/**
	 * Startet die Synchronisierung der übergebenen Clients mit dem Master.
	 * @param ort Ort, des Synchronisation-Masters
	 * @param clientList ArrayList<Client>, der zu sychronisierenden Clients
	 */
	@Override
	public void synchronize(String ort, ArrayList<Client> clientList) throws RemoteException {
		Client client = getClientByOrt(ort);
		// Master darf nicht schon synchronisiert sein, oder andere Clients synchronisieren
		if (client != null && !belongsToSynchronizationTeam(client) && !clientList.isEmpty()) {
			ArrayList<Client> slaves = new ArrayList<Client>();
			for (Client c : clientList) {
				// zu synchronisierende Clients, dürfen nicht schon in Synchro sein
				if (!belongsToSynchronizationTeam(c)) {
					slaves.add(c);
					c.getClientFacade().startSynchronisation();
				}
			}
			
			// füge zu SynchroTeam hinzu
			synchronizationTeams.put(client, slaves);

			// holt den im Master aktuell abgespielten song 
			Song currentSong = songListeners.get(client);
			if (currentSong != null) {
				// holt die im Master aktuell abgespielte Playliste
				Playlist playlist = playlistListeners.get(client);
				LinkedList<SongPackage> packages = new LinkedList<SongPackage>();

				// erstelle für jeden Sklave ein SongPackage und füge dieses in Liste hinzu
				for (Client c : slaves) {
					SongPackage p = play(c.getOrt(), currentSong, playlist);
					packages.add(p);
				}
				
				// starte synchronisierte Wiedergabe bei jedem Sklaven
				for (Client c : slaves)
					c.getClientFacade().synchronisationPlay(packages.pollLast());
			}
		}
	}

	/**
	 * stoppt die Synchronisation von allen Clients, die vom Übergebenen Client synchronisiert werden
	 * @param ort Ort des Clients
	 */
	@Override
	public void stopSynchronisation(String ort) {
		Client client = getClientByOrt(ort);

		if (client != null) {
			// holt synchronisierte Clients von diesem Client
			ArrayList<Client> slaves = synchronizationTeams.get(client);
			if (slaves != null) {
				// stoppt die Synchronisation von jedem Client der in der Liste ist
				for (Client slave : slaves) {
					try {
						slave.getClientFacade().stopSynchronisation();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
			// löscht Master aus der TeamSynchronize Liste
			synchronizationTeams.remove(client);
		}
	}

	/**
	 * gibt alle Playlisten aus der Datenbank zurück
	 * @return ArrayList<Playlist> alle gespeicherten Playlisten
	 */
	@Override
	public ArrayList<Playlist> getAllPlaylists() {
		return dataHandler.ggetAllPlaylists();
	}

	/**
	 * Öffnet eine Playliste und blockiert den Zugriff auf diese für den Administrator. 
	 * @param ort Ort des Clients 
	 * @param playlist zu öffnende Playliste
	 * @return ArrayList<Song> zu der Playliste gehörende Songs
	 */
	@Override
	public ArrayList<Song> openPlaylist(Playlist playlist, String ort) {
		if (!MainButtonPanel.getBlockedPlaylists().contains(new Integer(playlist.getPlaylistID()))) {
			// aktuell agespielte Playliste entfernen von hashMap
			openedPlaylists.remove(ort);
			// neu geöffnete Playliste hinzufügen
			openedPlaylists.put(ort, playlist.getPlaylistID());
			return dataHandler.openPlaylist(playlist.getPlaylistName());
		} else {
			// wenn Admin die Playlist editiert, return null
			return null;
		}
	}

	/**
	 * Sucht nach dem Parameter String entprechende Songs aus der Datenbank 
	 * und gibt Liste mit Playlisten, in denen die Songs vorkommen zurück.
	 * @param inputSearch String, nach dem gesucht werden soll
	 * @return ArrayList<Playlist> entprechende Playlisten
	 */
	@Override
	public ArrayList<Playlist> searchSongClient(String inputSearch) {
		if (inputSearch == null || inputSearch.equals(""))
			return getAllPlaylists();
		else
			return dataHandler.searchSongClient(inputSearch);
	}

	/**
	 * Ändert den im Server für den Client gespeicherten Ort.
	 * @param oldOrt alter gespeicherter Ort
	 * @param newOrt neuer, zu speichernder Ort
	 * @return true, wenn erfolgreich geändert
	 * @throws RemoteException bei RMI-Fehler
	 */
	@Override
	public boolean changeOrt(String oldOrt, String newOrt) throws RemoteException {
		if (oldOrt.equals(newOrt))
			return true;

		Client client = getClientByOrt(oldOrt);
		if (client != null && checkOrt(newOrt)) {
			client.setOrt(newOrt);
			return true;
		} else
			return false;
	}

	/**
	 * die Methode erstellt mit den Informationen ein neues SongPackage, bzw. einen neuen Stream.
	 * @param ort Ort des Clients
	 * @param song Lied, das abgespielt werden soll
	 * @param playlist Playliste aus der das Lied abgespielt werden soll
	 * @throws RemoteException RMI-Fehler
	 * @return {@link SongPackage} verpackter Stream in SongPackage
	 */
	@Override
	public SongPackage play(String ort, Song song, Playlist playlist) throws RemoteException {
		Client client = getClientByOrt(ort);

		if (client != null) {
			File file = new File(song.getSongPath());

			if (file.isFile()) {
				try {
					AudioInputStream originStream = AudioSystem.getAudioInputStream(file);

					AudioFormat baseFormat = originStream.getFormat();
					AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
							baseFormat.getSampleRate(), 16, 
							baseFormat.getChannels(),
							baseFormat.getChannels() * 2, 
							baseFormat.getSampleRate(), 
							false);

					AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, originStream);
					RemoteInputStreamServer remoteStream = new SimpleRemoteInputStream(decodedStream);

					AudioInputStream previousStream = streamListeners.get(client);
					if (previousStream != null)
						previousStream.close();

					songListeners.put(client, song);
					playlistListeners.put(client, playlist);
					streamListeners.put(client, decodedStream);

					// synchronisation
					if (isSynchronizationMaster(client)) {
						ArrayList<Client> slaves = synchronizationTeams.get(client);
						LinkedList<SongPackage> packages = new LinkedList<SongPackage>();

						for (Client slave : slaves) {
							SongPackage slavePackage = play(slave.getOrt(),song, playlist);
							packages.add(slavePackage);
						}

						for (Client slave : slaves)
							slave.getClientFacade().synchronisationPlay(packages.pollLast());
					}
					
					// remoteStream.export() wegen rmiio!
					return new SongPackage(remoteStream.export(),decodedFormat, 0);
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} else
				return null;
		} else
			return null;
	}

	/**
	 * startet die Wiedergabe des vorherigen Liedes in der Playliste
	 * @param ort Ort des Clients auf dem die Methode ausgeführt wird
	 * @return SongPackage mit dem Stream
	 */
	@Override
	public SongPackage prev(String ort) throws RemoteException {
		Client client = getClientByOrt(ort);

		if (client != null) {
			Song currentSong = songListeners.get(client);
			Playlist playlist = playlistListeners.get(client);

			int i = playlist.getSongs().indexOf(currentSong);
			if (i != -1 && i > 0)
				return play(ort, playlist.getSongs().get(i - 1), playlist);
			else
				return null;
		} else
			return null;
	}

	/**
	 * startet die Wiedergabe des nächsten Liedes in der Playliste
	 * @param ort Ort des Clients auf dem die Methode ausgeführt wird
	 * @return SongPackage mit dem Stream
	 */
	@Override
	public SongPackage forward(String ort) throws RemoteException {
		Client client = getClientByOrt(ort);

		if (client != null) {
			Song currentSong = songListeners.get(client);
			Playlist playlist = playlistListeners.get(client);

			int i = playlist.getSongs().indexOf(currentSong);
			if (i != -1 && i < playlist.getSongs().size() - 1)
				return play(ort, playlist.getSongs().get(i + 1), playlist);
			else
				return null;
		} else
			return null;
	}

	/**
	 * pausiert Wiedergabe bei synchronisierten Clients
	 * @param ort Ort des SynchronisationMasters
	 */
	@Override
	public void pause(String ort) throws RemoteException {
		Client client = getClientByOrt(ort);

		// für die Synchronisation pause
		if (client != null && isSynchronizationMaster(client)) {
			ArrayList<Client> slaves = synchronizationTeams.get(client);

			for (Client slave : slaves)
				slave.getClientFacade().synchronisationPause();
		}
	}

	/**
	 * nimmt die Wiedergabe des pausierte Liedes bei synchronisierten Clients wieder auf
	 * @param ort Ort des SynchronisationMasters
	 */
	@Override
	public void resume(String ort) throws RemoteException {
		Client client = getClientByOrt(ort);

		if (client != null && isSynchronizationMaster(client)) {
			ArrayList<Client> slaves = synchronizationTeams.get(client);

			for (Client slave : slaves)
				slave.getClientFacade().synchronisationResume();
		}
	}

	/**
	 * Gibt von Clients geöffnete Playlisten zurück
	 *
	 * @return HashSet mit IDs der Playlisten
	 */
	
	public static HashSet<Integer> getOpenedPlaylists() {
		HashSet<Integer> result = new HashSet<Integer>();

		for (Integer v : openedPlaylists.values()) {
			result.add(v);
		}
		return result;
	}
}
