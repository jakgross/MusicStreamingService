package clientgui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import clientlogic.ClientMain;

import Common.Client;

/**
 * Dialog Fenster, welches im Server angemeldete Clients anzeigt
 * @author teamJES
 * @version 1.0
 */

public class SynchroDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = 3153516921282195807L;

	{
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel SynchroPanel;
	private JButton b_startSynchro;
	private JButton b_cancel;
	private JTable t_synchroTable;
	private ArrayList<Client> clients;
	private ArrayList<String> clientsOrt = new ArrayList<String>();
	private JScrollPane scroll = new JScrollPane();
	private Object[][] infosFinal2;

	/**
	 * Konstruktor SynchroDialog
	 * @param Frame mainFrame
	 */
	public SynchroDialog(JFrame Frame) {
		super(Frame, true);
		super.setTitle("Synchronisation");
		
		initGUI();

		super.setLocationRelativeTo(null);
	}

	/**
	 * initialisiert SynchroDialog Komponenten
	 */
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					SynchroPanel = new JPanel();
					getContentPane().add(SynchroPanel);
					SynchroPanel.setBounds(6, 6, 376, 276);
					SynchroPanel.setLayout(null);
					{
						String myOrt = ClientMain.getClient().getOrt();
						clients = ClientMain.getServer().getClientList();

						ListIterator<Client> it = clients.listIterator();
						while (it.hasNext()) {
							Client client = it.next();
							if (client.getOrt().equals(myOrt)) {
								it.remove();
								break;
							}
						}

						if (clients != null) {
							String[] columName = { "Clients" };
							ArrayList<Object[]> infos = new ArrayList<Object[]>();

							for (Client c : clients) {
								Object[] info = new Object[1];
								info[0] = c.getOrt();

								clientsOrt.add(c.getOrt());

								infos.add(info);

							}

							Object[][] infosFinal3 = new Object[clients.size()][1];
							for (int j = 0; j < clients.size(); j++) {
								infosFinal3[j] = infos.get(j);
							}
							remove(scroll);
							scroll = getTableComponent(infosFinal3, columName);
							scroll.setSize(400, 300);
							SynchroPanel.add(scroll);
							scroll.setBounds(12, 12, 200, 100);

						} else {
							remove(scroll);

							String[] columName = { "Clients" };
							scroll = getTableComponent(infosFinal2, columName);
							scroll.setSize(300, 200);
							SynchroPanel.add(scroll);
							scroll.setBounds(12, 12, 300, 200);
						}
						{
							b_cancel = new JButton();
							SynchroPanel.add(b_cancel);
							b_cancel.setText("Abbrechen");
							b_cancel.setBounds(42, 226, 109, 31);

							b_cancel.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									dispose();
								}
							});
						}
						{
							b_startSynchro = new JButton();
							SynchroPanel.add(b_startSynchro);
							b_startSynchro.setText("Synchronisieren");
							b_startSynchro.setBounds(186, 226, 121, 31);

							b_startSynchro.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(ActionEvent e) {
											if (t_synchroTable.getSelectedRowCount() == 0) {
												JOptionPane.showMessageDialog(t_synchroTable,
																"Mindestens einen Client auswählen!");
											} else {
												ArrayList<Client> synchroClients = new ArrayList<Client>();

												for (int i = 0; i < t_synchroTable.getSelectedRowCount(); i++) {
													synchroClients.add(clients.get(t_synchroTable.getSelectedRows()[i]));
												}

												try {
													ClientMain.getPlayer().pause();
													ClientMain.getPlayer().setMediaTime(0);
													ClientMain.getServer().synchronize(ClientMain.getClient().getOrt(),synchroClients);
													ClientMain.getPlayer().resume();
													dispose();
												} catch (RemoteException e1) {
													JOptionPane.showMessageDialog(t_synchroTable,
																	"Fehler bei der Kommunikation mit dem Server.");
												} catch (IOException e1) {
													e1.printStackTrace();
												}
											}
										}
									});
						}
					}
				}
			}
			this.setSize(400, 350);
		} catch (Exception e) {
			e.printStackTrace();
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
	private JScrollPane getTableComponent(Object[][] infos, String[] columName) {
		String[] colNames = columName;
		final Object[][] info = infos;
		DefaultTableModel model = new DefaultTableModel(info, colNames) {

			private static final long serialVersionUID = 1532328204762208719L;

			@Override
			public Class<?> getColumnClass(int col) {
				return String.class;
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		t_synchroTable = new JTable(model);
		t_synchroTable.setRowSelectionAllowed(true);

		Dimension d = t_synchroTable.getPreferredSize();
		t_synchroTable.setPreferredScrollableViewportSize(d);
		t_synchroTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		t_synchroTable.setSize(300, 400);

		return new JScrollPane(t_synchroTable);
	}
}