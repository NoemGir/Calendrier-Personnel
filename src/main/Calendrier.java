package main;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calendrier {
	private int nbTacheAFaire = 0;
	private int nbSousTache = 0;
	private int nbPlan = 0;
	private int nbEvenement = 0;
	private Plan[] plans = new Plan[10];

	public void afficherPlanDeUnJour(GregorianCalendar jourAAfficher) {

		if (nbPlan > 0) {
			int i = 0;
			while (i < nbPlan && plans[i].getDate().getTime().before(jourAAfficher.getTime())) {
				i++;
			}
			int j = i;
			jourAAfficher.add(Calendar.DAY_OF_MONTH, 1);
			while (j < nbPlan && plans[j].getDate().getTime().before(jourAAfficher.getTime())) {
				plans[j].afficherPlan(j + 1);
				j++;
			}

			if (i == j) {
				Display.display("Aucun plan n'est prevu ce jour la\n");
			}
		} else {
			Display.display("Aucun plan n'est prevu ce jour la\n");
		}

	}

	public int ajouterPlan(Plan plan) {

		int i = nbPlan;
		while (i > 0 && plan.getDate().before(plans[i - 1].getDate())) {
			plans[i] = plans[i - 1];
			i--;
		}
		plans[i] = plan;
		nbPlan++;
		if (plan instanceof Tache) {
			nbTacheAFaire++;
		} else {
			nbEvenement++;
		}
		return i;
	}

	public void retirerPlan(int numDuPlan) {
		if (plans[numDuPlan] instanceof Tache) {
			nbTacheAFaire--;
		} else {
			nbEvenement--;
		}
		while (numDuPlan < nbPlan - 1) {
			plans[numDuPlan] = plans[numDuPlan + 1];
			numDuPlan++;
		}
		nbPlan--;
	}

	public void afficherTousLesPlans() {
		if (nbPlan > 0) {
			Display.display("Vous avez " + nbPlan + " plans enregistrés :\n");
			for (int i = 0; i < nbPlan; i++) {
				plans[i].afficherPlan(i + 1);

			}
		} else {
			Display.display("Aucun plan n'est prévu pour le moment\n");
		}

	}

	public int getNbPlan() {
		return nbPlan;
	}

	public Plan[] getPlan() {
		return plans;
	}

	public int getNbTacheAFaire() {
		return nbTacheAFaire;
	}

	public int getNbEvenement() {
		return nbEvenement;
	}

	public int getNbSousTache() {
		return nbSousTache;
	}

	public void ajouterUneSousTache() {
		nbSousTache++;
	}

	public void retirerUneSousTache() {
		nbSousTache--;
	}

}
