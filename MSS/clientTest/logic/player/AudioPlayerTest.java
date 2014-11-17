package logic.player;

import static org.junit.Assert.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;

import junit.framework.Assert;

import org.junit.Test;

import clientlogic.player.AudioPlayer;

public class AudioPlayerTest {
	
	@Test
	public void testAudioPlayer() {
		fail("Not yet implemented");
	}

	@Test//IsPlaying und Audiosystem isrunning muss das gleiche zurückgeben
	public void testIsPlaying() throws LineUnavailableException {
		AudioPlayer ap = new AudioPlayer();
		
		Assert.assertEquals(AudioSystem.getSourceDataLine(null).isRunning(), ap.isPlaying());
	}

	@Test//Gibt nach richtigem ausführen false zurück
	public void testIsPaused() throws LineUnavailableException {
		AudioPlayer ap = new AudioPlayer();
		Assert.assertFalse(ap.isPaused());
	//	AudioPlayer.this.pause();
	//	Assert.assertTrue(AudioPlayer.this.isPaused());
	}
/*
	@Test
	public void testGetMediaTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMediaTime() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testPause() {
		fail("Not yet implemented");
	}

	@Test
	public void testResume() {
		fail("Not yet implemented");
	}

	@Test
	public void testStop() {
		fail("Not yet implemented");
	}

	@Test
	public void testSupply() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlay() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAudioObserver() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveAudioObserver() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveAllAudioObservers() {
		fail("Not yet implemented");
	}

	@Test
	public void testRun() {
		fail("Not yet implemented");
	}
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
