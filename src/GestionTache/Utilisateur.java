package GestionTache;
import java.util.*;

import GestionBoutique.Pet;


public class Utilisateur {
	
	private String identifiant;
	private String surname;
	private String mdp;
	private int nbTacheAFaire = 0;
	private int nbTrophees = 0;
	private Tache[] tachesAFaire = new Tache[10];
	private Tache[] trophees = new Tache[100];
	private int stars = 0;
	private Pet[] pets = new Pet[50];
	private int nbTacheRealise = 0;
	private int tempsDeTravail = 0;
	
	
	public Utilisateur(String identifiant, String mdp, String surname) {
		this.identifiant = identifiant;
		this.mdp = mdp;
		this.surname = surname;
	}
	
	public void messageSystem(String texte) {
		System.out.println("Utilisateur " + surname + " : " + texte);
	}

	public void ajoutTachesAFaire(Tache tache) {
		
		if (!tache.isFini()) {
			if (nbTacheAFaire == 0) {
				tachesAFaire[0] = tache;
			}
			else {
				int i = nbTacheAFaire;
				while (tache.getDate().before(tachesAFaire[i-1].getDate())) {
					tachesAFaire[i] = tachesAFaire[i-1];
					i--;
				}
				tachesAFaire[i] = tache;
			}
			nbTacheAFaire++;
		}
		else {
			messageSystem("La tache :" + tache.getNom() + " est deja fini");
		}

	}

	public void retirerTachesAFaire(Tache tache) {

		List<Tache> list = Arrays.asList(tachesAFaire);
		if (list.contains(tache)) {
			int i = 0;
			while(tache != tachesAFaire[i]) {
				i++;
			}
			nbTacheAFaire--;
			for (int j = i; j<nbTacheAFaire; j++) {
				tachesAFaire[j]=tachesAFaire[j+1];
			}
		}
		else {
			messageSystem("La tache :" + tache.getNom() + " n'est pas dans le tableau");
		}
	}
	
	private void ajouterTrophee(Tache tache) {
		if (tache.isFini()) {
			trophees[nbTrophees] = tache;
			nbTrophees++;
		}
		else {
			messageSystem("La tache : " + tache.getNom() + " n'a pas été accomplie !");
		}
	}
	
	
	public void finirTache(Tache tache) {
		if (!tache.isFini()) {
			tache.setFini(true);
			retirerTachesAFaire(tache);
			ajouterTrophee(tache);
			messageSystem("Félicitation ! Vous avez accomplie une nouvelle tache ! *Musique*");
			stars += 10 ;
		}
		else {
			messageSystem("La tache : " + tache.getNom() + " a deja été accomplie !");
		}
	}
	
	public void afficherTableau(Tache[] tableau, int nbElement, String texte ) {
		int i;
		messageSystem(texte);
		for (i=0; i<nbElement; i++) {
			tableau[i].afficherTache();
		}
	}
	
	public void voirTachesAFaire() {
			afficherTableau(tachesAFaire, nbTacheAFaire, "Listes des taches a faire :");
		}
	
	public void voirTrophees() {
		afficherTableau(trophees, nbTrophees, "La liste des trophees est : ");
	}
	
	public void afficherMoney() {
		messageSystem("Vous avez recolter : " + stars + " Stars ! Félicitaton !");
	}
	
}






