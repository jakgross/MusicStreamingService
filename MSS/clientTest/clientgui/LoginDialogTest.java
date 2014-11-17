package clientgui;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.JFrame;

import junit.framework.Assert;

import org.junit.Test;

public class LoginDialogTest {

	
	@Test
	public void testLoginDialog() {
		LoginDialog ld = new LoginDialog(new JFrame(), false);
		
	}
/**
 * Ladet den Ort von txt-Datei
 */
	@Test
	public void testLoadOrt() {
		LoginDialog ld = new LoginDialog(new JFrame(), false);
		Assert.assertEquals("",ld.loadOrt());
		
	}
/**
 * Ladet IP von txt-Datei
 */
	@Test
	public void testLoadIP() {
		LoginDialog ld = new LoginDialog(new JFrame(), false);
		Assert.assertEquals("", ld.loadIP());
	}
/**
 * Speichert Ort in txt-Datei
 * @throws Exception
 */
	@Test
	public void testSaveOrt() throws Exception {
		LoginDialog ld = new LoginDialog(new JFrame(), false);
		ld.saveOrt("Wohnung");
		Assert.assertEquals("Wohnung", ld.loadOrt());
	}
/**
 * Speichert IP in txt Datei
 * @throws IOException
 */
	@Test
	public void testSaveServerIP() throws IOException {
		LoginDialog ld = new LoginDialog(new JFrame(), false);
		ld.saveServerIP("192.168.0.1");
		Assert.assertEquals("192.168.0.1", ld.loadIP());
	}

}
