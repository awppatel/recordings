package sre5.cloud.ws.recordings;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import sre2018.cloud.apis.CFGDRecordingServer;
import sre2018.cloud.apis.WSRDAuthsrednisResp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)


//@JsonPropertyOrder({ "sre5dbase","dualrecord","recfilepath","rstcomm_url","logfilepath","gmtoffset","customer_tz","logname","callbridge","directdial","recording_servers"})

@JsonPropertyOrder({ "sre5dbase","generic","recording_servers"})



public class CFGDSre5Config
{
	@JsonProperty("sre5dbase")
	private CFGDSre5DbConfig svc_db_host=null;

	@JsonProperty("generic")
	private CFGDSre19Generic generic_data=null;
	
	@JsonProperty("recording_servers")
	private List<CFGDRecordingServer> recording_servers = new ArrayList<CFGDRecordingServer>();
	
	
	
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
	 * @return the recording_servers
	 */
	public List<CFGDRecordingServer> getRecording_servers()
	{
		return recording_servers;
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
	 * @return the generic_data
	 */
	public CFGDSre19Generic getGeneric_data()
	{
		return generic_data;
	}

	/**
	 * @param generic_data the generic_data to set
	 */
	public void setGeneric_data(CFGDSre19Generic generic_data)
	{
		this.generic_data = generic_data;
	}

	/**
	 * @param recording_servers the recording_servers to set
	 */
	public void setRecording_servers(List<CFGDRecordingServer> recording_servers)
	{
		this.recording_servers = recording_servers;
	}


}
