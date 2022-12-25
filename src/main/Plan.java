package main;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Plan {

	protected GregorianCalendar date = new GregorianCalendar();
	protected String nom;
	private String infoSup = "";
	protected SimpleDateFormat df = new SimpleDateFormat("EEEE d MMMM yyyy");

	public Plan(GregorianCalendar date, String nom) {
		this.date = date;
		this.nom = nom;
	}

	public Plan(GregorianCalendar date) {
		this.date = date;
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

	public GregorianCalendar getDate() {
		return date;
	}

	public void afficherPlan(int numDuPlan) {

	}

	public void afficherDateEtNom() {
		Display.display("Pour le " + df.format(date.getTime()) + " : " + nom + ".\n");
	}

	public void affichageComplet() {
		Display.display("Pour le " + df.format(date.getTime()) + " :\n");
		Display.display("Nom : " + nom);
		if (!"".equals(infoSup)) {
			Display.display("Informations supplémentaires : \n" + infoSup + "\n");
		} else {
			Display.display("Aucune information supplémentaire\n");
		}
	}

	public void afficherPlanComplet() {

	}

}
