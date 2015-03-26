package lib.database.maker;

import org.apache.catalina.User;
import org.junit.Test;

public class TableMakerTest {

	TableMaker tm = new TableMaker(User.class);
	
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
