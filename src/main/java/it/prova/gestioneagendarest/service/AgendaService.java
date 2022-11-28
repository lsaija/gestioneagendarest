package it.prova.gestioneagendarest.service;

import java.util.List;

import it.prova.gestioneagendarest.model.Agenda;

public interface AgendaService {
	List<Agenda> listAllElements(boolean eager);

	Agenda caricaSingoloElemento(Long id);

	Agenda caricaSingoloElementoEager(Long id);

	Agenda aggiorna(Agenda filmInstance);

	Agenda inserisciNuovo(Agenda filmInstance);

	void rimuovi(Long idToRemove);

	List<Agenda> findByExample(Agenda example);

	List<Agenda> findByDescrizione(String descrizione);

}
