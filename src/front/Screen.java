package front;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

public class Screen {

	protected Shell shlSimuladorRfid;
	private Text init_tags;
	private Text increment;
	private Text tag_max;
	private Text retry_tag;
	private Text init_frame_size;
	private Button use_schoute;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Screen window = new Screen();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSimuladorRfid.open();
		shlSimuladorRfid.layout();
		while (!shlSimuladorRfid.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSimuladorRfid = new Shell();
		shlSimuladorRfid.setSize(444, 444);
		shlSimuladorRfid.setText("Simulador RFID");
		
		Composite composite = new Composite(shlSimuladorRfid, SWT.NONE);
		composite.setBounds(0, 0, 437, 280);
		
		Label lblProtocolos = new Label(composite, SWT.NONE);
		lblProtocolos.setBounds(10, 10, 113, 20);
		lblProtocolos.setText("Protocolos:");
		
		use_schoute = new Button(composite, SWT.RADIO);
		use_schoute.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		use_schoute.setBounds(10, 36, 83, 20);
		use_schoute.setText("Schoute");
		
		Button use_lb = new Button(composite, SWT.RADIO);
		use_lb.setBounds(10, 62, 111, 20);
		use_lb.setText("Lower Bound");
		
		Button use_sbs = new Button(composite, SWT.RADIO);
		use_sbs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		use_sbs.setBounds(10, 88, 111, 20);
		use_sbs.setText("ILBM SbS");
		
		Label lblT = new Label(composite, SWT.NONE);
		lblT.setBounds(10, 114, 94, 20);
		lblT.setText("Tags Iniciais:");
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(10, 140, 94, 20);
		lblNewLabel.setText("Incremento:");
		
		Label lblMaximoDeTags = new Label(composite, SWT.NONE);
		lblMaximoDeTags.setText("Maximo de Tags:");
		lblMaximoDeTags.setBounds(10, 166, 113, 20);
		
		Label lblRepetiesPorEtiqueta = new Label(composite, SWT.NONE);
		lblRepetiesPorEtiqueta.setBounds(10, 192, 167, 20);
		lblRepetiesPorEtiqueta.setText("Repeti\u00E7\u00F5es por Etiqueta:");
		
		Label lblTamanhoDoQuadro = new Label(composite, SWT.NONE);
		lblTamanhoDoQuadro.setBounds(10, 218, 196, 20);
		lblTamanhoDoQuadro.setText("Tamanho do Quadro Inicial:");
		
		Button limit_pow2 = new Button(composite, SWT.RADIO);
		limit_pow2.setBounds(10, 244, 196, 20);
		limit_pow2.setText("Limitado a Potencia de 2");
		
		init_tags = new Text(composite, SWT.BORDER);
		init_tags.setBounds(99, 114, 78, 26);
		
		increment = new Text(composite, SWT.BORDER);
		increment.setBounds(99, 140, 78, 26);
		
		tag_max = new Text(composite, SWT.BORDER);
		tag_max.setBounds(129, 166, 78, 26);
		
		retry_tag = new Text(composite, SWT.BORDER);
		retry_tag.setBounds(183, 192, 78, 26);
		
		init_frame_size = new Text(composite, SWT.BORDER);
		init_frame_size.setBounds(205, 218, 78, 26);
		
		Button simulate = new Button(shlSimuladorRfid, SWT.NONE);
		simulate.setBounds(166, 357, 90, 30);
		simulate.setText("Simular");

	}
}
