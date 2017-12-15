import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
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

public class form_crociere { 
	private int selected;
	Text text_nave_id;
	Text text_nave;
	Text text_crociera_id;
	Text text_crociera;
	Text text_data_inizio;
	Text text_data_fine;
	Date date_a;
	Date date_z;
	Table tbl_reg_crociere;

	public form_crociere(Display display) { 
		final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		shell.setText("Registro delle Crociere");
		int w = 640;
		int h = 320;
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

		TableViewer tbl_view_reg_crociere = new TableViewer(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
		//tbl_view_navi.setContentProvider (new ArrayContentProvider()); 
		//tbl_view_navi.setLabelProvider (new MyLabelProvider()); 		 

		tbl_reg_crociere = tbl_view_reg_crociere.getTable();
		tbl_reg_crociere.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		tbl_reg_crociere.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_reg_crociere.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_reg_crociere.setBounds(10, 10, 500, 110);
		tbl_reg_crociere.setHeaderVisible(true);
		tbl_reg_crociere.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(tbl_reg_crociere, SWT.CENTER);
		tc1.setWidth(0);
		tc1.setText("ID");
		TableColumn tc2 = new TableColumn(tbl_reg_crociere,SWT.CENTER);
		tc2.setWidth(0);
		tc2.setText("Crociera_ID");
		TableColumn tc3 = new TableColumn(tbl_reg_crociere,SWT.CENTER);
		tc3.setWidth(100);
		tc3.setText("Crociera");
		TableColumn tc4 = new TableColumn(tbl_reg_crociere,SWT.CENTER);
		tc4.setWidth(70);
		tc4.setText("Tipologia");		 		
		TableColumn tc5 = new TableColumn(tbl_reg_crociere,SWT.CENTER);
		tc5.setWidth(0);
		tc5.setText("Nave_ID");
		TableColumn tc6 = new TableColumn(tbl_reg_crociere,SWT.CENTER);
		tc6.setWidth(70);
		tc6.setText("Nave");
		TableColumn tc7 = new TableColumn(tbl_reg_crociere,SWT.CENTER);
		tc7.setWidth(80);
		tc7.setText("Inizio");
		TableColumn tc8 = new TableColumn(tbl_reg_crociere,SWT.CENTER);
		tc8.setWidth(80);
		tc8.setText("Fine");
		TableColumn tc9 = new TableColumn(tbl_reg_crociere,SWT.CENTER);
		tc9.setWidth(90);
		tc9.setText("Prenot.");
		
		CellEditor[] tbl10_ce = new CellEditor[3];
		tbl10_ce[0] = new TextCellEditor(tbl_reg_crociere);
		tbl10_ce[1] = new CheckboxCellEditor(tbl_reg_crociere);
		tbl10_ce[2] = new ComboBoxCellEditor(tbl_reg_crociere, new String[] {"A", "B", "C"}, SWT.READ_ONLY);

		Connection con = null;	 		  
		Statement stmt = null;
		try {
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, "
					+ "T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine, COUNT(T5.id_prenotazione) AS prenotazioni "
					+ "FROM Registro_Crociere T1 "
					+ "INNER JOIN Crociera T2 "
					+ "ON T1.id_crociera = T2.id_crociera "
					+ "INNER JOIN Nave T3 "
					+ "ON T1.id_nave = T3.id_nave "
					+ "INNER JOIN Tipologia_crociera T4 "
					+ "ON T2.id_tipologia_crociera = T4.id_tipologia_crociera "
					+ "LEFT JOIN Prenotazione T5 "
					+ "ON T1.id_registro_crociera = T5.id_registro_crociera "
					+ "GROUP BY T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine";  
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				TableItem item = new TableItem(tbl_reg_crociere, SWT.NONE);
				item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)});
			}
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell, "Error", 
					"IMPOSSIBILE OTTENERE I DATI DELLE CROCIERE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
		Label lbl_data_inizio = new Label(shell, SWT.SHADOW_IN);
		lbl_data_inizio.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_data_inizio.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_data_inizio.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_data_inizio.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_data_inizio.setBounds(10, 140, 80, 20);
		lbl_data_inizio.setText(" Inizio:");	
		//DateTime text_data_inizio = new DateTime(shell, SWT.BORDER);
		text_data_inizio = new Text(shell, SWT.BORDER);
		text_data_inizio.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_data_inizio.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_data_inizio.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_data_inizio.setEditable(false);
		text_data_inizio.setBounds(100, 140, 80, 20);

		Label lbl_data_fine = new Label(shell, SWT.SHADOW_IN);
		lbl_data_fine.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_data_fine.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_data_fine.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_data_fine.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_data_fine.setBounds(230, 140, 80, 20);
		lbl_data_fine.setText(" Fine:");	
		//DateTime text_data_fine = new DateTime(shell, SWT.BORDER);
		text_data_fine = new Text(shell, SWT.BORDER);
		text_data_fine.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_data_fine.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_data_fine.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_data_fine.setEditable(false);
		text_data_fine.setBounds(320, 140, 80, 20);

		text_data_inizio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				new ChildShell_dates(display, 1);
			}
		});
		text_data_fine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				new ChildShell_dates(display, 2);
			}
		});

		//row#2
		Label lbl_crociera = new Label(shell, SWT.SHADOW_IN);
		lbl_crociera.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_crociera.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_crociera.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_crociera.setBounds(10, 180, 80, 20);
		lbl_crociera.setText(" Crociera:");	
		text_crociera = new Text(shell, SWT.BORDER);
		text_crociera.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_crociera.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_crociera.setBounds(100, 180, 160, 20);

		text_crociera_id = new Text(shell, SWT.VIRTUAL);
		text_crociera_id.setVisible(false);

		Button btn_crociera = new Button(shell, SWT.NONE);
		btn_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_crociera.setBounds(270, 185, 20, 15);
		btn_crociera.setText("...");
		btn_crociera.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ChildShell_1(display); 
			}
		});			

		//row#3
		Label lbl_nave = new Label(shell, SWT.SHADOW_IN);
		lbl_nave.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_nave.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_nave.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_nave.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_nave.setBounds(10, 220, 80, 20);
		lbl_nave.setText(" Nave:");	
		text_nave = new Text(shell, SWT.BORDER);
		text_nave.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_nave.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_nave.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_nave.setBounds(100, 220, 80, 20);

		text_nave_id = new Text(shell, SWT.VIRTUAL);
		text_nave_id.setVisible(false);

		Button btn_nave = new Button(shell, SWT.NONE);
		btn_nave.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_nave.setBounds(190, 225, 20, 15);
		btn_nave.setText("...");
		btn_nave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ChildShell_2(display); 
			}
		});

		tbl_reg_crociere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int selected = tbl_reg_crociere.getSelectionIndex();
				if (selected > -1) { 
					text_crociera_id.setText(tbl_reg_crociere.getItem(selected).getText(1));
					text_crociera.setText(tbl_reg_crociere.getItem(selected).getText(2));
					//text_tipologia.setText(tbl_reg_crociere.getItem(selected).getText(3));
					text_nave_id.setText(tbl_reg_crociere.getItem(selected).getText(4));
					text_nave.setText(tbl_reg_crociere.getItem(selected).getText(5));
					text_data_inizio.setText(tbl_reg_crociere.getItem(selected).getText(6));	
					text_data_fine.setText(tbl_reg_crociere.getItem(selected).getText(7));
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
				text_data_inizio.setText("");
				text_data_fine.setText("");
				text_crociera.setText("");
				text_crociera_id.setText("");
				text_nave.setText("");
				text_nave_id.setText("");
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
				if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler registrare una nuova crociera?") == true) {
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "INSERT INTO Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) ";
						query += "VALUES ('" + text_data_inizio.getText() + "','" + text_data_fine.getText() + "','" + text_nave_id.getText() + "','"
								+ text_crociera_id.getText() + "')";
						stmt.executeUpdate(query); 	

						//Update table
						tbl_reg_crociere.removeAll();
						stmt.close();
						query = "SELECT T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, "
								+ "T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine, COUNT(T5.id_prenotazione) AS prenotazioni "
								+ "FROM Registro_Crociere T1 "
								+ "INNER JOIN Crociera T2 "
								+ "ON T1.id_crociera = T2.id_crociera "
								+ "INNER JOIN Nave T3 "
								+ "ON T1.id_nave = T3.id_nave "
								+ "INNER JOIN Tipologia_crociera T4 "
								+ "ON T2.id_tipologia_crociera = T4.id_tipologia_crociera "
								+ "LEFT JOIN Prenotazione T5 "
								+ "ON T1.id_registro_crociera = T5.id_registro_crociera "
								+ "GROUP BY T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine";
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_reg_crociere, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE REGISTRARE NUOVA CROCIERA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				selected = tbl_reg_crociere.getSelectionIndex();
				if (selected > -1) { 						 
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare il registro di crociera selezionato?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, "
									+ "T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine "
									+ "FROM Registro_Crociere T1 "
									+ "INNER JOIN Crociera T2 "
									+ "ON T1.id_crociera = T2.id_crociera "
									+ "INNER JOIN Nave T3 "
									+ "ON T1.id_nave = T3.id_nave "
									+ "INNER JOIN Tipologia_crociera T4 "
									+ "ON T2.id_tipologia_crociera = T4.id_tipologia_crociera "
									+ "LEFT JOIN Prenotazione T5 "
									+ "ON T1.id_registro_crociera = T5.id_registro_crociera "
									+ "WHERE T1.id_registro_crociera = '" + Integer.parseInt(tbl_reg_crociere.getItem(selected).getText(0))+ "'";
							ResultSet rs = stmt.executeQuery(query); 	
							rs.beforeFirst();
							while(rs.next()){
								rs.updateString( "data_inizio", text_data_inizio.getText());
								rs.updateString( "data_fine", text_data_fine.getText());
								rs.updateString( "id_crociera", text_crociera_id.getText());
								rs.updateString( "id_nave", text_nave_id.getText());
								rs.updateRow();
							}
							//Update table
							tbl_reg_crociere.removeAll();
							stmt.close();
							rs.close();
							query = "SELECT T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, "
									+ "T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine, COUNT(T5.id_prenotazione) AS prenotazioni "
									+ "FROM Registro_Crociere T1 "
									+ "INNER JOIN Crociera T2 "
									+ "ON T1.id_crociera = T2.id_crociera "
									+ "INNER JOIN Nave T3 "
									+ "ON T1.id_nave = T3.id_nave "
									+ "INNER JOIN Tipologia_crociera T4 "
									+ "ON T2.id_tipologia_crociera = T4.id_tipologia_crociera "
									+ "LEFT JOIN Prenotazione T5 "
									+ "ON T1.id_registro_crociera = T5.id_registro_crociera "
									+ "GROUP BY T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine";
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_reg_crociere, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)});
							}
							tbl_reg_crociere.select(selected);
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE MODIFICARE I DATI DEL REGISTRO DI CROCIERA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				int selected = tbl_reg_crociere.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare il registro della crociera selezionata?") == true)
					{
						Connection con = null;
						CallableStatement cstmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();			
							cstmt = con.prepareCall("{call sp_cancellare_registro_crociera(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, Integer.parseInt(tbl_reg_crociere.getItem(selected).getText(0)));
							cstmt.executeUpdate();

							//Update table
							tbl_reg_crociere.removeAll();
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String  query = "SELECT T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, "
									+ "T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine, COUNT(T5.id_prenotazione) AS prenotazioni "
									+ "FROM Registro_Crociere T1 "
									+ "INNER JOIN Crociera T2 "
									+ "ON T1.id_crociera = T2.id_crociera "
									+ "INNER JOIN Nave T3 "
									+ "ON T1.id_nave = T3.id_nave "
									+ "INNER JOIN Tipologia_crociera T4 "
									+ "ON T2.id_tipologia_crociera = T4.id_tipologia_crociera "
									+ "LEFT JOIN Prenotazione T5 "
									+ "ON T1.id_registro_crociera = T5.id_registro_crociera "
									+ "GROUP BY T1.id_registro_crociera, T1.id_crociera, T2.denominazione, T4.descrizione, T1.id_nave, T3.nome, T1.data_inizio, T1.data_fine";
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_reg_crociere, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)});
							}
							btn_clear.notifyListeners(SWT.MouseDown, null);
							selected = selected - 1;
							//tbl_navi.getItem(selected-1).notify();
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE CANCELLARE IL REGISTRO DELLA CROCIERA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
					MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessun registro selezionato.");
			}
		});

		Button btn_back = new Button(shell, SWT.NONE);
		btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setText("<--");
		btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
		btn_back.setBounds(600, 270, 30, 18);
		btn_back.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});

		Button btn_clienti = new Button(shell, SWT.NONE);
		btn_clienti.setBounds(10, 260, 240, 20);
		btn_clienti.setText("Visualizza prenotazioni per questa crociera...");
		btn_clienti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selected = tbl_reg_crociere.getSelectionIndex();
				if ( selected >= 0)
					new ChildShell_4(display, Integer.parseInt(tbl_reg_crociere.getItem(selected).getText()));
				else
					MessageDialog.openInformation(shell, "Attenzione", "Selezionare una crociera dalla lista clickando su di essa.");
			}
		});
		
		shell.open();
	} 	

	/****************************************************************************************************************************
	 ********* CHILD 01 **********************************************************************************************************
	 ****************************************************************************************************************************/	  	
	private class ChildShell_1 { 
		public ChildShell_1(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare una crociera...");
			int w = 500;
			int h = 280;
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

			Label lbl_separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			lbl_separator.setBounds(10, 150, 480, 2);

			//row#1
			Label lbl_denominazione = new Label(shell, SWT.SHADOW_IN);
			lbl_denominazione.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_denominazione.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_denominazione.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_denominazione.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_denominazione.setBounds(10, 160, 90, 20);
			lbl_denominazione.setText(" Denominazione:");	
			Text text_denominazione = new Text(shell, SWT.BORDER);
			text_denominazione.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_denominazione.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_denominazione.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_denominazione.setBounds(110, 160, 160, 20);

			Label lbl_tipologia = new Label(shell, SWT.SHADOW_IN);
			lbl_tipologia.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_tipologia.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_tipologia.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_tipologia.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_tipologia.setBounds(290, 160, 80, 20);
			lbl_tipologia.setText(" Tipologia:");	
			Combo cmb_tipologia = new Combo(shell, SWT.READ_ONLY);
			cmb_tipologia.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			cmb_tipologia.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			cmb_tipologia.setBounds(380, 160, 80, 15);

			Combo cmb_tipologia_ID = new Combo(shell, SWT.VIRTUAL);
			cmb_tipologia_ID.setVisible(false);		


			Button btn_tipologia = new Button(shell, SWT.NONE);
			btn_tipologia.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
			btn_tipologia.setBounds(470, 165, 20, 15);
			btn_tipologia.setText("...");
			btn_tipologia.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					new ChildShell_3(display); 
				}
			});

			//row#2
			Label lbl_cadenza = new Label(shell, SWT.SHADOW_IN);
			lbl_cadenza.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_cadenza.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_cadenza.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_cadenza.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_cadenza.setBounds(10, 200, 90, 20);
			lbl_cadenza.setText(" Cadenza:");	
			Text text_cadenza = new Text(shell, SWT.BORDER);
			text_cadenza.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_cadenza.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_cadenza.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_cadenza.setBounds(110, 200, 80, 20);

			Label lbl_costo = new Label(shell, SWT.SHADOW_IN);
			lbl_costo.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_costo.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_costo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_costo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_costo.setBounds(230, 200, 80, 20);
			lbl_costo.setText(" Costo:");	
			Text text_costo = new Text(shell, SWT.BORDER);
			text_costo.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_costo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_costo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_costo.setBounds(320, 200, 80, 20);

			Table tbl_crociera = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
			tbl_crociera.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			tbl_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			tbl_crociera.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			tbl_crociera.setBounds(10, 10, 370, 130);
			tbl_crociera.setHeaderVisible(true);
			tbl_crociera.setLinesVisible(true);
			TableColumn tc1 = new TableColumn(tbl_crociera, SWT.CENTER);
			tc1.setWidth(0);
			tc1.setText("ID");
			TableColumn tc2 = new TableColumn(tbl_crociera,SWT.CENTER);
			tc2.setWidth(160);
			tc2.setText("Denominazione");
			TableColumn tc3 = new TableColumn(tbl_crociera,SWT.CENTER);
			tc3.setWidth(0);
			tc3.setText("Tipologia_ID");
			TableColumn tc4 = new TableColumn(tbl_crociera,SWT.CENTER);
			tc4.setWidth(70);
			tc4.setText("Tipologia");
			TableColumn tc5 = new TableColumn(tbl_crociera,SWT.CENTER);
			tc5.setWidth(60);
			tc5.setText("Cadenza");
			TableColumn tc6 = new TableColumn(tbl_crociera,SWT.CENTER);
			tc6.setWidth(60);
			tc6.setText("Costo");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();			
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT T1.id_crociera, T1.denominazione, T2.id_tipologia_crociera, T2.Descrizione, T1.cadenza_giorni, T1.costo_base "
						+ "FROM Crociera T1 "
						+ "INNER JOIN Tipologia_Crociera T2 "
						+ "ON T1.id_tipologia_crociera = T2.id_tipologia_crociera"; 
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					TableItem item = new TableItem(tbl_crociera, SWT.NONE);
					item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
							rs.getString(6)});
				}
			}
			catch (SQLException exc) {
				MessageDialog.openError(shell, "Error", 
						"IMPOSSIBILE OTTENERE LA LISTA DELLE CROCIERE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

			//LOAD COMBOBOX
			try {
				con = SQLConnector.getDatabaseConnection();	 		  
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_tipologia_crociera, descrizione FROM Tipologia_Crociera ";  
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					cmb_tipologia_ID.add(rs.getString(1));
					cmb_tipologia.add(rs.getString(2));
				}
				cmb_tipologia.select(0);
			}
			catch (SQLException exc) {
				MessageDialog.openError(shell, "Error", 
						"IMPOSSIBILE OTTENERE I DATI DELLA CROCIERA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

			cmb_tipologia.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					cmb_tipologia_ID.select(cmb_tipologia.getSelectionIndex());
				}
			});

			tbl_crociera.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = tbl_crociera.getSelectionIndex();
					if (selected > -1) {
						text_crociera_id.setText(tbl_crociera.getItem(selected).getText(0));
						text_crociera.setText(tbl_crociera.getItem(selected).getText(1));
					}
					shell.dispose();
				}

				@Override
				public void mouseDown(MouseEvent e) {
					int selected = tbl_crociera.getSelectionIndex();
					if (selected > -1) {
						text_denominazione.setText(tbl_crociera.getItem(selected).getText(1));		 						
						cmb_tipologia.select(cmb_tipologia_ID.indexOf(tbl_crociera.getItem(selected).getText(2)));//(3)
						cmb_tipologia_ID.select(tbl_crociera.getSelectionIndex());//(2)
						text_cadenza.setText(tbl_crociera.getItem(selected).getText(4));
						text_costo.setText(tbl_crociera.getItem(selected).getText(5));
					}
				}
			});

			Button btn_back = new Button(shell, SWT.NONE);
			btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
			btn_back.setBounds(430, 220, 60, 20);
			btn_back.setText("Indietro");
			btn_back.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});

			Button btn_clear = new Button(shell, SWT.NONE);
			btn_clear.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
			btn_clear.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
			btn_clear.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
			btn_clear.setBounds(390, 10, 100, 20);
			btn_clear.setText("Svuota campi");
			btn_clear.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					text_denominazione.setText("");
					cmb_tipologia.deselectAll();
					cmb_tipologia_ID.deselectAll();
					text_cadenza.setText("");
					text_costo.setText("");
				}
			});

			Button btn_saveas = new Button(shell, SWT.NONE);
			btn_saveas.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
			btn_saveas.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
			btn_saveas.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
			btn_saveas.setBounds(390, 40, 100, 20);
			btn_saveas.setText("Crea nuovo");
			btn_saveas.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {		 			
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere una nuova crociera?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "INSERT INTO Crociera (id_tipologia_crociera, cadenza_giorni, costo_base, denominazione) ";
							query += "VALUES ('" + cmb_tipologia_ID.getItem(cmb_tipologia.getSelectionIndex()) + "','" + text_cadenza.getText() + "','" 
									+ text_costo.getText() + "','" + text_denominazione.getText() + "')";
							stmt.executeUpdate(query); 	

							//Update table
							tbl_crociera.removeAll();
							stmt.close();
							query = "SELECT T1.id_crociera, T1.denominazione, T2.id_tipologia_crociera, T2.Descrizione, T1.cadenza_giorni, T1.costo_base "
									+ "FROM Crociera T1 "
									+ "INNER JOIN Tipologia_Crociera T2 "
									+ "ON T1.id_tipologia_crociera = T2.id_tipologia_crociera ";
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_crociera, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6)});
							}
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE AGGIUNGERE NUOVA CROCIERA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
			btn_save.setBounds(390, 70, 100, 20);
			btn_save.setText("Salva");
			btn_save.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					selected = tbl_crociera.getSelectionIndex();
					if (selected > -1) {
						if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare la crociera selezionata?") == true) {
							Connection con = null;
							Statement stmt = null;
							try {
								con = SQLConnector.getDatabaseConnection();
								stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
								String query = "SELECT T1.id_crociera, T1.denominazione, T2.id_tipologia_crociera, T2.Descrizione, T1.cadenza_giorni, T1.costo_base "
										+ "FROM Crociera T1 "
										+ "INNER JOIN Tipologia_Crociera T2 "
										+ "ON T1.id_tipologia_crociera = T2.id_tipologia_crociera "
										+ "WHERE T1.id_crociera = '" + Integer.parseInt(tbl_crociera.getItem(selected).getText(0)) + "'";
								ResultSet rs = stmt.executeQuery(query); 	
								rs.beforeFirst();
								while(rs.next()){
									rs.updateString( "denominazione", text_denominazione.getText());
									rs.updateString( "id_tipologia_crociera", cmb_tipologia_ID.getItem(cmb_tipologia.getSelectionIndex()));
									rs.updateString( "cadenza_giorni", text_cadenza.getText());
									rs.updateString( "costo_base", text_costo.getText());
									rs.updateRow();
								}
								//Update table
								tbl_crociera.removeAll();
								stmt.close();
								rs.close();
								query = "SELECT T1.id_crociera, T1.denominazione, T2.id_tipologia_crociera, T2.Descrizione, T1.cadenza_giorni, T1.costo_base "
										+ "FROM Crociera T1 "
										+ "INNER JOIN Tipologia_Crociera T2 "
										+ "ON T1.id_tipologia_crociera = T2.id_tipologia_crociera";
								stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
								rs = stmt.executeQuery(query); 
								while (rs.next()) {
									TableItem item = new TableItem(tbl_crociera, SWT.NONE);
									item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
											rs.getString(5), rs.getString(6)});
								}
								tbl_crociera.select(selected);
							}
							catch (SQLException exc) {
								MessageDialog.openError(shell, "Error", 
										"IMPOSSIBILE MODIFICARE I DATI DELLA CROCIERA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
			btn_remove.setBounds(390, 100, 100, 20);
			btn_remove.setText("Cancella");
			btn_remove.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int selected = tbl_crociera.getSelectionIndex();
					if (selected > -1) {
						if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare la crociera selezionata?") == true)
						{
							Connection con = null;
							CallableStatement cstmt = null;
							try {
								con = SQLConnector.getDatabaseConnection();			
								cstmt = con.prepareCall("{call sp_cancellare_crociera(?)}", 
										ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
								cstmt.setInt(1, Integer.parseInt(tbl_crociera.getItem(selected).getText(0))); 
								cstmt.executeUpdate();

								//Update table
								tbl_crociera.removeAll();
								Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
								String  query = "SELECT T1.id_crociera, T1.denominazione, T2.id_tipologia_crociera, T2.Descrizione, T1.cadenza_giorni, T1.costo_base "
										+ "FROM Crociera T1 "
										+ "INNER JOIN Tipologia_Crociera T2 "
										+ "ON T1.id_tipologia_crociera = T2.id_tipologia_crociera";
								ResultSet rs = stmt.executeQuery(query); 
								while (rs.next()) {
									TableItem item = new TableItem(tbl_crociera, SWT.NONE);
									item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
											rs.getString(5), rs.getString(6)});
								}
								btn_clear.notifyListeners(SWT.MouseDown, null);
								selected = selected - 1;
								//tbl_navi.getItem(selected-1).notify();
							}
							catch (SQLException exc) {
								MessageDialog.openError(shell, "Error", 
										"IMPOSSIBILE CANCELLARE LA CROCIERA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
						MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessuna crociera selezionata.");
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

	/****************************************************************************************************************************
	 ******** CHILD 02 **********************************************************************************************************
	 ****************************************************************************************************************************/	  	
	private class ChildShell_2 { 
		public ChildShell_2(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare una nave...");
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

			Button btn_back = new Button(shell, SWT.NONE);
			btn_back.setBounds(0, 150, 50, 20);
			btn_back.setText("Indietro");
			btn_back.addSelectionListener(new SelectionAdapter() {
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
			tc1.setWidth(0);
			tc2.setWidth(100);
			tc3.setWidth(70);
			tc4.setWidth(60);
			tc5.setWidth(70);
			tc6.setWidth(90);
			tc7.setWidth(50);
			tc1.setText("ID");
			tc2.setText("Nome");
			tc3.setText("Stazza");
			tc4.setText("Ponti");
			tc5.setText("Cabine");
			tc6.setText("Passeggeri");
			tc7.setText("Anno");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();			
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_nave, nome, stazza, ponti, cabine, passeggeri, anno FROM Nave "; 
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					TableItem item = new TableItem(tbl_navi, SWT.NONE);
					item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
							rs.getString(6), rs.getString(7)});
				}
			}
			catch (SQLException exc) {
				MessageDialog.openError(shell, "Error", 
						"IMPOSSIBILE OTTENERE LA LISTA DELLE NAVI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
						text_nave_id.setText(tbl_navi.getItem(selected).getText(0));
						text_nave.setText(tbl_navi.getItem(selected).getText(1));
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

	/****************************************************************************************************************************
	 ********* CHILD 03 **********************************************************************************************************
	 ****************************************************************************************************************************/	  	
	private class ChildShell_3 { 
		public ChildShell_3(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Tipologie di Crociera...");
			int w = 500;
			int h = 280;
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

			Label lbl_separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			lbl_separator.setBounds(10, 150, 480, 2);

			//row#1
			Label lbl_tipologia = new Label(shell, SWT.SHADOW_IN);
			lbl_tipologia.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_tipologia.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_tipologia.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_tipologia.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_tipologia.setBounds(10, 160, 90, 20);
			lbl_tipologia.setText(" Tipologia:");	
			Text text_tipologia = new Text(shell, SWT.READ_ONLY);
			text_tipologia.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_tipologia.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_tipologia.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_tipologia.setBounds(110, 160, 160, 20);	  

			Table tbl_crociera = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
			tbl_crociera.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			tbl_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			tbl_crociera.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			tbl_crociera.setBounds(10, 10, 200, 130);
			tbl_crociera.setHeaderVisible(true);
			tbl_crociera.setLinesVisible(true);
			TableColumn tc1 = new TableColumn(tbl_crociera, SWT.CENTER);
			tc1.setWidth(0);
			tc1.setText("ID");
			TableColumn tc2 = new TableColumn(tbl_crociera,SWT.CENTER);
			tc2.setWidth(160);
			tc2.setText("Tipologia");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();			
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_tipologia_crociera, descrizione FROM Tipologia_Crociera "; 
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					TableItem item = new TableItem(tbl_crociera, SWT.NONE);
					item.setText(new String[]{rs.getString(1), rs.getString(2)});
				}
			}
			catch (SQLException exc) {
				MessageDialog.openError(shell, "Error", 
						"IMPOSSIBILE OTTENERE LA LISTA DELLE TIPOLOGIE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

			tbl_crociera.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = tbl_crociera.getSelectionIndex();
					if (selected > -1) {
						//...
					}
					shell.dispose();
				}

				@Override
				public void mouseDown(MouseEvent e) {
					int selected = tbl_crociera.getSelectionIndex();
					if (selected > -1) {
						text_tipologia.setText(tbl_crociera.getItem(selected).getText(1));
					}
				}
			});

			Button btn_back = new Button(shell, SWT.NONE);
			btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
			btn_back.setBounds(430, 220, 60, 20);
			btn_back.setText("Indietro");
			btn_back.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});

			Button btn_clear = new Button(shell, SWT.NONE);
			btn_clear.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
			btn_clear.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
			btn_clear.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
			btn_clear.setBounds(390, 10, 100, 20);
			btn_clear.setText("Svuota campi");
			btn_clear.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					text_tipologia.setText("");
				}
			});

			Button btn_saveas = new Button(shell, SWT.NONE);
			btn_saveas.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
			btn_saveas.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
			btn_saveas.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
			btn_saveas.setBounds(390, 40, 100, 20);
			btn_saveas.setText("Crea nuovo");
			btn_saveas.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {		 			
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere una nuova tipologia?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "INSERT INTO Tipologia_Crociera (descrizione ) ";
							query += "VALUES ('" + text_tipologia.getText() + "')";
							stmt.executeUpdate(query); 	

							//Update table
							tbl_crociera.removeAll();
							stmt.close();
							query = "SELECT id_tipologia_crociera, descrizione FROM Tipologia_Crociera "; 
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_crociera, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2)});
							}
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE AGGIUNGERE NUOVA TIPOLOGIA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
			btn_save.setBounds(390, 70, 100, 20);
			btn_save.setText("Salva");
			btn_save.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					selected = tbl_crociera.getSelectionIndex();
					if (selected > -1) {
						if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare la tipologia selezionata?") == true) {
							Connection con = null;
							Statement stmt = null;
							try {
								con = SQLConnector.getDatabaseConnection();
								stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
								String query = "SELECT id_tipologia_crociera, descrizione FROM Tipologia_Crociera "
										+ "WHERE id_tipologia_crociera = '" + Integer.parseInt(tbl_crociera.getItem(selected).getText(0)) + "'";
								ResultSet rs = stmt.executeQuery(query); 	
								rs.beforeFirst();
								while(rs.next()){
									rs.updateString( "descrizione", text_tipologia.getText());
									rs.updateRow();
								}
								//Update table
								tbl_crociera.removeAll();
								stmt.close();
								rs.close();
								query = "SELECT id_tipologia_crociera, descrizione FROM Tipologia_Crociera "; 
								stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
								rs = stmt.executeQuery(query); 
								while (rs.next()) {
									TableItem item = new TableItem(tbl_crociera, SWT.NONE);
									item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
											rs.getString(5), rs.getString(6)});
								}
								tbl_crociera.select(selected);
							}
							catch (SQLException exc) {
								MessageDialog.openError(shell, "Error", 
										"IMPOSSIBILE MODIFICARE I DATI DELLA TIPOLOGIA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
			btn_remove.setBounds(390, 100, 100, 20);
			btn_remove.setText("Cancella");
			btn_remove.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int selected = tbl_crociera.getSelectionIndex();
					if (selected > -1) {
						if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare la tipologia selezionata?") == true)
						{
							Connection con = null;
							CallableStatement cstmt = null;
							try {
								con = SQLConnector.getDatabaseConnection();			
								cstmt = con.prepareCall("{call sp_cancellare_tipologia_crociera(?)}", 
										ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
								cstmt.setInt(1, Integer.parseInt(tbl_crociera.getItem(selected).getText(0))); 
								cstmt.executeUpdate();

								//Update table
								tbl_crociera.removeAll();
								Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
								String  query = "SELECT id_tipologia_crociera, descrizione FROM Tipologia_Crociera "; 
								ResultSet rs = stmt.executeQuery(query); 
								while (rs.next()) {
									TableItem item = new TableItem(tbl_crociera, SWT.NONE);
									item.setText(new String[]{rs.getString(1), rs.getString(2)});
								}
								btn_clear.notifyListeners(SWT.MouseDown, null);
								selected = selected - 1;
								//tbl_navi.getItem(selected-1).notify();
							}
							catch (SQLException exc) {
								MessageDialog.openError(shell, "Error", 
										"IMPOSSIBILE CANCELLARE LA TIPOLOGIA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
						MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessuna tipologia selezionata.");
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
	
	/****************************************************************************************************************************
	 ******** CHILD 04 **********************************************************************************************************
	 ****************************************************************************************************************************/
	private class ChildShell_4 { 
		public ChildShell_4(Display display, int id_reg_crociera) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Prenotazioni per la crociera "+ id_reg_crociera +"...");
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

			Button btn_back = new Button(shell, SWT.NONE);
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setText("<--");
			btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
			btn_back.setBounds(460, 140, 30, 18); 
			btn_back.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});

			Button btn_anagrafica = new Button(shell, SWT.NONE);
			btn_anagrafica.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
			btn_anagrafica.setBounds(10, 140, 120, 20);
			btn_anagrafica.setText("Gestione anagrafica");
			btn_anagrafica.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					new form_anagrafica(display);
				}
			});

			Table tbl_anagraf = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
			tbl_anagraf.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			tbl_anagraf.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			tbl_anagraf.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			tbl_anagraf.setBounds(10, 10, 480, 125);
			tbl_anagraf.setHeaderVisible(true);
			tbl_anagraf.setLinesVisible(true);
			TableColumn tc1 = new TableColumn(tbl_anagraf, SWT.CENTER);
			TableColumn tc2 = new TableColumn(tbl_anagraf,SWT.CENTER);
			TableColumn tc3 = new TableColumn(tbl_anagraf,SWT.CENTER);
			TableColumn tc4 = new TableColumn(tbl_anagraf,SWT.CENTER);
			TableColumn tc5 = new TableColumn(tbl_anagraf,SWT.CENTER);
			TableColumn tc6 = new TableColumn(tbl_anagraf,SWT.CENTER);
			TableColumn tc7 = new TableColumn(tbl_anagraf,SWT.CENTER);
			tc1.setWidth(70);
			tc2.setWidth(50);
			tc3.setWidth(0);
			tc4.setWidth(70);
			tc5.setWidth(100);
			tc6.setWidth(80);
			tc7.setWidth(80);
			tc1.setText("Nave");
			tc2.setText("Cabina");
			tc3.setText("ID");
			tc4.setText("Nome");
			tc5.setText("Cognome");
			tc6.setText("Partenza");
			tc7.setText("Ritorno");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();			
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT T5.nome, T3.codice, T1.id_anagrafica, T2.nome, T2.cognome, T4.data_inizio, T4.data_fine "
						+ "FROM Prenotazione T1 "
						+ "INNER JOIN Anagrafica T2 "
						+ "ON T1.id_anagrafica = T2.id_anagrafica "
						+ "INNER JOIN Cabina T3 "
						+ "ON T1.id_cabina = T3.id_cabina "
						+ "INNER JOIN Registro_Crociere T4 "
						+ "ON T1.id_registro_crociera = T4.id_registro_crociera "
						+ "INNER JOIN Nave T5 "
						+ "ON T4.id_nave = T5.id_nave "
						+ "WHERE T4.id_registro_crociera = '" + id_reg_crociera + "'"
						+ "ORDER BY T5.nome, T3.codice ";
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					TableItem item = new TableItem(tbl_anagraf, SWT.NONE);
					item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
							rs.getString(5), rs.getString(6), rs.getString(7)});
				}
			}
			catch (SQLException exc) {
				MessageDialog.openError(shell, "Error", 
						"IMPOSSIBILE OTTENERE LA LISTA DEI CLIENTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

			shell.addListener(SWT.Close, new Listener() { 
				@Override 
				public void handleEvent(Event event) {  
					shell.dispose(); 
				} 
			}); 

			Button btn_aggiorna = new Button(shell, SWT.NONE);
			btn_aggiorna.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
			btn_aggiorna.setBounds(150, 140, 70, 20);
			btn_aggiorna.setText("Aggiorna");
			btn_aggiorna.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					tbl_anagraf.removeAll();
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();			
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "SELECT T5.nome, T3.codice, T1.id_anagrafica, T2.nome, T2.cognome, T4.data_inizio, T4.data_fine "
								+ "FROM Prenotazione T1 "
								+ "INNER JOIN Anagrafica T2 "
								+ "ON T1.id_anagrafica = T2.id_anagrafica "
								+ "INNER JOIN Cabina T3 "
								+ "ON T1.id_cabina = T3.id_cabina "
								+ "INNER JOIN Registro_Crociere T4 "
								+ "ON T1.id_registro_crociera = T4.id_registro_crociera "
								+ "INNER JOIN Nave T5 "
								+ "ON T4.id_nave = T5.id_nave "
								+ "WHERE T4.id_registro_crociera = '" + id_reg_crociera + "'"
								+ "ORDER BY T5.nome, T3.codice ";
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_anagraf, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE OTTENERE LA LISTA DEI CLIENTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
			});

			shell.open(); 
		} 
	} 	

	/****************************************************************************************************************************
	 ******** CHILD DATES **********************************************************************************************************
	 ****************************************************************************************************************************/	  	
	private class ChildShell_dates { 
		public ChildShell_dates(Display display, int src) { 
			final Shell shell = new Shell(display, SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare data...");
			int w = 120;
			int h = 40;
			int x = (display.getBounds().width-w)/2;
			int y = (display.getBounds().height-h)/2;
			shell.setSize(w, h);
			shell.setLocation(x, y);

			DateTime dt_1 = new DateTime(shell, SWT.BORDER);
			dt_1.setBounds(5, 5, 80, 24);

			if (src==1) {
				if (text_data_inizio.getText() != "") {
					dt_1.setYear(Integer.parseInt(text_data_inizio.getText().substring(0,4)));
					dt_1.setMonth(Integer.parseInt(text_data_inizio.getText().substring(5,7))-1);
					dt_1.setDay(Integer.parseInt(text_data_inizio.getText().substring(8,10)));
				}
			}
			if (src==2) {
				if (text_data_fine.getText() != "") {
					dt_1.setYear(Integer.parseInt(text_data_fine.getText().substring(0,4)));
					dt_1.setMonth(Integer.parseInt(text_data_fine.getText().substring(5,7))-1);
					dt_1.setDay(Integer.parseInt(text_data_fine.getText().substring(8,10)));
				}
			}

			Button button_1 = new Button(shell, SWT.NONE);
			button_1.setBounds(90, 5, 20, 24);
			button_1.setText("OK");
			button_1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					String newDate;
					if ( ((dt_1.getMonth()+1)>=10) && ((dt_1.getDay()>=10)) ) {
						newDate = (dt_1.getYear()+"-"+(dt_1.getMonth()+1)+"-"+dt_1.getDay());
					}
					else {
						if ( ((dt_1.getMonth()+1)<10) && ((dt_1.getDay()<10)) ) {
							newDate = (dt_1.getYear()+"-0"+(dt_1.getMonth()+1)+"-0"+dt_1.getDay());
						}
						else {
							if ( ((dt_1.getMonth()+1)<10) )
								newDate = (dt_1.getYear()+"-0"+(dt_1.getMonth()+1)+"-"+dt_1.getDay());
							else // ( ((dt_1.getDay()+1)<10) )
								newDate = (dt_1.getYear()+"-"+(dt_1.getMonth()+1)+"-0"+dt_1.getDay());
						}
					}

					if (src==1) {
						if ( (text_data_fine.getText() != "") ) {
							try {
								DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
								date_a = format.parse(""+newDate+"");
								date_z = format.parse(""+text_data_fine.getText()+"");
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							if(date_a.before(date_z))
								text_data_inizio.setText(""+newDate+"");	
							else
								MessageDialog.openError(shell, "Errore", "La data inizio è posteriore alla data fine.");
						} 	
						else
							text_data_inizio.setText(""+newDate+"");
					}
					if (src==2){
						if ( (text_data_inizio.getText() != "") ) {
							try {
								DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
								date_a = format.parse(""+text_data_inizio.getText()+"");
								date_z = format.parse(""+newDate+"");
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							if(date_z.after(date_a))
								text_data_fine.setText(""+newDate+"");	
							else
								MessageDialog.openError(shell, "Errore", "La data fine è precedente alla data inizio.");
						} 
						else
							text_data_fine.setText(""+newDate+"");
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
}