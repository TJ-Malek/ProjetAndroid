<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/utilisateur.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Utilisateur($db);

    $stmt = $items->getUtilisateurs();
    $itemCount = $stmt->rowCount();

    //echo json_encode($itemCount);

    if($itemCount > 0){
        
        $UtilisateurArr = array();
        $UtilisateurArr["utilisateur"] = array();
       
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $utilisateur = array(
                "IdUtilisateur" => $IdUtilisateur,
                "NomUtilisateur" => $NomUtilisateur,
                "Mail" => $Mail,
                "Role" => $Role,
                "Statut" => $Statut     
            );

            array_push($UtilisateurArr["utilisateur"], $utilisateur);
        }
        echo json_encode($UtilisateurArr);
    }

    else{
        http_response_code(404);
        echo json_encode(
            array("message" => "Aucun utilisateur trouvée.")
        );
    }
?>