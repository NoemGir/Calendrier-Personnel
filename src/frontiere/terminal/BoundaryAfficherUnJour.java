package frontiere.terminal;

import java.util.Calendar;
import java.util.GregorianCalendar;

import coeur.DonneesApplication;
import controleur.ControlGestionDonnees;
import controleur.ControlVerificationPlanSelectionne;
import utils.Utils;

public class BoundaryAfficherUnJour {
		
	private BoundaryDemanderJour boundaryDemanderJour;
	private BoundaryCreerPlan boundaryCreerPlan;
	private BoundaryModifierPlan boundaryModifierPlan;
	private ControlVerificationPlanSelectionne controlVerificationPlanSelectionne;
	private ControlGestionDonnees controlGestionDonnees;
	private BoundaryAfficherCalendrier boundaryAfficherCalendrier;

	public BoundaryAfficherUnJour(BoundaryDemanderJour boundaryDemanderJour, BoundaryCreerPlan boundaryCreerPlan,
			BoundaryModifierPlan boundaryModifierPlan,
			ControlGestionDonnees controlGestionDonnees,
			ControlVerificationPlanSelectionne controlVerificationPlanSelectionne,
			BoundaryAfficherCalendrier boundaryAfficherCalendrier) {
		this.boundaryDemanderJour= boundaryDemanderJour;
		this.boundaryCreerPlan  = boundaryCreerPlan;
		this.boundaryModifierPlan = boundaryModifierPlan;
		this.controlVerificationPlanSelectionne = controlVerificationPlanSelectionne;
		this.controlGestionDonnees = controlGestionDonnees;
		this.boundaryAfficherCalendrier = boundaryAfficherCalendrier;
	}
	
	public void displayMenuUnJour(GregorianCalendar jourDuCalendrier) {

		GregorianCalendar jourActuel = (GregorianCalendar)  DonneesApplication.JOUR_ACTUEL.clone();
		String userInput;

		do {
			System.out.println(" --- Le " + DonneesApplication.DF.format(jourDuCalendrier.getTime()) + " : --- \n");
	
			boundaryAfficherCalendrier.afficherPlanUnJour((GregorianCalendar) jourDuCalendrier.clone());
	
			System.out.println("1 : Jour précédent");
			System.out.println("2 : Jour suivant");
			System.out.println("3 : Changer de jour");
			System.out.println("4 : creer un plan pour ce jour");
			System.out.println("5 : Voir toutes les plans enregistrés");
			System.out.println("6 : Retour au menu principal");
			System.out.println("7 : Sauvegarder et quitter\n"); 
			
			System.out.println("Selectionnez un plan (plan <numéroDuPlan>) ou une option : \n");
			userInput = Clavier.entrerString("");
			
			switch (userInput) {
			case "1":
				jourDuCalendrier.add(Calendar.DAY_OF_MONTH, -1);
				if (jourDuCalendrier.getTime().before(jourActuel.getTime())) {
					System.out.println("Il n'est pas possible de voir les jours précédents le jour actuel \n");
					jourDuCalendrier = jourActuel;
				}
				break;
			case "2":
				jourDuCalendrier.add(Calendar.DAY_OF_MONTH, 1);
				break;
			case "3":
				int[] journee = boundaryDemanderJour.demanderUnJour(); 
				int annee = journee[0];
				int mois = journee[1];
				int jour = journee[2];
				jourDuCalendrier = new GregorianCalendar (annee, mois, jour);
				break;
			case "4":
				journee = Utils.createIntFromCalendar(jourDuCalendrier);
				boundaryCreerPlan.creerPlan(journee);
				break;
			case "5":
				boundaryAfficherCalendrier.afficherTousLesPlans();
				break;
			case "6":
				break;
			case "7":
				controlGestionDonnees.sauvegarderDonnees();
				System.exit(0);
				break; 
			default:
				int indicePlan = controlVerificationPlanSelectionne.planSelectionne( jourDuCalendrier, userInput);
				if ( indicePlan > 0) { 
					jourDuCalendrier = (GregorianCalendar) boundaryModifierPlan.menuModifierPlan(jourDuCalendrier, indicePlan-1).clone();
					}
				else {
					System.out.println("Format demandé = 'plan <numéroDuPlan>' ou  0 <= '<entier>' <= 7 \n");
				}
			}
		}while(!userInput.equals("6"));
	}
	
	


}
