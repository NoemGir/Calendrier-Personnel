package coeur.calendrier.plan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coeur.calendrier.Horloge;
import coeur.calendrier.plan.Tache;

class Test_Tache {
	
	Tache tache1;
	Tache tache2;
	Tache tache3;
	Tache tache4;
	Tache tache5;
	
	@BeforeEach
	public void setUp() {
		tache1 = new Tache(new GregorianCalendar(2023, 11, 16), "tache 1", "infos sup de la tache 1", new Horloge(10,13));
		tache2 = new Tache(new GregorianCalendar(2023, 11, 17), "tache 2", "infos sup de la tache 2", new Horloge(9,59));
		tache3 = new Tache(new GregorianCalendar(2023, 11, 16), "tache 1", "infos sup de la tache 1", new Horloge(10,13));
		tache4 = new Tache(new GregorianCalendar(2023, 11, 16), "tache 1", "infos sup de la tache 1", new Horloge(10,50));
		tache5 = new Tache(new GregorianCalendar(2023, 11, 16), "tache 1");
		
		tache1.ajouterSousTache("sous tache tache 1","infos sup sous tache", new Horloge(12, 13));
		tache1.ajouterSousTache("sous tache tache 2","infos sup sous tache", new Horloge(12, 15));
		tache1.ajouterSousTache("sous tache tache 3","infos sup sous tache", new Horloge(2, 13));
		
		tache3.ajouterSousTache("sous tache tache 1","infos sup sous tache", new Horloge(12, 13));
		tache3.ajouterSousTache("sous tache tache 2","infos sup sous tache", new Horloge(12, 15));
		tache3.ajouterSousTache("sous tache tache 3","infos sup sous tache", new Horloge(2, 13));
	}
	
	@Test
	void test_sousTachesToString() {
		System.out.println(tache1.getSousTaches().toString());
	}
	
	@Test
	void test_equals() {
		assertFalse(tache1.equals(tache2));
		assertTrue(tache1.equals(tache3));
		assertFalse(tache1.equals(tache4));
		assertFalse(tache1.equals(tache5));
		assertFalse(tache2.equals(tache4));
		assertFalse(tache4.equals(tache5));
	}

	@Test
	void test_compareTo() {
		assertTrue(tache1.compareTo(tache2) < 0);
		assertTrue(tache1.compareTo(tache3) == 0);
		assertTrue(tache1.compareTo(tache4) < 0);
		assertTrue(tache1.compareTo(tache5) > 0);
	}

}
