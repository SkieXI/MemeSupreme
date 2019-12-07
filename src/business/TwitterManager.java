/**CST-361
 * 10-21-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This is where most of our troubles lie as we were unsure of how to correctly create the beans and datatypes to properly line up
 * with one another.
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
import data.BatchDataInterface;
import data.BatchDataService;
import util.LoggingInterceptor;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class is used for the business logic of processing all information from the Minimeme and the database.
 * @author jleon
 *
 */
@Stateless
@Local(TwitterInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class TwitterManager implements TwitterInterface<BatchItems>
{
	//New instance of the logger.
	Logger logger = LoggerFactory.getLogger(TwitterManager.class);
	
	@EJB
	BatchDataInterface<BatchItems> BDI;
	
	@Inject
	RESTService REST;
	
	/**This class is dummied out since this logic should have gone in a controller instead. 
	 * It call the methods SaveNSave() and then getAllData().
	 * 
	 *@param tDatas
	 *@return TDates
	*/
	@Override
	public void PullnSave(BatchItems items)
	{
		logger.info("PullnSave called");
		//I think this might be where our timer will go.
		//TODO : Add in logic.
		logger.info("PullnSave Complete");
	}
	
	/**This takes in the results from {@link TwitterConnection#wordSearch(String, int)}
	 * And saves those results to a database.
	 * @input 
	 * @param tDatas
	 * @return void
	 */
	@Override
	public void SaveNSave(BatchItems data)
	{
		logger.info("SaveNSave called");
		//This method is more or less to save all information to a databse.
		logger.info("SaveNSave Complete");
	}
	/** This pulls all information from the database and saves it to a bean of {@link TwitterResponseData}
	 * This is what the REST service {@link RESTService#getAllData()} uses.
	 * @param 
	 * @return datas
	 */
	@Override
	public List<BatchItems> getAllData() 
	{
		//Logger telling us that we entered the method.
		logger.info("Starting TwitterManager.getAllData()");
		//List of BatchItems that is populated by the BatchDataService.findall() method.
		List<BatchItems> biList = BDI.findall();
		logger.info("TwitterManager.getAllData() Complete");
		//return the list.
		return biList;
	}

	/**Pulls information from the database at specificed points using the int t
	 * int t = the AA number in the databse.
	 * 
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
	
	public void Bridge(Search search)
	{
		/*
		logger.info(search.getSearch());
		logger.info("Starting REST call. THis is NOT GOINDG TOD WORK!");
		REST.setData(search);
		logger.info("IT WORKED?!");
		*/
		//try {
		logger.info("HOLY CRAP HERE WE GO!");
		Client client = ClientBuilder.newClient();
		Invocation.Builder bldr = client.target("http://localhost:8080/MiniMeme/rest/tweets/inboundData/" 
				+ search.getSearch() + "/" + search.getCount()).request("application/json");
      // BatchDTO BD = bldr.get(BatchDTO.class);
        
		BatchDataService BDI = new BatchDataService();
		BDI.create(bldr.get(BatchDTO.class));
		
		//return bldr.get(BatchItems.class);
		
		//WebTarget target = client.target("http://localhost:8080/MiniMeme/rest/tweets/inboundData/" 
		//+ search.getSearch() + "/" + search.getCount());
		
		//Response response = target.request().get();
        
        
	    //BatchItems bi = response.readEntity(BatchItems.class);
		
		// put data in database
	    logger.info("PUTTING STUFF INTO THE DATA BASE.");
		//BDI.create(bi);
		logger.info("STUFF PUT.");
		logger.info("Data Successfully put into Database. | RESTService.setData()");
		 client.close();
		//If unable to gather information, then throw Exception e.
		//} catch (Exception e) {
		//logger.error("An error occured putting data into database. |RESTService.setData()");
		//}
	}

}
