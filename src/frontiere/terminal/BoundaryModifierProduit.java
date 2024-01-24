package frontiere.terminal;

import controleur.ControlActiverProduit;
import controleur.ControlAfficherBoutique;
import controleur.ControlGestionDonnees;
import controleur.ControlModifierProduit;

public class BoundaryModifierProduit {
	
	private ControlAfficherBoutique controlAfficherBoutique;
	private ControlModifierProduit controlModifierProduit;
	private ControlActiverProduit controlActiverProduit;
	private ControlGestionDonnees controlGestionDonnees;


	public BoundaryModifierProduit(ControlAfficherBoutique controlAfficherBoutique,
			ControlModifierProduit controlModifierProduit,
			ControlGestionDonnees controlGestionDonnees,
			ControlActiverProduit controlActiverProduit) {
		this.controlAfficherBoutique = controlAfficherBoutique;
		this.controlModifierProduit =  controlModifierProduit;
		this.controlActiverProduit = controlActiverProduit;
		this.controlGestionDonnees = controlGestionDonnees;
	}

	public void displayProduit(String nomBoutique, String nomProduit) {
		String userInput;
		do {
			System.out.println("\n --- "+ nomBoutique +" : " + nomProduit + " --- \n");
			
			System.out.println(controlAfficherBoutique.getPresentationProduit(nomBoutique, nomProduit));
			
			if(controlActiverProduit.produitActive(nomBoutique, nomProduit)) {
				System.out.println("\n1 : Désactiver le produit");
			}
			else {
				System.out.println("\n1 : Activer le produit");
			}
			System.out.println("2 : retour");
			System.out.println("3 : Sauvegarder et quitter\n");
			System.out.println("Effectuez une modification ou selectionnez une option : ");
		
			userInput = Clavier.entrerString("");
		
			switch (userInput) {
				case "1":
					controlActiverProduit.changerActivation(nomBoutique, nomProduit);
					break;
				case "3":
					controlGestionDonnees.sauvegarderDonnees();
					System.exit(0);
					break;
				default :
					if(controlModifierProduit.modifierProduit(nomBoutique, nomProduit, userInput)) {
						System.out.println("La modification a bien été appliquée!!");
					}	
					else {
						System.out.println("La modification n'a pas pu être appliquée...");
					}
			}
		} while(!userInput.equals("2"));
	}
}
