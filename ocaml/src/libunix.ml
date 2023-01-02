
let get_example_file name =

  let exe = Sys.executable_name in
  let ( / ) = Filename.concat in
  Filename.dirname exe / "donnees" / name
