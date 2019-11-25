//Joe Leon
//CST-361
//9-26-19
//This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
//We used source code from the following websites to complete this assignment:
//WEBSITE 1
//WEBSITE 2

package business;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.User;
import data.UserDataService;
import data.UserDataInterface;
import business.UserInterface;

@Stateless
@Local(UserInterface.class)
@LocalBean
public class UserManager implements UserInterface <User>
{ 
	@EJB
	UserDataInterface<User> UDA;
	
	/**
	 * Method ask the UserDTO to find if a user already exist within the database. 
	 * @param User user
	 * @return bool
	 * @see UserDataInterface#findBy(Object)
	 */
	@Override
	public boolean checkExistance(User user) {
		//If there is no matching users of the provided information, return false.
		if (UDA.findBy(user).getuName().equals(user.getuName())) {
			return false;
		} else
			return true;
	}
	
	/**
	 * This method searches for both a user and password to see if both entries match at once in the database.
	 * @param User
	 * @return boolean
	 */
	public boolean validateLogin(User user) {
		if (UDA.findBy(user).getuName().equals(user.getuName()) && UDA.findBy(user).getPassword().equals(user.getPassword())) {
			return false;
		} else
			return true;
	}
	//Trusted Softward development methodlogices. 
	
	/**If the check for if the username already exist returns false, (meaning that it doesn't exist)
	 * It will insert the information into the database via the UserDTO create(User user).
	 * 
	 * @param User user
	 * @return user
	 * @see UserDataService#create(User)
	 */
	public User processRegister(User user) {
		UDA.create(user);
		return user;
	}
	
}
