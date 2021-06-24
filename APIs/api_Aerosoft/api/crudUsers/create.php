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

    $item->IdUtilisateur = $data->IdUtilisateur;
    $item->Nom = $data->Nom;
    $item->Prenom = $data->Prenom;
    $item->Mail = $data->Mail;
    $item->MotDePasse = $data->MotDePasse;
    $item->Statut = $data->Statut;
    $item->IdRole = $data->IdRole;

    if($item->createUtilisateur()){
        echo 'Utilisateur created successfully.';
    }
?>