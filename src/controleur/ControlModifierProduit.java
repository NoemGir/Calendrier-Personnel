package controleur;

import java.util.ArrayList;
import java.util.List;

import coeur.UserData;
import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.Boutique;
import coeur.boutique.IBoutiqueSecondaire;

public class ControlModifierProduit {

	
	Boutique<IBoutiqueSecondaire> boutique;
	UserData userData;
	
	public ControlModifierProduit(Boutique<IBoutiqueSecondaire> boutique, UserData userData) {
		this.boutique = boutique;
		this.userData = userData;
	}
	
	public boolean produitModifiable(String nomBoutique) {
		AbstractBoutiqueAchetable userBoutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(userBoutique != null) {
			return userBoutique.isProduitModifiable();
		}
		return false;
	}
	
	public boolean modifierProduit(String nomBoutique, String nomProduit, Object...objects) {
		AbstractBoutiqueAchetable userBoutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(userBoutique != null) {
			return userBoutique.modifierProduit(nomProduit, objects);
		}
		return false;
	}
	
	public void reinitialiserProduit(String nomBoutique, String nomProduit) {
		AbstractBoutiqueAchetable userBoutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(userBoutique != null) {
			userBoutique.reinitialiserProduit(nomProduit);
		}
	}

	public List<String> getNomsModification(String nomBoutique, String nomProduit) {
		AbstractBoutiqueAchetable userBoutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(userBoutique != null) {
			return userBoutique.nomModificationsProduit(nomProduit);
		}
		return new ArrayList<>();
	}
	
	public List<Object> getTypesModification(String nomBoutique, String nomProduit){
		AbstractBoutiqueAchetable userBoutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(userBoutique != null) {
			return userBoutique.typeModificationsProduit(nomProduit);
		}
		return new ArrayList<>();
	}

}
