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
	@EJB
	TwitterInterface<BatchItems> TI;
	
	@Inject
	BatchDataInterface<BatchItems> BDI;
	
	@Inject
	UserDataInterface<User> UDS;
	
	Logger logger = LoggerFactory.getLogger(RESTService.class);
	
	DataFactory dFactory = new DataFactory();
	
	/**This calls all the information from the database and transfers it off in a list of objects called List<TwitterItems> items.
	 * 
	 * @return
	 */
	@GET
	@Path("/getalldata")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BatchItems> getAllData() 
	{
		logger.info("Getting all data from database | RESTService.getAllData");
		try
		{
			//Create a new instance of Batchitems list.
			//List<BatchItems> bi = TI.getAllData();
			List<BatchItems> bi = BDI.findall();
			//returns BatchItems.
			return bi;
		}
		catch (Exception e)
		{
			//When failed, retunrs nothing.
			logger.info("Method Failed. | RESTService.getAllData");
			return null;
		}
	}
	
	/**
	 * This is used to call in a specific record of a batch for one at a time.
	 * @param id
	 * @return
	 */
	@GET
	@Path("/getdata/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BatchItems> getData(@PathParam("id") int id) 
	{
		logger.info("Entering Methiod | RESTService.getData()");
		try
		{
			//New instance of BatchItems that is populated by information from BatchDataServices.getData() using the id provided.
			BatchItems bi = TI.getData(id);
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
	
	/**This method is used to call in the outboundData method found in MiniMeme.
	 * This returns default data.
	 * @param items
	 */
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void setData(Search search) {

		System.out.println("TSTE");
		logger.info("Retreving data for: " + search.getSearch() + " | RESTService.setData()");
		//Try to get information from Minimeme.
		try {
			
			Client client = ClientBuilder.newClient();
			Response response = client.target("http://localhost:8080/MiniMeme/rest/tweets/inboundData/" 
			+ search.getSearch() + "/" + search.getCount()
			).request().get();
		    BatchItems bi = response.readEntity(BatchItems.class);

		    //System.out.println(bi.getLikesTotal());
		      response.close();
		      client.close();
			
			// put data in database
			TI.SaveNSave(bi);
			logger.info("Data Successfully put into Database. | RESTService.setData()");
			
			//If unable to gather information, then throw Exception e.
		} catch (Exception e) {
			logger.error("An error occured putting data into database. |RESTService.setData()");
		}
	}
	
	/**This is used to call the inboundData in Minimeme.
	 * The idea is that this method calls inbounData and provides the information from what a user
	 * searches for in the MainMenu.xhtml page and that in turn would start the whole process up.
	 * 
	 */
	@GET
	@Path("/bridge")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void Bridge(BatchItems items)
	{
		logger.info("Entering method | RESTService.Bridge()");
		//Create a new instances of Search and BatchItems.
		Search se = new Search();
		///BatchItems items = new BatchItems();
		
		//Try to get data from Mimimeme.
		try
		{
		Client client = ClientBuilder.newClient();
		BatchDTO BD = client.target("http://localhost:8080/MiniMeme/rest/tweets/inboundData")
				.path("{word}").resolveTemplate("word", se.getSearch()).request().get(BatchDTO.class);

		//Set values.
		items.setLikesTotal(BD.getItems().getLikesTotal());
		items.setRetweetTotal(BD.getItems().getRetweetTotal());
		items.setTweetsTotal(BD.getItems().getTweetsTotal());
		
			// put data in database
			TI.SaveNSave(items);
			logger.info("Data Successfully put into Database. | RESTService.Bridge()");
			
			//If unable to gather information, throw Exception e.
		} catch (Exception e) {
			logger.error("An error occured putting data into database. | RESTService.Bridge()");
		}
	}
	@GET
	@Path("/getAllUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers()
	{

		logger.info("Entering Method | RESTService.getAllUsers()");
		try
		{
			// List<User> LU = UDS.findAll()
					return UDS.findAll();
		}
		catch(Exception e)
		{
			logger.info("Failed to generate list | RESTService.getAllUsers()");
			return null;
		}
	}
}
