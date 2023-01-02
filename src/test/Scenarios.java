package test;

import java.util.GregorianCalendar;
import main.principal.*;
import main.plan.*;
import main.exterieur.*;

public class Scenarios {

	private Main main = Main.getInstance();
	private Controleur controleur = Controleur.getInstance();

	public Scenarios() {
		setUp();
	}

	public void setUp() {
		Calendrier calendrierMain = main.getCalendrier();
		Tache tache1 = new Tache(new GregorianCalendar(2023, 2, 2), "tache 1");
		Tache tache2 = new Tache(new GregorianCalendar(2023, 2, 3), "tache 2");
		Tache tache3 = new Tache(new GregorianCalendar(2023, 2, 1), "tache 3");
		Tache tache4 = new Tache(new GregorianCalendar(2023, 2, 1), "tache 4");
		Evenement event1 = new Evenement(new GregorianCalendar(2023, 2, 2), "evenement 1", "");
		Evenement event2 = new Evenement(new GregorianCalendar(2023, 2, 1), "evenement 2", "");

		SousTache sousTache1 = new SousTache(new GregorianCalendar(2023, 2, 2), "Sous tache 1",
				"infos sup sous tache 1", false);
		tache1.ajouterSousTache(sousTache1, calendrierMain);
		tache1.setInfoSup("infos sup de la tache 1");
		controleur.creerSousTache(tache1, "sous Tache 2");
		controleur.creerSousTache(tache2, "sous tache 3");

		calendrierMain.ajouterPlan(tache1);
		calendrierMain.ajouterPlan(tache2);
		calendrierMain.ajouterPlan(tache3);
		calendrierMain.ajouterPlan(tache4);
		calendrierMain.ajouterPlan(event1);
		calendrierMain.ajouterPlan(event2);

		controleur.terminerUneTache(tache4, 1);
		controleur.terminerUneTache(tache2, 5);

	}

	public void affichageDesPlans() {
		main.getCalendrier().afficherTousLesPlans();
		Display.display("test reussit :3");
	}

	public void afficherPlanUnJour() {
		main.getCalendrier().afficherPlanDeUnJour(new GregorianCalendar(2023, 2, 2));
		Display.display("test reussit :3");
	}

	public void modifierDateDeUnPlan() {

		Calendrier calendrier = main.getCalendrier();
		int ancienNbPlan = calendrier.getNbPlans();
		GregorianCalendar nouvelleDate = new GregorianCalendar(2023, 2, 5);
		Plan ancienPlan = calendrier.getPlan()[1];
		int nouveauNum = controleur.changerDateDuPlanReturnNouveauNum(ancienPlan, 1, nouvelleDate);
		Plan nouveauPlan = calendrier.getPlan()[nouveauNum];
		int nouveauNbPlan = calendrier.getNbPlans();

		assert (ancienNbPlan == nouveauNbPlan);
		assert (nouveauPlan.getDate() == nouvelleDate);
		assert (ancienPlan.getInfoSup().equals(nouveauPlan.getInfoSup()));
		assert (ancienPlan.getNom().equals(nouveauPlan.getNom()));
		Display.display("test reussit :3");
	}

	public void ajouterUneTache() {
		Calendrier calendrier = main.getCalendrier();
		Tache nouvelleTache = new Tache(new GregorianCalendar(2023, 2, 6), "tache rajoutée");
		int ancienNbPlan = calendrier.getNbPlans();
		int ancienNbTache = calendrier.getNbTacheAFaire();
		int ancienNbSousTache = calendrier.getNbTotalSousTachesAFaire();
		int ancienNbEvenement = calendrier.getNbEvenement();

		calendrier.ajouterPlan(nouvelleTache);

		int nouveauNbPlan = calendrier.getNbPlans();
		int nouveauNbTache = calendrier.getNbTacheAFaire();
		int nouveauNbSousTache = calendrier.getNbTotalSousTachesAFaire();
		int nouveauNbEvenement = calendrier.getNbEvenement();

		assert (nouveauNbPlan == ancienNbPlan + 1);
		assert (nouveauNbTache == ancienNbTache + 1);
		assert (nouveauNbSousTache == ancienNbSousTache);
		assert (nouveauNbEvenement == ancienNbEvenement);
		Display.display("test reussit :3");
	}

	public void ajouterUneTacheAvecSousTaches() {
		Calendrier calendrier = main.getCalendrier();
		Tache nouvelleTache = new Tache(new GregorianCalendar(2023, 2, 6), "tache rajoutée");
		int ancienNbPlan = calendrier.getNbPlans();
		int ancienNbTache = calendrier.getNbTacheAFaire();
		int ancienNbSousTache = calendrier.getNbTotalSousTachesAFaire();
		int ancienNbEvenement = calendrier.getNbEvenement();

		calendrier.ajouterPlan(nouvelleTache);
		controleur.creerSousTache(nouvelleTache, "sousTache1");
		controleur.creerSousTache(nouvelleTache, "sousTache2");

		int nouveauNbPlan = calendrier.getNbPlans();
		int nouveauNbTache = calendrier.getNbTacheAFaire();
		int nouveauNbSousTache = calendrier.getNbTotalSousTachesAFaire();
		int nouveauNbEvenement = calendrier.getNbEvenement();

		assert (nouveauNbPlan == ancienNbPlan + 1);
		assert (nouveauNbTache == ancienNbTache + 1);
		assert (nouveauNbSousTache == ancienNbSousTache + 2);
		assert (nouveauNbEvenement == ancienNbEvenement);
		assert (nouvelleTache.getNbSousTache() == 2);
		Display.display("test reussit :3");
	}

	public void terminerUneTache() {
		Calendrier calendrier = main.getCalendrier();
		int ancienNbPlan = calendrier.getNbPlans();
		int ancienNbTache = calendrier.getNbTacheAFaire();
		int ancienNbTrophee = main.getNbTrophees();

		Tache tacheTerminee = (Tache) calendrier.getPlan()[0];
		controleur.terminerUneTache(tacheTerminee, 0);

		int nouveauNbPlan = calendrier.getNbPlans();
		int nouveauNbTache = calendrier.getNbTacheAFaire();
		int nouveauNbTrophee = main.getNbTrophees();

		assert (nouveauNbPlan == ancienNbPlan - 1);
		assert (nouveauNbTache == ancienNbTache - 1);
		assert (nouveauNbTrophee == ancienNbTrophee + 1);
		assert (tacheTerminee.equals(main.getTrophees()[ancienNbTrophee]));

		Display.display("test reussit :3");
	}

	public void verificationOcaml() {
		Calendrier calendrier = main.getCalendrier();
		Evenement event = new Evenement(new GregorianCalendar(2022, 2, 6), "tache avant la date actuelle",
				"infos supp");
		EnvoyeurDeFichier ef = EnvoyeurDeFichier.getInstance();
		String fichierDonnees = Main.getFichierNouvellesDonnees();
		String fichierPlan = Main.getFichierNouveauPlans();

		calendrier.ajouterPlan(event);
		int ancienNbPlan = calendrier.getNbPlans();
		int ancienNbTache = calendrier.getNbTacheAFaire();
		int ancienNbSousTache = calendrier.getNbTotalSousTachesAFaire();
		int ancienNbEvenement = calendrier.getNbEvenement();

		ef.toutSauvegarder();
		Main.mettreAJourLesDonnees();
		Main.recupererToutesLesDonnees(fichierDonnees, fichierPlan);

		assert (calendrier.getNbPlans() == ancienNbPlan - 1);
		assert (calendrier.getNbTacheAFaire() == ancienNbTache);
		assert (calendrier.getNbEvenement() == ancienNbEvenement - 1);
		assert (calendrier.getNbTotalSousTachesAFaire() == ancienNbSousTache);
		Display.display("test reussit :3");
	}

	// dans la fonction si dessous, les " , \ " affichent une erreur, et les " :: ;
	// " sont suspects;
	public void testInfosOcamlEtPersistance() {
		Calendrier calendrier = main.getCalendrier();
		EnvoyeurDeFichier ef = EnvoyeurDeFichier.getInstance();
		String fichierDonnees = Main.getFichierNouvellesDonnees();
		String fichierPlan = Main.getFichierNouveauPlans();
		calendrier.getPlan()[2].setInfoSup("' :: :: ; fin des tests");
		String ancienneInfoSup = calendrier.getPlan()[2].getInfoSup();
		int ancienNbPlan = calendrier.getNbPlans();
		int ancienNbTache = calendrier.getNbTacheAFaire();
		int ancienNbSousTache = calendrier.getNbTotalSousTachesAFaire();
		int ancienNbEvenement = calendrier.getNbEvenement();

		ef.toutSauvegarder();
		Main.mettreAJourLesDonnees();
		Main.recupererToutesLesDonnees(fichierDonnees, fichierPlan);

		String nouvelleInfoSup = calendrier.getPlan()[2].getInfoSup();
		assert (nouvelleInfoSup.equals(ancienneInfoSup));
		assert (calendrier.getNbPlans() == ancienNbPlan);
		assert (calendrier.getNbTacheAFaire() == ancienNbTache);
		assert (calendrier.getNbEvenement() == ancienNbEvenement);
		assert (calendrier.getNbTotalSousTachesAFaire() == ancienNbSousTache);
		Display.display("test reussit :3");
	}

//	/!\ vérifier que l'argument -ea est bien mit avant de run les scenarios /!\
	public static void main(String[] args) {
		Scenarios testScenario = new Scenarios();

//		testScenario.affichageDesPlans();
//		testScenario.afficherPlanUnJour();
//		testScenario.modifierDateDeUnPlan();
//		testScenario.ajouterUneTache();
//		testScenario.ajouterUneTacheAvecSousTaches();
//		testScenario.terminerUneTache();
//    	testScenario.verificationOcaml();
//		testScenario.testInfosOcamlEtPersistance();
	}

}
