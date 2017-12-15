
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SQLConnector {
	public static String DB_SERVER=null;
	public static String DB_USER=null;
	public static String DB_PASS=null;

	public static Connection getDatabaseConnection() {
		//Check connection parameters at first run
		if (SQLConnector.DB_SERVER == null) {
			File ini_file = new File(System.getProperty("user.dir") + "\\res\\" + "config.ini");
			if(ini_file.exists() && !ini_file.isDirectory()) {
				if (IniManager.readINIFile("config.ini", "db_auth") == "SQL Server")
					SQLConnector.DB_SERVER = "jdbc:sqlserver://" + IniManager.readINIFile("config.ini", "db_server") + 
					";DatabaseName=" + IniManager.readINIFile("config.ini", "db_name");
				else
					SQLConnector.DB_SERVER = "jdbc:sqlserver://" + IniManager.readINIFile("config.ini", "db_server") + 
					";DatabaseName=" + IniManager.readINIFile("config.ini", "db_name") + ";integratedSecurity=true";
				SQLConnector.DB_USER = IniManager.readINIFile("config.ini", "db_username");
				SQLConnector.DB_PASS = IniManager.readINIFile("config.ini", "db_password");
			}
			else
			{
				form_impostazioni fi = new form_impostazioni();
				fi.open();
			}
		}

		//Establish connection
		Connection con = null;      	
		try {
			con = DriverManager.getConnection(DB_SERVER, DB_USER, DB_PASS);
		}
		catch(SQLException exc) {
			Display display = Display.getDefault();
			Shell shell = new Shell(SWT.None);
			shell.setSize(50, 20);
			shell.setLocation((display.getBounds().width-50)/2, (display.getBounds().height-20)/2);
			MessageDialog.openWarning(shell, "Attenzione!", "IMPOSSIBILE CONNETTERSI AL DATABASE." + 
					"\n\nError message:  \"" + exc.getMessage() + "\"" +
					"\nError state:  "+exc.getSQLState() + 
					"   -   Error code:  " + exc.getErrorCode());
			exc.printStackTrace();
		}
		return con;
	}

	public static void executeQuery(String query) 
	{  
		Connection con = null;
		Statement stmt = null;
		try { 
			con = getDatabaseConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  //allow absolute, first, last...
			String SQL = query;  
			ResultSet rs = stmt.executeQuery(SQL); 
			while (rs.next()) {  
				System.out.println(rs.getString(1));  
			} 
		}  
		catch (SQLException exc) {
			System.out.println("IMPOSSIBILE OTTENERE LA LISTA DI UTENTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
					"\nError state:  "+exc.getSQLState() +"   -   Error code:  " + exc.getErrorCode());
			exc.printStackTrace();
			return;
		}
		finally {
			try {
				if (con != null) { con.close(); }
				if (stmt != null) { stmt.close(); }
			} catch (SQLException exc) { exc.printStackTrace(); }
		}
	}
}