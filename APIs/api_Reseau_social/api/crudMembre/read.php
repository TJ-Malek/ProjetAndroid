<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/membre.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Membre($db);

    $stmt = $items->getMembre();
    $itemCount = $stmt->rowCount();

    //echo json_encode($itemCount);

    if($itemCount > 0){
        
        $MembreArr = array();
        $MembreArr["membre"] = array();
       
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $membre = array(
                "IdMembre" => $IdMembre,
                "Nom" => $Nom,
                "Prenom" => $Prenom,
                "Photo" => $Photo,
                "Poste" => $Poste,
                "Grade" => $Grade,
                "Departement" => $Departement,
                "Date_Embauche" => $Date_Embauche,
                "Designation" => $Designation   
            );

            array_push($MembreArr["membre"], $membre);
        }
        echo json_encode($MembreArr);
    }

    else{
        http_response_code(404);
        echo json_encode(
            array("message" => "Aucun membre trouvée.")
        );
    }
?>