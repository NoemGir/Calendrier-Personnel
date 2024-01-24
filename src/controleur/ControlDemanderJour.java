package controleur;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.GregorianCalendar;

import coeur.DonneesApplication;
import coeur.calendrier.Horloge;
import utils.Utils;

public class ControlDemanderJour {
	
	
	public boolean verifierDateDonnee(String input) {
		String[] valeurs = input.split("/");
		if(valeurs.length != 3) {
			return false;
		}
		int[] journee = recupererDate(input);
		return jourCorrect(journee[0], journee[1],journee[2]);
		
	}
	
	public boolean verifierHeureDonnee(String input) {
		String[] valeurs = input.split(":");
		if(valeurs.length != 2) {
			valeurs = input.split("h");
			if (valeurs.length != 2 ) {
				return false;
			}
		}
		int[] heure = recupererHeure(input);
		return !heureIncorrect(heure[0], heure[1]);
		
	}
	
	public int[] recupererHeure(String heure) {
		String[] valeurs = heure.split(":");
		if(valeurs.length != 2) {
			valeurs = heure.split("h");
		}
		int[] heureInt = new int[2];
		heureInt[0] = Integer.valueOf(valeurs[0]);
		heureInt[1] = Integer.valueOf(valeurs[1]);
		return heureInt;
	}
	
	public int[] recupererDate(String jour) {
		String[] valeurs = jour.split("/");
		int[] journee = new int[3];
		journee[0] = Integer.valueOf(valeurs[2]);
		journee[1] = Integer.valueOf(valeurs[1])-1;
		journee[2] = Integer.valueOf(valeurs[0]);
		return journee;
	}

	public Boolean anneeCorrect(int annee) {
		return (annee != -1 && annee >= DonneesApplication.JOUR_ACTUEL.get(Calendar.YEAR));
	}
	
	public Boolean moisCorrect(int moisDonne, int anneeDonnee) {
		
		if (0 > moisDonne || moisDonne > 11 ){
			return false;
		}
		else {
			int anneeCourante = DonneesApplication.JOUR_ACTUEL.get(Calendar.YEAR);
			int moisCourant =  DonneesApplication.JOUR_ACTUEL.get(Calendar.MONTH);
			if ( !anneeCorrect(anneeDonnee) || (anneeDonnee == anneeCourante && moisDonne < moisCourant )) {
				return false;
			}
		}
		return true;
	}
	
	public Boolean jourCorrect(int annee, int mois, int jour) {
		
		GregorianCalendar journeeDonnee = new GregorianCalendar(annee, mois, jour);
		GregorianCalendar jourActuel = (GregorianCalendar)  DonneesApplication.JOUR_ACTUEL.clone();
		return journeeDonnee.after(jourActuel) || Utils.comparerGregorianCalendar(journeeDonnee, jourActuel);
	}
	
	public boolean heureIncorrect(int heure, int minutes) {
		try {
			Horloge horloge = new Horloge(heure, minutes);
		} 
		catch(IllegalArgumentException e) {
			return true;
		}
		return false;
	}
}