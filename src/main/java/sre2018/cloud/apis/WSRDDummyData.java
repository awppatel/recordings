/**
 * 
 */
package sre2018.cloud.apis;

/**
 * @author awp
 *
 */

/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
*/
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
/*
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
*/

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sre18_nextstep"
})

public class WSRDDummyData
{
    @JsonProperty("sre18_nextstep")
    private String sre18Nextstep;

	/**
	 * @return the sre18Nextstep
	 */
	public String getSre18Nextstep()
	{
		return sre18Nextstep;
	}

	/**
	 * @param sre18Nextstep the sre18Nextstep to set
	 */
	public void setSre18Nextstep(String sre18Nextstep)
	{
		this.sre18Nextstep = sre18Nextstep;
	}

    public String toJsonString() 
    {
    	StringBuilder json_resp = new StringBuilder();
    	json_resp.append("{\n\"sre18_nextstep\" : \"");
    	json_resp.append(sre18Nextstep);
    	json_resp.append("\"\n}");
    	return json_resp.toString();
    }

}
