/**
 * 
 */
package sre2018.cloud.apis;

/**
 * @author awp
 *
 */

import java.io.IOException;
//import java.util.Random;
//import java.io.InputStream;
import java.util.UUID;
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


@Path("/authsreanidnis")
public class WSCLAuthSreAniDnis
{
	String						resp_str;
	int							resp_code;
	private static final Logger	log	= LoggerFactory
											.getLogger(WSCLAuthSreAniDnis.class);
	private StringBuffer log_buff = new StringBuffer(1024); 
	private static final String myclassname="authsreanidnis:";
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
			log_buff.setLength(0);
			log_buff.append("WSCLAuthSreAniDnis: json_req_data=null");
			log.error(log_buff.toString());
			MDC.remove("sessionId");
			return Response.status(404).entity("").build();
		}
		else
		{
			// log.info("JSON="+json_data_class+"\n");
			log_buff.setLength(0);
			log_buff.append(myclassname).append("JSON Raw=").append(json_req_data);
			log.info(log_buff.toString());
			ObjectMapper mapper = new ObjectMapper();
			try
			{
				WSQDAuthsrednisReqt dnis_req=mapper.readValue(json_req_data,WSQDAuthsrednisReqt.class);
				log_buff.setLength(0);
				log_buff.append(myclassname)
					.append("WSCLAuthSreAniDnis: Req from IP=")
					.append(req.getRemoteAddr())
					.append(", ANI=")
					.append(dnis_req.getSre18Ani())
					.append(", DNIS=")
					.append(dnis_req.getSre18Dnis())
					.append(", CNAME=")
					.append(dnis_req.getSre18Cname())
					.append(", SIPID=")
					.append(dnis_req.getSre18ASipId());
					
				log.info(log_buff.toString());

				// Check the DB if the DNIS exists, is Active and get the JSON String to return
				String dnis_json_info = new CLSre5Database().GetDnisJson(dnis_req.getSre18Dnis());
				
				// String new_dnis_resp = dnis_json_info.replace("{", "{ \"ani_to_send\" : \"" + ani_to_ret +"\",");
				
				//String dnis_resp = "{ \"ani_to_send\" : \"" + ani_to_ret +"\",\"dnis_info\" : "+dnis_json_info+"}";
				log_buff.setLength(0);
				log_buff.append(myclassname)
					.append("Returning for DNIS:")
					.append(dnis_req.getSre18Dnis())
					.append("\nJSON Data: ")
					.append((dnis_json_info == null ? "WHY NULL?" : dnis_json_info));
				
				log.info(log_buff.toString());
				
				return(Response.status(dnis_json_info== null ? 405 : 200).entity((dnis_json_info == null) ? "Server Error" : dnis_json_info).build());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("WSCLAuthSreAniDnis:Exception\n" + e.toString() + "\n");
				MDC.remove("sessionId");
				return Response.status(405).entity("Malformed JSON Body in Request.\n").build();
			}

		}
	}

}
