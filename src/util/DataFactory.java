/**Joe Leon
**CST-361
**10-20-19
**This assignment was completed in collaboration with Joe Leon, and Lewis Brown.
**
*/

package util;

import beans.BatchItems;
import beans.BatchDTO;
import data.BatchDataInterface;

public class DataFactory {
	
	/**Factory pattern.
	 * 
	 * @param batchData
	 * @return BatchDTO.
	 */
	public BatchDTO getBatchInterface(String batchData)
	{
		if(batchData ==null)
		{
			return new BatchDTO(-2,"Null data.", new BatchItems());
		}
		
		if(batchData.equalsIgnoreCase("DATAMISSING"))
		{
			return new BatchDTO(-2, "Batch data not found", new BatchItems());
		}
		if (batchData.equalsIgnoreCase("DTATAFOUND"))
		{
			return new BatchDTO(0, "Data Found!", new BatchItems());
		}
		return new BatchDTO(-2, "Unknown data.", new BatchItems());
	}
}
