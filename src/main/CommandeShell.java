package main;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class CommandeShell {
	
	private static CommandeShell instance = new CommandeShell();
	private static boolean isWindows = System.getProperty("os.name")
			  .toLowerCase().startsWith("windows");

	public static CommandeShell getInstance() {
		return instance;
	}
	
	public String[][] mettreTableauPlanFormatCSV(Plan[] tableauPlan, int nbPlan) {
		String[][] fichier = new String[100][4];
		for (int i = 0; i < nbPlan; i++) {
			GregorianCalendar date = tableauPlan[i].getDate();
			fichier[i][0] = Integer.toString(date.get(Calendar.YEAR));
			fichier[i][1] = Integer.toString(date.get(Calendar.MONTH));
			fichier[i][2] = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
			fichier[i][3] = tableauPlan[i].getNom();
		}
		return fichier;
	}
	
	public String toStringFichier(String[][] fichier, int nbPlan) {
		String stringFichier = "{";
		int i;
		for( i = 0; i<nbPlan-1; i++) {
			stringFichier = stringFichier + " {'"+fichier[i][0] + "', '"+fichier[i][1] + "','" +fichier[i][2] + "', '"+fichier[i][3] + "'},\n";
		}
		stringFichier = stringFichier + "{ '"+fichier[i][0] + "', '"+fichier[i][1] + "', '" +fichier[i][2] + "','"+fichier[i][3] + "'} }";
		return stringFichier;
	}
	
	public static void executerLesFichiersOcaml() {

		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
			Display.display("Vous êtes sur le mauvais systeme d'exploitation, action impossible, retour au Menu");
			Display.displayMainMenu(null);

		} else {
			builder.command(new File("./") + "/ExecuterOcaml.sh");
			try {
				Process process = builder.start();
				Display.display(read(process));
			} catch (Exception e) {
				Display.display("Erreur survenue, retour au Menu");
				Display.displayMainMenu(null);
			}

		}

	}

	private static String read(Process process) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			String result = builder.toString();
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		executerLesFichiersOcaml();
	}

}
