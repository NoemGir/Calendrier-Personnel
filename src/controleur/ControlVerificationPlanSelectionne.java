package controleur;


import java.util.GregorianCalendar;

import coeur.UserData;
import coeur.calendrier.plan.Tache;

public class ControlVerificationPlanSelectionne {
	private UserData userData;

	public ControlVerificationPlanSelectionne(UserData userData) {
		this.userData = userData;
	}
 
	public int planSelectionne(GregorianCalendar jour, String userInput) {
		int numDuPlan = -1;
		if (userInput.length() > 5) {
			numDuPlan = Integer.parseInt(userInput.split(" ")[1]);
			if (numDuPlan < 1 || numDuPlan > userData.getCalendrier().planDeUnJour(jour).size()) {
				numDuPlan = -1;
			}
		}
		return numDuPlan;
	}

	public int sousTacheSelectionnee(GregorianCalendar jour, String userInput, int indiceTache) {
		int numSousTache = -1;
		if (userInput.length() > 10) {
			Tache tacheAModifier = (Tache) userData.getCalendrier().getPlan(jour, indiceTache);
			if(tacheAModifier != null) {
				numSousTache = Integer.parseInt(userInput.split(" ")[1]);
				if (numSousTache < 1 || numSousTache > tacheAModifier.getNbSousTache()){
					numSousTache = -1;
				}
			}
		}
		return numSousTache;
	}	
}
