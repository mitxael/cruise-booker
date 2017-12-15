import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Combo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class form_login {

	protected Shell shell_login;
	private Text text_password;

	public static void main(String[] args) {



		//create login form
		try {
			form_login window = new form_login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(display);
		shell_login.open();
		shell_login.layout();
		while (!shell_login.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents(Display display) {
		shell_login = new Shell(SWT.TITLE | SWT.BORDER | SWT.CLOSE | SWT.PRIMARY_MODAL);
		shell_login.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		shell_login.setText("Sistema di Prenotazione");
		int w = 450;
		int h = 200;
		int x = (display.getBounds().width-w)/2;
		int y = (display.getBounds().height-h)/2;
		shell_login.setSize(w, h);
		shell_login.setLocation(x, y);

		Image origImage = new Image(display, System.getProperty("user.dir").replace('\\', '/') + "/res/bg_water.jpg");
		Image scaledImage = null; 
		double zoom = 1d/2; 
		final int width = origImage.getBounds().width; 
		final int height = origImage.getBounds().height; 
		scaledImage = new Image(Display.getDefault(), origImage.getImageData().scaledTo((int)(width * zoom),(int)(height * zoom))); 
		shell_login.setBackgroundImage(scaledImage);
		shell_login.setBackgroundMode(SWT.INHERIT_FORCE);

		Label lblSistemaDiPrenotazione = new Label(shell_login, SWT.NONE);
		lblSistemaDiPrenotazione.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblSistemaDiPrenotazione.setAlignment(SWT.CENTER);
		lblSistemaDiPrenotazione.setForeground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
		lblSistemaDiPrenotazione.setFont(SWTResourceManager.getFont("Bookman Old Style", 16, SWT.BOLD));
		lblSistemaDiPrenotazione.setBounds(0, 10, 444, 24);
		lblSistemaDiPrenotazione.setText("Sistema di Prenotazione di Crociere");

		Label lblUsername = new Label(shell_login, SWT.NONE);
		lblUsername.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblUsername.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblUsername.setAlignment(SWT.RIGHT);
		lblUsername.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblUsername.setBounds(39, 69, 80, 24);
		lblUsername.setText("Username: ");

		Label lblPassword = new Label(shell_login, SWT.NONE);
		lblPassword.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblPassword.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblPassword.setAlignment(SWT.RIGHT);
		lblPassword.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblPassword.setText("Password: ");
		lblPassword.setBounds(39, 110, 80, 24);

		Button btn_enter = new Button(shell_login, SWT.BORDER);
		btn_enter.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_enter.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_enter.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		btn_enter.setBounds(304, 69, 105, 67);
		btn_enter.setText("ACCEDI");

		text_password = new Text(shell_login, SWT.PASSWORD | SWT.BORDER);
		text_password.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_password.setText("1234");
		text_password.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		text_password.setBounds(131, 109, 129, 24);

		Label label = new Label(shell_login, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(0, 40, 444, 2);

		Combo cmb_username = new Combo(shell_login, SWT.NONE);
		cmb_username.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		cmb_username.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		cmb_username.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		cmb_username.setBounds(131, 68, 129, 25);

		Button btn_impostazioni = new Button(shell_login, SWT.BORDER);
		btn_impostazioni.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				form_impostazioni fi = new form_impostazioni();
				//shell_main.dispose();
				fi.open();
			}
		});
		btn_impostazioni.setText("Impostazioni");
		btn_impostazioni.setFont(SWTResourceManager.getFont("Times New Roman", 8, SWT.BOLD));
		btn_impostazioni.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btn_impostazioni.setBounds(0, 147, 91, 24);
		btn_enter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (cmb_username.getSelectionIndex() != -1) {
					Connection con = null;
					Statement stmt = null;
					try {
						con  = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement();
						String query = "SELECT id_utente, amministratore, assistente ";
						query += "FROM Utente ";
						query += "WHERE nome_utente = '" + cmb_username.getItem(cmb_username.getSelectionIndex()) + "' and password_utente = '" + text_password.getText()+"'";
						ResultSet rs = stmt.executeQuery(query);
						if (rs.next() ) {
							IniManager.writeKeyValuePairsINIFile("config.ini","Last_user", cmb_username.getText());

							form_main fm = new form_main();

							if (rs.getString(2).equals("1"))
								fm.admin = true;
							else 
								fm.admin = false;	
							if (rs.getString(3).equals("1"))
								fm.assist = true;
							else 
								fm.assist = false;

							shell_login.dispose();

							fm.open();
						}
						else {
							MessageDialog.openWarning(shell_login, "Error", "Password non valida.");
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell_login, "Error", 
								"IMPOSSIBILE VERIFICARE LE CREDENZIALI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				else
					MessageDialog.openError(shell_login, "Error", "Nessun utente selezionato.");
			}
		});

		//Catch the "close" button
		shell_login.addListener(SWT.Close, new Listener()
		{
			public void handleEvent(Event event)
			{
				int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
				MessageBox messageBox = new MessageBox(shell_login, style);
				messageBox.setText("Richiesta di conferma");
				messageBox.setMessage("Sei sicuro di voler uscire dall'applicazione?");
				event.doit = messageBox.open() == SWT.YES;
			}
		});

		//Get usernames
		Connection con = null;
		Statement stmt = null;
		try {
			con = SQLConnector.getDatabaseConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT id_utente, nome_utente  FROM Utente";
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {  
				cmb_username.add(rs.getString(2));
			}
			cmb_username.select(cmb_username.indexOf(IniManager.readINIFile("config.ini", "Last_user")));
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell_login, "Error", 
					"IMPOSSIBILE OTTENERE LA LISTA DI UTENTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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