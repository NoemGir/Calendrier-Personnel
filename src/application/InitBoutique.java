package application;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;

import coeur.boutique.Boutique;
import coeur.boutique.IBoutiqueSecondaire;
import coeur.boutique.secondaires.BoutiqueDeBoutiques;
import coeur.boutique.secondaires.BoutiqueFlashCards;
import coeur.boutique.secondaires.BoutiqueGUI;
import coeur.boutique.secondaires.BoutiqueLookAndFeel;
import coeur.jeux.FlashCards;

public class InitBoutique {
	
	private Boutique<IBoutiqueSecondaire> boutique = new Boutique<>();

	public InitBoutique() {
		initBoutique();
	}
	
	private void initBoutique() {
		
		BoutiqueDeBoutiques boutiqueBoutique = new BoutiqueDeBoutiques("Une boutique servant à débloquer d'autres boutiques !!");
		BoutiqueGUI boutiqueGUI = new BoutiqueGUI("Une boutique proposant des produits pouvant modifier l'interface GUI !!");
		BoutiqueLookAndFeel boutiqueLookAndFeel = new BoutiqueLookAndFeel("Une boutique proposant des look and feel pour l'application !");
		BoutiqueFlashCards boutiqueFlashCards = new BoutiqueFlashCards("Une boutique donnant accès à des jeux de FlashCards !");

		JButton button = new JButton();
		button.setName("Bouton");
		JTextField jTextField= new JTextField();
		jTextField.setName("TextField");
		JTextArea jTextArea = new JTextArea();
		jTextArea.setName("TextArea");
		JLabel jLabel = new JLabel();
		jLabel.setName("Label");
		JPanel jPanel = new JPanel();
		jPanel.setName("Panel");
		JToggleButton jToggleButton = new JToggleButton();
		jToggleButton.setName("ToggleButton");
		JTextPane jTextPane = new JTextPane();
		jTextPane.setName("TextPane");

		boutiqueGUI.ajouterProduit(2, "Personnalisation boutons", "Vous pouvez choisir la couleur du background et foreground des boutons !", button,true, true );
		boutiqueGUI.ajouterProduit(2, "Personnalisation textFields", "Vous pouvez choisir la couleur du background et foreground des champs de texte !", jTextField,true, true );
		boutiqueGUI.ajouterProduit(2, "Personnalisation textAreas", "Vous pouvez choisir la couleur du background et foreground des zones de textes !", jTextArea,true, true );
		boutiqueGUI.ajouterProduit(2, "Personnalisation labels", "Vous pouvez choisir la couleur du foreground des titres !", jLabel,false, true );
		boutiqueGUI.ajouterProduit(5, "Personnalisation panels", "Vous pouvez choisir la couleur du background des panels !", jPanel, true, false );
		boutiqueGUI.ajouterProduit(2, "Personnalisation toggleButton", "Vous pouvez choisir la couleur du foreground des togglesButtons !", jToggleButton, false, true );
		boutiqueGUI.ajouterProduit(2, "Personnalisation textPane", "Vous pouvez choisir la couleur du foreground des togglesButtons !", jToggleButton, false, true );

		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel Metal", "Un Look and feel sombre et metalique", "javax.swing.plaf.metal.MetalLookAndFeel");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel Nimbus", "Un Look and feel Nimbus ", "javax.swing.plaf.nimbus.NimbusLookAndFeel");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel CDE/Motif", "Un Look and feel CDE sct", "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel Windows", "Un Look and feel window", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		boutiqueLookAndFeel.ajouterProduit(5, "Look And Feel Windows Classic", "Un Look and feel Windows clasic", "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		
		
		boutiqueFlashCards.ajouterProduit(3, "flashcards 1", "Un jeu de flashcard", new FlashCards());
		boutiqueFlashCards.ajouterProduit(3, "flashcards 2", "Un jeu de flashcard", new FlashCards());
		boutiqueFlashCards.ajouterProduit(3, "flashcards 3", "Un jeu de flashcard", new FlashCards());
		boutiqueFlashCards.ajouterProduit(3, "flashcards 4", "Un jeu de flashcard", new FlashCards());
		boutiqueFlashCards.ajouterProduit(3, "flashcards 5", "Un jeu de flashcard", new FlashCards());
		
		boutiqueBoutique.ajouterProduit(boutiqueFlashCards);
		boutiqueBoutique.ajouterProduit(boutiqueLookAndFeel);
		boutiqueBoutique.ajouterProduit(boutiqueGUI);
		boutique.ajouterBoutique(boutiqueBoutique);
	}

	public Boutique<IBoutiqueSecondaire> getBoutique() {
		return boutique;
	}
	
	
}
