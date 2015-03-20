DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
	`User_id` integer auto_increment NOT NULL,
	`User_stringId` VARCHAR(255) NOT NULL DEFAULT '',
	`User_nickName` VARCHAR(255) NOT NULL DEFAULT '',
	`User_password` VARCHAR(255) NOT NULL DEFAULT '',
	`User_profile` text NOT NULL DEFAULT '',
	`User_gender` VARCHAR(255) NOT NULL DEFAULT '',
	
	PRIMARY KEY(`User_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `UserPhoto`;
CREATE TABLE `UserPhoto` (
	`UserPhoto_id` integer auto_increment NOT NULL,
	`UserPhoto_userId` integer NOT NULL DEFAULT 0,
	`UserPhoto_url` text NOT NULL DEFAULT '',
	PRIMARY KEY(`UserPhoto_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `UserPoint`;
CREATE TABLE `UserPoint` (
	`UserPoint_id` integer auto_increment NOT NULL,
	`UserPoint_userId` integer NOT NULL DEFAULT 0,
	`UserPoint_url` text NOT NULL DEFAULT '',
	PRIMARY KEY(`UserPoint_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `Matching`;
CREATE TABLE `Matching` (
	`Matching_female` integer NOT NULL,
	`Matching_male` integer NOT NULL DEFAULT 0,
	`Matching_date` date NOT NULL,
	PRIMARY KEY(`Matching_female`, `Matching_male`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;