//Joe Leon/Lewis Brown
//CST-361
//9-26-19
//This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
//We used source code from the following websites to complete this assignment:
//WEBSITE 1
//WEBSITE 2

package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
@ViewScoped
public class User 
{
	//Variables for all loging and regstrtaion purposes. 
	
	@NotNull(message="Please enter your desired username.")
	@Size(min=3, max=255, message ="Username need to be between 3 and 255 characters long.")
	private String uName;
	
	@NotNull(message="Please enter a password")
	@Size(min=3, max=255, message ="Password needs to be between 3 and 255 characters long.")
	private String password;
	
	private int isMod;
	
	@NotNull(message="Please enter a valid email address.")
	@Size(min=3, max=255, message ="An email address needs to be between 3 and 255 characters long.")
	private String email;
	
	private int id;
	public User()
	{
		uName = "";
		password = "";
		isMod = 0;
		email = "";
	}
	public User(String uName, String password, String email, int isMod, int id)
	{
		this.uName = uName;
		this.password = password;
		this.email = email;
		this.isMod = isMod;
		this.id = id;
	}
	
	//Getters and Setters

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsMod() {
		return isMod;
	}

	public void setIsMod(int isMod) {
		this.isMod = isMod;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
