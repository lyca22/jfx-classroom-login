package model;

public class UserAccount {
	
	private String username;
	private String password;
	private Gender gender;
	private boolean software;
	private boolean telematic;
	private boolean industrial;
	private Date birthday;
	private Browser favoriteBrowser;
	
	public UserAccount(String username, String password, Gender gender, boolean software, boolean telematic, boolean industrial, String birthday, Browser browser) {
		setUsername(username);
		setPassword(password);
		setGender(gender);
		setSoftware(software);
		setTelematic(telematic);
		setIndustrial(industrial);
		String[] dates = birthday.split("-");
		this.birthday = (new Date(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0])));
		setFavoriteBrowser(browser);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public boolean isSoftware() {
		return software;
	}

	public void setSoftware(boolean software) {
		this.software = software;
	}

	public boolean isTelematic() {
		return telematic;
	}

	public void setTelematic(boolean telematic) {
		this.telematic = telematic;
	}

	public boolean isIndustrial() {
		return industrial;
	}

	public void setIndustrial(boolean industrial) {
		this.industrial = industrial;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		String[] dates = birthday.split("-");
		this.birthday.setDay(Integer.parseInt(dates[2]));
		this.birthday.setMonth(Integer.parseInt(dates[1]));
		this.birthday.setYear(Integer.parseInt(dates[0]));
	}

	public void setBirthday(int day, int month, int year) {
		this.birthday.setDay(day);
		this.birthday.setMonth(month);
		this.birthday.setYear(year);
	}
	
	public Browser getFavoriteBrowser() {
		return favoriteBrowser;
	}

	public void setFavoriteBrowser(Browser favoriteBrowser) {
		this.favoriteBrowser = favoriteBrowser;
	}
	
	public String getCareer() {
		String text = "";
		if(software) {
			text += "Software Engineer   ";
		}
		if(telematic) {
			text += "Telematic Engineer   ";
		}
		if(industrial) {
			text += "Industrial Engineer   ";
		}
		return text;
	}

	public String getBirthdayDate() {
		String date = birthday.getDay() + "/" + birthday.getMonth() + "/" + birthday.getYear();
		return date;
	}
	
	public String getFavoriteBrowserInString() {
		String text = "";
		switch(favoriteBrowser) {
		case Google_Chrome:
			text = "Google Chrome";
			break;
		case Opera:
			text = "Opera";
			break;
		case Microsoft_Edge:
			text = "Microsoft Edge";
			break;
		case Tor:
			text = "Tor";
			break;
		}
		return text;
	}
	
}
