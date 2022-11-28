package it.prova.gestioneagendarest.repository.agenda;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestioneagendarest.model.Agenda;

public interface AgendaRepository extends CrudRepository<Agenda, Long>,CustomAgendaRepository {
	@Query("from Agenda a join fetch a.utente where a.id = ?1")
	Agenda findSingleAgendaEager(Long id);

	List<Agenda> findByDescrizione(String descrizione);

	@Query("select a from Agenda a join fetch a.utente")
	List<Agenda> findAllAgendaEager();
	
	@Query("select a from Agenda a left join fetch a.utente u where u.id=?1")
	List<Agenda> findAllAgendaByUtente(Long id);
}
