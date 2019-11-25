package data;

import java.util.List;

import beans.BatchItems;

public interface BatchDataInterface <T>
{
	public boolean create(T t);
	public List<T> findall();
	public T findby(int i);
}
