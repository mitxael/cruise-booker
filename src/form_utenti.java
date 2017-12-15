import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class form_utenti { 
	private int selected;
	Text text_anagrafica;

	public form_utenti(Display display) { 
		final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		shell.setText("Gestione degli utenti");
		int w = 640;
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

		shell.addListener(SWT.Close, new Listener() { 
			@Override 
			public void handleEvent(Event event) {  
				shell.dispose(); 
			} 
		}); 

		TableViewer tbl_view_navi = new TableViewer(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
		//tbl_view_navi.setContentProvider (new ArrayContentProvider()); 
		//tbl_view_navi.setLabelProvider (new MyLabelProvider()); 		 

		Table tbl_utenti = tbl_view_navi.getTable();
		tbl_utenti.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		tbl_utenti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_utenti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_utenti.setBounds(10, 10, 500, 110);
		tbl_utenti.setHeaderVisible(true);
		tbl_utenti.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(tbl_utenti, SWT.CENTER);
		TableColumn tc2 = new TableColumn(tbl_utenti,SWT.CENTER);
		TableColumn tc3 = new TableColumn(tbl_utenti,SWT.PASSWORD | SWT.CENTER);
		TableColumn tc4 = new TableColumn(tbl_utenti,SWT.CENTER);
		TableColumn tc5 = new TableColumn(tbl_utenti,SWT.CENTER);
		TableColumn tc6 = new TableColumn(tbl_utenti,SWT.CENTER);
		tc1.setWidth(0);
		tc2.setWidth(120);
		tc3.setWidth(70);
		tc4.setWidth(100);
		tc5.setWidth(80);
		tc6.setWidth(90);
		tc1.setText("ID");
		tc2.setText("Nome");
		tc3.setText("Password");
		tc4.setText("Amministratore");
		tc5.setText("Assistente");
		tc6.setText("Anagrafica");

		CellEditor[] tbl10_ce = new CellEditor[3];
		tbl10_ce[0] = new TextCellEditor(tbl_utenti);
		tbl10_ce[1] = new CheckboxCellEditor(tbl_utenti);
		tbl10_ce[2] = new ComboBoxCellEditor(tbl_utenti, new String[] {"A", "B", "C"}, SWT.READ_ONLY);

		Connection con = null;	 		  
		Statement stmt = null;
		try {
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT id_utente, nome_utente, password_utente, amministratore, assistente, id_anagrafica FROM Utente ";  
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				TableItem item = new TableItem(tbl_utenti, SWT.NONE);
				item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6)});
			}
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell, "Error", 
					"IMPOSSIBILE OTTENERE I DATI DELL'UTENTE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

		Label lbl_separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lbl_separator.setBounds(10, 130, 620, 2);

		//row#1
		Label lbl_nome = new Label(shell, SWT.SHADOW_IN);
		lbl_nome.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_nome.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_nome.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_nome.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_nome.setBounds(10, 140, 80, 20);
		lbl_nome.setText(" Nome:");	
		Text text_nome = new Text(shell, SWT.BORDER);
		text_nome.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_nome.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_nome.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_nome.setBounds(100, 140, 80, 20);

		Label lbl_password = new Label(shell, SWT.SHADOW_IN);
		lbl_password.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_password.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_password.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_password.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_password.setBounds(230, 140, 80, 20);
		lbl_password.setText(" Password:");	
		Text text_password = new Text(shell, SWT.BORDER);
		text_password.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_password.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_password.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_password.setBounds(320, 140, 80, 20);

		Label lbl_amministratore = new Label(shell, SWT.SHADOW_IN);
		lbl_amministratore.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_amministratore.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_amministratore.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_amministratore.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_amministratore.setBounds(450, 140, 100, 20);
		lbl_amministratore.setText(" Amministratore:");	
		Text text_amministratore = new Text(shell, SWT.BORDER);
		text_amministratore.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_amministratore.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_amministratore.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_amministratore.setBounds(560, 140, 60, 20);

		//row#2
		Label lbl_assistente = new Label(shell, SWT.SHADOW_IN);
		lbl_assistente.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_assistente.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_assistente.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_assistente.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_assistente.setBounds(10, 180, 80, 20);
		lbl_assistente.setText(" Assistente:");	
		Text text_assistente = new Text(shell, SWT.BORDER);
		text_assistente.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_assistente.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_assistente.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_assistente.setBounds(100, 180, 80, 20);

		Label lbl_anagrafica = new Label(shell, SWT.SHADOW_IN);
		lbl_anagrafica.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_anagrafica.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_anagrafica.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_anagrafica.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_anagrafica.setBounds(230, 180, 80, 20);
		lbl_anagrafica.setText(" Anagrafica:");	
		text_anagrafica = new Text(shell, SWT.BORDER);
		text_anagrafica.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_anagrafica.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_anagrafica.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_anagrafica.setBounds(320, 180, 80, 20);

		Button btn_anagrafica = new Button(shell, SWT.NONE);
		btn_anagrafica.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_anagrafica.setBounds(410, 185, 20, 15);
		btn_anagrafica.setText("...");
		btn_anagrafica.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ChildShell_1(display); 
			}
		});

		tbl_utenti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int selected = tbl_utenti.getSelectionIndex();
				if (selected > -1) {
					text_nome.setText(tbl_utenti.getItem(selected).getText(1));	
					text_password.setText(tbl_utenti.getItem(selected).getText(2));
					text_amministratore.setText(tbl_utenti.getItem(selected).getText(3));
					text_assistente.setText(tbl_utenti.getItem(selected).getText(4));
					text_anagrafica.setText(tbl_utenti.getItem(selected).getText(5));
				}
			}
		});

		Button btn_clear = new Button(shell, SWT.NONE);
		btn_clear.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
		btn_clear.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
		btn_clear.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_clear.setBounds(530, 10, 100, 20);
		btn_clear.setText("Svuota campi");
		btn_clear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_nome.setText("");
				text_password.setText("");
				text_amministratore.setText("");
				text_assistente.setText("");
				text_anagrafica.setText("");
			}
		});

		Button btn_saveas = new Button(shell, SWT.NONE);
		btn_saveas.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		btn_saveas.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		btn_saveas.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_saveas.setBounds(530, 40, 100, 20);
		btn_saveas.setText("Crea nuovo");
		btn_saveas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {		 			
				if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere un nuovo utente?") == true) {
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "INSERT INTO utente (nome_utente, password_utente, amministratore, assistente, id_anagrafica) ";
						query += "VALUES ('" + text_nome.getText() + "','" + text_password.getText() + "','" + text_amministratore.getText() + "','"
								+ text_assistente.getText() +"','" + text_anagrafica.getText() + "')";
						stmt.executeUpdate(query); 	

						//Update table
						tbl_utenti.removeAll();
						stmt.close();
						query = "SELECT id_utente, nome_utente, password_utente, amministratore, assistente, id_anagrafica FROM Utente ";
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_utenti, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE AGGIUNGERE NUOVO UTENTE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
					return;
				}
			}
		});

		Button btn_save = new Button(shell, SWT.NONE);
		btn_save.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btn_save.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btn_save.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_save.setBounds(530, 70, 100, 20);
		btn_save.setText("Salva");
		btn_save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selected = tbl_utenti.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare l'utente selezionato?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT id_utente, nome_utente, password_utente, amministratore, assistente, id_anagrafica FROM Utente ";
							query += "WHERE id_utente = '" + tbl_utenti.getItem(selected).getText(0) + "'";
							ResultSet rs = stmt.executeQuery(query); 	
							rs.beforeFirst();
							while(rs.next()){
								rs.updateString( "nome_utente", text_nome.getText());
								rs.updateString( "password_utente", text_password.getText());
								rs.updateString( "amministratore", text_amministratore.getText());
								rs.updateString( "assistente", text_assistente.getText());
								rs.updateString( "id_anagrafica", text_anagrafica.getText());
								rs.updateRow();
							}
							//Update table
							tbl_utenti.removeAll();
							stmt.close();
							rs.close();
							query = "SELECT id_utente, nome_utente, password_utente, amministratore, assistente, id_anagrafica FROM Utente ";
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_utenti, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6)});
							}
							tbl_utenti.select(selected);
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE MODIFICARE I DATI DELL'UTENTE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
						return;
					}
				}
			}
		});

		Button btn_remove = new Button(shell, SWT.NONE);
		btn_remove.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btn_remove.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btn_remove.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_remove.setBounds(530, 100, 100, 20);
		btn_remove.setText("Cancella");
		btn_remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selected = tbl_utenti.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare l'utente selezionato?") == true)
					{
						Connection con = null;
						CallableStatement cstmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();			
							cstmt = con.prepareCall("{call sp_cancellare_utente(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, Integer.parseInt(tbl_utenti.getItem(selected).getText(0))); 
							cstmt.executeUpdate();

							//Update table
							tbl_utenti.removeAll();
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String  query = "SELECT id_utente, nome_utente, password_utente, amministratore, assistente, id_anagrafica FROM Utente ";
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_utenti, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6)});
							}
							btn_clear.notifyListeners(SWT.MouseDown, null);
							selected = selected - 1;
							//tbl_navi.getItem(selected-1).notify();
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE CANCELLARE L'UTENTE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
											"\nError state:  "+exc.getSQLState() +"   -   Error code:  " + exc.getErrorCode());
							exc.printStackTrace();
							return;
						}
						finally {
							try {
								if (con != null) { con.close(); }
								if (cstmt != null) { cstmt.close(); }
							} catch (SQLException exc) { exc.printStackTrace(); }
						}
					}
				}
				else
					MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessun utente selezionata.");
			}
		});

		Button btn_back = new Button(shell, SWT.NONE);
		btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setText("<--");
		btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
		btn_back.setBounds(600, 240, 30, 18);
		btn_back.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});

		shell.open();
	} 	

	/****************************************************************************************************************************
	 ******** CHILD 01 **********************************************************************************************************
	 ****************************************************************************************************************************/	  	
	private class ChildShell_1 { 
		public ChildShell_1(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare una persona...");
			int w = 500;
			int h = 200;
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

			Button button_1 = new Button(shell, SWT.NONE);
			button_1.setBounds(0, 150, 50, 20);
			button_1.setText("Indietro");
			button_1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});

			Table tbl_navi = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
			tbl_navi.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			tbl_navi.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			tbl_navi.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			tbl_navi.setBounds(0, 0, 480, 145);
			tbl_navi.setHeaderVisible(true);
			tbl_navi.setLinesVisible(true);
			TableColumn tc1 = new TableColumn(tbl_navi, SWT.CENTER);
			TableColumn tc2 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc3 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc4 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc5 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc6 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc7 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc8 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc9 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc10 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc11 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc12 = new TableColumn(tbl_navi,SWT.CENTER);
			TableColumn tc13 = new TableColumn(tbl_navi,SWT.CENTER);
			tc1.setWidth(0);
			tc2.setWidth(100);
			tc3.setWidth(70);
			tc4.setWidth(60);
			tc5.setWidth(70);
			tc6.setWidth(90);
			tc7.setWidth(50);
			tc8.setWidth(100);
			tc9.setWidth(70);
			tc10.setWidth(60);
			tc11.setWidth(70);
			tc12.setWidth(90);
			tc13.setWidth(50);
			tc1.setText("ID");
			tc2.setText("Nome");
			tc3.setText("Cognome");
			tc4.setText("Nascita");
			tc5.setText("Telefono");
			tc6.setText("Cellulare");
			tc7.setText("Email");
			tc8.setText("Indirizzo");
			tc9.setText("CAP");
			tc10.setText("Citta");
			tc11.setText("CF");
			tc12.setText("Residenza");
			tc13.setText("Nazionalita");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();			
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, "
						+ "indirizzo, cap, citta, cf, paese_residenza, nazionalita FROM Anagrafica "; 
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					TableItem item = new TableItem(tbl_navi, SWT.NONE);
					item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
							rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)
							, rs.getString(12), rs.getString(13)});
				}
			}
			catch (SQLException exc) {
				MessageDialog.openError(shell, "Error", 
						"IMPOSSIBILE OTTENERE LA LISTA DELLE PERSONE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

			tbl_navi.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = tbl_navi.getSelectionIndex();
					if (selected > -1) {
						text_anagrafica.setText(tbl_navi.getItem(selected).getText(0));
					}
					shell.dispose();
				}
			});

			shell.addListener(SWT.Close, new Listener() { 
				@Override 
				public void handleEvent(Event event) {  
					shell.dispose(); 
				} 
			}); 

			shell.open(); 
		} 
	} 
	//}
}

