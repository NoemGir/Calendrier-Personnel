package coeur.exterieur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.InitBoutique;
import coeur.boutique.Boutique;
import coeur.boutique.IBoutiqueSecondaire;
import coeur.boutique.secondaires.BoutiqueFlashCards;
import coeur.boutique.secondaires.BoutiqueLookAndFeel;
import coeur.jeux.FlashCards;

class Test_Serialisation {
	
	private FlashCards flashCard;
	private String[] carte0 = {"desc0", "rep0"};
	private String[] carte1 = {"desc1", "rep1"};

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		flashCard = new FlashCards();
		flashCard.ajouter(carte0);
		flashCard.ajouter(carte1);
	}

	@Test
	void test_serialisation_flashCard() {
		
		Serialisation serialisation = new Serialisation();
		serialisation.sauvegarder(flashCard, "test/test_flashcard.ser");
		FlashCards result = new FlashCards();
		assertNotEquals(flashCard, result);
		try {
			result = serialisation.recupererSauvegarde("test/test_flashcard.ser");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		assertEquals(flashCard, result);
	}
	
	@Test
	void test_serialisation_Boutique() {
		InitBoutique initBoutique = new InitBoutique();
		Boutique<IBoutiqueSecondaire> boutique = initBoutique.getBoutique();

		Serialisation serialisation = new Serialisation();
		serialisation.sauvegarder(boutique, "test/Boutique.ser");
		Boutique<IBoutiqueSecondaire> boutiquerecuperee = new Boutique<>();
			try {
			boutiquerecuperee = serialisation.recupererSauvegarde("test/Boutique.ser");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		assertEquals(boutique, boutiquerecuperee);
	}
	
	@Test
	void test_serialisation_BoutiqueLookAndFeel() {
		BoutiqueLookAndFeel boutiqueLookAndFeel = new BoutiqueLookAndFeel("Une boutique proposant des look and feel pour l'application !");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel Metal", "Un Look and feel sombre et metalique", "javax.swing.plaf.metal.MetalLookAndFeel");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel Nimbus", "Un Look and feel Nimbus ", "javax.swing.plaf.nimbus.NimbusLookAndFeel");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel CDE/Motif", "Un Look and feel CDE sct", "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel Windows", "Un Look and feel window", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel Windows Classic", "Un Look and feel Windows clasic", "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		

		Serialisation serialisation = new Serialisation();
		serialisation.sauvegarder(boutiqueLookAndFeel, "test/BoutiqueLookAndFeel.ser");
		BoutiqueLookAndFeel boutiquerecuperee = new BoutiqueLookAndFeel("desc");
			try {
			boutiquerecuperee = serialisation.recupererSauvegarde("test/BoutiqueLookAndFeel.ser");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		assertEquals(boutiqueLookAndFeel, boutiquerecuperee);
		assertEquals(boutiqueLookAndFeel.getProduits(), boutiquerecuperee.getProduits());
	}
	
	@Test
	void test_serialisation_BoutiqueFlashCard() {
		
		BoutiqueFlashCards boutiqueFlashCard = new BoutiqueFlashCards();
		boutiqueFlashCard.ajouterProduit(0, "prod 1", "desc 1", flashCard);
		boutiqueFlashCard.ajouterProduit(0, "prod 2", "desc 2", new FlashCards());

		Serialisation serialisation = new Serialisation();
		serialisation.sauvegarder(boutiqueFlashCard, "test/test_Boutiqueflashcard.ser");
		BoutiqueFlashCards boutiquerecuperee = new BoutiqueFlashCards();

		try {
			boutiquerecuperee = serialisation.recupererSauvegarde("test/test_Boutiqueflashcard.ser");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		assertEquals(boutiqueFlashCard, boutiquerecuperee);
		assertEquals(boutiqueFlashCard.recupererFlashCards("prod 1"), boutiquerecuperee.recupererFlashCards("prod 1"));
		assertEquals(boutiqueFlashCard.recupererFlashCards("prod 2"), boutiquerecuperee.recupererFlashCards("prod 2"));
	}

}
