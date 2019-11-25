package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

public class BatchItems 
{

	private int tweetsTotal;
	private int likesTotal;
	private int retweetTotal;

	public BatchItems(int tweetsTotal, int likesTotal, int retweetTotal)
	{
		super();
		this.tweetsTotal = tweetsTotal;
		this.likesTotal = likesTotal;
		this.retweetTotal = retweetTotal;
		
	}
	public BatchItems()
	{
		super();
		tweetsTotal = 0;
		likesTotal = 0;
		retweetTotal = 0;
	}
	
	
	public int getTweetsTotal() {
		return tweetsTotal;
	}
	public void setTweetsTotal(int tweetsTotal) {
		this.tweetsTotal = tweetsTotal;
	}
	public int getLikesTotal() {
		return likesTotal;
	}
	public void setLikesTotal(int likesTotal) {
		this.likesTotal = likesTotal;
	}
	public int getRetweetTotal() {
		return retweetTotal;
	}
	public void setRetweetTotal(int retweetTotal) {
		this.retweetTotal = retweetTotal;
	}
	public String toString()
	{
		//		return "{pressure: "+this.pressure+" tempC: "+this.tempC+ " tempF: "+this.tempF+" humidity: "+this.humidity+"}";
		return "{tweetsTotal: " + this.tweetsTotal + ": likesTotal: " + this.likesTotal + " retweetTotal: " + this.retweetTotal + "}";
	}
}
