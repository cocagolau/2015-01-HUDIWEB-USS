package uss.right.user;

import uss.database.objects.User;
import uss.right.Right;

public class ReadRight implements Right {
	
	boolean right;

	public ReadRight(User loggedUser, User updateUser){
		right = loggedUser.getId().equals(updateUser.getId());
	}
	
	@Override
	public boolean hasRight() {
		return right;
	}
	
}
