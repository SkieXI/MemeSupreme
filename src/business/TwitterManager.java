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
import javax.interceptor.Interceptors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.BatchItems;
import data.BatchDataInterface;
import util.LoggingInterceptor;

@Stateless
@Local(TwitterInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class TwitterManager implements TwitterInterface<BatchItems>
{
	Logger logger = LoggerFactory.getLogger(TwitterManager.class);
	
	@EJB
	BatchDataInterface<BatchItems> BDI;
	/**This class is dummied out since this logic should have gone in a controller instead. 
	 * It call the methods SaveNSave() and then getAllData().
	 * 
	 *@param tDatas
	 *@return TDates
	*/
	public void PullnSave(BatchItems items)
	{
		logger.info("PullnSave called");
		
		logger.info("PullnSave Complete");
	}
	
	/**This takes in the results from {@link TwitterConnection#wordSearch(String, int)}
	 * And saves those results to a database.
	 * @input 
	 * @param tDatas
	 * @return void
	 */
	public void SaveNSave(BatchItems data)
	{
		logger.info("SaveNSave called");
		logger.info("SaveNSave Complete");
	}
	/** This pulls all information from the database and saves it to a bean of {@link TwitterResponseData}
	 * This is what the REST service {@link RESTService#getAllData()} uses.
	 * @param 
	 * @return datas
	 */
	public List<BatchItems> getAllData() 
	{
		logger.info("Starting TwitterManager.getAllData()");
		List<BatchItems> biList = BDI.findall();
		
		//biList = BDI.findall();
		
		logger.info("TwitterManager.getAllData() Complete");
		return biList;
	}

	public BatchItems getData(int t) 
	{
		logger.info("getData called| TwitterManger.getData(" + t + ")");
		BatchItems bi = new BatchItems();
		bi = BDI.findby(t);
		return bi;
	}
	

}
