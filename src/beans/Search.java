/**
CST-361
9-29-2019
This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
This bean is used for variables to input into Twitter's Twitter4j jar file.
**/
package beans;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@Stateless
@ViewScoped
public class Search 
{
	//To make a search, you need a String to search up, and a int for how many results you want.
	private String Search;
	private int count;
	
	//Default Constructor
	public Search()
	{
		Search = "Idaho";
		count = 10;
	}
	//Non-default Constructor 
	public Search(String Search, int count)
	{
		this.Search = Search;
		this.count = count;
	}

	//Getters and Setters.
	public String getSearch() {
		return Search;
	}

	public void setSearch(String search) {
		Search = search;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
