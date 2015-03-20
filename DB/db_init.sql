DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
	`User_id` INTEGER AUTO_INCREMENT NOT NULL,
	`User_stringId` VARCHAR(255) NOT NULL DEFAULT '',
	`User_nickName` VARCHAR(255) NOT NULL DEFAULT '',
	`User_password` VARCHAR(255) NOT NULL DEFAULT '',
	`User_profile` TEXT NOT NULL DEFAULT '',
	`User_gender` TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY(`User_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `UserPhoto`;
CREATE TABLE `UserPhoto` (
	`UserPhoto_id` INTEGER auto_increment NOT NULL,
	`UserPhoto_userId` INTEGER NOT NULL DEFAULT 0,
	`UserPhoto_url` TEXT NOT NULL DEFAULT '',
	PRIMARY KEY(`UserPhoto_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `UserPoint`;
CREATE TABLE `UserPoint` (
	`UserPoint_id` INTEGER auto_increment NOT NULL,
	`UserPoint_userId` INTEGER NOT NULL DEFAULT 0,
	`UserPoint_url` TEXT NOT NULL DEFAULT '',
	PRIMARY KEY(`UserPoint_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `Matching`;
CREATE TABLE `Matching` (
	`Matching_female` INTEGER NOT NULL,
	`Matching_male` INTEGER NOT NULL DEFAULT 0,
	`Matching_date` DATE NOT NULL,
	PRIMARY KEY(`Matching_female`, `Matching_male`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;
