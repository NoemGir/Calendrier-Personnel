package frontiere.terminal;

import java.util.Scanner;

import controleur.ControlCreerPlan;

public class BoundaryCreerPlan {
	
	private ControlCreerPlan controlCreerPlan;
	private BoundaryDemanderJour boundaryDemanderJour;
	private Scanner scan = new Scanner(System.in);

	public BoundaryCreerPlan(ControlCreerPlan controlCreerPlan, BoundaryDemanderJour boundaryDemanderJour) {
		this.controlCreerPlan = controlCreerPlan;
		this.boundaryDemanderJour = boundaryDemanderJour;
	}
	
	public void creerPlan(int[] journee){
		String userInput;
		do {
			System.out.println("Vous voulez creer :"); 
			System.out.println("1 - une Tache");
			System.out.println("2 - un Evenement");
			System.out.println("3 - retour");
			
			userInput = scan.next(); 
				
			if(!userInput.equals("3")) {					
				String nom = Clavier.entrerString("Donnez le nom du plan : \n");
					
				if ( journee == null) { //  || journee.length != 0
					journee = boundaryDemanderJour.demanderUnJour();
				}
					
				switch (userInput) {
				case "1":
					controlCreerPlan.ajouterTache(journee, nom);
					break;

				case "2":
					controlCreerPlan.ajouterEvenement(journee, nom);
					break;

				default:
					System.out.println("Vous devez choisir un chiffre entre 1 et 3 !");
					break;
				}
			}
		}
		while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3"));
	}
}
