import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class form_main {

	protected Shell shell_main;
	protected boolean admin=false;
	protected boolean assist=false;

	public static void main(String[] args) {
		try {
			form_main window = new form_main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(display);		
		shell_main.open();
		shell_main.layout();
		while (!shell_main.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents(Display display) {
		shell_main = new Shell(SWT.TITLE | SWT.BORDER | SWT.PRIMARY_MODAL);
		shell_main.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		shell_main.setText("Sistema di Prenotazione");
		int w = 450;
		int h = 300;
		int x = (display.getBounds().width-w)/2;
		int y = (display.getBounds().height-h)/2;
		shell_main.setSize(w, h);
		shell_main.setLocation(x, y);

		Image origImage = new Image(display, System.getProperty("user.dir").replace('\\', '/') + "/res/bg_cruise.jpg");
		Image scaledImage = null; 
		double zoom = 1d/3; 
		final int width = origImage.getBounds().width; 
		final int height = origImage.getBounds().height; 
		scaledImage = new Image(Display.getDefault(), origImage.getImageData().scaledTo((int)(width * zoom),(int)(height * zoom))); 
		shell_main.setBackgroundImage(scaledImage);
		shell_main.setBackgroundMode(SWT.INHERIT_FORCE);  

		Label lblSistemaDiPrenotazione = new Label(shell_main, SWT.NONE);
		lblSistemaDiPrenotazione.setText("Sistema di prenotazione");
		lblSistemaDiPrenotazione.setForeground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		lblSistemaDiPrenotazione.setFont(SWTResourceManager.getFont("Bookman Old Style", 16, SWT.BOLD));
		lblSistemaDiPrenotazione.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblSistemaDiPrenotazione.setAlignment(SWT.CENTER);
		lblSistemaDiPrenotazione.setBounds(160, 22, 273, 24);

		Button btn_prenotazione = new Button(shell_main, SWT.BORDER);
		btn_prenotazione.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_prenotazione.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				form_prenotazioni fp = new form_prenotazioni();
				//shlSistemaDiPrenotazione.dispose();
				fp.open();
			}
		});
		btn_prenotazione.setFont(SWTResourceManager.getFont("Rockwell Condensed", 12, SWT.BOLD));
		btn_prenotazione.setBounds(10, 86, 145, 24);
		btn_prenotazione.setText("Prenotazioni");

		Button btn_logout = new Button(shell_main, SWT.NONE);
		btn_logout.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btn_logout.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btn_logout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				form_login fl = new form_login();
				shell_main.dispose();
				fl.open();
			}
		});
		btn_logout.setText("logout");
		btn_logout.setFont(SWTResourceManager.getFont("Cambria", 9, SWT.BOLD));
		btn_logout.setBounds(389, 246, 55, 25);

		Label lbl_sep1 = new Label(shell_main, SWT.SEPARATOR | SWT.HORIZONTAL);
		lbl_sep1.setBounds(0, 78, 444, 2);

		Button btn_navi = new Button(shell_main, SWT.BORDER);
		btn_navi.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_navi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new form_navi(display); 
			}
		});
		btn_navi.setText("Navi");
		btn_navi.setFont(SWTResourceManager.getFont("Rockwell Condensed", 12, SWT.BOLD));
		btn_navi.setBounds(10, 177, 106, 25);

		Button btn_servizi = new Button(shell_main, SWT.BORDER);
		btn_servizi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new form_servizi(display); 
			}
		});
		btn_servizi.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_servizi.setText("Servizi");
		btn_servizi.setFont(SWTResourceManager.getFont("Rockwell Condensed", 12, SWT.BOLD));
		btn_servizi.setBounds(10, 239, 106, 25);

		Button btn_clienti = new Button(shell_main, SWT.BORDER);
		btn_clienti.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_clienti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new form_anagrafica(display); 
			}
		});
		btn_clienti.setText("Clienti");
		btn_clienti.setFont(SWTResourceManager.getFont("Rockwell Condensed", 12, SWT.BOLD));
		btn_clienti.setBounds(10, 116, 145, 24);

		Button btn_utenti = new Button(shell_main, SWT.BORDER);
		btn_utenti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new form_utenti(display); 
			}
		});
		btn_utenti.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_utenti.setText("Utenti");
		btn_utenti.setFont(SWTResourceManager.getFont("Rockwell Condensed", 12, SWT.BOLD));
		btn_utenti.setBounds(244, 239, 106, 25);

		Button btn_cabine = new Button(shell_main, SWT.BORDER);
		btn_cabine.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new form_cabine(display); 
			}
		});
		btn_cabine.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_cabine.setText("Cabine");
		btn_cabine.setFont(SWTResourceManager.getFont("Rockwell Condensed", 12, SWT.BOLD));
		btn_cabine.setBounds(10, 208, 106, 25);

		Label lbl_sep2 = new Label(shell_main, SWT.SEPARATOR | SWT.HORIZONTAL);
		lbl_sep2.setBounds(0, 168, 444, 2);

		Button btn_crociere = new Button(shell_main, SWT.BORDER);
		btn_crociere.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new form_crociere(display); 
			}
		});
		btn_crociere.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_crociere.setText("Crociere");
		btn_crociere.setFont(SWTResourceManager.getFont("Rockwell Condensed", 12, SWT.BOLD));
		btn_crociere.setBounds(126, 239, 106, 24);

		Label lbl_about = new Label(shell_main, SWT.NONE);
		lbl_about.setText("by mitXael");
		lbl_about.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_about.setFont(SWTResourceManager.getFont("Bookman Old Style", 9, SWT.BOLD | SWT.ITALIC | SWT.UNDERLINE_DOUBLE));
		lbl_about.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbl_about.setAlignment(SWT.CENTER);
		lbl_about.setBounds(363, 53, 70, 15);

		Button btn_Porti = new Button(shell_main, SWT.BORDER);
		btn_Porti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new form_porti(display); 
			}
		});
		btn_Porti.setText("Porti");
		btn_Porti.setFont(SWTResourceManager.getFont("Rockwell Condensed", 12, SWT.BOLD));
		btn_Porti.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btn_Porti.setBounds(126, 208, 106, 24);

		if (assist == false) {
			btn_prenotazione.setVisible(false);
			btn_clienti.setVisible(false);
		}
		if (admin == false) {
			btn_crociere.setVisible(false);
			btn_navi.setVisible(false);
			btn_cabine.setVisible(false);
			btn_servizi.setVisible(false);
			btn_utenti.setVisible(false);
			btn_Porti.setVisible(false);
		}

		btn_logout.setFocus();
	}
}
