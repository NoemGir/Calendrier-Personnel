package controleur;

import java.util.GregorianCalendar;

import coeur.UserData;
import coeur.calendrier.Horloge;
import coeur.calendrier.plan.Tache;

public class ControlModifierSousTache {
	private UserData userData;

	public ControlModifierSousTache(UserData userData) {
		this.userData = userData;
	}
	
	public void terminerSousTache(GregorianCalendar jour, int indiceTache, int indiceSousTache) {
		Tache tache = (Tache) userData.getCalendrier().getPlan(jour, indiceTache);
		userData.ajouterStarsTerminaisonSousTache();
		tache.terminerSousTache(indiceSousTache);
	}
	
	public void supprimerSousTache(GregorianCalendar jour, int indiceTache, int indiceSousTache) {
		Tache tache = (Tache) userData.getCalendrier().getPlan(jour, indiceTache);
		tache.supprimerSousTache(indiceSousTache);
	}
	
	public void modifierNom(GregorianCalendar jour, int indicePlan, int indiceSousTache, String nouveauNom) {
		Tache tacheAModifier = (Tache) userData.getCalendrier().getPlan(jour, indicePlan);
		tacheAModifier.setNomSousTache(indiceSousTache, nouveauNom);
	}

	public void modifierInfoSup(GregorianCalendar jour, int indicePlan, int indiceSousTache, String infoSup) {
		Tache tacheAModifier = (Tache) userData.getCalendrier().getPlan(jour, indicePlan);
		tacheAModifier.setInfoSupSousTache(indiceSousTache, infoSup);
	}
	
	public int modifierHeure(GregorianCalendar jour, int indicePlan, int indiceSousTache, int[] heure) {
		Tache tacheAModifier = (Tache) userData.getCalendrier().getPlan(jour, indicePlan);
		Horloge horloge = new Horloge(heure[0], heure[1]);
		
		return tacheAModifier.setHeureSousTache(indiceSousTache, horloge);
	}
}
