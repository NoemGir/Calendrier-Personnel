package coeur.boutique;

import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import coeur.boutique.secondaires.BoutiqueDeBoutiques;
import coeur.boutique.secondaires.BoutiqueGUI;

class Test_Boutique {
	
	private static Boutique<IBoutiqueSecondaire> boutique = new Boutique<>();

	@BeforeAll
	static void setUpBeforeClass() {
		BoutiqueDeBoutiques boutiqueBoutique = new BoutiqueDeBoutiques("Une boutique servant à débloquer d'autres boutiques !!");
		BoutiqueGUI boutiqueGUI = new BoutiqueGUI("Une boutique proposant des produits pouvant modifier l'interface GUI !!");
		boutiqueGUI.ajouterProduit(2, "Personnalisation boutons", "Vous pouvez choisir la couleur du background et foreground des boutons !", new JButton(),true, true );
		boutiqueGUI.ajouterProduit(2, "Personnalisation textFields", "Vous pouvez choisir la couleur du background et foreground des champs de texte !", new JTextField(),true, true );
		boutiqueGUI.ajouterProduit(2, "Personnalisation textAreas", "Vous pouvez choisir la couleur du background et foreground des zones de textes !", new JTextArea(),true, true );
		boutiqueGUI.ajouterProduit(2, "Personnalisation labels", "Vous pouvez choisir la couleur du foreground des titres !", new JLabel(),false, true );
		boutiqueGUI.ajouterProduit(5, "Personnalisation frame", "Vous pouvez choisir la couleur du background de la frame !", new JFrame(), true, false );
		boutiqueGUI.ajouterProduit(5, "Personnalisation panels", "Vous pouvez choisir la couleur du background des panels !", new JPanel(), true, false );
		boutiqueGUI.ajouterProduit(2, "Personnalisation toggleButton", "Vous pouvez choisir la couleur du foreground des togglesButtons !", new JToggleButton(), false, true );
		boutiqueBoutique.ajouterProduit(boutiqueGUI);
		boutique.ajouterBoutique(boutiqueBoutique);
		boutique.ajouterBoutique(boutiqueGUI);
	}

	@Test
	void test_obtenirProduits() {
				
		Map<String, List<String>> produits = boutique.obtenirProduits();
		for(String boutique : produits.keySet()) {
			System.out.println(boutique);
			for(String produit : produits.get(boutique)) {
				System.out.println(produit);
			}
		}
	}

}
