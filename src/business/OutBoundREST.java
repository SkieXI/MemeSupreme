/**CST-361
 * 10-23-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This class is a REST service thought it may be unused in the future.
 * The idea is that this REST service will make a outbound call that will jumpstart the search and save methods of the MiniMeme Project.
 */

package business;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.SearchData;


@RequestScoped
@Path("/Tweets")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class OutBoundREST 
{

	@GET
	@Path("/search")
	@Consumes("application/json")
	@Produces("application/json")
	public Response Search(SearchData search)
	{
		/*
		URL ulr = new URL("http://localhost:8080/MiniMeme/rest/Tweets/getalldata");
		HttpURLConnection conn = (HttpURLConnection); ulr.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
		*/
		System.out.println("TEst 4");
		String output = "word: " + search.getSearch() + " count: " + search.getCount() ;
		System.out.println(output);
		System.out.println(Response.status(200).entity(output).build());
		return Response.status(200).entity(output).build();
	}
}
