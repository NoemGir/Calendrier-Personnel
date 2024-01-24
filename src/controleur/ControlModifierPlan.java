package controleur;

import java.util.GregorianCalendar;

import coeur.UserData;
import coeur.calendrier.Calendrier;
import coeur.calendrier.Horloge;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

public class ControlModifierPlan {
	UserData userData;

	public ControlModifierPlan(UserData userData) {
		this.userData = userData;
	}
	
	public void supprimerPlan(GregorianCalendar jour, int indicePlan) {
		userData.getCalendrier().retirerPlan(jour, indicePlan);
	}
	
	public boolean terminerTache(GregorianCalendar jour, int indiceTache) {
		Tache tacheTerminee = (Tache) userData.getCalendrier().getPlan(jour, indiceTache);
		return userData.terminerTache(tacheTerminee);
	}
	
	
	public boolean modifierNomPlan(GregorianCalendar jour, int indicePlan, String nouveauNom) {
		Plan planAModifier = userData.getCalendrier().getPlan(jour, indicePlan);
		if(planAModifier != null) {
			planAModifier.setNom(nouveauNom);
			return true;
		}
		return false;
	}
	
	
	public boolean modifierInfoSupPlan(GregorianCalendar jour, int indicePlan, String infoSup) {
		Plan planAModifier = userData.getCalendrier().getPlan(jour, indicePlan);
		if(planAModifier != null) {
			planAModifier.setInfoSup(infoSup);
			return true;
		}
		return false;
	}
	
	public int modifierHeurePlan(GregorianCalendar jour, int indicePlan, int[] heure) {
		Plan planAModifier = userData.getCalendrier().getPlan(jour, indicePlan);
		int nouveauNum = -1;
		if(planAModifier != null) {
			Calendrier<Plan> cal = userData.getCalendrier();
			Horloge nouvelleHeure = new Horloge(heure[0], heure[1]);
			
			cal.retirerPlan(planAModifier);
			planAModifier.setHeure(nouvelleHeure);
			cal.ajouterPlan(planAModifier);
			nouveauNum = cal.obtenirIndicePlan(planAModifier,jour);
		}
		return nouveauNum;
	}
	
	public boolean planIsTache(GregorianCalendar jour, int indicePlan) {
		
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		return plan != null && plan.getClass() == Tache.class;
	}
	
	public int modifierDate(GregorianCalendar ancienneDate, int numDuPlan, GregorianCalendar nouvelleDate) {

		Plan planAModifier = userData.getCalendrier().getPlan(ancienneDate, numDuPlan);
		int nouveauNum = -1;
		
		if(planAModifier != null) {
			Calendrier<Plan> cal = userData.getCalendrier();
			cal.retirerPlan(planAModifier);
			planAModifier.setDate(nouvelleDate);
			cal.ajouterPlan(planAModifier);
			nouveauNum = cal.obtenirIndicePlan(planAModifier, nouvelleDate);
		}
		return nouveauNum;
	}
	
	public void ajouterSousTache(GregorianCalendar jour, int indiceTache, String nomSousTache) {

		Tache tacheAModifier = (Tache) userData.getCalendrier().getPlan(jour, indiceTache);
		tacheAModifier.ajouterSousTache(nomSousTache);
	}
}












