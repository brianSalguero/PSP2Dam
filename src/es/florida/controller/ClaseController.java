package es.florida.controller;

import es.florida.Pelis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Classe Controller on s'executarà totes les peticions GET y POST.
 */
@RestController
public class ClaseController implements Runnable {

	/**
	 * Carga usuaris autoritzats.
	 *
	 * @return Torna List<String> amb els usuaris autoritzats
	 * @throws IOException Exception
	 */
	private List<String> cargaUsuarisAutoritzats() throws IOException {
		List<String> usuaris = new ArrayList<>();
		FileReader fr = new FileReader("./autoritzats.txt");
		BufferedReader br = new BufferedReader(fr);
		String linea;

		while ((linea = br.readLine()) != null) {
			usuaris.add(linea.trim());
		}
		br.close();
		fr.close();

		return usuaris;
	}

	// URL esperat: http://localhost:8080/APIpelis/t?id=all || t?id={id}
	/**
	 * Mostrar dades.
	 *
	 * @param id String --> Paràmetre amb l'id que buscarà entre els fitxers
	 * @return Torna ResponseEntity<String> amb la resposta
	 * @throws IOException Exception
	 */
	@GetMapping("/APIpelis/t")
	ResponseEntity<String> mostrarDades(@RequestParam(value = "id") String id) throws IOException {
		if (id.equals("all")) {
			String pelisJson = Pelis.getPelisJson();
			return new ResponseEntity<>(pelisJson, HttpStatus.OK);
		} else {
			String idJson = Pelis.getDadesPelicula(id);
			if (idJson.equals("Fitxer no encontrat")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			} else {
				return new ResponseEntity<>(idJson, HttpStatus.OK);
			}
		}
	}

	// URL esperat: http://localhost:8080/APIpelis/novaRessenya
	/**
	 * Afegir nova ressenya.
	 *
	 * @param requestBody JsonNode --> JSON amb les noves dades per a insertar una
	 *                    nova ressenya
	 * @return Torna ResponseEntity<String> amb la resposta
	 * @throws IOException Exception
	 */
	@PostMapping("/APIpelis/novaRessenya")
	ResponseEntity<String> afegirNovaRessenya(@RequestBody JsonNode requestBody) throws IOException {
		String idPelicula = requestBody.get("id").asText();
		String nomUsuari = requestBody.get("usuari").asText();
		String ressenya = requestBody.get("ressenya").asText();

		List<String> llistaUsuaris = cargaUsuarisAutoritzats();

		if (!llistaUsuaris.contains(nomUsuari)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		boolean resposta = Pelis.afegirNovaRessenya(idPelicula, nomUsuari, ressenya);

		if (resposta) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// URL esperat: http://localhost:8080/APIpelis/novaPeli
	/**
	 * Afegir nova peli.
	 *
	 * @param requestBody JsonNode --> JSON amb les noves dades per a insertar una
	 *                    nova peli
	 * @return Torna ResponseEntity<String> amb la resposta
	 * @throws IOException Exception
	 */
	@PostMapping("/APIpelis/novaPeli")
	ResponseEntity<String> afegirNovaPeli(@RequestBody JsonNode requestBody) throws IOException {
		String nomUsuari = requestBody.get("usuari").asText();
		String titol = requestBody.get("titol").asText();

		List<String> llistaUsuaris = cargaUsuarisAutoritzats();

		if (!llistaUsuaris.contains(nomUsuari)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		boolean resposta = Pelis.afegirNovaPeli(nomUsuari, titol);

		if (resposta) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// URL esperat: http://localhost:8080/APIpelis/nouUsuari
	/**
	 * Afegir nou usuari autoritzat.
	 *
	 * @param requestBody JsonNode --> JSON amb les noves dades per a insertar un
	 *                    nou usuari autoritzat
	 * @return Torna ResponseEntity<String> amb la resposta
	 * @throws IOException Exception
	 */
	@PostMapping("/APIpelis/nouUsuari")
	ResponseEntity<String> afegirNouUsuariAutoritzat(@RequestBody JsonNode requestBody) throws IOException {
		String nomUsuari = requestBody.get("usuari").asText();

		boolean resposta = Pelis.afegirNouUsuariAutoritzat(nomUsuari);

		if (resposta) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/**
	 * Métode Run.
	 */
	public void run() {
	}
}
