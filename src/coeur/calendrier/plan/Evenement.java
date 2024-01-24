package coeur.calendrier.plan;

import java.util.GregorianCalendar;

import coeur.calendrier.Horloge;

public class Evenement extends Plan {

	private static final long serialVersionUID = 23L;

	public Evenement(GregorianCalendar date, String nom, String infoSup, Horloge heure) {
		super(date, nom, infoSup, heure);
	}

	public Evenement(GregorianCalendar date, String nom) {
		super(date, nom);
	}

	public String presenterPlan(int numero) {
		StringBuilder presentation = new StringBuilder("Événement " + numero + " :\n");
		presentation.append(super.presentationSimple());
		return presentation.toString();
	}
	
	

	@Override
	public String toString() {
		StringBuilder presentation = new StringBuilder("Événement :\n");
		presentation.append(super.presentationSimple());
		return presentation.toString();
	}

	@Override
	public String affichageComplet() {
		StringBuilder presentation = new StringBuilder(" --- Événement ---\n");
		presentation.append(super.affichageComplet());
		return presentation.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if( o.getClass() == Evenement.class) {
			return super.equals(o);
		}
		return false;
	}

	@Override
	public int compareTo(Plan o) {
		int valO = numeroPlan.get(o.getClass());
		int valActuel = numeroPlan.get(Evenement.class);
		int comparaison = valActuel - valO;
		if(comparaison == 0) {
			 comparaison = super.compareTo(o);
		}
		return comparaison;

	}
}
