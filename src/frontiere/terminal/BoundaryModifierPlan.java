package frontiere.terminal;

import java.util.GregorianCalendar;
import java.util.Scanner;

import controleur.ControlGestionDonnees;
import controleur.ControlModifierPlan;
import controleur.ControlVerificationPlanSelectionne;
import utils.Utils;

public class BoundaryModifierPlan {
	
	private Scanner scan = new Scanner(System.in);
	
	private ControlVerificationPlanSelectionne controlVerificationPlanSelectionne;
	private ControlModifierPlan controlModifierPlan;
	private BoundaryDemanderJour boundaryDemanderJour;
	private BoundaryAfficherCalendrier boundaryAfficherCalendrier;
	private BoundaryModifierSousTache boundaryModifierSousTache;
	private ControlGestionDonnees controlGestionDonnees;
	
	
	public BoundaryModifierPlan(ControlVerificationPlanSelectionne controlVerificationPlanSelectionne,
			ControlModifierPlan controlModifierPlan,
			ControlGestionDonnees controlGestionDonnees,
			BoundaryDemanderJour boundaryDemanderJour,
			BoundaryAfficherCalendrier boundaryAfficherCalendrier,
			BoundaryModifierSousTache boundaryModifierSousTache) {
		this.controlVerificationPlanSelectionne = controlVerificationPlanSelectionne;
		this.controlModifierPlan = controlModifierPlan;
		this.boundaryDemanderJour = boundaryDemanderJour;
		this.boundaryAfficherCalendrier = boundaryAfficherCalendrier;
		this.boundaryModifierSousTache = boundaryModifierSousTache;
		this.controlGestionDonnees = controlGestionDonnees;
	}

	public GregorianCalendar menuModifierPlan(GregorianCalendar jour, int indicePlan) {

		if (controlModifierPlan.planIsTache(jour, indicePlan)){
			 return menuModifierTache( jour, indicePlan );
		}
		else {
			 return menuModifierEvenement(jour, indicePlan);
		}
	}

	private GregorianCalendar menuModifierEvenement(GregorianCalendar jour, int indiceEvenement) {
		String userInput;
		
		do {
			System.out.println(" ---- menu Modifier Evenement ---- \n");
	
			boundaryAfficherCalendrier.affichagePlanComplet(jour, indiceEvenement);
			
			System.out.println("Choisisez une option : \n");
	
			System.out.println("1 : Changer le nom");
			System.out.println("2 : Changer le jour");
			System.out.println("3 : Changer les informations supplémentaires");
			System.out.println("4 : Changer l'heure");
			System.out.println("5 : Supprimer l'événement");
			System.out.println("6 : Retour au jour l'événement"); 
			System.out.println("7 : Sauvegarder et quitter\n"); 
	
			userInput = scan.next();
			
	
			switch (userInput) {
			case "1":
				changerNom(jour, indiceEvenement);
				break;
			case "2":
				GregorianCalendar nouveauJour = Utils.createCalendarFromInt(boundaryDemanderJour.demanderUnJour());
				indiceEvenement = controlModifierPlan.modifierDate(jour, indiceEvenement, nouveauJour);
				jour = nouveauJour;
				break;
			case "3":
				changerInfoSup(jour, indiceEvenement);
				break;
			case "4":
				int indice = changerHeure(jour, indiceEvenement);
				if(indice != -1) {
					indiceEvenement = indice;
				}
				break;
			case "5":
				controlModifierPlan.supprimerPlan(jour, indiceEvenement);
				break;
			case "7":
				sauvegarderEtQuitter();
				break;
			default:
				System.out.println("Format demandé = entier entre 1 et 7");
			}
		}while(!userInput.equals("6"));
		return jour;
	}
	
	private GregorianCalendar menuModifierTache(GregorianCalendar jour,  int indiceTache ) {
		String userInput;
		do {
		
			System.out.println(" ---- menu Modifier Tache ---- \n");

			boundaryAfficherCalendrier.affichagePlanComplet(jour, indiceTache);
	
			System.out.println("1 : Changer le nom");
			System.out.println("2 : Changer le jour");
			System.out.println("3 : Changer les informations supplémentaires");
			System.out.println("4 : Changer l'heure");
			System.out.println("5 : Terminer la tâche");
			System.out.println("6 : Creer une sous tâche");
			System.out.println("7 : Supprimer la tâche");
			System.out.println("8 : Retour au jour de la tâche");
			System.out.println("9 : Sauvegarder et quitter\n");
			
			System.out.println("Selectionner une option ou une sous-tâche (sousTâche [numSousTâche]): \n");
	
			userInput = Clavier.entrerString("");
	
			switch (userInput) {
			case "1":
				changerNom(jour, indiceTache);
				break;
			case "2":
				GregorianCalendar nouveauJour = Utils.createCalendarFromInt(boundaryDemanderJour.demanderUnJour());
				indiceTache = controlModifierPlan.modifierDate(jour, indiceTache, nouveauJour);
				jour = nouveauJour;
				break;
			case "3":
				changerInfoSup(jour, indiceTache);
				break;
			case "4":
				int indice = changerHeure(jour, indiceTache);
				if(indice != -1) {
					indiceTache = indice;
				}
				break;
			case "5":
				System.out.println("Félicitation ! Vous venez de terminer une nouvelle tâche ! *musique*\n"
						+ "Elle sera ajoutée a vos trophées\n");
				controlModifierPlan.terminerTache(jour, indiceTache);
				break;
			case "6":
				String nomSousTache = Clavier.entrerString("Quel nom voulez vous donner à la sous-tâche ?");
				controlModifierPlan.ajouterSousTache(jour, indiceTache, nomSousTache);
				break;
			case "7":
				controlModifierPlan.supprimerPlan(jour, indiceTache); 
				break;
			case "9":
				sauvegarderEtQuitter();
				break;
			default:
				int indiceSousTache = controlVerificationPlanSelectionne.sousTacheSelectionnee(jour, userInput, indiceTache);
				if ( indiceSousTache > 0) {
					boundaryModifierSousTache.menuModifierSousTache(jour, indiceTache, indiceSousTache-1);
					}
				else {
					System.out.println("Format demandé = 'soustache <numérosoustache>' ou  0 <= '<entier>' <= 9 \n");
				}
			}
		}while(!userInput.equals("5") && !userInput.equals("7") && !userInput.equals("8"));
		return jour;
	}

	
	void changerNom(GregorianCalendar jour, int indicePlan) {
		String nomPlan = Clavier.entrerString("Quel nom voulez vous donner au plan ?");
		controlModifierPlan.modifierNomPlan(jour, indicePlan, nomPlan);
	}
	
	void changerInfoSup(GregorianCalendar jour, int indicePlan) {
		String infoSup = Clavier.entrerString("Donnez les informations supplémentaires : ");
		controlModifierPlan.modifierInfoSupPlan(jour, indicePlan, infoSup);
	}
	
	
	private int changerHeure(GregorianCalendar jour, int indicePlan) {
		int[] heure = boundaryDemanderJour.demanderHeure();
		int nouvelIndice = -1;
		if (heure != null) {
			nouvelIndice = controlModifierPlan.modifierHeurePlan(jour, indicePlan, heure);
		}
		return nouvelIndice;
	}
	
	public void sauvegarderEtQuitter() {
		controlGestionDonnees.sauvegarderDonnees();
		System.exit(0);
	}
}








