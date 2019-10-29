/**CST-361
 * 9-28-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This is where all CRUD methods needed for the Twitter table in the Database is found.
 * 
 */

package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.TwitterItems;
import beans.TwitterResponseData;
import util.DatabaseException;

public class TwitterDTO //implements TwitterDataInterface
{
//Strings for the url, username, and the password for the LocalHost are stored here.
	String url = "jdbc:mysql://localhost:3306/memesupreme";
	String username = "root";
	String password = "root";
	
	/**Pulls everything from the database and then saves it to a list called TwitterItems.
	 * @param List<TwitterItems>
	 * @return items
	 */
	public List<TwitterItems> findAll() {
		// TODO Auto-generated method stub
		Connection conn = null;
		//String url = "jdbc:mysql://localhost:3306/memesupreme";
		//String username = "root";
		//String password = "root";
		System.out.println("Find Test 1");
		List<TwitterItems> items = new ArrayList<TwitterItems>();
		try
		{
			conn = DriverManager.getConnection(url, username, password);
			
			String sql1 = "SELECT * FROM TWITTER";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);

			System.out.println("Find Test 2");
			while (rs1.next()) 
			{
				TwitterItems ti = new TwitterItems(rs1.getString("TWEET"), rs1.getString("SCREEN_NAME"), rs1.getInt("RT_COUNT"), rs1.getInt("Likes"), rs1.getString("LANG"));
				items.add(ti);

				System.out.println(ti.getScreenName());
				System.out.println("Find Test loop.");
				//return items;
			}
		}
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
	                    throw new DatabaseException(e);
	                }
	            }
	        }
		return items;
	}
	
	/** This only pulls from a singular entry in the databse tabel.
	 * @param TwitterItems
	 * @return ti.
	 */
	public TwitterItems findBy(TwitterItems ti) {
		// TODO Auto-generated method stub
		Connection conn = null;
		//String url = "jdbc:mysql://localhost:3306/memesupreme";
		//String username = "root";
		//String password = "root";
		try
		{
			conn = DriverManager.getConnection(url, username, password);
			
			String sql1 = String.format("SELECT * FROM TWITTER WHERE TWEET='%s' AND SCREEN_NAME='%s' AND RT_COUNT=%d AND LANG='%s' AND LIKES=%d",
										ti.getTweet(),
										ti.getScreenName(),
										ti.getRetweetCount(),
										//ti.getHashtags(),
										ti.getLanguage(),
										ti.getLikes());
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			if(!rs1.next())
			{
				rs1.close();
				stmt1.close();
				return null;
			}
			
			ti.setTweet(rs1.getString("TWEET"));
			ti.setScreenName(rs1.getString("SCREEN_NAME"));
			ti.setRetweetCount(rs1.getInt("RT_COUNT"));
			ti.setHashtags(rs1.getString("HASH_TAGS"));
			ti.setLanguage(rs1.getString("LANG"));
			ti.setLikes(rs1.getInt("LIKES"));
			
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
	                    throw new DatabaseException(e);
	                }
	            }
		}
		return ti;
	}

	/**This takes the information that was gathered from the Twitter search and saves it to the Twitter table in the database.
	 * @param TwitterItems data.
	 * @return bool
	 */
	public boolean create(TwitterItems data) {
		Connection conn = null;
		try
		{
			//Machiato = really good coffee.
			//for loop for each entry in the list.
			
			System.out.println("Create Test 1");
		conn = DriverManager.getConnection(url, username, password);
		String sql1 = String.format("INSERT INTO TWITTER(SCREEN_NAME, TWEET, RT_COUNT, LIKES, LANG) VALUES('%s', '%s', %d, %d, '%s')",
				
				data.getScreenName(),
				data.getTweet(),
				data.getRetweetCount(),
				data.getLikes(),
				data.getLanguage());
				
		System.out.println(sql1);
		Statement stmt1 = conn.createStatement();
		stmt1.executeUpdate(sql1);
		
		System.out.println("Create Test 2");
		stmt1.close();
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
					throw new DatabaseException(e);
				}
			}
		}
		return true;
	}
	
}
