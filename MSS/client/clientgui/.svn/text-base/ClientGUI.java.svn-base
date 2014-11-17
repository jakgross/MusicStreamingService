package clientgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;


import Common.Playlist;
import Common.Song;
import Common.SongPackage;

import clientlogic.ClientMain;
import clientlogic.player.AudioObserver;
import clientlogic.player.BufferedAudioInputStream;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;

/**
 * Startet den Client, initialisiert ClientGUI
 * @author JES
 * @version 1.0
 */
public class ClientGUI extends javax.swing.JFrame implements WindowListener, AudioObserver {

	private static final long serialVersionUID = 6540565644183103472L;
	{
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel ClientMainButtonPanel;
	private JButton b_play_resume;
	private JButton b_openPlaylist;
	private JButton b_stopSynchro;
	private JButton b_prev;
	private JButton b_next;
	private JTable ta_songTable;
	private JButton b_changeOrt;
	private JButton b_pause;
	private JButton b_closeClient;
	private JButton b_openstartSynchro;
	JPanel SongPanel;
	JScrollPane spane = new JScrollPane();
	private boolean logged;
	private int playingRow;
	private Playlist selectedPlaylist;
	private static ClientGUI clientGUI;

	/**
	 * Startet Client
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ClientMain.startClient();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					clientGUI = new ClientGUI();
					clientGUI.setLocationRelativeTo(null);

					if (clientGUI.isLogged()) {
						clientGUI.setVisible(true);
						clientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

						ClientMain.getPlayer().addAudioObserver(clientGUI);
					}
				}
			});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * initialisiert Hauptfenster des Clients
	 */
	public ClientGUI() {
		super("MSS Client");

		clientGUI = this;
		logged = false;
		playingRow = -1;
		selectedPlaylist = null;

		initGUI();

		addWindowListener(this);
	}
	
	/**
	 * gibt markiertes Lied aus SongTable zurück
	 * @return Song
	 */
	public Song getSong() {
		int row = ta_songTable.getSelectedRow();
		if (row >= 0)
			return (Song) ta_songTable.getValueAt(row, -1);
		else
			return null;
	}
	
	/**
	 * Gibt markierte Playliste zurück
	 * @return Playlist
	 */
	public Playlist getSelectedPlaylist() {
		return selectedPlaylist;
	}

	/**
	 * setzt markierte Playliste, aktualisiert PlaylistTable
	 * @param playlist
	 */
	public void setSelectedPlaylist(Playlist playlist) {
		this.selectedPlaylist = playlist;
	}
	
	/**
	 * initialisiert GUI Komponenten des Clients
	 */
	private void initGUI() {
		try {
			{
				ClientMainButtonPanel = new JPanel();
				ClientMainButtonPanel.setLayout(null);
				getContentPane().add(ClientMainButtonPanel, BorderLayout.SOUTH);
				ClientMainButtonPanel.setPreferredSize(new java.awt.Dimension(784, 113));
				{
					b_play_resume = new JButton();
					ClientMainButtonPanel.add(b_play_resume);
					b_play_resume.setLayout(null);
					b_play_resume.setText("Play/Resume");
					b_play_resume.setBounds(18, 12, 115, 30);
					b_play_resume.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								if (playingRow == ta_songTable.getSelectedRow() && playingRow != -1) {
									try {
										ClientMain.getServer().resume(ClientMain.getClient().getOrt());
									} catch (RemoteException e) {
										e.printStackTrace();
									}

									ClientMain.getPlayer().resume();
								} else {
									Song song = getSong();
									if (song == null && ta_songTable.getRowCount() > 0) {
										ta_songTable.getSelectionModel() .setSelectionInterval(0, 0);
										song = getSong();
									}
									if (song != null) {
										SongPackage p = ClientMain.getServer().play(ClientMain.getClient().getOrt(),
														song, selectedPlaylist);

										if (p != null) {
											try {
												InputStream in = RemoteInputStreamClient.wrap(p.getRemoteStream());
												BufferedAudioInputStream bain = new BufferedAudioInputStream(in, p.getAudioFormat());

												ClientMain.getPlayer().supply(bain, 0);
												if (playingRow == -1)
													ClientMain.getPlayer().play();

												playingRow = ta_songTable.getSelectedRow();
											} catch (IOException e) {
												e.printStackTrace();
											} catch (LineUnavailableException e) {
												e.printStackTrace();
											}
										} else
											JOptionPane.showMessageDialog(null,"Lied nicht auf Server gefunden.",
															"Error", JOptionPane.ERROR_MESSAGE);
									}
								}
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					});
				}
				{
					b_openPlaylist = new JButton();
					ClientMainButtonPanel.add(b_openPlaylist);
					b_openPlaylist.setText("Playliste öffnen");
					b_openPlaylist.setBounds(282, 34, 123, 42);

					b_openPlaylist.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							PlaylistDialog dialog = new PlaylistDialog(clientGUI);
							dialog.setVisible(true);
						}
					});
				}
				{
					b_openstartSynchro = new JButton();
					ClientMainButtonPanel.add(b_openstartSynchro);
					b_openstartSynchro.setText("Synchronisation starten");
					b_openstartSynchro.setBounds(417, 13, 167, 30);

					b_openstartSynchro.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							SynchroDialog dialog = new SynchroDialog(clientGUI);
							dialog.setVisible(true);
						}
					});
				}
				{
					b_stopSynchro = new JButton();
					ClientMainButtonPanel.add(b_stopSynchro);
					b_stopSynchro.setText("Synchronisation stoppen");
					b_stopSynchro.setBounds(417, 65, 167, 30);
					b_stopSynchro.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								ClientMain.getServer().stopSynchronisation(ClientMain.getClient().getOrt());
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
					});
				}
				{
					b_closeClient = new JButton();
					ClientMainButtonPanel.add(b_closeClient);
					b_closeClient.setText("Programm beenden");
					b_closeClient.setBounds(611, 64, 149, 32);

					b_closeClient.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								ClientMain.getServer().logoutClient(ClientMain.getClient().getOrt());
							} catch (RemoteException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							System.exit(0);
						}
					});
				}
				{
					b_pause = new JButton();
					ClientMainButtonPanel.add(b_pause);
					b_pause.setText("Pausieren");
					b_pause.setBounds(149, 13, 115, 30);
					b_pause.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								ClientMain.getServer().pause(ClientMain.getClient().getOrt());
							} catch (RemoteException ex) {
								ex.printStackTrace();
							}
							ClientMain.getPlayer().pause();
						}
					});
				}
				{
					b_changeOrt = new JButton();
					ClientMainButtonPanel.add(b_changeOrt);
					b_changeOrt.setText("Ort ändern");
					b_changeOrt.setBounds(611, 13, 149, 30);

					b_changeOrt.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							LoginDialog dialog = new LoginDialog(clientGUI, logged);
							dialog.setVisible(true);
						}
					});
				}
				{
					b_next = new JButton();
					ClientMainButtonPanel.add(b_next);
					b_next.setText("Nächstes Lied");
					b_next.setBounds(149, 65, 115, 28);
					b_next.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (playingRow != -1 && playingRow < ta_songTable.getRowCount() - 1) {
								try {
									SongPackage p = ClientMain.getServer().forward(ClientMain.getClient().getOrt());

									if (p != null) {
										InputStream in = RemoteInputStreamClient.wrap(p.getRemoteStream());
										BufferedAudioInputStream bain = new BufferedAudioInputStream(in, p.getAudioFormat());

										ClientMain.getPlayer().supply(bain, 0);

										playingRow++;
										ta_songTable.getSelectionModel().setSelectionInterval(playingRow, playingRow);
									}
								} catch (RemoteException e1) {
									e1.printStackTrace();
								} catch (IOException ex) {
									ex.printStackTrace();
								} catch (LineUnavailableException ex) {
									ex.printStackTrace();
								}
							}
						}
					});
				}
				{
					b_prev = new JButton();
					ClientMainButtonPanel.add(b_prev);
					b_prev.setText("Vorheriges Lied");
					b_prev.setBounds(18, 65, 115, 28);

					b_prev.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (playingRow != -1 && playingRow > 0) {
								try {
									SongPackage p = ClientMain.getServer().prev(ClientMain.getClient().getOrt());
									if (p != null) {
										InputStream in = RemoteInputStreamClient.wrap(p.getRemoteStream());
										BufferedAudioInputStream bain = new BufferedAudioInputStream(in, p.getAudioFormat());

										ClientMain.getPlayer().supply(bain, 0);

										playingRow--;
										ta_songTable.getSelectionModel().setSelectionInterval(playingRow, playingRow);
									}
								} catch (RemoteException e1) {
									e1.printStackTrace();
								} catch (IOException ex) {
									ex.printStackTrace();
								} catch (LineUnavailableException ex) {
									ex.printStackTrace();
								}
							}
						}
					});
				}
			}
			{
				SongPanel = new JPanel();
				getContentPane().add(SongPanel, BorderLayout.CENTER);
				SongPanel.setLayout(null);
				{
					String[] columName = {"ID","Titel", "Interpret", "Album", "Genre"};
					ArrayList<Song> songs = new ArrayList<Song>();

					SongPanel.remove(spane);

					spane = getTableComponent(songs, columName);
					spane.setSize(600, 200);
					SongPanel.add(spane);
					spane.setBounds(30, 24, 720, 294);
				}
			}

			this.pack();
			this.setSize(800, 500);

			LoginDialog dialog = new LoginDialog(this, logged);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Erzeugt JScrollPane mit gefüllter Table
	 * @param songs Lieder, die angezeigt werden sollen
	 * @param columName Header der Spalten 
	 * @return erzeugte ScrollPane mit Table
	 */
	JScrollPane getTableComponent(final ArrayList<Song> songs, final String[] columName) {

		AbstractTableModel model = new AbstractTableModel() {

			private static final long serialVersionUID = -3076856179100742723L;

			@Override
			public int getRowCount() {
				return songs.size();
			}

			@Override
			public int getColumnCount() {
				return 5;
			}

			@Override
			public String getColumnName(int column) {
				return columName[column];
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Song song = songs.get(rowIndex);

				switch (columnIndex) {
				case 0:
					return song.getSongID();
				case 1:
					return song.getTitel();
				case 2:
					return song.getInterpret();
				case 3:
					return song.getAlbum();
				case 4:
					return song.getGenre();
				default:
					return song;
				}
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		ta_songTable = new JTable(model);
		// markieren ganze Zeile
		ta_songTable.setRowSelectionAllowed(true);
		// korrekt zu scrollen irgendwie
		Dimension d = ta_songTable.getPreferredSize();
		ta_songTable.setPreferredScrollableViewportSize(d);
		ta_songTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ta_songTable.setSize(300, 400);

		return new JScrollPane(ta_songTable);
	}

	/**
	 * boolean, ob Client im Server eingeloggt ist
	 * @param logged
	 */
	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	/**
	 * überprüft, ob Client im Server eingeloggt ist
	 * @return true wenn eingeloggt
	 */
	public boolean isLogged() {
		return logged;
	}

	/**
	 * WindowEvent
	 */
	@Override
	public void windowOpened(WindowEvent e) {
	}
	
	/**
	 * WindowEvent
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		windowClosed(e);
	}

	/**
	 * Aktion, wenn Fenster über Window-Buttons geschlossen wird.
	 * Schließt Client und loggt ihn aus Server aus
	 * @param e WindowEvent, der bei schließen des Fensters ausgeführt wird
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		try {
			if (logged)
				ClientMain.getServer().logoutClient(ClientMain.getClient().getOrt());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * WindowEvent
	 */
	@Override
	public void windowIconified(WindowEvent e) {
	}

	/**
	 * WindowEvent
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	/**
	 * WindowEvent
	 */
	@Override
	public void windowActivated(WindowEvent e) {
	}

	/**
	 * WindowEvent
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
	/**
	 * {@link AudioObserver#onPlay()}
	 */
	@Override
	public void onPlay() {
	}
	
	/**
	 * {@link AudioObserver#onPause()}
	 */
	@Override
	public void onPause() {
	}
	
	/**
	 * {@link AudioObserver#onResume()}
	 */
	@Override
	public void onResume() {
	}

	/**
	 * {@link AudioObserver#onMediaTimeChanged(long, long)}
	 */
	@Override
	public void onMediaTimeChanged(long oldMediaTime, long newMediaTime) {
	}
	
	/**
	 * wird benötigt, damit GUI weiß, dass Lied beendet ist und zum nächsten springen kann
	 * {@link AudioObserver#onStop(boolean)}
	 */
	@Override
	public void onStop(boolean premature) {
		if (playingRow != -1 && playingRow < ta_songTable.getRowCount() - 1) {
			try {
				SongPackage p = ClientMain.getServer().forward(ClientMain.getClient().getOrt());

				InputStream in = RemoteInputStreamClient.wrap(p.getRemoteStream());
				BufferedAudioInputStream bain = new BufferedAudioInputStream(in, p.getAudioFormat());

				ClientMain.getPlayer().supply(bain, 0);
				ClientMain.getPlayer().play();

				playingRow++;
				ta_songTable.getSelectionModel().setSelectionInterval(playingRow, playingRow);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (LineUnavailableException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Gibt HauptFenster zurück
	 * @return {@link ClientGUI}
	 */
	public static ClientGUI getClientGUI() {
		return clientGUI;
	}
}