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
		
		Logger logger = LoggerFactory.getLogger(BatchDataService.class);
		
	public List<BatchItems> findall() {
		Connection conn = null;
		List<BatchItems> bi = new ArrayList<BatchItems>();
		try
		{
			logger.info("Pulling info from the database | BatchDTO.findall()");
			conn = DriverManager.getConnection(url, username, password);
			
			String sql1 = "SELECT * FROM BATCHRECORDS";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			
			while (rs1.next()) 
			{
				BatchItems bit = new BatchItems(rs1.getInt("RETWEETS"), rs1.getInt("LIKES"), rs1.getInt("TWEETCOUNT")); 
						bi.add(bit);

				System.out.println("Retweet Total:" + bit.getRetweetTotal());
				return bi;
			}
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

	public boolean create(BatchItems bi) {
		Connection conn = null;
		try
		{
		
		logger.info("Adding new batch to the databse | BatchDTO.create()");
		
		conn = DriverManager.getConnection(url, username, password);
		String sql1 = String.format("INSERT INTO BATCHRECORDS(RETWEETS, LIKES, TWEETCONT) VALUES(?, ?, ?)");
		
		//Each collum that is going to be inserted into the database needs to be processed.
		PreparedStatement stmt1 = conn.prepareStatement(sql1);
		stmt1.setInt(1, bi.getRetweetTotal());
		stmt1.setInt(2, bi.getLikesTotal());
		stmt1.setInt(3, bi.getTweetsTotal());
		stmt1.executeUpdate();

		System.out.println("Create Finish");
		System.out.println("===");
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

	public BatchItems findby(int id) 
	{
		Connection conn = null;
		BatchItems items = new BatchItems();
		try {
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			// Execute SQL Query and loop over result set
			String sql1 = String.format("SELECT * FROM BATCHRECORDS WHERE BATCHNO =%d", id);
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			while(rs1.next()) {
			items = new BatchItems(rs1.getInt("RETWEETS"), rs1.getInt("LIKES"), rs1.getInt("TWEETCOUNT"));
			}
			
			// Cleanup
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
