package es.florida.ae2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * CLASSE CLIENT
 */
public class Client {

	private static String missatge = "";
	private static String usuari;

	/**
	 * Métode main de Clients
	 * 
	 * @param args Arguments
	 * @throws UnknownHostException Exceptions
	 * @throws IOException          Exceptions
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("CLIENT >>> Connexio al servidor");
		@SuppressWarnings("resource")
		Socket socket = new Socket();
		boolean conexioEstableida = false;
		
		while (!conexioEstableida) {
			try {
				System.out.print("IP: ");
				String ip = scanner.nextLine();
				System.out.print("Port: ");
				String pt = scanner.nextLine();
				int port = Integer.parseInt(pt);
				InetSocketAddress direccio = new InetSocketAddress(ip, port);
				socket.connect(direccio);
				conexioEstableida = true;
			} catch (IOException e) {
				System.err.println("CLIENT >>> Error Conexio amb Servidor");
				System.out.println();
			}
		}

		boolean autenticat = false;

		/**
		 * Mentres l'usuari no estiga Autenticat correctament, tornarà a pedir-lo fins
		 * que escriga el client les credencials correctes
		 */
		while (!autenticat) {
			System.out.println("AUTENTICACIO");
			System.out.print("Usuari: ");
			usuari = scanner.nextLine();
			System.out.print("Contrasenya: ");
			String contrasenya = scanner.nextLine();

			// Enviem l'usuari y la contrasenya al servidor
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			pw.println(usuari);
			pw.println(contrasenya);
			pw.flush();

			/**
			 * Rebem la resposta del Servidor si estan correctament les credencials
			 */
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bfr = new BufferedReader(isr);
			String resposta = bfr.readLine();
			String correcte = bfr.readLine();
			System.err.println(resposta);
			System.err.println(correcte);

			if (correcte.equals("OK")) {
				autenticat = true;
			} else {
				System.out.println("CLIENT >> Intenta de nou.");
			}
		}

		/**
		 * Llançem un fil per a rebre els missatges sempre
		 */
		Thread recepcioMissatges = new Thread(() -> {
			try {
				while (true) {
					InputStream is = socket.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader bfr = new BufferedReader(isr);

					String missatgeRebut = bfr.readLine();
					System.out.println(missatgeRebut);

					if (missatgeRebut.equals("exit")) {
						System.out.println("Tancant Sessio");
						System.exit(0);
						break;
					}
				}
			} catch (SocketException e) {
				System.err.println("ERROR EN SERVIDOR >> El servidor s'ha desconectat");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		recepcioMissatges.start();

		/**
		 * Presionarem ENTER per a enviar missatges. Paral·lelament s'ejecutarà el fil
		 * per a rebre els missatges S'obrirà una Vista per a rebre els missatges
		 */
		while (true) {
			System.out.println("Pressiona ENTER per a enviar missatge");
			scanner.nextLine();
			VistaMissatge vista = new VistaMissatge();
			vista.setLblTitol(usuari + " (exit per a tancar sessio)");
			/**
			 * 
			 */
			vista.setBtnEnviar(new ActionListener() {
				/**
				 * Event per a enviar el Missatge
				 * 
				 * @param e
				 */
				public void actionPerformed(ActionEvent e) {
					missatge = vista.getTxtMissatge();
					// Enviarem el missatge introduït al Servidor per a enviar-lo segons el missatge
					OutputStream os;
					try {
						os = socket.getOutputStream();
						PrintWriter pw = new PrintWriter(os);
						pw.println(missatge);
						pw.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					vista.dispose();
				}
			});
			vista.setBtnCancelar(new ActionListener() {
				/**
				 * Event per a Cancelar el missatge
				 * 
				 * @param e
				 */
				public void actionPerformed(ActionEvent e) {
					vista.dispose();
				}
			});
		}
	}
}
