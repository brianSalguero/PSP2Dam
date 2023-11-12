package es.florida.Ae1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * CLASE ON S'ENCARGARÀ DE FER TOT EL PROCÉS DE FABRICACIÓ D'ORDRES
 */
public class Tetronimo implements Runnable {
	private String nomPeça;
	private String tipoOutput;
	private String nomFitxer;
	private int tempsManufacture;

	/**
	 * Llista de resultats de la execució de fabricació
	 */
	private static ArrayList<String> llistaResultats = new ArrayList<>();

	/**
	 * Llista de Peces que s'han fabricat
	 */
	private static final ArrayList<String> llistaPeces = new ArrayList<>();

	/**
	 * @param peçaNom         Nom de la peça
	 * @param tipoEixida      Tipo de eixida del resultat
	 * @param nomFit          Nom del fitxer que passa l'usuari
	 * @param tempsManufactur Temps que tarda cada peça
	 */
	public Tetronimo(String peçaNom, String tipoEixida, String nomFit, int tempsManufactur) {
		nomPeça = peçaNom;
		tipoOutput = tipoEixida;
		nomFitxer = nomFit;
		tempsManufacture = tempsManufactur;
	}

	/**
	 * Junta tots els procesos de fabricació
	 */
	@Override
	public void run() {
		try {
			if ("fitxer".equals(tipoOutput) && nomFitxer != null) {
				String ManPieza = "Manufacturing" + nomPeça + "\n Writing to file...\n";
				llistaResultats.add(ManPieza);
			} else {
				System.out.println("Manufacturing " + nomPeça);
			}
			procesFabricacio(tempsManufacture, tipoOutput, nomPeça);
			afegirPecaALlista(nomPeça, tipoOutput);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escriu els resultats en un fitxer
	 * 
	 * @param nom Nom del fitxer que passa l'usuari
	 */
	public static void escriuResultatsFitxer(String nom) {
		String nomArchivo = nom + ".txt";

		try {
			FileWriter fw = new FileWriter(nomArchivo, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (String linea : llistaResultats) {
				bw.write(linea + "\n");
			}
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Realitza el temps de fabricació segons la peça
	 * 
	 * @param tempsFabricacio Temps de fabricació que tarda cada peça
	 * @param tipoEixida      Tipo de eixida del resultat
	 * @param peçaNom         Nom de la peça
	 */
	public static void procesFabricacio(int tempsFabricacio, String tipoEixida, String peçaNom) {
		long tempsInici = System.currentTimeMillis();
		long tempsFinal = tempsInici + tempsFabricacio;
		while (System.currentTimeMillis() < tempsFinal) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("console".equals(tipoEixida)) {
			System.out.println("Executant la fabricació de " + peçaNom);
		} else {
			String resultat = "Executant la fabricació de " + peçaNom + "\n";
			llistaResultats.add(resultat);
		}
	}

	/**
	 * Afegix la peça a la llista de peces.
	 * 
	 * @param nomPeça    Nom de la Peça
	 * @param tipoEixida Tipo d'eixida de resultats.
	 */
	public static void afegirPecaALlista(String nomPeça, String tipoEixida) {
		synchronized (llistaPeces) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String dataHora = dateFormat.format(new Date());
			String nomPeca = nomPeça + "_" + dataHora;
			llistaPeces.add(nomPeca);
			/**
			 * Depenent del tipo de eixida que vol l'usuari, executarà els resultats pero
			 * consola o per fitxer
			 */
			if ("console".equals(tipoEixida)) {
				System.out.println("\nPeca afegida a la llista: " + nomPeca);
				System.out.println("Peca fabricada amb èxit: " + nomPeca + "\n");
			} else {
				String resultat = "Peca afegida a la llista: " + nomPeca + "\n Peca fabricada amb èxit: " + nomPeca
						+ "\n";
				llistaResultats.add(resultat);
			}
		}
	}

	/**
	 * Escriu la llista de Peces en un fitxer
	 * 
	 * @param Llista     Llista de Peces
	 * @param tipoOutput Tipo d'eixida de resultats.
	 */
	public static void escriureLlistaEnFitxer(ArrayList<String> llista, String tipoOutput) {
		synchronized (llista) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String dataHora = dateFormat.format(new Date());
			String nomFitxer = "LOG_" + dataHora + ".txt";
			try {
				FileWriter fw = new FileWriter(nomFitxer);
				BufferedWriter bw = new BufferedWriter(fw);
				for (String peca : llista) {
					bw.write(peca + System.lineSeparator());
				}
				bw.close();
				fw.close();
				if ("console".equals(tipoOutput)) {
					System.out.println("Llista de peces escrita en el fitxer: " + nomFitxer);
				} else {
					String escriuLlist = "Llista de peces escrita en el fitxer: " + nomFitxer;
					llistaResultats.add(escriuLlist);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * GET LLISTA DE RESULTATS
	 * 
	 * @return ArrayList<String> de resultats
	 */
	public static ArrayList<String> getLlistaResultats() {
		return llistaResultats;
	}

	/**
	 * GET LLISTA DE PECES
	 * 
	 * @return ArrayList<String> de peçes
	 */
	public static ArrayList<String> getLlistaPeces() {
		return llistaPeces;
	}
	
	public static void setLlistaPeces() {
		llistaPeces.clear();
	}
	
	public static void setLlistaResultats() {
		llistaResultats.clear();
	}

}
