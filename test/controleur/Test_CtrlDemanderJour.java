package controleur;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test_CtrlDemanderJour {
	
	ControlDemanderJour controlDemanderJour = new ControlDemanderJour();
	GregorianCalendar jourActuel = new GregorianCalendar();
	int jour;
	int mois;
	int annee;
	
	@BeforeEach
	public void setUp() {
		controlDemanderJour = new ControlDemanderJour();
		jourActuel = new GregorianCalendar();
		annee = jourActuel.get(Calendar.YEAR);
		mois = jourActuel.get(Calendar.MONTH);
		jour = jourActuel.get(Calendar.DAY_OF_MONTH);
	}


	@Test
	public void test_anneeCorrect() {

		assertTrue(controlDemanderJour.anneeCorrect(annee));
		annee += 3;
		assertTrue(controlDemanderJour.anneeCorrect(annee));
		annee = jourActuel.get(Calendar.YEAR)-1;
		assertFalse(controlDemanderJour.anneeCorrect(annee));
		assertFalse(controlDemanderJour.anneeCorrect(2022));
		assertFalse(controlDemanderJour.anneeCorrect(-1));
	}
	
	@Test
	public void test_moisCorrect() {

		assertTrue(controlDemanderJour.moisCorrect(mois, annee));
		annee += 1;
		assertTrue(controlDemanderJour.moisCorrect(mois, annee));
		annee = jourActuel.get(Calendar.YEAR)-1;
		assertFalse(controlDemanderJour.moisCorrect(mois, annee));
		annee = jourActuel.get(Calendar.YEAR)+1;
		mois = (mois + 6)%12;
		assertTrue(controlDemanderJour.moisCorrect(mois, annee));
		annee = jourActuel.get(Calendar.YEAR);
		mois = jourActuel.get(Calendar.MONTH)-1 % 12;
		assertFalse(controlDemanderJour.moisCorrect(mois, annee));
		assertFalse(controlDemanderJour.moisCorrect(-1, annee));
		assertFalse(controlDemanderJour.moisCorrect(12, annee));
		annee = jourActuel.get(Calendar.YEAR)+1; 
		mois = 0;
		assertTrue(controlDemanderJour.moisCorrect(mois, annee));
	}
	
	@Test
	public void test_jourCorrect() {

		assertTrue(controlDemanderJour.jourCorrect(jour, mois, annee));
		GregorianCalendar temp = (GregorianCalendar) jourActuel.clone();
		temp.add(Calendar.DAY_OF_MONTH, 1);
		jour = temp.get(Calendar.DAY_OF_MONTH);
		assertTrue(controlDemanderJour.jourCorrect(jour, mois, annee));
		temp = (GregorianCalendar) jourActuel.clone();
		temp.add(Calendar.DAY_OF_MONTH, -1);
		jour = temp.get(Calendar.DAY_OF_MONTH);
		assertFalse(controlDemanderJour.jourCorrect(jour, mois, annee));
		annee = jourActuel.get(Calendar.YEAR)+3;
		mois = jourActuel.get(Calendar.MONTH)+1 %12;
		jour = jourActuel.get(Calendar.DAY_OF_MONTH);
		assertTrue(controlDemanderJour.jourCorrect(jour, mois, annee));
		annee = jourActuel.get(Calendar.YEAR);
		mois = jourActuel.get(Calendar.MONTH)-1 % 12; 
		assertFalse(controlDemanderJour.jourCorrect(jour, mois, annee)); 
		mois = jourActuel.get(Calendar.MONTH);
		annee = jourActuel.get(Calendar.YEAR)-1;
		assertFalse(controlDemanderJour.jourCorrect(jour, mois, annee));
	}
}
