package Common;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SongTest {
	
	Song s;
	
	@Before
	public void init(){
		
	 s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		
	}
/*
	@Test
	public void testSongStringStringStringStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSongIntStringStringStringStringStringString() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testGetSongID() {
		int id = s.getSongID();
		
		Assert.assertNotNull(id);
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		id = s.getSongID();
		Assert.assertEquals(0,id);
	}

	@Test
	public void testSetFileName() {
		s.setFileName("alpha");
		
		
		String string = s.getFileName();
		
		Assert.assertEquals("alpha",string);
	}

	@Test
	public void testGetFileName() {
		String string = s.getFileName();
		Assert.assertNotNull(string);
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		string = s.getFileName();
		Assert.assertEquals("name",string);
	}

	@Test
	public void testSetSongPath() {
		s.setSongPath("C:/musik");
		
		
		String string = s.getSongPath();
		
		Assert.assertEquals("C:/musik",string);
	}

	@Test
	public void testGetSongPath() {
		String string = s.getSongPath();
		Assert.assertNotNull(string);
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		string = s.getSongPath();
		Assert.assertEquals("C:/musik",string);
	}

	@Test
	public void testSetInterpret() {
		s.setInterpret("Bobi");
		
		
		String string = s.getInterpret();
		
		Assert.assertEquals("Bobi",string);
	}

	@Test
	public void testGetInterpret() {
		String string = s.getInterpret();
		Assert.assertNotNull(string);
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		string = s.getInterpret();
		Assert.assertEquals("Bob",string);
	}

	@Test
	public void testSetTitel() {
		s.setTitel("supertitel");
		
		
		String string = s.getTitel();
		
		Assert.assertEquals("supertitel",string);
	}

	@Test
	public void testGetTitel() {
		String string = s.getTitel();
		Assert.assertNotNull(string);
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		string = s.getTitel();
		Assert.assertEquals("titel",string);
	}

	@Test
	public void testSetAlbum() {
		s.setAlbum("90-er_album");
		
		
		String string = s.getAlbum();
		
		Assert.assertEquals("90-er_album",string);
	}

	@Test
	public void testGetAlbum() {
		String string = s.getAlbum();
		Assert.assertNotNull(string);
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		string = s.getAlbum();
		Assert.assertEquals("album",string);
	}

	@Test
	public void testSetGenre() {
		s.setGenre("pop_genre");
		
		
		String string = s.getGenre();
		
		Assert.assertEquals("pop_genre",string);
	}

	@Test
	public void testGetGenre() {
		String string = s.getGenre();
		Assert.assertNotNull(string);
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		string = s.getGenre();
		Assert.assertEquals("genre",string);
	}

	@Test
	public void testEqualsObject() {
		Assert.assertTrue(s.equals(s));
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		Assert.assertTrue(s.equals(s));
	}

/*	@Test
	public void testGetSong() {
		Song songs = Song.getSong(0);
		Assert.assertNotNull(songs);
		s = new Song(0, "name", "C:/musik","Bob", "titel", "album", "genre");
		songs = Song.getSong(0);
		Assert.assertEquals(0,songs);
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
	public void testEqualsObject1() {
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
