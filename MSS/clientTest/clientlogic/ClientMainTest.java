package clientlogic;

import static org.junit.Assert.*;

import clientgui.ClientGUI;

import java.rmi.RemoteException;

import org.junit.Assert;
import org.junit.Test;

public class ClientMainTest {
/*
	@Test
	public void testGetClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetServer() {
		fail("Not yet implemented");
	}
*/
	/**
	 * Client start test
	 */
	@Test
	public void testStartClient() {
		try {
			ClientMain.startClient();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(ClientMain.getClient());
	}
/**
 * Clientstop test
 */
	@Test
	public void testStopClient() {
		try {
			ClientMain.startClient();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			try {
				ClientMain.stopClient();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertNull(ClientMain.getClient());
	}
/*
	@Test
	public void testGetServerString() {
		fail("Not yet implemented");
	}
*/
//-----------------------------------------------
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
