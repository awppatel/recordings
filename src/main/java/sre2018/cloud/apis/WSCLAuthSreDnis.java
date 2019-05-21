	/**
 * 
 */
package sre2018.cloud.apis;

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

import sre5.cloud.ws.recordings.CLSre5Database;


@Path("/authsrednis")
public class WSCLAuthSreDnis
{
	String						resp_str;
	int							resp_code;
	private static final Logger	log	= LoggerFactory
											.getLogger(WSCLAuthSreDnis.class);
	//private static final CFGDSre5Config rec_config = CLSRE5SvcConfig.sre5_conf.sre5_config;
	
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
			log.error("WSCLAuthSreDnis: json_req_data=null");
			MDC.remove("sessionId");
			return Response.status(404).entity("").build();
		}
		else
		{
			// log.info("JSON="+json_data_class+"\n");
			ObjectMapper mapper = new ObjectMapper();
			try
			{
				WSQDAuthsrednisReqt dnis_req=mapper.readValue(json_req_data,WSQDAuthsrednisReqt.class);
				log.info("WSCLAuthSreDnis: Req from IP="+req.getRemoteAddr()+", ANI="+
						dnis_req.getSre18Ani()+", DNIS="+dnis_req.getSre18Dnis()+", CNAME="+
						dnis_req.getSre18Cname()+", SIPID="+dnis_req.getSre18ASipId()+"\n");

				// Check the DB if the DNIS exists, is Active and get the JSON String to return
				CLSre5Database dnis_db = new CLSre5Database();
				
				String dnis_resp = dnis_db.GetDnisJson(dnis_req.getSre18Dnis());
				
				log.info("Returning for DNIS :"+dnis_req.getSre18Dnis()+"\nJSON Data : "+ (dnis_resp != null ? dnis_resp : "WHY NULL?")+"\n");
				
				return(Response.status(dnis_resp== null ? 405 : 200).entity((dnis_resp != null) ? dnis_resp : "Server Error").build());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("WSCLAuthSreDnis:Exception\n" + e.toString() + "\n");
				MDC.remove("sessionId");
				return Response.status(405).entity("Malformed JSON Body in Request.\n").build();
			}

		}
	}
	
}
