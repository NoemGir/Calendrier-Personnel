package main;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Controleur {

	private static final int STARS_APRES_ACCOMPLISSEMENT_TACHE = 15;
	private static final int STARS_APRES_ACCOMPLISSEMENT_SOUS_TACHE = 5;
	private static final int STARS_APRES_SOUS_TACHE = 2;

	private static Controleur instance = new Controleur();
	private Main main = Main.getInstance();
	private Calendrier calendrierMain = getMain().getCalendrier();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy");
	

	public void creerTache(GregorianCalendar jour, String nom) {
		Tache nouvelleTache = new Tache((GregorianCalendar) jour.clone(), nom);
		ajouterPlanACalendrierMain(nouvelleTache, "La tache ");
	}

	public void creerEvenement(GregorianCalendar jour, String nom) {
		Evenement nouvelEvenement = new Evenement((GregorianCalendar) jour.clone(), nom);
		ajouterPlanACalendrierMain(nouvelEvenement, "L'evenement ");
	}

	public void ajouterPlanACalendrierMain(Plan nouveauPlan, String categorie) {
		calendrierMain.ajouterPlan(nouveauPlan);
		Display.display(categorie + " a bien été ajouté !\n");
	}

	public void changerNomDuPlan(Plan tacheAModifier, String nouveauNom) {
		tacheAModifier.setNom(nouveauNom);

	}

	public int changerDateDuPlanReturnNouveauNum(Plan planAModifier, int numDuPlan, GregorianCalendar nouvelleDate) {
		calendrierMain.retirerPlan(numDuPlan);
		planAModifier.setDate(nouvelleDate);
		return calendrierMain.ajouterPlan(planAModifier);
	}

	public void changerInfoSupDuPlan(Plan planAModifier, String infoSup) {
		planAModifier.setInfoSup(infoSup);
	}

	public void supprimerPlan(int numDuPlan) {
		calendrierMain.retirerPlan(numDuPlan);
	}

	public void terminerUneTache(Tache tacheTermine, int numDeLaTache) {
		tacheTermine.setAccomplie(true);
		calendrierMain.retirerPlan(numDeLaTache);
		getMain().ajouterUnTrophee(tacheTermine);
		main.ajouterDesStars(
				STARS_APRES_ACCOMPLISSEMENT_TACHE + STARS_APRES_SOUS_TACHE * tacheTermine.getNbSousTache());
		calendrierMain.retirerUneSousTache();
	}

	public void creerSousTache(Tache tacheAAjouterSousTache, String nomSousTache) {
		SousTache nouvelleSousTache = new SousTache(tacheAAjouterSousTache.getDate(), nomSousTache);
		tacheAAjouterSousTache.ajouterSousTache(nouvelleSousTache);
		calendrierMain.ajouterUneSousTache();
	}

	public void supprimerUneSousTache(Tache tacheModifiee, int numSousTache) {
		tacheModifiee.supprimerSousTache(numSousTache);
		calendrierMain.retirerUneSousTache();
	}

	public void terminerUneSousTache(Tache sousTacheATerminer) {
		sousTacheATerminer.setAccomplie(true);
		main.ajouterDesStars(STARS_APRES_ACCOMPLISSEMENT_SOUS_TACHE);
	}

	public void verifPlanSelectionne(String userInput, GregorianCalendar jourDuCalendrier) {

		if (userInput.length() > 5) {
			try {
				int numDuPlan = Integer.parseInt(userInput.split(" ")[1]) - 1;
				if (numDuPlan >= 0 || numDuPlan < calendrierMain.getNbPlan()) {
					Plan planAModifier = calendrierMain.getPlan()[numDuPlan];
					Display.displayMenuModifierPlan(planAModifier, numDuPlan);
				} else {
					erreurRetourPlanDuJour(jourDuCalendrier);
				}
			} catch (Exception e) {
				erreurRetourPlanDuJour(jourDuCalendrier);
			}
		} else {
			erreurRetourPlanDuJour(jourDuCalendrier);
		}
	}

	public boolean sousTacheBienSelectionnee(Tache tacheAModifier, String userInput) {
		if (userInput.length() > 10) {
			try {
				int numSousPlan = Integer.parseInt(userInput.split(" ")[1]) - 1;
				return (numSousPlan >= 0 || numSousPlan < tacheAModifier.getNbSousTache());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public void messageErreur() {
		Display.display("Mauvais input, veuillez recommencer...\n");
	}

	private void erreurRetourPlanDuJour(GregorianCalendar jourDuCalendrier) {
		messageErreur();
		Display.displayMenuUnJour(jourDuCalendrier);
	}

	public static Boolean jourIncorrect(int jourDonne, GregorianCalendar dateDonnee) {

		int annee = dateDonnee.get(Calendar.YEAR);
		int mois = dateDonnee.get(Calendar.MONTH);
		switch (mois) {
		case 0, 2, 4, 6, 7, 9, 11:
			return verifJour(dateDonnee, jourDonne, 31);
		case 1:
			if (dateDonnee.isLeapYear(annee)) {
				return verifJour(dateDonnee, jourDonne, 29);
			} else {
				return verifJour(dateDonnee, jourDonne, 28);
			}

		default:
			return verifJour(dateDonnee, jourDonne, 30);
		}

	}

	private static Boolean verifJour(GregorianCalendar dateDonnee, int jourDonne, int jourMax) {

		GregorianCalendar jourActuel = GregorianCalendar.from(Display.getLocalDate().atStartOfDay(ZoneId.systemDefault()));

		if (jourDonne > 0 && jourDonne <= jourMax) {
			dateDonnee.set(Calendar.DAY_OF_MONTH, jourDonne);
			return dateDonnee.getTime().before(jourActuel.getTime())
					&& !(dateFormat.format(jourActuel.getTime()).equals(dateFormat.format(dateDonnee.getTime())));
		} else {
			return true;
		}
	}

	public void afficheTousLesPlans() {
		calendrierMain.afficherTousLesPlans();
	}

	public Calendrier getCalendrierMain() {
		return calendrierMain;
	}

	public static Controleur getInstance() {
		return instance;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Main getMain() {
		return main;
	}

}