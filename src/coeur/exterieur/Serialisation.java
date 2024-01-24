package coeur.exterieur;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialisation {


	public Serialisation() {
	}

	public  void sauvegarder(Object obj, String nomFichier) {
		try {
	        FileOutputStream fichierSortie = new FileOutputStream(nomFichier);
	        ObjectOutputStream sortieObjet = new ObjectOutputStream(fichierSortie);
	        sortieObjet.writeObject(obj);
	        sortieObjet.close();
	        fichierSortie.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public <T> T recupererSauvegarde(String nomFichier) throws IOException, ClassNotFoundException {
 
        FileInputStream fichierEntree = new FileInputStream(nomFichier);
        ObjectInputStream entreeObjet = new ObjectInputStream(fichierEntree);
        T objetDeserialise = (T) entreeObjet.readObject();
        entreeObjet.close();
        fichierEntree.close();
        return objetDeserialise;
	}
}
