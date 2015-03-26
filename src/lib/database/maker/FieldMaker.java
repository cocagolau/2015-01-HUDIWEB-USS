package lib.database.maker;

import java.lang.reflect.Field;

public class FieldMaker {
	
	private String prefix;

	public FieldMaker(String prefix, Field field) {
		this.prefix = prefix;
	}

}
