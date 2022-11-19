package GestionTache;

import java.util.Calendar;

public class Action {
	
	public static void main(String[] args) {
		Tache tache1 = new Tache(2022, 10, 19, "tache1");
		Tache tache2 = new Tache(2023, 10, 19, "tache2");
		Tache tache3 = new Tache(2022, 10, 20, "tache3");
		Tache tache4 = new Tache(2022, 10, 19, "tache4");
		//Tache tache5 = new Tache(2022, 10, 10, "tache5"); ERREUR
	
		Utilisateur toto = new Utilisateur("toto.id", "12345", "Toto");
		
		toto.ajoutTachesAFaire(tache1);
		toto.ajoutTachesAFaire(tache2);
		toto.ajoutTachesAFaire(tache3);
		toto.ajoutTachesAFaire(tache4);
		toto.voirTachesAFaire();
		toto.finirTache(tache4);
		toto.finirTache(tache1);
		toto.finirTache(tache4);
		toto.voirTachesAFaire();
		toto.voirTrophees();
		toto.afficherMoney();
	}
}
