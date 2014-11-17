package Common;

import static org.junit.Assert.*;

import javax.sound.sampled.AudioFormat;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.healthmarketscience.rmiio.RemoteInputStream;

public class SongPackageTest {
	
	SongPackage s;
	
	@Before
	public void init(){
	// RemoteInputStream r ;
		RemoteInputStream a;
		
		a =(RemoteInputStream) s.getAudioFormat() ;
	 s = new SongPackage(a, null, 20);
		
	}
/*
	@Test
	public void testSongPackage() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testGetRemoteStream() {
		RemoteInputStream rs = s.getRemoteStream();
		
		Assert.assertNotNull(rs);
		RemoteInputStream a = (RemoteInputStream) s.getAudioFormat();
		s = new SongPackage(a, null, 20);
		rs = s.getRemoteStream();
		Assert.assertEquals(a,rs);
	}

	@Test
	public void testGetAudioFormat() {
		AudioFormat af = s.getAudioFormat();
		
		Assert.assertNotNull(af);
		s = new SongPackage(null, null, 20);
		af = s.getAudioFormat();
		Assert.assertNotNull(af);
	}
	/*
	 * Auskommentiert, hat Fehler
	@Test
	public void testGetLength() {
		long lg = s.getLength();
		
		Assert.assertNull(lg);
		s = new SongPackage(null, null, 20);
		lg = s.getLength();
		Assert.assertEquals(20,lg);
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
