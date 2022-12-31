package main;

import java.util.GregorianCalendar;

public class Tache extends Plan {
	
	private final int NB_MAX_SOUSTACHE = 10;
	protected int nbSousTache = 0;
	protected SousTache[] sousTaches = new SousTache[NB_MAX_SOUSTACHE];
	// private int tempsDeRealisation = 0;
	private boolean accomplie = false;

	public Tache(GregorianCalendar date, String nom, String infoSup, int nbSousTache, SousTache[] sousTaches,
			boolean accomplie) {
		super(date, nom, infoSup);
		this.nbSousTache = nbSousTache;
		this.sousTaches = sousTaches;
		this.accomplie = accomplie;
	}

	public Tache(GregorianCalendar date, String nom, String infoSup, Boolean accomplie) {
		super(date, nom, infoSup);
		this.accomplie = accomplie;
	}

	public Tache(GregorianCalendar date, String nom) {
		super(date, nom, "");
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
		super.affichageComplet();
		afficherSousTaches();
	}

	private void afficherSousTaches() {
		if (nbSousTache > 0) {
			Display.display(" Cette tache possède " + nbSousTache + " sous-taches :\n");
			for (int i = 0; i < nbSousTache; i++) {
				sousTaches[i].afficherPlan(i + 1);
			}
		} else {
			Display.display(" Aucune sous-tache enregistrée\n");
		}
	}

	protected String returnIndicationSiTacheTerminee() {
		if (accomplie) {
			return "(Terminee)";
		} else {
			return "(Non Terminee)";
		}
	}

	public void ajouterSousTache(SousTache nouvelleSousTache, Calendrier calendrierMain) {
		if (nbSousTache == NB_MAX_SOUSTACHE) {
			Display.display("nombre max de sous tache atteint " + NB_MAX_SOUSTACHE + ".\n vous ne pouvez pas en rajouter ");
		}
		else {
		sousTaches[nbSousTache] = nouvelleSousTache;
		nbSousTache++;
		calendrierMain.ajouterUneSousTache();	
		}
		

	}

	public void supprimerSousTache(int numSousTache, Calendrier calendrierMain) {
		sousTaches[numSousTache] = sousTaches[nbSousTache - 1];
		nbSousTache--;
		calendrierMain.retirerUneSousTache();
	}

	public void terminerSousTaches(Calendrier calendrierMain) {
		if (nbSousTache > 0) {
			for (int i = 0; i < nbSousTache; i++) {
				sousTaches[i].setAccomplie(true);
				calendrierMain.retirerUneSousTache();
			}
		}
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
