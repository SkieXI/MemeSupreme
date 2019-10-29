package data;

import java.util.List;

import beans.TwitterItems;
import beans.TwitterResponseData;

public interface TwitterDataInterface 
{
	@SuppressWarnings("rawtypes")
	//List wordSearch(String word, int Limit);
	public boolean create(TwitterItems data);
	public TwitterItems findBy(TwitterItems ti);
	public List<TwitterItems> findAll();
}
