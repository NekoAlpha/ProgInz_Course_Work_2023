package venta.lv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import venta.lv.models.Trip;
import venta.lv.models.users.Cashier;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Ticket;
import venta.lv.services.ITicketCRUDService;

@Controller
public class TicketController {
	
	@Autowired
	private ITicketCRUDService ticketService;
	
	@GetMapping("/ticket/showAll")
    public String showAll(org.springframework.ui.Model ticket, @PageableDefault(size = 2) Pageable pageable) {
		Page<Ticket> tickets = ticketService.getAll(pageable);
		ticket.addAttribute("Ticket", tickets);
		return "ticket-all-page";
    }
	
	@GetMapping("/ticket/showOne/{id}")
    public String showById(@PathVariable("id") long id, org.springframework.ui.Model ticket) {
        
		try {
			
			Ticket temp = new Ticket();
			
			temp = ticketService.findTicketById(id);
			
			ticket.addAttribute("Ticket", temp);
			
			return "ticket-one-page";

		}catch (Exception e) {
			// TODO: handle exception
		}

		return "error-page";
	}
	
	@GetMapping("/ticket/showAll/onlyChild")
	public String onlyShowAllChildTickts(org.springframework.ui.Model ticket) throws Exception {
		List<Ticket> tickets = ticketService.selectAllChildTickets(true);
		ticket.addAttribute("Ticket", tickets);
		return "ticket-all-page";
		
	}
	
	@GetMapping("/ticket/showAll/less/{threshold}")
	public String showAllTicketsPriceLessThan(@PathVariable("threshold") int threshold, org.springframework.ui.Model ticket) {
		if(threshold > 0) {
			List<Ticket> tickets = ticketService.showAllTicketsPriceLessThan(threshold);
			ticket.addAttribute("Ticket", tickets);
			return "ticket-all-page";
		}
		else{
			return "error-page";//error-page
		}
	}
	
	@GetMapping("/ticket/showAll/trip/{idtr}")
	public String showAllTicketsByTripID(org.springframework.ui.Model ticket, Long idtr, Pageable pageable) throws Exception {
		Page<Ticket> tickets = ticketService.selectAllTicketsByTripId(idtr, pageable);
		ticket.addAttribute("Ticket", tickets);
		return "ticket-all-page";
		
	}
	
	@GetMapping("/ticket/calculate/trip/{tripid}")
	public String calculateTicketsByTripID(org.springframework.ui.Model ticket, Trip idtr) {
		return null;
		
	}
	
	@GetMapping("/ticket/calculate/cashier/{cashierid}")
	public String calculateTicketsByCashierID(Model model, Cashier idc) {
		return null;
		
	}
	
	@GetMapping("/ticket/add/{tripid}")
	public String addTicketByTripID(org.springframework.ui.Model ticket, long idtr) {
		try {
			Ticket temp = ticketService.findTicketById(idtr);

			ticket.addAttribute("Ticket", temp);
			return "ticket-add-page";
		}catch (Exception e) {
			
			return "error-page";
			
		}
		
	}
	
	@PostMapping("/ticket/add/{tripid}")
	public String addTicketByTripIDPost(@PathVariable("idtr") Trip idtr, @ModelAttribute("Ticket") @Valid Ticket ticket, BindingResult result) {
		if (result.hasErrors()) {
            return "ticket-add-page";
        } else {
            try {
            	ticketService.insertNewTicketByTripId(idtr, ticket);
                return "redirect:/ticket/showAll"; 
            } catch (Exception e) {
                return "error-page";
            }
        }
		
	}
	
	@GetMapping("/ticket/addNew")
    public String addNewTicket(org.springframework.ui.Model ticket) {
		ticket.addAttribute("ticket", new Ticket());
		return "ticket-add-page";
	}
	
	@PostMapping("/ticket/addNew")
	public String addNewTicketPost(@Valid Ticket Ticket, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "ticket-add-page";
		}
		Ticket ticket = new Ticket();
		//Ticket newTicket = new Ticket(Ticket.getPurchaseDateTime(), Ticket.getPrice(), Ticket.isChild(), Ticket.getCashier(), Ticket.getTrip());
		ticketService.insertNewTicket(ticket);

		return "redirect:/ticket/showAll";
		
	}
	
	@GetMapping("/ticket/delete/{id}")
    public String removeById(@PathVariable("id") long id, org.springframework.ui.Model ticket, @PageableDefault(size = 10) Pageable pageable) {
		try {
			ticketService.deleteTicketById(id);
			ticket.addAttribute("Ticket", ticketService.getAll(pageable));
					
			return "redirect:/ticket/showAll";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "error-page";
	}
	
	@GetMapping("/ticket/update/{idt}")
	public String updateTicketByID(@PathVariable("idt") long idt, org.springframework.ui.Model ticket) throws Exception {
	    Ticket updatedTicket = ticketService.selectAllTicketsByID(idt);

	    ticket.addAttribute("updatedTicket", updatedTicket); 

	    return "ticket-update-page"; 
	}
	
	@PostMapping("/ticket/update/{idt}")
	public String updateTicketByIDPost(@PathVariable("idt") String idt, @ModelAttribute("Ticket") @Valid Ticket ticket, BindingResult result) {
		System.out.println("Received idd: " + idt);
		System.out.println("Updated values: " + ticket);

		try {
		    long ticketId = Long.parseLong(idt);
		    System.out.println("Parsed driverId: " + ticketId);

		    if (result.hasErrors()) {
		        System.out.println("Validation errors: " + result.getAllErrors());
		        return "ticket-update-page";
		    } else {
		    	ticketService.updateTicket(ticketId, ticket);
		        System.out.println("Update successful");
		        return "redirect:/ticket/showAll";
		    }
		} catch (NumberFormatException e) {
		    System.out.println("NumberFormatException: " + e.getMessage());
		    return "error-page";
		} catch (Exception e) {
		    System.out.println("Exception: " + e.getMessage());
		    return "error-page";
		}
	}
	
}
