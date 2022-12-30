package main;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EnvoyeurDeFichier {

	private static EnvoyeurDeFichier instance = new EnvoyeurDeFichier();
	Main main = Main.getInstance();

	public void toutSauvegarder() {

		sauvegarderDonnees(main);
		Calendrier calendrier = main.getCalendrier();
		sauvegarderTableauPlan(Main.getFichierPlans(), calendrier.getPlan(), calendrier.getNbPlans());
		sauvegarderTableauPlan(Main.getFichierTrophees(), main.getTrophees(), main.getNbTrophees());
	}

	public void sauvegarderDonnees(Main main) {

		String[][] donnesFormatCSV = mettreDonneesFormatCSV();
		modifierFichierCSV(Main.getFichierDonnees(), donnesFormatCSV, main.getNbDonnees());
	}

	public void sauvegarderTableauPlan(String nomDuFichier, Plan[] tableauASauvegarder, int nbElements) {
		String[][] tableauFormatCSV = mettreTableauPlanFormatCSV(tableauASauvegarder, nbElements);
		modifierFichierCSV(nomDuFichier, tableauFormatCSV, nbElements);
	}

	public String[][] mettreTableauPlanFormatCSV(Plan[] tableauPlan, int nbPlan) {

		String[][] fichier = new String[100][16];

		for (int i = 0; i < nbPlan; i++) {
			GregorianCalendar date = tableauPlan[i].getDate();
			Plan plan = tableauPlan[i];
			fichier[i][0] = Integer.toString(date.get(Calendar.YEAR));
			fichier[i][1] = Integer.toString(date.get(Calendar.MONTH));
			fichier[i][2] = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
			fichier[i][3] = plan.getNom();
			fichier[i][4] = plan.getInfoSup();
			if (plan instanceof Tache tache) {
				fichier[i][5] = String.valueOf(tache.isAccomplie());
				fichier[i][6] = Integer.toString(tache.getNbSousTache());
				int nbSousTache = tache.getNbSousTache();
				if (nbSousTache > 0) {
					for (int j = 0; j < nbSousTache; j++) {
						SousTache sousTache = tache.getSousTaches()[j];
						fichier[i][7 + j] = sousTache.getNom() + "::" + sousTache.getInfoSup() + "::"
								+ sousTache.isAccomplie();
					}
				}
			}
		}
		return fichier;
	}

	public String[][] mettreDonneesFormatCSV() {

		Calendrier calendrier = main.getCalendrier();
		int nbStars = main.getStars();
		int nbTrophees = main.getNbTrophees();
		int nbTachesAFaire = calendrier.getNbTacheAFaire();
		int nbPlans = calendrier.getNbPlans();
		int nbEvenements = calendrier.getNbEvenement();
		int nbTotalSousTachesAFaire = calendrier.getNbTotalSousTachesAFaire();
		String[][] fichier = new String[main.getNbDonnees()][2];

		fichier[0][0] = "nbStars";
		fichier[0][1] = Integer.toString(nbStars);
		fichier[1][0] = "nbTrophees";
		fichier[1][1] = Integer.toString(nbTrophees);
		fichier[2][0] = "nbTachesAFaire";
		fichier[2][1] = Integer.toString(nbTachesAFaire);
		fichier[3][0] = "nbPlans";
		fichier[3][1] = Integer.toString(nbPlans);
		fichier[4][0] = "nbEvenements";
		fichier[4][1] = Integer.toString(nbEvenements);
		fichier[5][0] = "nbTotalSousTachesAFaire";
		fichier[5][1] = Integer.toString(nbTotalSousTachesAFaire);
		return fichier;

	}

	public void modifierFichierCSV(String nomDuFichier, String[][] modification, int nbLigne) {

		File fichierCSV = new File(main.getCheminDeFichier() + nomDuFichier);
		try {
			FileWriter fileWriter = new FileWriter(fichierCSV);
			int i = 0;
			while (i < nbLigne) {
				String[] donnee = modification[i];
				StringBuilder line = new StringBuilder();
				for (int j = 0; j < donnee.length; j++) {
					line.append(donnee[j]);
					if (j != donnee.length - 1) {
						line.append(',');
					}
				}
				line.append("\n");
				fileWriter.write(line.toString());
				i++;
			}

			fileWriter.close();
			Display.display("Fichier " + nomDuFichier + " modifié avec succès\n");
		} catch (Exception e) {
			Display.display("Erreur lors de la modification du fichier " + nomDuFichier);
		}

	}

	public String toStringFichier(String[][] fichier, int nbPlan) {
		String stringFichier = "{";
		int i;
		for (i = 0; i < nbPlan - 1; i++) {
			stringFichier = stringFichier + " {'" + fichier[i][0] + "', '" + fichier[i][1] + "','" + fichier[i][2]
					+ "', '" + fichier[i][3] + "'},\n";
		}
		stringFichier = stringFichier + "{ '" + fichier[i][0] + "', '" + fichier[i][1] + "', '" + fichier[i][2] + "','"
				+ fichier[i][3] + "'} }";
		return stringFichier;
	}

	public static EnvoyeurDeFichier getInstance() {
		return instance;
	}

}
