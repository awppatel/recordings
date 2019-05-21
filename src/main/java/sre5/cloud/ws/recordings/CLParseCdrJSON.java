/**
 * 
 */
package sre5.cloud.ws.recordings;

// import java.io.File;
import java.io.IOException;

/**
 * @author awp
 *
 */
import com.fasterxml.jackson.databind.ObjectMapper;

public class CLParseCdrJSON
{
	public WSQDRecordingMetaData cdr_json_data = null;
	public int ret_val=-1;

	public CLParseCdrJSON(byte[] cdr_json )
	{

		try
		{
			// ObjectMapper provides functionality for data binding between
			// Java Bean Objects/POJO and JSON constructs/string
			if(cdr_json_data == null)
			{
				ObjectMapper mapper = new ObjectMapper();

				// read JSON format file, deserialize JSON, bind values to User
				// class object
				cdr_json_data = mapper.readValue(cdr_json,WSQDRecordingMetaData.class);
				// String resp_str = "LogFile name = " + mvoip_config.getConfLogging().getConfLogfilename() + "\n";
				//System.out.println(resp_str);
				ret_val = 0;
			}
			else
				ret_val = -3;

		}
		catch (IOException e)
		{
			e.printStackTrace();
			ret_val = -999;
		}
	}
	
	public WSQDRecordingMetaData GetJSONData()
	{
		return this.cdr_json_data;
	}

}
