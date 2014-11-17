package servergui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import serverlogic.ServerDataHandler;
import serverlogic.ServerMain;

/**
 * Das Hauptfenster des Servers. 
 * Wird aufgerufen bei Serverstart, kümmert sich um den korrekten Start des Servers.
 * @author teamJES
 *
 */
public class ServerGUI extends JFrame implements WindowListener {

	private static final long serialVersionUID = 5636314735422407849L;

	private playlistPanel playlistPanel;
	private JPanel mainButtonPanel;
	
	// Bietet Zugriff auf Methoden der Daten-Schicht
	private static ServerDataHandler sdh = new ServerDataHandler();

	{
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * startet das Programm
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerMain.startServer();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					ServerGUI gui = new ServerGUI();
					gui.setLocationRelativeTo(null);
					gui.setVisible(true);
					gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			});
		} catch (RemoteException e) {
			System.err.println("Konnte RMI Server nicht starten.");
			e.printStackTrace();
		}
	}

	/**
	 * Startet die GUI und überprüft Basisordner
	 */
	public ServerGUI() {
		super("MSS Server");

		super.setVisible(false);
		if (!sdh.checkBasisordner()) {
			JOptionPane.showMessageDialog(super.getRootPane(), "Wähle Basisordner");

			// Basisordner wählen
			if (fpathChoosert() == true) {
				super.setVisible(true);
			} else {
				System.exit(0);
			}
		} else {
			super.setVisible(true);
		}
		initGUI();

		super.addWindowListener(this);
		super.setMinimumSize(new Dimension(640, 480));
		super.setResizable(false);
		super.pack();
	}

	/**
	 * Dialog um Basisordner zu wählen bei erstem Serverstart
	 * 
	 * @return true wenn Basisordner erfolgreich gespeichert
	 */
	private boolean fpathChoosert() {
		JFileChooser f_pathChooser = new JFileChooser();
		f_pathChooser.setMaximumSize(new Dimension(400, 300));
		f_pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = f_pathChooser.showSaveDialog(super.getRootPane());
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = f_pathChooser.getSelectedFile();
			if (!saveBordner(file.getAbsolutePath().toString())) {
				JOptionPane.showMessageDialog(super.getRootPane(),
						"Bitte korrekten Basisordner wählen!");
				fpathChoosert();
			} else {
				JOptionPane.showMessageDialog(super.getRootPane(),
						"Basisordner indiziert");
				return true;
			}
		} else if (returnValue == JFileChooser.CANCEL_OPTION) {
			f_pathChooser.cancelSelection();
			return false;
		}
		return true;
	}

	/**
	 * Initialisiert GUI
	 */
	private void initGUI() {

		try {
			super.getContentPane().setBackground(new Color(214, 217, 223));
			GridBagLayout serverMainLayout = new GridBagLayout();
			super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			serverMainLayout.rowWeights = new double[] { 0.1, 0.1, 0.1, 0.1 };
			serverMainLayout.rowHeights = new int[] { 7, 7, 7, 7 };
			serverMainLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1 };
			serverMainLayout.columnWidths = new int[] { 97, 139, 256, 7 };
			super.getContentPane().setLayout(serverMainLayout);
			{
				playlistPanel = new playlistPanel();
				playlistPanel.refreshPlaylists();
				super.getContentPane().add(
						playlistPanel,
						new GridBagConstraints(0, 0, 3, 4, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH, new Insets(50, 50, 50,
										50), 0, 0));
			}
			{
				mainButtonPanel = new MainButtonPanel(this);
				super.getContentPane().add(
						mainButtonPanel,
						new GridBagConstraints(3, 0, 1, 4, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.VERTICAL, new Insets(0, 0,
										0, 0), 0, 0));
			}

			super.pack();
			super.setSize(400, 300);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(super.getRootPane(),
					"Fehler, konnte GUI nicht initialisieren!");
			e.printStackTrace();
		}
	}

	/**
	 * damit editPanel zugand zur PlaylistPanel hat
	 * 
	 * @return playlistPanel
	 */
	public playlistPanel getPlaylistPanel() {
		return playlistPanel;
	}

	/**
	 * Aktualisiert den Basisordner
	 * 
	 * @return true, wenn Basisordner erfolgreich aktualisiert wurde
	 */
	public boolean startRefresh() {
		if (sdh.grefreshBasisordner() == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * wechselt den Basisordner auf den neu angegeben
	 * 
	 * @param bordner
	 *            pfad zu neuem Basisordner
	 * @return true wenn erfolgreich geändert
	 */
	public boolean changeBordner(String bordner) {
		return sdh.gchangeBasisordner(bordner);
	}

	/**
	 * speichert übergebenen Basisordner
	 * 
	 * @param bordner
	 *            pfad zu übergebenem Basisordner
	 * @return true wenn gespeichert
	 */
	public boolean saveBordner(String bordner) {
		return sdh.gsaveBasisordner(bordner);
	}

	/**
	 * gibt den gespeicherten Basisordner zurück
	 * 
	 * @return File gespeicherter Basisordner
	 */
	public File getBOrdner() {
		return sdh.ggetBasisordner();
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		windowClosed(e);
	}

	/**
	 * führt die Methode stopServer aus, schließt RMI und trennt die Clients
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		ServerMain.getServer().close();

		try {
			ServerMain.stopServer();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
	public static ServerDataHandler getDataHandler() {
		return sdh;
	}
}