package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Display {

	private static final String MESSAGE_CHOISIR_OPTION = "Selectionner  une option : \n";
	private static Display instance = new Display();
	private static Controleur controleur = Controleur.getInstance();
	private static LocalDate localDate = LocalDate.now();
	
	private Display() {}
	

	public static void displayMainMenu(GregorianCalendar calendrier) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy");
		GregorianCalendar calAffichage = (GregorianCalendar) calendrier.clone();
		GregorianCalendar jourActuel = GregorianCalendar.from(getLocalDate().atStartOfDay(ZoneId.systemDefault()));

		display(" --- Menu principal : --- \n");

		for (int i = 0; i < 7; i++) {
			display(i + " : " + dateFormat.format(calAffichage.getTime()));
			calAffichage.add(Calendar.DAY_OF_MONTH, 1);
		}
		display("\n7 : Semaine precedente");
		display("8 : Semaine suivante");
		display("9 : Autre jour");
		display("10 : Voir toutes les plans enregistrés");
		display("11 : Boutique");
		display("12 : Infos Perso");
		display("13 : Quitter\n");

		int userInput = Integer.parseInt(inputOutput("Selectionner un jour ou une action : "));

		switch (userInput) {
		case 0, 1, 2, 3, 4, 5, 6:
			calendrier.add(Calendar.DAY_OF_MONTH, userInput);
			displayMenuUnJour(calendrier);
			break;
		case 7:
			calendrier.add(Calendar.DATE, -7);
			if (calendrier.getTime().before(jourActuel.getTime())) {
				displayMainMenu(jourActuel);
			} else {
				displayMainMenu(calendrier);
			}
			break;
		case 8:
			calendrier.add(Calendar.DATE, 7);
			displayMainMenu(calendrier);
			break;
		case 9:
			displayMenuUnJour(demanderUnJour());
			break;
		case 10:
			controleur.afficheTousLesPlans();
			displayMainMenu(calendrier);
			break;
		case 11:
			display("La boutique n'est pas encore accessible, reeseyer plus tard");
			displayMainMenu(calendrier);
			break;
		case 12:
			displayMenuInfoPerso();
			break;
		case 13:
			System.exit(0);
			break;
		default:
			controleur.messageErreur();
			displayMainMenu(calendrier);
		}
	}

	public static void displayMenuUnJour(GregorianCalendar jourDuCalendrier) {

		GregorianCalendar jourActuel = GregorianCalendar.from(getLocalDate().atStartOfDay(ZoneId.systemDefault()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy");
		Calendrier calendrierDuMain = controleur.getCalendrierMain();

		display(" --- Le " + dateFormat.format(jourDuCalendrier.getTime()) + " : --- \n");

		calendrierDuMain.afficherPlanDeUnJour((GregorianCalendar) jourDuCalendrier.clone());

		display("1 : Jour précédent");
		display("2 : Jour suivant");
		display("3 : Changer de jour");
		display("4 : creer une tache");
		display("5 : creer un évènement");
		display("6 : Voir toutes les plans enregistrés");
		display("7 : Retour au menu principal");
		display("8 : Quitter\n");

		String userInput = inputOutput("Selectionner un plan (plan numéroDuPlan) ou une option : \n");

		switch (userInput) {
		case "1":
			jourDuCalendrier.add(Calendar.DAY_OF_MONTH, -1);
			if (jourDuCalendrier.getTime().before(jourActuel.getTime())) {
				displayMenuUnJour(jourActuel);
			} else {
				displayMenuUnJour(jourDuCalendrier);
			}

			break;
		case "2":
			jourDuCalendrier.add(Calendar.DAY_OF_MONTH, +1);
			displayMenuUnJour(jourDuCalendrier);
			break;
		case "3":
			displayMenuUnJour(demanderUnJour());
			break;
		case "4":
			controleur.creerTache(jourDuCalendrier, demanderUnNom());
			displayMenuUnJour(jourDuCalendrier);
			break;
		case "5":
			controleur.creerEvenement(jourDuCalendrier, demanderUnNom());
			displayMenuUnJour(jourDuCalendrier);
			break;
		case "6":
			controleur.afficheTousLesPlans();
			displayMenuUnJour(jourDuCalendrier);
			break;
		case "7":
			displayMainMenu(jourDuCalendrier);
			break;
		case "9":
			System.exit(0);
			break;
		default:
			controleur.verifPlanSelectionne(userInput, jourDuCalendrier);
		}
	}

	public static void displayMenuModifierPlan(Plan planAModifier, int numDuPlan) {

		planAModifier.afficherPlanComplet();

		display("1 : Changer le nom");
		display("2 : Changer le jour");
		display("3 : Changer les informations supplémentaires");

		if (planAModifier instanceof Tache tacheAModifier) {
			displayMenuModifierTache(tacheAModifier, numDuPlan);
		} else {
			Evenement evenementAModifier = (Evenement) planAModifier;
			displayMenuModifierEvenement(evenementAModifier, numDuPlan);
		}

	}

	public static void displayMenuModifierEvenement(Evenement evenementAModifier, int numDuPlan) {

		GregorianCalendar jour = (GregorianCalendar) evenementAModifier.getDate().clone();

		display("4 : Supprimer l'evenement");
		display("5 : Retour au jour l'evenement");
		display("6 : Retour au menu");
		display("7 : Quitter\n");

		String userInput = inputOutput(MESSAGE_CHOISIR_OPTION);

		switch (userInput) {
		case "1":
			controleur.changerNomDuPlan(evenementAModifier, demanderUnNom());
			displayMenuModifierPlan(evenementAModifier, numDuPlan);
			break;
		case "2":
			numDuPlan = controleur.changerDateDuPlanReturnNouveauNum(evenementAModifier, numDuPlan,
					(GregorianCalendar) demanderUnJour().clone());
			displayMenuModifierPlan(evenementAModifier, numDuPlan);
			break;
		case "3":
			controleur.changerInfoSupDuPlan(evenementAModifier, demanderInfoSupplementaire());
			displayMenuModifierPlan(evenementAModifier, numDuPlan);
			break;
		case "4":
			controleur.supprimerPlan(numDuPlan);
			displayMenuUnJour(jour);
			break;
		case "5":
			displayMenuUnJour(jour);
			break;
		case "6":
			displayMainMenu(jour);
			break;
		case "7":
			System.exit(0);
			break;
		default:
			controleur.messageErreur();
			displayMenuModifierPlan(evenementAModifier, numDuPlan);
		}
	}

	public static void displayMenuModifierTache(Tache tacheAModifier, int numDuPlan) {

		GregorianCalendar jour = (GregorianCalendar) tacheAModifier.getDate().clone();

		display("4 : Terminer la tache");
		display("5 : Creer une sous tache");
		display("6 : Supprimer la tache");
		display("7 : Retour au jour de la tache");
		display("8 : Retour au menu");
		display("9 : Quitter\n");

		String userInput = inputOutput("Selectionner  une option  ou une sous-tache (sousTache numSousTache): \n");

		switch (userInput) {
		case "1":
			controleur.changerNomDuPlan(tacheAModifier, demanderUnNom());
			displayMenuModifierPlan(tacheAModifier, numDuPlan);
			break;
		case "2":
			numDuPlan = controleur.changerDateDuPlanReturnNouveauNum(tacheAModifier, numDuPlan,
					(GregorianCalendar) demanderUnJour().clone());
			display("numDuPlan = " + numDuPlan);
			displayMenuModifierPlan(tacheAModifier, numDuPlan);
			break;
		case "3":
			controleur.changerInfoSupDuPlan(tacheAModifier, demanderInfoSupplementaire());
			displayMenuModifierPlan(tacheAModifier, numDuPlan);
			break;
		case "4":
			display("Félicitation ! Vous venez de terminer une nouvelle tache ! *musique* elle sera ajoutée a vos trophees\n");
			controleur.terminerUneTache(tacheAModifier, numDuPlan);
			displayMenuUnJour(jour);
			break;
		case "5":
			controleur.creerSousTache(tacheAModifier, demanderUnNom());
			displayMenuModifierPlan(tacheAModifier, numDuPlan);
			break;
		case "6":
			controleur.supprimerPlan(numDuPlan);
			displayMenuUnJour(jour);
			break;
		case "7":
			displayMenuUnJour(jour);
			break;
		case "8":
			displayMainMenu(jour);
			break;
		case "9":
			System.exit(0);
			break;
		default:
			if (controleur.sousTacheBienSelectionnee(tacheAModifier, userInput)) {
				int numSousTache = Integer.parseInt(userInput.split(" ")[1]) - 1;
				Tache sousTacheSelectionnee = tacheAModifier.getSousTaches()[numSousTache];
				if (sousTacheSelectionnee.isAccomplie()) {
					displayInfoSousTacheFinie(tacheAModifier, numDuPlan, numSousTache);
				} else {
					displayMenuModifierSousTache(tacheAModifier, numDuPlan, numSousTache);
				}

			} else {
				controleur.messageErreur();
				displayMenuModifierPlan(tacheAModifier, numDuPlan);
			}
		}
	}

	public static void displayMenuModifierSousTache(Tache tacheModifiee, int numTache, int numSousTache) {

		GregorianCalendar jourActuel = GregorianCalendar.from(getLocalDate().atStartOfDay(ZoneId.systemDefault()));
		SousTache sousTacheAModifier = tacheModifiee.getSousTaches()[numSousTache];

		sousTacheAModifier.afficherPlanComplet();

		display("1 : Changer le nom");
		display("2 : Changer les informations supplémentaires");
		display("3 : Terminer la sous-tache");
		display("4 : Supprimer");
		display("5 : Retour a la tache");
		display("6 : Retour au Menu");
		display("7 : quitter");

		String userInput = inputOutput(MESSAGE_CHOISIR_OPTION);

		switch (userInput) {
		case "1":
			controleur.changerNomDuPlan(sousTacheAModifier, demanderUnNom());
			displayMenuModifierSousTache(tacheModifiee, numTache, numSousTache);
			break;
		case "2":
			controleur.changerInfoSupDuPlan(sousTacheAModifier, demanderInfoSupplementaire());
			break;
		case "3":
			controleur.terminerUneSousTache(sousTacheAModifier);
			displayInfoSousTacheFinie(tacheModifiee, numTache, numSousTache);
			break;
		case "4":
			controleur.supprimerUneSousTache(tacheModifiee, numSousTache);
			displayMenuModifierPlan(tacheModifiee, numTache);
			break;
		case "5":
			displayMenuModifierPlan(tacheModifiee, numTache);
			break;
		case "6":
			displayMainMenu(jourActuel);
			break;
		case "7":
			System.exit(0);
			break;
		default:
			controleur.messageErreur();
			displayMenuModifierSousTache(tacheModifiee, numTache, numSousTache);
		}

	}

	public static void displayInfoSousTacheFinie(Tache tache, int numTache, int numSousTache) {

		GregorianCalendar jourActuel = GregorianCalendar.from(getLocalDate().atStartOfDay(ZoneId.systemDefault()));
		SousTache sousTacheTerminee = tache.getSousTaches()[numSousTache];
		
		sousTacheTerminee.afficherPlanComplet();

		display("La sous tache est terminée, impossible de la modifier\n");
		display("1 : Retour a la tache");
		display("2 : Retour au menu");
		display("3 : Quitter\n");

		String userInput = inputOutput(MESSAGE_CHOISIR_OPTION);

		switch (userInput) {

		case "1":
			displayMenuModifierPlan(tache, numTache);
			break;
		case "2":
			displayMainMenu(jourActuel);
			break;
		case "3":
			System.exit(0);
			break;
		default:
			controleur.messageErreur();
			displayInfoSousTacheFinie(tache, numTache, numSousTache);
		}

	}

	public static void displayMenuInfoPerso() {
		
		GregorianCalendar jourActuel = GregorianCalendar.from(getLocalDate().atStartOfDay(ZoneId.systemDefault()));
		Main main = controleur.getMain();
		
		display("--- information personnelles ---\n");
		

		display("Vous avez recolter : " + main.getStars() + " Stars ! Félicitaton !\n");
		display(" Statistiques : ");
		display("  - Nombre total de taches a faire : " + main.getCalendrier().getNbTacheAFaire());
		display("  - Nombre total d'evenement prevu : " + main.getCalendrier().getNbEvenement());
		display("  - Nombre total de tache realisees : " + main.getNbTrophees() + "\n");

		display("1 : Voir tout le plan");
		display("1 : Voir la liste des trophets");
		display("2 : Retour au menu\n");

		String userInput = inputOutput(MESSAGE_CHOISIR_OPTION);

		switch (userInput) {
		case "1":
			controleur.afficheTousLesPlans();
			displayMenuInfoPerso();
			break;
		case "2":
			main.afficherTrophees();
			displayMenuInfoPerso();
			break;
		case "3":
			displayMainMenu(jourActuel);
			break;
		default:
			controleur.messageErreur();
			displayMenuInfoPerso();
		}

	}

	public static String demanderUnNom() {
		return inputOutput("Donnez un nom au plan : ");
	}

	public static String demanderInfoSupplementaire() {
		return inputOutput("Donnez des informations supplémentaires");
	}

	public static GregorianCalendar demanderUnJour() {
		
		GregorianCalendar jourActuel = GregorianCalendar.from(getLocalDate().atStartOfDay(ZoneId.systemDefault()));
		GregorianCalendar dateDonnee = new GregorianCalendar();
		dateDonnee.set(Calendar.HOUR_OF_DAY, 0);
		dateDonnee.set(Calendar.MINUTE, 0);
		dateDonnee.set(Calendar.SECOND, 0);
		dateDonnee.set(Calendar.MILLISECOND, 0);
		int annee;
		int mois;
		int jour;

		do {
			annee = Integer.parseInt(inputOutput("Donnez une année ( -1 si retour au Menu) : "));
		} while (annee != -1 && annee < jourActuel.get(Calendar.YEAR));
		if (annee == -1) {
			displayMainMenu(jourActuel);
		}
		dateDonnee.set(Calendar.YEAR, annee);

		do {
			mois = Integer.parseInt(inputOutput("Donnez un mois ( -1 si retour au Menu) : "));
			dateDonnee.set(Calendar.MONTH, mois);
			dateDonnee.set(Calendar.DAY_OF_MONTH, jourActuel.get(Calendar.DAY_OF_MONTH));
		} while (mois != -1 && (0 > mois || mois > 11 || dateDonnee.before(jourActuel)));
		if (mois == -1) {
			displayMainMenu(jourActuel);
		}

		do {
			jour = Integer.parseInt(inputOutput("Donnez un jour ( -1 si retour au Menu) : "));

		} while (jour != -1 && Boolean.TRUE.equals(Controleur.jourIncorrect(jour, dateDonnee)));
		if (jour == -1) {
			displayMainMenu(jourActuel);
		}
		dateDonnee.set(Calendar.DAY_OF_MONTH, jour);
		return dateDonnee;
	}

	private static String inputOutput(String message) {
		
		GregorianCalendar jourActuel = GregorianCalendar.from(getLocalDate().atStartOfDay(ZoneId.systemDefault()));
		
		display(message);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String returnString = "";
		try {
			returnString = br.readLine();
		} catch (IOException e) {
			display("Erreur lors de la lecture\n");
			displayMainMenu(jourActuel);
		}
		return returnString;
	}

	public static Display getInstance() {
		return instance;
	}

	public static void display(String texte) {
		System.out.println(texte);
	}


	public static LocalDate getLocalDate() {
		return localDate;
	}

}
