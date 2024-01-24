
Require Import String.
Require Import List.
Import ListNotations.
Require Import ZArith.

Definition is_None {A : Type} (option : option A) : bool :=
  match option with
  | None => true
  | _ => false
  end.

Module Type IBoutiqueSecondaire.

  Parameter produit : Type.
  Parameter boutique : Type.
  Parameter nom_produit : produit -> string.
  Parameter prix_produit :produit -> nat.
  Parameter egalite_produit : produit -> produit -> Prop.
  Parameter recuperer_prix : string -> boutique -> option nat.
  Parameter ajouterProduit : produit -> boutique -> boutique.
  Parameter retirerProduit : string -> boutique -> boutique.
  Parameter acheterProduit : string -> boutique -> nat -> boutique.
  Parameter argentSuffisant : string -> boutique -> nat -> bool.
  Parameter recupererProduit : string -> boutique -> option produit.
  Parameter isEmpty : boutique -> bool.
  Parameter equals : boutique  -> boutique -> bool.

  (*Preuve : Si un produit est present dans la boutique, alors il sera toujours présent si on y ajoute un produit*)
  Axiom recupererProduit_ajout : forall (p : produit) (b : boutique) (nom : string),
      is_None (recupererProduit nom b) = false ->
      is_None (recupererProduit nom (ajouterProduit p b)) = false.

  (*Preuve : la boutique reste la meme si on ajoute un élément deja présent*)
  Axiom ajout_deja_present :  forall (p : produit) (b : boutique),
      is_None (recupererProduit (nom_produit p) b) = false -> ajouterProduit p b = b.

  (*Preuve : Si argentSuffisant renvoie true, alors le produit est présent dans la boutique*)
  Axiom argentSuffisant_produit_present :  forall (nom : string) (b : boutique) (argent : nat),
      argentSuffisant nom b argent = true -> is_None (recupererProduit nom b) = false.

  (*Preuve : Si argentSuffisant renvoie true, alors le produit est présent dans la boutique*)
  Axiom argentSuffisant_produit_present2 :  forall (nom : string) (b : boutique) (argent : nat),
      argentSuffisant nom b argent = true -> exists p : produit, recupererProduit nom b = Some p.

  (*Preuve : Si argentSuffisant renvoie true, il est possible de recupérer le prix du produit *)
  Axiom argentSuffisant_recup_produit :  forall (n : string) (b : boutique) (argent : nat),
      argentSuffisant n b argent = true -> exists prix : nat,
        recuperer_prix n b = Some prix.

  (*Preuve : Si on retire un produit de la boutique, alors il ne sera plus présent dans la boutique*)
  Axiom retirerProduit_lem :   forall (nom : string) (b : boutique),
      is_None (recupererProduit nom (retirerProduit nom b)) = true.

  (*Preuve : Si on ajoute un produit dans la boutique, alors il sera présent*)
  Axiom ajouterProduit_lem :  forall (p : produit) (b : boutique),
      is_None (recupererProduit (nom_produit p) (ajouterProduit p b)) = false.

  (*Preuve : Si on a suffisament d'argent, alors le produit ne sera plus dans la boutique une fois qu'il est acheté*)
  Axiom produit_achete_retire : forall (nom : string) (b : boutique) (argent : nat),
      argentSuffisant nom b argent = true ->
      is_None (recupererProduit nom (acheterProduit nom b argent)) = true.

  (*Preuve : Si on ajoute puis retire un produit de la boutique, le produit ne sera plus présent *)
  Axiom produit_present_1_fois : forall (n : string) (b : boutique) (p : produit), nom_produit p = n ->
                                                                              is_None (recupererProduit n ( retirerProduit n (ajouterProduit p b))) = true.

  (*Preuve : Le nom du recultat donné par recupererProduit est égal au nom donné dans recupererProduit*)
  Axiom recupererProduit_lem : forall (p p2 : produit) (b : boutique),
      recupererProduit (nom_produit p) b = Some p2 -> egalite_produit p p2.

  (* Preuve : Le produit rendu par recupererProduit est égal produit dont le nom est donné dans recupererProduit *)
  Axiom recupererProduit_egalite: forall (p p2 : produit) ( b : boutique),
      recupererProduit (nom_produit p) b = Some p2 -> p = p2.

  (*Preuve : Si argentSuffisant renvoie true, le resultat donné par recuperer_prix est égal au prix du produit donné *)
  Axiom recuperer_prix_egalité :  forall (p : produit) (b : boutique) (argent : nat),
      argentSuffisant (nom_produit p) b argent = true ->
      recuperer_prix (nom_produit p) b = Some (prix_produit p).

  (* Preuve : si argentSuffisant renvoie true, alors le prix du produit donné est inférieur a l'argent possedé *)
  Axiom argentSuffisant_argent :  forall (p : produit) (b : boutique) (argent : nat),
      argentSuffisant (nom_produit p) b argent = true -> prix_produit p <= argent.
  (* Preuve : Si argentSuffisant renvoie true, alors le resultat donné par recuperer_prix existe et est inférieur a l'argent possedé*)

  Axiom argentSuffisant_lem :  forall (p : produit) (b : boutique) (argent : nat),
      argentSuffisant (nom_produit p) b argent = true -> exists prix : nat,
        recuperer_prix (nom_produit p) b = Some prix /\ prix <= argent.

End IBoutiqueSecondaire.

Module BoutiqueSecondaire : IBoutiqueSecondaire.

  Definition produit :=  (string * nat)%type.
  Definition boutique := list produit.

  Definition nom_produit (p : produit) := fst p.

  Definition prix_produit (p : produit) := snd p.

  Definition egalite_produit (p p2 : produit) : Prop :=
    nom_produit p = nom_produit p2.

  Fixpoint recupererProduit (nom : string) (b :  boutique) : option produit :=
      match b with
      | [] => None
      | p :: rl => if string_dec (nom_produit p) nom then Some p else recupererProduit nom rl
      end.

  Definition recuperer_prix (nom : string) (b: boutique) : option nat :=
    let p := recupererProduit nom b in
    match p with
      | None => None
      | Some prod => Some (prix_produit prod)
    end.

  Definition ajouterProduit (p : produit) (b : boutique):=
        if is_None (recupererProduit (nom_produit p) b) then p :: b else b.

  Fixpoint retirerProduit (nom : string) (b : boutique) :=
            match b with
            | [] => []
            | p :: rl => if string_dec (nom_produit p) nom then retirerProduit nom rl else p :: retirerProduit nom rl
            end.

  Definition argentSuffisant (nom : string) (b : boutique) (argent : nat) : bool :=
    let p := recupererProduit nom b in
    match p with
      | None => false
      | Some prod =>  (prix_produit prod) <=? argent
    end.

  Definition acheterProduit (nom : string) ( b : boutique ) (argent : nat) :=
    if argentSuffisant nom b argent then retirerProduit nom b else b.

  Definition isEmpty (b : boutique ) : bool :=
    match b with
    | [] => true
    | e :: rl => false
    end.

  Fixpoint equals ( b1 b2 : boutique) : bool :=
    match b1, b2 with
    | [], [] => true
    |p1 :: rl1, p2 :: rl2 => if string_dec (nom_produit p2) (nom_produit p2) then
                            equals rl1 rl2 else false
    | _, _ => false
    end.


  (*Preuve :  is_None None = true*)
  Lemma is_None_lem : forall opt : option Type, opt = None -> is_None opt = true.
  Proof.
    intros.
    unfold is_None.
    destruct opt.
    discriminate.
    reflexivity.
  Qed.

  (*Preuve :  is_None Some x = false*)
  Lemma is_Some_lem : forall opt : option Type,( exists x : Type, opt = Some x) -> is_None opt = false.
  Proof.
    intros.
    unfold is_None.
    destruct opt.
    reflexivity.
    simpl.
    destruct H.
    discriminate.
  Qed.


  (*Preuve : Si un produit est present dans la boutique, alors il sera toujours présent si on y ajoute un produit*)
  Lemma recupererProduit_ajout : forall (p : produit) (b : boutique) (nom : string),
      is_None (recupererProduit nom b) = false ->
      is_None (recupererProduit nom (ajouterProduit p b)) = false.
  Proof.
    intros.
    unfold ajouterProduit.
    destruct ( is_None (recupererProduit (nom_produit p) b)).
    simpl.
    destruct ( string_dec (nom_produit p) nom).
    simpl.
    reflexivity.
    apply H.
    apply H.
  Qed.


  (*Preuve : Si un produit n'est pas présent dans la boutique et qu'on y ajoute un autre produit différent du produit non présent, alors le premier produit n'est toujours pas présent*)
  Lemma recupererProduit_ajout2 : forall (p : produit) (b : boutique) (nom : string),
      nom_produit p <> nom -> is_None (recupererProduit nom b) = true ->
      is_None (recupererProduit nom (p :: b)) = true.
  Proof.
    intros.
    simpl.
    destruct (string_dec (nom_produit p) nom).
    -simpl.
     easy.
    -rewrite H0.
     reflexivity.
  Qed.

  (*Preuve : la boutique reste la meme si on ajoute un élément deja présent*)
  Lemma ajout_deja_present :  forall (p : produit) (b : boutique),
      is_None (recupererProduit (nom_produit p) b) = false -> ajouterProduit p b = b.
  Proof.
    intros.
    unfold ajouterProduit.
    rewrite H.
    reflexivity.
  Qed.

  (* Preuve : la boutique recuperer un produit si on l'ajoute et qu' elle ne le possèdait pas avant*)
  Lemma ajout_non_present :  forall (p : produit) (b : boutique),
      is_None (recupererProduit (nom_produit p) b) = true -> ajouterProduit p b = p :: b.
  Proof.
    intros.
    unfold ajouterProduit.
    rewrite H.
    easy.
  Qed.



  (*Preuve : Si argentSuffisant renvoie true, alors le produit est présent dans la boutique*)
  Lemma argentSuffisant_produit_present :  forall (nom : string) (b : boutique) (argent : nat),
      argentSuffisant nom b argent = true -> is_None (recupererProduit nom b) = false.
  Proof.
    intros nomH bH argentH.
    unfold argentSuffisant.
    destruct recupererProduit.
    -intro.
     simpl.
     reflexivity.
    -intro.
     unfold is_None.
     discriminate.
  Qed.


  (*Preuve : Si argentSuffisant renvoie true, alors le produit est présent dans la boutique*)
  Lemma argentSuffisant_produit_present2 :  forall (nom : string) (b : boutique) (argent : nat),
      argentSuffisant nom b argent = true -> exists p : produit, recupererProduit nom b = Some p.
  Proof.
    intros nomH bH argentH.
    unfold argentSuffisant.
    destruct recupererProduit as []eqn:hyp.
    intro.
    exists p.
    reflexivity.
    intro.
    contradict H.
    Lia.lia.
  Qed.

  (*Preuve : Si argentSuffisant renvoie true, il est possible de recupérer le prix du produit *)
  Lemma argentSuffisant_recup_produit :  forall (n : string) (b : boutique) (argent : nat),
      argentSuffisant n b argent = true -> exists prix : nat,
        recuperer_prix n b = Some prix.
  Proof.
    intros.
    pose proof (argentSuffisant_produit_present2 n b argent H) as Hprod_present.
    destruct Hprod_present.
    unfold recuperer_prix.
    destruct recupererProduit eqn:hyp.
    exists (prix_produit p).
    reflexivity.
    contradict H0.
    discriminate.
  Qed.


  (*Preuve : Si on retire un produit de la boutique, alors il ne sera plus présent dans la boutique*)
  Lemma retirerProduit_lem :   forall (nom : string) (b : boutique),
      is_None (recupererProduit nom (retirerProduit nom b)) = true.
  Proof.
    intros.
    induction b.
    - simpl.
      reflexivity.
    - simpl.
      destruct  (string_dec (nom_produit a)).
      {
        rewrite IHb.
        reflexivity.
      }
      { rewrite recupererProduit_ajout2.
        reflexivity.
        apply n.
        apply IHb.
      }
  Qed.


  (*Preuve : Si on ajoute un produit dans la boutique, alors il sera présent*)
  Lemma ajouterProduit_lem :  forall (p : produit) (b : boutique),
      is_None (recupererProduit (nom_produit p) (ajouterProduit p b)) = false.
  Proof.
    intros.
    unfold ajouterProduit.
    destruct ( is_None (recupererProduit (nom_produit p) b)) as []eqn:?.
    simpl.
    destruct (string_dec (nom_produit p) (nom_produit p))   as []eqn:?.
    simpl.
    reflexivity.
    contradict Heqb0.
    contradiction.
    apply Heqb0.
  Qed.

  (*Preuve : Si on a suffisament d'argent, alors le produit ne sera plus dans la boutique une fois qu'il est acheté*)
  Lemma produit_achete_retire : forall (nom : string) (b : boutique) (argent : nat),
      argentSuffisant nom b argent = true -> is_None (recupererProduit nom (acheterProduit nom b argent)) = true.
  Proof.
    intros.
    unfold acheterProduit.
    rewrite H.
    apply retirerProduit_lem.
  Qed.


  (*Preuve : Si on ajoute puis retire un produit de la boutique, le produit ne sera plus présent *)
  Lemma produit_present_1_fois : forall (n : string) (b : boutique) (p : produit), nom_produit p = n ->
                                                                              is_None (recupererProduit n ( retirerProduit n (ajouterProduit p b))) = true.
    intros.
    rewrite retirerProduit_lem.
    reflexivity.
  Qed.


  (*Preuve : Le nom du recultat donné par recupererProduit est égal au nom donné dans recupererProduit*)
  Lemma recupererProduit_lem : forall (p p2 : produit) (b : boutique),
      recupererProduit (nom_produit p) b = Some p2 -> egalite_produit p p2.
  Proof.
    intros.
    unfold egalite_produit.
    unfold recupererProduit in H.
    induction b in H.
    discriminate H.
    destruct (string_dec (nom_produit a) (nom_produit p)) as []eqn:hyp.
    injection H.
    intro.
    rewrite <- e.
    unfold nom_produit.
    f_equal.
    apply H0.
    apply IHb.
    apply H.
  Qed.

  (* Preuve : Le produit rendu par recupererProduit est égal produit dont le nom est donné dans recupererProduit *)
  Lemma recupererProduit_egalite: forall (p p2 : produit) ( b : boutique),
      recupererProduit (nom_produit p) b = Some p2 -> p = p2.
  Proof.
  Admitted.

  (*Preuve : Si argentSuffisant renvoie true, le resultat donné par recuperer_prix est égal au prix du produit donné *)
  Lemma recuperer_prix_egalité :  forall (p : produit) (b : boutique) (argent : nat),
      argentSuffisant (nom_produit p) b argent = true ->
      recuperer_prix (nom_produit p) b = Some (prix_produit p).
  Proof.
    intros.
    pose proof (argentSuffisant_recup_produit (nom_produit p) b argent H) as Hrecup_produit.
    unfold recuperer_prix.
    destruct Hrecup_produit.
    destruct (recupererProduit (nom_produit p) b) eqn:hyp.
    assert (p = p0).
    {.
          pose proof (recupererProduit_egalite p p0 b  ).
          apply (H1 hyp).
    }.
    rewrite H1.
    reflexivity.
    unfold prix_produit.
    destruct p.
    simpl.
    symmetry.
    symmetry.
    unfold recuperer_prix in H0.
    rewrite hyp in H0.
    contradict H0.
    discriminate.
  Qed.

  (* Preuve : si argentSuffisant renvoie true, alors le prix du produit donné est inférieur a l'argent possedé *)
  Lemma argentSuffisant_argent :  forall (p : produit) (b : boutique) (argent : nat),
      argentSuffisant (nom_produit p) b argent = true -> prix_produit p <= argent.
  Proof.
    intros Hp Hb Hargent.
    unfold argentSuffisant.
    destruct (recupererProduit (nom_produit Hp) Hb) eqn:hyp.
    assert (Hp = p).
    {
      pose proof (recupererProduit_egalite Hp p Hb  ).
      apply (H hyp).
    }
    intro.
    apply leb_complete in H0.
    rewrite H.
    apply H0.
    intro.
    discriminate H.
  Qed.

  (* Preuve : Si argentSuffisant renvoie true, alors le resultat donné par recuperer_prix existe et est inférieur a l'argent possedé*)
  Lemma argentSuffisant_lem :  forall (p : produit) (b : boutique) (argent : nat),
      argentSuffisant (nom_produit p) b argent = true -> exists prix : nat,
        recuperer_prix (nom_produit p) b = Some prix /\ prix <= argent.
  Proof.
    intros.
    pose proof (argentSuffisant_recup_produit (nom_produit p) b argent H) as Hrecup_produit.
    pose proof (recuperer_prix_egalité p b argent H) as Hrecup_prix .
    pose proof (argentSuffisant_argent p b argent H) as Hargent.
    destruct Hrecup_produit.
    exists x.
    split.
    apply H0.
    rewrite Hrecup_prix in H0.
    inversion H0.
    apply Hargent.
  Qed.

End BoutiqueSecondaire.
