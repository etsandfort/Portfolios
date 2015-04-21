USE jmelcher;

DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS Email;

CREATE TABLE Address (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  street VARCHAR(100) NOT NULL,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(100),
  zipcode VARCHAR(100) NOT NULL,
  country VARCHAR(100) NOT NULL
);

CREATE TABLE Email (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  emailAddress VARCHAR(100) NOT NULL
);

CREATE TABLE Person (
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	code VARCHAR(100) NOT NULL,
	lastName VARCHAR(100) NOT NULL,
	firstName VARCHAR(100) NOT NULL
);

INSERT INTO Address (street,city,state,zipcode,country) VALUES ('123 Nope', 'Detroit', 'MI', '58321','USA');
INSERT INTO Address (street,city,state,zipcode,country) VALUES ('789 Yep','Coolio','AK','45645','USA');
INSERT INTO Address (street,city,state,zipcode,country) VALUES ('444 Heee', 'Froro', 'Fjorden', '66666', 'Norway');
INSERT INTO Address (street,city,state,zipcode,country) VALUES ('7777 Bono', 'Playy', 'KS', '66211', 'USA');
INSERT INTO Address (street,city,state,zipcode,country) VALUES ('44566 High', 'Rett', 'QU', '78999', 'Canada');

INSERT INTO Email (emailAddress) VALUES ('yolo@gmail.com');
INSERT INTO Email (emailAddress) VALUES ('foobar@soh.com');
INSERT INTO Email (emailAddress) VALUES ('leenkiat@chair.net');
INSERT INTO Email (emailAddress) VALUES ('softkiatty@yeah.ca');

INSERT INTO Person (code, lastName, firstName) VALUES ('1456','Mama','Yo');
INSERT INTO Person (code, lastName, firstName) VALUES ('mmm','McDonald','Ronald');
INSERT INTO Person (code, lastName, firstName) VALUES ('toy','Barker','Bob');