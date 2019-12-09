/**CST-361
 * 11-20-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This is the DTO for all processes and methods related to the BatchItems and the information gathered from MiniMeme.
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
import controller.UserController;
import util.DatabaseException;
import util.LoggingInterceptor;

@Stateless
@Local(BatchDataInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class BatchDataService implements BatchDataInterface <BatchItems> 
{
	//Strings for the url, username, and the password for the LocalHost are stored here.
		String url = "jdbc:mysql://localhost:3306/memesupreme";
		String username = "root";
		String password = "root";
		
		//Set up the logger.
		Logger logger = LoggerFactory.getLogger(BatchDataService.class);
		
		/**This pulls everything from the database.
		 * @return List<BatchItems>
		 */
		@Override
		public List<BatchItems> findall() {
		
			//Make sure that the connection starts out null.
		Connection conn = null;
		//Create a new list of BatchItems.
		List<BatchItems> bi = new ArrayList<BatchItems>();
		
		//Attempt to pull from the database.
		try
		{
			logger.info("Pulling info from the database | BatchDTO.findall()");
			conn = DriverManager.getConnection(url, username, password);
			
			//SQL statement.
			String sql1 = "SELECT * FROM BATCHRECORDS";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			
			//For each entry in the database, it will be added to the list of BatchItems.
			while (rs1.next()) 
			{
				BatchItems bit = new BatchItems(rs1.getInt("RETWEETS"), rs1.getInt("LIKES"), rs1.getInt("TWEETCOUNT")); 
				bi.add(bit);
				//return bi;
			}
			//Close the connections.
			rs1.close();
			stmt1.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.info("Failed to pull from the database| BatchDTO.findall()");
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
	                    throw new DatabaseException(e);
	                }
	            }
	        }
		logger.info("Pull successful!| BatchDTO.findall()");
		return bi;
	}

	/**This create a new entry into the database each tiem the timer goes off and a new batch of information is pulled from Minimeme.
	 * @param BatchItems
	 * @return boolean
	 */
		@Override
	public boolean create(BatchDTO bto) {
			
			//Make sure that the connection is empty before starting.
		Connection conn = null;
		try {
		
		logger.info("Adding new batch to the databse | BatchDTO.create()");
		
		conn = DriverManager.getConnection(url, username, password);
		//SQL prepared statement. retweettotal | likesToatal | Tweetstotal 
		String sql1 = String.format("INSERT INTO BATCHRECORDS(RETWEETS, LIKES, TWEETCOUNT) VALUES(?, ?, ?)");
		
		BatchItems bi = bto.getItems();
		
		//Each collum that is going to be inserted into the database needs to be processed.
		PreparedStatement stmt1 = conn.prepareStatement(sql1);
		
		// First = RetweetTotal.
		stmt1.setInt(1, bi.getRetweetTotal());
		
		//Second = LikesTotatl. 
		stmt1.setInt(2, bi.getLikesTotal());
		
		//Third = TweetsTotal.
		stmt1.setInt(3, bi.getTweetsTotal());
		
		//Exectue SQL statement.
		stmt1.executeUpdate();

		//Close conections.
		stmt1.close();
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

	/**This is used to find a specific record or batch from the database.
	 * @param int 
	 * @return BatchItems
	 */
	public BatchItems findby(int id) 
	{
		//Make sure the connection is empty.
		Connection conn = null;
		//Create new instance of BatchItems.
		BatchItems items = new BatchItems();
		try {
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			// Execute SQL Query and loop over result set
			String sql1 = String.format("SELECT * FROM BATCHRECORDS WHERE BATCHNO =%d", id);
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			
			//For each row found in the Database, set it to the BatchItem list. 
			while(rs1.next()) {
			items = new BatchItems(rs1.getInt("RETWEETS"), rs1.getInt("LIKES"), rs1.getInt("TWEETCOUNT"));
			}
			
			// Close connections.
			rs1.close();
			stmt1.close();
		} catch (SQLException e) {
			logger.error("An SQLException or DatabaseException has Occured", new Exception(e));
			e.printStackTrace();
			throw new DatabaseException(e);
		} finally {
			// Cleanup Database
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("An SQLException or DatabaseException has Occured", new Exception(e));
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		logger.info("Pull from database finished.| BatchDTO.findby()");
		return items;
	}

}
