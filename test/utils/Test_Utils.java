package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

import coeur.DonneesApplication;
import utils.Utils;

class Test_Utils {
	
	@Test
	public void test_setUpDate() {
		GregorianCalendar date1 = new GregorianCalendar();
		GregorianCalendar date2 = new GregorianCalendar();
		Utils.setUpDate(date2);
		assertFalse(date1.equals(date2));
		assertEquals(date2.get(Calendar.MINUTE), 0);
		assertEquals(date2.get(Calendar.HOUR_OF_DAY), 0);
		assertEquals(date2.get(Calendar.SECOND), 0);
		assertEquals(date2.get(Calendar.MILLISECOND), 0);
		assertEquals(date2, DonneesApplication.JOUR_ACTUEL);
	}
	
	
	@Test
	public void test_createCalendarFromInt() {
		int[] journee = new int[3];
		journee[0] = 2023;
		journee[1] = 11;
		journee[2] = 26;
		GregorianCalendar jour = Utils.createCalendarFromInt(journee);
		GregorianCalendar autre = new GregorianCalendar(2023, 11, 26);
		assertEquals(jour, autre);
	}

	@Test
	public void test_comparerDate() {
		GregorianCalendar jour1 = new GregorianCalendar();
		GregorianCalendar jour2 = new GregorianCalendar();
		assertTrue(Utils.comparerGregorianCalendar(jour1, jour2));
		assertTrue(Utils.comparerGregorianCalendar(jour1, DonneesApplication.JOUR_ACTUEL));
		jour1.add(Calendar.DAY_OF_MONTH, 1);
		assertFalse(Utils.comparerGregorianCalendar(jour1, jour2));
		jour1.add(Calendar.DAY_OF_MONTH, -1);
		assertTrue(Utils.comparerGregorianCalendar(jour1, jour2));
		jour1.add(Calendar.DAY_OF_MONTH, -1);
		assertFalse(Utils.comparerGregorianCalendar(jour1, jour2));
		 jour1 = new GregorianCalendar();
		 jour2 = new GregorianCalendar();
		jour1.add(Calendar.MONTH, 4);
		assertFalse(Utils.comparerGregorianCalendar(jour1, jour2));
		jour1 = new GregorianCalendar();
		jour1.add(Calendar.YEAR, -6);
		assertFalse(Utils.comparerGregorianCalendar(jour1, jour2));
	}
	

}
