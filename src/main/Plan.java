package main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Plan {

	protected GregorianCalendar date = new GregorianCalendar();
	private String nom;
	private String infoSup = "";
	private SimpleDateFormat df = new SimpleDateFormat("EEEE d MMMM yyyy");

	public Plan(GregorianCalendar date, String nom, String infoSup) {
		this.date = date;
		this.nom = nom;
		this.infoSup = infoSup;
		setUpDate();
	}

	public Plan(GregorianCalendar date) {
		this.date = date;
		setUpDate();
	}

	private void setUpDate() {
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
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

	protected void afficherDateEtNom() {
		Display.display("Pour le " + df.format(date.getTime()) + " : " + nom + ".\n");
	}

	protected void affichageComplet() {
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
