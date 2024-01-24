package frontiere.terminal;

import controleur.ControlAfficherBoutique;
import controleur.ControlAfficherInfosPerso;
import controleur.ControlGestionDonnees;
import controleur.ControlVerificationBoutique;

public class BoundaryBoutiquePrincipale {
	
	private ControlGestionDonnees controlGestionDonnees;
	private ControlAfficherBoutique controlAfficherBoutique;
	private ControlAfficherInfosPerso controlAfficherInfosPerso;
	private ControlVerificationBoutique controlVerificationBoutique;
	private BoundaryAfficherInfosPerso boundaryAfficherInfosPerso;
	private BoundaryBoutique boundaryBoutique;



	
	public BoundaryBoutiquePrincipale(ControlAfficherBoutique controlAfficherBoutique,
			ControlAfficherInfosPerso controlAfficherInfosPerso,
			ControlGestionDonnees controlGestionDonnees,
			ControlVerificationBoutique controlVerificationBoutique,
			BoundaryAfficherInfosPerso boundaryAfficherInfosPerso, BoundaryBoutique boundaryBoutique) {
		
		this.controlAfficherBoutique = controlAfficherBoutique;
		this.controlAfficherInfosPerso = controlAfficherInfosPerso;
		this.controlVerificationBoutique = controlVerificationBoutique;
		this.controlGestionDonnees = controlGestionDonnees;
		this.boundaryAfficherInfosPerso = boundaryAfficherInfosPerso;
		this.boundaryBoutique = boundaryBoutique;
		boundaryAfficherInfosPerso.setBoundaryBoutiquePrincipale(this);
		boundaryBoutique.setBoundaryBoutiquePrincipale(this);
	}



	public void displayBoutique() {

		String userInput;
		do {
			
			System.out.println("\n --- Boutique Principale --- \n");
			System.out.println("Vous possedez "+ controlAfficherInfosPerso.getNbStars() + " stars ");
			
			if(controlAfficherBoutique.boutiquesRestantes()) {
				System.out.println("Voici les boutiques dont vous avez accès :\n");
				System.out.println(controlAfficherBoutique.getPresentationBoutique());
			}
			else {
				System.out.println("Vous avez acheté tous les produits disponibles ! \n");
			}
			
			
			System.out.println("\n1 : Infos Perso");
			System.out.println("2 : retour");
			System.out.println("3 : Sauvegarder et quitter\n");
			System.out.println("Selectionnez une boutique (nom de la boutique) ou une option : ");
		
			userInput = Clavier.entrerString("");
		
			switch (userInput) {
				case "1":
					boundaryAfficherInfosPerso.menuInfoPerso();
					break;
				case "3":
					controlGestionDonnees.sauvegarderDonnees();
					System.exit(0);
					break;
				default :
					if(controlVerificationBoutique.verificationBoutiqueSelectionnee(userInput)) {
						boundaryBoutique.displayBoutique(userInput);
					}	
			}
		} while(!userInput.equals("1") && !userInput.equals("2"));
	}
	
	public void displayBoutiquePersonnelle() {

		String userInput;
		do {
			
			System.out.println("\n --- Boutique Personnelle --- \n");
			
			if(controlAfficherBoutique.boutiquePersoVide()) {
				System.out.println("Vous n'avez acheté aucune boutique pour l'instant\n");
			}
			else {
				System.out.println("Voici les boutiques dont vous avez accès :\n");
				System.out.println(controlAfficherBoutique.getPresentationsBoutiquesPossedees());
			}
			
			
			System.out.println("\n1 : retour aux Infos Perso");
			System.out.println("2 : Acceder a la boutique ");
			System.out.println("3 : Sauvegarder et quitter\n");
			System.out.println("Selectionnez une boutique (nom de la boutique) ou une option : ");
		
			userInput = Clavier.entrerString("");
		
			switch (userInput) {
				case "2":
					displayBoutique();
					break;
				case "3":
					System.exit(0);
					break;
				default :
					if(controlVerificationBoutique.verificationBoutiqueUserSelectionnee(userInput)) {
						boundaryBoutique.displayBoutiquePersonnelle(userInput);
					}	
			}
		} while(!userInput.equals("1") && !userInput.equals("2"));
	}
}
