package es.florida.ae2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * CLASSE MISSATGERIA QUE REPARTIX ELS MISSATGES
 */
public class MissatgeriaClient implements Runnable {
	private Socket socket;
	private String usuari;

	private ArrayList<MissatgeriaClient> llistaConectats = Servidor.getLlistaAutenticats();

	/**
	 * Constructor amb el socket del Servidor per a enviar y rebre missatges
	 * 
	 * @param socket Socket del servidor
	 */
	public MissatgeriaClient(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Retorna el nom del usuari
	 * 
	 * @return String nom del usuari
	 */
	public String getUsuari() {
		return usuari;
	}

	/**
	 * Métode per a verificar les credencials que envia el client
	 * 
	 * @param usuari      Paràmetre amb usuari a comprovar
	 * @param contrasenya Paràmetre amb la contrasenya a comprovar
	 * @return Torna true or false depén de la verificació
	 */
	private boolean verificarCredencials(String usuari, String contrasenya) {
		try {
			/**
			 * Recorre la llista y si l'usuari conté un espai en blanc o té ja un client
			 * conectat amb el mateix nom tornarà false.
			 */
			for (MissatgeriaClient client : llistaConectats) {
				if (client.getUsuari().contains(usuari) || usuari.contains(" ")) {
					return false;
				}
			}

			/**
			 * Llegirà el txt amb les credencials
			 */
			FileReader fr = new FileReader("./credencials.txt");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);

			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				if (partes.length == 2) {
					String usuariFitxer = partes[0].trim();
					String contrasenyaFitxer = partes[1].trim();
					/**
					 * Si verifiquen l'usuari afegirà l'usuari a la llista de conectats
					 */
					if (usuariFitxer.equals(usuari) && contrasenyaFitxer.equals(contrasenya)) {
						Servidor.afegirUsuariConectat(this);
						return true;
					}
				}
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Métode per a enviar el missatge
	 * 
	 * @param missatge Missatge que s'enviarà
	 * @throws IOException Exception
	 */
	public void enviarMissatge(String missatge) throws IOException {
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os);
		pw.println(missatge);
		pw.flush();
	}

	/**
	 * Métode per a enviar missatge privat y cridar al métode del Servidor
	 * 
	 * @param destinatari Paràmetre qui rebr'a el missatge
	 * @param contingut   Paràmetre amb el contingut del missatge
	 * @throws IOException Exception
	 */
	private void enviarMissatgePrivat(String destinatari, String contingut) throws IOException {
		boolean conectat = false;
		/**
		 * Recorre la llista de conectats i si conté un usuari amb el nom del
		 * destinatari i si esta conectat cambiarà conectat a true;
		 */
		for (MissatgeriaClient client : llistaConectats) {
			if (client.getUsuari().contains(destinatari)) {
				Servidor.enviarMissatgePrivat(usuari, destinatari, contingut);
				conectat = true;
				break;
			}
		}
		/**
		 * Si el destinatari no està conectat tornarà un missatge que el destinatari no
		 * està conectat
		 */
		if (!conectat) {
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			pw.println("El destinatari " + destinatari + " no está conectat.");
			pw.flush();
		}
	}

	/**
	 * Métode run per a ejecutar la Missatgeria
	 */
	public void run() {
		try {
			boolean continuar = false;
			/**
			 * Mentres el client no haja iniciat sessió, no continuarà
			 */
			while (!continuar) {
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader bfr = new BufferedReader(isr);
				System.err.println("SERVIDOR >> Esperant credencials");
				usuari = bfr.readLine();
				String contrasenya = bfr.readLine();

				/**
				 * Fa el métode de la verificació de les credencials. Si es true torna un
				 * missatge 'OK' al client i ja podrà continuar per a envair els missatges
				 */
				if (verificarCredencials(usuari, contrasenya)) {
					System.err.println("SERVIDOR >> Usuari " + usuari + " s'ha conectat");
					PrintWriter pw = new PrintWriter(socket.getOutputStream());
					pw.println("Credencials correctes");
					pw.println("OK");
					pw.flush();
					continuar = true;
				} else {
					System.err.println("SERVIDOR >> Error al verificar les credencials");
					PrintWriter pw = new PrintWriter(socket.getOutputStream());
					pw.println("Error al verificar les credencials. Torna a posar les credencials");
					pw.println("");
					pw.flush();
				}
			}
			/**
			 * Ejecutarà la missatgeria
			 */
			while (true) {
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader bfr = new BufferedReader(isr);
				String missatge = bfr.readLine();
				if (missatge != null) {
					/**
					 * Si el missatge es igual a '?' rebrà el client amb el noms dels usuaris
					 * conectats
					 */
					if (missatge.equals("?")) {
						Servidor.enviarUsuaris(this);
					} else if (missatge.startsWith("@")) {
						/**
						 * Si el missatge comença amb '@' enviarà un missatge privat al destinatari
						 * sempre i quan el destinatari estiga conectat
						 */
						int espaciIndex = missatge.indexOf(' ');
						if (espaciIndex != -1) {
							String destinatari = missatge.substring(1, espaciIndex);
							String contingut = missatge.substring(espaciIndex + 1);
							enviarMissatgePrivat(destinatari, contingut);
						}
					} else if (missatge.equals("exit")) {
						/**
						 * Si el missatge es igual a 'exit' tancarà la sessió
						 */
						System.out.println(usuari + " ha tancat sessio");
						/**
						 * Elimina els usuaris de la llista de conectats
						 */
						Servidor.eliminarDeUsuarisConectats(this);
						/**
						 * Torna el missatge 'exit' al client i tanca la sessió
						 */
						Servidor.enviarEixida(this);
					} else {
						/**
						 * Envia el missatge a tots els usuaris conectats
						 */
						Servidor.enviarMissatgeATots(usuari, missatge, this);
					}
				}
			}

		} catch (SocketException e) {
			/**
			 * En cas de haver un problema i s'ha tancat la sessió d'un client, eliminarà de
			 * la llista dels conectats
			 */
			for (MissatgeriaClient client : llistaConectats) {
				if (client.equals(this)) {
					Servidor.eliminarDeUsuarisConectats(this);
					break;
				}
			}
			System.err.println("SERVIDOR >>> El client " + usuari + " s'ha desconectat.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("SERVIDOR >>> Error.");
		}
	}
}
