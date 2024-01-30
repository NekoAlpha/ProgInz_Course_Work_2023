package venta.lv.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import venta.lv.models.Trip;
import venta.lv.models.users.Cashier;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Ticket;
import venta.lv.repos.ITripRepo;
import venta.lv.repos.users.ICasheirRepo;
import venta.lv.repos.users.ITicketRepo;
import venta.lv.services.ITicketCRUDService;

@Service
public class TicketServiceImplWithDB implements ITicketCRUDService{
	
	@Autowired
	private ITripRepo tripRepo;
	
	@Autowired
	private ITicketRepo ticketRepo;
	
	@Autowired
	private ICasheirRepo casheirRepo;
	
	@Override
	public List<Ticket> selectAllChildTickets(boolean isChild) throws Exception {
		List<Ticket> childtickets = new ArrayList<>();
		for(Ticket ticket : getAll(null)) {
			if(ticket.isChild()) {
				childtickets.add(ticket);
			}
		}
		return childtickets;

	}
	
	@Override
	public Page<Ticket> selectAllTicketsByTripId(long idtr, Pageable pageable) throws Exception {
		  if (ticketRepo.existsByTripIdtr(idtr)) {
		        return (Page<Ticket>) ((TicketServiceImplWithDB) ticketRepo.findByTripIdtr(idtr)).getAll(null);
		    } else {
		        throw new Exception("Wrong id");
		    }
	}
	
	@Override
	public float calculateIncomeOfTripByTripId(long idtr) {
		float earnings = 0;
		for(Ticket ticket : getAll(null)) {
			if(ticket.getTrip().getIdtr() == idtr) {
				earnings = earnings + ticket.getPrice();
			}
		}
		return earnings;
    }
	
	
	@Override
	public float calculateIncomeOfCashierByCashierId(long idc) throws Exception {
        Cashier cashier = casheirRepo.findById(idc).orElse(null);
        if (cashier == null) {
            throw new Exception("Cashier with ID " + idc + " not found");
        }
        float income = (float) ((Cashier) cashier).getTicket().stream()
                .mapToDouble(Ticket::getPrice) 
                .sum();
        return income;
    }

	
	@Override
	public void insertNewTicketByTripId(Trip Idtr, Ticket ticket){
			Ticket newticket = new Ticket(ticket.getPurchaseDateTime(), ticket.getPrice(), ticket.isChild(), ticket.getCashier(),  ticket.getTrip());
			getAll(null).and(newticket);
			ticketRepo.save(newticket);
		}
	

	@Override
	public Page<Ticket> getAll(Pageable pageable) {
		return ticketRepo.findAll(pageable);
	}
	
	@Override
	public List<Ticket> showAllTicketsPriceLessThan(int threshold) {
		List<Ticket> showAllTicketsPriceLessThan = new ArrayList<>();
		for(Ticket ticket : getAll(null)) {
			if(ticket.getPrice() < threshold) {
				showAllTicketsPriceLessThan.add(ticket);
			}
		}	
		return showAllTicketsPriceLessThan;
	}

//	@Override
//	public List<Ticket> getAllWithChildren() throws Exception {
//		if(ticketRepo.existsByIsChild(true))
//		{
//			return ((ITicketCRUDService) ticketRepo.findByIsChild(true)).getAll();
//		}
//		else
//		{
//			throw new Exception("Error");
//		}
//	}

	@Override
	public Ticket findTicketById(long idt) throws Exception {
		if(ticketRepo.existsById(idt))
		{
			return ticketRepo.findById(idt).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	@Override
	public Ticket addNewTicket(LocalDateTime purchasedDateTime, float price, boolean isChild, Cashier cashier,
			Trip trip, @Valid Ticket ticket) {
		Ticket temp = ticketRepo.save(new Ticket(purchasedDateTime, price, isChild, cashier, trip));
		return temp;
	}

	@Override
	public void insertNewTicket(Ticket ticket) {
		for(Ticket ticket2 : getAll(null)) {
			if(ticket2.getPurchaseDateTime().equals(ticket.getPurchaseDateTime()) && ticket2.getPrice() == (ticket.getPrice())) {
				return;
			}
		}
		getAll(null).and(ticket);
		ticketRepo.save(ticket);
	}

	@Override
	public void deleteTicketById(long idt) throws Exception {
		if(ticketRepo.existsById(idt)) {
			ticketRepo.deleteById(idt);
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	@Override
	public Ticket selectAllTicketsByID(long idt) throws Exception {
		if(ticketRepo.existsById(idt))
		{
			return ticketRepo.findById(idt).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	@Override
	public void updateTicket(long idt, @Valid Ticket updatedTicket) {
		ticketRepo.findById(idt).ifPresent(ticket -> {
			ticket.setCashier(updatedTicket.getCashier());
			ticket.setChild(updatedTicket.isChild());
			ticket.setPrice(updatedTicket.getPrice());
			ticket.setPurchaseDateTime(updatedTicket.getPurchaseDateTime());
			ticket.setTrip(updatedTicket.getTrip());
			ticketRepo.save(ticket);
		});
	}

	
	
}
