package utils;

import java.awt.Color;
import java.util.Calendar;
import java.util.GregorianCalendar;

import coeur.DonneesApplication;

public class Utils {
	
	public static  boolean comparerGregorianCalendar(GregorianCalendar g1, GregorianCalendar g2) {
		return g1.get(Calendar.YEAR) == g2.get(Calendar.YEAR) && 
				g1.get(Calendar.MONTH) == g2.get(Calendar.MONTH) &&
				g1.get(Calendar.DAY_OF_MONTH) == g2.get(Calendar.DAY_OF_MONTH);
	}
	
	public static void setUpDate(GregorianCalendar date) {
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}
	
	public static GregorianCalendar createCalendarFromInt(int[] journee) {
		int annee = journee[0];
		int mois = journee[1];
		int jour = journee[2];
		GregorianCalendar cal = new GregorianCalendar(annee, mois, jour);
		Utils.setUpDate(cal);
		return cal;
	}
	
	public static int[] createIntFromCalendar(GregorianCalendar date) {
		int annee = date.get(Calendar.YEAR);
		int mois = date.get(Calendar.MONTH);
		int jour = date.get(Calendar.DAY_OF_MONTH);
		int[] journee = {annee, mois, jour};
		return journee;
	}
	
	public static String toStringColor(Color couleur) {
		return "RGB : [" + couleur.getRed() + "," + couleur.getGreen() +","+ couleur.getBlue()+ "]";
	}
	
	public static boolean equalsColor(Color couleur1, Color couleur2) {
		return couleur1.getRGB() == couleur2.getRGB() && couleur1.getAlpha() == couleur2.getAlpha();
	}

	public static GregorianCalendar ajouterJours(String input) {
		int valeur;
		try {
			valeur = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return null;
		}
		GregorianCalendar jour = (GregorianCalendar) DonneesApplication.JOUR_ACTUEL.clone();
		jour.add(Calendar.DAY_OF_MONTH, valeur);
		return jour;
	}
}
