package frontiere.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import coeur.DonneesApplication;
import controleur.ControlCommunicationOcaml;
import controleur.ControlDemanderJour;
import utils.Utils;

public class TachesPerduesUI extends GUIManager{

	private JFrame frmTachesPerdues;
	private JButton btnChangerLaDate;
	private JButton decalerTacheButton;
	private JList<String> list;
	private MenuPrincipalUI menuPrincipalUI;

	private ControlCommunicationOcaml controlCommunicationOcaml;
	private ControlDemanderJour controlDemanderJour; 
	
	/**
	 * Launch the application.
	 */
	public void lancerGUI(List<String> tachesRetires) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmTachesPerdues.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		initDynamique(tachesRetires);
		frmTachesPerdues.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitter();
            }
		});
	}

	/**
	 * Create the application.
	 */
	public TachesPerduesUI(ControlCommunicationOcaml controlCommunicationOcaml, ControlDemanderJour controlDemanderJour) {
		this.controlCommunicationOcaml = controlCommunicationOcaml;
		this.controlDemanderJour = controlDemanderJour;
		initialize();
		ajouterFrameSansModif(frmTachesPerdues);
	}
	
	public void setMenuPrincipalUI(MenuPrincipalUI menuPrincipalUI) {
		this.menuPrincipalUI = menuPrincipalUI;
	}

	private void initDynamique(List<String> nomTaches) {
		 DefaultListModel<String> model = new DefaultListModel<>();
		 model.addAll(nomTaches);
		 list.setModel(model);
		 btnChangerLaDate.setEnabled(false);
		 decalerTacheButton.setEnabled(false);
	}
	
	private void retirerTachesSelectionnees() {
		 DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
		 int[] tachesSelectionnees = list.getSelectedIndices();
		 for(int indice : tachesSelectionnees) {
			 model.remove(indice);
		 }
		 btnChangerLaDate.setEnabled(false);
		 decalerTacheButton.setEnabled(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTachesPerdues = new JFrame();
		frmTachesPerdues.setTitle("Taches Perdues");
		frmTachesPerdues.setBounds(100, 100, 661, 450);
		
		JLabel lblNewLabel = new JLabel("Durant votre absence, vous n'avez pas accomplis certaines tâches qui ne sont plus à jour ");
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);
		
		JLabel lblNewLabel_1 = new JLabel("Liste des tâches non accomplies");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setColumnHeaderView(lblNewLabel_1);
		
		list = new JList<>();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int[] indicesSelectionnees = list.getSelectedIndices();
				if(indicesSelectionnees != null) {
					 btnChangerLaDate.setEnabled(true);
					 decalerTacheButton.setEnabled(true);
				}
			}
		});
		list.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(list);
		
		JPanel panel = new JPanel();
		
		decalerTacheButton = new JButton("Décaler la/les tâche/s");
		decalerTacheButton.setEnabled(false);
		decalerTacheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(frmTachesPerdues, "Dans combien de jour voulez vous placez les tâches séléctionnées ?", null);
				if (input != null) {
					GregorianCalendar jour = Utils.ajouterJours(input);
					if(jour != null) {
						controlCommunicationOcaml.ajouterTachesAGarder(list.getSelectedIndices(), jour);
						retirerTachesSelectionnees();
					}
				}				 
			}
		});
		panel.add(decalerTacheButton);
		decalerTacheButton.setActionCommand("");
		decalerTacheButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnChangerLaDate = new JButton("Changer la Date");
		btnChangerLaDate.setEnabled(false);
		btnChangerLaDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(frmTachesPerdues, "Donnez la nouvelle date JOUR/MOIS/ANNEE : ", null);
				if (input != null && controlDemanderJour.verifierDateDonnee(input)) {
					GregorianCalendar jour = Utils.createCalendarFromInt(controlDemanderJour.recupererDate(input));
					controlCommunicationOcaml.ajouterTachesAGarder(list.getSelectedIndices(), jour);
					retirerTachesSelectionnees();
		
				}	
			}
		});
		btnChangerLaDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChangerLaDate.setActionCommand("");
		panel.add(btnChangerLaDate);
		
		JButton btnQuitter = new JButton("Ignorer ces tâches non accomplies");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitter();
			}
		});
		btnQuitter.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnQuitter.setActionCommand("");
		panel.add(btnQuitter);
		GroupLayout groupLayout = new GroupLayout(frmTachesPerdues.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
					.addGap(49))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
					.addGap(16))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(26)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
					.addGap(26)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
					.addGap(33))
		);
		frmTachesPerdues.getContentPane().setLayout(groupLayout);
	}
	
	private void quitter() {
		controlCommunicationOcaml.exporterTachesAGarder();
		menuPrincipalUI.lancerGUI(DonneesApplication.JOUR_ACTUEL);
        frmTachesPerdues.setVisible(false);
	}
}
