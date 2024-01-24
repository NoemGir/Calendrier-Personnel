package controleur;

import coeur.UserData;
import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.Boutique;
import coeur.boutique.IBoutiqueSecondaire;
import coeur.boutique.secondaires.BoutiqueDeBoutiques;
import coeur.boutique.secondaires.produits.AbstractProduit;

public class ControlAcheterProduit {
	
	Boutique<IBoutiqueSecondaire> boutique;
	UserData userData;
	
	public ControlAcheterProduit(Boutique<IBoutiqueSecondaire> boutique, UserData userData) {
		this.boutique = boutique;
		this.userData = userData;
	}
	
	public void payer(int somme) {
		int argentInitial = userData.getStars();
		int nouvelleSomme = argentInitial - somme;
		userData.setStars(nouvelleSomme);
	}
	
	public int prixProduit(String nomBoutique, String nomProduit) {
		int prix = -1;
		IBoutiqueSecondaire boutiqueSec = boutique.recupererBoutique(nomBoutique);
		if(boutiqueSec != null) {
			AbstractProduit produit = boutiqueSec.recupererProduit(nomProduit);
			if(produit!= null) {
				return produit.getPrix();
			}
		}
		return prix;
	}
	
	private AbstractProduit acheterBoutique(String nomBoutique) {
		BoutiqueDeBoutiques boutiqueBoutique = (BoutiqueDeBoutiques) boutique.recupererBoutique(new BoutiqueDeBoutiques("").getNom());
		AbstractBoutiqueAchetable boutiqueAchetee = (AbstractBoutiqueAchetable) boutiqueBoutique.acheterProduit(nomBoutique, userData.getStars());
		if(boutiqueAchetee != null ) {
			boutique.ajouterBoutique(boutiqueAchetee);
			userData.getUserBoutique().ajouterBoutique( boutiqueAchetee.getBoutiqueVide());
			
			if(boutiqueBoutique.isEmpty()) {
				boutique.retirerBoutique(boutiqueBoutique);
			}
		}
		return boutiqueAchetee;
	}
	
	public AbstractProduit acheterProduitHorsBoutique(String nomBoutique, String nomProduit) {
		IBoutiqueSecondaire boutiqueSec = boutique.recupererBoutique(nomBoutique);
		AbstractProduit produitAchete = boutiqueSec.acheterProduit(nomProduit, userData.getStars());
		
		if(produitAchete != null) {
			AbstractBoutiqueAchetable userBoutique = userData.getUserBoutique().recupererBoutique(nomBoutique);
			userBoutique.ajouterProduit(produitAchete);
			if(boutiqueSec.isEmpty()) {
				boutique.retirerBoutique(userBoutique);
			}
		}
		return produitAchete;
	}
	
	public boolean acheterProduit(String nomBoutique, String nomProduit) {
		AbstractProduit produitAchete;
		if(nomBoutique.equals(new BoutiqueDeBoutiques("").getNom())) {
			produitAchete = acheterBoutique(nomProduit);
		} else {
			produitAchete = acheterProduitHorsBoutique(nomBoutique, nomProduit);
		}
		if(produitAchete != null ) {
			
			payer(produitAchete.getPrix());
			produitAchete.setPossede(true);
			return true;
		}
		return false;
	}
	
	public boolean verifierArgent(String nomBoutique, String nomProduit) {
		IBoutiqueSecondaire boutiqueSec = boutique.recupererBoutique(nomBoutique);
		if(boutiqueSec != null) {
			return boutiqueSec.argentSuffisant(nomProduit, userData.getStars());
		}
		return false;
	}

}
