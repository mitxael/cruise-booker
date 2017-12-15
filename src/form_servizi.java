import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
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

public class form_servizi { 
	private int selected;

	public form_servizi(Display display) { 
		final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		shell.setText("Gestione dei Servizi");
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

		TableViewer tbl_view_servizi = new TableViewer(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
		tbl_view_servizi.setContentProvider (new ArrayContentProvider()); 
		tbl_view_servizi.setLabelProvider (new MyLabelProvider()); 		 

		Table tbl_servizi = tbl_view_servizi.getTable();
		tbl_servizi.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		tbl_servizi.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_servizi.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_servizi.setBounds(10, 10, 500, 110);
		tbl_servizi.setHeaderVisible(true);
		tbl_servizi.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(tbl_servizi, SWT.CENTER);
		TableColumn tc2 = new TableColumn(tbl_servizi,SWT.CENTER);
		TableColumn tc3 = new TableColumn(tbl_servizi,SWT.CENTER);
		TableColumn tc4 = new TableColumn(tbl_servizi,SWT.CENTER);
		TableColumn tc5 = new TableColumn(tbl_servizi,SWT.CENTER);
		tc1.setWidth(0);
		tc2.setWidth(300);
		tc3.setWidth(70);
		tc4.setWidth(0);
		tc5.setWidth(100);
		tc1.setText("ID");
		tc2.setText("Descrizione");
		tc3.setText("Costo");
		tc4.setText("ID_nave");
		tc5.setText("Nave");

		CellEditor[] tbl10_ce = new CellEditor[3];
		tbl10_ce[0] = new TextCellEditor(tbl_servizi);
		tbl10_ce[1] = new CheckboxCellEditor(tbl_servizi);
		tbl10_ce[2] = new ComboBoxCellEditor(tbl_servizi, new String[] {"A", "B", "C"}, SWT.READ_ONLY);

		Label lbl_separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lbl_separator.setBounds(10, 130, 620, 2);

		//row#1
		Label lbl_nave = new Label(shell, SWT.SHADOW_IN);
		lbl_nave.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_nave.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_nave.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_nave.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_nave.setBounds(10, 140, 60, 20);
		lbl_nave.setText(" Nave:");	
		Combo cmb_nave = new Combo(shell, SWT.READ_ONLY);
		cmb_nave.setBounds(80, 140, 100, 25);
		cmb_nave.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		Combo cmb_nave_ID = new Combo(shell, SWT.VIRTUAL);
		cmb_nave_ID.setVisible(false);			

		Button btn_nave = new Button(shell, SWT.NONE);
		btn_nave.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btn_nave.setBounds(190, 145, 20, 15);
		btn_nave.setText("...");
		btn_nave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ChildShell_1(display); 
			}
		});
		
		Label lbl_descrizione = new Label(shell, SWT.SHADOW_IN);
		lbl_descrizione.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_descrizione.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_descrizione.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_descrizione.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_descrizione.setBounds(240, 140, 80, 20);
		lbl_descrizione.setText(" Descrizione:");	
		Text text_descrizione = new Text(shell, SWT.BORDER);
		text_descrizione.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_descrizione.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_descrizione.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_descrizione.setBounds(330, 140, 120, 20);

		Label lbl_costo = new Label(shell, SWT.SHADOW_IN);
		lbl_costo.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_costo.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_costo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_costo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_costo.setBounds(480, 140, 50, 20);
		lbl_costo.setText(" Costo:");	
		Text text_costo = new Text(shell, SWT.BORDER);
		text_costo.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_costo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_costo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_costo.setBounds(540, 140, 90, 20);
		
		//LOAD COMBOBOX
		Connection con = null;	 		  
		Statement stmt = null;
		try {
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT id_nave, nome FROM Nave ";  
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				cmb_nave_ID.add(rs.getString(1));
				cmb_nave.add(rs.getString(2));
			}
			cmb_nave.select(0);
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell, "Error", 
					"IMPOSSIBILE OTTENERE I DATI DELLA CABINA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
		
		cmb_nave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cmb_nave_ID.select(cmb_nave.getSelectionIndex());
			}
		});
		
		cmb_nave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Connection con = null;	 		  
				Statement stmt = null;
				try {
					tbl_servizi.removeAll(); 
					con = SQLConnector.getDatabaseConnection();	 		  
					stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					/*int id_nave;
					if (cmb_nave.getSelectionIndex() < 0)
						id_nave = 0;
					else
						id_nave = Integer.parseInt(cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()));*/
					String query = "SELECT T1.id_servizio, T1.descrizione, T1.costo_extra, T1.id_nave, T2.nome "
							+ "FROM Servizio T1 "
							+ "INNER JOIN NAVE T2 "
							+ "ON T1.id_nave = T2.id_nave "
							+ "WHERE T1.id_nave = '" + cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()) + "'"; 
					ResultSet rs = stmt.executeQuery(query); 
					while (rs.next()) {
						TableItem item = new TableItem(tbl_servizi, SWT.NONE);
						item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
								rs.getString(5)});
					}
					text_costo.setText("");
					text_descrizione.setText("");
				}
				catch (SQLException exc) {
					MessageDialog.openError(shell, "Error", 
							"IMPOSSIBILE OTTENERE I DATI DEI SERVIZI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT T1.id_servizio, T1.descrizione, T1.costo_extra, T1.id_nave, T2.nome "
					+ "FROM Servizio T1 "
					+ "INNER JOIN NAVE T2 "
					+ "ON T1.id_nave = T2.id_nave "
					+ "WHERE T1.id_nave = '" + cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()) + "'"; 
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				TableItem item = new TableItem(tbl_servizi, SWT.NONE);
				item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)});
			}
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell, "Error", 
					"IMPOSSIBILE OTTENERE I DATI DEI SERVIZI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
		
		tbl_servizi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int selected = tbl_servizi.getSelectionIndex();
				if (selected > -1) {
					text_descrizione.setText(tbl_servizi.getItem(selected).getText(1));	
					text_costo.setText(tbl_servizi.getItem(selected).getText(2));
					cmb_nave.select(cmb_nave_ID.indexOf(tbl_servizi.getItem(selected).getText(4)));
					cmb_nave_ID.select(cmb_nave.getSelectionIndex());
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
				text_descrizione.setText("");
				text_costo.setText("");
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
				if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere un nuovo servizio?") == true) {
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "INSERT INTO servizio (descrizione, costo_extra, id_nave) ";
						query += "VALUES ('" + text_descrizione.getText() + "','" + text_costo.getText() + "','" + 
								cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()).toString() + "')";
						stmt.executeUpdate(query); 	
					
						//Update table
						tbl_servizi.removeAll();
						stmt.close();
						query = "SELECT T1.id_servizio, T1.descrizione, T1.costo_extra, T1.id_nave, T2.nome "
								+ "FROM Servizio T1 "
								+ "INNER JOIN NAVE T2 "
								+ "ON T1.id_nave = T2.id_nave "
								+ "WHERE T1.id_nave = '" + cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()) + "'"; 
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_servizi, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE AGGIUNGERE NUOVO SERVIZIO." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				selected = tbl_servizi.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare il servizio selezionato?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT T1.id_servizio, T1.descrizione, T1.costo_extra, T1.id_nave, T2.nome as xxx "
									+ "FROM (Servizio T1 "
									+ "INNER JOIN Nave T2 "
									+ "ON T1.id_nave = T2.id_nave) "
									+ "WHERE T1.id_servizio = '" + tbl_servizi.getItem(selected).getText(0) + "'";
							ResultSet rs = stmt.executeQuery(query); 	
							rs.beforeFirst();
							while(rs.next()){
								rs.updateString( "descrizione", text_descrizione.getText());
								rs.updateString( "costo_extra", text_costo.getText());
								rs.updateString( "id_nave", cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()).toString());
								rs.updateRow();
							}
							//Update table
							tbl_servizi.removeAll();
							stmt.close();
							rs.close();
							query = "SELECT T1.id_servizio, T1.descrizione, T1.costo_extra, T1.id_nave, T2.nome "
									+ "FROM Servizio T1 "
									+ "INNER JOIN NAVE T2 "
									+ "ON T1.id_nave = T2.id_nave "
									+ "WHERE T1.id_nave = '" + cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()) + "'"; 
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_servizi, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5)});
							}
							tbl_servizi.select(selected);
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE MODIFICARE I DATI DEL SERVIZIO." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				int selected = tbl_servizi.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare il servizio selezionato?") == true)
					{
						Connection con = null;
						CallableStatement cstmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();			
							cstmt = con.prepareCall("{call sp_cancellare_servizio(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, Integer.parseInt(tbl_servizi.getItem(selected).getText(0))); 
							cstmt.executeUpdate();

							//Update table
							tbl_servizi.removeAll();
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String  query = "SELECT T1.id_servizio, T1.descrizione, T1.costo_extra, T1.id_nave, T2.nome "
									+ "FROM Servizio T1 "
									+ "INNER JOIN NAVE T2 "
									+ "ON T1.id_nave = T2.id_nave "
									+ "WHERE T1.id_nave = '" + cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()) + "'";   
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_servizi, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5)});
							}
							btn_clear.notifyListeners(SWT.MouseDown, null);
							selected = selected - 1;
							//tbl_servizi.getItem(selected-1).notify();
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE CANCELLARE IL SERVIZIO." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
					MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessun servizio selezionato.");
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

	private class MyLabelProvider extends LabelProvider implements ITableLabelProvider { 
		public Image getColumnImage (Object element, int columnIndex) { 
			return null; 
		} 
		@Override
		public String getColumnText(Object arg0, int arg1) {
			return null;
		} 
	}	

	/****************************************************************************************************************************
	 ******** CHILD 01 **********************************************************************************************************
	 ****************************************************************************************************************************/	  	
	private class ChildShell_1 { 
		public ChildShell_1(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Informazione sulle navi...");
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
					item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
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
