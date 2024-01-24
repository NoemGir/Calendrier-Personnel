package controleur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coeur.UserData;
import coeur.calendrier.Calendrier;
import coeur.calendrier.plan.Evenement;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;
import utils.Utils;

class Test_CtrlCreerPlan {
	GregorianCalendar jour;
	UserData userData;
	ControlCreerPlan controlCreerPlan;
	Calendrier<Plan >calendrier;
	int[] journee;
	
	@BeforeEach
	public void setUp() {
		jour = new GregorianCalendar();
		userData = new UserData();
		controlCreerPlan = new ControlCreerPlan(userData);
		calendrier = userData.getCalendrier();
		
		journee = new int[3];
		journee[0] = 2023;
		journee[1] = 11;
		journee[2] = 26;
		
		jour = Utils.createCalendarFromInt(journee);
	}
	
	@Test
	public void test_ajouterSousTache() {
		
	}
	
	@Test
	public void test_ajouterEvenement() {
		controlCreerPlan.ajouterEvenement(journee, "evenement 1");
		Evenement event1 = new Evenement(jour, "evenement 1");
		
		Set<Plan> set1 = new TreeSet<>();
		
		set1.add(event1);
		
		journee[0] = 2023;
		journee[1] = 11;
		journee[2] = 27;
		GregorianCalendar jour2 = Utils.createCalendarFromInt(journee);
		
		controlCreerPlan.ajouterEvenement(journee, "evenement 1");
		Evenement event2 = new Evenement(jour2, "evenement 1");
		
		Set<Plan> set2 = new TreeSet<>();
		set2.add(event2);
		
		assertEquals(calendrier.planDeUnJour(jour), set1);
		assertNotEquals(calendrier.planDeUnJour(jour), set2);
		jour.add(Calendar.DAY_OF_MONTH, -1);
		assertEquals(calendrier.planDeUnJour(jour), null);
		assertEquals(calendrier.planDeUnJour(jour2), set2);

		
		
	}

	@Test
	public void test_ajouterTache() {

		controlCreerPlan.ajouterTache(journee, "tache 1");
		Tache tache1 = new Tache(jour, "tache 1");
		controlCreerPlan.ajouterTache(journee, "tache 2");
		Tache tache2 = new Tache(jour, "tache 2");
		controlCreerPlan.ajouterTache(journee, "tache 3");
		Tache tache3 = new Tache(jour, "tache 3");
		
		
		Set<Plan> set1 = new TreeSet<>();

		set1.add(tache1);
		set1.add(tache2);
		set1.add(tache3);
		
		assertEquals(calendrier.planDeUnJour(jour), set1);
		jour.add(Calendar.DAY_OF_MONTH, -1);
		assertEquals(calendrier.planDeUnJour(jour), null);
	}

}
