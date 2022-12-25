package main;

import java.util.GregorianCalendar;

public class Evenement extends Plan {

	public Evenement(GregorianCalendar date, String nom) {
		super(date, nom);
	}

	public Evenement(GregorianCalendar date) {
		super(date);
	}

	@Override
	public void afficherPlan(int numDuPlan) {
		Display.display("Evenement " + numDuPlan + " : ");
		afficherDateEtNom();
	}

	@Override
	public void afficherPlanComplet() {
		Display.display(" --- Evenement ---\n");
		affichageComplet();
	}
}
