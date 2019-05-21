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


@JsonPropertyOrder({ "db_host", "db_port","db_name", "db_uid","db_pwd","cdr_logic","max_connections"})

public class CFGDSre5DbConfig
{
	@JsonProperty("db_host")
	private String svc_db_host;

	@JsonProperty("db_port")
	private int svc_db_port;

	@JsonProperty("db_name")
	private String svc_db_name;

	@JsonProperty("db_uid")
	private String svc_db_uid;

	@JsonProperty("db_pwd")
	private String svc_db_pwd;

	@JsonProperty("cdr_logic")
	private String cdr_logic;
	
	@JsonProperty("max_connections")
	private int max_connections=0;

	private int cur_cdr_logic;
	
	// Hate this idiotic approach as opposed to the simple global Enum approach in C/C++
	// Define the constants for the CDR Logic handling
	private static final int CDRL_NORMAL = 0;
	private static final int CDRL_IN_DID = 1;

	/**
	 * @return the max_connections
	 */
	public int getMax_connections()
	{
		return max_connections;
	}

	/**
	 * @param max_connections the max_connections to set
	 */
	public void setMax_connections(int max_connections)
	{
		this.max_connections = max_connections;
	}

	public void setConfDBHost(String _svc_db_host) {
		this.svc_db_host = _svc_db_host;
	}

	public String getConfDBHost() {
		return this.svc_db_host;
	}

	public void setConfDBPortr(int _svc_db_port) {
		this.svc_db_port = _svc_db_port;
	}

	public int getConfDBPort() {
		return this.svc_db_port;
	}

	public void setConfDBName(String _svc_db_name) {
		this.svc_db_name = _svc_db_name;
	}

	public String getConfDBName() {
		return this.svc_db_name;
	}

	public void setConfDBUid(String _svc_db_uid) {
		this.svc_db_uid = _svc_db_uid;
	}

	public String getConfDBUid() {
		return this.svc_db_uid;
	}

	public void setConfDBPwd(String _svc_db_pwd) {
		this.svc_db_uid = _svc_db_pwd;
	}

	public String getConfDBPwd() {
		return this.svc_db_pwd;
	}


	/**
	 * @return the cur_cdr_logic
	 */
	public int getCur_cdr_logic()
	{
		return this.cur_cdr_logic;
	}

	/**
	 * @param None - Computes the CDR Logic Constant from the System Configuration file
	 */
	public void setCur_cdr_logic()
	{
		// Check to see if the field is defined in the JSON
		if((cdr_logic == null) || cdr_logic.equalsIgnoreCase("NORMAL"))
			this.cur_cdr_logic = CDRL_NORMAL;
		else
			if(cdr_logic.equalsIgnoreCase("USE_DID"))
				this.cur_cdr_logic = CDRL_IN_DID;
			else
				this.cur_cdr_logic = CDRL_NORMAL;
	}

	
}

