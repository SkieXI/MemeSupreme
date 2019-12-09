package data;

import java.util.List;

import beans.BatchDTO;
import beans.BatchItems;

public interface BatchDataInterface <T>
{
	public boolean create(BatchDTO t);
	public List<T> findall();
	public T findby(int i);
}
