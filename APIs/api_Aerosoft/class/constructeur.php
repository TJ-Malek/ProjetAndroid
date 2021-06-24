<?php
    class Constructeur{

        // Connection
        private $conn;

        // Table
        private $db_table = "constructeur";

        // Columns
        public $IdConstructeur;
        public $NomConstructeur;
       
        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getConstructeurs(){
            $sqlQuery = "SELECT IdConstructeur, NomConstructeur FROM " . $this->db_table . "";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }
        // CREATE
        public function createConstructeur(){
            $sqlQuery = "INSERT INTO
                        ". $this->db_table ."
                    SET
                        IdConstructeur = :IdConstructeur, 
                        NomConstructeur  = :NomConstructeur, 
                        created = :created";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            // sanitize
            $this->IdConstructeur=htmlspecialchars(strip_tags($this->IdConstructeur));
            $this->NomConstructeur=htmlspecialchars(strip_tags($this->NomConstructeur ));
            
            // bind data
            $stmt->bindParam(":IdConstructeur", $this->IdConstructeur);
            $stmt->bindParam(":NomConstructeur", $this->NomConstructeur);
                 
            if($stmt->execute()){
               return true;
            }
            return false;
        }

        // GET SINGLE
        public function getSingleConstructeur(){
            $sqlQuery = "SELECT
                        IdConstructeur, 
                        NomConstructeur,            
                      FROM
                        ". $this->db_table ."
                    WHERE 
                    IdConstructeur = ?
                    LIMIT 0,1";

            $stmt = $this->conn->prepare($sqlQuery);

            $stmt->bindParam(1, $this->IdConstructeur);

            $stmt->execute();

            $dataRow = $stmt->fetch(PDO::FETCH_ASSOC);
            
            $this->IdAeroport = $dataRow['IdConstructeur'];
            $this->NomConstructeur = $dataRow['NomConstructeur'];
           
        }        

        // UPDATE
        public function updateConstructeur(){
            $sqlQuery = "UPDATE
                        ". $this->db_table ."
                    SET
                    IdConstructeur = :IdConstructeur, 
                    NomConstructeur = :NomConstructeur , 
                    WHERE 
                    IdConstructeur = :IdConstructeur";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->IdConstructeur=htmlspecialchars(strip_tags($this->IdConstructeur));
            $this->NomConstructeur =htmlspecialchars(strip_tags($this->NomConstructeur));
        
            // bind data
            $stmt->bindParam(":IdConstructeur", $this->IdConstructeur);
            $stmt->bindParam(":NomConstructeur ", $this->NomConstructeur );
        
            if($stmt->execute()){
               return true;
            }
            return false;
        }

        // DELETE
        function deleteConstructeur(){
            $sqlQuery = "DELETE FROM " . $this->db_table . " WHERE IdConstructeur = ?";
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->IdConstructeur=htmlspecialchars(strip_tags($this->IdConstructeur));
        
            $stmt->bindParam(1, $this->IdConstructeur);
        
            if($stmt->execute()){
                return true;
            }
            return false;
        }

        public function VerifConstructeur(){
            $sqlQuery = "SELECT
                         
            IdConstructeur, 
            NomConstructeur, 
             
          FROM
            ". $this->db_table ."
        WHERE 
            IdConstructeur = ?
        LIMIT 0,1";

        $stmt = $this->conn->prepare($sqlQuery);

        $stmt->bindParam(1, $this->IdConstructeur);

        $stmt->execute();

        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data; 

        }
        

      

    }
?>

