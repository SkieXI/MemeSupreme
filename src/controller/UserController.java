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
import javax.faces.bean.ManagedBean;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	UserInterface<User> UI;
	
	@EJB
	TwitterInterface<BatchItems> TI;
	
	@EJB
	BatchDataInterface<BatchItems> BTI;
	
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
			if(!UI.checkExistance(user) == false)
			{
				logger.info("Regstration successful: Returning MainMenu.xhtml");
				UI.processRegister(user);
				return "MainMenu.xhtml";
			}
			else
			{
				logger.info("Regstration failed: Returning _REgistrationFailed.xhtml");
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
		
		try 
		{
			if(!UI.validateLogin(user) == true)
			{
				logger.info("Login successful: Returning MainMenu.xhtml");
				return "MainMenu.xhtml";
			}
			else
			{
				logger.info("Login failed: Returning _LoginFailed.xhtml");
				return "_LoginFailed.xhtml";
			}
		} 
		catch (UserNotFoundException e) 
		{	
				logger.info("I don't know what to tell you chief: Returning _LoginFailed.xhtml");
				return "_LoginFailed.xhtml";
		}

	}
	
	public String Test()
	{
		System.out.println("===");
		System.out.println("Process Finished.");
		logger.info("Uh...");

		return "MainMenu.xhtml";
	}
	
	
	/**This method was created to make a more effecent means of testing out the Twitter API.
	 * It may go unused in the final version. But it is fun to just play around with the search feature and see what comes up.
	 * 
	 * @param search
	 * @return
	 */
	public String Search(Search search)
	{
		try {
		
			logger.info("Starting a search based on: " + search.getSearch());

		}
		catch(DatabaseException e) {
			logger.info("Nothing Found in the database.");
			System.out.println("================> Tweets Not Found");
		}
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("search", search);
		logger.info("Search found: returning MainMenu.xhtml");

		return "MainMenu.xhtml";
	}
}
