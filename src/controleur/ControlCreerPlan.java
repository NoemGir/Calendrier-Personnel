package controleur;

import java.util.GregorianCalendar;

import coeur.UserData;
import coeur.calendrier.plan.Evenement;
import coeur.calendrier.plan.Tache;
import utils.Utils;

public class ControlCreerPlan {
	
	private UserData userData;

	public ControlCreerPlan(UserData userData) {
		this.userData = userData;
	}

	
	public void ajouterTache(int[] journee, String nom)
	{
		GregorianCalendar date = Utils.createCalendarFromInt(journee);
		Tache tache = new Tache(date, nom);
		userData.getCalendrier().ajouterPlan(tache);
	}
	
	public void ajouterEvenement(int[] journee, String nom)
	{
		GregorianCalendar date = Utils.createCalendarFromInt(journee);
		Evenement event = new Evenement(date, nom);
		userData.getCalendrier().ajouterPlan(event);
	}
}
