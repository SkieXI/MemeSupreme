package util;

import beans.BatchItems;
import beans.BatchDTO;
import data.BatchDataInterface;

public class DataFactory {
	//use getUser method to get object of type shape 
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
