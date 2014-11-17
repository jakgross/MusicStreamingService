package clientlogic;


import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import serverlogic.ServerFacadeInterface;

import Common.SongPackage;

import clientgui.ClientGUI;
import clientlogic.player.BufferedAudioInputStream;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;

/**
 * Implementiert Funktionalitäten, die es dem Server ermöglichen auf den Client zugreifen zu können
 * @author teamJES
 * @version 1.0
 */
public class ClientFacade extends UnicastRemoteObject implements ClientFacadeInterface {

	private static final long serialVersionUID = 6697125491942093772L;

	private String serverIP;
	private String ort;
	private JDialog synchroBlockade;

	/**
	 * Konstruktor der ClientFacade
	 * @throws RemoteException RMI-Fehler
	 */
	public ClientFacade() throws RemoteException {
		super();
		serverIP = null;
		ort = null;
	}
	
	/**
	 * Gibt ServerIP zurück
	 * @return String IP des Servers
	 */
	public String getServerIP() {
		return serverIP;
	}

	/**
	 * Gibt Ort des Clients zurück
	 * @return String Ort des Clients
	 */
	public String getOrt() {
		return ort;
	}
	
	/**
	 * Legt ServerIP fest
	 * @param IP IP-Adresse des Servers
	 */
	public void setServerIP(String IP) {
		serverIP = IP;
	}

	/**
	 * Legt Ort des Clients fest
	 * @param ort Ort des Clients
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * Gibt ServerFacadeInterface des Servers zurück.
	 * Wird Benötigt, um auf Funktionalität des Servers zugreifen zu können
	 * @return {@link ServerFacadeInterface} Instanz der ServerFacade
	 */
	public ServerFacadeInterface getServer() {
		return ClientMain.getServer(serverIP);
	}

	/**
	 * Öffnet ein DialogFenster, welches signalisiert, dass Client synchronisiert wird
	 * @return boolean true wenn Synchronisation erfolgreich gestartet
	 */
	@Override
	public boolean startSynchronisation() throws RemoteException {
		synchroBlockade = new JDialog(ClientGUI.getClientGUI());
		synchroBlockade.setVisible(false);
		synchroBlockade.setBounds(300, 300, 300, 300);
		synchroBlockade.setTitle("Synchronisiert");
		synchroBlockade.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		if (synchroBlockade.isVisible()) {
			return false;
		} else {
			synchroBlockade.getParent().setVisible(false);
			synchroBlockade.setVisible(true);
		}
		return true;
	}

	/**
	 * Blendet den SynchronisationsDialog wieder aus, 
	 * signalisiert, dass Synchronisation des Clients gestoppt ist
	 */
	@Override
	public void stopSynchronisation() throws RemoteException {
		synchroBlockade.setVisible(false);
		synchroBlockade.getParent().setVisible(true);

	}

	/**
	 * Pausiert die synchronisierte Wiedergabe auf dem Client
	 */
	@Override
	public void synchronisationPause() throws RemoteException {
		ClientMain.getPlayer().pause();
	}

	/**
	 * Nimmt die Synchronisierte Wiedergabe wieder auf, 
	 * wenn sie Pausiert wurde
	 */
	@Override
	public void synchronisationResume() throws RemoteException {
		ClientMain.getPlayer().resume();
	}

	/**
	 * Startet die Synchronisierte Wiedergabe auf dem Client
	 * @param p SongPackage, verpackter Stream, der abgespielt wird
	 */
	@Override
	public void synchronisationPlay(SongPackage p) throws RemoteException {
		try {
			InputStream in = RemoteInputStreamClient.wrap(p.getRemoteStream());
			BufferedAudioInputStream bain = new BufferedAudioInputStream(in, p.getAudioFormat());

			boolean on = ClientMain.getPlayer().isPlaying()
					|| ClientMain.getPlayer().isPaused();
			ClientMain.getPlayer().supply(bain, 0);
			if (!on)
				ClientMain.getPlayer().play();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Signalisiert dem Client-Benutzer, dass der Server geschlossen ist, 
	 * bei Bestätigen wird die Client-Software geschlossen
	 */
	@Override
	public void serverClosed() throws RemoteException {
		ClientMain.stopClient();
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JOptionPane.showMessageDialog(null,
						"Server geschlossen. Client wird beendet",
						"Server disconnect", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});
	}
}
