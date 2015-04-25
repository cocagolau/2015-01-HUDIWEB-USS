package uss.model;

public class User {

	private Integer id;
	private String stringId;
	private String name;
	private String email;
	private String password;
	private String company;
	private String phoneNumber;
	private String profile;
	private String cover;

	public User(Integer id, String stringId, String name, String email, String password, String company, String phoneNumber, String profile,
			String cover) {
		this.id = id;
		this.stringId = stringId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.company = company;
		this.phoneNumber = phoneNumber;
		this.profile = profile;
		this.cover = cover;
	}

	public Integer getId() {
		return id;
	}

	public String getStringId() {
		return stringId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getCompany() {
		return company;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getProfile() {
		return profile;
	}

	public String getCover() {
		return cover;
	}

	public Object[] getInsertParameters() {
		return new Object[] { stringId, name, email, password, company, phoneNumber, profile, cover };
	}
	
	public Object[] getUpdateParameters() {
		return new Object[] { name, email, password, company, phoneNumber, profile, cover, stringId };
	}
}
