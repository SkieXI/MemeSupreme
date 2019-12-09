/**CST-361
 * 12-4-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This is where most of our troubles lie as we were unsure of how to correctly create the beans and datatypes to properly line up
 * with one another.
 * Reference for parsing JSON : https://stackoverflow.com/questions/32634530/how-to-convert-json-to-list-of-pojos-using-resteasy
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.Search;
import beans.TweetsDTO;
import beans.TwitterItems;
import data.TwitterDataInterface;
import util.LoggingInterceptor;

@Stateless
@Local(TwitterInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class TwitterManager implements TwitterInterface<TwitterItems> {

	//Inject the TwitterDataService.
	@Inject
	TwitterDataInterface<TwitterItems> TDS;
	
	//Setup the logger.
	Logger logger = LoggerFactory.getLogger(BatchManager.class);
	
	/**The middleman method between RESTService.getAllData and TwitterDataService.findall()
	 * @return List<TwitterItems>
	 */
	@Override
	public List<TwitterItems> getAllData() {
		logger.info("Starting TwitterManager.getAllData()");
		//List of BatchItems that is populated by the BatchDataService.findall() method.
		List<TwitterItems> tiList = TDS.findAll();
		logger.info("TwitterManager.getAllData() Complete");
		return tiList;
	}

	/** Invokes the REST service from Minimeme's fullData method. It takes in the search object from the xhtml page and sends it in
	 * a URL call.
	 * @param Search search
	 * @return void.
	 */
	@Override
	public void Bridge(Search search) 
	{
		try {
			logger.info("Entering method | TwitterManager.Bridge(search)");
			Client client = ClientBuilder.newClient();
			
			Invocation.Builder bldr = client.target("http://localhost:8080/MiniMeme/rest/tweets/fullData/" 
				+ search.getSearch() + "/").request(MediaType.APPLICATION_JSON);
			
			List<TwitterItems> ti = bldr.get(new GenericType<List<TwitterItems>>(){});
			//List<Scheme> schema = [...].get(new GenericType<List<Scheme>>(){});
			
			//Take the list of TwitterItems and sends it to the TWITTER table in the DB. 
			TDS.create(ti);
			
		logger.info("Data Successfully put into Database. | TwitterManager.Bridge(search)");
		
		//Close the client once you are done making hte URL call.
		 client.close();
		 
		//If unable to gather information, then throw Exception e.
		} catch (Exception e) {
		logger.error("An error occured putting data into database. | TwitterManger.Bridge(search)");
		}
	}

}
