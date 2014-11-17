package servergui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import serverlogic.ServerFacade;
import serverlogic.ServerMain;

/**
 * JPanel mit allen Buttons die die ServerGUI steuern
 */
public class MainButtonPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5451078807710088898L;
	
	/** The b_create playlist. */
	private JButton b_createPlaylist;
	
	/** The b_edit playlist. */
	private JButton b_editPlaylist;
	
	/** The b_delete playlist. */
	private JButton b_deletePlaylist;
	
	/** The b_change basisordner. */
	private JButton b_changeBasisordner;
	
	/** The b_refresh basisordner. */
	private JButton b_refreshBasisordner;
	
	/** The b_ exit. */
	private JButton b_Exit;
	
	/** The f_path chooser. */
	private JFileChooser f_pathChooser = new JFileChooser();
	
	/** The blocked playlists. */
	private static HashSet<Integer> blockedPlaylists = new HashSet<Integer>();

	/**
	 * Instanziiert das MainButtonPanel
	 *
	 * @param serverGUI HauptFenster des Servers
	 */
	public MainButtonPanel(final ServerGUI serverGUI) {
		
		GridBagLayout mainButtonPanelLayout = new GridBagLayout();
		mainButtonPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
		mainButtonPanelLayout.rowHeights = new int[] {7, 20, 7, 20, 7, 7};
		mainButtonPanelLayout.columnWeights = new double[] {0.1};
		mainButtonPanelLayout.columnWidths = new int[] {7};
		
		setLayout(mainButtonPanelLayout);
		{
			b_createPlaylist = new JButton();
			add(b_createPlaylist, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			b_createPlaylist.setText("Playliste erstellen");
			b_createPlaylist.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ServerGUI.getDataHandler().createPlaylist();
					serverGUI.getPlaylistPanel().refreshPlaylists();
					serverGUI.getPlaylistPanel().repaint();
				}
			});
		}
		{
			b_editPlaylist = new JButton();
			add(b_editPlaylist, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			b_editPlaylist.setText("Playliste editieren");			
			
			b_editPlaylist.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int playlistID = serverGUI.getPlaylistPanel().getSelectedPlaylistID();
					if (playlistID < 0) {
						JOptionPane.showMessageDialog(new JRootPane(),
								"Die Playliste zum Editieren ist noch nicht gewählt");}
					else {
						if (ServerFacade.getOpenedPlaylists().contains(playlistID)) {
							JOptionPane.showMessageDialog(new JRootPane(),
							"Playliste wird gerade abgespielt - kann nicht editiert werden! Sorry :(");
						} else {
							blockedPlaylists.add(playlistID);
							new EditPanel(serverGUI, playlistID);
							serverGUI.getPlaylistPanel().refreshPlaylists();
							serverGUI.getPlaylistPanel().repaint();
							blockedPlaylists.remove(playlistID);
						}
					}
					
				}
			});
			
		}
		{
			b_deletePlaylist = new JButton();
			add(b_deletePlaylist, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			b_deletePlaylist.setText("Playliste entfernen");
			b_deletePlaylist.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					int playlistID = serverGUI.getPlaylistPanel().getSelectedPlaylistID();
					if (serverGUI.getPlaylistPanel().getSelectedPlaylistID() == -1) {
						JOptionPane.showMessageDialog(new JRootPane(),"markiere Playliste, um sie löschen zu können");
					}else{
						if (ServerFacade.getOpenedPlaylists().contains(playlistID)) {
							JOptionPane.showMessageDialog(new JRootPane(),
							"Playliste wird gerade abgespielt - kann nicht gelöscht werden! Sorry :(");
						}else
						if(JOptionPane.showConfirmDialog(new JRootPane(), "wirklich löschen?", "Playliste löschen", JOptionPane.YES_NO_OPTION) == 0){
							ServerGUI.getDataHandler().gdeletePlaylist(serverGUI.getPlaylistPanel().getSelectedPlaylistID());
							serverGUI.getPlaylistPanel().refreshPlaylists();
							serverGUI.getPlaylistPanel().repaint();
						}
					
					}
				}
			});
		}
		{
			b_changeBasisordner = new JButton();
			add(b_changeBasisordner, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			b_changeBasisordner.setText("Ordner wechseln");
			f_pathChooser.setMinimumSize(new Dimension(400,300));
			f_pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			File file = ServerGUI.getDataHandler().ggetBasisordner();
			f_pathChooser.setCurrentDirectory(file);
			b_changeBasisordner.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					int returnValue = f_pathChooser.showSaveDialog(getRootPane());
					if (returnValue == JFileChooser.APPROVE_OPTION) {
			            File file = f_pathChooser.getSelectedFile();
						if (ServerGUI.getDataHandler().gchangeBasisordner(file.getAbsolutePath().toString())==true) {
							serverGUI.getPlaylistPanel().refreshPlaylists();
						    serverGUI.getPlaylistPanel().repaint();
							JOptionPane.showMessageDialog(getRootPane(), "Basisordner erfolgreich gespeichert");
						}
						else {
							JOptionPane.showMessageDialog(getRootPane(), "Basisordner konnte nicht gespeichert werden, sorry!!");
						}
					}
					else if (returnValue == JFileChooser.CANCEL_OPTION) {
						f_pathChooser.cancelSelection();
					}
				}
			});
		}
		{
			b_refreshBasisordner = new JButton();
			add(b_refreshBasisordner, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			b_refreshBasisordner.setText("Ordner refresh");
			b_refreshBasisordner.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evtt) {
					System.out.println("refresh Basisordner");
					
					
					if (ServerGUI.getDataHandler().grefreshBasisordner()==true){
						serverGUI.getPlaylistPanel().refreshPlaylists();
					    serverGUI.getPlaylistPanel().repaint();
					    
						JOptionPane.showMessageDialog(getRootPane(), "Basisordner erfolgreich aktualisiert");
					} else {
						JOptionPane.showMessageDialog(getRootPane(), "Basisordner konnte nicht aktualisiert werden, sorry!!");
					}
				}
			});
		}
		{
			b_Exit = new JButton();
			add(b_Exit, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			b_Exit.setText("EXIT");
			b_Exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					ServerMain.getServer().close();
					System.exit(0);
				}
			});
		}
	}
	
	/**
	 * Gibt blockierte Playlisten zurück
	 *
	 * @return HashSet<Integer> HashSet mit blockierten Playlisten
	 */
	public static HashSet<Integer> getBlockedPlaylists() {
		return blockedPlaylists;
	}
}
