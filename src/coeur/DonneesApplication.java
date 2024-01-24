package coeur;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import utils.Utils;

public class DonneesApplication {

	public DonneesApplication() {
	}
	
	public static final int STARS_APRES_ACCOMPLISSEMENT_TACHE = 15;
	public static final int STARS_APRES_ACCOMPLISSEMENT_SOUS_TACHE = 5;
	public static final int STARS_APRES_SOUS_TACHE = 2;
	
	public static final String CHEMIN_DES_FICHIERS = "ocaml/csv/";
	public static final String FICHIER_PLANS = "calendrier.csv";
	public static final String FICHIER_NOUVEAU_PLANS = "calendrierMisAJour.csv";
	public static final String FICHIER_MODE = "mode.csv";
	public static final String FICHIER_AGARDER = "aGarder.csv";
	public static final String FICHIER_TACHES_RETIREES = "plansSupprimes.csv";
	
	public static final String FICHIER_USERDATA_SER = "serialisation/userData.ser" ;
	public static final String FICHIER_BOUTIQUE_SER = "serialisation/boutique.ser" ;


	public static final String LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public static final SimpleDateFormat DF = new SimpleDateFormat("EEEE d MMMM yyyy", Locale.FRANCE);
	public static final GregorianCalendar JOUR_ACTUEL = new GregorianCalendar();

	static {
		Utils.setUpDate(JOUR_ACTUEL);
	}
}
