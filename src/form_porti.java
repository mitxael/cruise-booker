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
import org.eclipse.swt.widgets.Combo;
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

public class form_porti { 
	private int selected;
	Text text_porto;
	Text text_porto_id;

	public form_porti(Display display) { 
		final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		shell.setText("Registro dei Porti");
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

		TableViewer tbl_view_reg_porti = new TableViewer(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
		//tbl_view_navi.setContentProvider (new ArrayContentProvider()); 
		//tbl_view_navi.setLabelProvider (new MyLabelProvider()); 		 

		Table tbl_reg_porti = tbl_view_reg_porti.getTable();
		tbl_reg_porti.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		tbl_reg_porti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_reg_porti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_reg_porti.setBounds(10, 10, 340, 110);
		tbl_reg_porti.setHeaderVisible(true);
		tbl_reg_porti.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(tbl_reg_porti, SWT.CENTER);
		TableColumn tc2 = new TableColumn(tbl_reg_porti,SWT.CENTER);
		TableColumn tc3 = new TableColumn(tbl_reg_porti,SWT.PASSWORD | SWT.CENTER);
		TableColumn tc4 = new TableColumn(tbl_reg_porti,SWT.CENTER);
		TableColumn tc5 = new TableColumn(tbl_reg_porti,SWT.CENTER);
		TableColumn tc6 = new TableColumn(tbl_reg_porti,SWT.CENTER);
		tc1.setWidth(0);
		tc2.setWidth(0);
		tc3.setWidth(160);
		tc4.setWidth(50);
		tc5.setWidth(0);
		tc6.setWidth(100);
		tc1.setText("ID");
		tc2.setText("Crociera_ID");
		tc3.setText("Crociera");
		tc4.setText("Tappa");
		tc5.setText("Porto_ID");
		tc6.setText("Porto");

		CellEditor[] tbl10_ce = new CellEditor[3];
		tbl10_ce[0] = new TextCellEditor(tbl_reg_porti);
		tbl10_ce[1] = new CheckboxCellEditor(tbl_reg_porti);
		tbl10_ce[2] = new ComboBoxCellEditor(tbl_reg_porti, new String[] {"A", "B", "C"}, SWT.READ_ONLY);

		Label lbl_separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lbl_separator.setBounds(10, 130, 620, 2);

		//row#1
		Label lbl_crociera = new Label(shell, SWT.SHADOW_IN);
		lbl_crociera.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_crociera.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_crociera.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_crociera.setBounds(10, 140, 80, 25);
		lbl_crociera.setText(" Crociera:");	

		Combo cmb_crociera = new Combo(shell, SWT.READ_ONLY);
		cmb_crociera.setBounds(100, 140, 200, 25);
		cmb_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));

		Combo cmb_crociera_ID = new Combo(shell, SWT.VIRTUAL);
		cmb_crociera_ID.setVisible(false);	

		//row#2
		Label lbl_tappa = new Label(shell, SWT.SHADOW_IN);
		lbl_tappa.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_tappa.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_tappa.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_tappa.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_tappa.setBounds(10, 180, 80, 20);
		lbl_tappa.setText(" Tappa:");	
		Text text_tappa = new Text(shell, SWT.BORDER);
		text_tappa.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_tappa.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_tappa.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_tappa.setBounds(100, 180, 80, 20);

		//row#3
		Label lbl_porto = new Label(shell, SWT.SHADOW_IN);
		lbl_porto.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_porto.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_porto.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_porto.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_porto.setBounds(10, 220, 80, 20);
		lbl_porto.setText(" Porto:");	
		text_porto = new Text(shell, SWT.BORDER);
		text_porto.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_porto.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_porto.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_porto.setBounds(100, 220, 80, 20);			

		Button btn_porto = new Button(shell, SWT.NONE);
		btn_porto.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_porto.setBounds(190, 225, 20, 15);
		btn_porto.setText("...");
		btn_porto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ChildShell_1(display); 
			}
		});

		text_porto_id = new Text(shell, SWT.BORDER);
		text_porto_id.setVisible(false);

		//LOAD COMBOBOX
		Connection con = null;	 		  
		Statement stmt = null;
		try {
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT id_crociera, denominazione FROM Crociera ";  
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				cmb_crociera_ID.add(rs.getString(1));
				cmb_crociera.add(rs.getString(2));
			}
			cmb_crociera.select(0);
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell, "Error", 
					"IMPOSSIBILE OTTENERE I DATI DEI PORTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

		//LOAD ON SELECTION
		cmb_crociera.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Connection con = null;	 		  
				Statement stmt = null;
				try {
					tbl_reg_porti.removeAll(); 
					con = SQLConnector.getDatabaseConnection();	 		  
					stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					String query = "SELECT T1.id_registro_porti, T1.id_crociera, T3.denominazione, T1.tappa,  T1.id_porto, T2.citta "
							+ "FROM Registro_Porti T1 "
							+ "INNER JOIN Porto T2 "
							+ "ON T1.id_porto = T2.id_porto "
							+ "INNER JOIN Crociera T3 "
							+ "ON T1.id_crociera = T3.id_crociera "
							+ "WHERE T1.id_crociera = '" + Integer.parseInt(cmb_crociera_ID.getItem(cmb_crociera.getSelectionIndex()))+ "'";  
					ResultSet rs = stmt.executeQuery(query); 
					while (rs.next()) {
						TableItem item = new TableItem(tbl_reg_porti, SWT.NONE);
						item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), ""+(Integer.parseInt(rs.getString(4))+1)+"", 
								rs.getString(5), rs.getString(6)});
					}
					text_tappa.setText("");
					text_porto_id.setText("");
					text_porto.setText("");
				}
				catch (SQLException exc) {
					MessageDialog.openError(shell, "Error", 
							"IMPOSSIBILE OTTENERE I DATI DEI PORTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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


		//LOAD TABLE
		try {
			tbl_reg_porti.removeAll(); 
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			int id_crociera;
			if (cmb_crociera.getSelectionIndex() < 0)
				id_crociera = 0;
			else
				id_crociera = Integer.parseInt(cmb_crociera_ID.getItem(cmb_crociera.getSelectionIndex()));
			String query = "SELECT T1.id_registro_porti, T1.id_crociera, T3.denominazione, T1.tappa,  T1.id_porto, T2.citta "
					+ "FROM Registro_Porti T1 "
					+ "INNER JOIN Porto T2 "
					+ "ON T1.id_porto = T2.id_porto "
					+ "INNER JOIN Crociera T3 "
					+ "ON T1.id_crociera = T3.id_crociera "
					+ "WHERE T1.id_crociera = '" + id_crociera + "'";  
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				TableItem item = new TableItem(tbl_reg_porti, SWT.NONE);
				item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), ""+(Integer.parseInt(rs.getString(4))+1)+"", 
						rs.getString(5), rs.getString(6)});
			}
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell, "Error", 
					"IMPOSSIBILE OTTENERE I DATI DEL REGISTRO DI PORTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

		tbl_reg_porti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int selected = tbl_reg_porti.getSelectionIndex();
				if (selected > -1) {
					text_tappa.setText(tbl_reg_porti.getItem(selected).getText(3));
					text_porto_id.setText(tbl_reg_porti.getItem(selected).getText(4));
					text_porto.setText(tbl_reg_porti.getItem(selected).getText(5));
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
				text_porto.setText("");
				text_porto_id.setText("");
				text_tappa.setText("");
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
				if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere un nuovo registro di porti?") == true) {
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "INSERT INTO Registro_Porti (id_porto, tappa, id_crociera) ";
						query += "VALUES ('" + text_porto_id.getText() + "','" + (Integer.parseInt(text_tappa.getText())-1) + "','" 
								+ cmb_crociera_ID.getItem(cmb_crociera.getSelectionIndex()) + "')";
						stmt.executeUpdate(query); 	

						//Update table
						tbl_reg_porti.removeAll();
						stmt.close();
						query = "SELECT T1.id_registro_porti, T1.id_crociera, T3.denominazione, T1.tappa,  T1.id_porto, T2.citta "
								+ "FROM Registro_Porti T1 "
								+ "INNER JOIN Porto T2 "
								+ "ON T1.id_porto = T2.id_porto "
								+ "INNER JOIN Crociera T3 "
								+ "ON T1.id_crociera = T3.id_crociera "
								+ "WHERE T1.id_crociera = '" + Integer.parseInt(cmb_crociera_ID.getItem(cmb_crociera.getSelectionIndex()))+ "'";
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_reg_porti, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), ""+(Integer.parseInt(rs.getString(4))+1)+"", 
									rs.getString(5), rs.getString(6)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE AGGIUNGERE NUOVO REGISTRO DI PORTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				selected = tbl_reg_porti.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare l'utente selezionato?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT T1.id_registro_porti, T1.id_crociera, T3.denominazione, T1.tappa,  T1.id_porto, T2.citta "
									+ "FROM Registro_Porti T1 "
									+ "INNER JOIN Porto T2 "
									+ "ON T1.id_porto = T2.id_porto "
									+ "INNER JOIN Crociera T3 "
									+ "ON T1.id_crociera = T3.id_crociera "
									+ "WHERE T1.id_registro_porti = '" + tbl_reg_porti.getItem(selected).getText(0) + "'";
							ResultSet rs = stmt.executeQuery(query); 	
							rs.beforeFirst();
							while(rs.next()){
								rs.updateString( "tappa", ""+(Integer.parseInt(text_tappa.getText())-1)+"");
								rs.updateString( "id_porto", text_porto_id.getText());
								rs.updateRow();
							}
							//Update table
							tbl_reg_porti.removeAll();
							stmt.close();
							rs.close();
							query = "SELECT T1.id_registro_porti, T1.id_crociera, T3.denominazione, T1.tappa,  T1.id_porto, T2.citta "
									+ "FROM Registro_Porti T1 "
									+ "INNER JOIN Porto T2 "
									+ "ON T1.id_porto = T2.id_porto "
									+ "INNER JOIN Crociera T3 "
									+ "ON T1.id_crociera = T3.id_crociera "
									+ "WHERE T1.id_crociera = '" + Integer.parseInt(cmb_crociera_ID.getItem(cmb_crociera.getSelectionIndex()))+ "'"; 
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_reg_porti, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), ""+(Integer.parseInt(rs.getString(4))+1)+"", 
										rs.getString(5), rs.getString(6)});
							}
							tbl_reg_porti.select(selected);
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE MODIFICARE I DATI DEI PORTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				int selected = tbl_reg_porti.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare il registro di porti selezionato?") == true)
					{
						Connection con = null;
						CallableStatement cstmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();			
							cstmt = con.prepareCall("{call sp_cancellare_registro_porti(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, Integer.parseInt(tbl_reg_porti.getItem(selected).getText(0))); 
							cstmt.executeUpdate();

							//Update table
							tbl_reg_porti.removeAll();
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String  query = "SELECT T1.id_registro_porti, T1.id_crociera, T3.denominazione, T1.tappa,  T1.id_porto, T2.citta "
									+ "FROM Registro_Porti T1 "
									+ "INNER JOIN Porto T2 "
									+ "ON T1.id_porto = T2.id_porto "
									+ "INNER JOIN Crociera T3 "
									+ "ON T1.id_crociera = T3.id_crociera "
									+ "WHERE T1.id_crociera = '" + Integer.parseInt(cmb_crociera_ID.getItem(cmb_crociera.getSelectionIndex()))+ "'";
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_reg_porti, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), ""+(Integer.parseInt(rs.getString(4))+1)+"", 
										rs.getString(5), rs.getString(6)});
							}
							btn_clear.notifyListeners(SWT.MouseDown, null);
							selected = selected - 1;
							//tbl_navi.getItem(selected-1).notify();
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE CANCELLARE IL PORTO." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
			shell.setText("Selezionare una porto...");
			int w = 500;
			int h = 230;
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

			Label lbl_citta = new Label(shell, SWT.SHADOW_IN);
			lbl_citta.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_citta.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_citta.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_citta.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_citta.setBounds(10, 170, 50, 20);
			lbl_citta.setText(" Citta:");	
			Text text_citta = new Text(shell, SWT.BORDER);
			text_citta.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_citta.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_citta.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_citta.setBounds(70, 170, 120, 20);

			Label lbl_paese = new Label(shell, SWT.SHADOW_IN);
			lbl_paese.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_paese.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_paese.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_paese.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_paese.setBounds(220, 170, 50, 20);
			lbl_paese.setText(" Paese:");	
			Text text_paese = new Text(shell, SWT.BORDER);
			text_paese.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_paese.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_paese.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_paese.setBounds(280, 170, 120, 20);				 

			Label lbl_separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			lbl_separator.setBounds(10, 160, 480, 2);

			Table tbl_porti = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
			tbl_porti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			tbl_porti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			tbl_porti.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			tbl_porti.setBounds(10, 10, 200, 145);
			tbl_porti.setHeaderVisible(true);
			tbl_porti.setLinesVisible(true);
			TableColumn tc1 = new TableColumn(tbl_porti, SWT.CENTER);
			TableColumn tc2 = new TableColumn(tbl_porti,SWT.CENTER);
			TableColumn tc3 = new TableColumn(tbl_porti,SWT.CENTER);
			tc1.setWidth(0);
			tc2.setWidth(100);
			tc3.setWidth(70);
			tc1.setText("ID");
			tc2.setText("Citta");
			tc3.setText("Paese");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();			
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_porto, citta, paese FROM Porto "; 
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					TableItem item = new TableItem(tbl_porti, SWT.NONE);
					item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3)});
				}
			}
			catch (SQLException exc) {
				MessageDialog.openError(shell, "Error", 
						"IMPOSSIBILE OTTENERE LA LISTA DEI PORTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

			tbl_porti.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					int selected = tbl_porti.getSelectionIndex();
					if (selected > -1) {
						text_citta.setText(tbl_porti.getItem(selected).getText(1));
						text_paese.setText(tbl_porti.getItem(selected).getText(2));
					}
				}
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = tbl_porti.getSelectionIndex();
					if (selected > -1) {
						text_porto_id.setText(tbl_porti.getItem(selected).getText(0));
						text_porto.setText(tbl_porti.getItem(selected).getText(1));
					}
					shell.dispose();
				}
			});

			Button btn_back = new Button(shell, SWT.NONE);
			btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD)); 		         
			btn_back.setBounds(430, 170, 50, 20);
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
					text_citta.setText("");
					text_paese.setText("");
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
					int selected = tbl_porti.getSelectionIndex();
					if (selected > -1) {
						if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare porto selezionato?") == true)
						{
							Connection con = null;
							CallableStatement cstmt = null;
							try {
								con = SQLConnector.getDatabaseConnection();			
								cstmt = con.prepareCall("{call sp_cancellare_porto(?)}", 
										ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
								cstmt.setInt(1, Integer.parseInt(tbl_porti.getItem(selected).getText(0))); 
								cstmt.executeUpdate();

								//Update table
								tbl_porti.removeAll();
								Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
								String  query = "SELECT id_porto, citta, paese FROM Porto ";
								ResultSet rs = stmt.executeQuery(query); 
								while (rs.next()) {
									TableItem item = new TableItem(tbl_porti, SWT.NONE);
									item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3)});
								}
								btn_clear.notifyListeners(SWT.MouseDown, null);
								selected = selected - 1;
								//tbl_navi.getItem(selected-1).notify();
							}
							catch (SQLException exc) {
								MessageDialog.openError(shell, "Error", 
										"IMPOSSIBILE CANCELLARE IL PORTO." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
						MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessun porto selezionata.");
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
					selected = tbl_porti.getSelectionIndex();
					if (selected > -1) {
						if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare il porto selezionato?") == true) {
							Connection con = null;
							Statement stmt = null;
							try {
								con = SQLConnector.getDatabaseConnection();
								stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
								String query = "SELECT id_porto, citta, paese FROM Porto "
										+ "WHERE id_porto = '" + tbl_porti.getItem(selected).getText(0) + "'";
								ResultSet rs = stmt.executeQuery(query); 	
								rs.beforeFirst();
								while(rs.next()){
									rs.updateString( "citta", text_citta.getText());
									rs.updateString( "paese", text_paese.getText());
									rs.updateRow();
								}
								//Update table
								tbl_porti.removeAll();
								stmt.close();
								rs.close();
								query = "SELECT id_porto, citta, paese FROM Porto ";
								stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
								rs = stmt.executeQuery(query); 
								while (rs.next()) {
									TableItem item = new TableItem(tbl_porti, SWT.NONE);
									item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3)});
								}
								tbl_porti.select(selected);
							}
							catch (SQLException exc) {
								MessageDialog.openError(shell, "Error", 
										"IMPOSSIBILE MODIFICARE I DATI DEL PORTO." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

			Button btn_saveas = new Button(shell, SWT.NONE);
			btn_saveas.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
			btn_saveas.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
			btn_saveas.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
			btn_saveas.setBounds(390, 40, 100, 20);
			btn_saveas.setText("Crea nuovo");
			btn_saveas.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {		 			
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere un nuovo registro di porti?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "INSERT INTO Porto (citta, paese) ";
							query += "VALUES ('" + text_citta.getText() + "','" + text_paese.getText() + "')";
							stmt.executeUpdate(query); 	

							//Update table
							tbl_porti.removeAll();
							stmt.close();
							query = "SELECT id_porto, citta, paese FROM Porto ";
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_porti, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3)});
							}
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE AGGIUNGERE NUOVO REGISTRO DI PORTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

