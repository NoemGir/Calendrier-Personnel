open Unix
open Libcsv

(*##############################------- DEFINITION DU MODULE -------#################################*)

type date = {
  annee : int;
  mois : int ;
  jour : int;
  } ;;

let annee_correct annee = annee > 0;;

let mois_correct  mois =
   mois >= 0 && mois < 12;;

let annee_bissextile annee =
  (annee mod 4 = 0 && annee mod 100 <> 0 ) ||
    annee mod 400 = 0;;

let jour_correct annee mois jour =
  annee_correct annee &&
  mois_correct mois &&
  jour > 0 &&
  match mois with
  | 0 | 2 | 4 | 6 | 7 | 9 | 11 -> jour <= 31
  | 3 | 5 | 8 | 10 -> jour <= 30
  | 1 -> if annee_bissextile annee then jour <= 29 else jour <= 28
  | _ -> failwith "mois donné incorrect";;


 let mk_date annee mois jour =
   if jour_correct annee mois jour
   then  {annee  = annee;
          mois  = mois;
          jour = jour}
   else let erreur =  Printf.sprintf "jour donne %d/%d/%d incorrect" jour mois annee in failwith erreur;;

let get_annee date = date.annee ;;
let get_mois date = date.mois ;;
let get_jour date = date.jour;;


let date_supperieure_egale date1 date2 =
  let annee_date1, annee_date2 = get_annee date1, get_annee date2 in
  annee_date1 > annee_date2 ||
    let mois_date1, mois_date2 = get_mois date1, get_mois date2 in
    annee_date1 = annee_date2 && mois_date1 > mois_date2 ||
      let jour_date1, jour_date2 = get_jour date1, get_jour date2 in
      annee_date1 = annee_date2 && mois_date1 = mois_date2 && jour_date1 >= jour_date2;;

let afficher_date date =
  Printf.sprintf "%d/%d/%d" (get_jour date) (get_mois date) (get_annee date);;


(*##############################------- MISE A JOUR DU CALENDRIER -------#################################*)


(* --------- initialisation date actuelle -------------*)


let annee_actuelle =
  let tm = localtime (time ()) in
  tm.tm_year + 1900;;

let mois_actuel =
  let tm = localtime (time ()) in
  tm.tm_mon;;

let jour_actuel =
  let tm = localtime (time ()) in
  tm.tm_mday;;

let jour_actuel = mk_date annee_actuelle mois_actuel jour_actuel;;

(* --------- etude de la ligne -------------*)

let recuperer_element l index = List.nth l index;;

let plan_est_correct plan =
  let annee = int_of_string (recuperer_element plan 0) in
  let mois =  int_of_string (recuperer_element plan 1) in
  let jour =  int_of_string (recuperer_element plan 2) in
  let date_donnee = mk_date annee mois jour in
  date_supperieure_egale date_donnee jour_actuel;;


(* --------- Vider le fichier plansSupprimes-------------*)

let effacer_plansSupprimes () =
  let output = Libunix.get_csv_file "plansSupprimes.csv" in
  let csv' = [[]] in
  let () = Format.printf "Suppression des éléments à l'intérieur du fichier %s\n" output in
  Libcsv.save_csv output csv';;

(* --------- sauvegarde de la tache retirée -------------*)

let ajouter_plan_supprime liste_liste_csv plan = plan :: liste_liste_csv;;

let afficher_plan plan = recuperer_element plan 3;;

let fichier_a_part plan =
  let output = Libunix.get_csv_file "plansSupprimes.csv" in
  let csv = Libcsv.load_csv output in
  let csv' = ajouter_plan_supprime csv plan in
  let () = Format.printf "Ajout du plan %s dans: %s\n" (afficher_plan plan) output in
  Libcsv.save_csv output csv';;

let est_un_evenement plan = recuperer_element plan 6 = "null";;

let mettre_a_part plan =
  if est_un_evenement plan then () else  fichier_a_part plan;;

(* ---------      parcours du csv     -------------*)

let rec enlever_lignes liste_liste_csv =
  List.fold_right (fun e r -> if plan_est_correct e then e :: r else let () =  mettre_a_part e in r) liste_liste_csv [];;

let mise_a_jour_csv () =
  let () = effacer_plansSupprimes () in
  let chemin = Libunix.get_csv_file "calendrier.csv" in
  let output = Libunix.get_csv_file "calendrierMisAJour.csv" in
  let csv = Libcsv.load_csv chemin in
  let csv' = enlever_lignes csv in
  let nl, nc = Libcsv.lines_columns csv' in
  let () = Format.printf "Ecriture d'un CSV de taille (%d x %d) dans: %s\n" nl nc output in
  Libcsv.save_csv output csv'

(*##############################------- RECUPERATION TACHES VOULUES-------#################################*)


(* ---------Remplacer les horaires de toutes les tâches déplacées par 00h00  -------------*)

let mettre_heure_0_sous_taches sousTachesString =
  let sousTaches = String.split_on_char '~' sousTachesString in
  let sousTachesModifiees = List.mapi (fun indice valeur -> if indice mod 4 = 2 then "00h00" else valeur ) sousTaches in
  List.fold_right (fun e r -> if String.contains e '[' then String.cat e r else  String.concat e ["~";r]) sousTachesModifiees "" ;;


let mettre_heure_0_plan plan = let sousTachesModifiees = mettre_heure_0_sous_taches (recuperer_element plan 7) in
                               List.mapi (fun indice valeur -> if indice = 5 then "00h00" else if indice = 7 then sousTachesModifiees else valeur) plan ;;


let mettre_heure_0 csv = List.map mettre_heure_0_plan csv;;

(* ---------Ajouter les tâches gardées avec leur nouvelles dates aux tâches du calendrier  -------------*)

let recuperer_aGarder () =
  let chemin = Libunix.get_csv_file "aGarder.csv" in
  let csv = Libcsv.load_csv chemin in
  mettre_heure_0 csv;;

let ajouter_plan liste_liste_csv = let a_garder = recuperer_aGarder () in
                                   List.append liste_liste_csv a_garder ;;

let ajouter_plans_csv () =
  let output = Libunix.get_csv_file "calendrierMisAJour.csv" in
  let csv = Libcsv.load_csv output in
  let csv' = ajouter_plan csv in
  let () = Format.printf "Ajout accompli des tâches récupérées dans %s\n" output in
  Libcsv.save_csv output csv';;


(*##############################------- CHOIX DU MODE -------#################################*)

let lire_mode liste_liste_csv = if List.nth (List.nth liste_liste_csv 0) 0 = "mise a jour"
                                then let () = Format.printf "Ocaml mode Mise à Jour" in
                                     mise_a_jour_csv ()
                                else let () = Format.printf "Ocaml mode Rajout" in
                                  let () =  ajouter_plans_csv () in
                                  effacer_plansSupprimes ();;

let main () =
  let csv_mode = Libunix.get_csv_file "mode.csv" in
  let csv = Libcsv.load_csv csv_mode in
  lire_mode csv;;

let () = main ();;

