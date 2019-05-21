/**
 * 
 */
package sre2018.cloud.apis;

/**
 * @author awp
 *
 */

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "sre18_cname", "sre18_ani", "sre18_a_start", "sre18_a_sip_id", "sre18_dnis", "sre18_AccountSid",
		"sre18_ApiVersion", "sre18_CallStatus", "sre18_CallTimestamp", "sre18_DialCallDuration", "sre18_DialCallSid",
		"sre18_DialCallStatus", "sre18_DialRingDuration", "sre18_Digits", "sre18_Direction", "sre18_ForwardedFrom",
		"sre18_LocationID",  "sre18_user_choice",
		  "sre18_rec_file_public_url",
		  "sre18_rec_file_duration",
		  "sre18_rec_file_url"
 })

public class WSQDSre18CdrData
{
	@JsonProperty("sre18_user_choice")
	private String sre18_user_choice;

	@JsonProperty("sre18_rec_file_public_url")
	private String sre18_rec_file_public_url;

	@JsonProperty("sre18_rec_file_duration")
	private String sre18_rec_file_duration;

	@JsonProperty("sre18_rec_file_url")
	private String sre18_rec_file_url;
	@JsonProperty("sre18_cname")
	private String sre18Cname;
	@JsonProperty("sre18_ani")
	private String sre18Ani;
	@JsonProperty("sre18_a_start")
	private String sre18AStart;
	@JsonProperty("sre18_a_sip_id")
	private String sre18ASipId;
	@JsonProperty("sre18_dnis")
	private String sre18Dnis;
	@JsonProperty("sre18_AccountSid")
	private String sre18AccountSid;
	@JsonProperty("sre18_ApiVersion")
	private String sre18ApiVersion;
	@JsonProperty("sre18_CallStatus")
	private String sre18CallStatus;
	@JsonProperty("sre18_CallTimestamp")
	private String sre18CallTimestamp;
	@JsonProperty("sre18_DialCallDuration")
	private String sre18DialCallDuration;
	@JsonProperty("sre18_DialCallSid")
	private String sre18DialCallSid;
	@JsonProperty("sre18_DialCallStatus")
	private String sre18DialCallStatus;
	@JsonProperty("sre18_DialRingDuration")
	private String sre18DialRingDuration;
	@JsonProperty("sre18_Digits")
	private String sre18Digits;
	@JsonProperty("sre18_Direction")
	private String sre18Direction;
	@JsonProperty("sre18_ForwardedFrom")
	private String sre18ForwardedFrom;
	@JsonProperty("sre18_LocationID")
	private String sre18_LocationID;

	
	
	/**
	 * @return the sre18_LocationID
	 */
	public String getSre18_LocationID()
	{
		return sre18_LocationID;
	}

	/**
	 * @param sre18_LocationID the sre18_LocationID to set
	 */
	public void setSre18_LocationID(String sre18_LocationID)
	{
		this.sre18_LocationID = sre18_LocationID;
	}

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("sre18_cname")
	public String getSre18Cname()
	{
		return sre18Cname;
	}

	@JsonProperty("sre18_cname")
	public void setSre18Cname(String sre18Cname)
	{
		this.sre18Cname = sre18Cname;
	}

	public WSQDSre18CdrData withSre18Cname(String sre18Cname)
	{
		this.sre18Cname = sre18Cname;
		return this;
	}

	@JsonProperty("sre18_ani")
	public String getSre18Ani()
	{
		return sre18Ani;
	}

	@JsonProperty("sre18_ani")
	public void setSre18Ani(String sre18Ani)
	{
		this.sre18Ani = sre18Ani;
	}

	public WSQDSre18CdrData withSre18Ani(String sre18Ani)
	{
		this.sre18Ani = sre18Ani;
		return this;
	}

	@JsonProperty("sre18_a_start")
	public String getSre18AStart()
	{
		return sre18AStart;
	}

	@JsonProperty("sre18_a_start")
	public void setSre18AStart(String sre18AStart)
	{
		this.sre18AStart = sre18AStart;
	}

	public WSQDSre18CdrData withSre18AStart(String sre18AStart)
	{
		this.sre18AStart = sre18AStart;
		return this;
	}

	@JsonProperty("sre18_a_sip_id")
	public String getSre18ASipId()
	{
		return sre18ASipId;
	}

	@JsonProperty("sre18_a_sip_id")
	public void setSre18ASipId(String sre18ASipId)
	{
		this.sre18ASipId = sre18ASipId;
	}

	public WSQDSre18CdrData withSre18ASipId(String sre18ASipId)
	{
		this.sre18ASipId = sre18ASipId;
		return this;
	}

	@JsonProperty("sre18_dnis")
	public String getSre18Dnis()
	{
		return sre18Dnis;
	}

	@JsonProperty("sre18_dnis")
	public void setSre18Dnis(String sre18Dnis)
	{
		this.sre18Dnis = sre18Dnis;
	}

	public WSQDSre18CdrData withSre18Dnis(String sre18Dnis)
	{
		this.sre18Dnis = sre18Dnis;
		return this;
	}

	@JsonProperty("sre18_AccountSid")
	public String getSre18AccountSid()
	{
		return sre18AccountSid;
	}

	@JsonProperty("sre18_AccountSid")
	public void setSre18AccountSid(String sre18AccountSid)
	{
		this.sre18AccountSid = sre18AccountSid;
	}

	public WSQDSre18CdrData withSre18AccountSid(String sre18AccountSid)
	{
		this.sre18AccountSid = sre18AccountSid;
		return this;
	}

	@JsonProperty("sre18_ApiVersion")
	public String getSre18ApiVersion()
	{
		return sre18ApiVersion;
	}

	@JsonProperty("sre18_ApiVersion")
	public void setSre18ApiVersion(String sre18ApiVersion)
	{
		this.sre18ApiVersion = sre18ApiVersion;
	}

	public WSQDSre18CdrData withSre18ApiVersion(String sre18ApiVersion)
	{
		this.sre18ApiVersion = sre18ApiVersion;
		return this;
	}

	@JsonProperty("sre18_CallStatus")
	public String getSre18CallStatus()
	{
		return sre18CallStatus;
	}

	@JsonProperty("sre18_CallStatus")
	public void setSre18CallStatus(String sre18CallStatus)
	{
		this.sre18CallStatus = sre18CallStatus;
	}

	public WSQDSre18CdrData withSre18CallStatus(String sre18CallStatus)
	{
		this.sre18CallStatus = sre18CallStatus;
		return this;
	}

	@JsonProperty("sre18_CallTimestamp")
	public String getSre18CallTimestamp()
	{
		return sre18CallTimestamp;
	}

	@JsonProperty("sre18_CallTimestamp")
	public void setSre18CallTimestamp(String sre18CallTimestamp)
	{
		this.sre18CallTimestamp = sre18CallTimestamp;
	}

	public WSQDSre18CdrData withSre18CallTimestamp(String sre18CallTimestamp)
	{
		this.sre18CallTimestamp = sre18CallTimestamp;
		return this;
	}

	@JsonProperty("sre18_DialCallDuration")
	public String getSre18DialCallDuration()
	{
		return sre18DialCallDuration;
	}

	@JsonProperty("sre18_DialCallDuration")
	public void setSre18DialCallDuration(String sre18DialCallDuration)
	{
		this.sre18DialCallDuration = sre18DialCallDuration;
	}

	public WSQDSre18CdrData withSre18DialCallDuration(String sre18DialCallDuration)
	{
		this.sre18DialCallDuration = sre18DialCallDuration;
		return this;
	}

	@JsonProperty("sre18_DialCallSid")
	public String getSre18DialCallSid()
	{
		return sre18DialCallSid;
	}

	@JsonProperty("sre18_DialCallSid")
	public void setSre18DialCallSid(String sre18DialCallSid)
	{
		this.sre18DialCallSid = sre18DialCallSid;
	}

	public WSQDSre18CdrData withSre18DialCallSid(String sre18DialCallSid)
	{
		this.sre18DialCallSid = sre18DialCallSid;
		return this;
	}

	@JsonProperty("sre18_DialCallStatus")
	public String getSre18DialCallStatus()
	{
		return sre18DialCallStatus;
	}

	@JsonProperty("sre18_DialCallStatus")
	public void setSre18DialCallStatus(String sre18DialCallStatus)
	{
		this.sre18DialCallStatus = sre18DialCallStatus;
	}

	public WSQDSre18CdrData withSre18DialCallStatus(String sre18DialCallStatus)
	{
		this.sre18DialCallStatus = sre18DialCallStatus;
		return this;
	}

	@JsonProperty("sre18_DialRingDuration")
	public String getSre18DialRingDuration()
	{
		return sre18DialRingDuration;
	}

	@JsonProperty("sre18_DialRingDuration")
	public void setSre18DialRingDuration(String sre18DialRingDuration)
	{
		this.sre18DialRingDuration = sre18DialRingDuration;
	}

	public WSQDSre18CdrData withSre18DialRingDuration(String sre18DialRingDuration)
	{
		this.sre18DialRingDuration = sre18DialRingDuration;
		return this;
	}

	@JsonProperty("sre18_Digits")
	public String getSre18Digits()
	{
		return sre18Digits;
	}

	@JsonProperty("sre18_Digits")
	public void setSre18Digits(String sre18Digits)
	{
		this.sre18Digits = sre18Digits;
	}

	public WSQDSre18CdrData withSre18Digits(String sre18Digits)
	{
		this.sre18Digits = sre18Digits;
		return this;
	}

	@JsonProperty("sre18_Direction")
	public String getSre18Direction()
	{
		return sre18Direction;
	}

	@JsonProperty("sre18_Direction")
	public void setSre18Direction(String sre18Direction)
	{
		this.sre18Direction = sre18Direction;
	}

	public WSQDSre18CdrData withSre18Direction(String sre18Direction)
	{
		this.sre18Direction = sre18Direction;
		return this;
	}

	@JsonProperty("sre18_ForwardedFrom")
	public String getSre18ForwardedFrom()
	{
		return sre18ForwardedFrom;
	}

	@JsonProperty("sre18_ForwardedFrom")
	public void setSre18ForwardedFrom(String sre18ForwardedFrom)
	{
		this.sre18ForwardedFrom = sre18ForwardedFrom;
	}

	public WSQDSre18CdrData withSre18ForwardedFrom(String sre18ForwardedFrom)
	{
		this.sre18ForwardedFrom = sre18ForwardedFrom;
		return this;
	}

	/**
	 * @return the sre18_user_choice
	 */
	public String getSre18_user_choice()
	{
		return sre18_user_choice;
	}

	/**
	 * @param sre18_user_choice the sre18_user_choice to set
	 */
	public void setSre18_user_choice(String sre18_user_choice)
	{
		this.sre18_user_choice = sre18_user_choice;
	}

	/**
	 * @return the sre18_rec_file_public_url
	 */
	public String getSre18_rec_file_public_url()
	{
		return sre18_rec_file_public_url;
	}

	/**
	 * @param sre18_rec_file_public_url the sre18_rec_file_public_url to set
	 */
	public void setSre18_rec_file_public_url(String sre18_rec_file_public_url)
	{
		this.sre18_rec_file_public_url = sre18_rec_file_public_url;
	}

	/**
	 * @return the sre18_rec_file_duration
	 */
	public String getSre18_rec_file_duration()
	{
		return sre18_rec_file_duration;
	}

	/**
	 * @param sre18_rec_file_duration the sre18_rec_file_duration to set
	 */
	public void setSre18_rec_file_duration(String sre18_rec_file_duration)
	{
		this.sre18_rec_file_duration = sre18_rec_file_duration;
	}

	/**
	 * @return the sre18_rec_file_url
	 */
	public String getSre18_rec_file_url()
	{
		return sre18_rec_file_url;
	}

	/**
	 * @param sre18_rec_file_url the sre18_rec_file_url to set
	 */
	public void setSre18_rec_file_url(String sre18_rec_file_url)
	{
		this.sre18_rec_file_url = sre18_rec_file_url;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value)
	{
		this.additionalProperties.put(name, value);
	}

	public WSQDSre18CdrData withAdditionalProperty(String name, Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("sre18Cname", sre18Cname).append("sre18Ani", sre18Ani)
				.append("sre18AStart", sre18AStart).append("sre18ASipId", sre18ASipId).append("sre18Dnis", sre18Dnis)
				.append("sre18AccountSid", sre18AccountSid).append("sre18ApiVersion", sre18ApiVersion)
				.append("sre18CallStatus", sre18CallStatus).append("sre18CallTimestamp", sre18CallTimestamp)
				.append("sre18DialCallDuration", sre18DialCallDuration).append("sre18DialCallSid", sre18DialCallSid)
				.append("sre18DialCallStatus", sre18DialCallStatus)
				.append("sre18DialRingDuration", sre18DialRingDuration).append("sre18Digits", sre18Digits)
				.append("sre18Direction", sre18Direction).append("sre18ForwardedFrom", sre18ForwardedFrom).append("sre18_LocationID", sre18_LocationID)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(sre18CallTimestamp).append(sre18ApiVersion).append(sre18Direction)
				.append(sre18DialRingDuration).append(sre18Dnis).append(sre18DialCallDuration).append(sre18Cname)
				.append(sre18AccountSid).append(sre18ForwardedFrom).append(sre18ASipId).append(sre18DialCallStatus)
				.append(sre18AStart).append(sre18DialCallSid).append(sre18CallStatus).append(sre18Digits)
				.append(additionalProperties).append(sre18Ani).toHashCode();
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}
		if ((other instanceof WSQDSre18CdrData) == false)
		{
			return false;
		}
		WSQDSre18CdrData rhs = ((WSQDSre18CdrData) other);
		return new EqualsBuilder().append(sre18CallTimestamp, rhs.sre18CallTimestamp)
				.append(sre18ApiVersion, rhs.sre18ApiVersion).append(sre18Direction, rhs.sre18Direction)
				.append(sre18DialRingDuration, rhs.sre18DialRingDuration).append(sre18Dnis, rhs.sre18Dnis)
				.append(sre18DialCallDuration, rhs.sre18DialCallDuration).append(sre18Cname, rhs.sre18Cname)
				.append(sre18AccountSid, rhs.sre18AccountSid).append(sre18ForwardedFrom, rhs.sre18ForwardedFrom)
				.append(sre18ASipId, rhs.sre18ASipId).append(sre18DialCallStatus, rhs.sre18DialCallStatus)
				.append(sre18AStart, rhs.sre18AStart).append(sre18DialCallSid, rhs.sre18DialCallSid)
				.append(sre18CallStatus, rhs.sre18CallStatus).append(sre18Digits, rhs.sre18Digits)
				.append(additionalProperties, rhs.additionalProperties).append(sre18Ani, rhs.sre18Ani).append(sre18_LocationID,rhs.sre18_LocationID).isEquals();
	}

}
