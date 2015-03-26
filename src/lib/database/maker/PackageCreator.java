package lib.database.maker;

import lib.mapping.dispatch.support.ClassFinder;

public class PackageCreator {
	public static void createTable(boolean ifExistDrop, String packagePath) {
		ClassFinder cf = new ClassFinder();
		cf.find(packagePath).forEach(cLass -> {
			TableMaker tm = new TableMaker(cLass);
			if (ifExistDrop)
				tm.dropTable();
			tm.createTable();
		});
	}
	
	
}
