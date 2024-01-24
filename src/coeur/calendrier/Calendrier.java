package coeur.calendrier;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

public class Calendrier<T extends Plan> implements Iterable<T>, Serializable {
	
	private static final long serialVersionUID = 2L;
	private int nbOperations;
	private Map<GregorianCalendar, Set<T>> calendrier = new TreeMap<>();
	

	@Override
	public Iterator<T> iterator() {
		return new Iterateur();
	}
	
	private class Iterateur implements Iterator<T>{
		private int nbOperationReference = nbOperations;
		private Iterator<GregorianCalendar> iterateurJour;
		private Iterator<T> iterateurPlan;
		private GregorianCalendar jour;
		
		
		
		public Iterateur() {
			iterateurJour = calendrier.keySet().iterator();
			
			if(iterateurJour.hasNext()) {
				jour = iterateurJour.next(); 
				iterateurPlan = calendrier.get(jour).iterator();
				
			}
		}
		
		private boolean hasNextLater() {
			
			Iterator<GregorianCalendar> temp = calendrier.keySet().iterator();
			for(;!temp.next().equals(jour););
			
			for(;temp.hasNext();) {
				GregorianCalendar jour_temp = temp.next();
				Set<T> setSuivant = calendrier.get(jour_temp);
				if(!setSuivant.isEmpty()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean hasNext() {
			if(iterateurPlan  == null) {
				if(iterateurJour.hasNext()) {
					jour = iterateurJour.next(); 
					iterateurPlan = calendrier.get(jour).iterator();
					
				}
				else return false;
			}
			if(!iterateurPlan.hasNext()) {
				return hasNextLater();
			}
			return true;
		}
		
		@Override
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			if(nbOperationReference != nbOperations) {
				throw new ConcurrentModificationException();
			}

			for(;!iterateurPlan.hasNext();){
				jour = iterateurJour.next();
				iterateurPlan = calendrier.get(jour).iterator();
	
			}
			return iterateurPlan.next();
		}
	}
	
	public int obtenirIndicePlan(T plan, GregorianCalendar jour) { 
		Iterator<T> it = calendrier.get(jour).iterator();
		T elt;
		for(int indice = 0; it.hasNext(); indice++) {
			elt = it.next();
			if(elt.equals(plan)) {
				return indice;
			}
		}
		return -1;
	}
	
	public void ajouterPlan(T plan) {

		Set<T> ensemblePlan;
		GregorianCalendar jour = plan.getDate();
		if(calendrier.containsKey(jour)) {
			ensemblePlan = calendrier.get(jour);
			ensemblePlan.add(plan);
		}
		else {
			ensemblePlan = new TreeSet<>();
			ensemblePlan.add(plan);
			calendrier.put(jour, ensemblePlan);
		}
		nbOperations++;
	}

	public boolean retirerPlan(T plan) {
		
		Set<T> ensemblePlan;
		GregorianCalendar jour = plan.getDate();
		
		if(calendrier.containsKey(jour)) {
			
			ensemblePlan = calendrier.get(jour);
			boolean present = ensemblePlan.remove(plan);
			
			if(present) {
				nbOperations++;
				if(ensemblePlan.isEmpty()) {
					calendrier.remove(jour);
				}
			}
			return present;
		}
		return false;
	}
	
	public Plan retirerPlan(GregorianCalendar jour, int indice) {
		
		T planSuppr = getPlan(jour, indice);
		if(planSuppr != null) {
			Set<T> ensemblePlan = calendrier.get(jour);
			ensemblePlan.remove(planSuppr);
			nbOperations++;
			if(ensemblePlan.isEmpty()) {
				calendrier.remove(jour);
			}
		}
		return planSuppr;
	}
	
	public T getPlan(GregorianCalendar jour, int numero) {
		if(numero >= 0) {
			T plan = null;
			
			if(calendrier.containsKey(jour)) {
				Set<T> ensemblePlan = calendrier.get(jour);
				Iterator<T> it = ensemblePlan.iterator();
				
				for (;it.hasNext() && numero >= 0; numero--) {
					plan = it.next();
				}
				if(numero < 0) {
					return plan;
				}
			}
		}
		return null;
	}
	
	public Set<T> planDeUnJour(GregorianCalendar jour) {
		if(calendrier.containsKey(jour)) {
			return calendrier.get(jour);
		} 
		return null;
	}
	 
	public int donnerNbPlan() {
		int compteur = 0;
		for(GregorianCalendar jour : calendrier.keySet()) {
			compteur += calendrier.get(jour).size();
		}
		return compteur;
	}
	
	public int donnerNbTacheETSousTache() {
		int compteur = 0;
		for(T plan : this) {
			if(plan.getClass() == Tache.class){
				compteur++;
				compteur += ((Tache) plan).getNbSousTache();
			}
		}
		return compteur;
	}
	
	public <E extends Plan> int donnerNombreElement(Class<E> classe){
		int compteur = 0;
		for(T plan : this) {
			if(plan.getClass() == classe){
				compteur++;
			}
		}
		return compteur;
	}
	
	public <E extends Plan> Set<E> donnerEnsembleElement(Class<E> classe){
		Set<E> ensemble = new HashSet<>();
		for(T plan : this) {
			if(plan.getClass() == classe){
				ensemble.add( (E) plan);
			}
		}
		return ensemble;
	}
	
	public <E extends Plan> Set<E> donnerEnsembleElementUnJour(GregorianCalendar jour, Class<E> classe){
		Set<E> ensemble = new HashSet<>();
		Set<T> plansJour = calendrier.get(jour);
		if(plansJour != null) {
			for(T plan : plansJour) {
				if(plan.getClass() == classe){
					ensemble.add( (E) plan);
				}
			}
		}
		return ensemble;
	}
	
	public Map<String, List<String>> obtenirNomTaches(GregorianCalendar jour){
		Map<String, List<String>> mapTaches = new HashMap<>();
		Set<Tache> plansJour = donnerEnsembleElementUnJour(jour, Tache.class);
		if(plansJour != null) {
		
			for(Tache plan : plansJour) {
				String heure = plan.getHeure().toString();
				List<String> nomSousTaches = plan.obtenirDescSousTaches();
				String nom;
				if(heure.equals(new KeyHorloge().toString())) {
					nom = plan.getNom();
				}
				else {
					nom = heure +" : " + plan.getNom();
				}
				mapTaches.put(nom, nomSousTaches);
			}
		}
		return mapTaches;
	}
	
	public Set<GregorianCalendar> donnerEnsembleJours(){
		return calendrier.keySet();
	}

	@Override
	public String toString() {
		StringBuilder presentation = new StringBuilder();
		for(Plan plan : this) {
			presentation.append(plan);
		}
		return presentation.toString();
	}


	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Calendrier<?>))
			return false;
		
		Calendrier<?> other = (Calendrier<?>) obj;
		return calendrier.equals(other.calendrier);
	}

	public void clear() {
		calendrier.clear();
		nbOperations++;
	}

	public boolean isEmpty() {
		return calendrier.isEmpty();
	}
}


