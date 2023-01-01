package main.exterieur;

import main.principal.*;
import main.plan.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class ReceveurDeFichier {

	private static ReceveurDeFichier instance = new ReceveurDeFichier();

	public int recupererUneDonnee(String nomDuFichier, String nomDonnee) {

		List<List<String>> fichierEnList = importationDuFichier(nomDuFichier);
		int i = 0;
		int nbDonnees = fichierEnList.size();

		while (i < nbDonnees && !fichierEnList.get(i).get(0).equals(nomDonnee)) {
			i++;
		}
		if (i >= nbDonnees) {
			Display.display("Le nom de la donnee est incorrect");
			return -1;
		} else {
			return Integer.parseInt(fichierEnList.get(i).get(1));
		}
	}

	public Tache[] recupererFichierEnTableauTache(String nomDuFichier) {
		List<List<String>> fichierEnList = importationDuFichier(nomDuFichier);
		Tache[] tableauTache = new Tache[100];
		tableauTache = (Tache[]) transformerListeEnTableauPlan(fichierEnList, tableauTache);
		return tableauTache;
	}

	public Plan[] recupererFichierEnTableauPlan(String nomDuFichier) {
		List<List<String>> fichierEnList = importationDuFichier(nomDuFichier);
		Plan[] tableauPlan = new Plan[100];
		return transformerListeEnTableauPlan(fichierEnList, tableauPlan);

	}

	private List<List<String>> importationDuFichier(String nomDuFichier) {

		Main main = Main.getInstance();
		File fichierCSV = new File(main.getCheminDeFichier() + nomDuFichier);
		List<List<String>> liste = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV))) {
			String ligne;
			while ((ligne = br.readLine()) != null) {
				String[] values = ligne.split(",");
				liste.add(Arrays.asList(values));
			}
			return liste;
		} catch (Exception e) {
			main.reinitialisationDesDonnees(nomDuFichier);
			return Collections.emptyList();
		}
	}

	private Plan[] transformerListeEnTableauPlan(List<List<String>> listesImbriquees, Plan[] tableauPlan) {

		int i = 0;

		while (i < listesImbriquees.size()) {
			List<String> liste = listesImbriquees.get(i);
			int annee = Integer.parseInt(liste.get(0));
			int mois = Integer.parseInt(liste.get(1));
			int jour = Integer.parseInt(liste.get(2));
			GregorianCalendar date = new GregorianCalendar(annee, mois, jour);
			String nom = liste.get(3);
			String infoSup = liste.get(4);

			if (!liste.get(5).equals("null")) {

				Boolean accomplie = Boolean.parseBoolean(liste.get(5));
				int nbSousTache = Integer.parseInt(liste.get(6));
				SousTache[] tableauSousTache = new SousTache[10];

				if (nbSousTache > 0) {
					tableauSousTache = recupererTableauSousTache(date, liste);
				}
				Tache tache = new Tache(date, nom, infoSup, nbSousTache, tableauSousTache, accomplie);
				tableauPlan[i] = tache;
			} else {
				Evenement evenement = new Evenement(date, nom, infoSup);
				tableauPlan[i] = evenement;
			}
			i++;
		}
		return tableauPlan;
	}

	private SousTache[] recupererTableauSousTache(GregorianCalendar date, List<String> liste) {

		SousTache[] tableauSousTache = new SousTache[10];
		int nbSousTache = Integer.parseInt(liste.get(6));

		for (int i = 0; i < nbSousTache; i++) {
			String[] values = liste.get(i + 7).split("::");
			String nom = values[0];
			String infoSup = values[1];
			Boolean accomplie = Boolean.parseBoolean(values[2]);
			SousTache sousTache = new SousTache(date, nom, infoSup, accomplie);
			tableauSousTache[i] = sousTache;
		}
		return tableauSousTache;

	}

	public static ReceveurDeFichier getInstance() {
		return instance;
	}
}
