<?php
    class Entreprise{

        // Connection
        private $conn;

        // Table
        private $db_table = "ENTREPRISE";

        // Columns
        public $IdEntreprise;
        public $Designation;
        public $Logo;
        public $Description;
        public $url;
        public $Statut;
        

        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getEntreprises(){
            $sqlQuery = "SELECT IdEntreprise, Designation, Logo, Description, url, Statut FROM " . $this->db_table . "";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }

        //IdAeroport, 
        public function VerifEntreprise(){
            $sqlQuery = "SELECT                                 
            IdEntreprise, 
            Designation,
            Logo,
            Description,
            url,
            Statut                    
          FROM
            ". $this->db_table ."
            WHERE 
            IdEntreprise = ?
            LIMIT 0,1";

            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->bindParam(1, $this->IdEntreprise);
            $stmt->execute();

            $data = $stmt->fetch(PDO::FETCH_ASSOC);
            return $data; 
        }
        // GET SINGLE
        public function getSingleEntreprise(){
            $dataRow = $this->VerifEntreprise();
            $this->IdEntreprise = $dataRow['IdEntreprise'];
            $this->Designation = $dataRow['Designation'];
            $this->Logo = $dataRow['Logo'];
            $this->Description = $dataRow['Description'];
            $this->url = $dataRow['url'];
            $this->Statut = $dataRow['Statut'];
        }

        // UPDATE
        public function updateEntreprise(){
            $sqlQuery = "UPDATE
                        ". $this->db_table ."
                    SET
                        Designation = :Designation, 
                        Logo  = :Logo, 
                        Description = :Description, 
                        url = :url,
                        Statut = :Statut                     
                    WHERE 
                        IdEntreprise = :IdEntreprise";

            $stmt = $this->conn->prepare($sqlQuery);

            $this->Designation=htmlspecialchars(strip_tags($this->Designation));
            $this->Logo =htmlspecialchars(strip_tags($this->Logo));
            $this->Description=htmlspecialchars(strip_tags($this->Description));
            $this->url=htmlspecialchars(strip_tags($this->url));
            $this->Statut=htmlspecialchars(strip_tags($this->Statut));
            $this->IdEntreprise=htmlspecialchars(strip_tags($this->IdEntreprise));

            // bind data
            $stmt->bindParam(":Designation", $this->Designation);
            $stmt->bindParam(":Logo", $this->Logo);
            $stmt->bindParam(":Description", $this->Description);
            $stmt->bindParam(":url", $this->url);
            $stmt->bindParam(":Statut", $this->Statut);
            $stmt->bindParam(":IdEntreprise", $this->IdEntreprise);

            if($stmt->execute()){
            return true;
            }
            return false;
        }


    }
?>

