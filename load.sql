CREATE DATABASE twisterdb;
USE twisterdb;

CREATE TABLE login(
	id		INTEGER PRIMARY KEY auto_increment,
	login		VARCHAR(255) UNIQUE,
	pwd		BLOB,
	nom		VARCHAR(255),
	prenom		VARCHAR(255)
);

CREATE TABLE friend(
	userId		INTEGER,
	friendId	INTEGER,
	time		TIMESTAMP,
	PRIMARY KEY(userId, friendId)
);


CREATE TABLE tw_session(
	id		INTEGER PRIMARY KEY,
	idUser		INTEGER,
	time		TIMESTAMP,
	clef		VARCHAR(255),
	isRoot		BOOLEAN
);

