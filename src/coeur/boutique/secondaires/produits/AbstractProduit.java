package coeur.boutique.secondaires.produits;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractProduit implements Serializable {
	
	private static final long serialVersionUID = 17L;
	private int prix;
	private String nom;
	private boolean possede = false;
	private String description;
	
	
	protected AbstractProduit(int prix, String nom, String description) {
		this.prix = prix;
		this.nom = nom;
		this.description = description;
	}
	
	public int getPrix() {
		return prix;
	}


	public String getNom() {
		return nom;
	}

	public String getDescription() {
		return description;
	}
	

	public boolean isPossede() {
		return possede;
	}

	public void setPossede(boolean possede) {
		this.possede = possede;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof AbstractProduit other){
			return Objects.equals(nom, other.nom);
		}
		return false;
		
	}
	
	@Override
	public int hashCode() {
		return nom.hashCode() * 13;
	}
	
	@Override
	public String toString() {
		if(possede) {
			return presentationPossession();
		}
		return presentationAchat();
	}

	public String presentationAchat() {
		return "Produit " +prix + " stars :"
				+ "\n   nom = "+nom + "\n"		
				+ "   " + description + "\n";
	}
	
	public String presentationPossession() {
		return "Produit " + " :"
				+ "\n   nom = "+nom + "\n"		
				+ "   " + description + "\n";
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
