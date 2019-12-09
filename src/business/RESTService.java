/**CST-361
 * 10-24-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * We used source code from the inclass activity as a template.
 * with one another.
 * Work cited:
 * https://www.oreilly.com/library/view/java-ee-7/9781449370589/ch04.html
 */
package business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.criteria.Order;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.BatchDataInterface;
import data.UserDataInterface;
import beans.BatchDTO;
import beans.BatchDTOInterface;
import beans.BatchItems;
import beans.Search;
import beans.TwitterItems;
import beans.User;
import util.DataFactory;
import util.LoggingInterceptor;

@RequestScoped
@Interceptors(LoggingInterceptor.class)
@Named
@Path("/tweets")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class RESTService 
{
	//Inject the Interfaces for a User, BatchItem, and TwitterItem DAO. 
	@Inject
	BatchInterface<BatchItems>  BI;
	
	@Inject
	TwitterInterface<TwitterItems> TI;
	
	@Inject
	UserDataInterface<User> UDS;
	
	//Setup the logger. 
	Logger logger = LoggerFactory.getLogger(RESTService.class);
	
	//Setup the data factory. 
	DataFactory dFactory = new DataFactory();
	
	/**
	 * This is used to call in a specific record of a batch for one at a time.
	 * @param id
	 * @return List<BatchItems>
	 */
	@GET
	@Path("/getData/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BatchItems> getData(@PathParam("id") int id) 
	{
		logger.info("Entering Methiod | RESTService.getData()");
		try
		{
			//New instance of BatchItems that is populated by information from BatchDataServices.getData() using the id provided.
			BatchItems bi = BI.getData(id);
			//New list of BatchItems.
			List<BatchItems> bis = new ArrayList<BatchItems>();
			//For each entry found, add it to the list.
			bis.add(bi);
			logger.info("Data gathered Succesffully!| RESTService.getData()");
			//Return said list.
			return bis;
			
		}
		catch(Exception e)
		{
			logger.info("Failed to gather data. | RESTService.getData()");
			return null;
		}
	}
	
	/**This calls all the information from the database and transfers it off in a list of objects called List<TwitterItems> items.
	 * 
	 * @return List<TwitterItems>
	 */
	@GET
	@Path("/getAllData")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TwitterItems> getAllData() 
	{
		//This is for pulliing all the informaion from the TWITTER table, not the results of hte thing.
		
		logger.info("Getting all data from database | RESTService.getAllData");
		//Attemps to get a list of TwitterItems from the TWITTER table.
		try
		{
			//Create a new instance of Batchitems list that gets populated with the information found in the 
			//TwitterManger.getAllData -> TwiiterDataService.findAll()
			List<TwitterItems> ti = TI.getAllData();
			//returns BatchItems.
			logger.info("Data found! | RESTService.getalldata()");
			return ti;
		}
		catch (Exception e)
		{
			//When failed, retunrs nothing.
			logger.info("Method Failed. | RESTService.getalldata");
			return null;
		}
	}
	/**This is the REST service that gets a list of all BatchItems that is generated from the miniMeme.
	 * 
	 * @return List<BatchItems>
	 */
	@GET
	@Path("/getAllBatch")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BatchItems> getAllBatch() 
	{
		logger.info("Getting all data from database | RESTService.getAllData");
		//Attempts to gather data from the BATCHRECORDS datatable. 
		try
		{
			logger.info("Data found! | RESTService.getalldata()");
			//Very simple return statement of getting all the data from the BatchManager's getAllData method.
			//BatchManger.getAllData() -> BatchDataService.findall()
			return BI.getAllData();//.getAllData();
		}
		catch (Exception e)
		{
			//When failed, retunrs nothing.
			logger.info("Method Failed. | RESTService.getalldata");
			return null;
		}
	}
	/**Method this is used to populate the tabular report of users in the admin page. 
	 * It calls in the method of UserDataService.finaAll() and returns a list of Users found in the database.
	 * 
	 * @return List<User>
	 */
	@GET
	@Path("/getAllUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers()
	{
		logger.info("Entering Method | RESTService.getAllUsers()");
		//Attempts to gather data from the USER table.
		try
		{
			//Attemps to populate a list that is generated inside of UserDataService.findAll()
			return UDS.findAll();
		}
		catch(Exception e)
		{
			//If failed, then returns null.
			logger.info("Failed to generate list | RESTService.getAllUsers()");
			return null;
		}
	}
}
