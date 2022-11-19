package GestionBoutique;

public class Jouet extends Article {
	
	private int duration;

	public Jouet(int prix, int duration) {
		super(prix);
		this.duration = duration;
	}

}
