package test;

import java.util.GregorianCalendar;
import main.*;

public class Scenarios {

	private Main utilisateur = Main.getInstance();
	private Controleur controleur = Controleur.getInstance();

	public Scenarios() {
		setUp();
	}

	public void setUp() {
		Calendrier calendrierMain = utilisateur.getCalendrier();
		Tache tache1 = new Tache(new GregorianCalendar(2023, 1, 2), "tache 1");
		Tache tache2 = new Tache(new GregorianCalendar(2023, 1, 3), "tache 2");
		Tache tache3 = new Tache(new GregorianCalendar(2023, 1, 1), "tache 3");
		Tache tache4 = new Tache(new GregorianCalendar(2023, 1, 1), "tache 4");
		Evenement event1 = new Evenement(new GregorianCalendar(2023, 1, 2), "evenement 1", "");
		Evenement event2 = new Evenement(new GregorianCalendar(2023, 1, 1), "evenement 2", "");

		SousTache sousTache1 = new SousTache(new GregorianCalendar(2023, 1, 2), "Sous tache 1",
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
		utilisateur.getCalendrier().afficherTousLesPlans();
	}

	public void afficherPlanUnJour() {
		utilisateur.getCalendrier().afficherPlanDeUnJour(new GregorianCalendar(2023, 1, 2));
	}

	public void verificationFonctionMiseEnFormatFichier() {
		EnvoyeurDeFichier ef = EnvoyeurDeFichier.getInstance();
		Calendrier calendrier = utilisateur.getCalendrier();
		int nbPlan = calendrier.getNbPlans();
		Plan[] plans = calendrier.getPlan();
		System.out.println(ef.toStringFichier(ef.mettreTableauPlanFormatCSV(plans, nbPlan), nbPlan));
	}

	public void creationDuFichierCalendrier() {
		EnvoyeurDeFichier ef = EnvoyeurDeFichier.getInstance();
		int nbPlan = utilisateur.getCalendrier().getNbPlans();
		Plan[] tableauDesPlans = utilisateur.getCalendrier().getPlan();
		String[][] calendrierFormatCSV = ef.mettreTableauPlanFormatCSV(tableauDesPlans, nbPlan);
		ef.modifierFichierCSV("listeDesPlanPrevu.csv", calendrierFormatCSV, nbPlan);
	}
	
	public void creationDuFichierTrophee() {
		EnvoyeurDeFichier ef = EnvoyeurDeFichier.getInstance();
		int nbTrophees = utilisateur.getNbTrophees();
		Plan[] tableauTrophees = utilisateur.getTrophees();
		String[][] calendrierFormatCSV = ef.mettreTableauPlanFormatCSV(tableauTrophees, nbTrophees);
		ef.modifierFichierCSV("trophees.csv", calendrierFormatCSV, nbTrophees);
	}

	public void creationDuFichierDonnee() {
		EnvoyeurDeFichier ef = EnvoyeurDeFichier.getInstance();
		Main main = Main.getInstance();
		ef.sauvegarderDonnees(main);
	}

	public void recuperationDuFichierEnTableauPlan() {
		ReceveurDeFichier rf = ReceveurDeFichier.getInstance();
		Plan[] fichierRecupere = rf.recupererFichierEnTableauPlan("listeDesPlanPrevu.csv");
		Calendrier calendrier = utilisateur.getCalendrier();
		calendrier.setPlans(fichierRecupere);
		calendrier.afficherTousLesPlans();
		calendrier.getPlan()[3].afficherPlanComplet();
	}

	public void recuperationDesDonnees() {
		ReceveurDeFichier rf = ReceveurDeFichier.getInstance();
		int nbEvenements = rf.recupererUneDonnee("donnees.csv", "nbEvenements");
		Display.display("nbEvenements= " + nbEvenements);
		int nbStars = rf.recupererUneDonnee("donnees.csv", "nbStars");
		Display.display("nbStars = " + nbStars);
		int nbTachesAFaire = rf.recupererUneDonnee("donnees.csv", "nbTachesAFaire");
		Display.display("nbTachesAFaire = " + nbTachesAFaire);
		int nbTotalSousTachesAFaire = rf.recupererUneDonnee("donnees.csv", "nbTotalSousTachesAFaire");
		Display.display("nbTotalSousTachesAFaire = " + nbTotalSousTachesAFaire);
	}

	public void modifierDateDeUnPlan() {
		controleur.setMain(utilisateur);
		Calendrier calendrier = utilisateur.getCalendrier();
		int ancienNbPlan = calendrier.getNbPlans();

		calendrier.afficherTousLesPlans();
		Display.display("" + calendrier.getNbPlans());
		controleur.changerDateDuPlanReturnNouveauNum(calendrier.getPlan()[0], 1, new GregorianCalendar(2023, 1, 2));
		int nouveauNbPlan = calendrier.getNbPlans();

		calendrier.afficherTousLesPlans();
	}

	public static void main(String[] args) {
		Scenarios testScenario = new Scenarios();

		 testScenario.affichageDesPlans();
		// testScenario.afficherPlanUnJour();
		// testScenario.modifierDateDeUnPlan();
		// testScenario.verificationFonctionMiseEnFormatFichier();
	   testScenario.creationDuFichierCalendrier();
	   // testScenario.recuperationDuFichierEnTableauPlan();
	    testScenario.creationDuFichierDonnee();
		testScenario.creationDuFichierTrophee();
		
		// testScenario.recuperationDesDonnees();
	}

}
