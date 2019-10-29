/**
CST-361
9-29-2019
This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
This class is used for REST services.
**/
package beans;

public class ResponseModel 
{
	//Variables with a int for a status, and a String for a message.
	private int status;
	private String message;
	
	//Non-default constructor.
	public ResponseModel(int status, String message)
	{
		super();
		this.status = status;
		this.message = message;
	}
	public ResponseModel()
	{
		super();
		message = "";
		status = 0;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
