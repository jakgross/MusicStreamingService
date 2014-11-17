package clientgui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clientlogic.ClientMain;

import Common.Playlist;
import Common.Song;

/**
 * Dialog in dem Playlisten aus dem Server angezeigt und geöffnet werden können
 * @author JES
 * @version 1.0
 */

public class PlaylistDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = -7155722702425929435L;

	{
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel PlaylistPanel;
	private JPanel SongPanel;
	private JButton b_cancel;
	private JTable ta_playlists;
	private JTable ta_songTable;
	private JTextField t_searchField;
	private JButton b_startPlaylist;
	private JPanel PlaylistButtonPanel;
	private ArrayList<Playlist> playlist = new ArrayList<Playlist>();
	private ArrayList<Integer> songid = new ArrayList<Integer>();
	private Object[][] infosFinal;
	private Object[][] infosFinal2;
	private JScrollPane spane = new JScrollPane();
	private JScrollPane scroll = new JScrollPane();
	private ArrayList<Integer> playlistIDs = new ArrayList<Integer>();
	private ClientGUI clientGUI;

	/**
	 * Erzeut Playlist Dialog
	 * @param gui ParentFrame
	 */
	public PlaylistDialog(ClientGUI gui) {
		super();
		super.setModalityType(ModalityType.APPLICATION_MODAL);
		super.setTitle("Playliste öffnen");

		clientGUI = gui;
		initGUI();

		super.setLocationRelativeTo(null);
	}

	/**
	 * Initialisiert Komponenten des Playlist Dialogs
	 */
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					PlaylistPanel = new JPanel();
					getContentPane().add(PlaylistPanel);
					PlaylistPanel.setBounds(800, 12, 450, 350);
					PlaylistPanel.setLayout(null);
					{
						try {
							playlist = ClientMain.getServer().getAllPlaylists();
						} catch (Exception ex) {
						}
						if (playlist.size() != 0) {
							String[] columName = { "Playlisten" };
							ArrayList<Object[]> infos = new ArrayList<Object[]>();

							for (Playlist p : playlist) {
								Object[] info = new Object[2];
								info[1] = new Integer(p.getPlaylistID());

								playlistIDs.add(p.getPlaylistID());

								info[0] = p.getPlaylistName();
								infos.add(info);
							}
							Object[][] infosFinal3 = new Object[playlist.size()][2];
							for (int j = 0; j < playlist.size(); j++) {
								infosFinal3[j] = infos.get(j);
							}
							PlaylistPanel.remove(scroll);
							scroll = getPlaylistTableComponent(infosFinal3, columName);
							scroll.setSize(300, 200);
							PlaylistPanel.add(scroll);
							scroll.setBounds(30, 24, 250, 294);

						} else {
						    PlaylistPanel.remove(scroll);
						    
							String[] columName = {"Playlisten"};
							scroll = getPlaylistTableComponent(infosFinal2, columName);
							scroll.setSize(300, 200);
							PlaylistPanel.add(scroll);
							scroll.setBounds(30, 24, 250, 294);
						}
					}
					{
						SongPanel = new JPanel();
						getContentPane().add(SongPanel);
						SongPanel.setBounds(12, 12, 750, 350);
						SongPanel.setLayout(null);
						{
							try {
								playlist = ClientMain.getServer().getAllPlaylists();
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(getRootPane(),
												"Fehler beim Holen der Playlisten aus der DB");
							}
							if (playlist.size() != 0) {
								String[] columName = {"ID", "Titel", "Interpret", "Album", "Genre" };
								ArrayList<Object[]> rows = new ArrayList<Object[]>();
								ArrayList<Song> songs = new ArrayList<Song>();
								Object selected = getPlaylist();
								if (selected != null) {
									Song song = new Song("", "", "", "", "", "");
									songs.add(song);

    								for (int i = 0; i < playlist.size(); i++) {
    									if (selected.equals(playlist.get(i).getPlaylistName())) {
    										songs = playlist.get(i).getSongs();
    									}
    								}

    								for (Song s : songs) {
    									Object[] rowsFinal = new Object[5];
    									rowsFinal[0] = new Integer[s.getSongID()];

    									songid.add(s.getSongID());

    									rowsFinal[1] = s.getTitel();
    									rowsFinal[2] = s.getInterpret();
    									rowsFinal[3] = s.getAlbum();
    									rowsFinal[4] = s.getGenre();

    									rows.add(rowsFinal);

    								}
    								if (songs.size() == 0) {
    									infosFinal = new Object[1][5];
    								} else {
    									infosFinal = new Object[songs.size()][5];
    								}
    								for (int i = 0; i < rows.size(); i++) {
    									infosFinal[i] = rows.get(i);
    								}
								}

								SongPanel.remove(spane);

								spane = getSongTableComponent(infosFinal, columName);
								spane.setSize(600, 200);
								SongPanel.add(spane);
								spane.setBounds(30, 24, 720, 294);
							} else {
								SongPanel.remove(spane);

								String[] columName = {"ID", "Titel", "Interpret", "Album", "Genre" };
								spane = getSongTableComponent(infosFinal, columName);
								spane.setSize(600, 200);
								SongPanel.add(spane);
								spane.setBounds(30, 24, 720, 294);
							}
						}
					}
					{
						PlaylistButtonPanel = new JPanel();
						getContentPane().add(PlaylistButtonPanel);
						PlaylistButtonPanel.setBounds(24, 400, 838, 96);
						PlaylistButtonPanel.setLayout(null);
						{
							b_cancel = new JButton();
							PlaylistButtonPanel.add(b_cancel);
							b_cancel.setText("Abbrechen");
							b_cancel.setBounds(47, 31, 159, 39);

							b_cancel.addActionListener(new ActionListener() {
								@Override
                                public void actionPerformed(ActionEvent e) {
									dispose();
								}
							});
						}
						{
							b_startPlaylist = new JButton();
							PlaylistButtonPanel.add(b_startPlaylist);
							b_startPlaylist.setText("Playliste starten");
							b_startPlaylist.setBounds(258, 29, 187, 44);
							b_startPlaylist.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(ActionEvent e) {
										    Playlist playlist = getPlaylist();
										    if(playlist != null)
										        setVisible(false);
										    else
										        JOptionPane.showMessageDialog(null, "Playlist not chosen.", "Error", JOptionPane.ERROR_MESSAGE);
										}
									});
						}
						{
							t_searchField = new JTextField();
							PlaylistButtonPanel.add(t_searchField);
							t_searchField.setText("Lied suchen");
							t_searchField.setBounds(538, 29, 211, 41);
							t_searchField.getDocument().addDocumentListener(new DocumentListener() {
								
								@Override
								public void removeUpdate(DocumentEvent e) {
									searchAction();	
								}
			
								@Override
								public void insertUpdate(DocumentEvent e) {
									searchAction();
								}
			
								@Override
								public void changedUpdate(DocumentEvent e) {
									searchAction();	
								}
							});
						}
					}
				}
			}
			this.pack();
			this.setSize(1200, 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sucht Songs und erstellt Table mit Playlisten 
	 * in denen die gefunden Songs vorhanden sind
	 */
	private void searchAction() {
		try {
            ArrayList<Playlist> filteredPlaylists = ClientMain.getServer().searchSongClient(t_searchField.getText());
            String[] columName = { "Playlisten" };
            Object[][] infos = new Object[filteredPlaylists.size()][1];

            for(int i = 0; i < filteredPlaylists.size(); i++)
                infos[i][0] = filteredPlaylists.get(i).getPlaylistName();

            PlaylistPanel.remove(scroll);

            scroll = getPlaylistTableComponent(infos, columName);
            scroll.setSize(300, 200);
            PlaylistPanel.add(scroll);
            scroll.setBounds(30, 24, 250, 294);

            String[] columNames = {"ID", "Titel", "Interpret", "Album", "Genre" };
            infos = new Object[0][columNames.length];

            SongPanel.remove(spane);

            spane = getSongTableComponent(infos, columNames);
            spane.setSize(600, 200);
            SongPanel.add(spane);
            spane.setBounds(30, 24, 720, 294);

        } catch(RemoteException e1) {
            e1.printStackTrace();
        }	
	}
	
	/**
	 * Erstellt eine Table und fügt diese der ScrollPane hinzu 
	 * und gibt die Pane zurück
	 * 
	 * @param infos alle InputDaten für die Table
	 * @param columnNames alle Spaltennamen
	 * @return JScrollPane
	 */
	private JScrollPane getSongTableComponent(Object[][] infos, String[] columName) {
	    final String[] colNames = columName;
		final Object[][] info = infos;
		DefaultTableModel model = new DefaultTableModel(info, colNames) {

			private static final long serialVersionUID = -3209419976377197392L;

			@Override
			public Class<?> getColumnClass(int col) {
				return colNames[col].getClass();
			}

			@Override
            public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		ta_songTable = new JTable(model);
		ta_songTable.setRowSelectionAllowed(true);

		Dimension d = ta_songTable.getPreferredSize();
		ta_songTable.setPreferredScrollableViewportSize(d);
		ta_songTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ta_songTable.setSize(300, 400);

		return new JScrollPane(ta_songTable);
	}

	/**
	 * Erstellt eine Table und fügt diese der ScrollPane hinzu 
	 * und gibt die Pane zurück
	 * 
	 * @param infos alle InputDaten für die Table
	 * @param columnNames alle Spaltennamen
	 * @return JScrollPane
	 */
	private JScrollPane getPlaylistTableComponent(final Object[][] infos, final String[] columnNames) {
	    DefaultTableModel model = new DefaultTableModel(infos, columnNames) {

            private static final long serialVersionUID = -3209419976377197392L;

            @Override
            public Class<?> getColumnClass(int col) {
                return infos[0][col].getClass();
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        ta_playlists = new JTable(model);
        ta_playlists.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting())
                {
                    Playlist playlist = getPlaylist();
                    try {
                     ArrayList<Song> songs = ClientMain.getServer().openPlaylist(playlist, ClientMain.getClient().getOrt());
                        if (songs == null) {
                        	JOptionPane.showMessageDialog(null, "Playlist not available", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
	                        String[] columNames = { "ID", "Titel", "Interpret", "Album", "Genre" };
	
	                        clientGUI.SongPanel.remove(clientGUI.spane);
	
	                        clientGUI.spane = clientGUI.getTableComponent(songs, columNames);
	                        clientGUI.spane.setSize(600, 200);
	                        clientGUI.SongPanel.add(clientGUI.spane);
	                        clientGUI.spane.setBounds(30, 24, 720, 294);
	                        clientGUI.setSelectedPlaylist(playlist);
	
	                        SongPanel.remove(spane);
	
	                        Object[][] infos = new Object[songs.size()][columNames.length];
	                        for(int i = 0; i < songs.size(); i++)
	                        {
	                            Song song = songs.get(i);
	
	                            infos[i][0] = song.getSongID();
	                            infos[i][1] = song.getTitel();
	                            infos[i][2] = song.getInterpret();
	                            infos[i][3] = song.getAlbum();
	                            infos[i][4] = song.getGenre();
	                        }
	
	                        spane = getSongTableComponent(infos, columNames);
	                        spane.setSize(600, 200);
	                        SongPanel.add(spane);
	                        spane.setBounds(30, 24, 720, 294);
                        }
                    } catch(RemoteException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        });
        ta_playlists.setRowSelectionAllowed(true);

        Dimension d = ta_playlists.getPreferredSize();
        ta_playlists.setPreferredScrollableViewportSize(d);
        ta_playlists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ta_playlists.setSize(300, 400);
        
        return new JScrollPane(ta_playlists);
    }
	
	/**
	 * gibt markierte Playliste zurück
	 * @return Playlist markierte Playliste
	 */
	public Playlist getPlaylist() {
		if (ta_playlists.getSelectedRow() >= 0) {
			int row = ta_playlists.getSelectedRow();
			String name = (String)ta_playlists.getValueAt(row, 0);

			for(Playlist p : playlist)
			    if(p.getPlaylistName().equals(name))
			        return p;

			return null;
		} else
			return null;
	}
}
