
package sre2018.cloud.apis;

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
@JsonPropertyOrder({
    "sre18_cname",
    "sre18_a_sip_id",
    "sre18_ani",
    "sre18_dnis",
    "sre18_a_start"
})
public class WSQDAuthsrednisReqt {

    @JsonProperty("sre18_cname")
    private String sre18Cname;
    @JsonProperty("sre18_a_sip_id")
    private String sre18ASipId;
    @JsonProperty("sre18_ani")
    private String sre18Ani;
    @JsonProperty("sre18_dnis")
    private String sre18Dnis;
    @JsonProperty("sre18_a_start")
    private String sre18AStart;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sre18_cname")
    public String getSre18Cname() {
        return sre18Cname;
    }

    @JsonProperty("sre18_cname")
    public void setSre18Cname(String sre18Cname) {
        this.sre18Cname = sre18Cname;
    }

    public WSQDAuthsrednisReqt withSre18Cname(String sre18Cname) {
        this.sre18Cname = sre18Cname;
        return this;
    }

    @JsonProperty("sre18_a_sip_id")
    public String getSre18ASipId() {
        return sre18ASipId;
    }

    @JsonProperty("sre18_a_sip_id")
    public void setSre18ASipId(String sre18ASipId) {
        this.sre18ASipId = sre18ASipId;
    }

    public WSQDAuthsrednisReqt withSre18ASipId(String sre18ASipId) {
        this.sre18ASipId = sre18ASipId;
        return this;
    }

    @JsonProperty("sre18_ani")
    public String getSre18Ani() {
        return sre18Ani;
    }

    @JsonProperty("sre18_ani")
    public void setSre18Ani(String sre18Ani) {
        this.sre18Ani = sre18Ani;
    }

    public WSQDAuthsrednisReqt withSre18Ani(String sre18Ani) {
        this.sre18Ani = sre18Ani;
        return this;
    }

    @JsonProperty("sre18_dnis")
    public String getSre18Dnis() {
        return sre18Dnis;
    }

    @JsonProperty("sre18_dnis")
    public void setSre18Dnis(String sre18Dnis) {
        this.sre18Dnis = sre18Dnis;
    }

    public WSQDAuthsrednisReqt withSre18Dnis(String sre18Dnis) {
        this.sre18Dnis = sre18Dnis;
        return this;
    }

    @JsonProperty("sre18_a_start")
    public String getSre18AStart() {
        return sre18AStart;
    }

    @JsonProperty("sre18_a_start")
    public void setSre18AStart(String sre18AStart) {
        this.sre18AStart = sre18AStart;
    }

    public WSQDAuthsrednisReqt withSre18AStart(String sre18AStart) {
        this.sre18AStart = sre18AStart;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public WSQDAuthsrednisReqt withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sre18Cname", sre18Cname).append("sre18ASipId", sre18ASipId).append("sre18Ani", sre18Ani).append("sre18Dnis", sre18Dnis).append("sre18AStart", sre18AStart).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(sre18Cname).append(sre18ASipId).append(sre18AStart).append(additionalProperties).append(sre18Ani).append(sre18Dnis).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof WSQDAuthsrednisReqt) == false) {
            return false;
        }
        WSQDAuthsrednisReqt rhs = ((WSQDAuthsrednisReqt) other);
        return new EqualsBuilder().append(sre18Cname, rhs.sre18Cname).append(sre18ASipId, rhs.sre18ASipId).append(sre18AStart, rhs.sre18AStart).append(additionalProperties, rhs.additionalProperties).append(sre18Ani, rhs.sre18Ani).append(sre18Dnis, rhs.sre18Dnis).isEquals();
    }

}
