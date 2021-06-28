<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    include_once '../../config/database.php';
    include_once '../../class/utilisateur.php';
    
    $database = new Database();
    $db = $database->getConnection();
    
    $item = new Utilisateur($db);
    
    $data = json_decode(file_get_contents("php://input"));

    echo json_encode($data);
    
    
    // utilisateur values
    $item->IdUtilisateur = $data->IdUtilisateur;
    $item->NomUtilisateur = $data->NomUtilisateur;
    $item->Mail = $data->Mail;
    $item->MotDePasse = $data->MotDePasse;
    $item->Statut = $data->Statut;

    //echo json_encode($item);
    
    if($item->updateUtilisateur()){
        $res = array(
            "res" => "ok"
        );
        echo json_encode($res);
    } else{
        $res = array(
            "res" => "Erreur : Les paramètres de l'utilisateur ne sont pas modifié."
        );
        echo json_encode($res);
        // echo json_encode("Erreur : Le vol n'a pas été modifié.");
    }
?>