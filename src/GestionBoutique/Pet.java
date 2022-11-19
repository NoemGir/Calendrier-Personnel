package GestionBoutique;

public class Pet extends Article {
	
	private String nom;
	private String description;
	private int rarete;
	
	public Pet(int prix,String nom, String description, int rarete) {
		super(prix);
		this.nom = nom;
		this.description = description;
		this.rarete = rarete;
	}

}
