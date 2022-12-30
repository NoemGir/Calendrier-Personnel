package main;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calendrier {

	private static Calendrier instance = new Calendrier();
	private int nbTachesAFaire = 0;
	private int nbTotalSousTachesAFaire = 0;
	private int nbPlans = 0;
	private int nbEvenements = 0;
	private Plan[] plans = new Plan[10];

	public void afficherPlanDeUnJour(GregorianCalendar jourAAfficher) {

		if (nbPlans > 0) {
			int i = 0;
			while (i < nbPlans && plans[i].getDate().getTime().before(jourAAfficher.getTime())) {
				i++;
			}
			int j = i;
			jourAAfficher.add(Calendar.DAY_OF_MONTH, 1);
			while (j < nbPlans && plans[j].getDate().getTime().before(jourAAfficher.getTime())) {
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

		int i = nbPlans;
		while (i > 0 && plan.getDate().before(plans[i - 1].getDate())) {
			plans[i] = plans[i - 1];
			i--;
		}
		plans[i] = plan;
		nbPlans++;
		if (plan instanceof Tache) {
			nbTachesAFaire++;
		} else {
			nbEvenements++;
		}
		return i;
	}

	public void retirerPlan(int numDuPlan) {
		if (plans[numDuPlan] instanceof Tache) {
			nbTachesAFaire--;
		} else {
			nbEvenements--;
		}
		while (numDuPlan < nbPlans - 1) {
			plans[numDuPlan] = plans[numDuPlan + 1];
			numDuPlan++;
		}
		nbPlans--;
	}

	public void afficherTousLesPlans() {
		if (nbPlans > 0) {
			Display.display("Vous avez " + nbPlans + " plans enregistrés :\n");
			for (int i = 0; i < nbPlans; i++) {
				plans[i].afficherPlan(i + 1);

			}
		} else {
			Display.display("Aucun plan n'est prévu pour le moment\n");
		}

	}

	public int getNbPlans() {
		return nbPlans;
	}

	public Plan[] getPlan() {
		return plans;
	}

	public int getNbTacheAFaire() {
		return nbTachesAFaire;
	}

	public int getNbEvenement() {
		return nbEvenements;
	}

	public int getNbTotalSousTachesAFaire() {
		return nbTotalSousTachesAFaire;
	}

	public void ajouterUneSousTache() {
		nbTotalSousTachesAFaire++;
	}

	public void retirerUneSousTache() {
		nbTotalSousTachesAFaire--;
	}

	public void setPlans(Plan[] plans) {
		this.plans = plans;
	}

	public void setNbPlan(int nbPlan) {
		this.nbPlans = nbPlan;
	}

	public void setNbTachesAFaires(int nbTachesAFaire) {
		this.nbTachesAFaire = nbTachesAFaire;
	}

	public void setNbTotalSousTachesAFaire(int nbTotalSousTaches) {
		this.nbTotalSousTachesAFaire = nbTotalSousTaches;
	}

	public void setNbPlans(int nbPlans) {
		this.nbPlans = nbPlans;
	}

	public void setNbEvenements(int nbEvenements) {
		this.nbEvenements = nbEvenements;
	}

	public static Calendrier getInstance() {
		return instance;
	}
}
