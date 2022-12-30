package main;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class CommandeShell {

	private static CommandeShell instance = new CommandeShell();
	private static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	public void executerLesFichiersOcaml() {

		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {

			Display.display(
					"Vous êtes sur le mauvais systeme d'exploitation, impossible de remettre les données a niveau\n");
			Main.recupererToutesLesDonnees(Main.getFichierDonnees(), Main.getFichierPlans());
			Display.displayMainMenu(null);

		} else {
			builder.command(new File("./") + "/ExecuterOcaml.sh");
			try {
				Process process = builder.start();
				Display.display(read(process));
			} catch (Exception e) {
				Display.display("Erreur survenue lors de l'execution des fichiers Ocaml");
			}

		}

	}

	private String read(Process process) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			return builder.toString();
		} catch (Exception e) {
			Display.display("Erreur survenue pendant la lecture du terminal");
			return "";
		}
	}

	public static CommandeShell getInstance() {
		return instance;
	}

}
