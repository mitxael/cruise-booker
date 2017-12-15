import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
import org.eclipse.swt.SWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.wb.swt.SWTResourceManager;

public class form_prenotazioni {

	protected Shell shell_prenotazioni;
	private int id_prenotazione=0;
	private Text text_annotazioni;
	private Button btn_prenota;
	private Button btn_crociera;
	private Button btn_cabina;
	private Label lbl_servizi;
	private Button btn_servizi_add;
	private Button btn_servizi_rem;
	private Label lbl_nave;
	private Label lbl_partenza;
	private Text text_partenza;
	private Label lbl_ritorno;
	private Text text_ritorno;
	private Label lbl_costobase;
	private Text text_costobase;
	private Text text_costoextra;
	private Text text_totale;
	private Label lbl_totale;
	private Label lbl_itinerario;
	private Table tbl_servizi;
	private Table tbl_nave;
	private Table tbl_cabina;
	private Table tbl_cliente;
	private Table tbl_crociera;
	private Table tbl_itinerario;
	private Text text_data1;
	private Text text_data2;
	private Date date_a;
	private Date date_z;
	private DateFormat dateFormat_1 = new SimpleDateFormat("yyyy-MM-dd");//new SimpleDateFormat("dd/MM/yyyy");
	private Date date_1 = new Date();
	private Calendar calendar_1 = Calendar.getInstance(); 

	public static void main(String[] args) {
		try {
			form_prenotazioni window = new form_prenotazioni();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(display);
		shell_prenotazioni.open();
		shell_prenotazioni.layout();
		while (!shell_prenotazioni.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents(Display display) {
		shell_prenotazioni = new Shell(SWT.TITLE | SWT.BORDER | SWT.PRIMARY_MODAL);
		shell_prenotazioni.setText("Sistema di Prenotazione");
		shell_prenotazioni.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		int w = 450;
		int h = 300;
		int x = (display.getBounds().width-w)/2;
		int y = (display.getBounds().height-h)/2;
		shell_prenotazioni.setSize(601, 347);
		shell_prenotazioni.setLocation(x, y);

		Image origImage = new Image(display, System.getProperty("user.dir").replace('\\', '/') + "/res/bg_water.jpg");
		Image scaledImage = null; 
		double zoom = 1d/2; 
		final int width = origImage.getBounds().width; 
		final int height = origImage.getBounds().height; 
		scaledImage = new Image(Display.getDefault(), origImage.getImageData().scaledTo((int)(width * zoom),(int)(height * zoom))); 
		shell_prenotazioni.setBackgroundImage(scaledImage);

		lbl_itinerario = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_itinerario.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_itinerario.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_itinerario.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_itinerario.setBounds(385, 67, 60, 15);
		lbl_itinerario.setText("Itinerario");

		Button btn_back = new Button(shell_prenotazioni, SWT.NONE);
		btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setText("<--");
		btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
		btn_back.setBounds(556, 294, 30, 18);
		btn_back.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//form_main fm = new form_main();
				shell_prenotazioni.dispose();
				//fm.open();
			}
		});
		/*GridLayout gl = new GridLayout();
		gl.numColumns = 3;
		table.setLayout(gl);
		GridData gd = GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 3;
		table.setLayoutData(gd);*/

		Label lbl_cliente = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_cliente.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_cliente.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_cliente.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_cliente.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_cliente.setBounds(10, 13, 64, 15);
		lbl_cliente.setText("Cliente");

		Label lbl_crociera = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_crociera.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_crociera.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_crociera.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_crociera.setBounds(337, 13, 60, 15);
		lbl_crociera.setText("Crociera");

		Label lbl_cabina = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_cabina.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_cabina.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_cabina.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_cabina.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_cabina.setBounds(337, 41, 60, 15);
		lbl_cabina.setText("Cabina");

		Label lbl_annotazioni = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_annotazioni.setText("Annotazioni");
		lbl_annotazioni.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_annotazioni.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_annotazioni.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_annotazioni.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_annotazioni.setBounds(10, 220, 76, 15);

		Label lbl_itinerario_pic = new Label(shell_prenotazioni, SWT.NONE);
		lbl_itinerario_pic.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_itinerario_pic.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lbl_itinerario_pic.setBounds(385, 67, 201, 110);

		tbl_itinerario = new Table(shell_prenotazioni,SWT.BORDER |SWT.FULL_SELECTION|SWT.MULTI);
		tbl_itinerario.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_itinerario.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_itinerario.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		tbl_itinerario.setBounds(385, 177, 201, 85);
		tbl_itinerario.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(tbl_itinerario, SWT.CENTER);
		TableColumn tc2 = new TableColumn(tbl_itinerario,SWT.CENTER);
		TableColumn tc3 = new TableColumn(tbl_itinerario,SWT.CENTER);
		tc1.setWidth(35);
		tc2.setWidth(71);
		tc3.setWidth(69);
		tc1.setText("No.");
		tc2.setText("Citta");
		tc3.setText("Paese");	

		text_annotazioni = new Text(shell_prenotazioni, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text_annotazioni.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_annotazioni.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_annotazioni.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_annotazioni.setBounds(10, 237, 230, 25);

		btn_prenota = new Button(shell_prenotazioni, SWT.BORDER);
		btn_prenota.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (id_prenotazione == 0) { //PRENOTA!
					if (tbl_cliente.getItem(0).getText() != "" && tbl_crociera.getItem(0).getText() != "") {
						if (MessageDialog.openConfirm(shell_prenotazioni, "", "Confermi l'inserimento della prenotazione nel sistema?") == true) {
							// **************** INSERT RESERVATION
							Connection con = null;
							CallableStatement cstmt = null;
							try {
								con = SQLConnector.getDatabaseConnection();
								cstmt = con.prepareCall(
										"{call sp_inserire_prenotazione(?, ?, ?, ?, ?)}",
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
								cstmt.setInt(1, Integer.parseInt(tbl_cliente.getItem(0).getText())); 
								cstmt.setInt(2, Integer.parseInt(tbl_crociera.getItem(0).getText()));
								if (tbl_cabina.getItem(0).getText() != "")
									cstmt.setInt(3, Integer.parseInt(tbl_cabina.getItem(0).getText()));
								else
									cstmt.setNull(3, Types.NULL);
								cstmt.setString(4, text_annotazioni.getText());
								cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
								cstmt.executeUpdate();
								id_prenotazione = cstmt.getInt(5);
								MessageDialog.openInformation(shell_prenotazioni, "", "La prenotazione è stata inserita con il codice di riferimento #" + id_prenotazione);
							}
							catch (SQLException exc) {
								MessageDialog.openError(shell_prenotazioni, "Error", 
										"IMPOSSIBILE EFFETTUARE PRENOTAZIONE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
							// **************** INSERT SERVICES		
							if (id_prenotazione > 0) {
								for (int i = 0; i < tbl_servizi.getItemCount(); i++) {
									try {
										con = SQLConnector.getDatabaseConnection();
										cstmt = con.prepareCall(
												"{call sp_registra_servizio(?, ?)}",
												ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);
										cstmt.setInt(1, id_prenotazione);									//id_prenotazione
										cstmt.setInt(2, Integer.parseInt(tbl_servizi.getItem(i).getText(0)));	//id_servizio
										cstmt.executeUpdate(); //cstmt.executeQuery();
									}
									catch (Exception exc) {
										MessageDialog.openError(shell_prenotazioni, "Error", "SQL connection not performed.");
										return;
									}
									finally{ 
										try{ 
											if(cstmt!=null) 
												cstmt.close(); 
											if(con!=null) 
												con.close(); 
										} catch(Exception exc){} 
									}
								}
							}
						}
						else
							MessageDialog.openError(shell_prenotazioni, "Attenzione", "Cliente e/o Crociera non selezionato.");
					}
				}
				else {//SALVA
					if (MessageDialog.openConfirm(shell_prenotazioni, "Conferma", "Confermi le modifiche effettuate nella prenotazione?") == true) {
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							//AGGIORNA PRENOTAZIONE
							String query = "SELECT T1.id_prenotazione, T1.id_anagrafica, T2.nome, T2.cognome, T1.id_registro_crociera, T4.denominazione, "
									+ "T1.id_cabina, T5.codice, T1.costo_totale, T3.data_inizio, T3.data_fine, T4.id_crociera, T1.annotazioni, T4.costo_base, "
									+ " T6.id_nave, T6.nome, T7.descrizione, T5.posti, T5.classe "
									+ "FROM Prenotazione T1 "
									+ "INNER JOIN Anagrafica T2 "
									+ "ON T1.id_anagrafica = T2.id_anagrafica "
									+ "INNER JOIN Registro_Crociere T3 "
									+ "ON T1.id_registro_crociera = T3.id_registro_crociera "
									+ "INNER JOIN Crociera T4 "
									+ "ON T3.id_crociera = T4.id_crociera "
									+ "INNER JOIN Cabina T5 "
									+ "ON T1.id_cabina = T5.id_cabina "
									+ "INNER JOIN Nave T6 "
									+ "ON T3.id_nave = T6.id_nave "
									+ "INNER JOIN Tipologia_crociera T7 "
									+ "ON T4.id_tipologia_crociera = T7. id_tipologia_crociera "
									+ "WHERE T1.id_prenotazione = '" + id_prenotazione + "'";
							ResultSet rs = stmt.executeQuery(query); 	
							rs.beforeFirst();
							while(rs.next()){
								rs.updateString( "id_anagrafica", tbl_cliente.getItem(0).getText(0));
								rs.updateString( "id_registro_crociera", tbl_crociera.getItem(0).getText(0));
								rs.updateString( "id_cabina", tbl_cabina.getItem(0).getText(0));
								rs.updateString( "annotazioni", text_annotazioni.getText());
								rs.updateRow();
							}
							//CANCELLA VECCHI SERVIZI
							CallableStatement cstmt = con.prepareCall("{call sp_cancellare_registro_servizi(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, id_prenotazione);
							cstmt.executeUpdate(); 
							//AGGIORNA SERVIZI
							for (int i = 0; i < tbl_servizi.getItemCount(); i++) {
								try {
									con = SQLConnector.getDatabaseConnection();
									cstmt = con.prepareCall(
											"{call sp_registra_servizio(?, ?)}",
											ResultSet.TYPE_SCROLL_INSENSITIVE,
											ResultSet.CONCUR_READ_ONLY);
									cstmt.setInt(1, id_prenotazione);									//id_prenotazione
									cstmt.setInt(2, Integer.parseInt(tbl_servizi.getItem(i).getText(0)));	//id_servizio
									cstmt.executeUpdate(); //cstmt.executeQuery();
								}
								catch (Exception exc) {
									MessageDialog.openError(shell_prenotazioni, "Error", "SQL connection not performed.");
									return;
								}
								finally{ 
									try{ 
										if(cstmt!=null) 
											cstmt.close(); 
										if(con!=null) 
											con.close(); 
									} catch(Exception exc){} 
								}
							}
							MessageDialog.openInformation(shell_prenotazioni, "", "La prenotazione con codice di riferimento #" + id_prenotazione + " è stata modificata.");
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell_prenotazioni, "Error", 
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
					}
				}
			}
		});
		btn_prenota.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_prenota.setBackground(SWTResourceManager.getColor(SWT.COLOR_MAGENTA));
		btn_prenota.setText("Prenota/Salva");
		btn_prenota.setFont(SWTResourceManager.getFont("Rockwell Condensed", 16, SWT.BOLD));
		btn_prenota.setBounds(261, 280, 133, 33);

		Button btn_cliente = new Button(shell_prenotazioni, SWT.NONE);
		btn_cliente.setBounds(297, 13, 21, 15);
		btn_cliente.setText("...");
		btn_cliente.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ChildShell_1(display); 
			}
			@Override 
			public void widgetDefaultSelected(SelectionEvent e) { 
				widgetSelected(e); 
			} 
		});

		btn_crociera = new Button(shell_prenotazioni, SWT.NONE);
		btn_crociera.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ChildShell_2(display);
			}
			@Override 
			public void widgetDefaultSelected(SelectionEvent e) { 
				widgetSelected(e); 
			} 
		});
		btn_crociera.setText("...");
		btn_crociera.setBounds(565, 13, 21, 15);

		btn_cabina = new Button(shell_prenotazioni, SWT.NONE);
		btn_cabina.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tbl_crociera.getItem(0).getText() != "") {
					new ChildShell_3(display);
				}
				else
					MessageDialog.openWarning(shell_prenotazioni, "Attenzione", "Selezionare prima una crociera.");
			}
			@Override 
			public void widgetDefaultSelected(SelectionEvent e) { 
				widgetSelected(e); 
			} 
		});
		btn_cabina.setText("...");
		btn_cabina.setBounds(565, 41, 21, 15);

		lbl_servizi = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_servizi.setText("Servizi");
		lbl_servizi.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_servizi.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_servizi.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_servizi.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_servizi.setBounds(10, 104, 76, 15);

		btn_servizi_add = new Button(shell_prenotazioni, SWT.NONE);
		btn_servizi_add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tbl_crociera.getItem(0).getText() != "") {
					new ChildShell_4(display);
				}
				else
					MessageDialog.openWarning(shell_prenotazioni, "Attenzione", "Selezionare prima una crociera."); 
			}
			@Override 
			public void widgetDefaultSelected(SelectionEvent e) { 
				widgetSelected(e); 
			}
		});
		btn_servizi_add.setText("+");
		btn_servizi_add.setBounds(219, 128, 21, 38);

		btn_servizi_rem = new Button(shell_prenotazioni, SWT.NONE);
		btn_servizi_rem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selected = tbl_servizi.getSelectionIndex();
				if (selected > -1) {
					double cost = Double.parseDouble(tbl_servizi.getItem(selected).getText(2));
					tbl_servizi.remove(selected);
					double temp_extra = Double.parseDouble(text_costoextra.getText()) - cost;
					text_costoextra.setText(Double.toString(temp_extra));
					double temp_total = Double.parseDouble(text_totale.getText()) - cost;
					text_totale.setText(Double.toString(temp_total));
				}
				else
					MessageDialog.openWarning(shell_prenotazioni, "Attenzione", "Nessun servizio selezionato.");
			}
		});
		btn_servizi_rem.setText("-");
		btn_servizi_rem.setBounds(219, 173, 21, 38);

		Image _origImage = new Image(display, System.getProperty("user.dir").replace('\\', '/') + "/res/bg_itinerary_mediterranean.jpg");
		Image _scaledImage = null; 
		double _zoom = 1d/1.4; 
		final int _width = _origImage.getBounds().width; 
		final int _height = _origImage.getBounds().height; 
		_scaledImage = new Image(Display.getDefault(), _origImage.getImageData().scaledTo((int)(_width * _zoom),(int)(_height * _zoom)));
		lbl_itinerario_pic.setImage(_scaledImage);

		lbl_nave = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_nave.setText("Nave");
		lbl_nave.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_nave.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_nave.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_nave.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_nave.setBounds(10, 41, 64, 15);

		lbl_partenza = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_partenza.setText("Partenza");
		lbl_partenza.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_partenza.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_partenza.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_partenza.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_partenza.setBounds(10, 70, 64, 15);

		text_partenza = new Text(shell_prenotazioni, SWT.BORDER | SWT.READ_ONLY);
		text_partenza.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_partenza.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_partenza.setEditable(false);
		text_partenza.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_partenza.setBounds(86, 68, 70, 21);

		lbl_ritorno = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_ritorno.setText("Ritorno");
		lbl_ritorno.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_ritorno.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_ritorno.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_ritorno.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_ritorno.setBounds(176, 70, 64, 15);

		text_ritorno = new Text(shell_prenotazioni, SWT.BORDER | SWT.READ_ONLY);
		text_ritorno.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_ritorno.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_ritorno.setEditable(false);
		text_ritorno.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_ritorno.setBounds(249, 65, 70, 21);

		lbl_costobase = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_costobase.setText("Costo base");
		lbl_costobase.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_costobase.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_costobase.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_costobase.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_costobase.setBounds(297, 108, 64, 15);

		text_costobase = new Text(shell_prenotazioni, SWT.BORDER);
		text_costobase.setOrientation(SWT.RIGHT_TO_LEFT);
		text_costobase.setText("0");
		text_costobase.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_costobase.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_costobase.setEditable(false);
		text_costobase.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_costobase.setBounds(275, 126, 86, 21);

		Label label = new Label(shell_prenotazioni, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label.setBounds(10, 92, 354, 15);

		Label label_1 = new Label(shell_prenotazioni, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_1.setBounds(374, 99, 9, 162);

		Label lbl_costoextra = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_costoextra.setText("Costo extra");
		lbl_costoextra.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_costoextra.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_costoextra.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_costoextra.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_costoextra.setBounds(297, 166, 64, 15);

		text_costoextra = new Text(shell_prenotazioni, SWT.BORDER);
		text_costoextra.setOrientation(SWT.RIGHT_TO_LEFT);
		text_costoextra.setText("0");
		text_costoextra.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_costoextra.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_costoextra.setEditable(false);
		text_costoextra.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		text_costoextra.setBounds(275, 184, 86, 21);

		text_totale = new Text(shell_prenotazioni, SWT.BORDER);
		text_totale.setOrientation(SWT.RIGHT_TO_LEFT);
		text_totale.setText("0");
		text_totale.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_totale.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		text_totale.setEditable(false);
		text_totale.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		text_totale.setBounds(275, 238, 86, 21);

		lbl_totale = new Label(shell_prenotazioni, SWT.SHADOW_IN);
		lbl_totale.setText("Totale");
		lbl_totale.setOrientation(SWT.RIGHT_TO_LEFT);
		lbl_totale.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_totale.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_totale.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lbl_totale.setBounds(297, 221, 64, 15);

		tbl_cabina = new Table(shell_prenotazioni, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tbl_cabina.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		tbl_cabina.setLinesVisible(true);
		tbl_cabina.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_cabina.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_cabina.setBounds(407, 37, 152, 22);    
		TableColumn t3_c1 = new TableColumn(tbl_cabina, SWT.CENTER);
		t3_c1.setWidth(0);
		TableColumn t3_c2 = new TableColumn(tbl_cabina, SWT.CENTER);
		t3_c2.setWidth(40);
		TableColumn t3_c3 = new TableColumn(tbl_cabina, SWT.CENTER);
		t3_c3.setWidth(55);
		TableColumn t3_c4 = new TableColumn(tbl_cabina, SWT.CENTER);
		t3_c4.setWidth(35);
		TableItem tableItem_3 = new TableItem(tbl_cabina, SWT.NONE);
		tableItem_3.setText("");

		tbl_cliente = new Table(shell_prenotazioni, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tbl_cliente.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE)); //COLOR_TRANSPARENT
		tbl_cliente.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_cliente.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_cliente.setBounds(86, 10, 206, 22);
		tbl_cliente.setLinesVisible(true);
		TableColumn tableColumn_3 = new TableColumn(tbl_cliente, SWT.CENTER);
		tableColumn_3.setWidth(0);
		TableColumn tableColumn_4 = new TableColumn(tbl_cliente, SWT.CENTER);
		tableColumn_4.setWidth(80);
		TableColumn tableColumn_5 = new TableColumn(tbl_cliente, SWT.CENTER);
		tableColumn_5.setWidth(100);
		TableItem tableItem_4 = new TableItem(tbl_cliente, SWT.NONE);
		tableItem_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		tableItem_4.setText("");

		tbl_crociera = new Table(shell_prenotazioni, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tbl_crociera.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		tbl_crociera.setLinesVisible(true);
		tbl_crociera.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_crociera.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_crociera.setBounds(407, 10, 152, 22);

		TableColumn tableColumn_6 = new TableColumn(tbl_crociera, SWT.CENTER);
		tableColumn_6.setWidth(0);

		TableColumn tableColumn_7 = new TableColumn(tbl_crociera, SWT.CENTER);
		tableColumn_7.setWidth(130);

		TableItem tableItem = new TableItem(tbl_crociera, SWT.NONE);
		tableItem.setText(new String[] {});
		tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		tableItem.setText("");

		tbl_servizi = new Table(shell_prenotazioni, SWT.BORDER |SWT.FULL_SELECTION| SWT.FULL_SELECTION); 
		tbl_servizi.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND)); //COLOR_TRANSPARENT
		tbl_servizi.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_servizi.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		tbl_servizi.setBounds(10, 127, 207, 85);
		tbl_servizi.setLinesVisible(true);	
		TableColumn tbl_s_tc1 = new TableColumn(tbl_servizi, SWT.CENTER);
		TableColumn tbl_s_tc2 = new TableColumn(tbl_servizi,SWT.CENTER);
		TableColumn tbl_s_tc3 = new TableColumn(tbl_servizi,SWT.CENTER);
		tbl_s_tc1.setWidth(0);
		tbl_s_tc2.setWidth(110);
		tbl_s_tc3.setWidth(45);
		tbl_s_tc1.setText("ID");
		tbl_s_tc2.setText("Nome");
		tbl_s_tc3.setText("Costo");

		tbl_nave = new Table(shell_prenotazioni, SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
		tbl_nave.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		tbl_nave.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbl_nave.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		tbl_nave.setBounds(86, 37, 233, 22);
		tbl_nave.setLinesVisible(true);
		TableColumn t2_c1 = new TableColumn(tbl_nave, SWT.CENTER);
		TableColumn t2_c2 = new TableColumn(tbl_nave,SWT.CENTER);
		TableColumn t2_c3 = new TableColumn(tbl_nave,SWT.CENTER);
		t2_c1.setWidth(0);
		t2_c2.setWidth(100); 		
		t2_c3.setWidth(91);	
		TableItem tableItem_2 = new TableItem(tbl_nave, SWT.NONE);
		tableItem_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		tableItem_2.setText("");

		Button btn_apri = new Button(shell_prenotazioni, SWT.BORDER);
		btn_apri.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				id_prenotazione=0;
				tbl_cliente.clearAll();
				tbl_cabina.clearAll();
				tbl_crociera.clearAll();
				tbl_nave.clearAll();
				tbl_servizi.removeAll();
				tbl_itinerario.removeAll();
				text_annotazioni.setText("");
				text_costobase.setText("");
				text_costoextra.setText("");
				text_totale.setText("");
				text_partenza.setText("");
				text_ritorno.setText("");

				new ChildShell_5(display);
			}
		});
		btn_apri.setText("Apri");
		btn_apri.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_apri.setFont(SWTResourceManager.getFont("Rockwell Condensed", 16, SWT.BOLD));
		btn_apri.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
		btn_apri.setBounds(137, 280, 95, 33);

		Button btn_nuovo = new Button(shell_prenotazioni, SWT.BORDER);
		btn_nuovo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				id_prenotazione=0;
				tbl_cliente.clearAll();
				tbl_cabina.clearAll();
				tbl_crociera.clearAll();
				tbl_nave.clearAll();
				tbl_servizi.clearAll();
				tbl_itinerario.clearAll();
				text_annotazioni.setText("");
				text_costobase.setText("");
				text_costoextra.setText("");
				text_totale.setText("");
				text_partenza.setText("");
				text_ritorno.setText("");
			}
		});
		btn_nuovo.setText("Nuovo");
		btn_nuovo.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_nuovo.setFont(SWTResourceManager.getFont("Rockwell Condensed", 16, SWT.BOLD));
		btn_nuovo.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btn_nuovo.setBounds(12, 280, 95, 33);

		Label label_2 = new Label(shell_prenotazioni, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_2.setBounds(10, 266, 576, 8);

		Button btn_cancella = new Button(shell_prenotazioni, SWT.BORDER);
		btn_cancella.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (id_prenotazione > 0) {
					if (MessageDialog.openConfirm(shell_prenotazioni, "Conferma", "Sei sicuro di voler cancellare la prenotazione corrente?") == true) {
						Connection con = null;
						CallableStatement cstmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();			
							cstmt = con.prepareCall("{call sp_cancellare_prenotazione(?)}", 
									ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
							cstmt.setInt(1, id_prenotazione);
							cstmt.executeUpdate();
							id_prenotazione=0;
							tbl_cliente.clearAll();
							tbl_cabina.clearAll();
							tbl_crociera.clearAll();
							tbl_nave.clearAll();
							tbl_servizi.clearAll();
							tbl_itinerario.clearAll();
							text_annotazioni.setText("");
							text_costobase.setText("");
							text_costoextra.setText("");
							text_totale.setText("");
							text_partenza.setText("");
							text_ritorno.setText("");
						}
						catch (SQLException exc) {
							MessageDialog.openError(shell_prenotazioni, "Error", 
									"IMPOSSIBILE CANCELLARE LA PRENOTAZIONE." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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
					MessageDialog.openInformation(shell_prenotazioni, "Attenzione", "Nessuna prenotazione selezionata.");
			}
		});
		btn_cancella.setText("Cancella");
		btn_cancella.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_cancella.setFont(SWTResourceManager.getFont("Rockwell Condensed", 16, SWT.BOLD));
		btn_cancella.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btn_cancella.setBounds(418, 280, 95, 33);

		Label lbl_background = new Label(shell_prenotazioni, SWT.NONE);
		lbl_background.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		lbl_background.setBounds(0, 274, 595, 45);

		btn_back.setFocus();
	}

	/****************************************************************************************************************************
	 ******** CHILD 01 **********************************************************************************************************
	 ****************************************************************************************************************************/
	private class ChildShell_1 { 
		public ChildShell_1(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare un cliente...");
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
			btn_anagrafica.setText("Aggiungi/Modifica");
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
			TableColumn tc8 = new TableColumn(tbl_anagraf,SWT.CENTER);
			tc1.setWidth(30);
			tc2.setWidth(100);
			tc3.setWidth(120);
			tc4.setWidth(90);
			tc5.setWidth(90);
			tc6.setWidth(90);
			tc7.setWidth(150);
			tc8.setWidth(100);
			tc1.setText("ID");
			tc2.setText("Nome");
			tc3.setText("Cognome");
			tc4.setText("Nascita");
			tc5.setText("Telefono");
			tc6.setText("Cellulare");
			tc7.setText("Email");
			tc8.setText("CF");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();			
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, cf  FROM Anagrafica";
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					TableItem item = new TableItem(tbl_anagraf, SWT.NONE);
					item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)});
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

			tbl_anagraf.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = tbl_anagraf.getSelectionIndex();
					if (selected > -1) {
						tbl_cliente.getItem(0).setText(new String[] {tbl_anagraf.getItem(selected).getText(0), tbl_anagraf.getItem(selected).getText(1), tbl_anagraf.getItem(selected).getText(2)});
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
						String query = "SELECT id_anagrafica, nome, cognome, data_nascita, telefono, cellulare, email, cf  FROM Anagrafica";
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {
							TableItem item = new TableItem(tbl_anagraf, SWT.NONE);
							item.setText(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)});
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
	 ******** CHILD 02 **********************************************************************************************************
	 ****************************************************************************************************************************/
	private class ChildShell_2 { 
		public ChildShell_2(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare una crociera...");
			int w = 700;
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

			Label lbl_crociere = new Label(shell, SWT.SHADOW_IN);
			lbl_crociere.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			lbl_crociere.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
			lbl_crociere.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_crociere.setBounds(100, 10, 110, 30);
			lbl_crociere.setText("Crociere:");

			Label lbl_itinerario = new Label(shell, SWT.SHADOW_IN);
			lbl_itinerario.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			lbl_itinerario.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
			lbl_itinerario.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_itinerario.setBounds(490, 10, 110, 30);
			lbl_itinerario.setText("Itinerario:");
			
			Label lbl_nota = new Label(shell, SWT.SHADOW_IN | SWT.WRAP);
			lbl_nota.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			lbl_nota.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
			lbl_nota.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_nota.setBounds(470, 155, 180, 20);
			lbl_nota.setText("<-- click per vedere l'itinerario");

			Table tbl_crociera_sh2 = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
			tbl_crociera_sh2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			tbl_crociera_sh2.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			tbl_crociera_sh2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			tbl_crociera_sh2.setBounds(10, 40, 450, 140);
			tbl_crociera_sh2.setHeaderVisible(true);
			tbl_crociera_sh2.setLinesVisible(true);
			TableColumn tc1 = new TableColumn(tbl_crociera_sh2, SWT.CENTER);
			TableColumn tc2 = new TableColumn(tbl_crociera_sh2,SWT.CENTER);
			TableColumn tc3 = new TableColumn(tbl_crociera_sh2,SWT.CENTER);
			TableColumn tc4 = new TableColumn(tbl_crociera_sh2,SWT.CENTER);
			TableColumn tc5 = new TableColumn(tbl_crociera_sh2,SWT.CENTER);
			TableColumn tc6 = new TableColumn(tbl_crociera_sh2,SWT.CENTER);
			TableColumn tc7 = new TableColumn(tbl_crociera_sh2,SWT.CENTER);
			tc1.setWidth(0);
			tc2.setWidth(70);
			tc3.setWidth(105);
			tc4.setWidth(53);
			tc5.setWidth(80);
			tc6.setWidth(80);
			tc7.setWidth(40);
			tc1.setText("ID");
			tc2.setText("Nome");
			tc3.setText("Tipo");
			tc4.setText("Costo");
			tc5.setText("Da");
			tc6.setText("Al");
			tc7.setText("Posti");

			Table tbl_itinerario_sh2 = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION|SWT.MULTI);
			tbl_itinerario_sh2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			tbl_itinerario_sh2.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			tbl_itinerario_sh2.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			tbl_itinerario_sh2.setBounds(470, 40, 220, 110);
			tbl_itinerario_sh2.setHeaderVisible(true);
			tbl_itinerario_sh2.setLinesVisible(true);
			TableColumn tb11_c1 = new TableColumn(tbl_itinerario_sh2, SWT.CENTER);
			tb11_c1.setWidth(40);
			tb11_c1.setText("No.");
			TableColumn tb11_c2 = new TableColumn(tbl_itinerario_sh2,SWT.CENTER);
			tb11_c2.setWidth(80);
			tb11_c2.setText("Citta"); 
			TableColumn tb11_c3 = new TableColumn(tbl_itinerario_sh2,SWT.CENTER);
			tb11_c3.setWidth(90);
			tb11_c3.setText("Paese");
			
			Label lbl_separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			lbl_separator.setBounds(10, 190, 620, 2);
			
			//row#1
			Label lbl_destinazione = new Label(shell, SWT.SHADOW_IN);
			lbl_destinazione.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_destinazione.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_destinazione.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_destinazione.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_destinazione.setBounds(10, 200, 80, 20);
			lbl_destinazione.setText(" Destinazione:");	
			Combo cmb_destinazione = new Combo(shell, SWT.READ_ONLY);
			cmb_destinazione.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			cmb_destinazione.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			cmb_destinazione.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			cmb_destinazione.setBounds(100, 200, 80, 20);

			Label lbl_data = new Label(shell, SWT.SHADOW_IN);
			lbl_data.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_data.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_data.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_data.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_data.setBounds(220, 200, 80, 20);
			lbl_data.setText(" Partenza tra:");	
			text_data1 = new Text(shell, SWT.BORDER);
			text_data1.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_data1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_data1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_data1.setEditable(false);
			text_data1.setBounds(310, 200, 80, 20);
			Label lbl_data_ = new Label(shell, SWT.SHADOW_IN);
			lbl_data_.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_data_.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_data_.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_data_.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_data_.setBounds(400, 200, 20, 20);
			lbl_data_.setText(" e:");	
			text_data2 = new Text(shell, SWT.BORDER);
			text_data2.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_data2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_data2.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_data2.setEditable(false);
			text_data2.setBounds(430, 200, 80, 20);
			text_data1.setText(dateFormat_1.format(date_1));
			calendar_1.setTime(date_1); 
			calendar_1.add(Calendar.MONTH, 1);
			text_data2.setText(dateFormat_1.format(calendar_1.getTime()));
			text_data1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					new ChildShell_dates(display, 1);
				}
			});
			text_data2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					new ChildShell_dates(display, 2);
				}
			});
			
			Label lbl_posti = new Label(shell, SWT.SHADOW_IN);
			lbl_posti.setOrientation(SWT.LEFT_TO_RIGHT);
			lbl_posti.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			lbl_posti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_posti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lbl_posti.setBounds(530, 200, 80, 20);
			lbl_posti.setText(" Posti liberi:");	
			Text text_posti = new Text(shell, SWT.BORDER);
			text_posti.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			text_posti.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			text_posti.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			text_posti.setBounds(630, 200, 60, 20);
						
			Button btn_filter = new Button(shell, SWT.NONE);
			btn_filter.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
			btn_filter.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
			btn_filter.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
			btn_filter.setBounds(10, 240, 100, 20);
			btn_filter.setText("Filtra dati");
			btn_filter.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Connection con = null;
					Statement stmt = null;
					try {
						con = SQLConnector.getDatabaseConnection();
						stmt = con.createStatement();
						tbl_crociera_sh2.removeAll();
						String query = "SELECT T0.id_registro_crociera, T0.nome, T0.denominazione, T0.costo_base, T0.id_nave, T0.descrizione, T0.data_inizio, T0.data_fine, SUM(posti) AS posti_liberi "
									+ "FROM ( "
									+ "SELECT T1.id_registro_crociera, T3.nome, T2.denominazione, T2. costo_base, T3.id_nave, T4.descrizione, T1.data_inizio, T1.data_fine, T7.posti, T7.id_cabina "
									+ "FROM Registro_crociere T1 "
									+ "INNER JOIN Crociera T2 "
									+ "ON T1.id_crociera=T2.id_crociera "
									+ "INNER JOIN Nave T3 "
									+ "ON T1.id_nave=T3.id_nave "
									+ "INNER JOIN Tipologia_crociera T4 "
									+ "ON T2.id_tipologia_crociera=T4.id_tipologia_crociera "
									+ "INNER JOIN Registro_Porti T5 "
									+ "ON T1.id_crociera=T5.id_crociera "
									+ "INNER JOIN Porto T6 "
									+ "ON T5.id_porto=T6.id_porto "
									+ "INNER JOIN Cabina T7 "
									+ "ON T3.id_nave=T7.id_nave "
									+ "WHERE (T6.citta like '%" + cmb_destinazione.getText() + "%') "
									+ "AND (T1.data_inizio >= '" + text_data1.getText() + "' AND T1.data_inizio <= '" + text_data2.getText() + "') "
									+ "AND (T7.id_cabina NOT IN (SELECT id_cabina FROM Prenotazione WHERE T1.id_registro_crociera IN (SELECT id_registro_crociera FROM Prenotazione))) "
									+ "GROUP BY T1.id_registro_crociera, T3.nome, T2.denominazione, T2. costo_base, T1.id_nave, T3.id_nave, T4.descrizione, T1.data_inizio, T1.data_fine, T7.id_cabina, T7.posti, T7.id_cabina "
									+ ") AS T0 "
								+ "GROUP BY id_registro_crociera, nome, denominazione, costo_base, id_nave, descrizione, data_inizio, data_fine "
								+ "HAVING (SUM(posti) >= '" + text_posti.getText() + "')";
						ResultSet rs = stmt.executeQuery(query); 
						while (rs.next()) {  
							TableItem item = new TableItem(tbl_crociera_sh2, SWT.NONE);
							item.setText(new String[]{rs.getString(1),rs.getString(2), rs.getString(3), 
									rs.getString(4), rs.getString(7), rs.getString(8), rs.getString(9)});
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
				}
			});

			Button btn_clean = new Button(shell, SWT.NONE);
			btn_clean.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
			btn_clean.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
			btn_clean.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
			btn_clean.setBounds(10, 240, 100, 20);
			btn_clean.setText("Svuota campi");
			btn_clean.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					cmb_destinazione.clearSelection();
					text_posti.setText("");
					text_data1.setText(dateFormat_1.format(date_1));
					calendar_1.setTime(date_1); 
					calendar_1.add(Calendar.MONTH, 1);
					text_data2.setText(dateFormat_1.format(calendar_1.getTime()));
				}
			});
			
			Button btn_back = new Button(shell, SWT.NONE);
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setText("<--");
			btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
			btn_back.setBounds(660, 240, 30, 18);
			btn_back.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});

			//LOAD COMBOBOX
			Connection con = null;	 		  
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();	 		  
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_porto, citta, paese FROM Porto ";  
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {
					cmb_destinazione.add(rs.getString(2));
				}
				cmb_destinazione.select(0);
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
			
			//LOAD CRUISES
			try {
				con = SQLConnector.getDatabaseConnection();
				stmt = con.createStatement();
				String query = "SELECT T0.id_registro_crociera, T0.nome, T0.denominazione, T0.costo_base, T0.id_nave, T0.descrizione, T0.data_inizio, T0.data_fine, SUM(posti) AS posti_liberi "
						+ "FROM ( "
						+ "SELECT T1.id_registro_crociera, T3.nome, T2.denominazione, T2. costo_base, T3.id_nave, T4.descrizione, T1.data_inizio, T1.data_fine, T7.posti, T7.id_cabina "
						+ "FROM Registro_crociere T1 "
						+ "INNER JOIN Crociera T2 "
						+ "ON T1.id_crociera=T2.id_crociera "
						+ "INNER JOIN Nave T3 "
						+ "ON T1.id_nave=T3.id_nave "
						+ "INNER JOIN Tipologia_crociera T4 "
						+ "ON T2.id_tipologia_crociera=T4.id_tipologia_crociera "
						+ "INNER JOIN Registro_Porti T5 "
						+ "ON T1.id_crociera=T5.id_crociera "
						+ "INNER JOIN Porto T6 "
						+ "ON T5.id_porto=T6.id_porto "
						+ "INNER JOIN Cabina T7 "
						+ "ON T3.id_nave=T7.id_nave "
						+ "WHERE (T7.id_cabina NOT IN (SELECT id_cabina FROM Prenotazione WHERE T1.id_registro_crociera IN (SELECT id_registro_crociera FROM Prenotazione))) "
						+ "GROUP BY T1.id_registro_crociera, T3.nome, T2.denominazione, T2. costo_base, T1.id_nave, T3.id_nave, T4.descrizione, T1.data_inizio, T1.data_fine, T7.id_cabina, T7.posti, T7.id_cabina "
						+ ") AS T0 "
					+ "GROUP BY id_registro_crociera, nome, denominazione, costo_base, id_nave, descrizione, data_inizio, data_fine ";
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {  
					TableItem item = new TableItem(tbl_crociera_sh2, SWT.NONE);
					item.setText(new String[]{rs.getString(1),rs.getString(2), rs.getString(3), 
							rs.getString(4), rs.getString(7), rs.getString(8), rs.getString(9)});
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

			tbl_crociera_sh2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					tbl_itinerario_sh2.removeAll();
					int selected = tbl_crociera_sh2.getSelectionIndex();
					if (selected > -1) {
						Connection con = null;
						Statement stmt = null;
						try {//carica itinerari
							con = SQLConnector.getDatabaseConnection();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT (T2.tappa+1), T3.citta, T3.paese FROM Registro_Crociere T1 ";
							query += "INNER JOIN Registro_Porti T2 ";
							query += "ON T1.id_crociera=T2.id_crociera ";
							query += "INNER JOIN Porto T3 ";
							query += "ON T2.id_porto=T3.id_porto ";	        		 
							query += "WHERE T1.id_registro_crociera =  '" + tbl_crociera_sh2.getItem(selected).getText() + "'";
							query += "ORDER BY tappa ASC";
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {  
								TableItem item = new TableItem(tbl_itinerario_sh2, SWT.NONE);
								item.setText(new String[]{rs.getString(1),rs.getString(2), rs.getString(3)});
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
					}
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = tbl_crociera_sh2.getSelectionIndex();
					if (selected > -1) {
						tbl_crociera.getItem(0).setText(new String[] {tbl_crociera_sh2.getItem(selected).getText(0)});
						Connection con = null;
						Statement stmt = null;
						try {//salva dati selezionati
							con = SQLConnector.getDatabaseConnection();	
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							String query = "SELECT T1.id_registro_crociera, T1.id_nave, data_inizio, T1.data_fine, T2.costo_base, T3.nome, T2.denominazione, T4.descrizione ";
							query += "FROM Registro_Crociere T1 ";
							query += "INNER JOIN Crociera T2 ";
							query += "ON T1.id_crociera = T2.id_crociera ";
							query += "INNER JOIN Nave T3 ";
							query += "ON T1.id_nave=T3.id_nave ";  
							query += "INNER JOIN Tipologia_crociera T4 ";
							query += "ON T2.id_tipologia_crociera=T4.id_tipologia_crociera ";
							ResultSet rs;
							rs = stmt.executeQuery(query);
							rs.absolute(selected+1); //List starts on 0, whereas ResultSet start on 1
							tbl_crociera.getItem(0).setText(new String[]{rs.getString(1), rs.getString(7)});
							tbl_nave.getItem(0).setText(new String[]{rs.getString(2), rs.getString(6), rs.getString(8)});					 		
							tbl_cabina.getItem(0).setText(new String[]{"", ""});
							text_partenza.setText(rs.getString(3));
							text_ritorno.setText(rs.getString(4));
							text_costobase.setText(rs.getString(5));
							text_costoextra.setText("0");
							text_totale.setText(Double.toString(Double.parseDouble(text_costobase.getText()) + Double.parseDouble(text_costoextra.getText())));
							tbl_servizi.removeAll();
							//Itinerario in prima pagina
							tbl_itinerario.removeAll();
							rs.close();
							stmt.close();
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							query = "SELECT (T2.tappa+1), T3.citta, T3.paese FROM Registro_Crociere T1 ";
							query += "INNER JOIN Registro_Porti T2 ";
							query += "ON T1.id_crociera=T2.id_crociera ";
							query += "INNER JOIN Porto T3 ";
							query += "ON T2.id_porto=T3.id_porto ";	        		 
							query += "WHERE T1.id_registro_crociera =  '" + tbl_crociera.getItem(0).getText() + "'";
							query += "ORDER BY tappa ASC";  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {  
								TableItem item = new TableItem(tbl_itinerario, SWT.NONE);
								item.setText(new String[]{rs.getString(1),rs.getString(2), rs.getString(3)});
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
	 ******** CHILD 03 **********************************************************************************************************
	 ****************************************************************************************************************************/
	private class ChildShell_3 { 
		public ChildShell_3(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare una cabina...");
			int w = 200;
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
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
			btn_back.setBounds(160, 150, 30, 18);
			btn_back.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});

			Table table_10 = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
			table_10.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			table_10.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			table_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			table_10.setBounds(0, 0, 200, 140);
			table_10.setHeaderVisible(true);
			table_10.setLinesVisible(true);
			TableColumn tc1 = new TableColumn(table_10, SWT.CENTER);
			tc1.setWidth(30);
			tc1.setText("ID");
			TableColumn tc2 = new TableColumn(table_10,SWT.CENTER);
			tc2.setWidth(50);
			tc2.setText("Codice");
			TableColumn tc3 = new TableColumn(table_10,SWT.CENTER);
			tc3.setWidth(45);
			tc3.setText("Classe");
			TableColumn tc4 = new TableColumn(table_10,SWT.CENTER);
			tc4.setWidth(45);
			tc4.setText("Posti");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();		
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_cabina, codice, classe, posti FROM Cabina T1 ";
				query += "INNER JOIN Registro_Crociere T2 ";
				query += "ON T1.id_nave = T2.id_nave ";
				query += "WHERE T2.id_registro_crociera =  '" + tbl_crociera.getItem(0).getText() + "' ";
				query += "AND T1.id_cabina NOT IN (SELECT id_cabina from Prenotazione WHERE id_registro_crociera = '" + tbl_crociera.getItem(0).getText() + "')";	           
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {  
					TableItem item = new TableItem(table_10, SWT.NONE);
					item.setText(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
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

			table_10.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = table_10.getSelectionIndex();
					if (selected > -1) {
						tbl_cabina.getItem(0).setText(new String[]{table_10.getItem(selected).getText(0), table_10.getItem(selected).getText(1), 
								table_10.getItem(selected).getText(3)+" posti", table_10.getItem(selected).getText(2)+"°classe"});	
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
	 ******** CHILD 04 **********************************************************************************************************
	 ****************************************************************************************************************************/
	private class ChildShell_4 { 
		public ChildShell_4(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare un servizio...");
			int w = 210;
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
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
			btn_back.setBounds(0, 150, 30, 18);
			btn_back.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});

			Table table_10 = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION|SWT.MULTI);
			table_10.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			table_10.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			table_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			table_10.setBounds(0, 0, 205, 145);
			table_10.setHeaderVisible(true);
			table_10.setLinesVisible(true);
			TableColumn tc1 = new TableColumn(table_10, SWT.CENTER);
			TableColumn tc2 = new TableColumn(table_10,SWT.CENTER);
			TableColumn tc3 = new TableColumn(table_10,SWT.CENTER);
			tc1.setWidth(50);
			tc2.setWidth(80);
			tc3.setWidth(50);
			tc1.setText("ID");
			tc2.setText("Nome");
			tc3.setText("Costo");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();	
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT id_servizio, descrizione, costo_extra FROM Servizio T1 ";
				query += "INNER JOIN Registro_Crociere T2 ";
				query += "ON T1.id_nave=T2.id_nave ";
				query += "WHERE T1.id_nave =  " + tbl_nave.getItem(0).getText(0) + " ";
				query += "AND T2.id_registro_crociera =  '" + tbl_crociera.getItem(0).getText() + "'";  
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {  
					TableItem item = new TableItem(table_10, SWT.NONE);
					item.setText(new String[]{rs.getString(1),rs.getString(2), rs.getString(3)});
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

			table_10.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = table_10.getSelectionIndex();
					boolean itemExist = false;
					if (selected > -1) {
						for (int i = 0; i < tbl_servizi.getItemCount(); i++) {
							if (tbl_servizi.getItem(i).getText(0).equals(table_10.getItem(selected).getText(0))) {
								MessageDialog.openWarning(shell_prenotazioni, "Attenzione", "Servizio già inserito!");
								itemExist = true;
								break;
							}
						}
						shell.setFocus();
						if (!itemExist) {
							TableItem item = new TableItem(tbl_servizi, SWT.NONE);
							item.setText(new String[]{table_10.getItem(selected).getText(0), table_10.getItem(selected).getText(1),table_10.getItem(selected).getText(2)});

							double temp_extra;
							if (text_costoextra.getText() != "")
								temp_extra = Double.parseDouble(text_costoextra.getText());
							else
								temp_extra = 0;
							temp_extra += Double.parseDouble(table_10.getItem(selected).getText(2));
							text_costoextra.setText(Double.toString(temp_extra));
							double temp_total = Double.parseDouble(text_costobase.getText()) + Double.parseDouble(text_costoextra.getText());
							text_totale.setText(Double.toString(temp_total));
							shell.dispose();
						}
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

	/****************************************************************************************************************************
	 ******** CHILD 05 **********************************************************************************************************
	 ****************************************************************************************************************************/
	private class ChildShell_5 { 
		public ChildShell_5(Display display) { 
			final Shell shell = new Shell(display, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			shell.setText("Selezionare una prenotazione...");
			int w = 620;
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
			btn_back.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			btn_back.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC | SWT.BOLD));
			btn_back.setBounds(580, 150, 30, 18);
			btn_back.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});

			Table table_prenotazioni = new Table(shell,SWT.BORDER |SWT.FULL_SELECTION |SWT.MULTI);
			table_prenotazioni.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			table_prenotazioni.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			table_prenotazioni.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			table_prenotazioni.setBounds(10, 10, 600, 140);
			table_prenotazioni.setHeaderVisible(true);
			table_prenotazioni.setLinesVisible(true);
			TableColumn tp_tc1 = new TableColumn(table_prenotazioni, SWT.CENTER);
			tp_tc1.setWidth(30);
			tp_tc1.setText("ID");
			TableColumn tp_tc2 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc2.setWidth(0);
			tp_tc2.setText("Anagrafica_ID");
			TableColumn tp_tc3 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc3.setWidth(70);
			tp_tc3.setText("Nome");
			TableColumn tp_tc4 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc4.setWidth(100);
			tp_tc4.setText("Cognome");
			TableColumn tp_tc5 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc5.setWidth(0);
			tp_tc5.setText("Reg_crociera_ID");
			TableColumn tp_tc6 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc6.setWidth(100);
			tp_tc6.setText("Crociera");
			TableColumn tp_tc7 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc7.setWidth(0);
			tp_tc7.setText("Cabina_ID");
			TableColumn tp_tc8 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc8.setWidth(60);
			tp_tc8.setText("Cabina");
			TableColumn tp_tc9 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc9.setWidth(60);
			tp_tc9.setText("Totale");
			TableColumn tp_tc10 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc10.setWidth(80);
			tp_tc10.setText("Partenza");
			TableColumn tp_tc11 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc11.setWidth(80);
			tp_tc11.setText("Ritorno");
			TableColumn tp_tc12 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc12.setWidth(0);
			tp_tc12.setText("Crociera_ID");
			TableColumn tp_tc13 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc13.setWidth(0);
			tp_tc13.setText("Annotazioni");
			TableColumn tp_tc14 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc14.setWidth(0);
			tp_tc14.setText("Costo_base");
			TableColumn tp_tc15 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc15.setWidth(0);
			tp_tc15.setText("Nave_ID");
			TableColumn tp_tc16 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc16.setWidth(0);
			tp_tc16.setText("Nave_Nome");
			TableColumn tp_tc17 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc17.setWidth(0);
			tp_tc17.setText("Nave_Nome");
			TableColumn tp_tc18 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc18.setWidth(0);
			tp_tc18.setText("Posti");
			TableColumn tp_tc19 = new TableColumn(table_prenotazioni,SWT.CENTER);
			tp_tc19.setWidth(0);
			tp_tc19.setText("Classe");

			Connection con = null;
			Statement stmt = null;
			try {
				con = SQLConnector.getDatabaseConnection();		
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String query = "SELECT T1.id_prenotazione, T1.id_anagrafica, T2.nome, T2.cognome, T1.id_registro_crociera, T4.denominazione, "
						+ "T1.id_cabina, T5.codice, T1.costo_totale, T3.data_inizio, T3.data_fine, T4.id_crociera, T1.annotazioni, T4.costo_base, "
						+ " T6.id_nave, T6.nome, T7.descrizione, T5.posti, T5.classe "
						+ "FROM Prenotazione T1 "
						+ "INNER JOIN Anagrafica T2 "
						+ "ON T1.id_anagrafica = T2.id_anagrafica "
						+ "INNER JOIN Registro_Crociere T3 "
						+ "ON T1.id_registro_crociera = T3.id_registro_crociera "
						+ "INNER JOIN Crociera T4 "
						+ "ON T3.id_crociera = T4.id_crociera "
						+ "INNER JOIN Cabina T5 "
						+ "ON T1.id_cabina = T5.id_cabina "
						+ "INNER JOIN Nave T6 "
						+ "ON T3.id_nave = T6.id_nave "
						+ "INNER JOIN Tipologia_crociera T7 "
						+ "ON T4.id_tipologia_crociera = T7. id_tipologia_crociera ";
				ResultSet rs = stmt.executeQuery(query); 
				while (rs.next()) {  
					TableItem item = new TableItem(table_prenotazioni, SWT.NONE);
					item.setText(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
							rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
							rs.getString(13),rs.getString(14),rs.getString(15),rs.getString(16),rs.getString(17),
							rs.getString(18),rs.getString(19)});
				}
			}
			catch (SQLException exc) {
				MessageDialog.openError(shell, "Error", 
						"IMPOSSIBILE OTTENERE I DATI DELLE PRENOTAZIONI." + "\n\nError message:  \"" + exc.getMessage() + "\"" +
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

			table_prenotazioni.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					int selected = table_prenotazioni.getSelectionIndex();
					if (selected > -1) {
						//0-4  T1.id_prenotazione, T1.id_anagrafica, T2.nome, T2.cognome, T1.id_registro_crociera
						//5-10 T4.denominazione, T1.id_cabina, T5.codice, T1.costo_totale, T3.data_inizio, T3.data_fine
						//11-15 id_crociera, annotazioni, costo_base, id_nave, T6.nome
						//16-20 T7.descrizione, T5.posti, T5.classe
						id_prenotazione = Integer.parseInt(table_prenotazioni.getItem(selected).getText(0));
						tbl_cliente.getItem(0).setText(0, table_prenotazioni.getItem(selected).getText(1));
						tbl_cliente.getItem(0).setText(1, table_prenotazioni.getItem(selected).getText(2));
						tbl_cliente.getItem(0).setText(2, table_prenotazioni.getItem(selected).getText(3));
						tbl_cabina.getItem(0).setText(0, table_prenotazioni.getItem(selected).getText(6));
						tbl_cabina.getItem(0).setText(1, table_prenotazioni.getItem(selected).getText(7));
						tbl_cabina.getItem(0).setText(2, table_prenotazioni.getItem(selected).getText(17)+" posti");
						tbl_cabina.getItem(0).setText(3, table_prenotazioni.getItem(selected).getText(18)+"ºclasse");
						tbl_crociera.getItem(0).setText(0, table_prenotazioni.getItem(selected).getText(4));
						tbl_crociera.getItem(0).setText(1, table_prenotazioni.getItem(selected).getText(5));
						tbl_nave.getItem(0).setText(0, table_prenotazioni.getItem(selected).getText(14));
						tbl_nave.getItem(0).setText(1, table_prenotazioni.getItem(selected).getText(15));
						tbl_nave.getItem(0).setText(2, table_prenotazioni.getItem(selected).getText(16));
						text_annotazioni.setText(table_prenotazioni.getItem(selected).getText(12));
						text_costobase.setText(table_prenotazioni.getItem(selected).getText(13));
						text_totale.setText(table_prenotazioni.getItem(selected).getText(8));
						text_partenza.setText(table_prenotazioni.getItem(selected).getText(9));
						text_ritorno.setText(table_prenotazioni.getItem(selected).getText(10));
						text_costoextra.setText(""+(Double.parseDouble(text_totale.getText()) - Double.parseDouble(text_costobase.getText()))+"");

						//tbl_itinerario && tbl_servizi
						Connection con = null;
						Statement stmt = null;
						try {
							con = SQLConnector.getDatabaseConnection();	
							stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							//tbl_itinerario
							String query = "SELECT (T2.tappa+1), T3.citta, T3.paese FROM Registro_Crociere T1 " +
									"INNER JOIN Registro_Porti T2 " +
									"ON T1.id_crociera=T2.id_crociera " +
									"INNER JOIN Porto T3 " +
									"ON T2.id_porto=T3.id_porto " +	        		 
									"WHERE T1.id_registro_crociera =  '" + table_prenotazioni.getItem(selected).getText(4) + "'" +
									"ORDER BY tappa ASC";
							ResultSet rs = stmt.executeQuery(query); 
							while (rs.next()) {  
								TableItem item = new TableItem(tbl_itinerario, SWT.NONE);
								item.setText(new String[]{rs.getString(1),rs.getString(2), rs.getString(3)});
							}
							//tbl_servizi
							query = "SELECT T1.id_servizio, T1.descrizione, T1.costo_extra FROM Servizio T1 "
									+ "INNER JOIN Registro_Servizi T2 "
									+ "ON T1.id_servizio = T2.id_servizio "
									+ "WHERE T2.id_prenotazione =  " + id_prenotazione + " ";  
							rs = stmt.executeQuery(query); 
							while (rs.next()) {  
								TableItem item = new TableItem(tbl_servizi, SWT.NONE);
								item.setText(new String[]{rs.getString(1),rs.getString(2), rs.getString(3)});
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
				if (text_data1.getText() != "") {
					dt_1.setYear(Integer.parseInt(text_data1.getText().substring(0,4)));
					dt_1.setMonth(Integer.parseInt(text_data1.getText().substring(5,7))-1);
					dt_1.setDay(Integer.parseInt(text_data1.getText().substring(8,10)));
				}
			}
			if (src==2) {
				if (text_data2.getText() != "") {
					dt_1.setYear(Integer.parseInt(text_data2.getText().substring(0,4)));
					dt_1.setMonth(Integer.parseInt(text_data2.getText().substring(5,7))-1);
					dt_1.setDay(Integer.parseInt(text_data2.getText().substring(8,10)));
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
						if ( (text_data2.getText() != "") ) {
							try {
								DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
								date_a = format.parse(""+newDate+"");
								date_z = format.parse(""+text_data2.getText()+"");
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							if(date_a.before(date_z))
								text_data1.setText(""+newDate+"");	
							else
								MessageDialog.openError(shell, "Errore", "La data inizio è posteriore alla data fine.");
						} 	
						else
							text_data1.setText(""+newDate+"");
					}
					if (src==2){
						if ( (text_data1.getText() != "") ) {
							try {
								DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
								date_a = format.parse(""+text_data1.getText()+"");
								date_z = format.parse(""+newDate+"");
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							if(date_z.after(date_a))
								text_data2.setText(""+newDate+"");	
							else
								MessageDialog.openError(shell, "Errore", "La data fine è precedente alla data inizio.");
						} 
						else
							text_data2.setText(""+newDate+"");
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
