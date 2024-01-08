CREATE TABLE drug (
   drug_id INT PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   price DECIMAL(10, 2) NOT NULL,
   description TEXT,
   prescription_required BOOLEAN NOT NULL,
   standard_dosage VARCHAR(255)
);
