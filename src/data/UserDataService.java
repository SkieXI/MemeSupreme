/**Joe Leon
**CST-361
**9-26-19
**This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
**
*/

package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import beans.User;
import data.UserDataInterface;
import util.LoggingInterceptor;

@Stateless
@Local(UserDataInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class UserDataService implements UserDataInterface<User>
{
	
	//Strings containing information to connect to the database.
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/memesupreme";
	String username = "root";
	String password = "root";
	
	//New instance of the logger for this class.
	Logger logger = LoggerFactory.getLogger(UserDataService.class);
	
	
	/**This provides a huge list of everything within the database inside the USER table.
	 * @return users
	 */
	public List<User> findAll() 
	{
		List<User> users = new ArrayList<User>();
		
		logger.info("Finding all users.| UserDTO.findAll()");
		try {
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			// Execute SQL Query and loop over result set
			String sql1 = "SELECT * FROM USER";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			//For each entry found, that row is going to be added to a list of users.
			//This entire method seems like a huge security risk.
			while (rs1.next()) 
			{
				User user = new User(rs1.getString("UNAME"), rs1.getString("EMAIL"), rs1.getString("PASSWORD"), rs1.getInt("ISMOD"), rs1.getInt("UID"));
				users.add(user);
			}

			// Cleanup
			rs1.close();
			stmt1.close();
			logger.info("Search successful, closing connection.| UserDTO.findAll()");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Cleanup Database
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.info("Search unsuccessful.| UserDTO.findAll()");
					e.printStackTrace();
				}
			}
		}

		// Return list of Users
		return users;
	}

	/**
	 * Searches the database for any entries that match the input username.
	 * @param id
	 * @return users
	 */
	public User findById(int id) {
	
		//New instance of User.
		User user = new User();
		try {
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			logger.info("Searching for: " + user.getuName() + ".| UserDTO.findById()");
			
			// Execute SQL Query and loop over result set
			String sql1 = String.format("SELECT * FROM USER WHERE USER_ID='%S'",id);
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			user = new User(rs1.getString("UNAME"), rs1.getString("EMAIL"), rs1.getString("PASSWORD"), rs1.getInt("ISMOD"), rs1.getInt("UID"));
			
			// Cleanup
			rs1.close();
			stmt1.close();
			logger.info("Search found!| UserDTO.findById(" + id + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Cleanup Database
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.info("Unable to search the database.| UserDTO.findById()");
					e.printStackTrace();
				}
			}
		}
		return user;
	}
	/**A more general search for any user. This time, it searches by Email.
	 * @param User user
	 * @return user
	 */
	public User findBy(User user) 
	{
		User user2 = new User();
		try {
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);
			
			logger.info("Searching for just: " + user2.getuName() + ".| UserDTO.findby()");
			// Execute SQL Query and loop over result set
			String sql1 = String.format("SELECT * FROM USER WHERE UNAME= '%S'", user.getuName());
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			while (rs1.next())
			{
			user2  = new User(rs1.getString("UNAME"), rs1.getString("EMAIL"), rs1.getString("PASSWORD"), rs1.getInt("ISMOD"), rs1.getInt("UID"));
			}
			//Close all connections.
			rs1.close();
			stmt1.close();
			logger.info("Search successful!| UserDTO.findby()");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Cleanup Database
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.info("Failed to search the database.| UserDTO.findby()");
					e.printStackTrace();
				}
			}
		}
		return user2;
	}

	/**Inserts into the database all the data within the user object.
	 * @param User user
	 * @return bool
	 */
	public boolean create(User user) 
	{
		// Insert new user into the database.
		logger.info("Adding " + user.getuName() + " to the database.| UserDTO.create()");
		
				try {
					// Connect to the Database
					conn = DriverManager.getConnection(url, username, password);

					// Insert an User
					String sql1 = String.format("INSERT INTO USER(UNAME, PASSWORD, EMAIL, ISMOD) VALUES('%s', '%s', '%s', 0)",
					user.getuName(),user.getPassword(),user.getEmail(),user.getIsMod());
							
					Statement stmt1 = conn.createStatement();
					stmt1.executeUpdate(sql1);
					
					//Close the connection. 
					stmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					// Cleanup Database
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e) {
							logger.info("Unable to add this user to the database.| UserDTO.findAll()");
							e.printStackTrace();
						}
					}
				}

				logger.info(user.getuName() + " was successfully registered to the database!| UserDTO.create()");
				// Return OK
				return true;
	}
	//to be implemented later
	public boolean update(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	/**This is used to remove a user from the database.
	 * @param User user.
	 * @return boolean.
	 */
	public boolean delete(User user) {
		
		//Make sure that conn is empty.
		conn = null;
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			String sql1 = "DELETE FROM USER WHERE UID = " + user.getId();
			Statement stmt1 = conn.createStatement();
			
			//Run SQL command.
			stmt1.executeUpdate(sql1);
			
			//Close conenction. 
			stmt1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Cleanup Database
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}
}


