package it.prova.gestioneagendarest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestioneagendarest.dto.UtenteDTO;
import it.prova.gestioneagendarest.model.Agenda;
import it.prova.gestioneagendarest.model.Utente;
import it.prova.gestioneagendarest.repository.agenda.AgendaRepository;
import it.prova.gestioneagendarest.web.api.exception.AgendaNotFoundException;
import it.prova.gestioneagendarest.web.api.exception.UtenteNonCombaciaException;



@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl implements AgendaService{
	
	@Autowired
	private AgendaRepository repository;
	@Autowired
	private UtenteService utenteService;

	public List<Agenda> listAllElements(boolean eager) {
		if (eager)
			return (List<Agenda>) repository.findAllAgendaEager();

		return (List<Agenda>) repository.findAll();
	}
	
	public List<Agenda> listAllElementsSingoloUtente(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UtenteDTO utenteItem=UtenteDTO.buildUtenteDTOFromModelConAgende(utenteService.findByUsername(username),false);
		return repository.findAllAgendaByUtente(utenteItem.getId());
	}

	public Agenda caricaSingoloElemento(Long id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		if(!utenteService.findByUsername(username).isAdmin()
				|| utenteService.findByUsername(username).getId() == repository.findById(id).get().getUtente().getId()) {
		return repository.findById(id).orElse(null);
		}else {
			throw new UtenteNonCombaciaException("Utente non ammesso!");
		}
	}

	public Agenda caricaSingoloElementoEager(Long id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		if(!utenteService.findByUsername(username).isAdmin()
				|| utenteService.findByUsername(username).getId() == repository.findById(id).get().getUtente().getId()) {
		return repository.findSingleAgendaEager(id);
		}else {
			throw new UtenteNonCombaciaException("Utente non ammesso!");
		}
	}

	@Transactional
	public Agenda aggiorna(Agenda agendaInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UtenteDTO utenteItem=UtenteDTO.buildUtenteDTOFromModelConAgende(utenteService.findByUsername(username),false);
		agendaInstance.setUtente(utenteService.findByUsername(username));
		if(agendaInstance.getUtente().getId() != utenteItem.getId())
			throw new UtenteNonCombaciaException("Utente non ammesso!");
		return repository.save(agendaInstance);
	}

	@Transactional
	public Agenda inserisciNuovo(Agenda agendaInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		agendaInstance.setUtente(utenteService.findByUsername(username));
		if(!agendaInstance.getUtente().equals(utenteService.findByUsername(username)))
			throw new UtenteNonCombaciaException("Utente non ammesso!");
		return repository.save(agendaInstance);
	}

	@Transactional
	public void rimuovi(Long idToRemove) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		repository.findById(idToRemove)
				.orElseThrow(() -> new AgendaNotFoundException("Agenda not found con id: " + idToRemove));
		
		if(repository.findSingleAgendaEager(idToRemove).getUtente().getId() != utenteService.findByUsername(username).getId())
			throw new UtenteNonCombaciaException("Utente non combacia!");
		repository.deleteById(idToRemove);
	}
	
	@Transactional
	public List<Agenda> findByExample(Agenda example) {
		return repository.findByExample(example);
	}

	@Transactional
	public List<Agenda> findByDescrizione(String descrizione) {
		return repository.findByDescrizione(descrizione);
	}


}
