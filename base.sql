CREATE DATABASE  sprint_extra;
USE sprint_extra;

CREATE TABLE sexe(
    id int PRIMARY KEY AUTO_INCREMENT,
    genre VARCHAR(100)

);

INSERT INTO sexe(genre) VALUES 
('Masculin'),
('Feminin');

CREATE TABLE patient(
    id int PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    date_naissance DATE,
    lieu_naissance VARCHAR(100),
    adresse VARCHAR(50),
    id_sexe INT,

    FOREIGN KEY  (id_sexe) REFERENCES sexe(id)
);


INSERT INTO patient(nom,date_naissance,lieu_naissance,adresse,id_sexe) VALUES
('Koto','2004-03-13','Befelatanana','Lot VI 17 JZ Anosy','1'),
('Julia','2006-02-11','HJRA','Lot VII 12 TER Andoharanifotsy','2');


CREATE TABLE role (
    id INT PRIMARY KEY AUTON_INCREMENT ,
    nom VARCHAR(100)

);