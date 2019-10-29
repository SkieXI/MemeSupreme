//Joe Leon
//CST-361
//9-26-19
//This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
//We used source code from the following websites to complete this assignment:
//WEBSITE 1
//WEBSITE 2

package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import beans.User;
import data.UserDataInterface;

@Stateless
@Local(UserDataInterface.class)
@LocalBean
public class UserDTO implements UserDataInterface<User>
{
	
	//
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/memesupreme";
	String username = "root";
	String password = "root";
	

	/**This provides a huge list of everything within the database.
	 * @param 
	 * @return users
	 */
	public List<User> findAll() 
	{
		List<User> users = new ArrayList<User>();
		
		try {
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			// Execute SQL Query and loop over result set
			String sql1 = "SELECT * FROM USER";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			while (rs1.next()) 
			{
				User user = new User(rs1.getString("UNAME"), rs1.getString("EMAIL"), rs1.getString("PASSWORD"), rs1.getInt("ISMOD"));
				users.add(user);
			}

			// Cleanup
			rs1.close();
			stmt1.close();
		} catch (SQLException e) {
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

		// Return list of Users
		return users;
	}

	/**
	 * Searches the database for any entries that match the input username.
	 * @param id
	 * @return users
	 */
	public User findById(int id) {
				User user = new User();
		try {
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			// Execute SQL Query and loop over result set
			String sql1 = String.format("SELECT * FROM USER WHERE USER_ID='%S'",id);
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			//user = new User
			user = new User(rs1.getString("UNAME"), rs1.getString("EMAIL"), rs1.getString("PASSWORD"), rs1.getInt("ISMOD"));
			
			// Cleanup
			rs1.close();
			stmt1.close();
		} catch (SQLException e) {
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
			// Execute SQL Query and loop over result set
			String sql1 = String.format("SELECT * FROM USER WHERE UNAME= '%S'", user.getuName());
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			while (rs1.next())
			{
			user2  = new User(rs1.getString("UNAME"), rs1.getString("EMAIL"), rs1.getString("PASSWORD"), rs1.getInt("ISMOD"));
			}
			
			rs1.close();
			stmt1.close();
		} catch (SQLException e) {
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
		return user2;
	}

	/**Inserts into the database all the data within the user object.
	 * @param User user
	 * @return bool
	 */
	public boolean create(User user) 
	{
		// Insert User
				try {
					// Connect to the Database
					conn = DriverManager.getConnection(url, username, password);

					// Insert an User
					String sql1 = String.format("INSERT INTO USER(UNAME, PASSWORD, EMAIL, ISMOD) VALUES('%s', '%s', '%s', 0)",
					//		user.getUserName(),user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getPermissions());
					user.getuName(),user.getPassword(),user.getEmail(),user.getIsMod());
							
							Statement stmt1 = conn.createStatement();
					stmt1.executeUpdate(sql1);
					stmt1.close();
				} catch (SQLException e) {
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

				// Return OK
				return true;
	}
	//to be implemented later
	public boolean update(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	//to be implemented later
	public boolean delete(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
