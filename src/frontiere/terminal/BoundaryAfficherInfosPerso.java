package frontiere.terminal;

import java.util.Scanner;

import controleur.ControlAfficherInfosPerso;
import controleur.ControlGestionDonnees;

public class BoundaryAfficherInfosPerso {
	
	private Scanner scan = new Scanner(System.in);
	
	private BoundaryAfficherCalendrier boundaryAfficherCalendrier;
	private BoundaryBoutiquePrincipale boundaryBoutiquePrincipale;
	private ControlAfficherInfosPerso controlAfficherInfosPerso;
	private ControlGestionDonnees controlGestionDonnees;

	public BoundaryAfficherInfosPerso(ControlAfficherInfosPerso controlAfficherInfosPerso,
			ControlGestionDonnees controlGestionDonnees,
			BoundaryAfficherCalendrier boundaryAfficherCalendrier) {
		this.controlAfficherInfosPerso = controlAfficherInfosPerso;
		this.boundaryAfficherCalendrier = boundaryAfficherCalendrier;
		this.controlGestionDonnees = controlGestionDonnees;
	}
	
	private void messageNombreTotal(String nom, String valeur) {
		System.out.println("  - Nombre total d" + nom + " : " + valeur);
	}

	public void afficherInfosPerso() {
		
		String[] infos = controlAfficherInfosPerso.getInfos();
		String stars = infos[0];
		String nbPlanTotal = infos[1];
		String nbTacheTotal = infos[2];
		String nbTacheSimple = infos[3];
		String nbEvent = infos[4];
		String nbTrophees = infos[5];
		String nbStarsTotal = infos[6];
		System.out.println("Vous avez recolte : " + stars + " Stars ! Félicitaton !\n");
		System.out.println(" Statistiques : ");
		messageNombreTotal("e stars obtenues : ", nbStarsTotal );
		messageNombreTotal("e plans", nbPlanTotal);
		messageNombreTotal("e taches a faire ( taches + sous-taches )", nbTacheTotal );
		messageNombreTotal("e taches ( sans les sous-taches )", nbTacheSimple );
		messageNombreTotal("'evenements prevu", nbEvent );
		messageNombreTotal("e trophees", nbTrophees );
	
		//Rajout potentiel : nb heure de sessions effectuees, nb stars gagnes total ( après boutique )
	}
	
public void menuInfoPerso() {
	    System.out.println (" ---- Informations personnelles ---- ");
	    String userInput;
	    
	    do {
	    	afficherInfosPerso();
	    
	    	System.out.println("Selectionnez une option : \n");
		
			System.out.println("1 : Voir tout le plan");
			System.out.println("2 : Voir la liste des trophees");
			System.out.println("3 : Voir les produits achetés");
			System.out.println("4 : Retour au menu");
			System.out.println("5 : Sauvegarder et quitter\n");
	
			userInput = scan.next();
	
			switch (userInput) {
			case "1":
				boundaryAfficherCalendrier.afficherTousLesPlans();
				break;
			case "2":
				afficherTrophees();
				break;
			case "3":
				boundaryBoutiquePrincipale.displayBoutiquePersonnelle();
				break;
			case "4":
				System.out.println("Retour au Menu");
				break;
			case "5":
				controlGestionDonnees.sauvegarderDonnees();
				System.exit(0);
				break;
			default:
				System.out.println("Entrez un entier entre 1 et 4 \n");
			}
	    }while(!"4".equals(userInput));
	}

	public void setBoundaryBoutiquePrincipale(BoundaryBoutiquePrincipale boundaryBoutiquePrincipale) {
	this.boundaryBoutiquePrincipale = boundaryBoutiquePrincipale;
}

	public void afficherTrophees() {
		boundaryAfficherCalendrier.afficherTousLesTrophees();
	}
}
