//Joe Leon
//CST-361
//9-26-19
//This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
//We used source code from the following websites to complete this assignment:
//WEBSITE 1
//WEBSITE 2

package business;

import beans.User;

public interface UserInterface 
{
	boolean validateLogin(User user);
	
	boolean checkExistance(User user);

	User processRegister(User user);
}
