package coeur.jeux;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class FlashCards implements Iterable<String[]>, Serializable{
	
	private static final long serialVersionUID = 9L;

	public enum Cote {DESCRIPTION, REPONSE, ALEATOIRE }
	
	private List<String[]> flashCards;
	private ListIterator<String[]> itFlashCards;
	private String[] carteDonnee;
	private Cote mode;
	
	private int nbOperations = 0;
	
	public FlashCards() {
		flashCards = new ArrayList<>();
		itFlashCards = new Iterateur();
		carteDonnee = new String[2];
	}
	
	@Override
	public ListIterator<String[]> iterator() {
		return new Iterateur();
	}
	
	public ListIterator<String[]> getItFlashCards() {
		return itFlashCards;
	}
	
	private class Iterateur implements ListIterator<String[]>{
		
		private int nbOperationReference = nbOperations;
		private ListIterator<String[]> it = flashCards.listIterator();
		private boolean nextEffectue = false;
		private boolean previousEffectue = false;
		private int indice = 0;
		
		@Override
		public boolean hasNext() {
			return !flashCards.isEmpty();
		}

		@Override
		public String[] next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			if(nbOperations != nbOperationReference) {
				throw new ConcurrentModificationException();
			}
			if(previousEffectue) {
				previousEffectue = false;
				next();	
			}
			if(!it.hasNext()) {
				it = flashCards.listIterator();
				indice = 1;
			}
			else {
				indice++;
			}
			nextEffectue = true;
			return it.next();
		}

		@Override
		public boolean hasPrevious() {
			return hasNext();
		}

		@Override
		public String[] previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if(nbOperations != nbOperationReference) {
				throw new ConcurrentModificationException();
			}
			if(nextEffectue) {
				nextEffectue = false;
				previous();
			}
			if(!it.hasPrevious()) {
				it = flashCards.listIterator(flashCards.size());
				indice = flashCards.size()-1;
			}
			else {
				indice--;
			}
			previousEffectue = true;
			return it.previous();
		}

		@Override
		public int nextIndex() {
			return indice;
		}

		@Override
		public int previousIndex() {
			return indice--;
		}

		@Override
		public void remove() {
			if(nbOperations != nbOperationReference) {
				throw new ConcurrentModificationException("nombre Op :" + nbOperations + " nb Op Ref : " + nbOperationReference);
			}
			if(nextEffectue) {
				nextEffectue = false;
				it.remove();
				indice -= 1;
			}
			else if(previousEffectue) {
				previousEffectue = false;
				it.remove();
			}
			else {
				throw new IllegalStateException();
			}
			nbOperations++;
			nbOperationReference++;
			nextEffectue = false;
			previousEffectue = false;
		}

		@Override
		public void set(String[] e) {
			if(nbOperations != nbOperationReference) {
				throw new ConcurrentModificationException("nombre Op :" + nbOperations + " nb Op Ref : " + nbOperationReference);
			}
			if(nextEffectue) {
				it.set(e);
			}
			else if(previousEffectue) {
				it.set(e);
			}
			else {
				throw new IllegalStateException();
			}
			nbOperations++;
			nbOperationReference++;
		}

		@Override
		public void add(String[] e) {
			if(nbOperations != nbOperationReference) {
				throw new ConcurrentModificationException();
			}
			flashCards.add(indice, e);
			indice++;
			it = flashCards.listIterator(indice);
			nbOperations++;
			nbOperationReference++;		
			nextEffectue = false;
			previousEffectue = false;
		}
	}
	
    private void writeObject(java.io.ObjectOutputStream stream) {
        try {
			stream.writeObject(flashCards);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
    	flashCards = (List<String[]>) stream.readObject();
		itFlashCards = new Iterateur();
		carteDonnee = new String[2];
    }
	
	
	public void setMode(Cote mode) {
		this.mode = mode;
	}

	public String changerCote() {
		String[] nouvelleCarteDonnee = new String[2];
		nouvelleCarteDonnee[0] = carteDonnee[1];
		nouvelleCarteDonnee[1] = carteDonnee[0];
		carteDonnee = nouvelleCarteDonnee;
		return coteFace();
	}
	
	public String donnerCartePrecedente() {
		if(itFlashCards.hasPrevious()) {
			carteDonnee = itFlashCards.previous();
			return donnerCote();
		}
		return obtenirCarteVide();
	}
	
	public String donnerCarteSuivante() {
		if(itFlashCards.hasNext()) {
			carteDonnee = itFlashCards.next();
			return donnerCote();
		}
		return obtenirCarteVide();
	}
	
	private String donnerCote() {
		int random = new Random().nextInt(2);
		if(mode == Cote.REPONSE || (mode == Cote.ALEATOIRE && random == 1)) {
			return changerCote();
		}
		return coteFace();
	}
	
	public String coteFace() {
		return carteDonnee[0];
	}
	
	public String coteDos() {
		return carteDonnee[1];
	}	
	
	public void melanger() {
		Collections.shuffle(flashCards);
		itFlashCards = new Iterateur();
	}
	
	public void ajouter(String[] carte) {
		itFlashCards.add(carte);
	}
	public String obtenirCarteVide() {
		String messageVide = "Vous n'avez pas encore de carte";
		carteDonnee[0] = messageVide;
		carteDonnee[1] = messageVide;
		return coteFace(); 
	}
	
	public boolean retirer(String input) {
		Iterator<String[]> it = flashCards.iterator();
		for(;it.hasNext();) {
			String[] carte = it.next();
			if(carte[0].equals(input) || carte[1].equals(input)) {
				it.remove();
				nbOperations++;
				itFlashCards = new Iterateur();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlashCards other = (FlashCards) obj;
		List<String[]> otherTab = other.flashCards;
		if(flashCards.size() == otherTab.size()) {
			Iterator<String[]> it = flashCards.iterator();
			Iterator<String[]> itOther = otherTab.iterator();
			for(;it.hasNext();) {
				String[] carte = it.next();
				String[] carteOther = itOther.next();
				if(!carte[0].equals(carteOther[0]) || !carte[1].equals(carteOther[1])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "FlashCards [flashCards=" + flashCards + "]";
	}

	public List<String[]> getFlashCards() {
		return flashCards;
	}

	public String[] getCarteDonnee() {
		return carteDonnee;
	}

	public void setItFlashCards() {
		this.itFlashCards = iterator();
	}
	
	
	
}
