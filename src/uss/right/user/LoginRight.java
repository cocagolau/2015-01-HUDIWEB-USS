package uss.right.user;

import uss.database.objects.User;
import uss.right.Right;

public class LoginRight implements Right {
	
	boolean right;

	public LoginRight(User loggedUser, User user){
		right = loggedUser.getPassword().equals(user.getPassword());
	}
	
	@Override
	public boolean hasRight() {
		return right;
	}
	
}
