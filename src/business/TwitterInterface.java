package business;

import java.util.List;

import beans.BatchItems;
import beans.Search;

public interface TwitterInterface <T>
{
	public void PullnSave(T t);
	public void SaveNSave(T t);
	public List<T> getAllData(); 
	public T getData(int t);
	void Bridge(Search search);
}
