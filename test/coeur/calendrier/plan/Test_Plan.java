package coeur.calendrier.plan;

import static org.junit.jupiter.api.Assertions.*;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

import coeur.calendrier.Horloge;
import coeur.calendrier.plan.Evenement;
import coeur.calendrier.plan.Tache;

class Test_Plan {

	@Test
	public void test_equalsPlan() {
		Tache tache1 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 1", "infos sup tache 1", new Horloge (0, 59));
		Tache tache2 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 1", "infos sup tache 1", new Horloge (0, 59));
		Tache tache3 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 1", "infos sup tache 1", new Horloge (0, 50));
		Tache tache4 = new Tache(new GregorianCalendar(2023, 10, 04), "tache numero 1", "infos sup tache 1", new Horloge (0, 59));
		Tache tache5 = new Tache(new GregorianCalendar(2023, 10, 05), "tache numero 2", "infos sup tache 1", new Horloge (0, 59));
		Tache tache6 = new Tache(new GregorianCalendar(2023, 10, 05), "tache numero 1", "infos sup tache 2", new Horloge (0, 59));
		Tache tache0 = new Tache(new GregorianCalendar(2023, 10, 05), "tache numero 0");
		Tache tache7 = new Tache(new GregorianCalendar(2023, 10, 05), "tache numero 0");
		Evenement event1 = new Evenement(new GregorianCalendar(2023, 10, 05), "tache numero 1");
		Evenement event2 = new Evenement(new GregorianCalendar(2023, 10, 05), "tache numero 1");
		
		assertEquals(tache1, tache2);
		assertFalse(tache1.equals(tache3));
		assertFalse(tache1.equals(tache4));
		assertFalse(tache1.equals(tache5));
		assertFalse(tache1.equals(tache6));
		assertFalse(event1.equals(tache0));
		assertEquals(tache0, tache7);
		assertFalse(tache1.equals(tache0));
		assertEquals(event1, event2);
	}

}
