package coeur.boutique.secondaires.produits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProduitActivable extends AbstractProduit implements Serializable{

	private static final long serialVersionUID = 18L;
	private boolean active = false;

	public AbstractProduitActivable(int prix, String nom, String description) {
		super(prix, nom, description);
	}
	
	protected abstract void lancerActivation(Object...objects);

	public abstract void setActive(boolean active);
	
	public void reinitialiser() {
		
	}

	public String descriptionDetaillee() {
		return toString();
				
	}

	public String descriptionDetailleeUI() {
		return "<b>nom : </b>" + getNom() + "<br>" +
				"<b>description : </b>" + getDescription() + "<br>";
	}

	public List<String> nomModifications(){
		return new ArrayList<>();
	}

	public List<Object> typeModifications(){
		return new ArrayList<>();
	}
	
	public boolean modifier(Object ...objects) {
		return false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void activer() {
		active = true;
	}
	
	public void desactiver() {
		active = false;
	}

	public String indicationSiActive() {
		if(active) {
			return "(Activé)";
		}
		return "(Désactivé)";
	}
	
}
