package controleur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coeur.UserData;
import coeur.calendrier.Calendrier;
import coeur.calendrier.Horloge;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

class Test_CtrlVerifPlanSelectionne {
	
	ControlVerificationPlanSelectionne controlverif;
	
	@BeforeEach
	public void setUp() {
		UserData userData = new UserData();
		controlverif = new ControlVerificationPlanSelectionne(userData);
		Calendrier<Plan >calendrier = userData.getCalendrier(); 

		Tache tache1 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 1", "infos sup tache 2", new Horloge (0, 59));
		Tache tache2 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 2", "infos sup tache 2", new Horloge (12, 00));
		Tache tache3 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 3", "infos sup tache 2", new Horloge (4, 50));
		Tache tache4 = new Tache(new GregorianCalendar(2023, 10, 05), "tache numero 4", "infos sup tache 2", new Horloge (3, 17));
		
		tache1.ajouterSousTache("sous tache 1");
		tache1.ajouterSousTache("sous tache 2");
		tache2.ajouterSousTache("SousTache 1 de tache 2");
		
		calendrier.ajouterPlan(tache1);
		calendrier.ajouterPlan(tache2);
		calendrier.ajouterPlan(tache3);
		calendrier.ajouterPlan(tache4);
	}
	
	
	@Test
	public void test_sousTacheSelectionnee() {
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 1", 0), 1);
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 2", 0), 2);
		assertFalse(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 3", 0) == 3);
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 1", 1), -1);
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 1", 2), 1);
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 2", 1),-1);
		assertFalse(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 1", 2) == -1);
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"1", 1), -1);
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"1", 0), -1);
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 0", 0), -1);
		assertEquals(controlverif.sousTacheSelectionnee(new GregorianCalendar(2023, 11, 05),"soustache 0", 1), -1);
	}
	
	
	@Test
	public void test_planSelectionne() {
		
		assertEquals(controlverif.planSelectionne(new GregorianCalendar(2023, 11, 05), "plan 1"), 1);
		assertEquals(controlverif.planSelectionne(new GregorianCalendar(2023, 11, 05), "plan 2"), 2);
		assertEquals(controlverif.planSelectionne(new GregorianCalendar(2023, 11, 05), "plan 3"), 3);
		assertEquals(controlverif.planSelectionne(new GregorianCalendar(2023, 10, 05), "plan 1"), 1);
		assertEquals(controlverif.planSelectionne(new GregorianCalendar(2023, 11, 05), "plan 4"), -1);
		assertEquals(controlverif.planSelectionne(new GregorianCalendar(2023, 11, 05), "plan 0"), -1);
		assertEquals(controlverif.planSelectionne(new GregorianCalendar(2023, 10, 05), "plan 2"), -1);
		assertEquals(controlverif.planSelectionne(new GregorianCalendar(2023, 11, 05), "1"), -1);
	}

}
