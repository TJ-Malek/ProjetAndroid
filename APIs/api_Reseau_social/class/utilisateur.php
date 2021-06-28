<?php
    class Utilisateur{

        // Connection
        private $conn;

        // Table
        private $db_table = "utilisateur";

        // Columns
        public $IdUtilisateur;
        public $NomUtilisateur;
        public $MotDePasse;
        public $Mail;
        public $Role;
        public $Statut;
        

        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getUtilisateurs(){
            $sqlQuery = "SELECT * FROM " . $this->db_table . "";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }

        //IdAeroport, 
        public function VerifUtilisateur(){
            $sqlQuery = "SELECT * FROM
            ". $this->db_table ."
            WHERE 
            IdUtilisateur = ?
            LIMIT 0,1";

            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->bindParam(1, $this->IdUtilisateur);
            $stmt->execute();

            $data = $stmt->fetch(PDO::FETCH_ASSOC);
            return $data; 
        }

        // GET SINGLE
        public function getSingleUtilsateur(){
            $dataRow = $this->VerifUtilisateur();
            $this->IdUtilisateur = $dataRow['IdUtilisateur'];
            $this->NomUtilisateur = $dataRow['NomUtilisateur'];
            $this->Mail = $dataRow['Mail'];
            $this->Role = $dataRow['Role'];
            $this->Statut = $dataRow['Statut'];
        }

        // UPDATE
        public function updateUtilisateur(){
            $sqlQuery = "UPDATE
                        ". $this->db_table ."
                    SET
                        NomUtilisateur = :NomUtilisateur, 
                        Mail = :Mail,
                        MotDePasse = :MotDePasse, 
                        Statut = :Statut                     
                    WHERE 
                    IdUtilisateur = :IdUtilisateur";

            $stmt = $this->conn->prepare($sqlQuery);

            $this->NomUtilisateur=htmlspecialchars(strip_tags($this->NomUtilisateur));
            $this->Mail =htmlspecialchars(strip_tags($this->Mail));
            $this->MotDePasse=htmlspecialchars(strip_tags($this->MotDePasse));
            $this->Statut=htmlspecialchars(strip_tags($this->Statut));
            $this->IdUtilisateur=htmlspecialchars(strip_tags($this->IdUtilisateur));

            // bind data
            $stmt->bindParam(":NomUtilisateur", $this->NomUtilisateur);
            $stmt->bindParam(":Mail", $this->Mail);
            $stmt->bindParam(":MotDePasse", $this->MotDePasse);
            $stmt->bindParam(":Statut", $this->Statut);
            $stmt->bindParam(":IdUtilisateur", $this->IdUtilisateur);

            if($stmt->execute()){
            return true;
            }
            return false;
        }

        // CREATE
        public function createUtilisateur(){
            if($this->VerifUtilisateur()==null){
                $sqlQuery = "INSERT INTO ". $this->db_table ."
                        SET 
                            IdUtilisateur = :IdUtilisateur,
                            NomUtilisateur = :NomUtilisateur, 
                            MotDePasse  = :MotDePasse, 
                            Mail = :Mail,
                            Role = :Role,
                            Statut = :Statut";


                $stmt = $this->conn->prepare($sqlQuery);

                // sanitize
                $this->IdUtilisateur=htmlspecialchars(strip_tags($this->IdUtilisateur));
                $this->NomUtilisateur=htmlspecialchars(strip_tags($this->NomUtilisateur));
                $this->MotDePasse=htmlspecialchars(strip_tags($this->MotDePasse));
                $this->Mail=htmlspecialchars(strip_tags($this->Mail));
                $this->Role=htmlspecialchars(strip_tags($this->Role));
                $this->Statut=0;
                

                // bind data
                $stmt->bindParam(":IdUtilisateur", $this->IdUtilisateur);
                $stmt->bindParam(":NomUtilisateur", $this->NomUtilisateur);
                $stmt->bindParam(":MotDePasse", $this->MotDePasse);
                $stmt->bindParam(":Mail", $this->Mail);
                $stmt->bindParam(":Role", $this->Role);
                $stmt->bindParam(":Statut", $this->Statut);

                if($stmt->execute()){
                return true;
                }
                    return false;
            }
            else {
                echo "User existe déjà dans la base";
            }
        }

        // DELETE
        function deleteUtilisateur(){
            $sqlQuery = "DELETE FROM " . $this->db_table . " WHERE IdUtilisateur = ?";
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->IdUtilisateur=htmlspecialchars(strip_tags($this->IdUtilisateur));
        
            $stmt->bindParam(1, $this->IdUtilisateur);
            
            echo "IdUtilisateur $this->IdUtilisateur";
            if($stmt->execute()){
                return true;
            }
            return false;
        }
    }
?>

