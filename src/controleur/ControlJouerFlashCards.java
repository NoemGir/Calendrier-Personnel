package controleur;

import java.util.ListIterator;

import coeur.UserData;
import coeur.boutique.secondaires.BoutiqueFlashCards;
import coeur.jeux.FlashCards;
import coeur.jeux.FlashCards.Cote;

public class ControlJouerFlashCards {
	
	private UserData userData;
	private FlashCards flashCards;

	public ControlJouerFlashCards(UserData userData) {
		this.userData = userData;
	}
	
	public void recupererFlashCards(String nomFlashCards) {
		BoutiqueFlashCards boutique = (BoutiqueFlashCards) userData.getUserBoutique().recupererBoutique(new BoutiqueFlashCards("").getNom());
		if(boutique != null) {
			flashCards = boutique.recupererFlashCards(nomFlashCards);
		}
	}
	
	public void activerModeAleatoire() {
		flashCards.setMode(Cote.ALEATOIRE);
	}
	
	public void activerModeDescription() {
		flashCards.setMode(Cote.DESCRIPTION);
	}
	
	
	public void activerModeReponse() {
		flashCards.setMode(Cote.REPONSE);
	}
	
	public boolean estVide() {
		return !flashCards.getItFlashCards().hasNext();
	}
	
	public String autreCote() {
		return flashCards.changerCote();
	}

	public String carteSuivante() {
		return flashCards.donnerCarteSuivante();
	}
	public String cartePrecedente() {
		return flashCards.donnerCartePrecedente();
	}
	
	public void ajouterCarte(String[] carte) {
		flashCards.ajouter(carte);
	}
	
	public boolean retirerCarteInput(String input) {
		return flashCards.retirer(input);
	}
	
	public void retirerCarte() {
		ListIterator<String[]> it = flashCards.getItFlashCards();
		it.remove();
	}

	public void setCarte(String[] carte) {
		ListIterator<String[]> it = flashCards.getItFlashCards();
		it.set(carte);		
	}
}
