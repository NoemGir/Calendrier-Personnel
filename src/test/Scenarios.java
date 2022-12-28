package test;


import java.util.GregorianCalendar;
import main.*;


public class Scenarios {
	
	private Main utilisateur;
	private Controleur controleur = Controleur.getInstance();
	
	public Scenarios() {
		setUp();
	}
	
	public void setUp() {
		utilisateur = new Main();
		
		Tache tache1 = new Tache(new GregorianCalendar(2023, 1, 2), "tache 1");
		Tache tache2 = new Tache(new GregorianCalendar(2023, 1, 3), "tache 2");
		Tache tache3 = new Tache(new GregorianCalendar(2023, 1, 1), "tache 3");
		Tache tache4 = new Tache(new GregorianCalendar(2023, 1, 1), "tache 4");
		Evenement event1 = new Evenement(new GregorianCalendar(2023, 1, 2), "evenement 1");
		Evenement event2 = new Evenement(new GregorianCalendar(2023, 1, 1), "evenement 2");
		controleur.creerSousTache(tache1, "sous Tache 1");
		controleur.creerSousTache(tache1, "sous Tache 2");
		controleur.creerSousTache(tache2, "sous tache 3");
		
		 utilisateur.getCalendrier().ajouterPlan(tache1);
		 utilisateur.getCalendrier().ajouterPlan(tache2);
		 utilisateur.getCalendrier().ajouterPlan(tache3);
		 utilisateur.getCalendrier().ajouterPlan(tache4);
		 utilisateur.getCalendrier().ajouterPlan(event1);
		 utilisateur.getCalendrier().ajouterPlan(event2);
		
	}
	
	public void affichageDesPlans() {
		utilisateur.getCalendrier().afficherTousLesPlans();	
	}
	
	public void afficherPlanUnJour() {
		utilisateur.getCalendrier().afficherPlanDeUnJour(new GregorianCalendar(2023, 1, 2));
	}
	
	public void verificationFonctionMiseEnFormatFichier() {
		CommandeShell cs = CommandeShell.getInstance();
		Calendrier calendrier = utilisateur.getCalendrier();
	    System.out.println(cs.toStringFichier(cs.mettreTableauPlanFormatCSV(calendrier.getPlan(), calendrier.getNbPlan()), calendrier.getNbPlan()));
	}
	
	public void modifierDateDeUnPlan() {
		controleur.setMain(utilisateur);
		Calendrier calendrier = utilisateur.getCalendrier();
		int ancienNbPlan = calendrier.getNbPlan();
		
		calendrier.afficherTousLesPlans();
		Display.display("" + calendrier.getNbPlan());
		controleur.changerDateDuPlanReturnNouveauNum(calendrier.getPlan()[0], 1, new GregorianCalendar(2023, 1, 2) );
		int nouveauNbPlan = calendrier.getNbPlan();
		
		calendrier.afficherTousLesPlans();
		
	}
	
	public static void main(String[] args) {
		Scenarios testScenario = new Scenarios();
		
	//	testScenario.affichageDesPlans();
		//testScenario.afficherPlanUnJour();
	//	testScenario.modifierDateDeUnPlan();
		testScenario.verificationFonctionMiseEnFormatFichier();
	}
	
}
