/**
 * 
 */
package sre5.cloud.ws.recordings;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author awp
 *
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;









//import org.apache.log4j.Logger;
//import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.libraries.awpcommon.database.CDatabaseConnections;

//import sre2018.cloud.apis.WSRDAuthsrednisResp;

//import javax.ws.rs.ApplicationPath;
//import javax.ws.rs.core.Application;

//import org.glassfish.jersey.media.multipart.MultiPartFeature;
//import org.glassfish.jersey.server.ResourceConfig;


public class CLSRE5SvcConfig implements ServletContextListener
{
	public static final String				ws_encr_key		= "AwpSRE5R3C$B0$$R0[k";
	private static final Logger				log				= LoggerFactory.getLogger(CLSRE5SvcConfig.class);
	public static int temp_val=100;
	public static CLLoadSre5Config 	sre19_conf;
	public static final CDatabaseConnections rec_db_conns = new CDatabaseConnections();
	private static StringBuilder log_buff = new StringBuilder(1024);
	public static CFGDSre5Config sre19_config = null;
	
	// Create threads to download the Recorded file
	public static final ExecutorService		executorService			= Executors
			.newFixedThreadPool(10);

	
			
	private static final String				sre19rec_ver			= "SoundRecall-2019 Cloud Recordings Web Service Version 2.1 : 003 (2020-01-14)\n";
	/*
	final Application application = new ResourceConfig()
    .packages("org.glassfish.jersey.examples.multipart")
    .register(MultiPartFeature.class);
	*/
	public void contextInitialized(ServletContextEvent arg0)
	{
		
		//log.get
		// Check if the configuration file is defined elsewhere. Allows us to define different files for each instance
		//String cfg_file = System.getProperty("sre5.config.file");
		final String cfg_file = System.getProperty("catalina.base")+"/conf/sre2018ws.conf";
		log_buff.setLength(0);

		//log.info("loaded the SRE5 Recording Service\n");
		sre19_conf = new CLLoadSre5Config(cfg_file);
		if(sre19_conf.err_code != 0)
		{
			// we should die here but how?
			log_buff.append(sre19rec_ver).append("Unable to open Config File \"").append(cfg_file).append("\"\n");
			log.error(log_buff.toString());
		}
		else
		{
			sre19_config = sre19_conf.sre5_config;
			log_buff.append(sre19rec_ver).append("Using Config File \"").append(cfg_file).append("\"\nDualRecord=").append(sre19_config.getGeneric_data().isDual_record()).append("\n");
			log.info(log_buff.toString());

			// Get the number of connections and the Initiate the Connections Class
			String database_name = "jdbc:mysql://"+ sre19_config.getSvc_db_host().getConfDBHost() + ":" + 
					sre19_config.getSvc_db_host().getConfDBPort() + "/" + sre19_config.getSvc_db_host().getConfDBName();
			int max_connections = sre19_config.getSvc_db_host().getMax_connections(); 
			if(max_connections <= 0)
				max_connections = 4;

			if(rec_db_conns.InitiateConnections("com.mysql.jdbc.Driver", database_name, sre19_config.getSvc_db_host().getConfDBUid(), 
					sre19_config.getSvc_db_host().getConfDBPwd(), max_connections) != 0)
			{
				log.error("Unable to initiate the DB Connections. Err="+rec_db_conns.getError_str()+"\n");
			}
			
		}
		
		// Create the threads for Downloading the recorded file
		executorService.execute(new Runnable() {
			public void run()
			{
				System.out.println("Asynchronous task");
			}
		});

	}// end contextInitialized method
	
	/*
	private void loadVandusenData()
	{
		// We will be moving this to the database but easier to do it this way for starters.
		callBridge = sre19_conf.sre19_config.getCallbridge();
		direct_dial = sre19_conf.sre19_config.getDirectdial();
	}
	*/

	public void contextDestroyed(ServletContextEvent arg0)
	{
		// Close the Database Connections
		rec_db_conns.CloseConnections();

		
		try
		{
			log.info("Calling MySQL AbandonedConnectionCleanupThread shutdown");
			com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();

		}
		catch (InterruptedException e)
		{
			log.error(
					"Error calling MySQL AbandonedConnectionCleanupThread shutdown {}",
					e);
		}

		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements())
		{
			Driver driver = drivers.nextElement();

			if (driver.getClass().getClassLoader() == cl)
			{

				try
				{
					log.info("Deregistering JDBC driver {}", driver);
					DriverManager.deregisterDriver(driver);

				}
				catch (SQLException ex)
				{
					log.error("Error deregistering JDBC driver {}", driver, ex);
				}

			}
			else
			{
				log.trace(
						"Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader",
						driver);
			}
		}

		MDC.clear();
		executorService.shutdown();
	}// end constextDestroyed method

}
