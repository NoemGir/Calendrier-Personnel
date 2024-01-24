package coeur.boutique;

import java.io.Serializable;

import coeur.boutique.secondaires.produits.AbstractProduit;

public interface IBoutiqueSecondaire extends Iterable<AbstractProduit>, Serializable {
	
	public abstract void ajouterProduit(AbstractProduit produit);
	
	public AbstractProduit retirerProduit(String nomProduit);

	public AbstractProduit acheterProduit(String nomProduit, int argent);

	public boolean argentSuffisant(String nomProduit, int argentPossede);
	
	public AbstractProduit recupererProduit(String nom);
	
	public boolean isEmpty();

	@Override
	public boolean equals(Object obj);
	
	@Override
	public int hashCode();
	
	public String getNom();
	
	@Override
	public String toString();
	
	public String getDescription();

	public abstract String descriptionProduitUI(String nomProduit);
			
}
