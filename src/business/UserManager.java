/**Joe Leon
**CST-361
**9-26-19
**This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
**/
package business;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.User;
import data.UserDataService;
import data.UserDataInterface;
import business.UserInterface;

@Stateless
@Local(UserInterface.class)
@LocalBean
public class UserManager implements UserInterface <User>
{ 
	//Inject the UserDataService using the User Object for its <T>.
	@Inject
	UserDataInterface<User> UDA;
	
	//Setup the logger.
	Logger logger = LoggerFactory.getLogger(BatchManager.class);
	
	/**
	 * Method ask the UserDTO to find if a user already exist within the database. 
	 * @param User user
	 * @return bool
	 * @see UserDataInterface#findBy(Object)
	 */
	@Override
	public boolean checkExistance(User user) {
		logger.info("Entering | UserManager.checkExistance(user)");
		//If there is no matching users of the provided information, return false.
		if (UDA.findBy(user).getuName().equals(user.getuName())) {
			logger.info("User found | UserManager.checkExistnace(user)");
			return false;
		} else
			logger.info("User does not exist | UserManager.checkExistance(user)");
			return true;
	}
	
	/**
	 * This method searches for both a user and password to see if both entries match at once in the database.
	 * @param User user
	 * @return int
	 */
	public int validateLogin(User user) 
	{
		logger.info("Entering | UserManager.validateLogin(user)");
		/**Checks the database for a match of both username and password. And then checks the properties of the user.getIsMod()
		**to see which value to return.
		* 0 = not found.
		* 1 = normal user.
		* 2 = admin user.
		*/
		if (UDA.findBy(user).getuName().equals(user.getuName()) && UDA.findBy(user).getPassword().equals(user.getPassword())) {
			{
				if(checkAdmin(user) == true)
				{
					logger.info("User is Admin. | UserManager.validateLogin(user)");
					return 2;
				}
				logger.info("User is a normal user. | UserManager.validateLogin(user)");
				return 1;
			}
		} 
		else
		logger.info("User doesn't exits. | UserManager.validateLogin(user)");
		return 0;
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
		logger.info("Entering | processRegister(user)");
		UDA.create(user);
		logger.info("Regstration processed! | processRegister(user)");
		return user;
	}
	
	/**Takes in the user object and checks to see what the value is in the getIsMod variable.
	 * @param User user
	 * @return boolean
	 */
	@Override
	public boolean checkAdmin(User user)
	{
		logger.info("Entering | UserManager.checkAdmin(user)");
		//If getIsMod = 1, then the user is a admin.
		//otherwise, they are a normal user.
		if(UDA.findBy(user).getIsMod() == 1)
		{
			
			logger.info("User is a admin | UserManger.checkAdmin(user)");
			return true;
		}else
			logger.info("User is not an admin | UserManager.checkAdmin(user)");
			return false;
	}
	
}
