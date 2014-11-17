package serverlogic;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

/**
 * Die ServerMain-Klasse startet RMI-Server.
 * @author JES
 * @version 1.0
 */
public class ServerMain {
	// Port wählbar ab 1024
	public static final int RMI_REGISTRY_PORT = 1099;
	private static final ServerMain INSTANCE = new ServerMain();
	private ServerFacade serverFacade;
	// zeigt an, ob Server bereits gestartet
	private boolean running;

	/**
	 * Instanziiert neuen Server
	 */
	private ServerMain() {
		serverFacade = null;
		running = false;
	}
	
	/**
	 * Gibt Instanz der ServerFacade zurück
	 * @return {@link ServerFacade} 
	 */
	public static ServerFacade getServer() {
		return INSTANCE.serverFacade;
	}

	/**
	 * Startet den Server
	 *
	 * @throws RemoteException RMI-Fehler
	 */
	public static void startServer() throws RemoteException {
		
		// überprüfen ob RMI-Registry noch nicht gestartet ist
		if (!INSTANCE.running) {
			INSTANCE.serverFacade = new ServerFacade();
			// rejestr rmi do ktoregi sie klienci odwoluja,korzystaja
			Registry registry = null;
			try {
				// erstelle Registry auf diesem Port
				registry = LocateRegistry.createRegistry(RMI_REGISTRY_PORT);
			} catch (ExportException e) {
				// Registry läuft bereits, hole Referenz auf RMI-Registry
				registry = LocateRegistry.getRegistry(RMI_REGISTRY_PORT);
			}
			// schicke an RMI-Registry ServerFacade-Objekt
			registry.rebind("mp3server", INSTANCE.serverFacade);
			// speichern: RMI-Registry gestartet 
			INSTANCE.running = true;
		}
	}

	
	/**
	 * Stoppt dem Server.
	 *
	 * @throws RemoteException RMI-Fehler
	 */
	public static void stopServer() throws RemoteException {
		// wenn Registry in Betrieb
		if (INSTANCE.running) {
			// hole Referenz zu Registry
			Registry registry = LocateRegistry.getRegistry(RMI_REGISTRY_PORT);
			try {
				// löschen der Referenz zum ServerFacade-Objekt aus RMI Registry
				registry.unbind("mp3server");
				INSTANCE.serverFacade = null;
			} catch (NotBoundException e) {
				// laufender Server, aber keine Bindung zu RMI Registry
				e.printStackTrace();
			}
			INSTANCE.running = false;
		}
	}
}
