package uss.right;

import uss.database.objects.User;

public class UserUpdateRight implements Right {
	
	boolean right;

	public UserUpdateRight(User loggedUser, User updateUser){
		right = loggedUser.getId().equals(updateUser.getId());
	}
	
	@Override
	public boolean hasRight() {
		return right;
	}
	
}
