package coeur.boutique.secondaires;

import coeur.DonneesApplication;
import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.secondaires.produits.AbstractProduit;
import coeur.boutique.secondaires.produits.AbstractProduitActivable;
import frontiere.gui.GUIManager;

public class BoutiqueLookAndFeel extends AbstractBoutiqueAchetable {
	
	private static final long serialVersionUID = 7L;
	private static String nom = "Boutique Look And Feel";
	private static int prix = 10;


	public BoutiqueLookAndFeel(String description) {
		super(prix, nom, description, false);
	}
	
	private static class ProduitLookAndFeel extends AbstractProduitActivable{
				

		private static final long serialVersionUID = 12L;
		private final String lookAndFeel;
		private final String nomLookAndFeel;

		public ProduitLookAndFeel(int prix, String nom, String description, String lookAndFeel) {
			super(prix, nom, description);
			this.lookAndFeel = lookAndFeel;
			String[] decompNom = nom.split(" ");
			nomLookAndFeel = decompNom[decompNom.length-1];
		}

		@Override
		public void setActive(boolean active) {
			if(active) {
				activer();
				lancerActivation();
			}
			else {
				desactiver();
				lancerActivation(DonneesApplication.LOOK_AND_FEEL);

			}
		}

		@Override
		public String descriptionDetaillee() {
			return "nom : " + getNom() + "\n" +
					"description : " + getDescription() + "\n" +
					"lookAndFeel : " + nomLookAndFeel+ "\n" ;
					
		}

		@Override
		public String descriptionDetailleeUI() {
			return "<b>nom : </b>" + getNom() + "<br>" +
					"<b>description : </b>" + getDescription() + "<br>" +
					"<b>lookAndFeel : </b>" + nomLookAndFeel ;
		}

		@Override
		protected void lancerActivation(Object... objects) {
			if(objects.length == 0) {
				GUIManager.setLookAndFeel(lookAndFeel);
			}
			else {
				GUIManager.setLookAndFeel(DonneesApplication.LOOK_AND_FEEL);
			}
		}
	}
	
	@Override
	public void lancerActivationProduitsActives() {
		for (AbstractProduit produit : this) {
			ProduitLookAndFeel flookAndFeel= (ProduitLookAndFeel) produit;
			if(flookAndFeel.isActive()) {
				flookAndFeel.lancerActivation();
			}
		}
		
	}
	
	@Override
	public boolean changerActivationProduit(String nomProduit) {
		boolean nouvelleActivation = false;
		AbstractProduitActivable produitRetire = recupererProduit(nomProduit);
		if(produitRetire != null) {			
			if(produitRetire.isActive()) {
				produitRetire.setActive(false);
				nouvelleActivation = false;
			}
			else {
				activerProduit(produitRetire);
				nouvelleActivation = true;
			}
		}
		return nouvelleActivation;
	}
	
	public void activerProduit(AbstractProduitActivable produitActiver) {
		for(AbstractProduit produit : this) {
			if(produit.equals(produitActiver)) {
				((AbstractProduitActivable)produit).setActive(true);
			}
			else {
				((AbstractProduitActivable)produit).desactiver();
			}
		}
	}
	
	@Override
	public void ajouterProduit(AbstractProduit produit) {
		if(produit instanceof ProduitLookAndFeel nouveauProduit) {
			getProduits().add(nouveauProduit);
		}
	}

	@Override
	public void ajouterProduit(int prix, String nom, String description, Object... objects) {
		if(objects.length == 1 && objects[0] instanceof String lookAndFeel) {
			ProduitLookAndFeel nouveauProduit = new ProduitLookAndFeel(prix, nom, description, lookAndFeel);
			getProduits().add(nouveauProduit);
		}
	}

	@Override
	public AbstractBoutiqueAchetable getBoutiqueVide() {
		return new BoutiqueLookAndFeel("Boutique LookAndFeel achetée par l'utilisateur");
	}

}
