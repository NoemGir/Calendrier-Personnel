package frontiere.terminal;

import java.util.GregorianCalendar;
import java.util.Scanner;

import controleur.ControlGestionDonnees;
import controleur.ControlModifierSousTache;

public class BoundaryModifierSousTache {
	
	private Scanner scan = new Scanner(System.in);
	
	private ControlModifierSousTache controlModifierSousTache;
	private BoundaryAfficherCalendrier boundaryAfficherCalendrier;
	private BoundaryDemanderJour boundaryDemanderJour;
	private ControlGestionDonnees controlGestionDonnees;


	public BoundaryModifierSousTache(BoundaryAfficherCalendrier boundaryAfficherCalendrier,
			ControlModifierSousTache controlModifierSousTache,
			ControlGestionDonnees controlGestionDonnees,
			BoundaryDemanderJour boundaryDemanderJour) {
		this.boundaryAfficherCalendrier = boundaryAfficherCalendrier;
		this.controlModifierSousTache = controlModifierSousTache;
		this.controlGestionDonnees = controlGestionDonnees;
		this.boundaryDemanderJour = boundaryDemanderJour;
	}
	
	public void menuModifierSousTache(GregorianCalendar jour, int indiceTache, int indiceSousTache) {
		
		String userInput;
		do {
			boundaryAfficherCalendrier.affichageSousTacheComplet(jour, indiceTache, indiceSousTache);

			System.out.println("1 : Changer le nom");
			System.out.println("2 : Changer les informations supplémentaires");
			System.out.println("3 : Changer l'heure");
			System.out.println("4 : Terminer la sous-tâche");
			System.out.println("5 : Supprimer");
			System.out.println("6 : Retour à la tâche");
			System.out.println("7 : Sauvegarder et quitter\n");
	
			System.out.println("Séléctionnez une option");
			userInput = Clavier.entrerString("");
	
			switch (userInput) {
			case "1":
				String nomPlan = Clavier.entrerString("Quel nom voulez vous donner a la sous tache ?");
				controlModifierSousTache.modifierNom(jour, indiceTache,indiceSousTache, nomPlan);
				break;
			case "2":
				String infoSup = Clavier.entrerString("Donnez les informations supplémentaires : ");
				controlModifierSousTache.modifierInfoSup(jour, indiceTache,indiceSousTache, infoSup);
				break;
			case "3":
				int[] heure = boundaryDemanderJour.demanderHeure();
				if (heure != null) {
					indiceSousTache = controlModifierSousTache.modifierHeure(jour, indiceTache,indiceSousTache, heure);
				}
				break;
			case "4":
				controlModifierSousTache.terminerSousTache(jour, indiceTache,indiceSousTache);
				displayInfoSousTacheFinie(jour, indiceTache, indiceSousTache);
				break;
			case "5":
				controlModifierSousTache.terminerSousTache(jour, indiceTache,indiceSousTache);
				break;
			case "7":
				controlGestionDonnees.sauvegarderDonnees();
				System.exit(0);
				break;
			default:
				System.out.println("La valeur doit être entre 1 et 6");
			}
		}while( !userInput.equals("4") && !userInput.equals("5") && !userInput.equals("6"));
		
		if(userInput.equals("4")) {
			
		}
	}
	
	
	private void displayInfoSousTacheFinie(GregorianCalendar jour, int numTache, int numSousTache) {

		String userInput;
		
		boundaryAfficherCalendrier.affichageSousTacheComplet(jour, numTache, numSousTache);

		System.out.println("La sous-tache est terminée, impossible de la modifier\n");
		System.out.println("1 : Retour â la tâche");
		System.out.println("2 : Sauvegarder et quitter\n");

		userInput = scan.next();
		do {
			switch (userInput) {

			case "2":
				controlGestionDonnees.sauvegarderDonnees();
				System.exit(0);
				break;
			default:
				System.out.println("Entrez un entier entre 1 et 2");
			}
		} while(!userInput.equals("1"));
	}

}
