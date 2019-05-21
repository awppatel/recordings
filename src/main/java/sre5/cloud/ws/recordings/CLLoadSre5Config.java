/**
 * 
 */
package sre5.cloud.ws.recordings;

/**
 * @author awp
 *
 */
import java.io.File;
import java.io.IOException;

// import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CLLoadSre5Config
{
	public CFGDSre5Config sre5_config = null;
	public int err_code = 0;

	public CLLoadSre5Config(String cfg_file_name)
	{

		try
		{
			// ObjectMapper provides functionality for data binding between
			// Java Bean Objects/POJO and JSON constructs/string
			if(sre5_config == null)
			{
				ObjectMapper mapper = new ObjectMapper();

				// read JSON format file, deserialize JSON, bind values to User
				// class object
				sre5_config = mapper.readValue(new File(cfg_file_name),CFGDSre5Config.class);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			err_code = 999;
		}
	}
}
