/**
CST-361
11-20-2019
This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
Interface for Facotry Pattern.
**/
package beans;

public interface BatchDTOInterface 
{
	public BatchItems getItems();

	public void setItems(BatchItems items);
	
	public int getStatus();

	public void setStatus(int status);

	public String getMessage();

	public void setMessage(String message);
}
