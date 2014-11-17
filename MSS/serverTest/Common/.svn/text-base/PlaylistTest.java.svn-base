package Common;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class PlaylistTest {
	
	Playlist pl;
	
	@Before
	public void init(){
		
	 pl = new Playlist(0,"alpha",null, false, false);
		
	}

	@Test
	public void testPlaylistInt() {
		pl = new Playlist(0,"alpha",null, false, false);
		Assert.assertEquals(0, pl.getPlaylistID());
		
	}

	@Test
	public void testGetPlaylistID() {
		int id = pl.getPlaylistID();
		
		Assert.assertNotNull(id);
		pl = new Playlist(0,"alpha",null, false, false);
		id = pl.getPlaylistID();
		Assert.assertEquals(0,id);
	}

	@Test
	public void testSetPlaylistName() {
		pl.setPlaylistName("alpha");
		
		
		String string = pl.getPlaylistName();
		
		Assert.assertEquals("alpha",string);
	}

	@Test
	public void testGetPlaylistName() {
		String string = pl.getPlaylistName();
		Assert.assertNotNull(string);
		pl = new Playlist(0,"alpha",null, false, false);
		string = pl.getPlaylistName();
		Assert.assertEquals("alpha",string);
	}

	@Test
	public void testSetSongs() {
		ArrayList<Song> songs = null;
		
		pl.setSongs(songs);
		
		
		ArrayList<Song> song = pl.getSongs();
		
		Assert.assertEquals(songs,song);
	}

	@Test
	public void testGetSongs() {
		ArrayList<Song> songs = pl.getSongs();
		Assert.assertNull(songs);
		pl = new Playlist(0,"alpha",null, false, false);
		songs = pl.getSongs();
		Assert.assertEquals(null,songs);
	}


	@Test
	public void testEqualsObject() {
		Assert.assertTrue(pl.equals(pl));
		pl = new Playlist(0,"alpha",null, false, false);
		Assert.assertTrue(pl.equals(pl));
	}
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
