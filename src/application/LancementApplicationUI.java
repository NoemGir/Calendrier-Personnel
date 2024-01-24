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
import controleur.ControlJouerFlashCards;
import controleur.ControlModifierPlan;
import controleur.ControlModifierProduit;
import controleur.ControlModifierSousTache;
import frontiere.gui.BoutiqueUI;
import frontiere.gui.FlashCardUI;
import frontiere.gui.InfosPersoUI;
import frontiere.gui.MenuPrincipalUI;
import frontiere.gui.MenuUnJourUI;
import frontiere.gui.ModifierPlanUI;
import frontiere.gui.TachesPerduesUI;

public class LancementApplicationUI {

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
		
		ControlCommunicationOcaml controlCommunicationOcaml= new ControlCommunicationOcaml(commandeShell, exporteur, importeur, userData);
		ControlActiverProduit controlActiverProduit = new ControlActiverProduit(userData);
		ControlModifierProduit controlModifierProduit = new ControlModifierProduit(boutique, userData);
		ControlAfficherBoutique controlAfficherBoutique = new ControlAfficherBoutique(boutique, userData);
		ControlAcheterProduit controlAcheterProduit = new ControlAcheterProduit(boutique, userData);
		ControlAfficherCalendrier controlAfficherCalendrier = new ControlAfficherCalendrier(userData);
		ControlAfficherInfosPerso controlAfficherInfosPerso = new ControlAfficherInfosPerso(userData);
		ControlCreerPlan controlCreerPlan = new ControlCreerPlan(userData);
		ControlDemanderJour controlDemanderJour = new ControlDemanderJour();
		ControlModifierPlan controlModifierPlan = new ControlModifierPlan(userData);
		ControlModifierSousTache controlModifierSousTache = new ControlModifierSousTache(userData);
		ControlJouerFlashCards controlJouerFlashCards = new ControlJouerFlashCards(userData);
		
		FlashCardUI  flashCardUI = new FlashCardUI(controlJouerFlashCards, controlAfficherInfosPerso);
		InfosPersoUI infoPersoUI = new InfosPersoUI(controlAfficherInfosPerso, controlAfficherCalendrier, controlModifierProduit, controlActiverProduit, flashCardUI);
		BoutiqueUI boutiqueUI = new BoutiqueUI(controlAcheterProduit, infoPersoUI, controlAfficherBoutique, controlAfficherInfosPerso);
		ModifierPlanUI modifierPlanUI = new ModifierPlanUI(controlDemanderJour, controlAfficherCalendrier, controlModifierPlan, controlModifierSousTache);
		MenuUnJourUI menuUnJourUI = new MenuUnJourUI(controlDemanderJour, controlAfficherCalendrier, controlCreerPlan, controlModifierPlan, modifierPlanUI);
		TachesPerduesUI tachesPerduesUI = new TachesPerduesUI(controlCommunicationOcaml, controlDemanderJour);
		MenuPrincipalUI menuPrincipalUI = new MenuPrincipalUI(controlDemanderJour, menuUnJourUI, infoPersoUI, boutiqueUI, controlGestionDonnees, controlCommunicationOcaml, tachesPerduesUI);		
		
		controlCommunicationOcaml.mettreAJour();
		controlActiverProduit.lancerActivationTousLesProduits();
		
		menuPrincipalUI.lancerGUI(jourActuel);
	}
}
