package controleur;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import coeur.DonneesApplication;
import coeur.UserData;
import coeur.calendrier.Calendrier;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;
import coeur.exterieur.CommandeShell;
import coeur.exterieur.ExporteurFichier;
import coeur.exterieur.ImporteurFichier;

public class ControlCommunicationOcaml {
	
	private List<Plan> tachesAGarder;
	private List<Tache> tachesPerdues;
	
	private CommandeShell commandeShell;
	private ExporteurFichier exporteur;
	private ImporteurFichier importeur;
	private UserData userData;

	public ControlCommunicationOcaml(CommandeShell commandeShell, ExporteurFichier exporteur,
			ImporteurFichier importeur, UserData userData) {
		this.commandeShell = commandeShell;
		this.exporteur = exporteur;
		this.importeur = importeur;	
		this.userData = userData;
		tachesAGarder = new ArrayList<>();
	}
	
	public List<String> recupererTachesRetirees(){
		List<String> presentationsTaches = new ArrayList<>();
		List<Tache> tachesRetirees = importeur.recupererTachesRetirees();
		tachesPerdues = tachesRetirees;
		for(Tache tache : tachesRetirees) {
			presentationsTaches.add(tache.getNom());
		}
		return presentationsTaches;
	}
	
	public void mettreAJour() {
		Calendrier<Plan> calendrier = userData.getCalendrier();
		if(calendrier.isEmpty()) {
			return;
		}
		exporteur.transfererEnsemblePlans(calendrier, DonneesApplication.FICHIER_PLANS);
		exporteur.mode_mise_a_jour();
		commandeShell.executerOcaml();
		importeur.recupererCalendrier(calendrier);
	}
	
	public void ajouterTachesAGarder(int[] tachesSelectionnees, GregorianCalendar nouvelleDate) {
		for (int indice : tachesSelectionnees) {
			Tache tache = tachesPerdues.remove(indice);
			tache.setDate(nouvelleDate);
			tachesAGarder.add(tache);
		}
	}
	
	public void exporterTachesAGarder() {
		exporteur.transfererEnsemblePlans(tachesAGarder, DonneesApplication.FICHIER_AGARDER);
		exporteur.mode_rajout();
		commandeShell.executerOcaml();
		importeur.recupererCalendrier(userData.getCalendrier());
	}
}






