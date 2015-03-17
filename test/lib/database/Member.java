package lib.database;

import java.util.Date;

public class Member {

	private String name;
	private int age;
	private Date birthday;

	@Override
	public String toString() {
		return "test [name=" + name + ", age=" + age + ", birthday=" + birthday + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
