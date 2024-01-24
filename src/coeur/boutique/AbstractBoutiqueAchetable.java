package coeur.boutique;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import coeur.boutique.keys.KeyProduit;
import coeur.boutique.secondaires.produits.AbstractProduit;
import coeur.boutique.secondaires.produits.AbstractProduitActivable;

public abstract class AbstractBoutiqueAchetable extends AbstractProduit  implements  IBoutiqueSecondaire{

	private static final long serialVersionUID = 15L;
	private Set<AbstractProduitActivable> produits = new HashSet<>();
	private int nbOperations;
	private boolean produitModifiable;
	
	protected AbstractBoutiqueAchetable(int prix, String nom, String description, boolean produitModifiable) {
		super(prix, nom, description);
		this.produitModifiable = produitModifiable;
	}
	

	public abstract void ajouterProduit(int prix, String nom, String description, Object... objects);
	
	public abstract void lancerActivationProduitsActives();
	
	public abstract AbstractBoutiqueAchetable getBoutiqueVide();	
	
	public Iterator<AbstractProduit> iterator() {
		return new Iterateur();
	}
	
	private class Iterateur implements Iterator<AbstractProduit>{
		private int nbOperationReference = nbOperations;		
		private Iterator<AbstractProduitActivable> it;
		
		public Iterateur() {
			it = produits.iterator();
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}
		
		@Override
		public AbstractProduit next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			if(nbOperationReference != nbOperations) {
				throw new ConcurrentModificationException();
			}
			return it.next();
		}
	}
	
	
	public boolean modifierProduit(String nomProduit, Object... objects) {
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {	
			return produitRetire.modifier(objects);
		}
		return false;
	}

	public List<String> nomModificationsProduit(String nomProduit){
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {			
			return produitRetire.nomModifications();
		}
		return new ArrayList<>();
	}
	
	public List<Object> typeModificationsProduit(String nomProduit){
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {			
			return produitRetire.typeModifications();
		}
		return new ArrayList<>();
	}
	
	public void reinitialiserProduit(String nomProduit) {
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {			
			produitRetire.reinitialiser();
		}
	}

	public boolean changerActivationProduit(String nomProduit) {
		boolean nouvelleActivation = false;
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {			
			if(produitRetire.isActive()) {
				produitRetire.setActive(false);
				nouvelleActivation = false;
			}
			else {
				produitRetire.setActive(true);
				nouvelleActivation = true;
			}
		}
		return nouvelleActivation;
	}
	
	public AbstractProduitActivable recupererProduit(String nom) {
		AbstractProduit key = new KeyProduit(nom);
		if(produits.contains(key)) {

			Iterator<AbstractProduitActivable> it = produits.iterator();
			AbstractProduitActivable produit = null;
			
			for(;it.hasNext() && !key.equals(produit) ; produit = it.next());
			
			if(key.equals(produit)) {
				return produit;
			}
		}
		return null;
	}
	
	public String descriptionProduit(String nomProduit) {
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {			
			return produitRetire.descriptionDetaillee();
		}
		return "";
	}
	
	public String descriptionProduitUI(String nomProduit) {
		AbstractProduitActivable produitRetire =   recupererProduit(nomProduit);
		if(produitRetire != null) {			
			return produitRetire.descriptionDetailleeUI();
		}
		return "";
	}
	
	public AbstractProduitActivable retirerProduit(String nomProduit) {
		AbstractProduitActivable produitRetire =   recupererProduit(nomProduit);
		produits.remove(produitRetire);
		return produitRetire;
	}
	
	public void retirerProduit(AbstractProduitActivable produitARetirer) {
		produits.remove(produitARetirer);
	}

	public AbstractProduitActivable acheterProduit(String nomProduit, int argent) {
		AbstractProduitActivable produit = null;
		if(argentSuffisant(nomProduit,argent)) {
			produit = retirerProduit(nomProduit);
		}
		return produit;
	}

	public boolean argentSuffisant(String nomProduit, int argentPossede) {
		AbstractProduitActivable produit =  recupererProduit(nomProduit);
		if(produit != null) {
			return produit.getPrix() <= argentPossede;
		}
		return false;
	}

	public boolean isEmpty() {
		return produits.isEmpty();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof AbstractBoutiqueInitiale other) {
			return Objects.equals(getNom(), other.getNom());
		}
		if (obj instanceof AbstractBoutiqueAchetable other) {
			return Objects.equals(getNom(), other.getNom());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Boutique : "
				+ "\n   nom = "+getNom() + "\n"		
				+ "   " + getDescription() + "\n";
		
	}

	public Set<AbstractProduitActivable> getProduits() {
		return produits;
	}


	public boolean isProduitModifiable() {
		return produitModifiable;
	}

	
}
