package uk.ac.reigate.rm13030.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.ac.reigate.rm13030.core.Application;
import uk.ac.reigate.rm13030.utils.SimpleLogger;
import uk.ac.reigate.rm13030.utils.SimpleLogger.MessageType;

/**
 * @author Robbie <http://reigate.ac.uk/>
 */
public class Database {
	
	private Properties connectionProperties;
	
	public static void main(String args[]) {
		new Database();
	}
	
	Database() {
		getConnection();
	}
	
	public Connection getConnection() {
		
		/**
		 * MySQL Server Settings
		 */
        String username = "pathfinder_user";
        String password = "reigatecollege";
        String dbms = "mysql";
        String serverName = "www.db4free.net";
        String portNumber = "3306";
        boolean useSSL = true;
        
        /**
         * Initialize the connectionProperties.
         */
        connectionProperties = new Properties();
        Connection connection = null;
        connectionProperties.put("user", username);
        connectionProperties.put("password", password);
        
        SimpleLogger.log(Database.class, MessageType.INFO, "Attempting to connect to database...");
        
        /**
         * Connect to the server
         */
        if (dbms.equals("mysql")) {
            try {
				connection = DriverManager.getConnection(
				        "jdbc:" + dbms + "://"
				        + serverName
				        + ":" + portNumber +"?useSSL="+useSSL,
				        connectionProperties);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				SimpleLogger.log(Database.class, MessageType.INFO, "Connected to database!");
			}
        }
        
        return connection;
	}
	
	//TODO: createUsersTable() method
	private void createUsersTable() {
        Connection con = getConnection(); //Opens the connection to the SQL Server
        
        /**
         * MACAddresses and Passwords are stored as CHAR not VARCHAR: 
         * 
         * -> The SHA-256 hashing algorithm will always
         * generate a 256-bit hash value (Stored as Hex -> use CHAR(64) or BINARY(32)).
         */
        
		String createString =
		    "CREATE TABLE pathfinder_user.tbl_Users" + 		//Creates the SQL Query
		    "(MACAddress CHAR(17), Username VARCHAR(12), Password CHAR(64), AccessLevel INT(1), EMPLOYEEID INT(9));";
		    //"(Username Varchar(12), Password CHAR(64), AccessLevel Int(1), EMPLOYEEID Int(9));";

		Statement stmt = null;
		try {
		    stmt = con.createStatement();
		    stmt.executeUpdate(createString);
		} catch (SQLException ex) {
			
		} finally {
		    if (stmt != null) {
		        try {
		            stmt.close(); //Closes the connection to the SQL Server
		        } catch (SQLException ex) {
		        	
		        }
		    }
		}
        
	}

}
