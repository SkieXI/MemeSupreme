/**CST-361
 * 10-21-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This is where most of our troubles lie as we were unsure of how to correctly create the beans and datatypes to properly line up
 * with one another.
 */
package business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import beans.TwitterItems;
import beans.TwitterResponseData;
import data.TwitterConnection;
import data.TwitterDTO;
import data.TwitterDataInterface;

public class TwitterManager 
{
	@EJB 
	TwitterDataInterface TDA;

	/**This class is dummied out since this logic should have gone in a controller instead. 
	 * It call the methods SaveNSave() and then getAllData().
	 * 
	 *@param tDatas
	 *@return TDates
	/*
	public TwitterResponseDatas PullnSave(String word, int count)
	{
		//TwitterConnection TConn = new TwitterConnection();
		TwitterResponseData TDatas = new TwitterResponseData();
		TDatas = this.getAllData();
				TwitterResponseData TRD = new TwitterResponseData();
		
		this.SaveNSave(TDatas);
		return TDatas;
		
	}
	*/
	/**This takes in the results from {@link TwitterConnection#wordSearch(String, int)}
	 * And saves those results to a database.
	 * @input 
	 * @param tDatas
	 * @return void
	 */
	public void SaveNSave(TwitterItems tDatas)
	{
		//Test line.
		System.out.println("Mini Save Test 1");
		
		TwitterDTO DTO = new TwitterDTO();
		
		//Create a new instance of TwitterConnection, and that places the return of #wordSearch into the object of tDatas.
		TwitterConnection tconn = new TwitterConnection();
		tDatas = tconn.wordSearch("Idaho", 1);
		System.out.println(tDatas.getScreenName());
		System.out.println("Mini Save Test 2");
		//For loop for each entry.
		System.out.println(tDatas.getScreenName());
		//Saves the object into the TwitterDTO create().
		DTO.create(tDatas);
		System.out.println("Mini Save Test 3");
	}
	/** This pulls all information from the database and saves it to a bean of {@link TwitterResponseData}
	 * This is what the REST service {@link TwitterREST#getAllData()} uses.
	 * @param 
	 * @return datas
	 */
	public TwitterResponseData getAllData() 
	{
		System.out.println("Mini Load Test 1");
		
		TwitterDTO DTO = new TwitterDTO();
		
		//Creates a new instance of TwitterResponseData.
		TwitterResponseData datas = new TwitterResponseData();
		System.out.println("Mini Load Test 2");
		
		//Sets the values of this new object from what is pulled from the database in Twitter DTO#findAll();
		datas.setItems(DTO.findAll());
		System.out.println(datas.getItems().size());
		System.out.println("Mini Load Test 3");
		return datas;
				
				//(List<TwitterItems>) datas;
		
	}

}
