/**CST-361
 * 10-21-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This bean contains both the Status of the REST service, its message, the bean of TwitterItems, and a list of TwitterItems.
 */
package beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class TwitterResponseData extends ResponseModel
{
	//private TwitterItems data;
	private List<TwitterItems> items;
	
	//Default Constructor
	public TwitterResponseData()
	{
		super(0,"");
		
	}
	//Non-Default Constructor
	public TwitterResponseData(int status, String message, List<TwitterItems> items)
	{
		super(status, message);
		//this.data = data;
		this.items = items;
	}

	//Getters and Setters.
	public List<TwitterItems> getItems() {
		return items;
	}

	public void setItems(List<TwitterItems> items) {
		this.items = items;
	}
}
