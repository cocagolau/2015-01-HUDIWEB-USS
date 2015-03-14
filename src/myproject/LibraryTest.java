package myproject;

import static org.junit.Assert.*;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Test;

public class LibraryTest {
	@Test
	public void testJoda() throws Exception {
		LocalDate theDay = new LocalDate(1988, 3, 9);
		
		assertEquals(theDay.getDayOfWeek(), DateTimeConstants.WEDNESDAY);
	}
	

}
