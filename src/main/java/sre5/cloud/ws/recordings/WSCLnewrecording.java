/**
 * 
 */
package sre5.cloud.ws.recordings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.math.BigInteger;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.io.IOException;
//import java.util.UUID;

//import org.slf4j.MDC;

import org.slf4j.MDC;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.HttpEntity;
//import org.glassfish.jersey.media.multipart.*;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;  
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

// import java.sql.Timestamp;
import java.text.DateFormat;
// import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


//import comlink.telephony.ws.dynswitch.mvoip.ws01.WSCLactivate;

/**
 * @author awp
 * 
 */
@Path("/newrecording")
public class WSCLnewrecording
{
	String						resp_str;
	int							resp_code;
	private static final Logger	log	= LoggerFactory
											.getLogger(WSCLnewrecording.class);
	private static final CFGDSre5Config rec_config = CLSRE5SvcConfig.sre19_config;
	private WSQDRecordingMetaData rec_metadata;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(			
			)
	{
		String ret_str = "Activate Got it! Newrecording Value = " + Integer.toString(++CLSRE5SvcConfig.temp_val) +  "\n";
		log.info("New Recording Sending Resp=" + ret_str);
		return ret_str;
	}

	
	@POST
	@Produces({ "text/plain" })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response consumeJSON(@Context HttpServletRequest req,
			@FormDataParam("MAX_FILE_SIZE") long file_size,
			@FormDataParam("userfile") InputStream uploadedInputStream,
			@FormDataParam("userfile") FormDataContentDisposition fileDetail,
			@FormDataParam("job") byte[] recMetaData
			)

	{
		String session_id = UUID.randomUUID().toString();
		MDC.put("sessionId", session_id);

		if(recMetaData == null)
		{
			resp_str = "No file METADATA was received\n";
			//meta_rcvd = false;
			log.info(resp_str);
			MDC.remove("sessionID");
			return Response.status(402).entity(resp_str).build();
		}
		else
		{
			log.info(bytesToString(recMetaData) + "\n");
		}

		if(rec_config.getGeneric_data().isDual_record())
		{
			// DoDualRecord will fill the appropriate resp_code and resp_str to send back
			DoDualRecord(req,file_size,uploadedInputStream,fileDetail,recMetaData);
			return Response.status(resp_code).entity(resp_str).build();
		}

		if(fileDetail == null)
		{
			resp_str = "No file data was received\n";
			log.info(resp_str);
			MDC.remove("sessionID");
			return Response.status(401).entity(resp_str).build();
			
		}
		
		
	    //boolean meta_rcvd=true;
	    
		
		switch(SaveRecordedFileCDR(recMetaData))
		{
			case 0 :		// CDR was saved and all is well
				if(UploadRecordedFile(uploadedInputStream,fileDetail) == 0)		// ALl is well return a success
				{
					MDC.remove("sessionID");
					return Response.status(200).entity("SUCCESS\n").build();
				}
				else
				{
					MDC.remove("sessionID");
					return Response.status(405).entity("Error in File Upload\n").build();
				}
				
			case 999 : 		// Do not record this agent
				MDC.remove("sessionID");
				return Response.status(200).entity("SUCCESS\n").build();
				
			default : 	// Anything else and we flag error and return
				MDC.remove("sessionID");
				return Response.status(403).entity("CDR Error\n").build();
		}

	}
	
	private void DoDualRecord(HttpServletRequest _req,
							long _file_size,
							InputStream _uploadedInputStream,
							FormDataContentDisposition _fileDetail,
							byte[] _recMetaData)
	{
		resp_code=200;
		resp_str="SUCCESS\n";
		
		CLParseCdrJSON json_data = new CLParseCdrJSON(_recMetaData);
		rec_metadata = json_data.GetJSONData();
		if(rec_metadata == null)		// Error in Parsing the data
		{
			log.error("DoDualRecord:Error Parsing JSON:" + bytesToString(_recMetaData) + "\n");
			resp_code = 403;
			resp_str = "Error Parsing JSON Metadata\n";
			return;
		}
		// No check to see if this is a First Record or last
		String STOC = rec_metadata.getStoc();
		if(STOC == null || STOC.equals("") || STOC.isEmpty())
		{
			// Special condition where we do not get ETOC and STOC so this is final record.
			log.info("DualRecord: STOC=EMPTY, handle special record\n");
			rec_metadata.setStoc(GetNewSTOC(rec_metadata.getRecFname()));
			rec_metadata.setEtoc(GetMySQLDateTime());
		}
		// Now get teh ETOC. At this point if above condition was met then teh ETOC will not be empty and we continue.
		String ETOC = rec_metadata.getEtoc();
		if(ETOC == null || ETOC.equals("") || ETOC.isEmpty())		// This is the first record
		{
			rec_metadata.setEtoc(rec_metadata.getStoc());		// Date cannot be empty so set to start date for 0 duration
			log.info("DualRecord: STOC="+rec_metadata.getStoc()+", ETOC=" + rec_metadata.getEtoc() + "\n");
			CLSre5Database sre5_dbase = new CLSre5Database();
			switch(sre5_dbase.InsertCDR(rec_metadata,rec_config.getSvc_db_host().getCur_cdr_logic()))
			{
				case 0 :		// All was fine
				case 999 : 	// DO not record this Agent
					break;
					
				default :	// All others are errors
					resp_code = 403;
					resp_str = "DB Error\n";
					break;
			}
		}
		else		// Second Record, update the first
		{
			CLSre5Database sre5_dbase = new CLSre5Database();
			// Changed on Dec 12, 2016. We should always store the file even if we did not get the A record.
			switch(sre5_dbase.InsertCDR(rec_metadata,rec_config.getSvc_db_host().getCur_cdr_logic()))
			{
				case 0 :		// All was fine, need to upload the file
					long rec_file_len = UploadRecordedFile(_uploadedInputStream,_fileDetail);
					/* Jan 14, 2020. We absorb all records even if the lenght is 0
					if(rec_file_len <= 0)		// Error Uploading the file??
					{
						resp_code = 405;
						resp_str = "Error in File Upload\n";
						// We should at this point set teh record as an error file upload
						sre5_dbase.SetFileError(rec_metadata.getRecID());
					}
					else
					*/
					{
						sre5_dbase.SetFileDuration(rec_metadata.getRecID(),rec_file_len > 0L ? rec_file_len : 0L);
					}
					break;

				case 999 : 	// DO not record this Agent
					break;
					
				default :	// All others are errors
					resp_code = 403;
					resp_str = "DB Error\n";
					break;
			}

		}

	}
	
	private String GetMySQLDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		Date date = new Date();
		return(dateFormat.format(date)); 
	}
	
	/*
	 * private String GetNewSTOC(String stoc_data_str)
	 * 
	 * Takes the Filename and extracts teh start date/time for this record
	 */
	private String GetNewSTOC(String stoc_data_str)
	{
		//StringBuilder stoc_str = new StringBuilder();
		String stoc_parts[] = stoc_data_str.split("-");
		return(stoc_parts[2]+"-"+stoc_parts[3]);	// Return CCYYMMDD-HHMMSS
		/* Old version to make CCYY-MM-DD HH:MM:SS
		stoc_str.append(stoc_parts[2].substring(0, 4));
		stoc_str.append("-");
		stoc_str.append(stoc_parts[2].substring(4, 6));
		stoc_str.append("-");
		stoc_str.append(stoc_parts[2].substring(6, 8));
		stoc_str.append(" ");
		stoc_str.append(stoc_parts[3].substring(0, 2));
		stoc_str.append(":");
		stoc_str.append(stoc_parts[3].substring(2, 4));
		stoc_str.append(":");
		stoc_str.append(stoc_parts[3].substring(4, 6));
		stoc_str.append(stoc_parts[2])
		log.info("GetNewSTOC: STOC_DATA="+stoc_data_str+ ", STOC="+stoc_str.toString()+ "\n");
		return(stoc_str.toString());
		*/

	}
	
	private int SaveRecordedFileCDR(byte[] cdr_metadata)
	{
		// First get teh JSON Data
		CLParseCdrJSON json_data = new CLParseCdrJSON(cdr_metadata);
		rec_metadata = json_data.GetJSONData();
		if(rec_metadata == null)		// Error in Parsing the data
		{
			log.error("Error Parsing JSON:" + bytesToString(cdr_metadata) + "\n");
			return(403);
		}
		// Ok Parsed the Data, now store the record and see if this call needs to be recorded
		CLSre5Database sre5db = new CLSre5Database();
		return(sre5db.InsertCDR(rec_metadata,rec_config.getSvc_db_host().getCur_cdr_logic()));
		//return(0);
	}


	
	private long UploadRecordedFile(InputStream uploadedInputStream,FormDataContentDisposition fileDetail)
	{
		// Need to convert the GMT Time to the local time since the Portal will save the time as local time and look for file in that directory
		CLSre5Database sre5db = new CLSre5Database();
		String fileLocation = rec_config.getGeneric_data().getRecfilepath() + "/" + sre5db.GetRecFileDir(rec_metadata.getRecID(),rec_metadata.getStoc());
		//rec_metadata.getStoc().substring(0, 4) + "/" + rec_metadata.getStoc().substring(4, 6);
		
		// Check if the location directory exists. If not create it.
		new File(fileLocation).mkdirs();
		
		fileLocation = fileLocation + "/" + rec_metadata.FormatDBFileName();
		
		// saving file
		try
		{
			FileOutputStream out = new FileOutputStream(new File(fileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			//out = new FileOutputStream(new File(fileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1)
			{
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			log.info("File successfully uploaded to : " + fileLocation + "\n");
			Mp3File mp3file = new Mp3File(fileLocation);
			log.info("Length of \"" + fileLocation + "\" is: " + mp3file.getLengthInSeconds() + " seconds");

			return(mp3file.getLengthInSeconds());
		}
		catch (IOException | UnsupportedTagException | InvalidDataException e)
		{
			e.printStackTrace();
			return(-405);
		}
	}
	
	public String bytesToString(byte[] data)
	{
		StringBuilder ret_str = new StringBuilder();
		for(int i=0;i< data.length;i++)
		{
			ret_str.append((char)(data[i] & 0xFF));
		}
		return(ret_str.toString());
	}


}
