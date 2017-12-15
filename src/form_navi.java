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

public class form_navi { 
	private int selected;

	public form_navi(Display display) { 
		final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		shell.setText("Gestione delle Navi");
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

		Table tbl_navi = tbl_view_navi.getTable();
		tbl_navi.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		tbl_navi.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_navi.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_navi.setBounds(10, 10, 500, 110);
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

		CellEditor[] tbl10_ce = new CellEditor[3];
		tbl10_ce[0] = new TextCellEditor(tbl_navi);
		tbl10_ce[1] = new CheckboxCellEditor(tbl_navi);
		tbl10_ce[2] = new ComboBoxCellEditor(tbl_navi, new String[] {"A", "B", "C"}, SWT.READ_ONLY);

		Connection con = null;	 		  
		Statement stmt = null;
		try {
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT id_nave, nome, stazza, ponti, cabine, passeggeri, anno FROM Nave ";  
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				TableItem item = new TableItem(tbl_navi, SWT.NONE);
				item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getString(7)});
			}
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell, "Error", 
					"IMPOSSIBILE OTTENERE I DATI DELLE NAVI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

		Label lbl_stazza = new Label(shell, SWT.SHADOW_IN);
		lbl_stazza.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_stazza.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_stazza.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_stazza.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_stazza.setBounds(230, 140, 80, 20);
		lbl_stazza.setText(" Stazza:");	
		Text text_stazza = new Text(shell, SWT.BORDER);
		text_stazza.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_stazza.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_stazza.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_stazza.setBounds(320, 140, 80, 20);

		Label lbl_ponti = new Label(shell, SWT.SHADOW_IN);
		lbl_ponti.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_ponti.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_ponti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_ponti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_ponti.setBounds(450, 140, 80, 20);
		lbl_ponti.setText(" Ponti:");	
		Text text_ponti = new Text(shell, SWT.BORDER);
		text_ponti.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_ponti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_ponti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_ponti.setBounds(540, 140, 80, 20);

		//row#2
		Label lbl_cabine = new Label(shell, SWT.SHADOW_IN);
		lbl_cabine.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_cabine.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_cabine.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_cabine.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_cabine.setBounds(10, 180, 80, 20);
		lbl_cabine.setText(" Cabine:");	
		Text text_cabine = new Text(shell, SWT.BORDER);
		text_cabine.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_cabine.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_cabine.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_cabine.setBounds(100, 180, 80, 20);

		Label lbl_passeggeri = new Label(shell, SWT.SHADOW_IN);
		lbl_passeggeri.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_passeggeri.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_passeggeri.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_passeggeri.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_passeggeri.setBounds(230, 180, 80, 20);
		lbl_passeggeri.setText(" Passeggeri:");	
		Text text_passeggeri = new Text(shell, SWT.BORDER);
		text_passeggeri.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_passeggeri.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_passeggeri.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_passeggeri.setBounds(320, 180, 80, 20);

		Label lbl_anno = new Label(shell, SWT.SHADOW_IN);
		lbl_anno.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_anno.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_anno.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_anno.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_anno.setBounds(450, 180, 80, 20);
		lbl_anno.setText(" Anno:");	
		Text text_anno = new Text(shell, SWT.BORDER);
		text_anno.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_anno.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_anno.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_anno.setBounds(540, 180, 80, 20);

		tbl_navi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int selected = tbl_navi.getSelectionIndex();
				if (selected > -1) {
					text_nome.setText(tbl_navi.getItem(selected).getText(1));	
					text_stazza.setText(tbl_navi.getItem(selected).getText(2));
					text_ponti.setText(tbl_navi.getItem(selected).getText(3));
					text_cabine.setText(tbl_navi.getItem(selected).getText(4));
					text_passeggeri.setText(tbl_navi.getItem(selected).getText(5));
					text_anno.setText(tbl_navi.getItem(selected).getText(6));
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
				text_stazza.setText("");
				text_ponti.setText("");
				text_cabine.setText("");
				text_passeggeri.setText("");
				text_anno.setText("");
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
				if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere una nuova nave?") == true) {
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "INSERT INTO nave (stazza, ponti, cabine, passeggeri, anno, nome) ";
						query += "VALUES ('" + text_stazza.getText() + "','" + text_ponti.getText() + "','" + text_cabine.getText() + "','"
								+ text_passeggeri.getText() +"','" + text_anno.getText() + "','" + text_nome.getText() + "')";
						stmt.executeUpdate(query); 	

						//Update table
						tbl_navi.removeAll();
						stmt.close();
						query = "SELECT id_nave, nome, stazza, ponti, cabine, passeggeri, anno FROM Nave ";
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_navi, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE AGGIUNGERE NUOVA NAVE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				selected = tbl_navi.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare la nave selezionata?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT id_nave, nome, stazza, ponti, cabine, passeggeri, anno FROM Nave ";
							query += "WHERE id_nave = '" + tbl_navi.getItem(selected).getText(0) + "'";
							ResultSet rs = stmt.executeQuery(query); 	
							rs.beforeFirst();
							while(rs.next()){
								rs.updateString( "Nome", text_nome.getText());
								rs.updateString( "Stazza", text_stazza.getText());
								rs.updateString( "Ponti", text_ponti.getText());
								rs.updateString( "Cabine", text_cabine.getText());
								rs.updateString( "Passeggeri", text_passeggeri.getText());
								rs.updateString( "Anno", text_anno.getText());
								rs.updateRow();
							}
							//Update table
							tbl_navi.removeAll();
							stmt.close();
							rs.close();
							query = "SELECT id_nave, nome, stazza, ponti, cabine, passeggeri, anno FROM Nave ";
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_navi, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6), rs.getString(7)});
							}
							tbl_navi.select(selected);
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE MODIFICARE I DATI DELLA NAVE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				int selected = tbl_navi.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare la nave selezionata?") == true)
					{
						Connection con = null;
						CallableStatement cstmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();			
							cstmt = con.prepareCall("{call sp_cancellare_nave(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, Integer.parseInt(tbl_navi.getItem(selected).getText(0))); 
							cstmt.executeUpdate();

							//Update table
							tbl_navi.removeAll();
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String  query = "SELECT id_nave, nome, stazza, ponti, cabine, passeggeri, anno FROM Nave ";
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_navi, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6), rs.getString(7)});
							}
							btn_clear.notifyListeners(SWT.MouseDown, null);
							selected = selected - 1;
							//tbl_navi.getItem(selected-1).notify();
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE CANCELLARE LA NAVE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
					MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessuna nave selezionata.");
			}
		});

		Button btn_filter = new Button(shell, SWT.NONE);
		btn_filter.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btn_filter.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btn_filter.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btn_filter.setBounds(10, 220, 120, 20);
		btn_filter.setText("Filtra risultati...");
		btn_filter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ( (text_nome.getText()!="") || (text_stazza.getText()!="") || (text_ponti.getText()!="") || 
					(text_cabine.getText()!="") || (text_passeggeri.getText()!="") || (text_anno.getText()!="")) {
					Connection con = null;	 		  
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();	 		  
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "SELECT id_nave, nome, stazza, ponti, cabine, passeggeri, anno FROM Nave "
								+ "WHERE nome like '%" + text_nome.getText() +"%'"
								+ "AND stazza like '%" + text_stazza.getText() +"%'"
								+ "AND ponti like '%" + text_ponti.getText() +"%'"
								+ "AND cabine like '%" + text_cabine.getText() +"%'"
								+ "AND passeggeri like '%" + text_passeggeri.getText() +"%'"
								+ "AND anno like '%" + text_anno.getText() +"%'";
						ResultSet rs = stmt.executeQuery(query); 
						tbl_navi.removeAll();
						while (rs.next()) {
							TableItem item = new TableItem(tbl_navi, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE FILTRARE I DATI DELLE NAVI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				else {
					Connection con = null;	 		  
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();	 		  
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "SELECT id_nave, nome, stazza, ponti, cabine, passeggeri, anno FROM Nave ";
						ResultSet rs = stmt.executeQuery(query); 
						tbl_navi.removeAll();
						while (rs.next()) {
							TableItem item = new TableItem(tbl_navi, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE FILTRARE I DATI DEI CLIENTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
					MessageDialog.openWarning(shell, "Attenzione", "Compilare almeno un campo per eseguire il filtro.");
				}
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

}
