package controleur;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coeur.UserData;
import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.secondaires.BoutiqueFlashCards;

public class ControlAfficherInfosPerso {
	UserData userData;

	public ControlAfficherInfosPerso(UserData userData) {
		this.userData = userData;
	}
	
	public String[] getInfos() {
		return userData.recupererInformation();
	}

	public int getNbStars() {
		return userData.getStars();
	}
	
	public Map<String, List<String>> obtenirNomTrophees(GregorianCalendar jour){
		return userData.getTrophees().obtenirNomTaches(jour);
	}

	public List<GregorianCalendar> obtenirJoursEnregistresTrophees() {
		List<GregorianCalendar> joursList = new ArrayList<>();

		Set<GregorianCalendar> jours = userData.getTrophees().donnerEnsembleJours();
		joursList.addAll(jours);
		return joursList;
	}
	
	public Map<String, List<String>> getMapProduitsPossedes(){
		return userData.getUserBoutique().obtenirProduits();
	}
	
	public String getDescriptionBoutique(String nomBoutique) {
		String description = "";
		AbstractBoutiqueAchetable boutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(boutique != null) {
			description = boutique.getDescription();
		}
		return description;
	}
	
	public String getDescriptionProduit(String nomBoutique, String nomProduit) {
		String description = "";
		AbstractBoutiqueAchetable boutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(boutique != null) {
			description = boutique.descriptionProduitUI(nomProduit);
		}
		return description;
	}

	public List<String> getFlashCardsPossedes() {
		BoutiqueFlashCards boutique = (BoutiqueFlashCards) userData.getUserBoutique().recupererBoutique(new BoutiqueFlashCards("").getNom());
		if(boutique != null) {
			return boutique.nomsFlashCardsPossedees();
		}
		return new ArrayList<>();
	}
}
