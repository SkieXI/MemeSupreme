package data;

import java.util.List;

import beans.TweetsDTO;
import beans.TwitterItems;

public interface TwitterDataInterface<T>
{
	public List<T> findAll();
	public boolean create(List<T> t);

}
