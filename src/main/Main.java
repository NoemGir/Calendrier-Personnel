package main;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Main {

	private static Main instance = new Main();
	private int stars = 0;
	// private Pet[] pets = new Pet[50];
	// private int tempsDeTravailTotal = 0;
	private Calendrier calendrier = new Calendrier();
	private Tache[] trophees = new Tache[100];
	private int nbTrophees = 0;

	public Calendrier getCalendrier() {
		return calendrier;
	}

	public void setCalendrier(Calendrier calendrier) {

		this.calendrier = calendrier;

	}

	public void ajouterUnTrophee(Tache tacheTermine) {
		assert (tacheTermine.isAccomplie());
		trophees[nbTrophees] = tacheTermine;
		nbTrophees++;
	}

	public void afficherTrophees() {
		if (nbTrophees > 0) {
			Display.display("La liste des trophees est : \n");
			for (int i = 0; i < nbTrophees; i++) {
				trophees[i].afficherPlan(i + 1);
			}
		} else {
			Display.display(" Vous n'avez aucun trophee pour le moment ");
		}
	}

	public void afficherInfosPerso() {
		Display.display("Vous avez recolte : " + stars + " Stars ! Félicitaton !\n");
		Display.display(" Statistiques : ");
		Display.display(
				"  - Nombre total de taches a faire : " + calendrier.getNbTacheAFaire() + calendrier.getNbSousTache());
		Display.display("  - Nombre total d'evenement prevu : " + calendrier.getNbEvenement());
		Display.display("  - Nombre total de tache realisees : " + nbTrophees + "\n");
	}

	public void ajouterDesStars(int nbStarsAAjouter) {
		this.stars = stars + nbStarsAAjouter;
	}

	public int getNbTrophees() {
		return nbTrophees;
	}

	public int getStars() {
		return stars;
	}

	public static Main getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		LocalDate localDate = LocalDate.now();
		GregorianCalendar jourActuel = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Display.displayMainMenu(jourActuel);
	}

}


