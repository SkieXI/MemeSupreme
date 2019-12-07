//Joe Leon/lewis brwon
//CST-361
//9-26-19
//This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
//We used source code from the following websites to complete this assignment:
//WEBSITE 1
//WEBSITE 2

package controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.User;
import beans.BatchItems;
import beans.Search;
import business.TwitterInterface;
import business.TwitterManager;
import business.UserInterface;
import data.BatchDataInterface;
import util.DatabaseException;
import util.LoggingInterceptor;
import util.UserNotFoundException;

@Interceptors(LoggingInterceptor.class)
@Named
@ViewScoped
@Stateless
public class UserController implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//Calling in different beans and parts of the application.
	@Inject
	UserInterface<User> UI;
	
	@Inject
	TwitterInterface<BatchItems> TI;
	
	@EJB
	TwitterInterface<Search> SI;
	
	@EJB
	BatchDataInterface<BatchItems> BTI;
	
	@EJB
	TwitterManager TM;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	/**Method grabs the infromation from the webpage and then sends it off to the UserInterface which then checks to see if
	 * the username already exist within the database. If it does, then it return the Register.xthml page.
	 * If it doesn't, then it takes the information the user entered and return the MainMenu.xhtml page.
	 * 
	 * @param user
	 * @return webpages
	 * @see UserInterface#validateRegister(User)
	 * @see UserInterface#checkExistance(User)
	 */
	public String Register(User user) 
	{
		try {
			logger.info("Registration process started: Entering try block.");
			//Gets the information from the textfields in the Register.xhtml page.
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
			//If the check for an existing user turns out false fails, then it grants the user access to the MainMenu.xhtml.
			//Should fix in future since its a double negiative. 
			if(!UI.checkExistance(user) == false)
			{
				logger.info("Regstration successful: Returning MainMenu.xhtml");
				//Create a new user in the database.
				UI.processRegister(user);
				return "MainMenu.xhtml";
			}
			else
			{
				logger.info("Regstration failed: Returning _REgistrationFailed.xhtml");
				//If the process fails, or the validation fails, then it returns _RegistrationFailed.xhtml page.
				return "_RegistrationFailed.xhtml";
			}
		}
	//If it fails, then it will return the register page again.
		catch(UserNotFoundException e) 
		{
			logger.info("Something went terribly wrong: Returning RegistrationFialed.xhtml");
			return "_RegistrationFailed.xhtml";
		}
	}
	
	/** Checks to see if the username and password from the Login.xhtml page matches an existing entry within the database.
	 * 
	 * @param user
	 * @return
	 * @see UserInterface#validateLogin(User)
	 * @see TwitterDataInterface#wordSearch(String, int)
	 */
	public String login(User user) 
	{
		logger.info("Starting login process.");
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
		
		//Try to find a user from the database.
		try 
		{
			//If a user is found, then it returns the MainMenu.xhtml page.
			if(UI.validateLogin(user) == 1)
			{
				logger.info("Noraml login successful: Returning MainMenu.xhtml");
				return "MainMenu.xhtml";
			}
			if(UI.validateLogin(user) == 2)
			{
				logger.info("Admin login successful: Returning MainMenu.xhtml");
				return "AdminMain.xhtml";
			}
			if(UI.validateLogin(user) == 0)
			{
				logger.info("Login failed: Admin level = 0: Returning _LoginFailed.xhtml");
				return "_LoginFailed.xhtml";
			}
				/*
				if (user.getIsMod() == 0)
				{
				logger.info("Noraml login successful: Returning MainMenu.xhtml");
				return "MainMenu.xhtml";
				}
				if (user.getIsMod() == 1)
				{
					logger.info("Admin login successful: Returning AdminMain");
					return "AdminMain.xhtml";
				}
				*/
			{
				//If there isn't any users found, then it returns with a _LoginFailed.xhtml page.
				logger.info("Login failed hard: Returning _LoginFailed.xhtml");
				return "_LoginFailed.xhtml";
			}
		} 
		catch (UserNotFoundException e) 
		{	
			//If something goes wrong in the login process. Then just return _Loginfailed anyway.
				logger.info("I don't know what to tell you chief: Returning _LoginFailed.xhtml");
				return "_LoginFailed.xhtml";
		}

	}
	
	/**This method was created to make a more effecent means of testing out the Twitter API.
	 * It may go unused in the final version. But it is fun to just play around with the search feature and see what comes up.
	 * 
	 * @param search
	 * @return
	 */
	public String Search(Search search)
	{
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("search", search);
		logger.info("Searching for " + search.getSearch() + ". : returning MainMenu.xhtml");
		
		TM.Bridge(search);
		logger.info("We did the thing.");
		return "AdminMain.xhtml";
	}
}
