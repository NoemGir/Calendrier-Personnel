package coeur.boutique.secondaires;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;

import coeur.boutique.AbstractBoutiqueAchetable;
import coeur.boutique.secondaires.produits.AbstractProduit;
import coeur.boutique.secondaires.produits.AbstractProduitActivable;
import frontiere.gui.GUIManager;
import utils.Utils;

public class BoutiqueGUI extends  AbstractBoutiqueAchetable {
	
	
	private static final long serialVersionUID = 6L;
	private static final String nom = "Boutique GUI";
	private static final int prix = 5;
	
	private static class ProduitGUI extends AbstractProduitActivable {
		
		private static final long serialVersionUID = 11L;
		private Component composant;
		private final boolean backgroundPossible;
		private final boolean foregroundPossible;
		private Color backgroundCouleur;
		private Color foregroundCouleur;
		private final Color backgroundDefault;
		private final Color foregroundDefault;

		public ProduitGUI(int prix, String nom, String description, Component composant, boolean backgroundPossible, boolean foregroundPossible) {
			super(prix, nom, description);
			this.composant = composant;
			this.backgroundPossible = backgroundPossible;
			this.foregroundPossible = foregroundPossible;
			backgroundCouleur = composant.getBackground();
			foregroundCouleur = composant.getForeground();
			backgroundDefault = backgroundCouleur;
			foregroundDefault = foregroundCouleur;
		}
		
		@Override
		public void setActive(boolean active) {
			if(active) {
				activer();
				lancerActivation(backgroundCouleur, foregroundCouleur);
			}
			else {
				desactiver();
				lancerActivation(backgroundDefault, foregroundDefault);
			}
		}
		
		@Override
		protected void lancerActivation(Object ...objects) {
			Color[] couleurs = new Color[2];
			if(objects.length == 2 && objects[0] instanceof Color couleur1 && objects[1] instanceof Color couleur2 ) {
				if(backgroundPossible) {
					couleurs[1] = couleur1;
				}
				else {
					couleurs[1] = null;
				}
				if(foregroundPossible) {
					couleurs[0] = couleur2;
				} 
				else {
					couleurs[0] = null;
				}
			}
			
			GUIManager.updatePersonnalisation(composant.getClass(), couleurs);	
		}

		@Override
		public void reinitialiser() {
			backgroundCouleur = backgroundDefault;
			foregroundCouleur = foregroundDefault;
		}
		
		@Override
		public List<String> nomModifications() {
			List<String> noms = new ArrayList<>();
			if(backgroundPossible) {
				noms.add("Changer la couleur du background :");
			}
			if(foregroundPossible) {
				noms.add("Changer la couleur du foreground :");
			}
			return noms;
		}
		
		@Override
		public List<Object> typeModifications() {
			List<Object> types = new ArrayList<>();
			if(backgroundPossible) {
				types.add(new JColorChooser());
			}
			if(foregroundPossible) {
				types.add(new JColorChooser());
			}
			return types;
		}

		private boolean setModification(String mode, Color couleur) {
			boolean reussit = false;
			if(mode != null && couleur != null) {
				if(mode.equals("foreground") && foregroundPossible) {
					this.foregroundCouleur = couleur;
					reussit = true;
				}
				else if(mode.equals("background") && backgroundPossible){
					this.backgroundCouleur = couleur;
					reussit = true;
				}
			}
			return reussit;
		}
			
		
		private boolean modifProduitTerminal(String userInput) {
			boolean reussit = false;
			String[] donnees = userInput.split(" ");
			if(donnees.length == 4) {
				Color couleur = null;
				String mode = donnees[0];
				int val1;
				int val2;
				int val3;
				try {
					val1 = Integer.parseInt(donnees[1]);
					val2 = Integer.parseInt(donnees[2]);
					val3 = Integer.parseInt(donnees[3]);
					couleur = new Color(val1, val2, val3);
				} catch(Exception e) {
					return false;
				}
				reussit = setModification(mode, couleur);
			}
			return reussit;
		}
		
		
		private boolean modifProduitGUI(Object[] objets) {
			boolean reussit = false;
			if(objets[1] instanceof Color couleurDonnee) {
				String[] desc = ((String) objets[0]).split(" ");
				String mode = desc[desc.length-2];
				Color couleur = couleurDonnee;
				reussit = setModification(mode, couleur);
			}
			return reussit;
		}
		
		@Override
		public boolean modifier(Object...objects) {
			boolean reussit = false;
			if(objects.length > 0 && objects[0] instanceof String data)
				if(objects.length == 1) {
					reussit = modifProduitTerminal(data);
				}
				else if (objects.length == 2) {
					reussit = modifProduitGUI(objects);
				}
			
			return reussit;
		}
		
		@Override
		public String toString() {
			StringBuilder presentation = new StringBuilder(super.toString());
			if(!Utils.equalsColor(backgroundCouleur, backgroundDefault) && backgroundPossible) {
				presentation.append("   BackgroundColor = " + Utils.toStringColor(backgroundCouleur) + "\n");
			}
			if(!Utils.equalsColor(foregroundCouleur, foregroundDefault) && foregroundPossible) {
				presentation.append("   ForegroundColor = " + Utils.toStringColor(foregroundCouleur) + "\n");
			}
			return  presentation.toString();
		}
		
		@Override
		public String descriptionDetailleeUI() {
			StringBuilder sb = new StringBuilder("<b>nom :</b> " + getNom() + "<br>"+
					"<b>description :</b> " + getDescription() + "<br>" +
					"<b>composant :</b> " + nomComposant() +"<br>" );
			if(backgroundPossible) {
				String couleurBack = getHexa(backgroundCouleur);
				sb.append("<pre><b>backgound color :</b> <span style=\"background-color: " + couleurBack+ ";\">   </span> " + couleurBack + " " + indicationDefautFond() +"</pre>");
			}
			if(foregroundPossible) {
				String couleurFront = getHexa(foregroundCouleur);
				sb.append("<pre><b>foreground color :</b> <span style=\"background-color: " + couleurFront+ ";\">   </span> " + couleurFront + " " + indicationDefautFace() +"</pre>");
			}
			sb.append("Vous pouvez modifier la couleur du ");
			if(backgroundPossible) {
				sb.append("background");
				if(foregroundPossible) {
					sb.append(" et du ");
				}
			}
			if(foregroundPossible) {
				sb.append("foreground");
			}
			sb.append(" du composant");
			return sb.toString();					
		}
		
		@Override
		public String descriptionDetaillee() {
			StringBuilder sb = new StringBuilder(" ----------- " + indicationSiActive() + "\n" +
					"nom : " + getNom() + "\n"+
					"description : " + getDescription() + "\n" +
					"composant : " + nomComposant() +"\n");
			if(backgroundPossible) {
				sb.append("backgound color : " + Utils.toStringColor(backgroundCouleur) + indicationDefautFond() + "\n");
			}
			if(foregroundPossible) {
				sb.append("foreground color : " + Utils.toStringColor(foregroundCouleur) + indicationDefautFace() + "\n\n");
			}
			sb.append("Propriétés modifiables :");
			if(backgroundPossible) {
				sb.append("\n- background color (background val1 val2 val3) change la couleur de fond a RGB(val1, val2, val3)" );
			}
			if(foregroundPossible) {
				sb.append("\n- foreground color (background val1 val2 val3) change la couleur de front a RGB(val1, val2, val3)");
			}
			return sb.toString();
		}
		
		private String indicationDefautFond() {
			if(Utils.equalsColor(backgroundCouleur, backgroundDefault)) {
				return "(defaut)";
			}
			return "";

		}

		private String indicationDefautFace() {
			if(Utils.equalsColor(foregroundCouleur, foregroundDefault)) {
				return "(defaut)";
			}
			return "";
		}


		public String nomComposant() {
			return composant.getName();
		}
		
		private String getHexa(Color couleur) {
			return String.format("#%06x", couleur.getRGB() & 0x00FFFFFF);
		}

		public Color getBackgroundCouleur() {
			return backgroundCouleur;
		}

		public Color getForegroundCouleur() {
			return foregroundCouleur;
		}		
	}
	
	public BoutiqueGUI(String description) {
		super(prix, nom, description, true);
	}
	
	@Override
	public void lancerActivationProduitsActives() {
		for (AbstractProduit produit : this) {
			ProduitGUI produitGUI = (ProduitGUI) produit;
			if(produitGUI.isActive()) {
				produitGUI.lancerActivation(produitGUI.getBackgroundCouleur(), produitGUI.getForegroundCouleur());
			}
		}
		
	}
	
	@Override
	public void ajouterProduit(int prix, String nom, String description, Object... objects) {
		
		if(objects.length == 3 && objects[0] instanceof Component composant &&  objects[1] instanceof Boolean backgoundPossible && objects[2] instanceof Boolean foregroundPossible) {
			ProduitGUI nouveau = new ProduitGUI(prix, nom, description, composant, backgoundPossible, foregroundPossible);
			getProduits().add(nouveau);
		}
	}

	@Override
	public void ajouterProduit(AbstractProduit produit) {
		if(produit instanceof ProduitGUI nouveauProduit) {
			getProduits().add(nouveauProduit);
		}
	}

	@Override
	public AbstractBoutiqueAchetable getBoutiqueVide() {
		return  new BoutiqueGUI("L'ensemble des produits de type GUI achetés par l'utilisateur !");

	}
}
