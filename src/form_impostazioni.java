
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class form_impostazioni {

	protected Shell shell;
	private String db_string=null;

	public static void main(String[] args) {
		try {
			form_impostazioni window = new form_impostazioni();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(display);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents(Display display) {
		shell = new Shell(SWT.TITLE | SWT.BORDER | SWT.PRIMARY_MODAL);
		shell.setText("Sistema di Prenotazione");
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		int w = 500;
		int h = 300;
		int x = (display.getBounds().width-w)/2;
		int y = (display.getBounds().height-h)/2;
		shell.setSize(w, h);
		shell.setLocation(x, y);

		Image origImage = new Image(display, System.getProperty("user.dir").replace('\\', '/') + "/res/bg_water.jpg");
		Image scaledImage = null; 
		double zoom = 1d/2; 
		final int width = origImage.getBounds().width; 
		final int height = origImage.getBounds().height; 
		scaledImage = new Image(Display.getDefault(), origImage.getImageData().scaledTo((int)(width * zoom),(int)(height * zoom))); 
		shell.setBackgroundImage(scaledImage);

		//row#1
		Label lbl_db_server = new Label(shell, SWT.SHADOW_IN);
		lbl_db_server.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_db_server.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_db_server.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_db_server.setBounds(10, 10, 100, 20);
		lbl_db_server.setText("DB server: ");

		Text text_db_server = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_db_server.setEditable(true);
		text_db_server.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_db_server.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_db_server.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_db_server.setBounds(120, 10, 200, 20);

		//row#2
		Label lbl_db_auth = new Label(shell, SWT.SHADOW_IN);
		lbl_db_auth.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_db_auth.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_db_auth.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_db_auth.setBounds(10, 50, 100, 20);
		lbl_db_auth.setText("DB authentication: ");

		Combo cmb_db_auth = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_db_auth.setBounds(120, 50, 200, 20);
		cmb_db_auth.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		cmb_db_auth.add("SQL Server");
		cmb_db_auth.add("Windows");

		//row#3
		Label lbl_db_user = new Label(shell, SWT.SHADOW_IN);
		lbl_db_user.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_db_user.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_db_user.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_db_user.setBounds(10, 90, 100, 20);
		lbl_db_user.setText("DB username: ");

		Text text_db_user = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_db_user.setEditable(true);
		text_db_user.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_db_user.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_db_user.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_db_user.setBounds(120, 90, 100, 20);	    

		//row#4	    
		Label lbl_db_password = new Label(shell, SWT.SHADOW_IN);
		lbl_db_password.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_db_password.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_db_password.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_db_password.setBounds(10, 130, 100, 20);
		lbl_db_password.setText("DB password: ");

		Text text_db_password = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_db_password.setEditable(true);
		text_db_password.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_db_password.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_db_password.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_db_password.setBounds(120, 130, 100, 20);

		//row#5
		Label lbl_db_name = new Label(shell, SWT.SHADOW_IN);
		lbl_db_name.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_db_name.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_db_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_db_name.setBounds(10, 170, 100, 20);
		lbl_db_name.setText("DB name: ");

		//LOAD COMBO DBNAME
		Combo cmb_db_name = new Combo(shell, SWT.NONE);//SWT.READ_ONLY
		cmb_db_name.setBounds(120, 170, 200, 20);
		cmb_db_name.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		Connection con = null;
		try {
			if (cmb_db_auth.getSelectionIndex() == 0) //SQL Server authentication
				con = DriverManager.getConnection("jdbc:sqlserver://" + text_db_server.getText() + ";DatabaseName=" + "master",
						text_db_user.getText(), text_db_password.getText());
			else //Windows authentication
				con = DriverManager.getConnection("jdbc:sqlserver://" + text_db_server.getText() + ";DatabaseName=" + "master" + ";integratedSecurity=true",
						text_db_user.getText(), text_db_password.getText());

			DatabaseMetaData meta = con.getMetaData();
			ResultSet res = meta.getCatalogs();
			cmb_db_name.removeAll();
			while (res.next()) {
				cmb_db_name.add(res.getString("TABLE_CAT"));
			}
			cmb_db_name.select(0);
			res.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//LOAD INI VALUES
		File ini_file = new File(System.getProperty("user.dir") + "\\res\\" + "config.ini");
		if(ini_file.exists() && !ini_file.isDirectory()) {
			if (IniManager.readINIFile("config.ini", "db_server") != "")
				text_db_server.setText(IniManager.readINIFile("config.ini", "db_server"));
			else
				text_db_server.setText("localhost:1433");

			if (IniManager.readINIFile("config.ini", "db_auth") != "")
				cmb_db_auth.select(cmb_db_auth.indexOf(IniManager.readINIFile("config.ini", "db_auth")));
			else
				cmb_db_auth.select(1);

			if (IniManager.readINIFile("config.ini", "db_username") != "")
				text_db_user.setText(IniManager.readINIFile("config.ini", "db_username"));
			else
				text_db_user.setText("sa");

			if (IniManager.readINIFile("config.ini", "db_password") != "")
				text_db_password.setText(IniManager.readINIFile("config.ini", "db_password"));
			else
				text_db_password.setText("");	

			if (IniManager.readINIFile("config.ini", "db_name") != "")
				cmb_db_name.select(cmb_db_name.indexOf(IniManager.readINIFile("config.ini", "db_name")));
			else
				cmb_db_name.select(0);
		} else {
			text_db_server.setText("localhost:1433");
			cmb_db_auth.select(1);
			text_db_user.setText("sa");			
			text_db_password.setText("");	
			cmb_db_name.select(0);			
		}

		//Check authentication type
		if (cmb_db_auth.getSelectionIndex() == 1) {
			lbl_db_user.setVisible(false);
			text_db_user.setVisible(false);
			lbl_db_password.setVisible(false);
			text_db_password.setVisible(false);
		}
		else {
			lbl_db_user.setVisible(true);
			text_db_user.setVisible(true);
			lbl_db_password.setVisible(true);
			text_db_password.setVisible(true);
		}

		cmb_db_auth.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (cmb_db_auth.getSelectionIndex() == 1) {
					lbl_db_user.setVisible(false);
					text_db_user.setVisible(false);
					lbl_db_password.setVisible(false);
					text_db_password.setVisible(false);
				}
				else {
					lbl_db_user.setVisible(true);
					text_db_user.setVisible(true);
					lbl_db_password.setVisible(true);
					text_db_password.setVisible(true);
				}

				Connection con = null;
				try {
					if (cmb_db_auth.getSelectionIndex() == 0) //SQL Server authentication
						con = DriverManager.getConnection("jdbc:sqlserver://" + text_db_server.getText() + ";DatabaseName=" + "master",
								text_db_user.getText(), text_db_password.getText());
					else //Windows authentication
						con = DriverManager.getConnection("jdbc:sqlserver://" + text_db_server.getText() + ";DatabaseName=" + "master" + ";integratedSecurity=true",
								text_db_user.getText(), text_db_password.getText());

					DatabaseMetaData meta = con.getMetaData();
					ResultSet res = meta.getCatalogs();
					cmb_db_name.removeAll();
					while (res.next()) {
						cmb_db_name.add(res.getString("TABLE_CAT"));
					}
					cmb_db_name.select(0);
					res.close();
					con.close();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});

		Button btn_save = new Button(shell, SWT.NONE);
		btn_save.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btn_save.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btn_save.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_save.setBounds(10, 240, 100, 30);
		btn_save.setText("Salva");
		btn_save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (cmb_db_auth.getSelectionIndex() == 0) //SQL Server authentication
					db_string = "jdbc:sqlserver://" + text_db_server.getText() + ";DatabaseName=" + 
							cmb_db_name.getItem(cmb_db_name.getSelectionIndex());
				else //Windows authentication
					db_string = "jdbc:sqlserver://" + text_db_server.getText() + ";DatabaseName=" +  
							cmb_db_name.getItem(cmb_db_name.getSelectionIndex()) + ";integratedSecurity=true";

				Connection con=null;
				try {
					con = DriverManager.getConnection(db_string, text_db_user.getText(), text_db_password.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				if (con != null) {
					SQLConnector.DB_SERVER = db_string;
					SQLConnector.DB_USER = text_db_user.getText();
					SQLConnector.DB_PASS = text_db_password.getText();
					IniManager.writeKeyValuePairsINIFile("config.ini","db_server", text_db_server.getText());
					IniManager.writeKeyValuePairsINIFile("config.ini","db_auth", cmb_db_auth.getText());
					IniManager.writeKeyValuePairsINIFile("config.ini","db_username", text_db_user.getText());
					IniManager.writeKeyValuePairsINIFile("config.ini","db_password", text_db_password.getText());
					IniManager.writeKeyValuePairsINIFile("config.ini","db_name", cmb_db_name.getText());
					MessageDialog.openInformation(shell, "Ok", "CONNESSIONE AL DATABASE RIUSCITA.");
					shell.dispose();
				}
				else
					MessageDialog.openError(shell, "Errore", "IMPOSSIBILE CONNETTERSI AL DATABASE.");
			}
		});

		Button btn_cancel = new Button(shell, SWT.NONE);
		btn_cancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_cancel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_cancel.setText("Annulla");
		btn_cancel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_cancel.setBounds(390, 240, 100, 30);
		btn_cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//form_main fm = new form_main();
				shell.dispose();
				//fm.open();
			}
		});
	}
}
