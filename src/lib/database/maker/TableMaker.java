package lib.database.maker;

import java.lang.reflect.Field;

import lib.database.DAO;
import lib.database.SqlField;
import lib.database.SqlFieldNormal;
import lib.database.annotation.Key;
import lib.database.annotation.OtherTable;
import lib.database.annotation.Table;
import lib.database.annotation.Unique;

public class TableMaker {

	private DAO dao;
	private Class<?> tableClass;
	private String tableName;

	public TableMaker(Object tableObj) {
		dao = new DAO();
		tableClass = tableObj.getClass();
		tableName = tableClass.getSimpleName();
		if (!tableClass.isAnnotationPresent(Table.class))
			return;
		Table table = tableClass.getAnnotation(Table.class);
		if (!table.value().equals(""))
			tableName = table.value();
	}

	private static final String CREATE_TABLE = "CREATE TABLE `%s` %s ENGINE = InnoDB DEFAULT CHARACTER SET utf8;";

	public void createTable() {
		dao.execute(String.format(CREATE_TABLE, tableName, getColumnString()));
	}

	private static final String DROP_TABLE = "DROP TABLE IF EXISTS `%s`";

	public void dropTable() {
		dao.execute(String.format(DROP_TABLE, tableName));
	}

	private static final String PRIMARY_KEY = "PRIMARY KEY";
	private static final String UNIQUE = "UNIQUE";

	private String getColumnString() {
		Field[] fields = tableClass.getDeclaredFields();
		String result = "(";

		Function primaryKey = new Function();
		Function unique = new Function();

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(OtherTable.class))
				continue;
			SqlFieldNormal fm = (SqlFieldNormal) SqlField.getInstance(fields[i]);
			result += fm.getFieldString() + ", ";
			if (fields[i].isAnnotationPresent(Key.class)) {
				primaryKey.add(fm);
			}
			if (fields[i].isAnnotationPresent(Unique.class)) {
				unique.add(fm);
			}
		}

		result += primaryKey.getFunctionString(PRIMARY_KEY);

		if (unique.hasItem())
			result += ", " + unique.getFunctionString(UNIQUE);

		result += ")";
		return result;
	}

	@Override
	public String toString() {
		return tableName + getColumnString();
	}
}
