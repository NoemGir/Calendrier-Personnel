package frontiere.terminal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import coeur.DonneesApplication;
import controleur.ControlGestionDonnees;
import utils.Utils;

public class BoundaryApplication {

	private ControlGestionDonnees controlGestionDonnees;
	private BoundaryAfficherCalendrier boundaryAfficherCalendrier;
	private BoundaryDemanderJour boundaryDemanderJour;
	private BoundaryAfficherInfosPerso boundaryAfficherInfosPerso;
	private BoundaryAfficherUnJour boundaryAfficherUnJour;
	private BoundaryBoutiquePrincipale boundaryBoutiquePrincipale;

	
	public BoundaryApplication(BoundaryAfficherCalendrier boundaryAfficherCalendrier,ControlGestionDonnees controlGestionDonnees,
			BoundaryDemanderJour boundaryDemanderJour, BoundaryAfficherInfosPerso boundaryAfficherInfosPerso,
			BoundaryAfficherUnJour boundaryAfficherUnJour, BoundaryBoutiquePrincipale boundaryBoutiquePrincipale) {
		this.boundaryAfficherCalendrier = boundaryAfficherCalendrier;
		this.boundaryDemanderJour = boundaryDemanderJour;
		this.boundaryAfficherInfosPerso = boundaryAfficherInfosPerso;
		this.boundaryAfficherUnJour = boundaryAfficherUnJour;
		this.boundaryBoutiquePrincipale = boundaryBoutiquePrincipale;
		this.controlGestionDonnees = controlGestionDonnees;
	}

	public void displayMainMenu(GregorianCalendar jour) {

		GregorianCalendar jourActuel =(GregorianCalendar) DonneesApplication.JOUR_ACTUEL.clone();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy", Locale.FRANCE);
		int userInput;
		do {
			GregorianCalendar calAffichage = (GregorianCalendar) jour.clone();
			
		System.out.println("\n --- Menu principal : --- \n");

		for (int i = 0; i < 7; i++) {
			System.out.println(i + " : " + dateFormat.format(calAffichage.getTime()));
			calAffichage.add(Calendar.DAY_OF_MONTH, 1);
		}
		System.out.println("\n7  : Semaine precedente");
		System.out.println("8  : Semaine suivante");
		System.out.println("9  : Autre jour");
		System.out.println("10 : Voir toutes les plans enregistrés");
		System.out.println("11 : Boutique");
		System.out.println("12 : Infos Perso");
		System.out.println("13 : Help - Guide");
		System.out.println("14 : Sauvegarder et quitter\n");

		userInput = Clavier.entrerEntier("Selectionner un jour ou une action : ");

		switch (userInput) {
			case 0, 1, 2, 3, 4, 5, 6:
				jour.add(Calendar.DATE, userInput);
				boundaryAfficherUnJour.displayMenuUnJour(jour);
				break;
			case 7:
				jour.add(Calendar.DATE, -7);
				if (jour.getTime().before(jourActuel.getTime())) {
					System.out.println("Il n'est pas possible de voir les jours précédents le jour actuel \n");
					jour = (GregorianCalendar) jourActuel.clone();
				}
				break;
			case 8: 
				jour.add(Calendar.DATE, 7);
				break;
			case 9:
				int[] journee = boundaryDemanderJour.demanderUnJour();
				jour = Utils.createCalendarFromInt(journee);
				break;
			case 10:
				boundaryAfficherCalendrier.afficherTousLesPlans();
				break;
			case 11:
				 boundaryBoutiquePrincipale.displayBoutique();
				break;
			case 12:
				boundaryAfficherInfosPerso.menuInfoPerso();
				break;
			case 13: 
				displayGuide();
				break;
			case 14: 
				sauvegarderEtQuitter();
				break;
			default:
				System.out.println("Vous devez entrer un chiffre entre 1 et 14");
			}
		}while(userInput != 14);
	}

	private final void displayGuide() {
		String explication = """

				   --- Guide d'utilisation ---

				Ce logiciel est une esquisse de Calendrier.
				L"utilisateur peut se déplacer dans le calendrier et y voir les plans organisés, ainsi que rajouter / supprimer des plans.
				Les plans sont composés en deux catégories : les événements et les tâches.
				--Les événements ne servent qu'à marquer un événement qui va se dérouler cette journee là.
				--Les tâches sont des activités à faire, elles peuvent donc être accomplies, et possèdent des sous-taches.

				Pour utiliser les menus : taper sur le clavier le numéro placé devant l'option de votre choix et appuyez sur ENTRER.

				Pour modifier un plan : Deplacez vous sur le menu du jour du plan de votre choix et entrez : "plan *numeroDuPlan*".
				Le numéro du plan correspond au numéro placé juste après le nom de la catégorie du plan voulu ( Tâche ou Evénement ).

				Pour modifier une sous-tâche : Une foi sur le menu d'un plan, vous pouvez creer/modifier une sous-tâche."
				Pour la modifier, taper "soustâche *numeroDeLaSousTâche*". Comme pour les plans, le numéro de la sous tâche sera affiché après sa catégorie : Sous-Tache.

				Les informations personnelles contiennent les nombre de Stars, de plans prévus ( tâches et événements ) ainsi que le nombre de trophees.
				Elles sont misent sous forme de menu ou il est possible d'afficher le tableau de Trophees.

				Les Stars sont un type de monnaie spécifique au logiciel. Elles serviront à acheter des produits dans la boutique qui sera prochainement disponible.
				10 Stars sont obtenues à l'accomplissement d'une tâche, plus deux fois le nombre de sous-tâches que cette tâche possèdait ( terminées ou pas ).
				A l'accomplissement d'une sous-tâche, l'utilisateur gagne 5 Stars.

				N'OUBLIEZ PAS D'APPUYER SUR SAUVEGARDER ET QUITTER AVANT DE PARTIR, OU VOUS PERDREZ TOUTE DONNÉES RAJOUTÉES.
				
				""";
		System.out.println(explication);
	}
	
	public void sauvegarderEtQuitter() {
		controlGestionDonnees.sauvegarderDonnees();
		System.exit(0);
	}

}
