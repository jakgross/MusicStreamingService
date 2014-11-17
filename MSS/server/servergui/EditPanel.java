package servergui;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Common.Playlist;
import Common.Song;


/**
 * Erzeugt ein Dialog-Fenster, indem eine ausgewählte Playliste bearbeitet
 * werden kann
 * 
 * @author teamJES
 */
public class EditPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4468681836477353390L;
	// Erzeugung von TextFeldern um Name der Playliste zu bearbeiten und für Suche
	private JTextField t_PlaylistName = new JTextField();
	private JTextField t_search = new JTextField();

	// Erzeugung von Buttons, um Playlisten zu editieren
	private JButton b_add;
	private JButton b_delete;
	private JButton b_cancel;
	private JButton b_save;

	// Erzeugung von ScrollPanes um Tables anzuzeigen
	private JScrollPane s_scrollPanePlaylistsSongs = new JScrollPane();
	private JScrollPane s_scrollPaneAllSongs = new JScrollPane();

	// Erzeugung der Tables für alle Songs und die Songs der zu bearbeitenden Playliste
	private JTable songsTable = new JTable();
	private JTable playlistSongsTable = new JTable();

	// Erzeugung der Labels
	private JLabel songsLabel = new JLabel("Lieder in Datenbank");
	private JLabel playlistSongsLabel = new JLabel("Lieder in Playliste");
	private JLabel suchenLabel = new JLabel("Suche:");
	private JLabel playlistNameLabel = new JLabel("Playlist Name");

	// HilfsArray, für identifizierung der Lieder in Table
	private ArrayList<Integer> playlistSongsIDs = new ArrayList<Integer>();
	private ArrayList<Song> songs = new ArrayList<Song>();

//	private DataManager dataManager = new DataManager();
	private Playlist playlist;

	// Erzeugen des Dialogfensters
	private JDialog d;

	/**
	 * Erstellt das Dialogfenster mit Funktionalitäten um die übergebene
	 * Playliste zu editieren
	 * 
	 * @param serverGUI
	 *            uebergebenes Frame
	 * @param playlistID
	 *            ID der zu bearbeitenden Playliste
	 */
	public EditPanel(final ServerGUI serverGUI, int playlistID) {

		setLayout(null);

		// erzeuge neues Objekt Playliste nur mit id
		playlist = new Playlist(playlistID);

		// erzeuge Tables mit Liedern der Playliste und Table mit allen Liedern
		getPlaylistSongss();
		getSongss();

		// initialisieren des modalen Dialogs
		d = new JDialog(serverGUI, "Playliste editieren", true);

		// fügt Elemente dem Dialog hinzu
		d.add(this);

		// setze Position und Größe
		d.setBounds(0, 0, 1000, 600);

		songsLabel.setBounds(622, 20, 200, 20);
		playlistSongsLabel.setBounds(22, 20, 200, 20);

		suchenLabel.setBounds(400, 300, 100, 30);

		t_search.setBounds(400, 350, 100, 30);

		// Setze DocumentListener auf das Suchen-Textfeld
		t_search.getDocument().addDocumentListener(new DocumentListener() {

			/*
			 * führt Suche durch bei Löschen von Text aus Textfield
			 */
			@Override
			public void removeUpdate(DocumentEvent e) {
				searchAction();
			}

			/*
			 * führt Suche durch bei Einfügen von Text in Textfield
			 */
			@Override
			public void insertUpdate(DocumentEvent e) {
				searchAction();
			}

			/*
			 * führt Suche durch bei Änderungen im Textfield
			 */
			@Override
			public void changedUpdate(DocumentEvent e) {
				searchAction();
			}
		});

		playlistNameLabel.setBounds(400, 420, 100, 30);
		t_PlaylistName.setBounds(400, 470, 100, 30);
		t_PlaylistName.setColumns(5);
		add(songsLabel);
		add(playlistNameLabel);
		add(playlistSongsLabel);
		add(suchenLabel);
		add(t_search);
		add(t_PlaylistName);

		// Erzeuge Button für "Lied hinzufügen"
		b_add = new JButton("add Song");
		b_add.setBounds(400, 50, 150, 30);
		b_add.addActionListener(new ActionListener() {

			/*
			 * Überprüft ob ein Lied in SongTable markiert ist, wenn ja:
			 * überprüfe, ob Lied schon in PlaylistTable vorhanden, wenn ja:
			 * zeige Hinweis: "Lied schon in Playliste" wenn nicht: füge Lied in
			 * Playliste hinzu (noch nicht gespeichert) wenn nicht, zeige
			 * Hinweis: "Kein Lied markiert"
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				// Überprüfen, ob ein Lied markiert ist
				if (songsTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(new JRootPane(),
							"markiere Lied, um es hinzufügen zu können");
				} else {

					// Überprüfen ob Playlistsongsids ist ein id mit id des
					// Songs das wir hinzufügen wollen
					playlistSongsIDs.contains(songs.get(
							songsTable.getSelectedRow()).getSongID());

					// wenn nicht dann füge das markierte Song zur Plalyiste
					// hinzu
					if (!playlistSongsIDs.contains(songs.get(
							songsTable.getSelectedRow()).getSongID())) {

						// füge zur Playliste hinzu
						playlist.getSongs().add(
								songs.get(songsTable.getSelectedRow()));

						playlistSongsIDs.add(songs.get(
								songsTable.getSelectedRow()).getSongID());

						// aktualisieren des PlalyistTables
						refreshPlaylist();
						repaint();
					} else {
						// zeige Hinweis, dass Lied schon in Playliste vorhanden
						// ist
						JOptionPane.showMessageDialog(new JRootPane(),
								"Lied ist schon in der Playliste");
					}

				}
			}
		});
		{
			// füge Button zum Dialog hinzu
			add(b_add);
		}

		// erzeuge Button für markiertes Lied aus der Playliste zu entfernen
		b_delete = new JButton("delete Song");
		b_delete.setBounds(400, 100, 150, 30);
		b_delete.addActionListener(new ActionListener() {

			/*
			 * Entferne Lied aus Playliste überprüfe ob Lied in PlaylistTable
			 * markiert ist wenn ja: zeige ConfirmDialog, ob Lied wirklich aus
			 * Playliste entfernt werden soll lösche Lied, wenn bestätigt
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				// Wenn kein Lied markiert ist, zeige Hinweis: "markiere Lied"
				if (playlistSongsTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(new JRootPane(),
							"markiere zu entfernendes Lied");
				} else {
					// wenn Lied markiert ist, zeige ConfirmDialog, ob Lied
					// wirklich entfernt werden soll
					int ok = JOptionPane.showConfirmDialog(serverGUI,
							"Lied wirkich entfernen?", "Lied entfernen",
							JOptionPane.YES_NO_OPTION);

					// nach Bestätigung wird Lied aus der Playliste entfernt
					// (noch nicht gespeichert)
					if (ok == 0) {
						// Aus PlaylistID-Liste entfernen
						playlistSongsIDs.remove(playlistSongsTable
								.getSelectedRow());

						// Aus PlaylistTable entfernen
						playlist.getSongs().remove(
								playlistSongsTable.getSelectedRow());

						// PlaylistTable aktualisieren
						refreshPlaylist();
						repaint();
					}
				}
			}
		});
		{
			// füge Button zum Dialog hinzu
			add(b_delete);

		}

		// erzeuge Button um geänderte Playliste zu speichern und Dialogfenster
		// zu schließen
		b_save = new JButton("save");
		b_save.setBounds(400, 200, 150, 30);
		b_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				serverGUI.getPlaylistPanel().repaint();

				// Überprüft ob Playlistname angegeben
				if (t_PlaylistName.getText() == null || t_PlaylistName.getText().trim().equals("")) {

					JOptionPane.showMessageDialog(getRootPane(), "Bitte korrekten Namen angeben");
				} else {

					// Wenn kein PlaylistName angegeben zeige Hinweis
					// "Name angeben"
					playlist.setPlaylistName(t_PlaylistName.getText());
					// save als boolean, damit wir wissen ob playliste
					// erfolgreich gespeichert ist oder nicht
					if (ServerGUI.getDataHandler().gsavePlaylist(playlist) == true) {
						JOptionPane.showMessageDialog(new JRootPane(), "Playliste gespeichert");
					} else {
						JOptionPane.showMessageDialog(new JRootPane(), "Playliste existiert bereits, \nPlayliste wurde nicht gespeichert!");
					}
				}			
				d.dispose();
			}
		});
		{
			add(b_save);
		}
		// cancel button schliesst EditPanel und gehen wir zurück zum Hauptfenster
		b_cancel = new JButton("cancel");
		b_cancel.setBounds(400, 250, 150, 30);
		b_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
				d.dispose();
			}
		});
		{
			add(b_cancel);
		}
		d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		d.setVisible(true);
	}

	/**
	 * Holt die Songs aus der DB, lädt sie in Table und fügt sie der GUI hinzu
	 */
	private void getSongss() {
		// löschen erst vorher gespeichere Songs
		songs.removeAll(songs);
		// erzeugen neue Liste wo man Songs wieder ladet
		List<Song> list = null;
		try {
			list = ServerGUI.getDataHandler().getAllSongs();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getRootPane(),"Fehler beim Holen der Songs aud DB");
		}
		if (list != null) {
			// erzeuge object table, songs = elemente der table
			ArrayList<Object[]> infos = new ArrayList<Object[]>();
			String[] columnNames = {/* "ID", */"Titel", "Album", "Interpret",
					"Genre" };
			for (Song s : list) {

				Object[] info = new Object[5];
				info[4] = new Integer(s.getSongID());
				songs.add(s);
				// wegen null, damit es nicht "schriebt"
				info[0] = s.getTitel() == null ? "" : s.getTitel();
				info[1] = s.getAlbum() == null ? "" : s.getAlbum();
				info[2] = s.getInterpret() == null ? "" : s.getInterpret();
				info[3] = s.getGenre() == null ? "" : s.getGenre();
				// schreibt "unnamed" als titel und dateiname als Interpret
				if (info[0].equals("null")) {
					info[0] = "unnamed";
					info[1] = s.getFileName();
				}
				infos.add(info);

			}
			Object[][] infosFinal = new Object[list.size()][5];
			for (int j = 0; j < list.size(); j++) {
				infosFinal[j] = infos.get(j);//
			}

			remove(s_scrollPaneAllSongs);

			s_scrollPaneAllSongs = getTableComponent(infosFinal, columnNames,
					true);// boolean, weil wir zwei table haben,true für
							// songstable und false wenn playlistsongstable
			s_scrollPaneAllSongs.setBounds(620, 40, 350, 400);
			add(s_scrollPaneAllSongs);
		}
	}

	/**
	 * Holt die Songs zu einer Playliste aus der DB, lädt sie in Table und fügt
	 * sie der GUI hinzu
	 */
	private void getPlaylistSongss() {
		playlistSongsIDs.removeAll(playlistSongsIDs);
		ArrayList<Song> list = null;
		try {
			// laden alle Playlisten
			for (Playlist p : ServerGUI.getDataHandler().ggetAllPlaylists()) {
				// suchen welche wir editieren
				if (p.equals(playlist)) {
					playlist = p;
					// setzte Playlistname in Edietierfeld
					t_PlaylistName.setText(playlist.getPlaylistName());
					break;
				}
			}
			list = playlist.getSongs();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(getRootPane(),
					"Fehler beim Holen der Songs aud DB");
		}

		if (list != null) {
			ArrayList<Object[]> infos = new ArrayList<Object[]>();
			// falls wir alles anzeigen wollen
			// String[] columnNames = { "ID", "Titel", "Album", "Interpret",
			// "Genre" };
			String[] columnNames = { "Titel", "Interpret" };
			for (Song s : list) {
				Object[] info = new Object[5];
				playlistSongsIDs.add(s.getSongID());
				info[3] = new Integer(s.getSongID());
				info[0] = s.getTitel() == null ? "" : s.getTitel();
				info[2] = s.getAlbum() == null ? "" : s.getAlbum();
				info[1] = s.getInterpret() == null ? "" : s.getInterpret();
				info[4] = s.getGenre() == null ? "" : s.getGenre();
				infos.add(info);

			}
			Object[][] infosFinal = new Object[list.size()][5];
			for (int j = 0; j < list.size(); j++) {
				infosFinal[j] = infos.get(j);
			}

			remove(s_scrollPanePlaylistsSongs);

			s_scrollPanePlaylistsSongs = getTableComponent(infosFinal,
					columnNames, false);
			s_scrollPanePlaylistsSongs.setBounds(20, 40, 350, 400);
			add(s_scrollPanePlaylistsSongs);
		}

	}

	/**
	 * Lädt die Songs einer Playliste erneut (nicht aus DB) und fügt sie der GUI
	 * hinzu
	 */
	void refreshPlaylist() {

		List<Song> list = playlist.getSongs();

		if (list != null) {
			ArrayList<Object[]> infos = new ArrayList<Object[]>();
			// String[] columnNames = { "ID", "Titel", "Album",
			// "Interpret","Genre" };
			String[] columnNames = { "Titel", "Interpret" };

			for (Song s : list) {
				Object[] info = new Object[5];
				info[3] = new Integer(s.getSongID());
				info[0] = s.getTitel() == null ? "" : s.getTitel();
				info[2] = s.getAlbum() == null ? "" : s.getAlbum();
				info[1] = s.getInterpret() == null ? "" : s.getInterpret();
				info[4] = s.getGenre() == null ? "" : s.getGenre();
				// schreibt "unnamed" als titel und dateiname als Interpret
				if (info[0].equals("null")) {
					info[0] = "unnamed";
					info[1] = s.getFileName();
				}
				infos.add(info);

			}
			Object[][] infosFinal = new Object[list.size()][5];
			for (int j = 0; j < list.size(); j++) {
				infosFinal[j] = infos.get(j);
			}

			remove(s_scrollPanePlaylistsSongs);

			s_scrollPanePlaylistsSongs = getTableComponent(infosFinal,
					columnNames, false);
			s_scrollPanePlaylistsSongs.setBounds(20, 40, 350, 400);
			add(s_scrollPanePlaylistsSongs);
		}

	}

	/**
	 * Erstellt eine Table und fügt diese der ScrollPane hinzu und gibt die Pane
	 * zurück
	 * 
	 * @param infos
	 *            alle InputDaten für die Table
	 * @param columnNames
	 *            alle Spaltennamen
	 * @param isSongEdited
	 *            definiert den Typ der Table, Song- oder Playlist-Table
	 * @return JScrollPane
	 */
	JScrollPane getTableComponent(Object[][] infos, String[] columnNames,
			boolean isSongEdited) {
		remove(songsTable);
		String[] colNames = columnNames;
		final Object[][] info = infos;
		DefaultTableModel model = new DefaultTableModel(info, colNames) {

			private static final long serialVersionUID = -5154386985716823050L;

			@Override
			public Class<? extends Object> getColumnClass(int col) {
				return info[0][col].getClass();
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		JTable table = new JTable(model);
		table.getSelectionModel();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(table.getRowCount());
		table.setRowSelectionAllowed(true);
		table.setPreferredScrollableViewportSize(new Dimension(300, 300));
		if (isSongEdited) {
			songsTable = table;
		} else {
			playlistSongsTable = table;
		}
		return new JScrollPane(table);
	}

	/**
	 * Sucht Songs und erstellt Table mit gefunden Song/s
	 */
	public void searchAction() {
		if (t_search.getText().equals("")) {
			// alle Songs holen
			getSongss();

		} else {
			songs.removeAll(songs);
			ArrayList<Song> list = null;
			try {
				list = ServerGUI.getDataHandler().gsearchSongAdmin(t_search.getText());
				for (Song s : list) {
					System.out.println(s.getTitel());
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(getRootPane(),
						"Fehler beim Holen der Songs aus DB");
			}
			if (list != null) {
				ArrayList<Object[]> infos = new ArrayList<Object[]>();
				String[] columnNames = { "Titel", "Album", "Interpret", "Genre" };
				for (Song s : list) {
					Object[] info = new Object[5];
					info[4] = new Integer(s.getSongID());

					songs.add(s);
					info[0] = s.getTitel();
					info[1] = s.getAlbum();
					info[2] = s.getInterpret();
					info[3] = s.getGenre();
					// schreibt "unnamed" als titel und dateiname als Interpret
					if (info[0].equals("null")) {
						info[0] = "unnamed";
						info[1] = s.getFileName();
					}
					infos.add(info);

				}
				Object[][] infosFinal = new Object[list.size()][5];
				for (int j = 0; j < list.size(); j++) {
					infosFinal[j] = infos.get(j);
				}

				remove(s_scrollPaneAllSongs);
				// die Liste wird "gefiltert" und angezeigt
				s_scrollPaneAllSongs = getTableComponent(infosFinal,
						columnNames, true);
				s_scrollPaneAllSongs.setBounds(620, 40, 350, 400);
				add(s_scrollPaneAllSongs);
			}
		}
	}
}