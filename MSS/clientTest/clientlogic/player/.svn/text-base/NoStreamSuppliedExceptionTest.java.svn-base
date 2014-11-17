package clientlogic.player;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class NoStreamSuppliedExceptionTest {

	@Test
	public void testNoStreamSuppliedException() {
		NoStreamSuppliedException nsse = new NoStreamSuppliedException("Meldung");
	}
	/**
	 * To String muss beim ausführung gleiche Meldung zurückgeben
	 */
	@Test//To String muss beim ausführung gleiche Meldung zurückgeben
	public void testToString() {
		NoStreamSuppliedException nsse = new NoStreamSuppliedException("Meldung");
		Assert.assertEquals("Meldung", nsse.toString());
	}
}