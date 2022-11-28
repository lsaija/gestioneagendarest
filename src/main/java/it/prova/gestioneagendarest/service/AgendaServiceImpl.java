package it.prova.gestioneagendarest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestioneagendarest.model.Agenda;
import it.prova.gestioneagendarest.repository.agenda.AgendaRepository;
import it.prova.gestioneagendarest.web.api.exception.AgendaNotFoundException;



@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl implements AgendaService{
	
	@Autowired
	private AgendaRepository repository;

	public List<Agenda> listAllElements(boolean eager) {
		if (eager)
			return (List<Agenda>) repository.findAllAgendaEager();

		return (List<Agenda>) repository.findAll();
	}

	public Agenda caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Agenda caricaSingoloElementoEager(Long id) {
		return repository.findSingleAgendaEager(id);
	}

	@Transactional
	public Agenda aggiorna(Agenda agendaInstance) {
		return repository.save(agendaInstance);
	}

	@Transactional
	public Agenda inserisciNuovo(Agenda agendaInstance) {
		return repository.save(agendaInstance);
	}

	@Transactional
	public void rimuovi(Long idToRemove) {
		repository.findById(idToRemove)
				.orElseThrow(() -> new AgendaNotFoundException("Agenda not found con id: " + idToRemove));
		repository.deleteById(idToRemove);
	}

	public List<Agenda> findByExample(Agenda example) {
		return repository.findByExample(example);
	}

	public List<Agenda> findByDescrizione(String descrizione) {
		return repository.findByDescrizione(descrizione);
	}


}
