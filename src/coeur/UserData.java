package coeur;

import java.io.Serializable;

import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.Boutique;
import coeur.calendrier.Calendrier;
import coeur.calendrier.plan.Evenement;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

public class UserData implements Serializable {
	
		private static final long serialVersionUID = 1L;

		private Calendrier<Tache> trophees = new Calendrier<>();
		private Boutique<AbstractBoutiqueAchetable> userBoutique = new Boutique<>();
		
		
		//private int tempsDeTravailTotal = 0;
		private int stars = 0;
		private int nbTotalStars = 0;
		private Calendrier<Plan> calendrier = new Calendrier<>(); 

		public String[] recupererInformation(){
			String[] infos = new String[7];
			String nbPlanTotal = String.valueOf(calendrier.donnerNbPlan());
			String nbTacheTotal = String.valueOf(calendrier.donnerNbTacheETSousTache());
			String nbTacheSimple = String.valueOf(calendrier.donnerNombreElement(Tache.class));
			String nbEvent = String.valueOf(calendrier.donnerNombreElement(Evenement.class));
			infos[0] = String.valueOf(stars);
			infos[1] = nbPlanTotal;
			infos[2] = nbTacheTotal;
			infos[3] = nbTacheSimple;
			infos[4] = nbEvent;
			infos[5] = String.valueOf(getNbTrophees());
			infos[6] = String.valueOf(nbTotalStars);
			return infos;
			
		}
		
		public void ajouterTrophee(Tache tacheTermine) {
			trophees.ajouterPlan(tacheTermine);
		}

		
		public Calendrier<Plan> getCalendrier() {
			return calendrier;
		}

		public void setCalendrier(Calendrier<Plan> calendrier) {
			this.calendrier = calendrier;
		}
	
		public void ajouterDesStars(int nbStarsAAjouter) {
			this.stars = stars + nbStarsAAjouter;
		}

		
		public int getStars() {
			return stars;
		}
		
		public void setStars(int stars) {
			this.stars = stars;
		}
		
		public int getNbTrophees() {
			return trophees.donnerNbPlan();
		}
		

		public Boutique<AbstractBoutiqueAchetable> getUserBoutique() {
			return userBoutique;
		}

		public void setTrophees(Calendrier<Tache> trophees) {
			this.trophees = trophees;
		}
		
		public boolean terminerTache(Tache tacheTermine) {
			boolean present = calendrier.retirerPlan(tacheTermine);
			if(present) {
				tacheTermine.terminerTache();
				int sommeAjoutee = DonneesApplication.STARS_APRES_ACCOMPLISSEMENT_TACHE + DonneesApplication.STARS_APRES_SOUS_TACHE * tacheTermine.getNbSousTache();
				stars += sommeAjoutee;
				nbTotalStars += sommeAjoutee;
				ajouterTrophee(tacheTermine);		
			}
			return present;
		}
		
		public void ajouterStarsTerminaisonSousTache() {
			stars += DonneesApplication.STARS_APRES_ACCOMPLISSEMENT_SOUS_TACHE;
			nbTotalStars += DonneesApplication.STARS_APRES_ACCOMPLISSEMENT_SOUS_TACHE;
		}
		

		public String presenterTrophees() {
			StringBuilder presentation = new StringBuilder();
			if (getNbTrophees() > 0) {
				presentation.append("La liste des trophees est : \n");
				presentation.append(trophees);
			} else {
				presentation.append(" Vous n'avez aucun trophee pour le moment\n ");
			}
			return presentation.toString();
		}

		public Calendrier<Tache> getTrophees() {
			return trophees;
		}

		public int getNbTotalStars() {
			return nbTotalStars;
		}

		

}
