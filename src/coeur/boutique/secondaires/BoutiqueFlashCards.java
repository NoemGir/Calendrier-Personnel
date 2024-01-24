package coeur.boutique.secondaires;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.secondaires.produits.AbstractProduit;
import coeur.boutique.secondaires.produits.AbstractProduitActivable;
import coeur.jeux.FlashCards;
import frontiere.gui.InfosPersoUI;

public class BoutiqueFlashCards extends AbstractBoutiqueAchetable {
	

	private static final long serialVersionUID = 5L;
	private final static String nom = "Boutique Flashs Cards";
	private final static int prix = 5;
	private int nbProduitActives = 0;
	
	 public BoutiqueFlashCards() {
		 super(prix, nom, "test de boutique sans constructeur", true);
	   }

	public BoutiqueFlashCards(String description) {
		super(prix, nom, description, true);
	}
	
	private static class ProduitFlashCards extends AbstractProduitActivable {

		private static final long serialVersionUID = 10L;

		private FlashCards flashCard;
		private String nomDefaut;
		
		public ProduitFlashCards() {
	        super(0, "test nom produit", "test desc produit");
	    }
		
		public ProduitFlashCards(int prix, String nom, String description, FlashCards flashCard) {
			super(prix, nom, description);
			this.flashCard = flashCard;
			nomDefaut = nom;
		}
		

		public FlashCards getFlashCard() {
			return flashCard;
		}

		@Override
		protected void lancerActivation(Object... objects) {
			InfosPersoUI.activerButtonFlashCard();	
		}

		@Override
		public void setActive(boolean active) {
			if(active) {
				activer();
				lancerActivation();
			}
			else {
				desactiver();
			}
		}
		
		@Override
		public void reinitialiser() {
			setNom(nomDefaut);
			InfosPersoUI.setNomProduitSelectionne(nomDefaut);
		}
		
		@Override
		public List<String> nomModifications() {
			List<String> noms = new ArrayList<>();
			noms.add("Modifier le nom de la FlashCard :");
			return noms;
		}
		
		@Override
		public List<Object> typeModifications() {
			List<Object> types = new ArrayList<>();
			types.add(new JTextField());
			return types;
		}
		
		@Override
		public boolean modifier(Object...objects) {
			String nouveuNom = (String) objects[0];
			setNom(nouveuNom);
			InfosPersoUI.setNomProduitSelectionne(nouveuNom);
			return true;
			
		}
	}
	
	public List<String> nomsFlashCardsPossedees() {
		List<String> nom = new ArrayList<>();
		for(AbstractProduit produit : this) {
			AbstractProduitActivable produitAct = (AbstractProduitActivable) produit;
			if(produitAct.isActive()) {
				nom.add(produit.getNom());
			}
		}
		return nom;
	}
	
	public FlashCards recupererFlashCards(String nomFlashCards) {
		ProduitFlashCards produitRetire = (ProduitFlashCards) recupererProduit(nomFlashCards);
		if(produitRetire != null) {	
			return produitRetire.getFlashCard();
		}
		return null;
	}
	
	@Override
	public boolean modifierProduit(String nomProduit, Object... objects) {
		System.out.println("modif produit");
		boolean reussit = false;
		if(!(objects.length == 1 && objects[0] instanceof String nom && !nom.equals("") && recupererProduit(nom) == null)) {
			return false;
		}	
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {	
			retirerProduit(produitRetire);
			reussit = produitRetire.modifier(objects);
			ajouterProduit(produitRetire);
		}
		return reussit;
	}
	
	@Override
	public boolean changerActivationProduit(String nomProduit) {
		boolean nouvelleActivation = false;
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {			
			if(produitRetire.isActive()) {
				nbProduitActives--;
				produitRetire.setActive(false);
				nouvelleActivation = false;

				if(nbProduitActives == 0) {
					InfosPersoUI.desactiverButtonFlashCard();
				}
			}
			else {
				nbProduitActives++;
				produitRetire.setActive(true);
				nouvelleActivation = true;
			}
		}
		return nouvelleActivation;
	}

	@Override
	public void ajouterProduit(int prix, String nom, String description, Object... objects) {
		
		if(objects.length == 1 && objects[0] instanceof FlashCards flashCard) {
			ProduitFlashCards nouveau = new ProduitFlashCards(prix, nom, description, flashCard);
			getProduits().add(nouveau);
		}
	}

	@Override
	public void ajouterProduit(AbstractProduit produit) {
		if(produit instanceof ProduitFlashCards nouveauProduit) {
			getProduits().add(nouveauProduit);
		}
	}

	@Override
	public AbstractBoutiqueAchetable getBoutiqueVide() {
		return new BoutiqueFlashCards("Boutique FlashCard achetée !");
	}

	@Override
	public void lancerActivationProduitsActives() {
		for (AbstractProduit produit : this) {
			ProduitFlashCards flashCard = (ProduitFlashCards) produit;
			if(flashCard.isActive()) {
				flashCard.lancerActivation();
			}
		}
		
	}

}
