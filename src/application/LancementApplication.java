package application;

import java.util.GregorianCalendar;

import coeur.DonneesApplication;
import coeur.UserData;
import coeur.boutique.Boutique;
import coeur.boutique.IBoutiqueSecondaire;
import coeur.exterieur.CommandeShell;
import coeur.exterieur.ExporteurFichier;
import coeur.exterieur.ImporteurFichier;
import coeur.exterieur.Serialisation;
import controleur.ControlAcheterProduit;
import controleur.ControlActiverProduit;
import controleur.ControlAfficherBoutique;
import controleur.ControlAfficherCalendrier;
import controleur.ControlAfficherInfosPerso;
import controleur.ControlCommunicationOcaml;
import controleur.ControlCreerPlan;
import controleur.ControlDemanderJour;
import controleur.ControlGestionDonnees;
import controleur.ControlModifierPlan;
import controleur.ControlModifierProduit;
import controleur.ControlModifierSousTache;
import controleur.ControlVerificationBoutique;
import controleur.ControlVerificationPlanSelectionne;
import frontiere.terminal.BoundaryAfficherCalendrier;
import frontiere.terminal.BoundaryAfficherInfosPerso;
import frontiere.terminal.BoundaryAfficherUnJour;
import frontiere.terminal.BoundaryApplication;
import frontiere.terminal.BoundaryBoutique;
import frontiere.terminal.BoundaryBoutiquePrincipale;
import frontiere.terminal.BoundaryCreerPlan;
import frontiere.terminal.BoundaryDemanderJour;
import frontiere.terminal.BoundaryModifierPlan;
import frontiere.terminal.BoundaryModifierProduit;
import frontiere.terminal.BoundaryModifierSousTache;

public class LancementApplication {


	public static void main(String[] args) {
		GregorianCalendar jourActuel = (GregorianCalendar) DonneesApplication.JOUR_ACTUEL.clone();
		InitBoutique initBoutique = new InitBoutique();
		
		CommandeShell commandeShell = new CommandeShell();
		ExporteurFichier exporteur = new ExporteurFichier();
		ImporteurFichier importeur = new ImporteurFichier();
		Serialisation serialisation = new Serialisation();
		
		ControlGestionDonnees controlGestionDonnees = new ControlGestionDonnees(serialisation, initBoutique);
		
		UserData userData = controlGestionDonnees.recupererUserData();
		Boutique<IBoutiqueSecondaire> boutique = controlGestionDonnees.recupererBoutique();
		
		//initialisation fonction exterieur
		ControlActiverProduit controlActiverProduit = new ControlActiverProduit( userData);
		ControlCommunicationOcaml controlCommunicationOcaml= new ControlCommunicationOcaml(commandeShell, exporteur, importeur, userData);
		ControlVerificationBoutique controlVerificationBoutique = new ControlVerificationBoutique(boutique, userData);
		ControlModifierProduit controlModifierProduit = new ControlModifierProduit(boutique, userData);
		ControlAfficherBoutique controlAfficherBoutique = new ControlAfficherBoutique(boutique, userData);
		ControlAcheterProduit controlAcheterProduit = new ControlAcheterProduit(boutique, userData);
		ControlAfficherCalendrier controlAfficherCalendrier = new ControlAfficherCalendrier(userData);
		ControlAfficherInfosPerso controlAfficherInfosPerso = new ControlAfficherInfosPerso(userData);
		ControlCreerPlan controlCreerPlan = new ControlCreerPlan(userData);
		ControlDemanderJour controlDemanderJour = new ControlDemanderJour();
		ControlModifierPlan controlModifierPlan = new ControlModifierPlan(userData);
		ControlModifierSousTache controlModifierSousTache = new ControlModifierSousTache(userData);
		ControlVerificationPlanSelectionne controlVerificationPlanSelectionne = new ControlVerificationPlanSelectionne(
				userData);

		
		BoundaryAfficherCalendrier boundaryAfficherCalendrier = new BoundaryAfficherCalendrier(
				controlAfficherCalendrier);
		BoundaryDemanderJour boundaryDemanderJour = new BoundaryDemanderJour(controlDemanderJour);
		BoundaryAfficherInfosPerso boundaryAfficherInfosPerso = new BoundaryAfficherInfosPerso(
				controlAfficherInfosPerso, controlGestionDonnees, boundaryAfficherCalendrier);
		BoundaryCreerPlan boundaryCreerPlan = new BoundaryCreerPlan(controlCreerPlan, boundaryDemanderJour);

		BoundaryModifierSousTache boundaryModifierSousTache = new BoundaryModifierSousTache(boundaryAfficherCalendrier,
				controlModifierSousTache, controlGestionDonnees, boundaryDemanderJour);
		
		BoundaryModifierPlan boundaryModifierPlan = new BoundaryModifierPlan(controlVerificationPlanSelectionne,
				controlModifierPlan, controlGestionDonnees, boundaryDemanderJour, boundaryAfficherCalendrier, boundaryModifierSousTache);

		BoundaryAfficherUnJour boundaryAfficherUnJour = new BoundaryAfficherUnJour(boundaryDemanderJour,
				boundaryCreerPlan, boundaryModifierPlan, controlGestionDonnees, controlVerificationPlanSelectionne,
				boundaryAfficherCalendrier);
		BoundaryModifierProduit BoundaryModifierProduit = new BoundaryModifierProduit(controlAfficherBoutique, controlModifierProduit, controlGestionDonnees, controlActiverProduit);
		BoundaryBoutique boundaryBoutique = new BoundaryBoutique(controlAfficherBoutique, controlGestionDonnees, controlAcheterProduit, controlVerificationBoutique, controlAfficherInfosPerso, boundaryAfficherInfosPerso, BoundaryModifierProduit);
		BoundaryBoutiquePrincipale boundaryBoutiquePrincipale = new BoundaryBoutiquePrincipale(controlAfficherBoutique, controlAfficherInfosPerso, controlGestionDonnees, controlVerificationBoutique, boundaryAfficherInfosPerso, boundaryBoutique);

		BoundaryApplication boundaryApplication = new BoundaryApplication(boundaryAfficherCalendrier,
				controlGestionDonnees, boundaryDemanderJour, boundaryAfficherInfosPerso, boundaryAfficherUnJour, boundaryBoutiquePrincipale);
		
		controlCommunicationOcaml.mettreAJour();

		boundaryApplication.displayMainMenu(jourActuel);

	}

}
