package servergui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Common.Playlist;

/**
 * JPanel mit der Table auf der alle Playlisten angezeigt werden
 * @author teamJES
 *
 */
public class playlistPanel extends JPanel {

	private static final long serialVersionUID = 7371408049494594352L;
	private ArrayList<Integer> playlistIDs = new ArrayList<Integer>();//id der playliste in der tabele
	private JScrollPane scroll = new JScrollPane();
	private JTable table = new JTable();
	
	/**
	 * Konstruktor des PlaylistPanels
	 */
	public playlistPanel() {
		super();
		refreshPlaylists();
		}
	
	/**
	 * Die Methode wird aufgerufen, wenn  man Liste der Playlisten aktualisieren will
	 */
	void refreshPlaylists() {
		// löschen table mit "alten" Playlisten
		remove(table);
		playlistIDs.removeAll(playlistIDs);
		// erstelle eine leere Liste, um Playlisten zu laden
		List<Playlist> list = null;
		try{
			//holen aus der DB alle Playlisten
			list = ServerGUI.getDataHandler().ggetAllPlaylists();
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(getRootPane(), "Fehler beim Holen der Playlisten aus der DB");
		}
		if(list!=null){
			// wenn die Liste mit Playlisten nicht leer ist, erzeugen wir Liste mit Objekten, die beinhalten
			// Informationen über Playlisten: IDs und Namen von Playlisten
			ArrayList<Object[]> infos = new ArrayList<Object[]>();
			String[] columnNames = {/*"ID",*/ "Playlisten"};
			for(Playlist p : list){
				Object[] info = new Object[2];// table hat 2 Elemente: ID und Name
				info[1] = new Integer(p.getPlaylistID());
				
				playlistIDs.add(p.getPlaylistID());
				
				info[0] = p.getPlaylistName();
				infos.add(info);
				
			}
			//erzeugen Tabele mit soviel Zeilen wieviel Playlisten 
			// kopieren aus der liste in die table
			Object[][] infosFinal = new Object[list.size()][2];
			for (int j = 0; j < list.size(); j++){
				infosFinal[j] = infos.get(j);
			}
			// lösche scroll damit man einen auf anderen nicht erzeugt wird
			remove(scroll);
			// table mit aktuellen Informationen (aktuelle Liste der Playlisten)
			scroll = getTableComponent(infosFinal, columnNames);
			scroll.setSize(300,300);
			add(scroll);
		}
	}
	
	/**
	 * Erzeugt JScrollPane mit Table
	 * @return JScrollPane
	 */
	JScrollPane getTableComponent(Object[][] infos, String[] columnNames) {
		String[] colNames = columnNames;
		 final Object[][] info = infos;
		DefaultTableModel model = new DefaultTableModel(info, colNames){
			
			private static final long serialVersionUID = -1287985760547210158L;
		
			@Override
			public Class<?> getColumnClass(int col){
				return info[0][col].getClass();
			}
			// zeby tam gdzie klikamy na pola nie mogly byc edytowane
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		table = new JTable(model);
		// markieren ganze Zeile
		table.setRowSelectionAllowed(true);
		
		//korrekt zu scrollen irgendwie 
//		Dimension d = table.getPreferredSize();
		//wenn table ereicht diese Größe, wird scroll hinzugefügt
		table.setPreferredScrollableViewportSize(new Dimension(300,300));
		//einfache markierung
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSize(300,300);
	
		return new JScrollPane(table);
	}
	
	/**
	 * Die Methode gibt die markierte Playliste zurück
	 * @return int ID der selektierten Playliste
	 */
	public int getSelectedPlaylistID(){
		// überprüfen, ob irgendwelche zeile in table gewählt worden ist
		if(table.getSelectedRow() >= 0){
			return playlistIDs.get(table.getSelectedRow());
		} else{
			return -1;
		}
		
	}
}
