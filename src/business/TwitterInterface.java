package business;

import java.util.List;

import beans.BatchItems;
import beans.Search;

public interface TwitterInterface <T>
{
	public List<T> getAllData(); 
	void Bridge(Search s);
}
