package coeur.calendrier.plan;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashMap;

import coeur.DonneesApplication;
import coeur.calendrier.Horloge;
import coeur.calendrier.KeyHorloge;
import utils.Utils;

public abstract class Plan implements Comparable<Plan>, Serializable{
	
	private static final long serialVersionUID = 20L;
	private GregorianCalendar date = new GregorianCalendar();
	private String nom;
	private String infoSup;
	private Horloge heure;
	
	protected static HashMap<Class<? extends Plan>, Integer> numeroPlan = new HashMap<>();
	
	static {
		numeroPlan.put(Evenement.class, 1);
		numeroPlan.put(Tache.class, 2);
	}

	protected Plan(GregorianCalendar date, String nom, String infoSup, Horloge heure) {
		this.date = date;
		this.nom = nom;
		this.infoSup = infoSup;
		this.heure = heure;
		Utils.setUpDate(date);
	}

	protected Plan(GregorianCalendar date, String nom) {
		this.date = date;
		this.nom = nom;
		infoSup = "";
		heure = new KeyHorloge();
		Utils.setUpDate(date);
	}

	@Override
	public boolean equals(Object o) {
		if( o instanceof Plan plan) {
			return (Utils.comparerGregorianCalendar(plan.date, date) && plan.nom.equals(nom) && plan.infoSup.equals(infoSup) && plan.heure.equals(heure) );
		}
		return false;
	}
	
	@Override
	public int compareTo(Plan p) {
		int comparaison = date.compareTo(p.date);
		if(comparaison == 0) {
			comparaison = heure.compareTo(p.heure);
			if(comparaison == 0) {
				comparaison = nom.compareTo(p.nom);
				if(comparaison == 0) {
					comparaison = infoSup.compareTo(p.infoSup);
				}
			}
		}
		return comparaison;
	}
	
	public String dateEtHeure(){
		if(heure.equals(new KeyHorloge())) {
			return "Pour le " + DonneesApplication.DF.format(date.getTime());
		}
		else {
			return "Pour le " + DonneesApplication.DF.format(date.getTime()) + " à " + heure;

		}
	}
	
	

	@Override
	public String toString() {
		return nom + "," + infoSup + "," + heure ;
	}

	public String presentationSimple() {
			return dateEtHeure() + " : " + nom + ".\n";
	}
	
	public abstract String presenterPlan(int numero);
	

	public String affichageComplet() {
		StringBuilder texte = new StringBuilder(dateEtHeure() + " :\n");
		texte.append("Nom : " + nom + "\n");
		if (!"".equals(infoSup)) {
			texte.append("Informations supplémentaires : \n" + infoSup + "\n");
		} else {
			texte.append("Aucune information supplémentaire\n");
		}
		return texte.toString();
	}

	public String getNom() {
		return nom;
	}

	public String getInfoSup() {
		return infoSup;
	}

	public void setInfoSup(String infoSup) {
		this.infoSup = infoSup;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setHeure(Horloge heure) {
		this.heure = heure;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public Horloge getHeure() {
		return heure;
	}

	
}
