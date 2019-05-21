/**
 * 
 */
package sre2018.cloud.apis;

/**
 * @author awp
 *
 */
import java.io.IOException;
//import java.io.InputStream;
import java.util.UUID;

/**
* @author awp
*
*/

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fasterxml.jackson.databind.ObjectMapper;

//import sre5.cloud.ws.recordings.CFGDSre5Config;
//import sre5.cloud.ws.recordings.CLSRE5SvcConfig;

@Path("/dummynextstep")
public class WSCLdummynextstep
{
	String						resp_str;
	int							resp_code;
	private static final Logger	log	= LoggerFactory
											.getLogger(WSCLdummynextstep.class);

	@POST
	@Produces({  MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN  })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJSON(@Context HttpServletRequest req,String json_req_data
			)
	{
		String session_id = UUID.randomUUID().toString();
		MDC.put("sessionId", session_id);

		if (json_req_data == null)
		{
			log.error("WSCLdummynextstep: json_req_data=null");
			MDC.remove("sessionId");
			return Response.status(404).entity("").build();
		}
		else
		{
			// log.info("JSON="+json_data_class+"\n");
			ObjectMapper mapper = new ObjectMapper();
			try
			{
				WSQDAuthsrednisReqt dummy_data=mapper.readValue(json_req_data,WSQDAuthsrednisReqt.class);
				log.info("WSCLdummynextstep: Req from IP="+req.getRemoteAddr()+", ANI="+
						dummy_data.getSre18Ani()+", DNIS="+dummy_data.getSre18Dnis()+", CNAME="+
						dummy_data.getSre18Cname()+", SIPID="+dummy_data.getSre18ASipId()+"\n");
				// We can add additional logic here depending upon how th eoutgoing call ended. If busy, no answer etc then perhaps we need to send the call back
				// Also we should increment the number of attempts?
				// Write to the DB
				
				//WSRDAuthsrednisResp dnis_resp = GetDNISResponse(dummy_data);
				//log.info("Returning for DNIS :"+dummy_data.getSre18Dnis()+"\nJSON Data : "+ (dnis_resp != null ? dnis_resp.toJsonString() : "WHY NULL?")+"\n");
				
				return(Response.status(200).entity("OK").build());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("WSCLdummynextstep:Exception\n" + e.toString() + "\n");
				MDC.remove("sessionId");
				return Response.status(405).entity("Malformed JSON Body in Request.\n").build();
			}

		}
	}

}
