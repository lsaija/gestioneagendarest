package it.prova.gestioneagendarest.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestioneagendarest.dto.AgendaDTO;
import it.prova.gestioneagendarest.model.Agenda;
import it.prova.gestioneagendarest.service.AgendaService;
import it.prova.gestioneagendarest.web.api.exception.AgendaNotFoundException;
import it.prova.gestioneagendarest.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("api/agenda")
public class AgendaController {
	
	@Autowired
	private AgendaService agendaService;

	@GetMapping
	public List<AgendaDTO> getAll() {
		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.listAllElements(true), true);
	}

	@PostMapping
	public AgendaDTO createNew(@Valid @RequestBody AgendaDTO agendaInput) {
	
		if (agendaInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Agenda agendaInserito = agendaService.inserisciNuovo(agendaInput.buildAgendaModel());
		return AgendaDTO.buildAgendaDTOFromModel(agendaInserito, true);
	}

	@GetMapping("/{id}")
	public AgendaDTO findById(@PathVariable(value = "id", required = true) long id) {
		Agenda agenda = agendaService.caricaSingoloElementoEager(id);

		if (agenda == null)
			throw new AgendaNotFoundException("Agenda not found con id: " + id);

		return AgendaDTO.buildAgendaDTOFromModel(agenda, true);
	}
	
	@PutMapping("/{id}")
	public AgendaDTO update(@Valid @RequestBody AgendaDTO agendaInput, @PathVariable(required = true) Long id) {
		Agenda agenda = agendaService.caricaSingoloElemento(id);

		if (agenda == null)
			throw new AgendaNotFoundException("Agenda not found con id: " + id);

		agendaInput.setId(id);
		Agenda agendaAggiornato = agendaService.aggiorna(agendaInput.buildAgendaModel());
		return AgendaDTO.buildAgendaDTOFromModel(agendaAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		agendaService.rimuovi(id);
	}

	//Implementare
	@PostMapping("/search")
	public List<AgendaDTO> search(@RequestBody AgendaDTO example) {
		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.findByExample(example.buildAgendaModel()),
				false);
	}

	

}
