/**
CST-361
11-20-2019
This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
Interface for Facotry Pattern.
**/
package beans;

/**
 * 
 * @author jleon
 *
 * @param <T>
 */
public interface BatchDTOInterface <T>
{
	public T getItems();

	public void setItems(T t);
	
	public int getStatus();

	public void setStatus(int status);

	public String getMessage();

	public void setMessage(String message);
}
