package coeur.exterieur;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import coeur.DonneesApplication;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

public class ExporteurFichier {
	
	public ExporteurFichier() {
	}


	public <T extends Iterable<Plan>> void transfererEnsemblePlans(T ensemblePlan, String nomDuFichier) {
		List<String[]> calFormatCSV = mettreFormatCSV(ensemblePlan);
		ecrireCSV( calFormatCSV, nomDuFichier);
	}

	private <T extends Iterable<Plan>> List<String[]> mettreFormatCSV(T ensemblePlan) {
		
		List<String[]> donneesFormatCSV = new ArrayList<>();

		for (Plan plan : ensemblePlan) {
			String[] ligne = new String[8];
			GregorianCalendar date = plan.getDate();		
			
			
			ligne[0] = Integer.toString(date.get(Calendar.YEAR));
			ligne[1] = Integer.toString(date.get(Calendar.MONTH));
			ligne[2] = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
			ligne[3] = plan.getNom();
			ligne[4] = plan.getInfoSup();
			ligne[5] = plan.getHeure().toString();
			ligne[6] = "null";
			ligne[7] = "null";
			if (plan.getClass() == Tache.class) {
				Tache tache = (Tache) plan;
				ligne[6] = String.valueOf(tache.isAccomplie());
				String sousTaches = tache.getSousTaches().toString();
				ligne[7] = sousTaches.replace(',', '~');
			}			
			donneesFormatCSV.add(ligne);
		}
		return donneesFormatCSV;
	}
	
	
	private String eviterCharactereSpeciaux(String ligne) {
		String nouvelleLigne = ligne;
	    if (ligne != null &&  (ligne.contains(",") || ligne.contains("\"") || ligne.contains("'"))) {
		        ligne = ligne.replace("\"", "\"\"");
		        nouvelleLigne = "\"" + ligne + "\"";
	    }
	    return nouvelleLigne;
	}
	
	private String conversionCSV(String[] ligne) {
	    return Stream.of(ligne).map(this::eviterCharactereSpeciaux).collect(Collectors.joining(","));
	}

	private void ecrireCSV(List<String[]> donnees, String nomDuFichier){
		File fichierCSV = new File(DonneesApplication.CHEMIN_DES_FICHIERS + nomDuFichier);
	    try (PrintWriter pw = new PrintWriter(fichierCSV)) {
	        donnees.stream().map(this::conversionCSV).forEach(pw::println);
	    }
	    catch(IOException e) {
	    	System.out.println("Erreur lors de l'écriture dans le fichier CSV...");
	    	e.printStackTrace();
	    }
	    if(fichierCSV.exists()){
	    	System.out.println("Fichier bien créé");
	    }
	    else {
	    	System.out.println("le fichier n'a pas pu etre créé");
	    }
	    
	}
	
	public void mode_mise_a_jour () {
		String[] miseAJour = {"mise a jour"};
		changerMode(miseAJour);
	}
	
	public void mode_rajout() {
		String[] rajout = {"rajout"};
		changerMode(rajout);
	}
	
	private void changerMode(String[] nouveauMode) {
		List<String[]> mode = new ArrayList<>();
		mode.add(nouveauMode);
		ecrireCSV(mode, DonneesApplication.FICHIER_MODE);
	}
}


























