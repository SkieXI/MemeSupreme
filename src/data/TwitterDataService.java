/**CST-361
 * 12-4-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This is the DTO for all processes and methods related to the TwitterItems and the tweets themselves.
 */

package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.BatchDTO;
import beans.BatchItems;
import beans.TweetsDTO;
import beans.TwitterItems;
import util.DatabaseException;
import util.LoggingInterceptor;

@Stateless
@Local(TwitterDataInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class TwitterDataService implements TwitterDataInterface<TwitterItems>
{
	//All the information to access the database.
	String url = "jdbc:mysql://localhost:3306/memesupreme";
	String username = "root";
	String password = "root";
	
	Logger logger = LoggerFactory.getLogger(BatchDataService.class);
	
	/**Searches the TWITTER datatable and returns all the information found.
	 * 
	 * @return List<TwitterItems>
	 */
	@Override
	public List<TwitterItems> findAll() 
	{
		//Make sure the connection is empty.
		Connection conn = null;
		//Create a new list of TwitterItems and name it items.
		List<TwitterItems> items = new ArrayList<TwitterItems>();
		try
		{
			logger.info("Pulling everything from the TWITTER table. | BatchDTO.findAll()");
			
			conn = DriverManager.getConnection(url, username, password);
			
			//SQL statemet. 
			String sql1 = "SELECT * FROM TWITTER";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			
			//For each row found in the database, take that information and add it to the items list.
			while (rs1.next()) 
			{
				TwitterItems ti = new TwitterItems(rs1.getString("TWEET"), rs1.getString("SCREEN_NAME"), rs1.getInt("RT_COUNT"), rs1.getInt("Likes"), rs1.getString("LANG"));
				items.add(ti);
			}
			//Close connection. 
			logger.info("Read from database complete | TwitterDataService.findAll()");
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			//throw new DatabaseException(e);
		}
		finally
		{
			  if(conn != null)
	            {
	                try
	                {
	                    conn.close();
	                }
	                catch (SQLException e)
	                {
	                    e.printStackTrace();
	                    //throw new DatabaseException(e);
	                }
	            }
	        }
		return items;
	}
	
	/**Takes a list of tweets and then saves everything to the database. 
	 * @param List<TwitterItems> ti
	 * @return boolean.
	 */
	@Override
	public boolean create(List<TwitterItems> ti) {
		
		//Make sure the connection is empty. 
		Connection conn = null;
		try
		{
		logger.info("Adding new batch to the databse | BatchDTO.create()");
		
		//SQL Statement. 
		conn = DriverManager.getConnection(url, username, password);
		String sql1 = String.format("INSERT INTO TWITTER(TWEET, SCREEN_NAME, RT_COUNT, LIKES, LANG) VALUES(?, ?, ?, ?, ?)");
		
		//For reach object in the Arraylist, save that object's information to the database.
		for (TwitterItems t : ti )
		{
			//Also, DO NOT put any print or debug lines in here, it WILL flood your console.
			//Each collum that is going to be inserted into the database needs to be processed.
			PreparedStatement stmt1 = conn.prepareStatement(sql1);
		
			//String of Tweet.
			stmt1.setString(1, t.getTweet());
		
			//String of ScreennName. 
			stmt1.setString(2, t.getScreenName());
		
			//Int of RetweetCount.
			stmt1.setInt(3, t.getRetweetCount());
		
			//Int of Likes.
			stmt1.setInt(4, t.getLikes());
		
			//String of Language.
			stmt1.setString(5, t.getLanguage());
		
			//Run SQL line.
			stmt1.executeUpdate();
		}
		System.out.println("Create Finish");
		System.out.println("===");
		logger.info("Information uploaded successfully!| BatchDTO.create()");
		
	}
		//Everything below this is copied from FindBYID();
	catch (SQLException e)
		{
			e.printStackTrace();
			throw new DatabaseException(e);
		}
		finally
		{
			if(conn != null)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
					logger.info("Failed to add information to the database.| BatchDTO.create()");
					throw new DatabaseException(e);
				}
			}
		}
		return false;
	}
}
