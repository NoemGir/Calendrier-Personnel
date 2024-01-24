package controleur;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coeur.DonneesApplication;
import coeur.UserData;
import coeur.calendrier.KeyHorloge;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

public class ControlAfficherCalendrier {
	UserData userData;

	public ControlAfficherCalendrier(UserData userData) {
		this.userData = userData;
	}
	
	public String donnerPlanUnJour(GregorianCalendar jour){
		
		Set<Plan> plans = userData.getCalendrier().planDeUnJour(jour);

		if(plans != null) {
			StringBuilder presentation = new StringBuilder();
			int numero = 1;
			for(Plan plan : plans) {
				presentation.append(plan.presenterPlan(numero) +"\n\n");
				numero++;
			}
			return presentation.toString();
		}
		return null;
	}
	
	public String presentationPlan(GregorianCalendar jour, int indicePlan){
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if (plan != null) {
			return plan.presenterPlan(indicePlan);
		}
		return null;
		
	}
	
	public String presentationPlanComplet(GregorianCalendar jour, int indicePlan){
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null) {
			return plan.affichageComplet();
		}
		return null;
	}
	 
	public String presentationSousTacheComplet(GregorianCalendar jour, int indicePlan, int indiceSousTache){
		Tache tache = (Tache) userData.getCalendrier().getPlan(jour, indicePlan);
		if(tache != null) {
			return tache.presentationCompleteSousTache(indiceSousTache);
		}
		return null;
	}
	
	public String presentationEnsemblePlans() {
		return userData.getCalendrier().toString();
	}
	
	public String presentationEnsembleTrophees() {
		return userData.getTrophees().toString();
	}
	
	public List<String> obtenirNomPlans(GregorianCalendar jour){
		List<String> nomPlans = new ArrayList<>();
		Set<Plan> plansJour = userData.getCalendrier().planDeUnJour(jour);
		if(plansJour == null) {
			return null;
		}
		for(Plan plan : plansJour) {
			String heure = plan.getHeure().toString();
			if(heure.equals(new KeyHorloge().toString())) {
				nomPlans.add(plan.getNom());
			}
			else {
				nomPlans.add(heure +" : " + plan.getNom());
			}
		}
		return nomPlans;
	}
	
	public Map<String, List<String>> obtenirNomTaches(GregorianCalendar jour){
		return userData.getCalendrier().obtenirNomTaches(jour);
	}
	
	public List<String> obtenirDescSousTaches(GregorianCalendar jour, int indicePlan){
		Tache tache = (Tache) userData.getCalendrier().getPlan(jour, indicePlan);
		if(tache == null) {
			return null;
		}
		return tache.obtenirDescSousTaches();
	}
	
	public List<GregorianCalendar> obtenirJoursEnregistres(){
		List<GregorianCalendar> joursList = new ArrayList<>();

		Set<GregorianCalendar> jours = userData.getCalendrier().donnerEnsembleJours();
		joursList.addAll(jours);
		return joursList;
	}
	
	
	public String obtenirNomPlan(GregorianCalendar jour, int indicePlan) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null) {
			return plan.getNom();
		}
		return "";
	}
	
	public String obtenirDatePlan(GregorianCalendar jour, int indicePlan) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null) {
			return DonneesApplication.DF.format(plan.getDate().getTime());
		}
		return "";
	}
	public String obtenirHeurePlan(GregorianCalendar jour, int indicePlan) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null) {
			return plan.getHeure().toString();
		}
		return "";
	}
	public String obtenirInfoSupPlan(GregorianCalendar jour, int indicePlan) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null) {
			return plan.getInfoSup();
		}
		return "";
	}

	public String obtenirCategoriePlan(GregorianCalendar jour, int indicePlan) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		String titre = "";
		if(plan != null) {
			if(plan.getClass() == Tache.class) {
				titre = "Tâche " + ((Tache) plan).indicationSiTacheTerminee();
			}
			else {
				titre = "Evénement";
			}
		}
		return titre;
	}
	
	public String obtenirNomSousTache(GregorianCalendar jour, int indicePlan, int indiceSousTache) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null && plan.getClass() == Tache.class) {
			Tache tache = (Tache) plan;
			return tache.getNomSousTache(indiceSousTache);
		}
		return "";
	}
	
	public String obtenirHeureSousTache(GregorianCalendar jour, int indicePlan, int indiceSousTache) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null && plan.getClass() == Tache.class) {
			Tache tache = (Tache) plan;
			return tache.getHeureSousTache(indiceSousTache).toString();
		}
		return "";
	}
	public String obtenirInfoSupSousTache(GregorianCalendar jour, int indicePlan, int indiceSousTache) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null && plan.getClass() == Tache.class) {
			Tache tache = (Tache) plan;
			return tache.getInfosSupSousTache(indiceSousTache);
		}
		return "";
	}

	public String obtenirTerminaisonSousTache(GregorianCalendar jour, int indicePlan, int indiceSousTache) {
		Plan plan = userData.getCalendrier().getPlan(jour, indicePlan);
		if(plan != null && plan.getClass() == Tache.class) {
			Tache tache = (Tache) plan;
			return tache.indicationSiSousTacheTerminee(indiceSousTache);
		}
		return "";
	} 
}






