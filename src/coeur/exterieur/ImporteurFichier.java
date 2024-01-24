package coeur.exterieur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import coeur.DonneesApplication;
import coeur.calendrier.Calendrier;
import coeur.calendrier.Horloge;
import coeur.calendrier.plan.Evenement;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;
import utils.Utils;

public class ImporteurFichier {
		
	public ImporteurFichier() {
	}

	public boolean recupererCalendrier(Calendrier<Plan> calendrier) {
		String nomDuFichier = DonneesApplication.FICHIER_NOUVEAU_PLANS;
		List<List<String>> fichierEnList = importationDuFichier(nomDuFichier);
		if(fichierEnList == null) {
			return false;
		}
		importerDansCalendrier(fichierEnList, calendrier);
		return true;
	}
	
	public List<Tache> recupererTachesRetirees() {
		String nomDuFichier = DonneesApplication.FICHIER_TACHES_RETIREES;
		List<List<String>> fichierEnList = importationDuFichier(nomDuFichier);
		if(fichierEnList == null) {
			return new ArrayList<>();
		}
		return tachesRetirees(fichierEnList);
	}

	private List<List<String>> importationDuFichier(String nomDuFichier) {
		File fichierCSV = new File(DonneesApplication.CHEMIN_DES_FICHIERS + nomDuFichier);
		List<List<String>> liste = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV))) {
			String ligne;
			while ((ligne = br.readLine()) != null){
				String[] values = ligne.split(",");
				liste.add(Arrays.asList(values));
			}
			return liste;
		} catch (Exception e) {
			return null;
		}
	}
	
	private void importerDansCalendrier(List<List<String>> listesImbriquees, Calendrier<Plan> calendrier) {
		calendrier.clear();
		Iterator<List<String>> it = listesImbriquees.iterator();
		for(;it.hasNext() ;) {
			List<String> next = it.next();
			if(next.size() >= 6) {
				Plan plan = recupererPlan(next);
				calendrier.ajouterPlan(plan);
			}
		}
	}
	
	private List<Tache> tachesRetirees(List<List<String>> listesImbriquees) {
		List<Tache> tachesRetirees = new ArrayList<>();
		Iterator<List<String>> it = listesImbriquees.iterator();
		for(;it.hasNext() ;) {
			List<String> next = it.next();
			if(next.size() >= 6) {
				Plan plan = recupererPlan(next);
				if(plan instanceof Tache tache) {
					tachesRetirees.add(tache);
				}
			}
			
		}
		return tachesRetirees;
	}

	private Plan recupererPlan( List<String> ligne) {

		Plan planLigne;
		Iterator<String> itLigne = ligne.iterator();
		
		int annee = Integer.parseInt(itLigne.next());
		int mois = Integer.parseInt(itLigne.next());
		int jour = Integer.parseInt(itLigne.next());
		GregorianCalendar date = new GregorianCalendar(annee, mois, jour);
		Utils.setUpDate(date);
		String nom = itLigne.next();
		String infoSup = itLigne.next();
		Horloge heure = recupererHeure(itLigne.next());
		String next = itLigne.next();
		if (!next.equals("null")) {
			Boolean accomplie = Boolean.parseBoolean(next);

			String sousTaches = itLigne.next();
			planLigne = new Tache(date, nom, infoSup, heure, accomplie);
			ajouterSousTaches((Tache) planLigne, sousTaches);
			
		} else {
			planLigne = new Evenement(date, nom, infoSup, heure);
		}
		return planLigne;
	}
	
	private Horloge recupererHeure(String strHorloge ) {
		String[] strTabHorloge = strHorloge.split("h");
		int heure = Integer.parseInt(strTabHorloge[0]);
		int minutes = Integer.parseInt(strTabHorloge[1]);
		return new Horloge(heure, minutes);
	}

	private void ajouterSousTaches(Tache tache, String ligneSousTache) {
		ligneSousTache =  ligneSousTache.substring(1,  ligneSousTache.length() - 1);
		if(!ligneSousTache.equals("")) {
			String[] sousTache = ligneSousTache.split("~ ");
			for(int i = 0; i < sousTache.length; i++) {
				String[] valeurs = sousTache[i].split("~");
				
				String nom = valeurs[0];
				String infoSup = valeurs[1];
				Horloge heure = recupererHeure(valeurs[2]);
				Boolean accomplie = Boolean.parseBoolean(valeurs[3]);
				
				tache.ajouterSousTache(nom, infoSup, heure, accomplie);
			}
		}
	}

}
