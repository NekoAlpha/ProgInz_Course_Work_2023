//package venta.lv.services.impl;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import jakarta.validation.Valid;
//import venta.lv.models.Trip;
//import venta.lv.models.users.Cashier;
//import venta.lv.models.users.Ticket;
//import venta.lv.services.ITicketCRUDService;
//
//public class TicketServiveImpl implements ITicketCRUDService{
//	
//	private ArrayList<Ticket> allTickets = new ArrayList<>(Arrays.asList(new Ticket(LocalDateTime.now(), 10, false, null, null)));
//	
//	@Override
//	public Ticket selectAllChildTickets(boolean isChild) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Ticket> showAllTicketsPriceLessThan(int threshold) {
//		if (threshold > 0) {
//
//			ArrayList<Ticket> allTicketsWherePriceIsLow = new ArrayList<>();
//			for (Ticket temp : allTickets) {
//				if (temp.getPrice() < threshold) {
//					allTicketsWherePriceIsLow.add(temp);
//				}
//			}
//			return allTicketsWherePriceIsLow;
//		}
//		return new ArrayList<>();
//	}
//
//	@Override
//	public ArrayList<Ticket> selectAllTicketsByTripId(long idtr) {
//		return null;
//		if(idtr == idtr) {
//			ArrayList<Ticket> allTicketsWithTripId = new ArrayList<>();
//			for (Ticket temp : allTickets) {
//				if (temp.getIdtr() == idtr) {
//					allTicketsWithTripId.add(temp);
//		
//				}
//			}
//			return allTicketsWithTripId;
//		}
//		else
//		{
//			throw new Exception("Wrong trip id");
//		}
//	}
//
//	@Override
//	public Ticket insertNewTicketByTripId(Trip idtr) throws Exception {
//		
//		for (Ticket temp : allTickets) {
//			if (temp.getIdtr() == idtr) {
//				Ticket ticket = new Ticket();
//				return ticket;
//			}
//		}
//		throw new Exception("Wrong id");
//	}
//
//	@Override
//	public double calculateIncomeOfTripByTripId(long idtr) {
//		// TODO Auto-generated method stub
//		return (Double )null;
//	}
//
//	@Override
//	public double calculateIncomeOfCashierByCashierId(long idc) {
//		// TODO Auto-generated method stub
//		return (Double) null;
//	}
//
//	@Override
//	public List<Ticket> getAll() {
//		return allTickets;
//	}
//
//	@Override
//	public Ticket findTicketById(long idt) throws Exception {
//		for (Ticket temp : allTickets) {
//			if (temp.getIdt() == idt) {
//				return temp;
//			}
//		}
//		throw new Exception("Wrong id");
//	}
//
//	@Override
//	public Ticket addNewTicket(LocalDateTime purchasedDateTime, float price, boolean isChild, Cashier cashier, Trip trip,
//			@Valid Ticket ticket) {
//		for (Ticket temp : allTickets) {
//			if (temp.getCashier().equals(cashier)
//					&& temp.getPrice() == price && temp.getPurchaseDateTime().equals(purchasedDateTime)
//					&& temp.getTrip().equals(trip)) {
//				return temp;
//			}
//			}
//					
//			Ticket newProduct = new Ticket(purchasedDateTime, price, isChild, cashier, trip);
//			allTickets.add(newProduct);
//			return newProduct;
//	}
//
//	@Override
//	public List<Ticket> getAllWithChildren() {
//		for (Ticket temp : allTickets) {
//			if (temp.isChild() == true) {
//				return (List<Ticket>) temp;
//			}
//		}
//		return allTickets;
//	}
//
//}
