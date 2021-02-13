package model;
import java.util.ArrayList;

public class Classroom {
	
	private ArrayList<UserAccount> userAccountList;

	public Classroom() {
		userAccountList = new ArrayList<UserAccount>();
	}

	public ArrayList<UserAccount> getUserAccountList() {
		return userAccountList;
	}

	public void setUserAccountList(ArrayList<UserAccount> userAccountList) {
		this.userAccountList = userAccountList;
	}
	
	public String registerUser(String username, String password, Gender gender, boolean software, boolean telematic, boolean industrial, String birthday, Browser browser) {
		String text = "This user already exists";
		UserAccount user = searchUser(username);
		if(user == null) {
			UserAccount newAccount = new UserAccount(username, password, gender, software, telematic, industrial, birthday, browser);
			userAccountList.add(newAccount);
			text = "The account was created successfully";
		}
		return text;
	}
	
	public UserAccount searchUser(String name) {
		UserAccount foundUser = null;
		for(UserAccount i: userAccountList) {
			if(name.equals(i.getUsername())) {
				foundUser = i;
			}
		}
		return foundUser;
	}
	
	public boolean checkPassword(String name, String password) {
		boolean equals = false;
		try {
			if(password.equals(searchUser(name).getPassword())) {
				equals = true;
			}
		}catch(NullPointerException npe) {
			
		}
		return equals;
	}
	
}
