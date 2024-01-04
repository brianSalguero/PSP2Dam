package es.florida.ae2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * CLASSE VISTA PER A ENVIAR EL MISSATGE
 */
public class VistaMissatge extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMissatge;
	private JButton btnEnviar;
	private JButton btnCancelar;
	private JLabel lbltitol;
	private JLabel lblIcon;

	/**
	 * Vista amb tots els components
	 */
	public VistaMissatge() {
		setTitle("Missatge");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 516, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtMissatge = new JTextField();
		txtMissatge.setFont(new Font("Arial", Font.PLAIN, 15));
		txtMissatge.setBounds(148, 38, 344, 24);
		contentPane.add(txtMissatge);
		txtMissatge.setColumns(10);

		lbltitol = new JLabel("titol");
		lbltitol.setFont(new Font("Arial", Font.PLAIN, 17));
		lbltitol.setHorizontalAlignment(SwingConstants.CENTER);
		lbltitol.setBounds(148, 10, 344, 24);
		contentPane.add(lbltitol);

		btnEnviar = new JButton("OK");
		btnEnviar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEnviar.setBounds(181, 72, 96, 24);
		contentPane.add(btnEnviar);

		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCancelar.setBounds(327, 72, 138, 24);
		contentPane.add(btnCancelar);

		lblIcon = new JLabel("");
		lblIcon.setBounds(26, 10, 96, 96);
		contentPane.add(lblIcon);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// La ruta de la imatge al principi es "./img/missatge.png"
		// Cambie la ruta per "../img/missatge.png" ja que tinc l'ejecutable dins de la
		// carpeta JAR
		setImage(lblIcon, "../img/missatge.png");

	}

	/**
	 * Torna el missatge
	 * 
	 * @return String Missatge
	 */
	public String getTxtMissatge() {
		return txtMissatge.getText();
	}

	/**
	 * Métode que mostra una imatge en un JLabel
	 * 
	 * @param label Paràmetre amb el JLabel
	 * @param ruta  Paràmetre amb la ruta de la imatge
	 */
	public void setImage(JLabel label, String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icon = new ImageIcon(
				image.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icon);
		this.repaint();
	}

	/**
	 * Métode Set que modifica el titol
	 * 
	 * @param titol Parametre String amb el títol
	 */
	public void setLblTitol(String titol) {
		lbltitol.setText(titol);
	}

	/**
	 * Métode Listener que fa l'acció al donar a enviar
	 * 
	 * @param listener Paràmetre amb el ActionListener
	 */
	public void setBtnEnviar(ActionListener listener) {
		btnEnviar.addActionListener(listener);
	}

	/**
	 * Métode Listener que fa l'acció al donar a Cancelar
	 * 
	 * @param listener Paràmetre amb el ActionListener
	 */
	public void setBtnCancelar(ActionListener listener) {
		btnCancelar.addActionListener(listener);
	}
}
