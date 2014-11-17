package clientlogic;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import javax.sound.sampled.LineUnavailableException;

import clientlogic.player.AudioPlayer;

import serverlogic.ServerFacadeInterface;

/**
 * Die ClientMain-Class startet RMI-client.
 *
 * @author JES
 * @version 1.0
 */
public class ClientMain {
	
    public static final int RMI_REGISTRY_PORT = 1099;
	private static final ClientMain INSTANCE = new ClientMain(); 
	private ClientFacade clientFacade;
	private AudioPlayer player;
	private boolean running = false;

	/**
	 * Initialisiert Objekt dieser Klasse. Erzeugt intern AudioPlayer-Objekt,
	 * welches genutzt wird, solange die Applikation aktiv ist.
	 */
	private ClientMain() {
	    clientFacade = null;
	     try {
            player = new AudioPlayer();
        } catch(LineUnavailableException e) {
            System.err.println("Failed to create AudioPlayer instance.");
            e.printStackTrace();
        }
	    running = false;
	}

	/**
	 * Gibt ClientFacade-Objekt zurück, welches in der Methode startClient() erzeugt wurde. 
	 *
	 * @return  object clientFacade
	 */
	public static ClientFacade getClient() {
	    return INSTANCE.clientFacade;
	}

	/**
	 * Gibt AudioPlayer-Objekt zurück.
	 *
	 * @return object player
	 */
	public static AudioPlayer getPlayer() {
	    return INSTANCE.player;
	}

	/**
	 * Gibt ServerFacade zurück.
	 * @return object server
	 */
	public static ServerFacadeInterface getServer() {
	    return getClient().getServer();
	}

	/**
	 * Startet rmi-client durch Instanziierung der ClientFacade 
	 * und Eintragung in RMIRegistry
	 * @throws RemoteException bei belegtem Port
	 */
	public static void startClient() throws RemoteException {
	    if(!INSTANCE.running) {
	        INSTANCE.clientFacade = new ClientFacade();

            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(RMI_REGISTRY_PORT);
            } catch (ExportException e) {
                registry = LocateRegistry.getRegistry(RMI_REGISTRY_PORT);
            }
            // Facade vom Client erzeugt unter dem Name:mp3client
            registry.rebind("mp3client", INSTANCE.clientFacade);
            INSTANCE.running = true;
	    }
	}

	/**
	 * Stopt RMI-Client: entfernt ihn aus der Registry.
	 *
	 * @throws RemoteException bei RMI-Fehler
	 */
	public static void stopClient() throws RemoteException {
	    if(INSTANCE.running) {
            Registry registry = LocateRegistry.getRegistry(RMI_REGISTRY_PORT);
            try {
                registry.unbind("mp3client");
                INSTANCE.clientFacade = null;
            } catch(NotBoundException e) {
                e.printStackTrace();
            }

            INSTANCE.running = false;
        }
	}

	/**
	 * Gibt ServerFacade zurück, welche Methoden des Servers verfügbar macht
	 *
	 * @param serverIp IP-Adresse des Servers, nicht lokal verfügbar in dieser Klasse
	 * @return Instanz der ServerFacade
	 */
	public static ServerFacadeInterface getServer(String serverIp) {
		Registry registry = null;
		ServerFacadeInterface server = null;
        try {
            registry = LocateRegistry.getRegistry(serverIp, RMI_REGISTRY_PORT);
            server = (ServerFacadeInterface)registry.lookup("mp3server");
        } catch(RemoteException e) {
            e.printStackTrace();
        } catch(NotBoundException e) {
            e.printStackTrace();
        }
		return server;
	}
}
