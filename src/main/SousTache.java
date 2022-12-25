package main;

import java.util.GregorianCalendar;

public class SousTache extends Tache {

	public SousTache(GregorianCalendar date, String nom) {
		super(date, nom);
	}

	@Override
	public void afficherPlan(int numDuPlan) {
		Display.display(" -- Sous-Tache " + numDuPlan + " : " + returnIndicationSiTacheTerminee());
		afficherDateEtNom();
	}

	@Override
	public void afficherPlanComplet() {
		Display.display(" --- Sous-Tache " + returnIndicationSiTacheTerminee() + " ---\n");
		affichageComplet();
	}
}