package GestionTache;

import java.text.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Tache {
	
	private GregorianCalendar date = new GregorianCalendar();
	private String nom;
	private int nbSousTache = 0;
	private Tache[] sousTaches = new Tache[20];
	private String infoSup = "";
	private boolean decalage = false;
	private int tempsDeRealisation = 0;
	private boolean fini = false;
	
	
	public Tache(int annee, int mois, int jour, String nom) {
		this.date.set(annee, mois, jour);
		this.nom = nom;
		assert (date.after(Calendar.getInstance()) || date.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	

	public String getNom() {
		return nom;
	}


	public GregorianCalendar getDate() {
		return date;
	}

	public void setFini(boolean fini) {
		this.fini = fini;
	}

	public boolean isFini() {
		return fini;
	}
	
	public void afficherTache() {
		SimpleDateFormat df = new SimpleDateFormat("EEEE d MMMM yyyy");
		System.out.println( "Pour le " + df.format(this.date.getTime()) + " : " + nom + ".");
	}
	
}
