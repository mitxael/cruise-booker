
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

public class form_anagrafica { 
	private int selected;

	public form_anagrafica(Display display) { 
		final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		shell.setText("Gestione dei Clienti");
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

		TableViewer tbl_view_anagrafica = new TableViewer(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
		tbl_view_anagrafica.setContentProvider (new ArrayContentProvider()); 
		tbl_view_anagrafica.setLabelProvider (new MyLabelProvider()); 		 

		Table tbl_anagrafica = tbl_view_anagrafica.getTable();
		tbl_anagrafica.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		tbl_anagrafica.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_anagrafica.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_anagrafica.setBounds(10, 10, 500, 110);
		tbl_anagrafica.setHeaderVisible(true);
		tbl_anagrafica.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(tbl_anagrafica, SWT.CENTER);
		TableColumn tc2 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc3 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc4 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc5 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc6 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc7 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc8 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc9 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc10 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc11 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc12 = new TableColumn(tbl_anagrafica,SWT.CENTER);
		TableColumn tc13 = new TableColumn(tbl_anagrafica,SWT.CENTER);
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

		CellEditor[] tbl10_ce = new CellEditor[3];
		tbl10_ce[0] = new TextCellEditor(tbl_anagrafica);
		tbl10_ce[1] = new CheckboxCellEditor(tbl_anagrafica);
		tbl10_ce[2] = new ComboBoxCellEditor(tbl_anagrafica, new String[] {"A", "B", "C"}, SWT.READ_ONLY);

		Connection con = null;	 		  
		Statement stmt = null;
		try {
			con = SQLConnector.getDatabaseConnection();	 		  
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, "
					+ "indirizzo, cap, citta, cf, paese_residenza, nazionalita FROM Anagrafica ";  
			ResultSet rs = stmt.executeQuery(query); 
			while (rs.next()) {
				TableItem item = new TableItem(tbl_anagrafica, SWT.NONE);
				item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
						rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)});
			}
		}
		catch (SQLException exc) {
			MessageDialog.openError(shell, "Error", 
					"IMPOSSIBILE OTTENERE I DATI DEI CLIENTI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

		Label lbl_cognome = new Label(shell, SWT.SHADOW_IN);
		lbl_cognome.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_cognome.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_cognome.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_cognome.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_cognome.setBounds(230, 140, 80, 20);
		lbl_cognome.setText(" Cognome:");	
		Text text_cognome = new Text(shell, SWT.BORDER);
		text_cognome.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_cognome.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_cognome.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_cognome.setBounds(320, 140, 80, 20);

		Label lbl_nascita = new Label(shell, SWT.SHADOW_IN);
		lbl_nascita.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_nascita.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_nascita.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_nascita.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_nascita.setBounds(450, 140, 80, 20);
		lbl_nascita.setText(" Nascita:");	
		Text text_nascita = new Text(shell, SWT.BORDER);
		text_nascita.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_nascita.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_nascita.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_nascita.setBounds(540, 140, 80, 20);

		//row#2
		Label lbl_telefono = new Label(shell, SWT.SHADOW_IN);
		lbl_telefono.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_telefono.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_telefono.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_telefono.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_telefono.setBounds(10, 170, 80, 20);
		lbl_telefono.setText(" Telefono:");	
		Text text_telefono = new Text(shell, SWT.BORDER);
		text_telefono.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_telefono.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_telefono.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_telefono.setBounds(100, 170, 80, 20);

		Label lbl_cellulare = new Label(shell, SWT.SHADOW_IN);
		lbl_cellulare.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_cellulare.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_cellulare.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_cellulare.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_cellulare.setBounds(230, 170, 80, 20);
		lbl_cellulare.setText(" Cellulare:");	
		Text text_cellulare = new Text(shell, SWT.BORDER);
		text_cellulare.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_cellulare.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_cellulare.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_cellulare.setBounds(320, 170, 80, 20);

		Label lbl_email = new Label(shell, SWT.SHADOW_IN);
		lbl_email.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_email.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_email.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_email.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_email.setBounds(450, 170, 80, 20);
		lbl_email.setText(" Email:");	
		Text text_email = new Text(shell, SWT.BORDER);
		text_email.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_email.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_email.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_email.setBounds(540, 170, 80, 20);

		//row#3
		Label lbl_indirizzo = new Label(shell, SWT.SHADOW_IN);
		lbl_indirizzo.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_indirizzo.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_indirizzo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_indirizzo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_indirizzo.setBounds(10, 200, 80, 20);
		lbl_indirizzo.setText(" Indirizzo:");	
		Text text_indirizzo = new Text(shell, SWT.BORDER);
		text_indirizzo.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_indirizzo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_indirizzo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_indirizzo.setBounds(100, 200, 80, 20);

		Label lbl_cap = new Label(shell, SWT.SHADOW_IN);
		lbl_cap.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_cap.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_cap.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_cap.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_cap.setBounds(230, 200, 80, 20);
		lbl_cap.setText(" CAP:");	
		Text text_cap = new Text(shell, SWT.BORDER);
		text_cap.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_cap.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_cap.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_cap.setBounds(320, 200, 80, 20);

		Label lbl_citta = new Label(shell, SWT.SHADOW_IN);
		lbl_citta.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_citta.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_citta.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_citta.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_citta.setBounds(450, 200, 80, 20);
		lbl_citta.setText(" Citta:");	
		Text text_citta = new Text(shell, SWT.BORDER);
		text_citta.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_citta.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_citta.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_citta.setBounds(540, 200, 80, 20);

		//row#4
		Label lbl_cf = new Label(shell, SWT.SHADOW_IN);
		lbl_cf.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_cf.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_cf.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_cf.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_cf.setBounds(10, 230, 80, 20);
		lbl_cf.setText(" CF:");	
		Text text_cf = new Text(shell, SWT.BORDER);
		text_cf.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_cf.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_cf.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_cf.setBounds(100, 230, 80, 20);

		Label lbl_residenza = new Label(shell, SWT.SHADOW_IN);
		lbl_residenza.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_residenza.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_residenza.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_residenza.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_residenza.setBounds(230, 230, 80, 20);
		lbl_residenza.setText(" Residenza:");	
		Text text_residenza = new Text(shell, SWT.BORDER);
		text_residenza.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_residenza.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_residenza.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_residenza.setBounds(320, 230, 80, 20);

		Label lbl_nazionalita = new Label(shell, SWT.SHADOW_IN);
		lbl_nazionalita.setOrientation(SWT.LEFT_TO_RIGHT);
		lbl_nazionalita.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_nazionalita.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_nazionalita.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_nazionalita.setBounds(450, 230, 80, 20);
		lbl_nazionalita.setText(" Nazionalita:");	
		Text text_nazionalita = new Text(shell, SWT.BORDER);
		text_nazionalita.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_nazionalita.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_nazionalita.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_nazionalita.setBounds(540, 230, 80, 20);

		tbl_anagrafica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int selected = tbl_anagrafica.getSelectionIndex();
				if (selected > -1) {
					text_nome.setText(tbl_anagrafica.getItem(selected).getText(1));	
					text_cognome.setText(tbl_anagrafica.getItem(selected).getText(2));
					text_nascita.setText(tbl_anagrafica.getItem(selected).getText(3));
					text_telefono.setText(tbl_anagrafica.getItem(selected).getText(4));
					text_cellulare.setText(tbl_anagrafica.getItem(selected).getText(5));
					text_email.setText(tbl_anagrafica.getItem(selected).getText(6));
					text_indirizzo.setText(tbl_anagrafica.getItem(selected).getText(7));
					text_cap.setText(tbl_anagrafica.getItem(selected).getText(8));
					text_citta.setText(tbl_anagrafica.getItem(selected).getText(9));
					text_cf.setText(tbl_anagrafica.getItem(selected).getText(10));
					text_residenza.setText(tbl_anagrafica.getItem(selected).getText(11));
					text_nazionalita.setText(tbl_anagrafica.getItem(selected).getText(12));
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
				text_cognome.setText("");
				text_nascita.setText("");
				text_telefono.setText("");
				text_cellulare.setText("");
				text_email.setText("");
				text_indirizzo.setText("");
				text_cap.setText("");
				text_citta.setText("");
				text_cf.setText("");
				text_residenza.setText("");
				text_nazionalita.setText("");
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
				if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler aggiungere un nuovo cliente?") == true) {
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "INSERT INTO anagrafica (nome, cognome, data_nascita, telefono, cellulare, "
								+ "email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) ";
						//query += "VALUES ('" + text_nome.getText() + "','" + text_cognome.getText() + "','" + text_nascita.getText() + "','"
						query += "VALUES ('" + text_nome.getText() + "','" + text_cognome.getText() + "', CONVERT(VARCHAR,'" + text_nascita.getText() + "',103),'"
								+ text_telefono.getText() +"','" + text_cellulare.getText() + "','" + text_email.getText() + "','"
								+ text_indirizzo.getText() +"','" + text_cap.getText() +"','" + text_citta.getText() + "','"
								+ text_cf.getText() +"','" + text_residenza.getText() +"','" + text_nazionalita.getText() + "')";
						stmt.executeUpdate(query); 	

						//Update table
						tbl_anagrafica.removeAll();
						stmt.close();
						query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, "
								+ "indirizzo, cap, citta, cf, paese_residenza, nazionalita FROM Anagrafica ";
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_anagrafica, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
									rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)});
						}
					}
					catch (SQLException exc) {
						MessageDialog.openError(shell, "Error", 
								"IMPOSSIBILE AGGIUNGERE NUOVO CLIENTE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				selected = tbl_anagrafica.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler modificare il cliente selezionato?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, "
									+ "indirizzo, cap, citta, cf, paese_residenza, nazionalita FROM Anagrafica "
									+ "WHERE id_anagrafica = '" + tbl_anagrafica.getItem(selected).getText(0) + "'";
							ResultSet rs = stmt.executeQuery(query); 	
							rs.beforeFirst();
							while(rs.next()){
								rs.updateString( "nome", text_nome.getText());
								rs.updateString( "cognome", text_cognome.getText());
								rs.updateString( "data_nascita", text_nascita.getText());
								rs.updateString( "telefono", text_telefono.getText());
								rs.updateString( "cellulare", text_cellulare.getText());
								rs.updateString( "email", text_email.getText());
								rs.updateString( "indirizzo", text_indirizzo.getText());
								rs.updateString( "cap", text_cap.getText());
								rs.updateString( "citta", text_citta.getText());
								rs.updateString( "cf", text_cf.getText());
								rs.updateString( "paese_residenza", text_residenza.getText());
								rs.updateString( "nazionalita", text_nazionalita.getText());
								rs.updateRow();
							}
							//Update table
							tbl_anagrafica.removeAll();
							stmt.close();
							rs.close();
							query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, "
									+ "indirizzo, cap, citta, cf, paese_residenza, nazionalita FROM Anagrafica ";
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_anagrafica, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
										rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)});
							}
							tbl_anagrafica.select(selected);
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE MODIFICARE I DATI DEL CLIENTE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
				int selected = tbl_anagrafica.getSelectionIndex();
				if (selected > -1) {
					if (MessageDialog.openConfirm(shell, "Conferma", "Sei sicuro di voler cancellare il cliente selezionato?") == true)
					{
						Connection con = null;
						CallableStatement cstmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();			
							cstmt = con.prepareCall("{call sp_cancellare_anagrafica(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, Integer.parseInt(tbl_anagrafica.getItem(selected).getText(0))); 
							cstmt.executeUpdate();

							//Update table
							tbl_anagrafica.removeAll();
							String query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, "
									+ "indirizzo, cap, citta, cf, paese_residenza, nazionalita FROM Anagrafica ";
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {
								TableItem item = new TableItem(tbl_anagrafica, SWT.NONE);
								item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
										rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
										rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)});
							}
							btn_clear.notifyListeners(SWT.MouseDown, null);
							selected = selected - 1;
							//tbl_anagrafica.getItem(selected-1).notify();
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell, "Error", 
									"IMPOSSIBILE CANCELLARE IL CLIENTE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
					MessageDialog.openWarning(shell, "Attenzione", "Impossibile cancellare, nessun cliente selezionato.");
			}
		});
		
		Button btn_filter = new Button(shell, SWT.NONE);
		btn_filter.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btn_filter.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btn_filter.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btn_filter.setBounds(10, 260, 120, 20);
		btn_filter.setText("Filtra risultati...");
		btn_filter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ( (text_nome.getText()!="") || (text_cognome.getText()!="") || (text_email.getText()!="") || (text_cap.getText()!="") ||
					 (text_cellulare.getText()!="") || (text_cf.getText()!="") || (text_citta.getText()!="") || (text_indirizzo.getText()!="") ||
				     (text_nascita.getText()!="") || (text_nazionalita.getText()!="") || (text_residenza.getText()!="") || (text_telefono.getText()!="")) {
					Connection con = null;	 		  
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();	 		  
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, "
								+ "indirizzo, cap, citta, cf, paese_residenza, nazionalita FROM Anagrafica "
								+ "WHERE nome like '%" + text_nome.getText() +"%'"
								+ "AND cognome like '%" + text_cognome.getText() +"%'"
								+ "AND data_nascita like '%" + text_nascita.getText() +"%'"
								+ "AND telefono like '%" + text_telefono.getText() +"%'"
								+ "AND cellulare like '%" + text_cellulare.getText() +"%'"
								+ "AND email like '%" + text_email.getText() +"%'"
								+ "AND indirizzo like '%" + text_indirizzo.getText() +"%'"
								+ "AND cap like '%" + text_cap.getText() +"%'"
								+ "AND citta like '%" + text_citta.getText() +"%'"
								+ "AND cf like '%" + text_cf.getText() +"%'"
								+ "AND paese_residenza like '%" + text_residenza.getText() +"%'"
								+ "AND nazionalita like '%" + text_nazionalita.getText() +"%'";
						ResultSet rs = stmt.executeQuery(query); 
						tbl_anagrafica.removeAll();
						while (rs.next()) {
							TableItem item = new TableItem(tbl_anagrafica, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
									rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)});
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
				}
				else {
					Connection con = null;	 		  
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();	 		  
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, "
								+ "indirizzo, cap, citta, cf, paese_residenza, nazionalita FROM Anagrafica "; 
						ResultSet rs = stmt.executeQuery(query); 
						tbl_anagrafica.removeAll();
						while (rs.next()) {
							TableItem item = new TableItem(tbl_anagrafica, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
									rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
									rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)});
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
		btn_back.setBounds(600, 270, 30, 18);
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

