//Joe Leon
//CST-361
//9-26-19
//This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
//We used source code from the following websites to complete this assignment:
//WEBSITE 1
//WEBSITE 2

package data;

import java.util.List;

public interface UserDataInterface <T>
{
	public List<T> findAll();

	public T findBy(T t);

	public boolean create(T t);

	public boolean update(T t);

	public boolean delete(T t);

	public T findById(int i);
}