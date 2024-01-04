package es.florida.ae2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * CLASSE SERVIDOR
 */
public class Servidor {
	/**
	 * Llista per a guardar tots els usuaris conectats
	 */
	private static ArrayList<MissatgeriaClient> llistaAutenticats = new ArrayList<>();

	/**
	 * Métode Main del Servidor
	 * 
	 * @param args Arguments
	 * @throws IOException Exception
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ServerSocket servidor = null;
		try {
			servidor = new ServerSocket(2345);
		} catch (IOException e) {
			System.err.println("\nSERVIDOR >>> Error");
			return;
		}
		System.err.println("\nSERVIDOR >> Escoltant...");

		/**
		 * Sempre esperarà una conexió del client
		 */
		while (true) {
			Socket connexio = servidor.accept();
			System.err.println("\nSERVIDOR >> Connexio rebuda! --> Llança nou fil");
			MissatgeriaClient missatgeriaClient = new MissatgeriaClient(connexio);
			Thread fil = new Thread(missatgeriaClient);
			fil.start();
		}
	}

	/**
	 * Métode pero a afegir un Usuari a la llista
	 * 
	 * @param client Paràmetre Client per a afegir a la llista
	 */
	public static synchronized void afegirUsuariConectat(MissatgeriaClient client) {
		llistaAutenticats.add(client);
	}

	/**
	 * Métode per a eliminar un Usuari de la llista
	 * 
	 * @param client Paràmetre Client per a eliminar de la llista
	 */
	public static synchronized void eliminarDeUsuarisConectats(MissatgeriaClient client) {
		llistaAutenticats.remove(client);
	}

	/**
	 * Métode per a retornar la llista dels Usuaris Conectats
	 * 
	 * @return Torna llista dels usuaris
	 */
	public static synchronized ArrayList<MissatgeriaClient> getLlistaAutenticats() {
		return llistaAutenticats;
	}

	/**
	 * Métode per a enviar un Missatge privat
	 * 
	 * @param remitent    Paràmetre amb l'usuari que envia el missatge
	 * @param destinatari Paràmetre amb l'usuari que rebrà el missatge
	 * @param missatge    Paràmetre amb el missatge
	 * @throws IOException Exception
	 */
	public static synchronized void enviarMissatgePrivat(String remitent, String destinatari, String missatge)
			throws IOException {
		/**
		 * Data actual amb format 'yyyy-MM-dd HH:mm:ss'
		 */
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String fechaFormat = localDate.format(format);

		System.out.println("\nMissatge privat " + remitent + " a " + destinatari + ": " + missatge);

		/**
		 * Recorre la llista per a enviar el missatge al destinatari
		 */
		for (MissatgeriaClient cliente : llistaAutenticats) {
			if (cliente.getUsuari().equals(destinatari)) {
				cliente.enviarMissatge(fechaFormat + " " + remitent + " (privat): " + missatge);
				break;
			}
		}

		/**
		 * Recorre la llista per a enviar el missatge a qui envia el missatge, el
		 * remitent
		 */
		for (MissatgeriaClient cliente : llistaAutenticats) {
			if (cliente.getUsuari().equals(remitent)) {
				cliente.enviarMissatge(fechaFormat + ": @" + destinatari + " " + missatge);
				break;
			}
		}
	}

	/**
	 * Métode per a tancar sessió
	 * 
	 * @param client Parámetre del client que tanca la sessió
	 * @throws IOException Exception
	 */
	public static synchronized void enviarEixida(MissatgeriaClient client) throws IOException {
		client.enviarMissatge("exit");
	}

	/**
	 * Métode pero a enviar el missatge a tots els clients conectats
	 * 
	 * @param usuari   Paràmetre amb l'usuari que envia el missatge
	 * @param missatge Parametre amb el missatge
	 * @param client   Paràmetre amb el client qui envia el missatge
	 * @throws IOException Exception
	 */
	public static synchronized void enviarMissatgeATots(String usuari, String missatge, MissatgeriaClient client)
			throws IOException {
		/**
		 * Data actual amb format 'yyyy-MM-dd HH:mm:ss'
		 */
		LocalDateTime ahora = LocalDateTime.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String fechaFormateada = ahora.format(formato);

		/**
		 * Retorna un missatge al client qui envia el missatge
		 */
		client.enviarMissatge(fechaFormateada + ": " + missatge);
		System.out.println("\n" + usuari + " a tots: " + missatge);

		/**
		 * Recorre la llista y envia el missatge a tots els clients
		 */
		for (MissatgeriaClient clientLlista : llistaAutenticats) {
			if (!clientLlista.getUsuari().equals(usuari)) {
				clientLlista.enviarMissatge(fechaFormateada + " " + usuari + ": " + missatge);
				System.out.println("Enviat a " + clientLlista.getUsuari() + ": " + missatge);
			}
		}
	}

	/**
	 * Métode per a enviar els noms dels usuaris al client
	 * 
	 * @param client Qui vol rebre els noms dels usuaris
	 * @throws IOException Exception
	 */
	public static synchronized void enviarUsuaris(MissatgeriaClient client) throws IOException {
		StringBuilder llistaUsuaris = new StringBuilder("Clients conectats: ");
		System.out.println("\nEnviant usuaris conectats a " + client.getUsuari());
		for (int i = 0; i < llistaAutenticats.size(); i++) {
			llistaUsuaris.append(llistaAutenticats.get(i).getUsuari());

			if (i < llistaAutenticats.size() - 1) {
				llistaUsuaris.append(" | ");
			}
		}
		client.enviarMissatge("?");
		client.enviarMissatge("" + llistaUsuaris.toString());
	}
}
