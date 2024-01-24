package frontiere.terminal;

import controleur.ControlDemanderJour;

public class BoundaryDemanderJour {
	private ControlDemanderJour controlDemanderJour;
	
	
	public BoundaryDemanderJour(ControlDemanderJour controlDemanderJour) {
		this.controlDemanderJour = controlDemanderJour;
	}

	public int[] demanderUnJour() {

		int[] journee = new int[3];
		int annee;
		int mois;
		int jour;

		do {
			annee = Clavier.entrerEntier("Donnez le nombre de l'année : ");
		} while (Boolean.FALSE.equals(controlDemanderJour.anneeCorrect(annee)));

		do {
			mois = Clavier.entrerEntier("Donnez le numero du mois entre 1 et 12 : ")-1;
		} while (Boolean.FALSE.equals(controlDemanderJour.moisCorrect(mois, annee)));
		do {
			jour = Clavier.entrerEntier("Donnez le nombre du jour ( -1 si retour au Menu) : ");

		} while (Boolean.FALSE.equals(controlDemanderJour.jourCorrect(annee, mois, jour)));
		journee[0] = annee;
		journee[1] = mois;
		journee[2] = jour;
		return journee;
		
	}
	

	public int[] demanderHeure() {
		int heureDonnee;
		int minutesDonnees;
		int[] horloge = null; 
		do {
			heureDonnee = Clavier.entrerEntier("Donner l'heure entre 0 et 23 inclus (-1 si retour arrière): ");
		} while( heureDonnee != -1 && controlDemanderJour.heureIncorrect(heureDonnee, 0));
		if(heureDonnee != -1) {
			do {
				minutesDonnees = Clavier.entrerEntier("Donner les minutes entre 0 et 23 inclus (-1 si retour arrière): ");
			} while(minutesDonnees != -1 && controlDemanderJour.heureIncorrect(heureDonnee, minutesDonnees));
			
			if (minutesDonnees != -1) {
				horloge = new int[2];
				horloge[0] = heureDonnee;
				horloge[1] = minutesDonnees; 
			}
		}
		return horloge;
	}
}
