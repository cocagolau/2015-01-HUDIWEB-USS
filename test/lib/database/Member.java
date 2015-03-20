package lib.database;

import java.util.Date;

public class Member {

	private String name;
	private Integer age;
	private Date birthday;

	@Override
	public String toString() {
		return "test [name=" + name + ", age=" + age + ", birthday=" + birthday + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
