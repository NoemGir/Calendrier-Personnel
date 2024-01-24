package coeur.boutique;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coeur.boutique.keys.KeyBoutique;
import coeur.boutique.secondaires.produits.AbstractProduit;

public class Boutique<T extends IBoutiqueSecondaire>  implements Iterable<T>,Serializable {
	
	private static final long serialVersionUID = 3L;
	private Set<T> boutiquesDisponibles = new HashSet<>();
	
	public Boutique() {
	}
	
	public void ajouterBoutique(T boutique) {
		boutiquesDisponibles.add(boutique);
	}
	
	public void retirerBoutique(T boutique) {
		boutiquesDisponibles.remove(boutique);
	}
	
	public T recupererBoutique(String nom) {
		KeyBoutique key = new KeyBoutique(nom);
		if(boutiquesDisponibles.contains(key)) {
			Iterator<T> it = boutiquesDisponibles.iterator();
			T boutique = null;
			
			for(;it.hasNext() && !key.equals(boutique) ; boutique = it.next());
			
			if(key.equals(boutique)) {
				return boutique;
			}
		}
		return null;
	}
	
	public Map<String, List<String>> obtenirProduits(){
		Map<String, List<String>> mapBoutique = new HashMap<>();
		for(T boutique : this) {
			List<String> nomProduits = new ArrayList<>();
			for(AbstractProduit produit : boutique ) {
				nomProduits.add(produit.getNom());
			}
			mapBoutique.put(boutique.getNom(), nomProduits);
		}
		return mapBoutique;
	}
	
	public Set<T> getBoutiquesDisponibles() {
		return boutiquesDisponibles;
	}

	@Override
	public Iterator<T> iterator() {
		return boutiquesDisponibles.iterator();
	}
	
	
	public boolean isEmpty() {
		return boutiquesDisponibles.isEmpty();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Boutique<IBoutiqueSecondaire> other = (Boutique<IBoutiqueSecondaire>) obj;
		return obtenirProduits().equals(other.obtenirProduits());
	}
	
	
}



























