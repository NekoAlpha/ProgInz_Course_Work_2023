package venta.lv.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import venta.lv.models.Trip;
import venta.lv.models.users.Cashier;
import venta.lv.models.users.Ticket;

public interface ITicketCRUDService {

	List<Ticket> selectAllChildTickets(boolean isChild) throws Exception;

	float calculateIncomeOfCashierByCashierId(long idc) throws Exception;
	
	Page<Ticket> getAll(Pageable pageable);

	List<Ticket> showAllTicketsPriceLessThan(int threshold);

	float calculateIncomeOfTripByTripId(long idtr);

	Ticket findTicketById(long idt) throws Exception;

	Ticket addNewTicket(LocalDateTime purchasedDateTime, float price, boolean isChild, Cashier cashier, Trip trip,
			@Valid Ticket ticket);

	void insertNewTicketByTripId(Trip Idtr, Ticket ticket) throws Exception;

	void insertNewTicket(Ticket ticket);

	Page<Ticket> selectAllTicketsByTripId(long idtr, Pageable pageable) throws Exception;

	void deleteTicketById(long id) throws Exception;








}
