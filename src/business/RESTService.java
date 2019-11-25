/**CST-361
 * 10-24-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * We used source code from the inclass activity as a template.
 * with one another.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.BatchDataInterface;
import beans.BatchDTO;
import beans.BatchDTOInterface;
import beans.BatchItems;
import beans.Search;
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
	//TwitterManager TMG = new TwitterManager();
	
	@Inject
	BatchDataInterface<BatchItems> BDI;
	
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
		logger.info("Entering Methiod | RESTService.getAllData");
		try
		{
			List<BatchItems> bi = TI.getAllData();
			return bi;
		}
		catch (Exception e)
		{
			logger.info("Method Failed. | RESTService.getAllData");
			return null;
		}
	}
	
	@GET
	@Path("/getdata/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BatchItems> getData(@PathParam("id") int id) 
	{
		logger.info("Entering Methiod | RESTService.getData()");
		try
		{
			BatchItems bi = TI.getData(id);
			List<BatchItems> bis = new ArrayList<BatchItems>();
			bis.add(bi);
			logger.info("Data gathered Succesffully!| RESTService.getData()");
			return bis;
			
		}
		catch(Exception e)
		{
			logger.info("Failed to gather data. | RESTService.getData()");
		}
		return null;
	}
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void setData(BatchItems items) {

		logger.info("Data Recieved:" + items + "| RESTService.setData()");
		try {
		Client client = ClientBuilder.newClient();
		BatchDTO BTO = client.target("http://localhost:8080/MiniMeme/rest/tweets/outboundData").request()
				.post(Entity.entity(new BatchDTO(), "application/json"), 
					      BatchDTO.class);
		
		//Set values.
		items.setLikesTotal(BTO.getItems().getLikesTotal());
		items.setRetweetTotal(BTO.getItems().getRetweetTotal());
		items.setTweetsTotal(BTO.getItems().getTweetsTotal());
		
			// put data in database
			TI.SaveNSave(items);
			logger.info("Data Successfully put into Database. | RESTService.setData()");
		} catch (Exception e) {
			logger.error("An error occured putting data into database. |RESTService.setData()");
		}
	}
	
	@GET
	@Path("/bridge")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void Bridge()
	{
		logger.info("Entering method | RESTService.Bridge()");
		Search se = new Search();
		BatchItems items = new BatchItems();
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
		} catch (Exception e) {
			logger.error("An error occured putting data into database. | RESTService.Bridge()");
		}
	}
}
