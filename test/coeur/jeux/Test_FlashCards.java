package coeur.jeux;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coeur.jeux.FlashCards.Cote;

class Test_FlashCards {
	
	FlashCards flashCards;
	List<String[]> original;
	String[] carte0 = {"desc0", "rep0"};
	String[] carte1 = {"desc1", "rep1"};
	String[] carte2 = {"desc2", "rep2"};
	String[] carte3 = {"desc3", "rep3"};
	String[] carte4 = {"desc4", "rep4"};
	

	@BeforeEach
	public void setUpBeforeClass() {
		flashCards = new FlashCards();
		original = new LinkedList<>();
		List<String[]> cartes = flashCards.getFlashCards();
		original.add(carte0);
		original.add(carte1);
		original.add(carte2);
		original.add(carte3);
		original.add(carte4);
		cartes.addAll(original);
		flashCards.setItFlashCards();
	}
	
	@Test
	public void test_equals() {
		FlashCards autre = new FlashCards();
		List<String[]> cartes = autre.getFlashCards();
		cartes.addAll(original);
		assertEquals(flashCards, autre);
	}

	@Test
	public void test_iterateur_move() {
		ListIterator<String[]> it = flashCards.iterator();
		assertEquals(it.previous(),carte4);
		assertEquals(it.previous(),carte3);
		assertEquals(it.previous(),carte2);
		assertEquals(it.previous(),carte1);
		assertEquals(it.previous(),carte0);
		assertEquals(it.previous(),carte4);
		assertEquals(it.previous(),carte3);
		assertEquals(it.previous(),carte2);

		assertEquals(it.next(),carte3);
		assertEquals(it.next(),carte4);
		assertEquals(it.next(),carte0);
		assertEquals(it.next(),carte1);
		
		assertEquals(it.previous(),carte0);
		assertEquals(it.previous(),carte4);
		assertEquals(it.previous(),carte3);
		assertEquals(it.previous(),carte2);
	}
	
	@Test
	public void test_iterateur_delete() {
		assertEquals(original, flashCards.getFlashCards());
		
		ListIterator<String[]> it = flashCards.iterator();
		it.previous();
		it.remove();
		original.remove(4);
		assertEquals(original, flashCards.getFlashCards());
		
		it.next();
		it.remove();
		original.remove(0);
		assertEquals(original, flashCards.getFlashCards());
		
		it.next();
		it.remove();
		original.remove(0);
		assertEquals(original, flashCards.getFlashCards());
		
		it.next();
		it.next();
		it.remove();
		original.remove(1);
		assertEquals(original, flashCards.getFlashCards());
		
		it.previous();
		it.remove();
		original.remove(0);
		assertEquals(original, flashCards.getFlashCards());
	}
	
	@Test
	public void test_iterateur_set() {
		List<String[]> cartes = flashCards.getFlashCards();
		ListIterator<String[]> it = flashCards.iterator();
		String[] nouvelleCarte = {"descNouv", "repNouv"};
		it.previous();
		it.previous();
		it.next();
		it.set(nouvelleCarte);
		assertEquals(nouvelleCarte, cartes.get(4));
		
		it.next();
		it.set(nouvelleCarte);
		assertEquals(nouvelleCarte, cartes.get(0));
		
		it.next();
		it.set(nouvelleCarte);
		assertEquals(nouvelleCarte, cartes.get(1));
		
		it.next();
		it.set(nouvelleCarte);
		assertEquals(nouvelleCarte, cartes.get(2));
		
		it.next();
		it.set(nouvelleCarte);
		assertEquals(nouvelleCarte, cartes.get(3));
	}
	
	@Test
	public void test_iterateur_add() {
		List<String[]> cartes = flashCards.getFlashCards();
		cartes.clear();
		original.clear();
		ListIterator<String[]> it = flashCards.iterator();
		it.add(carte3);
		original.add(carte3);
		assertEquals(original, cartes);
		it.previous();
		it.add(carte1);
		original.add(0,carte1);
		assertEquals(original, cartes);

		it.previous();
		it.previous();
		it.add(carte2);
		original.add(1,carte2);
		assertEquals(original, cartes);

		it.next();
		it.add(carte4);
		original.add(3,carte4);
		assertEquals(original, cartes);

		it.next();
		it.next();
		it.previous();
		it.add(carte0);
		original.add(0,carte0);
		assertEquals(original, cartes);
	}
	
	@Test
	public void test_changerCote() {
		flashCards.donnerCarteSuivante();
		String autreCote = flashCards.changerCote();
		assertEquals(carte0[1], autreCote);
		autreCote = flashCards.changerCote();
		assertEquals(carte0[0], autreCote);
		
	}
	
	@Test
	public void test_donnerCartePrecedente() {
		
	}
	
	@Test
	public void test_donnerCarteSuivante() {
		String carte = flashCards.donnerCarteSuivante();
		
		flashCards.setMode(Cote.DESCRIPTION);
		
		assertEquals(carte0[0], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte1[0], carte);
		
		flashCards.setMode(Cote.REPONSE);
		
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte2[1], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte3[1], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte4[1], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte0[1], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte1[1], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte2[1], carte);
		
		flashCards.setMode(Cote.DESCRIPTION);
		
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte3[0], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte4[0], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte0[0], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte1[0], carte);
		carte = flashCards.donnerCarteSuivante();
		assertEquals(carte2[0], carte);
		
	}
	
	@Test
	public void test_melanger() {

	}
	
	@Test
	public void test_ajouter() {

	}
	
	@Test
	public void test_retirer() {

	}

}
