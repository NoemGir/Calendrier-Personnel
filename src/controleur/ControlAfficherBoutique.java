package controleur;

import java.util.List;
import java.util.Map;

import coeur.UserData;
import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.Boutique;
import coeur.boutique.IBoutiqueSecondaire;
import coeur.boutique.secondaires.produits.AbstractProduit;

public class ControlAfficherBoutique {

	Boutique<IBoutiqueSecondaire> boutique;
	UserData userData;
	


	public ControlAfficherBoutique(Boutique<IBoutiqueSecondaire> boutique, UserData userData) {
		this.boutique = boutique;
		this.userData = userData;
	}

	private String presentationProduits(IBoutiqueSecondaire boutique) {
		StringBuilder presentation = new StringBuilder();
		if(boutique != null) { 
			for(AbstractProduit produit : boutique) {
				presentation.append(produit);
			}
		}
		return presentation.toString();
	}
	
	public String getPresentationProduitsBoutique(String nomBoutique) {
		IBoutiqueSecondaire boutiqueSec = boutique.recupererBoutique(nomBoutique);
		return presentationProduits(boutiqueSec);
	}
	

	public String getPresentationProduitsPossedes(String nomBoutique) {
		AbstractBoutiqueAchetable boutiqueSec = userData.getUserBoutique().recupererBoutique(nomBoutique);
		return presentationProduits(boutiqueSec);
	}
	
	public String getPresentationBoutique() {
		StringBuilder presentation = new StringBuilder();

		for(IBoutiqueSecondaire boutiqueSec : boutique) {
			presentation.append(boutiqueSec);
		}
		return presentation.toString();
	}
	
	public String getPresentationsBoutiquesPossedees() {
		StringBuilder presentation = new StringBuilder();

		for(AbstractBoutiqueAchetable boutiqueSec : userData.getUserBoutique()) {
			presentation.append(boutiqueSec);
		}
		return presentation.toString();
	}

	public boolean produitsRestants(String nomBoutique) {
		IBoutiqueSecondaire boutiqueSec = boutique.recupererBoutique(nomBoutique);
		if(boutiqueSec != null) {
			return !boutiqueSec.isEmpty();
		}
		return false;
	}
	
	public boolean userBoutiqueVide(String nomBoutique) {
		
		AbstractBoutiqueAchetable boutiqueSec = userData.getUserBoutique().recupererBoutique(nomBoutique);
		
		if(boutiqueSec != null) {
			return boutiqueSec.isEmpty();
		}
		return false;
	}
	
	public String getDescriptionBoutique(String nomBoutique) {
		String description = "";
		IBoutiqueSecondaire boutiqueSec = boutique.recupererBoutique(nomBoutique);
		if(boutiqueSec != null) {
			description = boutiqueSec.getDescription();
		}
		return description;
	}

	public boolean boutiquesRestantes() {
		return !boutique.isEmpty();
	}
	
	public boolean boutiquePersoVide() {
		return userData.getUserBoutique().isEmpty();
	}
	
	public String getPresentationProduit(String nomBoutique, String nomProduit) {
		AbstractBoutiqueAchetable boutiqueSec = (AbstractBoutiqueAchetable) boutique.recupererBoutique(nomBoutique);
		if(boutiqueSec != null) {
			return boutiqueSec.descriptionProduit(nomProduit);
		}
		return "";
	}
	
	public String getPresentationProduitUI(String nomBoutique, String nomProduit) {
		IBoutiqueSecondaire boutiqueSec = boutique.recupererBoutique(nomBoutique);
		if(boutiqueSec != null) {
			return boutiqueSec.descriptionProduitUI(nomProduit);
		}
		return "";
	}
	
	public Map<String, List<String>> getMapProduits(){
		return boutique.obtenirProduits();
	}
	
}
