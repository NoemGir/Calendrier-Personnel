package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.BufferedReader;

public class CommandeShell {
	
	private CommandeShell instance = new CommandeShell();
	private static boolean isWindows = System.getProperty("os.name")
			  .toLowerCase().startsWith("windows");

	public CommandeShell getInstance() {
		return instance;
	}
	
	public static void faireUneCommande() throws IOException, InterruptedException, ExecutionException, TimeoutException {
	
	ProcessBuilder builder = new ProcessBuilder();
	if (isWindows) {
	    builder.command("cmd.exe", "/c", "wslalias");
	    Process process = builder.start();
	    Display.display(read(process));
	    builder.directory(new File("Linux/Ubuntu/home"));
	    Display.display("" + builder.directory());
	    builder.command("sh", "-c", "ls");
	    process = builder.start();
	    Display.display(read(process));
	    
	    
	    
	//    builder.command("cmd.exe", "/c", "echo coucou 2");
	} else {
	    builder.command("cd", "ocaml-cli/");
	    builder.command("make &&", "./main.exe", "10", "20");
	}
//	Process process = builder.start();
//	String result = read(process);
//	System.out.println("Resultat : \n" + result);
	
//	StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
//	Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);
//	int exitCode = process.waitFor();
//	assert exitCode == 0;
//	future.get(10, TimeUnit.SECONDS);
	
	}
	
	private static String read(Process process) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				builder.append(line +  "\n");
			}
			String result = builder.toString();
			return result;
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
			try {
				faireUneCommande();
			} catch (IOException | InterruptedException | ExecutionException | TimeoutException e1) {
				e1.printStackTrace();
			}

		
	}
	
}
