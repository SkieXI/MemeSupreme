//Joe Leon/lewis brwon
//CST-361
//9-26-19
//This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
//We used source code from the following websites to complete this assignment:
//WEBSITE 1
//WEBSITE 2

package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import beans.User;
import beans.Search;
import beans.SearchData;
import beans.TwitterItems;
import beans.TwitterResponseData;
import business.OutBoundREST;
import business.TwitterManager;
import business.UserInterface;
import data.TwitterConnection;
import data.TwitterDataInterface;
import util.UserNotFoundException;

@ManagedBean
@ViewScoped
@Stateless
public class UserController 
{
	@EJB
	UserInterface UI;
	
	@EJB
	TwitterDataInterface TDA;
	
	
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
			//Gets the information from the textfields in the Register.xhtml page.
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
			if(!UI.checkExistance(user) == false)
			{
				UI.processRegister(user);
				return "MainMenu.xhtml";
			}
			else
			{
				
				return "Register.xhtml";
			}
		}
	//If it fails, then it will return the register page again.
		catch(UserNotFoundException e) 
		{
			return "Register.xhtml";
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
		try 
		{
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
			if(!UI.validateLogin(user) == true)
			{
				System.out.println("Tthis is a test.");
				
				//prints list of searched tweets
				//System.out.println(TDA.wordSearch("Thing", 30));
				return "MainMenu.xhtml";
			}
			else
			{
				return "Login.xhtml";
			}
		} 
		catch (UserNotFoundException e) 
		{	
		}
	return "MainMenu.xhtml";
	}
	
	public String Test()
	{

		System.out.println("Mini Test 1");
		TwitterManager tw = new TwitterManager();
		TwitterItems ti = new TwitterItems();
		System.out.println("Mini Test 2");
		System.out.println("Test 2.5???????!");
		
		System.out.println("Mini Test 3");
		tw.SaveNSave(ti);
		System.out.println("Mini Test 4");
		tw.getAllData();
		System.out.println("FINALLY CELAR!");
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

		System.out.println("Test 1");
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("search", search);
		TwitterConnection tconn = new TwitterConnection();
		System.out.println(tconn.wordSearch(search.getSearch(), search.getCount()));
		/*
		OutBoundREST REST = new OutBoundREST();
		SearchData DATA = new SearchData();
		
		System.out.println(search.getSearch());
		System.out.println(search.getCount());
		DATA.setData(search);
		System.out.println("Test 2");
		
		DATA.setSearch(search.getSearch());
		DATA.setCount(search.getCount());
		
		System.out.println("TEST 2.5");
		System.out.println(DATA.getSearch());
		REST.Search(DATA);		
		System.out.println("Test 3");

*/
		return "MainMenu.xhtml";
	}
}
