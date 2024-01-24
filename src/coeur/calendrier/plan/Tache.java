package coeur.calendrier.plan;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import coeur.calendrier.Horloge;
import coeur.calendrier.KeyHorloge;

public class Tache extends Plan {
	
	private static final long serialVersionUID = 20L;
	protected Set<SousTache> sousTaches = new TreeSet<>();
	private boolean accomplie = false;
	
	public Tache(GregorianCalendar date, String nom, String infoSup, Horloge heure, boolean accomplie) {
		super(date, nom, infoSup, heure);
		this.accomplie = accomplie;
	}

	public Tache(GregorianCalendar date, String nom, String infoSup, Horloge heure) {
		super(date, nom, infoSup, heure);
	}


	public Tache(GregorianCalendar date, String nom) {
		super(date, nom);
	}
	
	
	private static class SousTache extends Plan{
		private static final long serialVersionUID = 22L;
		private boolean accomplie;

		public SousTache(GregorianCalendar date, String nom, String infoSup, Horloge heure, boolean accomplie) {
			super(date, nom, infoSup, heure);
			this.accomplie = accomplie;
		}
		
		public SousTache(GregorianCalendar date, String nom, String infoSup, Horloge heure) {
			super(date, nom, infoSup, heure);
			 accomplie = false;
		}

		public SousTache(GregorianCalendar date,String nom) {
			super(date, nom);
			 accomplie = false;
		}
		
		public void terminerSousTache(){
			accomplie = true;
		}
		
		public String indicationSiSousTacheTerminee() {
			if (accomplie) {
				return "(Terminée)";
			} else {
				return "(Non Terminée)";
			}
		}
		
		@Override
		public String toString() {
			return super.toString() + "," + accomplie ;
		}
		
		public String presenterPlan(int numero) {
			StringBuilder presentation = new StringBuilder(" -- Sous-Tâche " + (numero +1) + " : " + indicationSiSousTacheTerminee() + "\n");
			presentation.append(super.presentationSimple());
			return presentation.toString();
		}

		@Override
		public String affichageComplet() {
			StringBuilder presentation = new StringBuilder(" --- Sous-Tâche " + indicationSiSousTacheTerminee() + " ---\n");
			presentation.append(super.affichageComplet());
			return presentation.toString();
		}

		
		@Override 
		public boolean equals (Object o) {
			if( o.getClass() == SousTache.class) {
				return super.equals(o);
			}
			return false;
		}

		@Override
		public int compareTo(Plan o) {
			if( o.getClass() == SousTache.class) {
				return super.compareTo(o);
			}
			return 3;
		}
	}
	
	private SousTache obtenirSousTache(int numero) {
		if(numero >= 0) {
			SousTache sousTache = null;
			Iterator<SousTache> it = sousTaches.iterator();
			for (;it.hasNext() && numero >= 0; numero--) {
				sousTache = it.next();
			}
			if(numero < 0) {
				return sousTache;
			}
		}
		return null;
	}

	public void terminerTache() {
		accomplie = true;
		
		if (!sousTaches.isEmpty()) {
			for (SousTache sousTache : sousTaches) {
				sousTache.terminerSousTache();
			}
		}
	}

	public void terminerSousTache(int numero) {
		SousTache sousTache = obtenirSousTache(numero);
			if(sousTache  != null) {
				sousTache.terminerSousTache();
			}
	}
	
	public void ajouterSousTache( String nom, String infoSup, Horloge heure, boolean accomplie) {
		SousTache nouvelleSousTache = new SousTache(getDate(), nom,infoSup,heure, accomplie);
		sousTaches.add(nouvelleSousTache);
	}
	
	public void ajouterSousTache( String nom, String infoSup, Horloge heure) {
		SousTache nouvelleSousTache = new SousTache(getDate(), nom,infoSup,heure);
		sousTaches.add(nouvelleSousTache);
	}
	
	public void ajouterSousTache( String nom) {
		SousTache nouvelleSousTache = new SousTache(getDate(), nom);
		sousTaches.add(nouvelleSousTache);
	}

	public SousTache supprimerSousTache(int numSousTache) {
		SousTache sousTache = obtenirSousTache(numSousTache);
		if(sousTache  != null) {
			sousTaches.remove(sousTache);
		}
		return sousTache;
	}
	
	public String presentationSousTache(int numero) {
		SousTache sousTache = obtenirSousTache(numero);
		if(sousTache  != null) {
			return sousTache.presenterPlan(numero);
		}
		return null;
	}
	
	public String presentationCompleteSousTache(int numero) {
		SousTache sousTache = obtenirSousTache(numero);
		if(sousTache  != null) {
			return sousTache.affichageComplet();
		}
		return null;
	}
	
	private String presentationSousTaches() {
		StringBuilder presentation = new StringBuilder();
		int nbSousTache = sousTaches.size();
		if (nbSousTache > 0) {
			presentation.append("\nCette tâche possède " + nbSousTache + " sous-tâches :\n\n");
			int compteur = 0;
			for (SousTache sousTache : sousTaches) {
				presentation.append(sousTache.presenterPlan(compteur));
				compteur ++;
			}
		} else {
			presentation.append("Aucune sous-tâche enregistrée\n");
		}
		return presentation.toString();
	}
	
	public String getNomSousTache(int numero) {
		SousTache sousTache = obtenirSousTache(numero);
		if(sousTache  != null) {
			return sousTache.getNom();
		}
		return "";
	}
	
	public Horloge getHeureSousTache(int numero) {
		SousTache sousTache = obtenirSousTache(numero);
		if(sousTache  != null) {
			return sousTache.getHeure();
		}
		return new KeyHorloge();
	}
	
	
	public String indicationSiSousTacheTerminee(int numero) {
		SousTache sousTache = obtenirSousTache(numero);
		if(sousTache  != null) {
			return sousTache.indicationSiSousTacheTerminee();
		}
		return "";
	}
	
	
	public String getInfosSupSousTache(int numero) {
		SousTache sousTache = obtenirSousTache(numero);
		if(sousTache  != null) {
			return sousTache.getInfoSup();
		}
		return "";
	}
	
	public void setNomSousTache(int numero, String nom) {
		SousTache sousTache = obtenirSousTache(numero);
		if(sousTache  != null) {
			sousTache.setNom(nom);
		}
	}
	
	public void setInfoSupSousTache(int numero, String infosSup) {
		SousTache sousTache = obtenirSousTache(numero);
		if(sousTache  != null) {
			sousTache.setInfoSup(infosSup);
		}		
	}
	
	public int obtenirIndiceSousTache(SousTache sousTache) {
		Iterator<SousTache> it = sousTaches.iterator();
		for(int indice = 0; it.hasNext(); indice++) {
			if(it.next().equals(sousTache)) {
				return indice;
			}
		}
		return -1;
	}
	
	public int setHeureSousTache(int numero, Horloge heure) {
		SousTache sousTache = obtenirSousTache(numero);
		int nouvelIndice = -1;
		if(sousTache  != null) {
			sousTaches.remove(sousTache);
			sousTache.setHeure(heure);
			sousTaches.add(sousTache);
			nouvelIndice = obtenirIndiceSousTache(sousTache);
		}	
		return nouvelIndice;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o!= null && o.getClass() == Tache.class) {
			Tache tache = (Tache) o;
			return super.equals(tache) && tache.sousTaches.equals(sousTaches) ;
		}
		return false;
	}
	

	@Override
	public int compareTo(Plan o) {
		int valO = numeroPlan.get(o.getClass());
		int valActuel = numeroPlan.get(Tache.class);
		int comparaison = valActuel - valO;
		if(comparaison == 0) {
			Tache tache = (Tache) o;
			comparaison = super.compareTo(tache);
			if(comparaison == 0) {
				if(sousTaches.equals(tache.sousTaches)) {
					comparaison = 0;
				}
				else {
					comparaison = 1;
				}
			}
		}
		return comparaison;
	}
	

	@Override
	public void setDate(GregorianCalendar nouvelleDate) {
		super.setDate(nouvelleDate);
		for (SousTache sousTache : sousTaches) {
			sousTache.setDate(nouvelleDate);
		}
	}
	
	@Override
	public String presenterPlan(int numero) {
		StringBuilder presentation = new StringBuilder("Tâche " + numero + " : " + indicationSiTacheTerminee() + "\n");
		presentation.append(super.presentationSimple());
		presentation.append(presentationSousTaches());
		return presentation.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder presentation = new StringBuilder("Tâche : " + indicationSiTacheTerminee() + "\n");
		presentation.append(super.presentationSimple());
		presentation.append(presentationSousTaches());
		return presentation.toString();
	}

	@Override
	public String affichageComplet() {
		StringBuilder presentation = new StringBuilder(" --- Tâche " + indicationSiTacheTerminee() + " ---\n");
		presentation.append(super.affichageComplet());
		presentation.append(presentationSousTaches());
		return presentation.toString();
	}

	public String indicationSiTacheTerminee() {
		if (accomplie) {
			return "(Terminée)";
		} else {
			return "(Non Terminée)";
		}
	}

	public int getNbSousTache() {
		return sousTaches.size();
	}

	public Set<SousTache> getSousTaches() {
		return sousTaches;
	}

	public boolean isAccomplie() {
		return accomplie;
	}
	
	public void setAccomplie(boolean accomplie) {
		this.accomplie = accomplie;
	}

	public List<String> obtenirDescSousTaches() {
		List<String> descriptions = new ArrayList<>();
		for(SousTache sousTache : sousTaches) {
			descriptions.add(sousTache.getNom() + " " + sousTache.indicationSiSousTacheTerminee());
		}
		return descriptions;
	}
}
