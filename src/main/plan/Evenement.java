package main.plan;

import main.principal.Display;
import java.util.GregorianCalendar;

public class Evenement extends Plan {

	public Evenement(GregorianCalendar date, String nom, String infoSup) {
		super(date, nom, infoSup);
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
