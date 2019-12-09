/**CST-361
 * 12-4-19
 * This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
 * Interface for all BatchItem methods.
 */
package business;

import java.util.List;

import beans.Search;

public interface BatchInterface <T>
{
	public List<T> getAllData(); 
	public T getData(int t);
	void Bridge(Search s);
}
