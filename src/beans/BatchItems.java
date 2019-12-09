/**
CST-361
9-29-2019
This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
This is the class for the items that are going to be recorded in Minimemed and saved in our database.
**/
package beans;

public class BatchItems 
{

	//Variables for what to get from the MiniMeme.
	private int tweetsTotal;
	private int likesTotal;
	private int retweetTotal;

	//Non-default Constructor
	public BatchItems(int tweetsTotal, int likesTotal, int retweetTotal)
	{
		super();
		this.tweetsTotal = tweetsTotal;
		this.likesTotal = likesTotal;
		this.retweetTotal = retweetTotal;
		
	}
	//Default Constructor
	public BatchItems()
	{
		super();
		tweetsTotal = 0;
		likesTotal = 0;
		retweetTotal = 0;
	}
	
	//Getters and Setters.
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
	//REturns everything isn a sting.
	public String toString()
	{
		//		return "{pressure: "+this.pressure+" tempC: "+this.tempC+ " tempF: "+this.tempF+" humidity: "+this.humidity+"}";
		return "{tweetsTotal: " + this.tweetsTotal + ": likesTotal: " + this.likesTotal + " retweetTotal: " + this.retweetTotal + "}";
	}
}
