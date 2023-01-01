package main;

public class Main {

	private static final int NOMBRE_MAX_TROPHEES = 300;
	private final String CHEMIN_DES_FICHIERS = "ocaml/donnees/";
	private static final String FICHIER_DONNEES = "donnees.csv";
	private static final String FICHIER_PLANS = "listeDesPlanPrevu.csv";
	private static final String FICHIER_NOUVELLES_DONNEES = "nouvellesDonnees.csv";
	private static final String FICHIER_NOUVEAU_PLANS = "nouvelleListeDesPlan.csv";
	private static final String FICHIER_TROPHEES = "trophees.csv";
	private static Tache[] trophees = new Tache[NOMBRE_MAX_TROPHEES];
	private static Calendrier calendrier = Calendrier.getInstance();
	private final int NB_DONNEES = 6;
	private static Main instance = new Main();
	private static int stars = 0;
	private static int nbTrophees = 0;
	// private Pet[] pets = new Pet[50];
	// private int tempsDeTravailTotal = 0;

	public Calendrier getCalendrier() {
		return calendrier;
	}

	public static void setCalendrier(Calendrier calendrier) {

		Main.calendrier = calendrier;

	}

	public static void ajouterUnTrophee(Tache tacheTermine) {
		if (nbTrophees == NOMBRE_MAX_TROPHEES) {
			Display.display("nombre max de trophees atteint");
		}
		else {
			trophees[nbTrophees] = tacheTermine;
			nbTrophees++;
		}
		
	}

	public void afficherTrophees() {
		if (nbTrophees > 0) {
			Display.display("La liste des trophees est : \n");
			for (int i = 0; i < nbTrophees; i++) {
				trophees[i].afficherPlan(i + 1);
			}
		} else {
			Display.display(" Vous n'avez aucun trophee pour le moment\n ");
		}
	}
	
	private void messageNombreTotal(String nom, int valeur) {
		Display.display("  - Nombre total d" + nom + " : " + valeur);
	}

	public void afficherInfosPerso() {
		Display.display("Vous avez recolte : " + stars + " Stars ! Félicitaton !\n");
		Display.display(" Statistiques : ");
		messageNombreTotal("e plans", calendrier.getNbPlans() );
		messageNombreTotal("e taches a faire ( taches + sous-taches )", (calendrier.getNbTacheAFaire() + calendrier.getNbTotalSousTachesAFaire()) );
		messageNombreTotal("e taches ( sans les sous-taches )", calendrier.getNbTacheAFaire() );
		messageNombreTotal("'evenements prevu", calendrier.getNbEvenement() );
		messageNombreTotal("e trophees", nbTrophees );
	

	}

	private static void messageBienRecupere(String nom) {
		Display.display(nom + " a bien été recuperé !");
	}

	private static void reinitialiserTrophees() {
		nbTrophees = 0;
		trophees = new Tache[NOMBRE_MAX_TROPHEES];
	}

	private static void reinitialiserDonnees() {
		stars = 0;
		calendrier = new Calendrier();
	}

	public void reinitialisationDesDonnees(String nomDuFichier) {
		Display.display("Les informations du fichier " + nomDuFichier + " n'ont pas pu etre récupérés. "
				+ "\nLancement du programme avec un fichier " + nomDuFichier + " remis a zero..");
		if (nomDuFichier.equals(FICHIER_TROPHEES)) {
			reinitialiserTrophees();
		} else {
			reinitialiserDonnees();
		}
		Display.displayMainMenu(null);
	}

	public static void mettreAJourLesDonnees() {
		CommandeShell cs = CommandeShell.getInstance();

		Display.display("Remise a niveau des fichiers..\n");
		cs.executerLesFichiersOcaml();

	}

	public static void recupererToutesLesDonnees(String fichierDonnees, String fichierPlan) {

		ReceveurDeFichier rf = ReceveurDeFichier.getInstance();

		Display.display("Récuperation des données...\n");

		stars = rf.recupererUneDonnee(fichierDonnees, "nbStars");
		messageBienRecupere("nbStars");
		nbTrophees = rf.recupererUneDonnee(fichierDonnees, "nbTrophees");
		messageBienRecupere("nbTrophees");
		calendrier.setNbTachesAFaires(rf.recupererUneDonnee(fichierDonnees, "nbTachesAFaire"));
		messageBienRecupere("nbTacheAFaire");
		calendrier.setNbPlans(rf.recupererUneDonnee(fichierDonnees, "nbPlans"));
		messageBienRecupere("nbPlans");
		calendrier.setNbEvenements(rf.recupererUneDonnee(fichierDonnees, "nbEvenements"));
		messageBienRecupere("nbEvenements");
		calendrier.setNbTotalSousTachesAFaire(rf.recupererUneDonnee(fichierDonnees, "nbTotalSousTachesAFaire"));
		messageBienRecupere("nbTotalSousTachesAFaire");
		trophees = rf.recupererFichierEnTableauTache(FICHIER_TROPHEES);
		messageBienRecupere("Le tableau de trophees");
		calendrier.setPlans(rf.recupererFichierEnTableauPlan(fichierPlan));
		messageBienRecupere("Le tableau de plan");

	}

	public static void ajouterDesStars(int nbStarsAAjouter) {
		Main.stars = stars + nbStarsAAjouter;
	}

	public int getNbTrophees() {
		return nbTrophees;
	}

	public int getStars() {
		return stars;
	}

	public Tache[] getTrophees() {
		return trophees;
	}

	public static void setTrophees(Tache[] trophees) {
		Main.trophees = trophees;
	}

	public static Main getInstance() {
		return instance;
	}

	public static String getFichierDonnees() {
		return FICHIER_DONNEES;
	}

	public static String getFichierPlans() {
		return FICHIER_PLANS;
	}

	public static String getFichierNouvellesDonnees() {
		return FICHIER_NOUVELLES_DONNEES;
	}

	public static String getFichierNouveauPlans() {
		return FICHIER_NOUVEAU_PLANS;
	}

	public static String getFichierTrophees() {
		return FICHIER_TROPHEES;
	}

	public String getCheminDeFichier() {
		return CHEMIN_DES_FICHIERS;
	}

	public int getNbDonnees() {
		return NB_DONNEES;
	}

	public static void main(String[] args) {
		mettreAJourLesDonnees();
		recupererToutesLesDonnees(FICHIER_NOUVELLES_DONNEES, FICHIER_NOUVEAU_PLANS);
		Display.displayMainMenu(null);
	}

}

