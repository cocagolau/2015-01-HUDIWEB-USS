package uss.launcher;

import uss.model.database.User;
import lib.database.DAO;
import lib.database.exception.RegexNotMatches;
import lib.database.maker.PackageCreator;

public class DBLauncher {
	public static void main(String[] args) throws Exception {
		PackageCreator.createTable(true, "uss.model.database");
		insertTestData();
	}

	private static void insertTestData() throws RegexNotMatches {
		DAO dao = new DAO();
		User user = new User();
		user.setNickName("abc");
		dao.insert(user);
	}
}
