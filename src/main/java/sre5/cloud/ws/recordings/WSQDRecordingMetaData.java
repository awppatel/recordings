/**
 * 
 */
package sre5.cloud.ws.recordings;

/**
 * @author awp
 *
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "STOC", "ETOC", "FILE","DIR","CLI","DN","STN","RECID","HASH" })

public class WSQDRecordingMetaData
{
	@JsonProperty("STOC")
	private String stoc;
	
	@JsonProperty("ETOC")
	private String etoc;

	@JsonProperty("FILE")
	private String recFname;
	
	@JsonProperty("DIR")
	private String callDir;
	
	@JsonProperty("CLI")
	private String callerId;
	
	@JsonProperty("DN")
	private String dnis;

	@JsonProperty("STN")
	private String extensionNum;
	
	@JsonProperty("RECID")
	private String recID;

	@JsonProperty("HASH")
	private String recHash;

	/**
	 * @return the stoc
	 */
	public String getStoc()
	{
		return stoc;
	}

	/**
	 * @param stoc the stoc to set
	 */
	public void setStoc(String stoc)
	{
		this.stoc = stoc;
	}

	/**
	 * @return the etoc
	 */
	public String getEtoc()
	{
		return etoc;
	}

	/**
	 * @param etoc the etoc to set
	 */
	public void setEtoc(String etoc)
	{
		this.etoc = etoc;
	}

	/**
	 * @return the recFname
	 */
	public String getRecFname()
	{
		return recFname;
	}

	/**
	 * @param recFname the recFname to set
	 */
	public void setRecFname(String recFname)
	{
		this.recFname = recFname;
	}

	/**
	 * @return the callDir
	 */
	public String getCallDir()
	{
		return callDir;
	}

	/**
	 * @param callDir the callDir to set
	 */
	public void setCallDir(String callDir)
	{
		this.callDir = callDir;
	}

	/**
	 * @return the callerId
	 */
	public String getCallerId()
	{
		return callerId;
	}

	/**
	 * @param callerId the callerId to set
	 */
	public void setCallerId(String callerId)
	{
		this.callerId = callerId;
	}

	/**
	 * @return the dnis
	 */
	public String getDnis()
	{
		return dnis;
	}

	/**
	 * @param dnis the dnis to set
	 */
	public void setDnis(String dnis)
	{
		this.dnis = dnis;
	}

	/**
	 * @return the extensionNum
	 */
	public String getExtensionNum()
	{
		return extensionNum;
	}

	/**
	 * @param extensionNum the extensionNum to set
	 */
	public void setExtensionNum(String extensionNum)
	{
		this.extensionNum = extensionNum;
	}

	/**
	 * @return the recID
	 */
	public String getRecID()
	{
		return recID;
	}

	/**
	 * @param recID the recID to set
	 */
	public void setRecID(String recID)
	{
		this.recID = recID;
	}

	/**
	 * @return the recHash
	 */
	public String getRecHash()
	{
		return recHash;
	}

	/**
	 * @param recHash the recHash to set
	 */
	public void setRecHash(String recHash)
	{
		this.recHash = recHash;
	}
	
	public String FormatDBDateTime(String cloud_date)
	{
		if(cloud_date.length() != 15)
			return("");
		StringBuilder ret_string = new StringBuilder();
		ret_string.append(cloud_date.substring(0, 4)).append("-").append(cloud_date.substring(4, 6))
			.append("-").append(cloud_date.substring(6, 8)).append(" ")
			.append(cloud_date.substring(9, 11)).append(":").append(cloud_date.substring(11, 13))
			.append(":").append(cloud_date.substring(13));
		return(ret_string.toString());
	}
	
	/*
	 * @return Filename prepended with the Date to match the portal requirements
	 */
	public String FormatDBFileName()
	{
		StringBuilder ret_str = new StringBuilder();
		ret_str.append(this.stoc).append("_").append(recFname);
		return(ret_str.toString());
	}

}
