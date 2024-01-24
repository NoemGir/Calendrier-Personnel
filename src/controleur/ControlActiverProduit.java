package controleur;

import coeur.UserData;
import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.secondaires.produits.AbstractProduitActivable;

public class ControlActiverProduit {
	
	private UserData userData;

	public ControlActiverProduit(UserData userData) {
		this.userData = userData;
	}
	
	public void lancerActivationTousLesProduits() {
		for (AbstractBoutiqueAchetable boutique : userData.getUserBoutique()) {
			boutique.lancerActivationProduitsActives();
		}
	}
	
	public boolean produitActive(String nomBoutique, String nomProduit) {
		AbstractBoutiqueAchetable userBoutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		if(userBoutique != null) {
			AbstractProduitActivable produit = userBoutique.recupererProduit(nomProduit);
			if(produit != null) {
				return produit.isActive();
			}
		}
		return false;
	}
	
	public boolean changerActivation(String nomBoutique, String nomProduit) {
		AbstractBoutiqueAchetable userBoutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
		return userBoutique.changerActivationProduit(nomProduit);
	}
}
