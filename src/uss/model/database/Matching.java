package uss.model.database;

import java.util.Date;

import lib.database.annotation.Key;

public class Matching {
	@Key
	private Integer male;
	@Key
	private Integer female;
	private Date date;

	public Integer getMale() {
		return male;
	}

	public void setMale(Integer male) {
		this.male = male;
	}

	public Integer getFemale() {
		return female;
	}

	public void setFemale(Integer female) {
		this.female = female;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
