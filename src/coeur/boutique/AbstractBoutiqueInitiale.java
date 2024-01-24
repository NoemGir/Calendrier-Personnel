package coeur.boutique;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import coeur.boutique.keys.KeyProduit;
import coeur.boutique.secondaires.produits.AbstractProduit;

public abstract class AbstractBoutiqueInitiale implements IBoutiqueSecondaire{

	/**
	 * 
	 */
	private static final long serialVersionUID = 16L;
	private Set<AbstractProduit> produits = new HashSet<>();
	private String nom;
	private String description;
	
	

	protected AbstractBoutiqueInitiale(String nom, String description) {
		this.nom = nom;
		this.description = description;
	}

	public AbstractProduit recupererProduit(String nom) {
		AbstractProduit key = new KeyProduit(nom);
		if(produits.contains(key)) {
			Iterator<AbstractProduit> it = produits.iterator();
			AbstractProduit produit = null;
			
			for(;it.hasNext() && !key.equals(produit) ; produit = it.next());
			
			if(key.equals(produit)) {
				return produit;
			}
		}
		return null;
	}

	public AbstractProduit retirerProduit(String nomProduit) {
		AbstractProduit produitRetire =  recupererProduit(nomProduit);
			produits.remove(produitRetire);
		return produitRetire;
	}

	public AbstractProduit acheterProduit(String nomProduit, int argent) {
		AbstractProduit produit = null;
		if(argentSuffisant(nomProduit,argent)) {
			produit = retirerProduit(nomProduit);
		}
		return produit;
	}

	public boolean argentSuffisant(String nomProduit, int argentPossede) {
		AbstractProduit produit = recupererProduit(nomProduit);
		return produit.getPrix() <= argentPossede;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof AbstractBoutiqueInitiale other) {
			return Objects.equals(nom, other.nom);
		}
		if (obj instanceof AbstractBoutiqueAchetable other) {
			return Objects.equals(nom, other.getNom());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return nom.hashCode() * 13;
	}
	
	@Override
	public String descriptionProduitUI(String nomProduit) {
		AbstractProduit produit = recupererProduit(nomProduit);
		if(produit != null) {
			return produit.getDescription();
		}
		return "";
		}
	
	public Iterator<AbstractProduit> iterator() {
		return produits.iterator();
	}
	
	public void ajouterProduit(AbstractProduit produit) {
		if(produit instanceof AbstractBoutiqueAchetable) {
			produits.add(produit);
		}
	}
	
	@Override
	public String toString() {
		return "Boutique : "
				+ "\n   nom = "+nom + "\n"		
				+ "   " + description + "\n";
		
	}
	public boolean isEmpty() {
		return produits.isEmpty();
	}
	
	public String getNom() {
		return nom;
	}

	public String getDescription() {
		return description;
	}
}
