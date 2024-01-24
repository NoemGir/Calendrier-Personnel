package controleur;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coeur.UserData;
import coeur.calendrier.Calendrier;
import coeur.calendrier.Horloge;
import coeur.calendrier.plan.Evenement;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

class Test_CtrlModifierPlan {
	UserData userData;
	Calendrier<Plan> calendrier;
	ControlModifierPlan controlModifierPlan;
	
	@BeforeEach
	public void setUp() {
		userData = new UserData();
		calendrier = userData.getCalendrier();
		controlModifierPlan = new ControlModifierPlan(userData);
		
		Tache tache1 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 1", "infos sup tache 1", new Horloge (0, 59));
		Tache tache2 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 2", "infos sup tache 2", new Horloge (12, 00));
		Tache tache3 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 3", "infos sup tache 3", new Horloge (4, 50));
		Tache tache4 = new Tache(new GregorianCalendar(2023, 10, 05), "tache numero 4", "infos sup tache 4", new Horloge (3, 17));

		calendrier.ajouterPlan(tache1);
		calendrier.ajouterPlan(tache2);
		calendrier.ajouterPlan(tache3);
		calendrier.ajouterPlan(tache4);
		
	}
	
	@Test
	public void test_modifierDate() {
		GregorianCalendar jour1 = new GregorianCalendar(2023, 11, 5);
		GregorianCalendar jour2 = new GregorianCalendar(2023, 11, 6);
		Tache tache3 = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 3", "infos sup tache 3", new Horloge (4, 50));
		
		Plan tache =  calendrier.getPlan(jour1, 0);
		int nouvelIndice = controlModifierPlan.modifierDate(jour1, 0, jour2);
		assertEquals(jour2, tache.getDate());
		assertEquals(tache, calendrier.getPlan(jour2, 0));
		assertEquals( 0, nouvelIndice);
		assertEquals( tache3 ,calendrier.getPlan(jour1, 0));
	}
	

	@Test
	public void test_terminerTache() {
	
		int ancienNbPlanTotal = calendrier.donnerNbPlan();
		int ancienNbTacheTotal = calendrier.donnerNbTacheETSousTache();
		int ancienNbTacheSimple = calendrier.donnerNombreElement(Tache.class);
		int ancienNbEvent = calendrier.donnerNombreElement(Evenement.class);
		int ancienNbTrophees = userData.getNbTrophees();

		controlModifierPlan.terminerTache(new GregorianCalendar(2023, 11, 05), 2);
		Tache tacheTerminee = new Tache(new GregorianCalendar(2023, 11, 05), "tache numero 2");
		tacheTerminee.terminerTache();

		int nouvNbPlanTotal = calendrier.donnerNbPlan();
		int nouvNbTacheTotal = calendrier.donnerNbTacheETSousTache();
		int nouvNbTacheSimple = calendrier.donnerNombreElement(Tache.class);
		int nouvNbEvent = calendrier.donnerNombreElement(Evenement.class);
		int nouvNbTrophees = userData.getNbTrophees();

		assertEquals(nouvNbPlanTotal, ancienNbPlanTotal - 1);
		assertEquals(nouvNbTacheTotal, ancienNbTacheTotal - 1);
		assertEquals(nouvNbTacheSimple, ancienNbTacheSimple - 1);
		assertEquals(nouvNbEvent, ancienNbEvent);
		assertEquals( nouvNbTrophees ,ancienNbTrophees + 1);
	//	assertEquals(tacheTerminee, userData.getTrophees().getPlan(new GregorianCalendar(2023, 11, 05), 0));

	}

}
