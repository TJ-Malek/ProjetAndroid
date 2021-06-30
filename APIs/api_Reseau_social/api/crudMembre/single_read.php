<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once '../../config/database.php';
    include_once '../../class/membre.php';

    $database = new Database();
    $db = $database->getConnection();

    $item = new Membre($db);

    $item->IdMembre = isset($_GET['IdMembre']) ? $_GET['IdMembre'] : die();
    
    $item->getSingleMembre();

    if($item->Nom != null){

        $membre = array(
            "IdMembre" => $item->$IdMembre,
            "Nom" => $item->$Nom,
            "Prenom" => $item->$Prenom,
            "Photo" => $item->$Photo,
            "Poste" => $item->$Poste,
            "Grade" => $item->$Grade,
            "Departement" => $item->$Departement,
            "Date_Embauche" => $item->$Date_Embauche,
            "Designation" => $item->$Designation   
        );

        http_response_code(200);
        echo json_encode($membre);
    }
      
    else{
        http_response_code(404);
        echo json_encode("Membre non trouvée.");
    }
?>