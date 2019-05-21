/**
 * 
 */
package sre2018.cloud.apis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author awp
 *
 */

@JsonPropertyOrder({ "server_ip", "recfilepath","sre18_recfileurl","rstcom_acc_sid","rstcom_auth_token"})


public class CFGDRecordingServer
{
	@JsonProperty("server_ip")
	private String server_ip;

	@JsonProperty("recfilepath")
	private String recfilepath;

	@JsonProperty("sre18_recfileurl")
	private String sre18_recfileurl;

	@JsonProperty("rstcom_acc_sid")
	private String rstcom_acc_sid;
	
	@JsonProperty("rstcom_auth_token")
	private String rstcom_auth_token;

	/**
	 * @return the server_ip
	 */
	public String getServer_ip()
	{
		return server_ip;
	}

	/**
	 * @param server_ip the server_ip to set
	 */
	public void setServer_ip(String server_ip)
	{
		this.server_ip = server_ip;
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

	/**
	 * @return the sre18_recfileurl
	 */
	public String getSre18_recfileurl()
	{
		return sre18_recfileurl;
	}

	/**
	 * @param sre18_recfileurl the sre18_recfileurl to set
	 */
	public void setSre18_recfileurl(String sre18_recfileurl)
	{
		this.sre18_recfileurl = sre18_recfileurl;
	}

	/**
	 * @return the rstcom_acc_sid
	 */
	public String getRstcom_acc_sid()
	{
		return rstcom_acc_sid;
	}

	/**
	 * @param rstcom_acc_sid the rstcom_acc_sid to set
	 */
	public void setRstcom_acc_sid(String rstcom_acc_sid)
	{
		this.rstcom_acc_sid = rstcom_acc_sid;
	}

	/**
	 * @return the rstcom_auth_token
	 */
	public String getRstcom_auth_token()
	{
		return rstcom_auth_token;
	}

	/**
	 * @param rstcom_auth_token the rstcom_auth_token to set
	 */
	public void setRstcom_auth_token(String rstcom_auth_token)
	{
		this.rstcom_auth_token = rstcom_auth_token;
	}

	

}
