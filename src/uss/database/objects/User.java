package uss.database.objects;

import lib.database.annotation.Key;

public class User {
	
	@Key
	private Integer id;
	private String stringId;
	private String nickName;
	private String password;
	private String profile;
	private Integer gender;

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", stringId=" + stringId + ", nickName=" + nickName + ", password=" + password + ", profile=" + profile
				+ ", gender=" + gender + "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

}
