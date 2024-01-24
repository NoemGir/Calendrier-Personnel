package frontiere.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import coeur.DonneesApplication;
import controleur.ControlGestionDonnees;

public class GUIManager {
	
	private static List<JFrame> frames = new ArrayList<>(); 
	private static ControlGestionDonnees controlGestionDonnees;
	
	public GUIManager() {
		setLookAndFeel(DonneesApplication.LOOK_AND_FEEL);
	}
	
	public  void ajouterFrame(JFrame frame) {
		frames.add(frame);
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("sauvegarde des données...");
                controlGestionDonnees.sauvegarderDonnees();
                System.exit(0);
            }
		});
	}
	
	public void ajouterFrameSansModif(JFrame frame) {
		frames.add(frame);
	}
	
	public static void setLookAndFeel(String lookAndFeel) {
		try { 
	        UIManager.setLookAndFeel(lookAndFeel); 
	        for(JFrame frame : frames) {
				SwingUtilities.updateComponentTreeUI(frame);	
				 frame.pack();
				 frame.repaint();
			}
	    } 
	    catch (Exception e) { 
	        System.out.println("Look and Feel not set"); 
	    } 
	}
	
	private static void activerPersonalisationContainer(Container contenant, Class<? extends Component> classe, Color[] couleurs) {
		Component[] composants = contenant.getComponents(); 
		for(Component composant : composants) {
			 if(classe == composant.getClass()) {
				 if(couleurs[0] != null) {
						composant.setForeground(couleurs[0]);
					}
				if(couleurs[1] != null) {
					composant.setBackground(couleurs[1]);
				}
			}
			if(composant instanceof Container composantContainer) {
				activerPersonalisationContainer(composantContainer, classe, couleurs);
			}
		}
	}
	
	
	public static void updatePersonnalisation(Class<? extends Component> classe, Color[] couleurs) {	
		for(JFrame frame : frames) {
			activerPersonalisationContainer(frame, classe, couleurs );
		}
	}
	public static void setControlGestionDonnees(ControlGestionDonnees controlGestionDonnees) {
		GUIManager.controlGestionDonnees = controlGestionDonnees;
	}
	
	
}
