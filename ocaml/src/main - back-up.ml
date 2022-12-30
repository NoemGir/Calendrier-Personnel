open Unix
open Libcsv


let current_year () =
  let tm = localtime (time ()) in
  tm.tm_year + 1900;;

let current_month () =
  let tm = localtime (time ()) in
  tm.tm_mon;;

let current_day () =
  let tm = localtime (time ()) in
  tm.tm_mday;;

let jour_est_inferieur annee mois jour =
  annee < current_year () ||
  annee = current_year () && mois < current_month () ||
  annee = current_year () && mois = current_month () && jour < current_day ();;

let rec recuperer_element l index = match l with
  |[] -> failwith "liste vide"
  |e :: rl -> if index = 0 then e else recuperer_element rl (index-1);;

let e_est_correct liste =
  let annee = int_of_string (recuperer_element liste 0) in
  let mois =  int_of_string (recuperer_element liste 1) in
  let jour =  int_of_string (recuperer_element liste 2) in
   not(jour_est_inferieur annee mois jour);;

let rec enlever_lignes liste_liste_csv = match liste_liste_csv with
  |[] -> []
  |e :: rl -> if e_est_correct e then e :: enlever_lignes rl else
                enlever_lignes rl;;

let main_csv () =
  let chemin = Libunix.get_example_file "listeDesPlanPrevu.csv" in
  let output = Libunix.get_example_file "nouvelleListeDesPlan.csv" in
  let csv = Libcsv.load_csv chemin in
  let csv' = enlever_lignes csv in
  let nl, nc = Libcsv.lines_columns csv' in
  let () = Format.printf "Ecriture d'un CSV de taille (%d x %d) dans: %s\n" nl nc output in
  Libcsv.save_csv output csv'


(* Exécute les procédures précédentes *)

let () = main_csv ();;
