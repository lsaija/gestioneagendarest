package it.prova.gestioneagendarest.repository.ruolo;

import org.springframework.data.repository.CrudRepository;

import it.prova.gestioneagendarest.model.Ruolo;


public interface RuoloRepository extends CrudRepository<Ruolo, Long> {
	Ruolo findByDescrizioneAndCodice(String descrizione, String codice);
}
