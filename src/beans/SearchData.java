/**CST-361
 * 10-21-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This class combines all the variables for the REST service along with the DTO Search.
 */

package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchData extends Search
{
	//Import the bean Switch.
	private Search data;

	//Non-default Constructor
	public SearchData(int status, String message, SearchData data)
	{
		super(message, status);
		this.data = data;
	}
	//Default Constuctor
	public SearchData()
	{
		super("",0);
		this.data = null;
	}
	
	//Gettesr and setters.
	public Search getData() {
		return data;
	}

	public void setData(Search search) {
		this.data = search;
	}
}
