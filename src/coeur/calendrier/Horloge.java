package coeur.calendrier;

import java.io.Serializable;

public class Horloge implements Comparable<Horloge>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 25L;
	private int heures;
	private int minutes;

	
	public Horloge(int heures, int minutes) throws IllegalArgumentException {
		
		if (heures < 0 || heures > 23 || minutes < 0 || minutes > 59 ) {
			throw new IllegalArgumentException();
		}
		
		this.heures = heures;
		this.minutes = minutes;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Horloge other) {
			return heures == other.heures && minutes == other.minutes;
		}
		return false;
	}

	@Override
	public int compareTo(Horloge o) {
		int comparaison = heures-o.heures;
		if(comparaison == 0) {
			comparaison = minutes-o.minutes;
		}
		return comparaison;
	}

	@Override
	public String toString() {
		return String.format("%02d", heures) + "h" +String.format("%02d", minutes) ;
	}

	public int getHeures() {
		return heures;
	}

	public void setHeures(int heures) {
		this.heures = heures;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
