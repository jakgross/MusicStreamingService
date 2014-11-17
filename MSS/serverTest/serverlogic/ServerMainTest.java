package serverlogic;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import serverdata.DBConnector;


import Common.Client;

public class ServerMainTest {
	

	
	@Before
	public void init(){
		
	 
		
	}
	
/*
	@Test
	public void testGetServer() {
		fail("Not yet implemented");
	}
*/
	/**
	 *  Testet ob Server gestartet
	 */
	@Test
	public void testStartServer() {
		try {
			ServerMain.startServer();
			System.out.println("Server gestartet");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(ServerMain.getServer());
	}

	/**
	 * Startet Server Testet ob gestartet - stoppt den Server - testet ob gestoppt
	 */
	@Test
	public void testStopServer() {
		try {
			ServerMain.startServer();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server gestartet");
		try {
			ServerMain.stopServer();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server gestoppt");
		assertNull(ServerMain.getServer());
	}
//-----------------------------------------
/*
	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}
*/
}
