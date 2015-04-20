

DROP TABLE IF EXISTS TestResult;
CREATE TABLE TestResult(
	TestResult_id INTEGER AUTO_INCREMENT NOT NULL,
	TestResult_stringId VARCHAR(20) NOT NULL DEFAULT '',
	TestResult_result VARCHAR(20) NOT NULL DEFAULT '',
	
	PRIMARY KEY(TestResult_id)
);

INSERT INTO TestResult(TestResult_stringId, TestResult_result) VALUES('uss', '1234');

