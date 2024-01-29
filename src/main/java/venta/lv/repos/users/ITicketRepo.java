package venta.lv.repos.users;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import venta.lv.models.Trip;
import venta.lv.models.users.Ticket;

public interface ITicketRepo extends CrudRepository<Ticket, Long>{

	boolean existsByIsChild(boolean isChild);

	Object findByIsChild(boolean isChild);

	boolean existsByTripIdtr(long idtr);

	Object findByTripIdtr(long idtr);

	Ticket save(Ticket ticket);

	Page<Ticket> findAll(Pageable pageable);

}
