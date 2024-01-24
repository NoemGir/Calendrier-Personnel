package frontiere.terminal;

import java.util.GregorianCalendar;

import controleur.ControlAfficherCalendrier;

public class BoundaryAfficherCalendrier {
	
	private ControlAfficherCalendrier controlAfficherCalendrier; 

	
	public BoundaryAfficherCalendrier(ControlAfficherCalendrier controlAfficherCalendrier) {
		this.controlAfficherCalendrier = controlAfficherCalendrier;
	}

	protected void afficherinfoPlan(GregorianCalendar jour, int indicePlan) {
		String presentationPlan = controlAfficherCalendrier.presentationPlan(jour, indicePlan);
		if ( presentationPlan != null)
		{
			System.out.println(presentationPlan);
		}
		else {
			afficherIndiceIncorrect();
		}
		
	}

	protected void affichagePlanComplet(GregorianCalendar jour,int indicePlan) {
		String presentationPlan = controlAfficherCalendrier.presentationPlanComplet(jour, indicePlan);
		if ( presentationPlan != null)
		{
			System.out.println(presentationPlan);
		}
		else {
			afficherIndiceIncorrect();
		}
	}
	
	protected void affichageSousTacheComplet(GregorianCalendar jour,int indicePlan, int numSousTache) {

		String presentationSousTache = controlAfficherCalendrier.presentationSousTacheComplet(jour, indicePlan, numSousTache);
		if ( presentationSousTache != null) {
			System.out.println(presentationSousTache);
		}
		else {
			afficherIndiceIncorrect();
		}
	}
 
	public void afficherPlanUnJour(GregorianCalendar jour){
		String presentation = controlAfficherCalendrier.donnerPlanUnJour(jour);
		if(presentation == null) {
			System.out.println("Aucun plan n'est prévu ce jour la...\n");
		}
		else {
			System.out.println(presentation);

		}
	}
	
	public void afficherTousLesPlans() {
		String presentation = controlAfficherCalendrier.presentationEnsemblePlans();
		if(presentation != null) {
			System.out.println(presentation);
		}
		else {
			System.out.println("Aucun plan n'a été créé pour l'instant");
		}
	}

	public void afficherTousLesTrophees() {
		String presentation = controlAfficherCalendrier.presentationEnsembleTrophees();
		if(presentation != null) {
			System.out.println(presentation);
		}
		else {
			System.out.println("Vous n'avez aucun trophés pour le moment !");
		}
	}
	
	private void afficherIndiceIncorrect() {
		System.out.println(" l'indice donné est incorrect");
	}
		
}
