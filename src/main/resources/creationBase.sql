create table ville (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, nom varchar(255) NOT NULL, code varchar(255) NOT NULL, population int, departement_id int NOT NULL, region_id int NOT NULL, pays_id int NOT NULL);

create table departement (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, nom varchar(255) NOT NULL, code varchar(255) NOT NULL, population int, region_id int NOT NULL, pays_id int NOT NULL);

create table region (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, nom varchar(255) NOT NULL, code varchar(255) NOT NULL, population int, pays_id int NOT NULL);

create table pays (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, nom varchar(255) NOT NULL, code varchar(255) NOT NULL, population int);