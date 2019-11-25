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
