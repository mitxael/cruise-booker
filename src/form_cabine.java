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

public class form_cabine { 
	private int selected;

	public form_cabine(Display display) { 
		final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		shell.setText("Gestione delle Cabine");
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

		TableViewer tbl_view_cabine = new TableViewer(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
		tbl_view_cabine.setContentProvider (new ArrayContentProvider()); 
		tbl_view_cabine.setLabelProvider (new MyLabelProvider()); 		 

		Table tbl_cabine = tbl_view_cabine.getTable();
		tbl_cabine.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		tbl_cabine.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_cabine.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_cabine.setBounds(10, 10, 500, 110);
		tbl_cabine.setHeaderVisible(true);
		tbl_cabine.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(tbl_cabine, SWT.CENTER);
		TableColumn tc2 = new TableColumn(tbl_cabine,SWT.CENTER);
		TableColumn tc3 = new TableColumn(tbl_cabine,SWT.CENTER);
		TableColumn tc4 = new TableColumn(tbl_cabine,SWT.CENTER);
		TableColumn tc5 = new TableColumn(tbl_cabine,SWT.CENTER);
		TableColumn tc6 = new TableColumn(tbl_cabine,SWT.CENTER);
		TableColumn tc7 = new TableColumn(tbl_cabine,SWT.CENTER);
		tc1.setWidth(0);
		tc2.setWidth(100);
		tc3.setWidth(70);
		tc4.setWidth(60);
		tc5.setWidth(70);
		tc6.setWidth(0);
		tc7.setWidth(100);
		tc1.setText("ID");
		tc2.setText("Codice");
		tc3.setText("Classe");
		tc4.setText("Posti");
		tc5.setText("Ponte");
		tc6.setText("Nave_ID");
		tc7.setText("Nave");

		CellEditor[] tbl10_ce = new CellEditor[3];
		tbl10_ce[0] = new TextCellEditor(tbl_cabine);
		tbl10_ce[1] = new CheckboxCellEditor(tbl_cabine);
		tbl10_ce[2] = new ComboBoxCellEditor(tbl_cabine, new String[] {"A", "B", "C"}, SWT.READ_ONLY);	 	 

		Label lbl_separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lbl_separator.setBounds(10, 130, 620, 2);

		//row#1
		Label lbl_nave = new Label(shell, SWT.SHADOW_IN);
		lbl_nave.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_nave.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_nave.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_nave.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_nave.setBounds(10, 140, 80, 20);
		lbl_nave.setText(" Nave:");	
		Combo cmb_nave = new Combo(shell, SWT.READ_ONLY);
		cmb_nave.setBounds(100, 140, 120, 25);
		cmb_nave.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));

		Combo cmb_nave_ID = new Combo(shell, SWT.VIRTUAL);
		cmb_nave_ID.setVisible(false);			

		//row#2
		Label lbl_codice = new Label(shell, SWT.SHADOW_IN);
		lbl_codice.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_codice.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_codice.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_codice.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_codice.setBounds(10, 180, 80, 20);
		lbl_codice.setText(" Codice:");	
		Text text_codice = new Text(shell, SWT.BORDER);
		text_codice.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_codice.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_codice.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_codice.setBounds(100, 180, 80, 20);

		Text text_ID = new Text(shell, SWT.VIRTUAL);
		text_ID.setVisible(false);

		Label lbl_classe = new Label(shell, SWT.SHADOW_IN);
		lbl_classe.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_classe.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_classe.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_classe.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_classe.setBounds(230, 180, 80, 20);
		lbl_classe.setText(" Classe:");	
		Text text_classe = new Text(shell, SWT.BORDER);
		text_classe.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_classe.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_classe.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_classe.setBounds(320, 180, 80, 20);

		Label lbl_posti = new Label(shell, SWT.SHADOW_IN);
		lbl_posti.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_posti.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_posti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_posti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_posti.setBounds(450, 180, 80, 20);
		lbl_posti.setText(" Posti:");	
		Text text_posti = new Text(shell, SWT.BORDER);
		text_posti.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_posti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_posti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_posti.setBounds(540, 180, 80, 20);

		//row#3
		Label lbl_ponte = new Label(shell, SWT.SHADOW_IN);
		lbl_ponte.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_ponte.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_ponte.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_ponte.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_ponte.setBounds(10, 220, 80, 20);
		lbl_ponte.setText(" Ponte:");	
		Text text_ponte = new Text(shell, SWT.BORDER);
		text_ponte.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_ponte.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_ponte.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_ponte.setBounds(100, 220, 80, 20);

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
				Connection con = null;	 		  
				Statement stmt = null;
				try {
					tbl_cabine.removeAll(); 
					con = SQLConnector.getDatabaseConnection();	 		  
					stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					String query = "SELECT T1.id_cabina, T1.codice, T1.classe, T1.posti, T1.ponte, T1.id_nave, T2.nome "
							+ "FROM Cabina T1 "
							+ "INNER JOIN Nave T2 "
							+ "ON T1.id_nave = T2.id_nave "
							+ "WHERE T1.id_nave = '" + Integer.parseInt(cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()))+ "'";  
					ResultSet rs = stmt.executeQuery(query); 
					while (rs.next()) {
						TableItem item = new TableItem(tbl_cabine, SWT.NONE);
						item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
								rs.getString(5), rs.getString(6), rs.getString(7)});
					}
					text_ID.setText("");
					text_codice.setText("");
					text_classe.setText("");
					text_posti.setText("");
					text_ponte.setText("");
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
			}
		});

		//LOAD TABLE
		try {
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			int id_nave;
			if (cmb_nave.getSelectionIndex() < 0)
				id_nave = 0;
			else
				id_nave = Integer.parseInt(cmb_nave_ID.getItem(cmb_nave.getSelectionIndex()));
			String query = "SELECT T1.id_cabina, T1.codice, T1.classe, T1.posti, T1.ponte, T1.id_nave, T2.nome "
					+ "FROM Cabina T1 "
					+ "INNER JOIN Nave T2 "
					+ "ON T1.id_nave = T2.id_nave "
					+ "WHERE T1.id_nave = '" + id_nave + "'";  
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				TableItem item = new TableItem(tbl_cabine, SWT.NONE);
				item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getString(7)});
			}
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

		tbl_cabine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int selected = tbl_cabine.getSelectionIndex();
				if (selected > -1) {
					text_ID.setText(tbl_cabine.getItem(selected).getText(0));
					text_codice.setText(tbl_cabine.getItem(selected).getText(1));
					text_classe.setText(tbl_cabine.getItem(selected).getText(2));
					text_posti.setText(tbl_cabine.getItem(selected).getText(3));
					text_ponte.setText(tbl_cabine.getItem(selected).getText(4));
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
				text_codice.setText("");
				text_classe.setText("");
				text_posti.setText("");
				text_ponte.setText("");
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
				if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere una nuova cabina?") == true) {
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "INSERT INTO cabina (classe, posti, ponte, codice, id_nave) ";
						query += "VALUES ('" + text_classe.getText() + "','" + text_posti.getText() + "','" + text_ponte.getText() + "','"
								+ text_codice.getText() + "','" + Integer.parseInt(cmb_nave_ID.getItem(cmb_nave.getSelectionIndex())) + "')";
						stmt.executeUpdate(query); 	

						//Update table
						tbl_cabine.removeAll();
						stmt.close();
						query = "SELECT T1.id_cabina, T1.codice, T1.classe, T1.posti, T1.ponte, T1.id_nave, T2.nome "
								+ "FROM Cabina T1 "
								+ "INNER JOIN Nave T2 "
								+ "ON T1.id_nave = T2.id_nave "
								+ "WHERE T1.id_nave = '" + Integer.parseInt(cmb_nave_ID.getItem(cmb_nave.getSelectionIndex())) + "'"; 
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_cabine, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE AGGIUNGERE NUOVA CABINA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				selected = tbl_cabine.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare la cabina selezionata?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT T1.id_cabina, T1.codice, T1.classe, T1.posti, T1.ponte, T1.id_nave, T2.nome "
									+ "FROM Cabina T1 "
									+ "INNER JOIN Nave T2 "
									+ "ON T1.id_nave = T2.id_nave "
									+ "WHERE T1.id_cabina = '" + text_ID.getText() + "'"; 
							ResultSet rs = stmt.executeQuery(query); 	
							rs.beforeFirst();
							while(rs.next()){
								rs.updateString( "codice", text_codice.getText());
								rs.updateString( "classe", text_classe.getText());
								rs.updateString( "posti", text_posti.getText());
								rs.updateString( "ponte", text_ponte.getText());
								rs.updateRow();
							}
							//Update table
							tbl_cabine.removeAll();
							stmt.close();
							rs.close();
							query = "SELECT T1.id_cabina, T1.codice, T1.classe, T1.posti, T1.ponte, T1.id_nave, T2.nome "
									+ "FROM Cabina T1 "
									+ "INNER JOIN Nave T2 "
									+ "ON T1.id_nave = T2.id_nave "
									+ "WHERE T1.id_nave = '" + Integer.parseInt(cmb_nave_ID.getItem(cmb_nave.getSelectionIndex())) + "'"; 
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_cabine, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6), rs.getString(7)});
							}
							tbl_cabine.select(selected);
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE MODIFICARE I DATI DELLA CABINA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				int selected = tbl_cabine.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare la cabina selezionata?") == true)
					{
						Connection con = null;
						CallableStatement cstmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();			
							cstmt = con.prepareCall("{call sp_cancellare_cabina(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, Integer.parseInt(tbl_cabine.getItem(selected).getText(0))); 
							cstmt.executeUpdate();

							//Update table
							tbl_cabine.removeAll();
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String  query = "SELECT T1.id_cabina, T1.codice, T1.classe, T1.posti, T1.ponte, T1.id_nave, T2.nome "
									+ "FROM Cabina T1 "
									+ "INNER JOIN Nave T2 "
									+ "ON T1.id_nave = T2.id_nave "
									+ "WHERE T1.id_nave = '" + Integer.parseInt(cmb_nave_ID.getItem(cmb_nave.getSelectionIndex())) + "'"; 
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_cabine, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6), rs.getString(7)});
							}
							btn_clear.notifyListeners(SWT.MouseDown, null);
							selected = selected - 1;
							//tbl_navi.getItem(selected-1).notify();
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE CANCELLARE LA CABINA." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
					MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessuna cabina selezionata.");
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
}
