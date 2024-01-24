package controleur;

import java.io.IOException;

import application.InitBoutique;
import coeur.DonneesApplication;
import coeur.UserData;
import coeur.boutique.Boutique;
import coeur.boutique.IBoutiqueSecondaire;
import coeur.exterieur.Serialisation;

public class ControlGestionDonnees {
	
	private Serialisation serialisation;
	private InitBoutique initBoutique;
	private UserData userData;
	private Boutique<IBoutiqueSecondaire> boutique;

	
	public ControlGestionDonnees(Serialisation serialisation,InitBoutique initBoutique) {
		this.serialisation = serialisation;
		this.initBoutique = initBoutique;
	}

	public void sauvegarderDonnees() {
		serialisation.sauvegarder(userData, DonneesApplication.FICHIER_USERDATA_SER);
		serialisation.sauvegarder(boutique, DonneesApplication.FICHIER_BOUTIQUE_SER);
	}
	
	public UserData recupererUserData() {
		try {
			userData = serialisation.recupererSauvegarde(DonneesApplication.FICHIER_USERDATA_SER);
		}
		catch(IOException | ClassNotFoundException e) {
			userData = new UserData();
		}
		return userData;

	}
	
	public Boutique<IBoutiqueSecondaire> recupererBoutique() {
		try {
			boutique = serialisation.recupererSauvegarde(DonneesApplication.FICHIER_BOUTIQUE_SER);
		}
		catch(IOException | ClassNotFoundException e) {
			boutique = initBoutique.getBoutique();
					
		}
		return boutique;
	}
}
