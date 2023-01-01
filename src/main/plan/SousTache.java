package main.plan;

import main.principal.Display;
import java.util.GregorianCalendar;

public class SousTache extends Tache {

	public SousTache(GregorianCalendar date, String nom, String infoSup, Boolean accomplie) {
		super(date, nom, infoSup, accomplie);
	}

	public SousTache(GregorianCalendar date, String nom) {
		super(date, nom);
	}

	@Override
	public void afficherPlan(int numDuPlan) {
		Display.display(" -- Sous-Tâche " + numDuPlan + " : " + returnIndicationSiTacheTerminee());
		afficherDateEtNom();
	}

	@Override
	public void afficherPlanComplet() {
		Display.display(" --- Sous-Tâche " + returnIndicationSiTacheTerminee() + " ---\n");
		affichageComplet();
	}
}
