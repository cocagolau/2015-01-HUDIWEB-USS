package uss.model.database;

import lib.database.annotation.Column;
import lib.database.annotation.Key;

public class UserPhoto {
	@Key(AUTO_INCREMENT = true)
	private Integer id;
	@Column(DATA_TYPE = "TEXT")
	private String userId;
	private String url;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
