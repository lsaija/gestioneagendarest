package it.prova.gestioneagendarest.repository.agenda;

import java.util.List;

import it.prova.gestioneagendarest.model.Agenda;

public interface CustomAgendaRepository {
	List<Agenda> findByExample(Agenda example);

}
