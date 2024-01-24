package coeur.calendrier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

import coeur.UserData;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

class Test_Calendrier {

	@Test
	public void test_IterateurCalendrier() {
		
		
		UserData userData = new UserData();
		Calendrier<Plan> calendrier = userData.getCalendrier();
		
		for (Plan plan : calendrier) {
			System.out.println(plan);
		}
		
		Tache tache1 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 1", "infos sup tache 1", new Horloge (0, 59));
		Tache tache2 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 2", "infos sup tache 2", new Horloge (12, 00));
		Tache tache3 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 3", "infos sup tache 3", new Horloge (4, 50));
		Tache tache4 = new Tache(new GregorianCalendar(2023, 10, 05), "tache numero 4", "infos sup tache 4", new Horloge (3, 17));

		calendrier.ajouterPlan(tache1);
		calendrier.ajouterPlan(tache2);
		calendrier.ajouterPlan(tache3);
		calendrier.ajouterPlan(tache4);
		
		for (Plan plan : calendrier) {
			System.out.println(plan);
		}
	}
	
	@Test
	public void test_ajouterPlan() {
		
		UserData userData = new UserData();
		Calendrier<Plan >calendrier = userData.getCalendrier();
		
		Tache tache1 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 1", "infos sup tache 1", new Horloge (0, 59));
		Tache tache2 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 2", "infos sup tache 2", new Horloge (12, 00));
		Tache tache3 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 3", "infos sup tache 3", new Horloge (4, 50));
		Tache tache4 = new Tache(new GregorianCalendar(2023, 10, 05), "tache numero 4", "infos sup tache 4", new Horloge (3, 17));

		
		calendrier.ajouterPlan(tache1);
		calendrier.ajouterPlan(tache2);
		calendrier.ajouterPlan(tache3);
		calendrier.ajouterPlan(tache4);
		
		assertEquals(tache4, calendrier.getPlan(new GregorianCalendar(2023, 10, 05), 0));
		assertEquals(tache1, calendrier.getPlan(new GregorianCalendar(2023, 11, 05), 0));
		assertEquals(tache3, calendrier.getPlan(new GregorianCalendar(2023, 11, 05), 1));
		assertEquals(tache2, calendrier.getPlan(new GregorianCalendar(2023, 11, 05), 2));
	}

}
