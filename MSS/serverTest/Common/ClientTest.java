package Common;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ClientTest {

	Client c;
	
	@Before //Client erstellt
	public void init(){
		
	 c = new Client();
		
	}
	
	@Test//Client mit String als IP und Ort
	public void testClientStringString() {
		c = new Client("192.168.0.1","Wohnzimmer");
		Assert.assertEquals("192.168.0.1", c.getClientIP());
		Assert.assertEquals("Wohnzimmer", c.getOrt());
		
	}

	@Test
	public void testGetClientIP() { //Client erzeugbar ohne IP da kein setClientIP
		String string = c.getClientIP();
		
		Assert.assertNull(string);
		c = new Client("192.168.0.1","Wohnzimmer");
		string = c.getClientIP();
		Assert.assertEquals("192.168.0.1",string);
		
	}

	@Test//erstellter Ort und wiedergeholter Ort muss Wohnzimmer sein
	public void testSetOrt() {
		c.setOrt("Wohnzimmer");
		
		
		String string = c.getOrt();
		//(erwartet, Wirklich)
		Assert.assertEquals("Wohnzimmer",string);
		
	}

	@Test
	public void testGetOrt() {
		
		String string = c.getOrt();
		Assert.assertNull(string);
		c = new Client("192.168.0.1","Wohnzimmer");
		string = c.getOrt();
		Assert.assertEquals("Wohnzimmer",string);
	}


	@Test
	public void testGetClientFacade() {//Gibt interface zurück da "192.168.0.1" nicht existiert null
		
		
		Assert.assertNull(c.getClientFacade());
	}
}
