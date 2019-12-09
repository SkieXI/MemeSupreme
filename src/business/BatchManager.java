/**CST-361
 * 10-21-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This is where most of our troubles lie as we were unsure of how to correctly create the beans and datatypes to properly line up
 * with one another.
 * References for JSON Parsing: 
 * https://www.oreilly.com/library/view/java-ee-7/9781449370589/ch04.html
 */
package business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.BatchDTO;
import beans.BatchItems;
import beans.Search;
import beans.TweetsDTO;
import data.BatchDataInterface;
import data.TwitterDataService;
import util.LoggingInterceptor;


/**
 * This class is used for the business logic of processing all information from the Minimeme and the database.
 * @author jleon
 *
 */
@Stateless
@Local(BatchInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class BatchManager implements BatchInterface<BatchItems>
{
	//New instance of the logger.
	Logger logger = LoggerFactory.getLogger(BatchManager.class);
	
	//Inject the DAO for BatchItems.
	@Inject
	BatchDataInterface<BatchItems> BDI;
	
	/** This pulls all information from the database and saves it to a bean of {@link TwitterResponseData}
	 * This is what the REST service {@link RESTService#getAllData()} uses.
	 * @param 
	 * @return List<BatchItems> biList
	 */
	@Override
	public List<BatchItems> getAllData() 
	{
		//Logger telling us that we entered the method.
		logger.info("Entering Method | BatchManager.getAllData()");
		//List of BatchItems that is populated by the BatchDataService.findall() method.
		List<BatchItems> biList = BDI.findall();
		logger.info("TwitterManager.getAllData() Complete");
		//return the list.
		return biList;
	}

	/**Pulls information from the database at specificed points using the int t
	 * int t = the AA number in the databse.
	 * @return BatchItems bi.
	 */
	@Override
	public BatchItems getData(int t) 
	{
		logger.info("getData called| TwitterManger.getData(" + t + ")");
		BatchItems bi = new BatchItems();
		//Create a new instance of BatchItems that is populated by the BatchDataService.findby(t) method.
		bi = BDI.findby(t);
		logger.info("TwitterManager.getData(" + t + ") Complete");
		//Returns the single entry.
		return bi;
	}
	
	/**This is the method used to pull in information from the REST service in Minimeme. 
	 * @param Search search
	 * @return void.
	 */
	@Override
	public void Bridge(Search search)
	{
		try {
			logger.info("Entering Method | BatchManager.Bridge(search)");
			
			//The ClientBuilder is a class that is used to target a REST service API and parse the JSON that it gets
			//into a POJO that has the matching fields. 
			Client client = ClientBuilder.newClient();
			
			/**Getting Totals from MiniMeme.
			**The base code is Schema schema = client.target(url). 
			**In this case, the url is a GET method that takes in a {word} parameter for the minimeme to know what to look up.
			**/
			Invocation.Builder bldr1 = client.target("http://localhost:8080/MiniMeme/rest/tweets/inboundData/" 
				+ search.getSearch() + "/").request("application/json");
			
			/**This is where you have to be careful. When sending a DTO through a REST service, you have to have a receving DTO
			 * in order to catch everything. Otherwise you'll run into null errors. 
			 * Sends the BatchItems DTO to the database.
			 */
			BDI.create(bldr1.get(BatchDTO.class));
		
		// put data in database
		logger.info("Data Successfully put into Database. | BatchManager.Bridge(" + search.getSearch() + ")");
		
		//Close the client after you are done making the URL call. 
		 client.close();
		 
		//If unable to gather information, then throw Exception e.
		} catch (Exception e) {
		logger.error("An error occured putting data into database. | BatchManager.Bridge(" + search.getSearch() + ")");
		}
	}

}
