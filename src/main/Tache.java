package main;

import java.util.GregorianCalendar;

public class Tache extends Plan {

	protected int nbSousTache = 0;
	protected SousTache[] sousTaches = new SousTache[20];
	// private int tempsDeRealisation = 0;
	private boolean accomplie = false;

	public Tache(GregorianCalendar date, String nom) {
		super(date, nom);
	}

	public void setAccomplie(boolean accomplie) {
		this.accomplie = accomplie;
	}

	@Override
	public void afficherPlan(int numDuPlan) {

		Display.display("Tache " + numDuPlan + " : " + returnIndicationSiTacheTerminee());
		afficherDateEtNom();
		afficherSousTaches();
	}

	@Override
	public void afficherPlanComplet() {

		Display.display(" --- Tache " + returnIndicationSiTacheTerminee() + " ---\n");
		affichageComplet();
		afficherSousTaches();
	}

	private void afficherSousTaches() {
		if (nbSousTache > 0) {
			Display.display(" Cette tache possède " + nbSousTache + " sous-taches :\n");
			for (int i = 0; i < nbSousTache; i++) {
				sousTaches[i].afficherPlan(i + 1);
			}
			Display.display(" Aucune sous-tache enregistrée\n");
		}
	}

	public String returnIndicationSiTacheTerminee() {
		if (accomplie) {
			return "(Terminee)";
		} else {
			return "(Non Terminee)";
		}
	}

	public void ajouterSousTache(SousTache nouvelleSousTache) {
		sousTaches[nbSousTache] = nouvelleSousTache;
		nbSousTache++;
	}

	public void supprimerSousTache(int numSousTache) {
		sousTaches[numSousTache] = sousTaches[nbSousTache - 1];
		nbSousTache--;
	}

	public int getNbSousTache() {
		return nbSousTache;
	}

	public SousTache[] getSousTaches() {
		return sousTaches;
	}

	public boolean isAccomplie() {
		return accomplie;
	}

	@Override
	public void setDate(GregorianCalendar nouvelleDate) {
		super.date = nouvelleDate;
		for (int i = 0; i < nbSousTache; i++) {
			sousTaches[i].date = nouvelleDate;
		}
	}

}
