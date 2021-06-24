<?php
    class Utilisateur{

        // Connection
        private $conn;

        // Table
        private $db_table = ["utilisateur", "roles"];

        // Columns
        public $IdUtilisateur;
        public $Nom;
        public $Prenom;
        public $Mail;
        public $MotDePasse;
        public $Statut;
        public $IdRole;
       

        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getUtilisateurs(){
            $sqlQuery = "SELECT * FROM " . $this->db_table[0] . "
                        INNER JOIN " . $this->db_table[1] ."
                        ON roles.IdRole = utilisateur.IdRole";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }

        // CREATE
        public function createUtilisateur(){
            if($this->VerifUser()==null){
                echo json_encode("we in");
                $sqlQuery = "INSERT INTO ". $this->db_table[0] ."
                        SET 
                            IdUtilisateur = :IdUtilisateur,
                            Nom = :Nom, 
                            Prenom  = :Prenom, 
                            Mail = :Mail, 
                            MotDePasse = :MotDePasse,
                            Statut = :Statut,
                            IdRole = :IdRole";


                $stmt = $this->conn->prepare($sqlQuery);

                // sanitize
                $this->IdUtilisateur=htmlspecialchars(strip_tags($this->IdUtilisateur));
                $this->Nom=htmlspecialchars(strip_tags($this->Nom));
                $this->Prenom=htmlspecialchars(strip_tags($this->Prenom));
                $this->Mail=htmlspecialchars(strip_tags($this->Mail));
                $this->MotDePasse=htmlspecialchars(strip_tags($this->MotDePasse));
                $this->Statut=htmlspecialchars(strip_tags($this->Statut));
                $this->IdRole=htmlspecialchars(strip_tags($this->IdRole));

                // bind data
                $stmt->bindParam(":IdUtilisateur", $this->IdUtilisateur);
                $stmt->bindParam(":Nom", $this->Nom);
                $stmt->bindParam(":Prenom", $this->Prenom);
                $stmt->bindParam(":Mail", $this->Mail);
                $stmt->bindParam(":MotDePasse", $this->MotDePasse);
                $stmt->bindParam(":Statut", $this->Statut);
                $stmt->bindParam(":IdRole", $this->IdRole);

                echo json_encode($this->IdUtilisateur." ". $this->Nom ." ". $this->Prenom ." ". $this->Mail . " ". $this->MotDePasse . " ". $this->Statut." ". $this->IdRole);
                echo json_encode($stmt);
                if($stmt->execute()){
                return true;
                }
                    return false;
            }
            else {
                echo "User existe déjà dans la base";
            }
        }
        public function VerifUser(){
            $sqlQuery = "SELECT
                         
            Nom, 
            Prenom , 
            Mail, 
            MotDePasse,
            Statut,
            IdRole
         
          FROM
            ". $this->db_table[0] ."
        WHERE 
        IdUtilisateur = ?
        LIMIT 0,1";

        $stmt = $this->conn->prepare($sqlQuery);

        $stmt->bindParam(1, $this->IdUtilisateur);

        $stmt->execute();

        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data; 

        }

    

    }