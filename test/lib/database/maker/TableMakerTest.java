package lib.database.maker;

import org.junit.Test;

import uss.model.database.User;

public class TableMakerTest {

	TableMaker tm = new TableMaker(new User());
	
	@Test
	public void print(){
		System.out.println(tm);
	}
	
	
	@Test
	public void dropTest() {
		tm.dropTable();
	}
	
	@Test
	public void createTest() {
		tm.createTable();
	}

}
