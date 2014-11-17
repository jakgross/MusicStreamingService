package clientlogic.player;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import junit.framework.Assert;

import org.junit.Test;

public class AudioPlayerTest {
	
	static final String mp3 = "C:/musik/beispiel.mp3";
	/**
	 * Test des AusioPlayers Prozesses - ausioDatei in "C/musik/beispiel.mp3" muss vorhanden sein
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testPlayAudio() throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException{
		 File file = new File(mp3);
	        assertTrue(file.isFile());
		
	        
	        
	        AudioInputStream in = AudioSystem.getAudioInputStream(file);

            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                          baseFormat.getSampleRate(),
                                                          16,
                                                          baseFormat.getChannels(),
                                                          baseFormat.getChannels() * 2,
                                                          baseFormat.getSampleRate(),
                                                          false);

            in = AudioSystem.getAudioInputStream(decodedFormat, in);
            BufferedAudioInputStream bbin = new BufferedAudioInputStream(in, decodedFormat);
		
            
            
            
            AudioPlayer ap = new AudioPlayer();
            ap.supply(bbin, 0);
            
            ap.play();
            Thread.sleep(500);
            ap.pause();
            
            assertTrue(ap.isPlaying());
            assertTrue(ap.isPaused());
            
           
		
	}
	/*
	@Test
	public void testAudioPlayer() {
		fail("Not yet implemented");
	}
*/
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

	@Test
	public void testGetMediaTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMediaTime() {
		fail("Not yet implemented");
	}

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
