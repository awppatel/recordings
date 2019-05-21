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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

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

import sre5.cloud.ws.recordings.CLSRE5SvcConfig;

//import sre5.cloud.ws.recordings.CFGDSre5Config;
//import sre5.cloud.ws.recordings.CLSRE5SvcConfig;

@Path("/writesre18cdr")
public class WSCLWriteSre18Cdr
{
	String						resp_str;
	int							resp_code;
	private static final Logger	log	= LoggerFactory
											.getLogger(WSCLWriteSre18Cdr.class);
	private static final ExecutorService es = CLSRE5SvcConfig.executorService;
	private StringBuffer log_buff = new StringBuffer(1024); 
	private static final String myclassname="writesre18cdr";

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
			log_buff.append("writesre18cdr: json_req_data=null");
			log.error(log_buff.toString());
			MDC.remove("sessionId");
			return Response.status(404).entity("").build();
		}
		else
		{
			log_buff.setLength(0);
			log_buff.append(myclassname).append("JSON Raw=").append(json_req_data);
			log.info(log_buff.toString());
			// log.info("JSON="+json_data_class+"\n");
			ObjectMapper mapper = new ObjectMapper();
			try
			{
				WSQDSre18CdrData sre18_cdr=mapper.readValue(json_req_data,WSQDSre18CdrData.class);
				log_buff.setLength(0);
				log_buff.append(myclassname)
					.append("WSCLAuthSreAniDnis: Req from IP=")
					.append(req.getRemoteAddr())
					.append(", ANI=")
					.append(sre18_cdr.getSre18Ani())
					.append(", DNIS=")
					.append(sre18_cdr.getSre18Dnis())
					.append(", CNAME=")
					.append(sre18_cdr.getSre18Cname())
					.append(", SIPID=")
					.append(sre18_cdr.getSre18ASipId())
					.append(", DialCallStatus=")
					.append(sre18_cdr.getSre18DialCallStatus())
					.append(", Outgoing #=")
					.append(sre18_cdr.getSre18Digits())
					.append(", ApiVersion=")
					.append(sre18_cdr.getSre18ApiVersion());

				log.info(log_buff.toString());
				// Get the recorded file and update the DB int eh background
				Runnable do_qrcode = new CLGetNxtGnRecordedFile(sre18_cdr,req.getRemoteAddr());
				es.execute(do_qrcode);

				return(Response.status(200).entity("OK").build());
			}
			catch (IOException | RejectedExecutionException | NullPointerException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("WSCLWriteSre18Cdr:Exception\n" + e.toString() + "\n");
				MDC.remove("sessionId");
				return Response.status(405).entity("Malformed JSON Body in Request.\n").build();
			}

		}
	}

}
