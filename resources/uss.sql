

DROP TABLE IF EXISTS TestResult;
CREATE TABLE TestResult(
	TestResult_id INTEGER AUTO_INCREMENT NOT NULL,
	TestResult_stringId VARCHAR(20) NOT NULL DEFAULT '',
	TestResult_result VARCHAR(20) NOT NULL DEFAULT '',
	
	PRIMARY KEY(TestResult_id)
);

INSERT INTO TestResult(TestResult_stringId, TestResult_result) VALUES('uss', '1234');

DROP TABLE IF EXISTS User;

CREATE TABLE User(
	User_id INTEGER AUTO_INCREMENT NOT NULL,
	User_stringId VARCHAR(20) NOT NULL DEFAULT '',
	User_name VARCHAR(20) NOT NULL DEFAULT '',
	User_email VARCHAR(255) NOT NULL DEFAULT '',
	User_password VARCHAR(20) NOT NULL DEFAULT '',
	User_company VARCHAR(20) NOT NULL DEFAULT '',
	User_phoneNumber VARCHAR(20) NOT NULL DEFAULT '',
	User_profile VARCHAR(255) NOT NULL DEFAULT '',
	User_cover VARCHAR(255) NOT NULL DEFAULT '',
	PRIMARY KEY(User_id)
);

INSERT INTO User VALUES(null, 'uss', 'name', 'email', 'password', 'company', 'phoneNumber', 'profile' ,'cover');
INSERT INTO User VALUES(null, 'uss2', 'name', 'email', 'password', 'company', 'phoneNumber', 'profile' ,'cover');
INSERT INTO User VALUES(null, 'uss3', 'name', 'email', 'password', 'company', 'phoneNumber', 'profile' ,'cover');
INSERT INTO User VALUES(null, 'uss4', 'name', 'email', 'password', 'company', 'phoneNumber', 'profile' ,'cover');

DROP TABLE IF EXISTS Relation;
CREATE TABLE Relation(
	Relation_id INTEGER NOT NULL,
	Relation_friendId INTEGER NOT NULL,
	PRIMARY KEY(Relation_id, Relation_friendId)
);

INSERT INTO Relation VALUES(1, 2);
INSERT INTO Relation VALUES(1, 3);
INSERT INTO Relation VALUES(1, 4);
