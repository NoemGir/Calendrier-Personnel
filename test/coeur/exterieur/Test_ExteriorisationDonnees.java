package coeur.exterieur;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coeur.DonneesApplication;
import coeur.UserData;
import coeur.calendrier.Calendrier;
import coeur.calendrier.Horloge;
import coeur.calendrier.KeyHorloge;
import coeur.calendrier.plan.Evenement;
import coeur.calendrier.plan.Plan;
import coeur.calendrier.plan.Tache;

class Test_ExteriorisationDonnees {
	
	UserData userData = new UserData();
	UserData userData2 = new UserData();
	Calendrier<Plan> calendrier;
	
	ExporteurFichier exporter;
	ImporteurFichier importer;
	CommandeShell commande;
	
	Evenement event1;
	Evenement event2;
	Tache tache1;
	Tache tache2;
	Tache tache3;
	Tache tache4;
	Tache tache5;
	Tache tache6;
	Tache tache7;
	
	
	@BeforeEach
	public void setUp() {
		importer = new ImporteurFichier();
		exporter = new ExporteurFichier();
		commande = new CommandeShell();
		calendrier = userData.getCalendrier();
		
		GregorianCalendar jourSup = new GregorianCalendar();
		jourSup.add(Calendar.DAY_OF_MONTH, 10);
		GregorianCalendar jourSup2 = new GregorianCalendar();
		jourSup2.add(Calendar.MONTH, 1); 
		GregorianCalendar jourSup3 = new GregorianCalendar();
		jourSup3.add(Calendar.YEAR, 2); 
		
		tache1 = new Tache(new GregorianCalendar(2023, 10, 16), "tache 1", "infos sup de la tache 1", new Horloge(10,13));
		tache2 = new Tache(new GregorianCalendar(2023, 10, 17), "tache 2", "infos sup de la tache 2", new Horloge(9,59));
		tache3 = new Tache(new GregorianCalendar(2023, 10, 16), "tache 3", "infos sup de la tache 1", new Horloge(10,50));
		event1 = new Evenement(new GregorianCalendar(),"evenement 1", "infos sup event 1", new Horloge(5,17));
		tache4 = new Tache(new GregorianCalendar(2023, 10, 16), "tache 4");
		tache5 = new Tache(jourSup, "tache5");
		tache6 = new Tache(jourSup2, "tache 6", "infos sup de la tache 6", new Horloge (12, 30));
		tache7 = new Tache(jourSup3, "tache 7", "infos sup de la tache 7", new Horloge (19, 59));
		event2 = new Evenement(new GregorianCalendar(2023, 10, 16), "evenement 2", "infos des evenements 2", new Horloge (23,40));
		
		
		tache1.ajouterSousTache("sous tache tache 1","infos sup sous tache", new Horloge(12, 13));
		tache1.ajouterSousTache("sous tache tache 2","infos sup sous tache", new Horloge(12, 15));
		tache1.ajouterSousTache("sous tache tache 3","infos sup sous tache", new Horloge(2, 13));
		
		tache6.ajouterSousTache("sous tache 1 tache6", "infos su sous tache 1 tache 6", new Horloge(11,00));
		tache6.ajouterSousTache("sous tache 2 tache6", "infos su sous tache 2 tache 6", new Horloge(00,00));
		tache6.ajouterSousTache("sous tache 3 tache6", "infos su sous tache 3 tache 6", new Horloge(13,10));

		tache7.ajouterSousTache("sous tache tache 7");
		
		calendrier.ajouterPlan(tache1);
		calendrier.ajouterPlan(tache6);
		calendrier.ajouterPlan(tache2);
		calendrier.ajouterPlan(tache7);
		calendrier.ajouterPlan(event2);
		calendrier.ajouterPlan(event1);
		calendrier.ajouterPlan(tache3);
		calendrier.ajouterPlan(tache4);
		calendrier.ajouterPlan(tache5);
	}
	
	@Test
	void test_miseAJourOcaml() {
		Calendrier<Plan> calendrier2 = new Calendrier<>();
		calendrier2.ajouterPlan(event1);
		calendrier2.ajouterPlan(tache5);
		calendrier2.ajouterPlan(tache6);
		calendrier2.ajouterPlan(tache7);
		
		List<Tache> plansPerdus = new ArrayList<>();
		plansPerdus.add(tache4);
		plansPerdus.add(tache1);
		plansPerdus.add(tache3);
		plansPerdus.add(tache2);
		
		exporter.transfererEnsemblePlans(userData.getCalendrier(), DonneesApplication.FICHIER_PLANS);
		exporter.mode_mise_a_jour();
		commande.executerOcaml();
		importer.recupererCalendrier(userData2.getCalendrier());
		List<Tache> plansPerduRecuperes = importer.recupererTachesRetirees();

		assertEquals(calendrier2,userData2.getCalendrier());
		assertEquals(plansPerdus, plansPerduRecuperes);
	}

	@Test
	void test_exportationImportation() {
		exporter.transfererEnsemblePlans(userData.getCalendrier(), DonneesApplication.FICHIER_NOUVEAU_PLANS);
		importer.recupererCalendrier(userData2.getCalendrier());
		assertEquals(userData.getCalendrier(),userData2.getCalendrier());
	}
	
	@Test
	void test_rajoutPlans() {
		test_miseAJourOcaml();
		
		tache1.setDate(new GregorianCalendar());
		GregorianCalendar jour = new GregorianCalendar();
		jour.add(Calendar.DAY_OF_MONTH, 15);
		tache2.setDate(jour);
		GregorianCalendar jour2 = new GregorianCalendar();
		jour2.add(Calendar.MONTH, 14);
		tache3.setDate(jour2);
		List<Plan> tachesGardees = new ArrayList<>();
		tachesGardees.add(tache1);
		tachesGardees.add(tache2);
		tachesGardees.add(tache3);
		
		
		Calendrier<Plan> calendrier2 = new Calendrier<>();
		tache1.setHeure(new KeyHorloge());
		tache2.setHeure(new KeyHorloge());
		tache3.setHeure(new KeyHorloge());
		tache1.setHeureSousTache(0, new KeyHorloge());
		tache1.setHeureSousTache(1, new KeyHorloge());
		tache1.setHeureSousTache(2, new KeyHorloge());
		calendrier2.ajouterPlan(tache1);
		calendrier2.ajouterPlan(tache2);
		calendrier2.ajouterPlan(tache3);
		calendrier2.ajouterPlan(event1);
		calendrier2.ajouterPlan(tache5);
		calendrier2.ajouterPlan(tache6);
		calendrier2.ajouterPlan(tache7);
		
		exporter.transfererEnsemblePlans(tachesGardees, DonneesApplication.FICHIER_AGARDER);
		exporter.mode_rajout();
		commande.executerOcaml();
		importer.recupererCalendrier(calendrier);

		assertEquals(calendrier2.toString(), calendrier.toString());
	}
}












