/**
 * 
 */
package sre5.cloud.ws.recordings;

/**
 * @author awp
 *
 */
import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import com.fasterxml.jackson.databind.ObjectMapper;

import sre2018.cloud.apis.WSQDSre18CdrData;
// import sre2018.cloud.apis.WSRDAuthsrednisResp;


public class CLSre5Database
{
	private static final Logger log = LoggerFactory.getLogger(CLSre5Database.class);
	private static final CFGDSre5Config rec_config = CLSRE5SvcConfig.sre19_config;
	
	Connection sre5_db_conn = null;
	
	/*
	public void CloseDbConnOld()
	{
		try
		{
			if((sre5_db_conn != null) && sre5_db_conn.isValid(1))
				sre5_db_conn.close();
			sre5_db_conn = null;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("CloseDbConn:Exception:" + e.toString() +"\n");
		}
	}
	*/

	/*
	 * New function that uses connection pools to Return Connection back to pool instead of Closing the connection
	 */
	public void CloseDbConn()
	{
		CLSRE5SvcConfig.rec_db_conns.ReleaseConnection(this.sre5_db_conn);
	}

	/*
	private int ConnectToDBOld()
	{
		log.info("Connecting to "+ rec_config.getSvc_db_host().getConfDBName() +" : "+rec_config.getSvc_db_host().getConfDBUid() +"\n");
		try
		{
			if(sre5_db_conn != null && sre5_db_conn.isValid(1))
				return(0);
			Class.forName("com.mysql.jdbc.Driver");
			String database_name = "jdbc:mysql://"+ rec_config.getSvc_db_host().getConfDBHost() + ":" + 
					rec_config.getSvc_db_host().getConfDBPort() + "/" + rec_config.getSvc_db_host().getConfDBName();
			sre5_db_conn = DriverManager.getConnection(database_name,rec_config.getSvc_db_host().getConfDBUid(),rec_config.getSvc_db_host().getConfDBPwd());
			if(!sre5_db_conn.isValid(1))
			{
				// We have a problem, need to print to log file here
				return(1);
			}
		}
		catch (SQLException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("ConnectToDB:Exception:" + e.toString()+"\n");
			return(2);
		}	// Connection is up
		
		return(0);		// All was well
	}
	*/
	
	/*
	 * int ConnectToDB()
	 * 
	 * Function that returns a Connection to the Database from a pool of connections.
	 */
	private int ConnectToDB()
	{
		this.sre5_db_conn = CLSRE5SvcConfig.rec_db_conns.AcquireConn();
		return(CLSRE5SvcConfig.rec_db_conns.getError_code());
	}
	
	/*
	 * STOC: 20151221-154741 ETOC: FILE: /tmp/rec-6477787108-20151221-154741-1450712861.1278 DIR: O CLI: 8772718275 DN: 6477787108 STN: 227 RECID:  JSON: 
	 * {"STOC":"20151221-154741", "ETOC":"", "FILE":"rec-6477787108-20151221-154741-1450712861.1278.mp3", "DIR":"O", "CLI":"8772718275", "DN":"6477787108", "STN":"227", "RECID":"1450712861.1279", "HASH":""}
	 * {"STOC":"20151221-154741", "ETOC":"20151221-154754", "FILE":"rec-6477787108-20151221-154741-1450712861.1278.mp3", "DIR":"O", "CLI":"8772718275", "DN":"6477787108", "STN":"227", "RECID":"1450712861.1278", "HASH":"7dec2d13429a0fc1823d19ac353bc524f381c334"}
	 * 
	 */

	public int UpdateCDRRecord(WSQDRecordingMetaData cdr_data)
	{
		if(ConnectToDB() != 0)
		{
			log.error("UpdateCDRRecord:Unable to connect to Database\n");
			return(-1);
		}
		PreparedStatement update_cdr_stmt;
		int ret_val = -2;
		try
		{
			update_cdr_stmt = sre5_db_conn
					.prepareStatement("call update_cloud_cdr(?,?,?,?,?)");
			update_cdr_stmt.setString(1, cdr_data.FormatDBDateTime(cdr_data.getEtoc()));
			update_cdr_stmt.setString(2, cdr_data.FormatDBFileName());
			update_cdr_stmt.setString(3, cdr_data.getRecHash());
			update_cdr_stmt.setString(4, cdr_data.getRecID());
			update_cdr_stmt.setInt(5, rec_config.getGeneric_data().getGmt_offset());
			
			log.debug("UpdateCDR:Query:" + update_cdr_stmt.toString()+"\n");

			ResultSet update_cdr_result = (ResultSet) update_cdr_stmt.executeQuery();
			
			if(update_cdr_result != null)
			{
				update_cdr_result.beforeFirst();
				if (update_cdr_result.next())
				{
					ret_val = update_cdr_result.getInt("RETCODE");
				}
				else
					ret_val = -2;
				update_cdr_result.close();
			}
			else
			{
				ret_val = -3;	// We have an error
			}
			CloseDbConn(); 		// Explicitly close the datbase connection
			log.debug("InsertCDR:Query:Returns=" + Integer.toString(ret_val) + "\n");
			return(ret_val);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("UpdateCDR:Query:Exception:"+e.toString()+"\n");
			CloseDbConn(); 		// Explicitly close the datbase connection
			return(-4);	// SQLException Error
		}
	}
	
	public String GetRecFileDir(String rec_id,String rec_gmt_date)
	{
		StringBuilder ret_fstr = new StringBuilder();
		ret_fstr.append(rec_gmt_date.substring(0, 4))
		.append("/")
		.append(rec_gmt_date.substring(4, 6));

		// by default just return the GMT string so that we save the file
		String ret_str = ret_fstr.toString();

		if(ConnectToDB() != 0)
		{
			log.error("GetRecFileDir:Unable to connect to Database\n");
			// Need to return something. Return the defualt GMT string
			return ret_str;
		}
		PreparedStatement set_file_err_stmt;
		String ret_loc_dir = "";
		try
		{
			set_file_err_stmt = sre5_db_conn
					.prepareStatement("select left(CallStart,7) as 'RETCODE' from Traffic where CustField_1_Val=?");
			set_file_err_stmt.setString(1, rec_id);


			log.debug("GetRecFileDir:Query:" + set_file_err_stmt.toString()+"\n");

			ResultSet set_file_err_result = (ResultSet) set_file_err_stmt.executeQuery();
			
			if(set_file_err_result != null)
			{
				set_file_err_result.beforeFirst();
				if (set_file_err_result.next())	// If we have a response then get teh string and use it
				{
					ret_loc_dir = set_file_err_result.getString("RETCODE");
					ret_str = ret_loc_dir.substring(0, 4) + "/" + ret_loc_dir.substring(5, 7);
				}
				set_file_err_result.close();
			}
			CloseDbConn(); 		// Explicitly close the datbase connection
			log.debug("GetRecFileDir:Query Rec_id=" + rec_id + ", GMT_DATE=" + rec_gmt_date + ", DB Date is=" + ret_str+ "\n");
			return(ret_str);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("GetRecFileDir:Query:Exception:"+e.toString()+"\n");
			CloseDbConn(); 		// Explicitly close the datbase connection
			return(ret_str);
		}

	}
	
	public void SetFileError(String record_id)
	{
		if(ConnectToDB() != 0)
		{
			log.error("SetFileError:Unable to connect to Database\n");
			return;
		}
		/*
		 * TODO.
		 * Add code to screen the input data for SQL injection, invalid data etc??
		 */
		PreparedStatement set_file_err_stmt;
		try
		{
			set_file_err_stmt = sre5_db_conn
					.prepareStatement("update Traffic set Note='FILE UPLOAD ERROR' where CustField_1_Val=?");
			set_file_err_stmt.setString(1, record_id);


			log.debug("SetFileError:Query:" + set_file_err_stmt.toString()+"\n");

			int num_rows_updated = set_file_err_stmt.executeUpdate();
			
			CloseDbConn(); 		// Explicitly close the datbase connection
			log.debug("SetFileError:Query:Returns=" + Integer.toString(num_rows_updated) + "\n");
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("SetFileError:Query:Exception:"+e.toString()+"\n");
			CloseDbConn(); 		// Explicitly close the datbase connection
		}
	}
		
	public void SetFileDuration(String record_id, long dur_in_secs)
	{
		if(ConnectToDB() != 0)
		{
			log.error("SetFileDuration:Unable to connect to Database\n");
			return;
		}
		/*
		 * TODO.
		 * Add code to screen the input data for SQL injection, invalid data etc??
		 */
		PreparedStatement set_file_err_stmt;
		// int ret_val = -2;
		try
		{
			set_file_err_stmt = sre5_db_conn
					.prepareStatement("update Traffic set CallEnd=DATE_ADD(CallStart,INTERVAL ? SECOND), CallDuration= ? where CustField_1_Val=?");
			set_file_err_stmt.setLong(1,dur_in_secs);
			set_file_err_stmt.setLong(2,dur_in_secs);
			set_file_err_stmt.setString(3, record_id);


			log.debug("SetFileDuration:Query:" + set_file_err_stmt.toString()+"\n");

			int num_rows_updated = set_file_err_stmt.executeUpdate();
			set_file_err_stmt.close();
			CloseDbConn(); 		// Explicitly close the datbase connection
			log.debug("SetFileDuration:Query:Returns=" + Integer.toString(num_rows_updated) + "\n");
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("SetFileDuration:Query:Exception:"+e.toString()+"\n");
			CloseDbConn(); 		// Explicitly close the datbase connection
		}
	}
	
	public String GetDnisJson(String dnis)
	{
		
		if(ConnectToDB() != 0)
		{
			log.error("GetDnisJson:Unable to connect to Database\n");
			return null;
		}

		PreparedStatement dnis_json_stmt;
		String dnis_json_data = null;
		try
		{
			dnis_json_stmt = sre5_db_conn
					.prepareStatement("select dnis_json from Dnis_Json where Dnis=? and Status='Active'");
			dnis_json_stmt.setString(1, dnis);


			log.debug("GetDnisJson:Query:" + dnis_json_stmt.toString()+"\n");

			ResultSet dnis_json_result = (ResultSet) dnis_json_stmt.executeQuery();
			
			if(dnis_json_result != null)
			{
				dnis_json_result.beforeFirst();
				if (dnis_json_result.next())
				{
					dnis_json_data = dnis_json_result.getString("dnis_json");
				}
				else
				dnis_json_result.close();
			}
			dnis_json_stmt.close();
			CloseDbConn(); 		// Explicitly close the datbase connection
			log.debug("GetDnisJson:Query:Returns=" + dnis_json_data + "\n");
			return(dnis_json_data);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("GetDnisJson:Query:Exception:"+e.toString()+"\n");
			CloseDbConn(); 		// Explicitly close the datbase connection
			return(null);	// SQLException Error
		}
		
		
	}
	
	public int Write2018Cdr(WSQDSre18CdrData cdr_data,String rec_filename,String rstcm_fname)
	{
		if(ConnectToDB() != 0)
		{
			log.error("Write2018Cdr:Unable to connect to Database\n");
			return (-1);
		}
		
		PreparedStatement insert_cdr_stmt;
		int ret_val = -2;
		try
		{
			insert_cdr_stmt = sre5_db_conn
					.prepareStatement("call load_sre2018_01_cdr(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			insert_cdr_stmt.setString(1, cdr_data.getSre18Cname());
			insert_cdr_stmt.setString(2, cdr_data.getSre18Ani());
			insert_cdr_stmt.setString(3, cdr_data.getSre18AStart());
			insert_cdr_stmt.setString(4, cdr_data.getSre18ASipId());
			insert_cdr_stmt.setString(5, cdr_data.getSre18Dnis());
			insert_cdr_stmt.setString(6, cdr_data.getSre18AccountSid());
			insert_cdr_stmt.setString(7, cdr_data.getSre18ApiVersion());
			insert_cdr_stmt.setString(8, cdr_data.getSre18CallStatus());
			insert_cdr_stmt.setString(9, cdr_data.getSre18CallTimestamp());
			
			insert_cdr_stmt.setString(10, cdr_data.getSre18DialCallDuration());
			insert_cdr_stmt.setString(11, cdr_data.getSre18DialCallSid());
			insert_cdr_stmt.setString(12, cdr_data.getSre18DialCallStatus());
			insert_cdr_stmt.setString(13, cdr_data.getSre18DialRingDuration());
			insert_cdr_stmt.setString(14, cdr_data.getSre18Digits());
			insert_cdr_stmt.setString(15, cdr_data.getSre18Direction());
			insert_cdr_stmt.setString(16, cdr_data.getSre18ForwardedFrom());
			insert_cdr_stmt.setString(17, cdr_data.getSre18_LocationID());
			insert_cdr_stmt.setString(18, rec_filename);
			insert_cdr_stmt.setString(19, cdr_data.getSre18_user_choice());
			insert_cdr_stmt.setString(20, rstcm_fname);

			/*
			 * call load_sre2018_cdr('null','9054958272','','CA9cf52b28d1564961823d18f7fa866abd','12896088802',
			 * 'AC2b7ecad2fc92b05ec40a6d6acefbeebc','2012-04-24','completed','2018-04-16T02:36:38.134Z','15',
			 * 'CAf334f9315c024b7fbf02a606fa9397bb','completed','2','','inbound','12896088802','Vandusen')
			 */

			log.debug("Write2018Cdr:Query:" + insert_cdr_stmt.toString()+"\n");

			ResultSet insert_cdr_result = (ResultSet) insert_cdr_stmt.executeQuery();
			
			if(insert_cdr_result != null)
			{
				insert_cdr_result.beforeFirst();
				if (insert_cdr_result.next())
				{
					ret_val = insert_cdr_result.getInt("RETCODE");
				}
				else
					ret_val = -2;
				insert_cdr_result.close();
			}
			else
			{
				ret_val = -3;	// We have an error
			}
			insert_cdr_stmt.close();
			CloseDbConn(); 		// Explicitly close the datbase connection
			log.debug("Write2018Cdr:Query:Returns=" + Integer.toString(ret_val) + "\n");
			return(ret_val);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Write2018Cdr:Query:Exception:"+e.toString()+"\n");
			CloseDbConn(); 		// Explicitly close the datbase connection
			return(-4);	// SQLException Error
		}

	}
	
	public int InsertCDR(WSQDRecordingMetaData cdr_data,int cdr_logic)
	{
		if(ConnectToDB() != 0)
		{
			log.error("InsertCDR:Unable to connect to Database\n");
			return (-1);
		}
		/*
		 * TODO.
		 * Add code to screen the input data for SQL injection, invalid data etc??
		 */
		PreparedStatement insert_cdr_stmt;
		int ret_val = -2;
		try
		{
			insert_cdr_stmt = sre5_db_conn
					.prepareStatement("call load_cloud_cdr(?,?,?,?,?,?,?,?,?,?,?)");
			insert_cdr_stmt.setString(1, cdr_data.FormatDBDateTime(cdr_data.getStoc()));
			insert_cdr_stmt.setString(2, cdr_data.FormatDBDateTime(cdr_data.getEtoc()));
			insert_cdr_stmt.setString(3, cdr_data.FormatDBFileName());
			insert_cdr_stmt.setString(4, cdr_data.getCallDir());
			insert_cdr_stmt.setString(5, cdr_data.getCallerId());
			insert_cdr_stmt.setString(6, cdr_data.getCallerId());
			insert_cdr_stmt.setString(7, cdr_data.getDnis());
			insert_cdr_stmt.setString(8, cdr_data.getExtensionNum());
			insert_cdr_stmt.setString(9, cdr_data.getRecID());
			insert_cdr_stmt.setInt(10, rec_config.getGeneric_data().getGmt_offset());
			insert_cdr_stmt.setInt(11, cdr_logic);


			log.debug("InsertCDR:Query:" + insert_cdr_stmt.toString()+"\n");

			ResultSet insert_cdr_result = (ResultSet) insert_cdr_stmt.executeQuery();
			
			if(insert_cdr_result != null)
			{
				insert_cdr_result.beforeFirst();
				if (insert_cdr_result.next())
				{
					ret_val = insert_cdr_result.getInt("RETCODE");
				}
				else
					ret_val = -2;
				insert_cdr_result.close();
			}
			else
			{
				ret_val = -3;	// We have an error
			}
			insert_cdr_stmt.close();
			CloseDbConn(); 		// Explicitly close the datbase connection
			log.debug("InsertCDR:Query:Returns=" + Integer.toString(ret_val) + "\n");
			return(ret_val);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("InsertCDR:Query:Exception:"+e.toString()+"\n");
			CloseDbConn(); 		// Explicitly close the datbase connection
			return(-4);	// SQLException Error
		}
	}


}
