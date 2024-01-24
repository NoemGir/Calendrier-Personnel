package controleur;

import coeur.UserData;
import coeur.boutique.Boutique;
import coeur.boutique.IBoutiqueSecondaire;

public class ControlVerificationBoutique {
	
	Boutique<IBoutiqueSecondaire> boutique;
	UserData userData;

	
	public ControlVerificationBoutique(Boutique<IBoutiqueSecondaire> boutique, UserData userData) {
		this.boutique = boutique;
		this.userData = userData;
	}

	public boolean verificationBoutiqueSelectionnee(String userInput) {
		return boutique.recupererBoutique(userInput) != null;
	}
	
	public boolean verificationProduitSelectionne(String nomBoutique, String userInput) {
		IBoutiqueSecondaire boutiqueSec = boutique.recupererBoutique(nomBoutique);
		if(boutiqueSec != null) {
			return boutiqueSec.recupererProduit(userInput) != null;
		}
		return false;
	}
	
	public boolean verificationProduitUserSelectionne(String nomBoutique, String userInput) {
		IBoutiqueSecondaire boutiqueSec = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(boutiqueSec != null) {
			return boutiqueSec.recupererProduit(userInput) != null;
		}
		return false;
	}

	public boolean verificationBoutiqueUserSelectionnee(String userInput) {
		return userData.getUserBoutique().recupererBoutique(userInput) != null;

	}
}
