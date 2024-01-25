package es.florida;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Pelis.
 */
public class Pelis {

	/**
	 * Get pelis json.
	 *
	 * @return Torna String amb el JSON de les dades de les películes
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String getPelisJson() throws IOException {
		String directori = "./pelis";

		// Llista per a emmagatzemar les dades de les películes
		List<JSONObject> pelicules = new ArrayList<>();

		// Obté la llista de fitxers en el directori
		File carpeta = new File(directori);
		File[] fitxers = carpeta.listFiles();

		if (fitxers != null) {
			for (File fitxer : fitxers) {
				// Llegir cada fitxer i extraure la informació
				JSONObject pelicula = llegirFitxer(fitxer);
				if (pelicula != null) {
					pelicules.add(pelicula);
				}
			}
		}

		// Construeix el JSON
		JSONObject jsonFinal = new JSONObject();
		jsonFinal.put("titols", pelicules);

		return jsonFinal.toString();
	}

	/**
	 * Llegir fitxer.
	 *
	 * @param fitxer File --> Paràmetre amb el fitxer que va a ser llegit
	 * @return Torna JSONObject amb un JSON de la película
	 * @throws IOException Exception
	 */
	private static JSONObject llegirFitxer(File fitxer) throws IOException {

		FileReader fr = new FileReader(fitxer);
		BufferedReader br = new BufferedReader(fr);

		// Llig el títol de la primera linia
		String titulo = br.readLine().substring("Titulo: ".length()).trim();

		// Crea l'objecte JSON per a la película
		JSONObject pelicula = new JSONObject();
		pelicula.put("id", fitxer.getName().replace(".txt", ""));
		pelicula.put("titol", titulo);

		br.close();
		fr.close();

		return pelicula;
	}

	/**
	 * Get dades pelicula.
	 *
	 * @param id String --> Parámetre amb l'id per a llegir les dades d'una pelicula
	 * @return Torna String amb el resultat.
	 * @throws IOException Exception
	 */
	public static String getDadesPelicula(String id) throws IOException {

		String rutaFitxer = "./pelis" + File.separator + id + ".txt";
		File fitxer = new File(rutaFitxer);

		if (fitxer.exists()) {
			List<String> ressenyes = llegirRessenyes(fitxer);

			JSONObject jsonFinal = new JSONObject();
			jsonFinal.put("id", id);
			jsonFinal.put("titol", obtenirTitol(fitxer));
			jsonFinal.put("ressenyes", ressenyes);

			return jsonFinal.toString();
		} else {
			return "Fitxer no encontrat";
		}
	}

	/**
	 * Llegir ressenyes.
	 *
	 * @param fitxer File --> Parámetre per a llegir les ressenyes del fitxer
	 * @return Torna List<String> amb les ressenyes del fitxer
	 * @throws IOException Exception
	 */
	private static List<String> llegirRessenyes(File fitxer) throws IOException {
		List<String> ressenyes = new ArrayList<>();
		FileReader fr = new FileReader(fitxer);
		BufferedReader br = new BufferedReader(fr);
		br.readLine(); // Salta la linia del títol

		String linia;
		while ((linia = br.readLine()) != null) {
			ressenyes.add(linia.trim());
		}

		br.close();
		fr.close();

		return ressenyes;
	}

	/**
	 * Obtenir titol.
	 *
	 * @param fitxer File --> Parámetre per a obtenir el títol del fitxer
	 * @return Torna String amb el titol del fitxer
	 * @throws IOException Exception
	 */
	private static String obtenirTitol(File fitxer) throws IOException {
		FileReader fr = new FileReader(fitxer);
		BufferedReader br = new BufferedReader(fr);
		String titol = br.readLine().substring("Titulo: ".length()).trim();

		br.close();
		fr.close();
		return titol;
	}

	/**
	 * Afegir nova ressenya.
	 *
	 * @param idPelicula String --> Parámetre per a identificar el fitxer de la
	 *                   pelicula
	 * @param nomUsuari  String --> Parámetre per a intriduïr el nou nom d'usuari
	 * @param ressenya   String --> Parámetre per a intriduïr la nova ressenya de
	 *                   l'usuari
	 * @return Torna boolean amb la resposta si s'ha afegit o no la ressenya
	 */
	public static boolean afegirNovaRessenya(String idPelicula, String nomUsuari, String ressenya) {

		String rutaFitxer = "./pelis" + File.separator + idPelicula + ".txt";
		File fitxer = new File(rutaFitxer);

		if (fitxer.exists()) {
			try {
				List<String> liniesActuals = new ArrayList<>();
				FileReader fr = new FileReader(fitxer);
				BufferedReader br = new BufferedReader(fr);

				String linia;
				while ((linia = br.readLine()) != null) {
					liniesActuals.add(linia);
				}
				br.close();

				liniesActuals.add(nomUsuari + ": " + ressenya);

				FileWriter fw = new FileWriter(fitxer);
				BufferedWriter bw = new BufferedWriter(fw);

				for (String liniaActual : liniesActuals) {
					bw.write(liniaActual);
					bw.newLine();
				}
				bw.close();
				fw.close();

				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Afegir nova peli.
	 *
	 * @param nomUsuari String --> Paràmetre amb el nom de l'usuari que va a afegir
	 *                  la nova pelicula
	 * @param titol     String --> Paràmetre amb el titol de la nova película
	 * @return Torna boolean si s'ha afegit correctament la nova película o no
	 * @throws IOException Exception
	 */
	public static boolean afegirNovaPeli(String nomUsuari, String titol) throws IOException {

		File carpeta = new File("./pelis");
		File[] fitxers = carpeta.listFiles();
		
		// Serà l'id de la nova Película
		int idPelicula = 0;

		if (fitxers != null) {
			for (File fitxer : fitxers) {
				String[] parts = fitxer.getName().split("\\.");
				String idString = parts[0];
				int id = Integer.parseInt(idString);
				if (id >= idPelicula) {
					idPelicula = id + 1;
				}
			}
		} else {
			idPelicula = 1;
		}

		File novaPeli = new File("./pelis/" + idPelicula + ".txt");
		List<String> linies = new ArrayList<>();

		linies.add("Titulo: " + titol);

		if (!novaPeli.exists()) {
			novaPeli.createNewFile();
			FileWriter fw = new FileWriter(novaPeli);
			BufferedWriter bw = new BufferedWriter(fw);

			for (String linia : linies) {
				bw.write(linia);
				bw.newLine();
			}
			bw.close();
			return true;
		}

		return false;
	}

	/**
	 * Afegir nou usuari autoritzat.
	 *
	 * @param nomUsuari String --> Paràmetre amb el nom de l'usuari que s'afegirà al fitxer d'autoritzats
	 * @return Torna boolean si s'ha afegit correctament el nou usuari o no
	 * @throws IOException Exception
	 */
	public static boolean afegirNouUsuariAutoritzat(String nomUsuari) throws IOException {

		File fitxer = new File("./autoritzats.txt");

		if (fitxer.exists()) {
			List<String> liniesActuals = new ArrayList<>();
			FileReader fr = new FileReader(fitxer);
			BufferedReader br = new BufferedReader(fr);
			
			String linia;
			while ((linia = br.readLine()) != null) {
				liniesActuals.add(linia);
			}
			br.close();
			fr.close();
			
			liniesActuals.add(nomUsuari);
			
			FileWriter fw = new FileWriter(fitxer);
			BufferedWriter bw = new BufferedWriter(fw);
			for (String liniaActual : liniesActuals) {
				bw.write(liniaActual);
				bw.newLine();
			}
			bw.close();
			fw.close();
			
			return true;
		}

		return false;
	}

}
