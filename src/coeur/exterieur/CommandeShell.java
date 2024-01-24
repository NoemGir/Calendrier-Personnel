package coeur.exterieur;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import coeur.DonneesApplication;

public class CommandeShell {

	private static CommandeShell instance = new CommandeShell();
	private static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	public void executerOcaml() {

		if (isWindows) {
			executerOcamlWindow();
		} else {
			executerOcamlLinux();
		}
	}
 
	private void executerOcamlWindow() {
		ProcessBuilder builder = new ProcessBuilder("CMD", "/C", "Bash ExecuterOcaml.sh");
		String chemin = DonneesApplication.CHEMIN_DES_FICHIERS;
		File plans = new File(chemin +  DonneesApplication.FICHIER_PLANS);

		if (plans.exists()) {
			try {
				Process process = builder.start();
				System.out.println((read(process)));

				if (!(new File(chemin + DonneesApplication.FICHIER_NOUVEAU_PLANS).exists())) {
					System.out.println(" Les nouvelles données n'ont pas pu être créées.");
					/* C'est peut-être parsque la commande 'make && ./main.exe' est introuvable*/
				}
			} catch (Exception e) {
				String erreur = "Erreur survenue lors de l'execution des fichiers Ocaml : " + e + "\n"
						+ "Une solution possible est de donner les droits d'éxécution au fichier ExecuterOcaml.sh avec 'chmod u+v ExecuterOcaml.sh'.";
				System.out.println((erreur));
			}
		} else {
			System.out.println("Les fichiers de donnés n'existent pas.\n");
		}
	}
	
	private void executerOcamlLinux() {
		ProcessBuilder builder = new ProcessBuilder();
		String chemin = DonneesApplication.CHEMIN_DES_FICHIERS;
		builder.command(new File("./") + "/ExecuterOcaml.sh");
		File plans = new File(chemin +  DonneesApplication.FICHIER_PLANS);

		if (plans.exists()) {
			try {
				Process process = builder.start();
				System.out.println((read(process)));

				if (!(new File(chemin + DonneesApplication.FICHIER_NOUVEAU_PLANS).exists())) {
					System.out.println(" Les nouvelles données n'ont pas pu être créées. C'est peut-être parsque la commande 'make && ./main.exe' est introuvable");
				}
			} catch (Exception e) {
				String erreur = "Erreur survenue lors de l'execution des fichiers Ocaml : " + e + "\n"
						+ "Une solution possible est de donner les droits d'éxécution au fichier ExecuterOcaml.sh avec 'chmod u+v ExecuterOcaml.sh'.";
				System.out.println((erreur));
			}
		} else {
			System.out.println("Les fichiers de donnés n'existent pas.\n");
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
			System.out.println("Erreur survenue pendant la lecture du terminal");
			return "";
		}
	}

	public static CommandeShell getInstance() {
		return instance;
	}

}
