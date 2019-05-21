/**
 * 
 */
package sre2018.cloud.apis;

import java.io.File;
import java.io.IOException;
// import java.net.MalformedURLException;
import java.net.URL;
//import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sre5.cloud.ws.recordings.CLSRE5SvcConfig;
import sre5.cloud.ws.recordings.CLSre5Database;

import org.apache.commons.io.FileUtils;

/**
 * @author awp
 *
 */
public class CLGetNxtGnRecordedFile implements Runnable
{
//	private String call_start=null;
//	private URL file_url=null;
//  private int call_duration=0;
//	private String ani=null;
//	private String dnis=null;
	private String rec_file_dir=null;
//	private String call_sip_id=null;
	private static final int conn_timeout=6000;
	private static final int readTimeout=6000;
	private WSQDSre18CdrData cdr_data;
	private String source_ip=null;
	
	private static final Logger			log		= LoggerFactory.getLogger(CLGetNxtGnRecordedFile.class);

	public CLGetNxtGnRecordedFile(WSQDSre18CdrData _cdr_data, String src_ip)
	{
		this.cdr_data = _cdr_data;
		this.source_ip = src_ip;
	}
	/*
	public CLGetNxtGnRecordedFile(String _call_start, String _file_url, String _sip_id, String _ani,String _dnis,String _rec_file_dir)
	{
		this.call_start = _call_start;
		this.ani=_ani;
		this.dnis = _dnis;
		this.call_sip_id=_sip_id;
		this.rec_file_dir=_rec_file_dir;	// Default directory. We will create the actual directory from the call Start.
		try
		{
			this.file_url=new URL(_file_url);
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("CLGetNxtGnRecordedFile:File URL Exception\n" + e.toString() + "\n");
		}
	}
	*/
	
	public String GenerateRecordedFileName(String rec_file_path)
	{
		/*
		 * String.split : SPECIAL CHARACTERS IN REGEXP
		 * there are 12 characters with special meanings: the backslash \, the caret ^, the dollar sign $, the period or dot ., 
		 * the vertical bar or pipe symbol |, the question mark ?, the asterisk or star  *, the plus sign +, the opening parenthesis 
		 * (, the closing parenthesis ), and the opening square bracket [, the opening curly brace {, These special characters 
		 * are often called "metacharacters".
		 * 
		 * So, if you want to split on e.g. period/dot . which means "any character" in regex, use either backslash \ to 
		 * escape the individual special character like so split("\\."), or use character class [] to represent literal 
		 * character(s) like so split("[.]"), or use Pattern#quote() to escape the entire string like so split(Pattern.quote(".")).
		 */
		String[] call_start_parts=this.cdr_data.getSre18AStart().split("T");
		String[] call_date_parts=call_start_parts[0].split("-");
		// Now create the Recording file directory as Base_dir/CCYY/mm
		this.rec_file_dir=rec_file_path+"/"+call_date_parts[0]+"/"+call_date_parts[1];
		String[] call_time_parts=call_start_parts[1].split("\\.");	// Remember that the "." is a special REGEXP char so we must escape it. 
		String[] sip_id_parts=this.cdr_data.getSre18DialCallSid().split("-");
		return(call_start_parts[0].replaceAll("-", "")+"-"+call_time_parts[0].replaceAll(":", "")+"-"+this.cdr_data.getSre18Ani()+"-"+this.cdr_data.getSre18Dnis()+"-"+sip_id_parts[1]+".wav");
	}
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		// We need to download the file to the proper directory
		// ** First get the Filename and directory
		// If there is no recording file then just return as we have nothing to save
		if((cdr_data.getSre18_rec_file_public_url() == null) || (cdr_data.getSre18_rec_file_public_url().isEmpty()))
		{
			// We have not file to save so just log and return
			log.info("CLGetNxtGnRecordedFile: RstFileURL=is NUll or Empty. Nothing to save\n");
			return;
		}
		try
		{
			// First get the Specific for the REstcomm server
			CFGDRecordingServer cur_server = CLSRE5SvcConfig.sre5_conf.sre5_config.getServerConfig(this.source_ip);
			final String rstcm_fname = cdr_data.getSre18_rec_file_public_url().substring(cdr_data.getSre18_rec_file_public_url().lastIndexOf("/")+1);
			final String rstcm_file_name_url_str=cur_server.getSre18_recfileurl().replaceAll("IPADDRESS",this.source_ip)+"/"+ rstcm_fname;
			final String rec_file_name = GenerateRecordedFileName(cur_server.getRecfilepath());	// Need this to send to the CDR record. Also this function creates the actual directory path
			final String fname_str = this.rec_file_dir+"/"+ rec_file_name;
			File sn_file_name = new File (fname_str);
			log.info("CLGetNxtGnRecordedFile: RstFileURL="+rstcm_file_name_url_str+"\nSN File Name="+sn_file_name+"\n");
			FileUtils.copyURLToFile(new URL(rstcm_file_name_url_str), sn_file_name, conn_timeout, readTimeout);
			
			// Now we need to update the DB with the Filename etc.
			CLSre5Database cdr_db = new CLSre5Database();
			log.info("CLGetNxtGnRecordedFile: CDR Write Returns "+Integer.toString(cdr_db.Write2018Cdr(this.cdr_data,rec_file_name,rstcm_fname))+"\n");
			// Now to delete the File from the server. Waiting for details from Restcomm
			// DeleteRecordingFromServer(rstcm_fname,cur_server,CLSRE5SvcConfig.sre5_conf.sre5_config.getRstcomm_url());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("CLGetNxtGnRecordedFile:File Download Exception\n" + e.toString() + "\n");

		}
		
	}
	
	/*
	public void DeleteRecordingFromServer(String file_name,CFGDRecordingServer rst_server,String rstcomm_url)
	{
		
	}
	*/

}
