package frontiere.terminal;

import controleur.ControlAcheterProduit;
import controleur.ControlAfficherBoutique;
import controleur.ControlAfficherInfosPerso;
import controleur.ControlGestionDonnees;
import controleur.ControlVerificationBoutique;

public class BoundaryBoutique {
	
	private ControlGestionDonnees controlGestionDonnees;
	private ControlAfficherBoutique controlAfficherBoutique;
	private ControlAcheterProduit controlAcheterProduit;
	private ControlVerificationBoutique controlVerificationBoutique;
	private ControlAfficherInfosPerso controlAfficherInfosPerso;
	private BoundaryAfficherInfosPerso boundaryAfficherInfosPerso;
	private BoundaryBoutiquePrincipale boundaryBoutiquePrincipale;
	private BoundaryModifierProduit boundaryModifierProduit;

	public void setBoundaryBoutiquePrincipale(BoundaryBoutiquePrincipale boundaryBoutiquePrincipale) {
		this.boundaryBoutiquePrincipale = boundaryBoutiquePrincipale;
	}




	public BoundaryBoutique(ControlAfficherBoutique controlAfficherBoutique,ControlGestionDonnees controlGestionDonnees,
			ControlAcheterProduit controlAcheterProduit, ControlVerificationBoutique controlVerificationBoutique,
			ControlAfficherInfosPerso controlAfficherInfosPerso,
			BoundaryAfficherInfosPerso boundaryAfficherInfosPerso,
			BoundaryModifierProduit boundaryModifierProduit) {
		this.controlAfficherBoutique = controlAfficherBoutique;
		this.controlAcheterProduit = controlAcheterProduit;
		this.controlVerificationBoutique = controlVerificationBoutique;
		this.controlGestionDonnees = controlGestionDonnees;
		this.controlAfficherInfosPerso = controlAfficherInfosPerso;
		this.boundaryAfficherInfosPerso = boundaryAfficherInfosPerso;
		this.boundaryModifierProduit = boundaryModifierProduit;
	}




	public void displayBoutique(String nomBoutique) {


		String userInput;
		do {
			
			System.out.println("\n --- " +nomBoutique + " --- \n");
			System.out.println("Vous possedez "+ controlAfficherInfosPerso.getNbStars() + " stars");
			
			if(controlAfficherBoutique.produitsRestants(nomBoutique)) {
				System.out.println("Voici les produits proposés :\n");
				System.out.println(controlAfficherBoutique.getPresentationProduitsBoutique(nomBoutique));
			}
			else {
				System.out.println("Vous avez achetés tous les produits de cette boutiques ! ");
			}
			
			System.out.println("\n1 : Infos Perso");
			System.out.println("2 : Retour à la boutique principale ");
			System.out.println("3 : Sauvegarder et quitter\n");
	
			System.out.println("Achetez un produit (nom du produit) ou selectionnez une option : ");
			
			userInput = Clavier.entrerString("");
	
			switch (userInput) {
				case "1" :
					boundaryAfficherInfosPerso.menuInfoPerso();
					break;
				case "3" :
					System.exit(0);
					break;
				default:
					if(controlVerificationBoutique.verificationProduitSelectionne(nomBoutique, userInput)) {
						if (!controlAcheterProduit.acheterProduit(nomBoutique, userInput)) {
							System.out.println("Vous n'avez pas suffisament d'argent pour acheter ce produit ! ");
						}
					}
					else {
						System.out.println("Input incorrect, veuillez reeseyer");
					}
			}
		}while(!userInput.equals("2") && !userInput.equals("1"));
	}




	public void displayBoutiquePersonnelle(String nomBoutique) {
		String userInput;
		do {
			
			System.out.println("\n --- " +nomBoutique + " Personnelle --- \n");
			
			if(controlAfficherBoutique.userBoutiqueVide(nomBoutique)) {
				System.out.println("Vous n'avez pas encore acheté de produit dans cette boutique !");
			}
			else {
				System.out.println("Voici les produits proposés :\n");
				System.out.println(controlAfficherBoutique.getPresentationProduitsPossedes(nomBoutique));
			}
			
			System.out.println("\n1 : Retour à la page des boutiques possedées");
			System.out.println("2 : acceder a la boutique ");
			System.out.println("3 : Sauvegarder et quitter\n");
	
			System.out.println("Selectionnez un produit (nom produit) ou une option : ");
			
			userInput = Clavier.entrerString("");
	
			switch (userInput) {
				case "1" :
					boundaryAfficherInfosPerso.menuInfoPerso();
					break;
				case "2" : 
					boundaryBoutiquePrincipale.displayBoutique();
				case "3" :
					controlGestionDonnees.sauvegarderDonnees();
					System.exit(0);
					break;
				default:
					if(controlVerificationBoutique.verificationProduitUserSelectionne(nomBoutique, userInput)) {
						boundaryModifierProduit.displayProduit(nomBoutique, userInput);
					}
					else {
						System.out.println("Input incorrect, veuillez reeseyer");
					}
			}
		}while(!userInput.equals("1") && !userInput.equals("2"));		
	}

}
