package lib.database.maker;

import java.lang.reflect.Field;

import lib.database.DAO;

public class TableMaker {

	private DAO dao;
	private Class<?> tableClass;
	private String tableName;

	public TableMaker(Object tableObj) {
		tableClass = tableObj.getClass();

	}

	private static final String CREATE_TABLE = "CREATE TABLE ? %s ENGINE = InnoDB DEFAULT CHARACTER SET utf8;";

	public void createTable(){
		dao.execute(String.format(CREATE_TABLE, ), tableName);
	}

	private static final String DROP_TABLE = "DROP TABLE IF EXISTS ?";

	public void dropTable() {
		dao.execute(DROP_TABLE, tableName);
	}

	private String getColumString() {
		Field[] fields = tableClass.getDeclaredFields();
		FieldMaker fm = new FieldMaker(fields[1]);
	}
	

}
