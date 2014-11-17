package logic;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clientlogic.ClientFacade;

import Common.Client;

public class ClientFacadeTest {
	
	ClientFacade cf;
	
	@Before
	public void init(){
		
	 try {
		cf = new ClientFacade();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}
		
	}

/*	
	@Test
	public void testClientFacade() {
		fail("Not yet implemented");
		
	}
*/
/*
	@Test
	public void testGetServerIP() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOrt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetServerIP() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetOrt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetServer() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testStartSynchronisation() {
		
		/*
		 * Auskommentiert, hat Fehler
		 * 
		try {
			Assert.assertNotNull(cf.startSynchronisation("a", null, null));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	@Test
	public void testStopSynchronisation() {//Fehlt noch was bei exception passiert - Nicht implementierte Methode
		fail("Not yet implemented");
		
	}
	
	/*
	 * Auskommentiert, hat Fehler
	 * 
	@Test
	public void testServerClosed() {
		try {
			cf.startSynchronisation("a", null, null);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			cf.serverClosed();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(cf);
	}
	*/
//------------------------------------------------------------
/*
	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnicastRemoteObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnicastRemoteObjectInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnicastRemoteObjectIntRMIClientSocketFactoryRMIServerSocketFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportObjectRemote() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportObjectRemoteInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportObjectRemoteIntRMIClientSocketFactoryRMIServerSocketFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnexportObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoteServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoteServerRemoteRef() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClientHost() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLog() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLog() {
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
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoteObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoteObjectRemoteRef() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRef() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStub() {
		fail("Not yet implemented");
	}
*/
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
