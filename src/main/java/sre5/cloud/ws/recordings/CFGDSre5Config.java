package sre5.cloud.ws.recordings;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import sre2018.cloud.apis.CFGDRecordingServer;
import sre2018.cloud.apis.WSRDAuthsrednisResp;


@JsonPropertyOrder({ "sre5dbase","dualrecord","recfilepath","rstcomm_url","logfilepath","gmtoffset","logname","callbridge","directdial","recording_servers"})

public class CFGDSre5Config
{
	@JsonProperty("sre5dbase")
	private CFGDSre5DbConfig svc_db_host;

	@JsonProperty("dualrecord")
	private String dual_record;
	
	boolean is_dual_record;
	
	@JsonProperty("logfilepath")
	String log_file_path;

	@JsonProperty("gmtoffset")
	int gmt_offset;

	@JsonProperty("rstcomm_url")
	String rstcomm_url = "";
	

	@JsonProperty("logname")
	String logname;
	
	@JsonProperty("callbridge")
	WSRDAuthsrednisResp callbridge;

	@JsonProperty("directdial")
	WSRDAuthsrednisResp directdial;
	
	@JsonProperty("recfilepath")
	String recfilepath;
	
	@JsonProperty("recording_servers")

	List<CFGDRecordingServer> recording_servers = new ArrayList<CFGDRecordingServer>();
	
	/* We declare a default URL assuming that the recording files are located on the server
	 * 192.168.1.6 via HTTP at port 27080. 
	 * 
	 * NOTE we are using HTTP as this is a private closed network
	 * 
	 * On the 192.168.1.6 Server we need to define an entry int eh Vhosts.conf file listening on 27080
	 * 
	 * 
	 * 
	 * NameVirtualHost *:27080

		<VirtualHost *:27080>
		    DocumentRoot "/var/www/SoundRecall"
		    ServerName 192.168.1.6
		    ServerAlias 192.168.1.6
		</VirtualHost>
		
		Also have the HTTP daemon (/etc/httpd/conf/httpd.conf) listening on the IP:port or just the port

	 * 
	 */
	// private static final String def_recfile_url="http://IPADDRESS:27080/recordings";

	public CFGDRecordingServer getServerConfig(String server_ip)
	{
		for(int i=0;i< recording_servers.size();i++)
		{
			if(recording_servers.get(i).getServer_ip().equals(server_ip))
			{
				return recording_servers.get(i);
			}
		}
		
		return(null);
	}
	
	/**
	 * @return the rstcomm_url
	 */
	public String getRstcomm_url()
	{
		return rstcomm_url;
	}
	/**
	 * @return the recording_servers
	 */
	public List<CFGDRecordingServer> getRecording_servers()
	{
		return recording_servers;
	}

	/**
	 * @return the logname
	 */
	public String getLogname()
	{
		return logname;
	}

	/**
	 * @param logname the logname to set
	 */
	public void setLogname(String logname)
	{
		this.logname = logname;
	}

	/**
	 * @return the svc_db_host
	 */
	public CFGDSre5DbConfig getSvc_db_host()
	{
		return this.svc_db_host;
	}

	/**
	 * @param svc_db_host the svc_db_host to set
	 */
	public void setSvc_db_host(CFGDSre5DbConfig svc_db_host)
	{
		this.svc_db_host = svc_db_host;
	}


	/**
	 * @return the dual_record
	 */
	public String getDual_record()
	{
		return this.dual_record;
	}

	/**
	 * @param dual_record the dual_record to set
	 */
	public void setDual_record(String _dual_record)
	{
		this.dual_record = _dual_record;
	}

	/**
	 * @return the is_dual_record
	 */
	public boolean Is_dual_record()
	{
		return this.is_dual_record;
	}

	/**
	 * @param is_dual_record the is_dual_record to set
	 */
	private void setIs_dual_record(boolean is_dual_record)
	{
		this.is_dual_record = is_dual_record;
	}

	public void SetBoolDualRec()
	{
		if(this.dual_record != null && (this.dual_record.equalsIgnoreCase("YES") || this.dual_record.equalsIgnoreCase("1")))
		{
			setIs_dual_record(true);
		}
		else
			setIs_dual_record(false);
	}

	/**
	 * @return the log_file_path
	 */
	public String getLog_file_path()
	{
		return this.log_file_path;
	}

	/**
	 * @param log_file_path the log_file_path to set
	 */
	public void setLog_file_path(String _log_file_path)
	{
		this.log_file_path = _log_file_path;
	}

	/**
	 * @return the gmt_offset
	 */
	public int getGmt_offset()
	{
		return this.gmt_offset;
	}

	/**
	 * @param gmt_offset the gmt_offset to set
	 */
	public void setGmt_offset(int _gmt_offset)
	{
		this.gmt_offset = _gmt_offset;
	}
	
	/**
	 * @param None the SetConfigData to set
	 */
	public void SetConfigData()
	{
		// First make sure that we have the Dual Record flag
		if(this.dual_record != null && (this.dual_record.equalsIgnoreCase("YES") || this.dual_record.equalsIgnoreCase("1")))
		{
			setIs_dual_record(true);
		}
		else
			setIs_dual_record(false);
		this.svc_db_host.setCur_cdr_logic();
		/* This will be now based upon the recording servers
		if(this.sre18_recfileurl == null)
			this.sre18_recfileurl = def_recfile_url;
		*/
	}

	/**
	 * @return the callbridge
	 */
	public WSRDAuthsrednisResp getCallbridge()
	{
		return callbridge;
	}

	/**
	 * @param callbridge the callbridge to set
	 */
	public void setCallbridge(WSRDAuthsrednisResp callbridge)
	{
		this.callbridge = callbridge;
	}

	/**
	 * @return the directdial
	 */
	public WSRDAuthsrednisResp getDirectdial()
	{
		return directdial;
	}

	/**
	 * @param directdial the directdial to set
	 */
	public void setDirectdial(WSRDAuthsrednisResp directdial)
	{
		this.directdial = directdial;
	}

	/**
	 * @return the recfilepath
	 */
	public String getRecfilepath()
	{
		return recfilepath;
	}

	/**
	 * @param recfilepath the recfilepath to set
	 */
	public void setRecfilepath(String recfilepath)
	{
		this.recfilepath = recfilepath;
	}


}
