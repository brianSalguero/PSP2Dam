package es.florida.Ae1;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CLASE QUE EXECUTARÀ LA FABRICACIÓ DE PEÇES
 */
public class Manufacture {

	private static int MAX_MACHINES = 8;

	/**
	 * Executará la Fabricació de Peçes
	 * 
	 * @param tipusPeces      ArrayList<String> on conté els tipus de peces que ha
	 *                        seleccionat l'usuari
	 * @param quantitatsPeces ArrayList<Integer> on conté el numero de peces de cada
	 *                        tipus
	 * @param tipoOutput      Tipo d'eixida ( Eixida de missatges console/ fitxer)
	 * @param nomFitxer       Nom del fitxer que passa l'usuari
	 */
	public static void startManufacturing(ArrayList<String> tipusPeces, ArrayList<Integer> quantitatsPeces,
			String tipoOutput, String nomFitxer) {
		/**
		 * Executador de la fabrica de peces
		 */
		ExecutorService executorService = Executors.newFixedThreadPool(MAX_MACHINES);

		for (int i = 0; i < tipusPeces.size(); i++) {
			String tipus = tipusPeces.get(i);
			int quantitat = quantitatsPeces.get(i);

			for (int j = 1; j <= quantitat; j++) {
				int tempsManufacture = getTempsFabricacioPerTipus(tipus);
				Tetronimo thread = new Tetronimo(tipus + j, tipoOutput, nomFitxer, tempsManufacture);
				executorService.execute(thread);
			}
		}
		/**
		 * Terminarà la execució
		 */
		try {
			executorService.shutdown();
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/**
		 * Escriu la llista de peces que s'ha executat en un fitxer.
		 */
		ArrayList<String> llista = Tetronimo.getLlistaPeces();
		Tetronimo.escriureLlistaEnFitxer(llista, tipoOutput);

		/**
		 * En cas que l'usuari elegix els resultats en un fitxer, escriu tots els
		 * resultats en un fitxer.
		 */
		if ("fitxer".equals(tipoOutput) && nomFitxer != null) {
			Tetronimo.escriuResultatsFitxer(nomFitxer);
		}
	}

	/**
	 * @param tipus Tipus de peça que volen fabricar
	 * @return int: Numero de temps que tarda la peça en fabricar
	 */
	private static int getTempsFabricacioPerTipus(String tipus) {
		switch (tipus) {
		case "I":
			return 1000;
		case "O":
			return 2000;
		case "T":
			return 3000;
		case "J":
			return 4000;
		case "L":
			return 4000;
		case "S":
			return 5000;
		case "Z":
			return 5000;
		default:
			return 0;
		}
	}

}
