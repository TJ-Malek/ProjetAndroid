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
            do {
                $this->IdUtilisateur =  rand(00000000,99999999);
            } while($this->VerifUser()!=null);
            if(($this->VerifUser()==null)&&($this->VerifUserMail()==null)){
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
                //$this->IdUtilisateur=htmlspecialchars(strip_tags($this->IdUtilisateur));
                $this->Nom=htmlspecialchars(strip_tags($this->Nom));
                $this->Prenom=htmlspecialchars(strip_tags($this->Prenom));
                $this->Mail=htmlspecialchars(strip_tags($this->Mail));
                $this->MotDePasse=htmlspecialchars(strip_tags($this->MotDePasse));
                $this->Statut=0;
                $this->IdRole="11111";

                // bind data
                $stmt->bindParam(":IdUtilisateur", $this->IdUtilisateur);
                $stmt->bindParam(":Nom", $this->Nom);
                $stmt->bindParam(":Prenom", $this->Prenom);
                $stmt->bindParam(":Mail", $this->Mail);
                $stmt->bindParam(":MotDePasse", $this->MotDePasse);
                $stmt->bindParam(":Statut", $this->Statut);
                $stmt->bindParam(":IdRole", $this->IdRole);

                if($stmt->execute()){
                    http_response_code(201);
                return true;
                }
                    return false;
            }
            else {
               
                echo "User existe déjà dans la base";
            }
        }
        public function VerifUser(){
            $sqlQuery = "SELECT *         
          FROM
            ". $this->db_table[0] ."
            INNER JOIN " . $this->db_table[1] ."
            ON roles.IdRole = utilisateur.IdRole
        WHERE 
        IdUtilisateur = ?
        LIMIT 0,1";

        $stmt = $this->conn->prepare($sqlQuery);

        $stmt->bindParam(1, $this->IdUtilisateur);

        $stmt->execute();

        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data; 

        }
        public function VerifUserMail(){
            $sqlQuery = "SELECT *         
          FROM
            ". $this->db_table[0] ."
            INNER JOIN " . $this->db_table[1] ."
            ON roles.IdRole = utilisateur.IdRole
        WHERE 
        Mail = ?
        LIMIT 0,1";

        $stmt = $this->conn->prepare($sqlQuery);

        $stmt->bindParam(1, $this->Mail);

        $stmt->execute();

        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data; 

        }
        public function getSingleUtilisateur(){
           
            $dataRow = $this->VerifUser();
            $this->Nom = $dataRow['Nom'];
            $this->Prenom  = $dataRow['Prenom'];
            $this->Mail = $dataRow['Mail'];
            $this->Statut = $dataRow['Statut'];
            $this->RoleNom = $dataRow['RoleNom'];
           
        }
        
        public function GetUserToActivate(){
            $sqlQuery = "SELECT * FROM " . $this->db_table[0] . "
                        INNER JOIN " . $this->db_table[1] ."
                        ON roles.IdRole = utilisateur.IdRole
                        WHERE Statut = 0";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }

        public function activateUser(){
            $sqlQuery = "UPDATE
                        ". $this->db_table[0] ."
                    SET
                        Statut = 1
                    WHERE 
                        IdUtilisateur = :IdUtilisateur";
        
            $stmt = $this->conn->prepare($sqlQuery);
            $this->IdUtilisateur=htmlspecialchars(strip_tags($this->IdUtilisateur));
        
            // bind data

            $stmt->bindParam(":IdUtilisateur", $this->IdUtilisateur);
        
            if($stmt->execute()){
                return true;
            }
            return false;
        }  
        
        // UPDATE
        public function updateUtilisateur(){
            $sqlQuery = "UPDATE
                        ". $this->db_table[0] ."
                    SET
                        Nom = :Nom, 
                        Prenom  = :Prenom, 
                        Mail = :Mail, 
                        MotDePasse = :MotDePasse
                        
                    WHERE 
                        IdUtilisateur = :IdUtilisateur";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            
            $this->Nom=htmlspecialchars(strip_tags($this->Nom));
            $this->Prenom=htmlspecialchars(strip_tags($this->Prenom));
            $this->Mail=htmlspecialchars(strip_tags($this->Mail));
            $this->MotDePasse=htmlspecialchars(strip_tags($this->MotDePasse));
            $this->IdUtilisateur=htmlspecialchars(strip_tags($this->IdUtilisateur));
        
            // bind data
            $stmt->bindParam(":Nom", $this->Nom);
            $stmt->bindParam(":Prenom", $this->Prenom);
            $stmt->bindParam(":Mail", $this->Mail);
            $stmt->bindParam(":MotDePasse", $this->MotDePasse);
            $stmt->bindParam(":IdUtilisateur", $this->IdUtilisateur);
        
            if($stmt->execute()){
                return true;
            }
            return false;
        }  
        
        // DELETE
        function deleteUtilisateur(){
            $sqlQuery = "DELETE FROM " . $this->db_table[0] . " WHERE IdUtilisateur = ?";
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->IdUtilisateur=htmlspecialchars(strip_tags($this->IdUtilisateur));
        
            $stmt->bindParam(1, $this->IdUtilisateur);
            echo "okkkkkkkkkkk";
            echo "IdUtilisateur $this->IdUtilisateur";
            if($stmt->execute()){
                return true;
            }
            return false;
        }

    }