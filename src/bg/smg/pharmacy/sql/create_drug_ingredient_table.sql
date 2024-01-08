CREATE TABLE drug_ingredient (
   drug_id INT,
   ingredient_id INT,
   ingredient_weight INT,
   PRIMARY KEY (drug_id, ingredient_id),
   FOREIGN KEY (drug_id) REFERENCES drug (drug_id),
   FOREIGN KEY (ingredient_id) REFERENCES ingredient (ingredient_id)
);
