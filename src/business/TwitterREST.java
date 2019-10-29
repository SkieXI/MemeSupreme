/**CST-361
 * 10-24-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * We used source code from the inclass activity as a template.
 * with one another.
 */
package business;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.TwitterResponseData;
import beans.TwitterItems;
import data.TwitterDTO;
import data.TwitterDataInterface;


@RequestScoped
@Path("/tweets")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class TwitterREST 
{
	//Create a new instance of TwitterDTO.
	//TwitterDTO DTO = new TwitterDTO();
	
	//Create a new instance of the TwitterManager.
	TwitterManager TMG = new TwitterManager();
	
	
	/**This calls all the information from the database and transfers it off in a list of objects called List<TwitterItems> items.
	 * 
	 * @return
	 */
	@GET
	@Path("/getalldata")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TwitterItems> getAllData() 
	{
		//Creates a new instance of TwitterResponseData.
		TwitterResponseData TRD = new TwitterResponseData();
		
		//Calls the something.
		TRD = (TwitterResponseData) TMG.getAllData();
		//Creates a list of TwitterItems.
		//TwitterItems items = new TwitterItems();
		List<TwitterItems> items = TRD.getItems();
		System.out.println("REST TEST");
		System.out.println(items);
		return items;
		//return null;
	}
}
