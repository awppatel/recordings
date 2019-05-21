
package sre2018.cloud.apis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({
    "sre18_nextstep",
    "sre18_play_rec_msg",
    "sre18_dialnumber",
    "sre18_errcode",
    "sre18_gw_ip",
    "sre18_gw_port",
    "sre18_dialnumbers",
    "sre18_curtimestamp",
    "sre18_LocationID"
})
public class WSRDAuthsrednisResp {

    @JsonProperty("sre18_nextstep")
    private String sre18Nextstep;
    @JsonProperty("sre18_play_rec_msg")
    private String sre18PlayRecMsg;
    @JsonProperty("sre18_dialnumber")
    private String sre18Dialnumber;
    @JsonProperty("sre18_errcode")
    private Integer sre18Errcode;
    @JsonProperty("sre18_dialnumbers")
    private List<String> sre18Dialnumbers = new ArrayList<String>();

    @JsonProperty("sre18_gw_ip")
    private String sre18_gw_ip;
    @JsonProperty("sre18_gw_port")
    private Integer sre18_gw_port;
	@JsonProperty("sre18_LocationID")
	private String sre18_LocationID;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	
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

    
    
    @JsonProperty("sre18_nextstep")
    public String getSre18Nextstep() {
        return sre18Nextstep;
    }

    @JsonProperty("sre18_nextstep")
    public void setSre18Nextstep(String sre18Nextstep) {
        this.sre18Nextstep = sre18Nextstep;
    }

    public WSRDAuthsrednisResp withSre18Nextstep(String sre18Nextstep) {
        this.sre18Nextstep = sre18Nextstep;
        return this;
    }

    @JsonProperty("sre18_play_rec_msg")
    public String getSre18PlayRecMsg() {
        return sre18PlayRecMsg;
    }

    @JsonProperty("sre18_play_rec_msg")
    public void setSre18PlayRecMsg(String sre18PlayRecMsg) {
        this.sre18PlayRecMsg = sre18PlayRecMsg;
    }

    public WSRDAuthsrednisResp withSre18PlayRecMsg(String sre18PlayRecMsg) {
        this.sre18PlayRecMsg = sre18PlayRecMsg;
        return this;
    }

    @JsonProperty("sre18_dialnumber")
    public String getSre18Dialnumber() {
        return sre18Dialnumber;
    }

    @JsonProperty("sre18_dialnumber")
    public void setSre18Dialnumber(String sre18Dialnumber) {
        this.sre18Dialnumber = sre18Dialnumber;
    }

    public WSRDAuthsrednisResp withSre18Dialnumber(String sre18Dialnumber) {
        this.sre18Dialnumber = sre18Dialnumber;
        return this;
    }

    @JsonProperty("sre18_errcode")
    public Integer getSre18Errcode() {
        return sre18Errcode;
    }

    @JsonProperty("sre18_errcode")
    public void setSre18Errcode(Integer sre18Errcode) {
        this.sre18Errcode = sre18Errcode;
    }

    public WSRDAuthsrednisResp withSre18Errcode(Integer sre18Errcode) {
        this.sre18Errcode = sre18Errcode;
        return this;
    }

    @JsonProperty("sre18_dialnumbers")
    public List<String> getSre18Dialnumbers() {
        return sre18Dialnumbers;
    }

    @JsonProperty("sre18_dialnumbers")
    public void setSre18Dialnumbers(List<String> sre18Dialnumbers) {
        this.sre18Dialnumbers = sre18Dialnumbers;
    }

    public WSRDAuthsrednisResp withSre18Dialnumbers(List<String> sre18Dialnumbers) {
        this.sre18Dialnumbers = sre18Dialnumbers;
        return this;
    }

    /**
	 * @return the sre18_gw_ip
	 */
	public String getSre18_gw_ip()
	{
		return sre18_gw_ip;
	}

	/**
	 * @param sre18_gw_ip the sre18_gw_ip to set
	 */
	public void setSre18_gw_ip(String sre18_gw_ip)
	{
		this.sre18_gw_ip = sre18_gw_ip;
	}

	/**
	 * @return the sre18_gw_port
	 */
	public Integer getSre18_gw_port()
	{
		return sre18_gw_port;
	}

	/**
	 * @param sre18_gw_port the sre18_gw_port to set
	 */
	public void setSre18_gw_port(Integer sre18_gw_port)
	{
		this.sre18_gw_port = sre18_gw_port;
	}

	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public WSRDAuthsrednisResp withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
    
    public String toJsonString() 
    {
    	StringBuilder json_resp = new StringBuilder();
    	json_resp.append("{\n\"sre18_nextstep\" : \"");
    	json_resp.append(sre18Nextstep);
    	json_resp.append("\",\n\"sre18_play_rec_msg\" : \"");
    	json_resp.append(sre18PlayRecMsg);
    	json_resp.append("\",\n\"sre18_gw_ip\" : \"");
    	json_resp.append(sre18_gw_ip);
    	json_resp.append("\",\n\"sre18_gw_port\" : \"");
    	json_resp.append(Integer.toString(sre18_gw_port));
    	json_resp.append("\",\n\"sre18_errcode\" : \"");
    	json_resp.append(Integer.toString(sre18Errcode));
    	json_resp.append("\",\n\"sre18_dialnumbers\" : [");
    	int idx = 0;
    	for(String nbr : sre18Dialnumbers)
    	{
    		if(idx > 0)
    		{
    			json_resp.append(",");
    		}
    		else
    		{
    			idx = 1;
    		}
    		json_resp.append("\"");
    		json_resp.append(nbr);
    		json_resp.append("\"");
    	}
    	json_resp.append("]\n}");
    	return json_resp.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sre18Nextstep", sre18Nextstep).append("sre18PlayRecMsg", sre18PlayRecMsg).append("sre18Dialnumber", sre18Dialnumber).append("sre18Errcode", sre18Errcode).append("sre18Dialnumbers", sre18Dialnumbers).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(sre18Errcode).append(sre18Nextstep).append(sre18Dialnumbers).append(sre18Dialnumber).append(additionalProperties).append(sre18PlayRecMsg).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof WSRDAuthsrednisResp) == false) {
            return false;
        }
        WSRDAuthsrednisResp rhs = ((WSRDAuthsrednisResp) other);
        return new EqualsBuilder().append(sre18Errcode, rhs.sre18Errcode).append(sre18Nextstep, rhs.sre18Nextstep).append(sre18Dialnumbers, rhs.sre18Dialnumbers).append(sre18Dialnumber, rhs.sre18Dialnumber).append(additionalProperties, rhs.additionalProperties).append(sre18PlayRecMsg, rhs.sre18PlayRecMsg).isEquals();
    }

}
