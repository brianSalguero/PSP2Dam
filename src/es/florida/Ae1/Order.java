package es.florida.Ae1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * VISTA ON L'USUARI ELEGIX LES OPCIONS QUE VOL
 */
public class Order extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomFitxer;
	private JLabel lblTitol;
	private JLabel lblTipoFabricacio;
	private JLabel lblMostrar;
	private JLabel lblNomFitxer;

	private JCheckBox ckbTipoI, ckbTipoL, ckbTipoT, ckbTipoJ, ckbTipoO, ckbTipoS, ckbTipoZ;
	private JSpinner spnI, spnO, spnT, spnJ, spnL, spnS, spnZ;
	private JRadioButton rdbConsola, rdbFitxer;
	private JButton btnOrdenarFabricacio;
	private String tipoOutput = "console";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Order frame = new Order();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Order() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTitol = new JLabel("ORDRE DE FABRICACIÓ");
		lblTitol.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitol.setFont(new Font("Arial", Font.BOLD, 25));
		lblTitol.setBounds(106, 10, 314, 46);
		contentPane.add(lblTitol);

		ckbTipoI = new JCheckBox("  I");
		ckbTipoI.setFont(new Font("Arial", Font.PLAIN, 12));
		ckbTipoI.setBounds(40, 107, 64, 20);
		contentPane.add(ckbTipoI);
		// En cas de elegir-lo, spinner corresponent serà ocultat
		ckbTipoI.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spnI.setVisible(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spnI.setVisible(false);
					spnI.setValue(1);
				}
			}
		});

		ckbTipoL = new JCheckBox("  L");
		ckbTipoL.setFont(new Font("Arial", Font.PLAIN, 12));
		ckbTipoL.setBounds(304, 107, 64, 20);
		contentPane.add(ckbTipoL);
		// En cas de elegir-lo, spinner corresponent serà ocultat
		ckbTipoL.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spnL.setVisible(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spnL.setVisible(false);
					spnL.setValue(1);
				}
			}
		});

		ckbTipoT = new JCheckBox("  T");
		ckbTipoT.setFont(new Font("Arial", Font.PLAIN, 12));
		ckbTipoT.setBounds(172, 107, 64, 20);
		contentPane.add(ckbTipoT);
		// En cas de elegir-lo, spinner corresponent serà ocultat
		ckbTipoT.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spnT.setVisible(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spnT.setVisible(false);
					spnT.setValue(1);
				}
			}
		});

		ckbTipoJ = new JCheckBox("  J");
		ckbTipoJ.setFont(new Font("Arial", Font.PLAIN, 12));
		ckbTipoJ.setBounds(238, 107, 64, 20);
		contentPane.add(ckbTipoJ);
		// En cas de elegir-lo, spinner corresponent serà ocultat
		ckbTipoJ.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spnJ.setVisible(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spnJ.setVisible(false);
					spnJ.setValue(1);
				}
			}
		});

		ckbTipoO = new JCheckBox("  O");
		ckbTipoO.setFont(new Font("Arial", Font.PLAIN, 12));
		ckbTipoO.setBounds(106, 107, 64, 20);
		contentPane.add(ckbTipoO);
		// En cas de elegir-lo, spinner corresponent serà ocultat
		ckbTipoO.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spnO.setVisible(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spnO.setVisible(false);
					spnO.setValue(1);
				}
			}
		});

		ckbTipoS = new JCheckBox("  S");
		ckbTipoS.setFont(new Font("Arial", Font.PLAIN, 12));
		ckbTipoS.setBounds(370, 107, 64, 20);
		contentPane.add(ckbTipoS);
		// En cas de elegir-lo, spinner corresponent serà ocultat
		ckbTipoS.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spnS.setVisible(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spnS.setVisible(false);
					spnS.setValue(1);
				}
			}
		});

		ckbTipoZ = new JCheckBox("  Z");
		ckbTipoZ.setFont(new Font("Arial", Font.PLAIN, 12));
		ckbTipoZ.setBounds(436, 107, 64, 20);
		contentPane.add(ckbTipoZ);
		// En cas de elegir-lo, spinner corresponent serà ocultat
		ckbTipoZ.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spnZ.setVisible(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spnZ.setVisible(false);
					spnZ.setValue(1);
				}
			}
		});

		lblTipoFabricacio = new JLabel("ELEGIX EL TIPUS QUE VOLS FABRICAR");
		lblTipoFabricacio.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTipoFabricacio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTipoFabricacio.setBounds(117, 66, 314, 20);
		contentPane.add(lblTipoFabricacio);
		
		SpinnerNumberModel spinnerI = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		spnI = new JSpinner(spinnerI);
		spnI.setFont(new Font("Arial", Font.PLAIN, 15));
		spnI.setBounds(33, 146, 44, 20);
		spnI.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int currentValue = (int) spnI.getValue();
				// Si el valor es menor que 1, estableixer el valor mínim com 1
				if (currentValue < 1) {
					spnI.setValue(1);
				}
			}
		});
		spnI.setVisible(false);
		contentPane.add(spnI);
		
		SpinnerNumberModel spinnerO = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		spnO = new JSpinner(spinnerO);
		spnO.setFont(new Font("Arial", Font.PLAIN, 15));
		spnO.setBounds(99, 146, 44, 20);
		spnO.setVisible(false);
		spnO.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int currentValue = (int) spnO.getValue();
				// Si el valor es menor que 1, establecer el valor mínimo como 1
				if (currentValue < 1) {
					spnO.setValue(1);
				}
			}
		});
		contentPane.add(spnO);
		
		SpinnerNumberModel spinnerT = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		spnT = new JSpinner(spinnerT);
		spnT.setFont(new Font("Arial", Font.PLAIN, 15));
		spnT.setBounds(166, 146, 44, 20);
		spnT.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int currentValue = (int) spnT.getValue();
				// Si el valor es menor que 1, establecer el valor mínimo como 1
				if (currentValue < 1) {
					spnT.setValue(1);
				}
			}
		});
		spnT.setVisible(false);
		contentPane.add(spnT);

		SpinnerNumberModel spinnerJ = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		spnJ = new JSpinner(spinnerJ);
		spnJ.setFont(new Font("Arial", Font.PLAIN, 15));
		spnJ.setBounds(231, 146, 44, 20);
		spnJ.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int currentValue = (int) spnJ.getValue();
				// Si el valor es menor que 1, establecer el valor mínimo como 1
				if (currentValue < 1) {
					spnJ.setValue(1);
				}
			}
		});
		spnJ.setVisible(false);
		contentPane.add(spnJ);

		SpinnerNumberModel spinnerL = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		spnL = new JSpinner(spinnerL);
		spnL.setFont(new Font("Arial", Font.PLAIN, 15));
		spnL.setBounds(297, 146, 44, 20);
		spnL.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int currentValue = (int) spnL.getValue();
				// Si el valor es menor que 1, establecer el valor mínimo como 1
				if (currentValue < 1) {
					spnL.setValue(1);
				}
			}
		});
		spnL.setVisible(false);
		contentPane.add(spnL);

		SpinnerNumberModel spinnerS = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		spnS = new JSpinner(spinnerS);
		spnS.setFont(new Font("Arial", Font.PLAIN, 15));
		spnS.setBounds(362, 146, 44, 20);
		spnS.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int currentValue = (int) spnS.getValue();
				// Si el valor es menor que 1, establecer el valor mínimo como 1
				if (currentValue < 1) {
					spnS.setValue(1);
				}
			}
		});
		spnS.setVisible(false);
		contentPane.add(spnS);

		SpinnerNumberModel spinnerZ = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		spnZ = new JSpinner(spinnerZ);
		spnZ.setFont(new Font("Arial", Font.PLAIN, 15));
		spnZ.setBounds(426, 146, 44, 20);
		spnZ.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int currentValue = (int) spnZ.getValue();
				// Si el valor es menor que 1, establecer el valor mínimo como 1
				if (currentValue < 1) {
					spnZ.setValue(1);
				}
			}
		});
		spnZ.setVisible(false);
		contentPane.add(spnZ);

		JLabel lblNewLabel_2 = new JLabel("__________________________");
		lblNewLabel_2.setBounds(176, 176, 146, 20);
		contentPane.add(lblNewLabel_2);

		lblMostrar = new JLabel("COMO VOLS QUE ES MOSTRE EL \r\nRESULTAT DE LA FABRICACIÓ?");
		lblMostrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMostrar.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMostrar.setBounds(20, 192, 504, 46);
		contentPane.add(lblMostrar);

		ButtonGroup group = new ButtonGroup();

		rdbConsola = new JRadioButton("CONSOLA");
		rdbConsola.setSelected(true);
		rdbConsola.setBounds(203, 233, 88, 21);

		rdbFitxer = new JRadioButton("FITXER");
		rdbFitxer.setBounds(203, 256, 88, 21);
		// Depenent el radioButton que elegisques, la opció de posar el nom de fitxer
		// s'oculta o no
		rdbConsola.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbConsola.isSelected()) {
					lblNomFitxer.setVisible(false);
					txtNomFitxer.setVisible(false);
					tipoOutput = "console";
				}
			}
		});
		rdbFitxer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbFitxer.isSelected()) {
					lblNomFitxer.setVisible(rdbFitxer.isSelected());
					txtNomFitxer.setVisible(rdbFitxer.isSelected());
					tipoOutput = "fitxer";
				}
			}
		});
		contentPane.add(rdbFitxer);
		contentPane.add(rdbConsola);

		group.add(rdbConsola);
		group.add(rdbFitxer);

		lblNomFitxer = new JLabel("Introdueix el nom del fitxer:");
		lblNomFitxer.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomFitxer.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNomFitxer.setBounds(84, 283, 163, 20);
		lblNomFitxer.setVisible(false);
		contentPane.add(lblNomFitxer);

		txtNomFitxer = new JTextField();
		txtNomFitxer.setFont(new Font("Arial", Font.PLAIN, 12));
		txtNomFitxer.setBounds(257, 283, 96, 19);
		contentPane.add(txtNomFitxer);
		txtNomFitxer.setColumns(10);
		txtNomFitxer.setVisible(false);

		btnOrdenarFabricacio = new JButton("ORDENAR FABRICACIO");
		btnOrdenarFabricacio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realitzarFabricacio();
			}
		});
		btnOrdenarFabricacio.setFont(new Font("Arial", Font.PLAIN, 15));
		btnOrdenarFabricacio.setBounds(149, 313, 224, 35);
		contentPane.add(btnOrdenarFabricacio);

	}

	/**
	 * REALITZA L'ACCIÓ AL PULSAR EL BOTÓ. EXECUTA LA FABRICACIÓ
	 */
	public void realitzarFabricacio() {
		ArrayList<String> tipusPeces = new ArrayList<>();
		ArrayList<Integer> quantitatPeces = new ArrayList<>();

		if (ckbTipoI.isSelected()) {
			tipusPeces.add("I");
			quantitatPeces.add((int) spnI.getValue());
		}

		if (ckbTipoO.isSelected()) {
			tipusPeces.add("O");
			quantitatPeces.add((int) spnO.getValue());
		}

		if (ckbTipoT.isSelected()) {
			tipusPeces.add("T");
			quantitatPeces.add((int) spnT.getValue());
		}

		if (ckbTipoJ.isSelected()) {
			tipusPeces.add("J");
			quantitatPeces.add((int) spnJ.getValue());
		}

		if (ckbTipoL.isSelected()) {
			tipusPeces.add("L");
			quantitatPeces.add((int) spnL.getValue());
		}

		if (ckbTipoS.isSelected()) {
			tipusPeces.add("S");
			quantitatPeces.add((int) spnS.getValue());
		}

		if (ckbTipoZ.isSelected()) {
			tipusPeces.add("Z");
			quantitatPeces.add((int) spnZ.getValue());
		}

		if (tipoOutput.equals("fitxer")) {
			String nomFitxer = txtNomFitxer.getText();
			Tetronimo.setLlistaPeces();
			Tetronimo.setLlistaResultats();
			Manufacture.startManufacturing(tipusPeces, quantitatPeces, tipoOutput, nomFitxer);
		} else {
			Tetronimo.setLlistaPeces();
			Tetronimo.setLlistaResultats();
			Manufacture.startManufacturing(tipusPeces, quantitatPeces, tipoOutput, null);
		}

	}
}
