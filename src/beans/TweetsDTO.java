/**CST-361
 * 12-6-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * This class is the DTO of all TwitterItems. I think its used somewhere.
 */
package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class TweetsDTO implements BatchDTOInterface<TwitterItems>
{
	private int status;
	private String message;
	private TwitterItems items;
	
	public TweetsDTO(int status, String message, TwitterItems items)
	{
		super();
		this.status = status;
		this.message = message;
		this.items = items;
	}
	public TweetsDTO()
	{
		super();
		message = "";
		status = 0;
		items = null;
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
	@Override
	public TwitterItems getItems() {
		return items;
	}
	@Override
	public void setItems(TwitterItems items) {
		this.items = items;
	}

}
