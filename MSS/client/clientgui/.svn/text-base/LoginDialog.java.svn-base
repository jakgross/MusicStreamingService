package clientgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clientlogic.ClientMain;

import serverlogic.ServerFacadeInterface;


/**
 * Erzeugt Dialog für Eingabe der ServerIP und des Client Orts
 * 
 * @author JES
 * @version 1.0
 */

public class LoginDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = -7836593945695004721L;

	{
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JDialog self;
	private JPanel LoginPanel;
	private JTextField t_serverIP;
	private JButton b_ok;
	private JButton b_cancel;
	private JLabel la_ort;
	private JLabel la_serverIP;
	private JTextField t_ort;

	/**
	 * Erzeugt LoginDialog-Fenster
	 * @param parent ParentFrame
	 * @param alreadyLogged boolean, ob Client schon in Server eingeloggt
	 */
	public LoginDialog(JFrame parent, boolean alreadyLogged) {
		super(parent);
		super.setModalityType(ModalityType.APPLICATION_MODAL);
		super.setTitle("Server & Ort angeben");
		self = this;
		initGUI();

		super.setLocationRelativeTo(null);

		if (alreadyLogged)
			t_serverIP.setEnabled(false);
	}

	/**
	 * Initialisiert LoginDialog Komponenten
	 */
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					LoginPanel = new JPanel();
					getContentPane().add(LoginPanel);
					LoginPanel.setBounds(12, 12, 376, 276);
					LoginPanel.setLayout(null);
					{
						t_serverIP = new JTextField();
						LoginPanel.add(t_serverIP);
						t_serverIP.setText(loadIP());
						t_serverIP.setBounds(28, 52, 173, 27);
					}
					{
						t_ort = new JTextField();
						LoginPanel.add(t_ort);
						t_ort.setText(loadOrt());
						t_ort.setBounds(28, 108, 173, 27);
					}
					{
						la_serverIP = new JLabel();
						LoginPanel.add(la_serverIP);
						la_serverIP.setText("Server-IP");
						la_serverIP.setBounds(232, 52, 65, 22);
					}
					{
						la_ort = new JLabel();
						LoginPanel.add(la_ort);
						la_ort.setText("Ort");
						la_ort.setBounds(232, 108, 48, 27);
					}
					{
						b_cancel = new JButton();
						LoginPanel.add(b_cancel);
						b_cancel.setText("Abbrechen");
						b_cancel.setBounds(28, 204, 134, 31);

						b_cancel.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {

								dispose();
							}
						});
					}
					{
						b_ok = new JButton();
						LoginPanel.add(b_ok);
						b_ok.setText("OK");
						b_ok.setBounds(201, 204, 138, 31);

						b_ok.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								ServerFacadeInterface server = ClientMain.getServer(t_serverIP.getText());
								if (server == null)
									JOptionPane.showMessageDialog(null,"Server nicht gefunden.", 
											"Error", JOptionPane.ERROR_MESSAGE);
								else {
									boolean logged = ((ClientGUI) self.getParent()).isLogged();

									try {
										if (logged || server.checkOrt(t_ort.getText()) && !(t_ort.getText().length() <= 4) && !(t_ort.getText().trim().equals(""))) {
											if (logged) {
												if(!(t_ort.getText().length() <= 4)){
												if (!server.changeOrt(ClientMain.getClient().getOrt(), t_ort.getText()))
													JOptionPane.showMessageDialog(null, "Ort schon vorhanden.",
																	"Error", JOptionPane.ERROR_MESSAGE);
												else {
													saveOrt(t_ort.getText());
													ClientMain.getClient().setOrt(t_ort.getText());
													self.setVisible(false);
													}
												}
												else{
													JOptionPane.showMessageDialog(null, "Ort zu kurz", "Error",
															JOptionPane.ERROR_MESSAGE);
												}
											} else {
												if (server.login(InetAddress.getLocalHost().getHostAddress(),t_ort.getText())) {
													saveServerIP(t_serverIP.getText());
													saveOrt(t_ort.getText());

													((ClientGUI) self.getParent()).setLogged(true);
													ClientMain.getClient().setServerIP(t_serverIP.getText());
													ClientMain.getClient().setOrt(t_ort.getText());
													
													t_serverIP.setEnabled(false);
													self.setVisible(false);
												} else
													JOptionPane.showMessageDialog(null,"Login fehlgeschlagen.",
																	"Error", JOptionPane.ERROR_MESSAGE);
											}
										} else
											JOptionPane.showMessageDialog(null,"Ort schon vorhanden.",
													"Error", JOptionPane.ERROR_MESSAGE);
									} catch (RemoteException ex) {
										ex.printStackTrace();
									} catch (UnknownHostException ex) {
										ex.printStackTrace();
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
						});
					}
				}
			}
			this.setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * holt den gespeicherten Ort von der Festplatte (Textdatei)
	 * 
	 * @throws IOException
	 */
	public String loadOrt() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("ort.txt"));
			String line = br.readLine();
			br.close();

			return line;
		} catch (FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * holt die gespeicherte Server-IP von der Festplatte (Textdatei)
	 * 
	 * @throws IOException
	 */
	public String loadIP() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("serverip.txt"));
			String line = br.readLine();
			br.close();

			return line;
		} catch (FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 
	 * Überprüft ob eine ort.txt datei vorhanden ist, wenn ja wird diese
	 * gelöscht. wenn nicht, wird eine neue Datei mit dem aktuellen Ort
	 * erstellt.
	 * 
	 * @param Ort zu speichernder Ort
	 * @throws Exception 
	 */
	public void saveOrt(String Ort) throws Exception {

		Writer fw = null;

		File fileOrt = new File("ort.txt");
		if (fileOrt.exists() == true) {
			if (!fileOrt.isDirectory() == true) {
				fileOrt.delete();
				fw = new FileWriter("ort.txt");
				fw.write(Ort);
				fw.close();
			}
		} else {
			fw = new FileWriter("ort.txt");
			fw.write(Ort);
			fw.close();
		}
	}

	/**
	 * 
	 * Überprüft ob eine serverip.txt datei vorhanden ist, wenn ja wird diese
	 * gelöscht. wenn nicht, wird eine neue Datei mit der aktuellen Server-IP
	 * erstellt.
	 * 
	 * @param IP zu speichernde ServerIP
	 * @throws IOException
	 */
	public void saveServerIP(String IP) throws IOException {
		Writer fw = null;

		File fileServer = new File("serverip.txt");
		if (fileServer.exists() == true) {
			if (!fileServer.isDirectory() == true) {
				fileServer.delete();
				fw = new FileWriter("serverip.txt");
				fw.write(IP);
				fw.close();
			}
		} else {
			fw = new FileWriter("serverip.txt");
			fw.write(IP);
			fw.close();
		}
	}
}