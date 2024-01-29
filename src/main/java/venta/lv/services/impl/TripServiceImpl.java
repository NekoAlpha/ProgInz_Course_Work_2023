//package venta.lv.services.impl;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import venta.lv.models.City;
//import venta.lv.models.Trip;
//import venta.lv.models.users.Driver;
//import venta.lv.models.users.Ticket;
//import venta.lv.repos.ITripRepo;
//import venta.lv.services.ITripCRUDService;
//
//public class TripServiceImpl implements ITripCRUDService {
//	
//	//private ArrayList<Trip> allTrips = new ArrayList<>(Arrays.asList(new Trip(LocalDateTime.now(), 2, null, null, null)));
//	
//	@Autowired
//	private ITripRepo tripRepo;
//	
//	@Override
//	public Trip selectAllTripsByDriverId(long id) {
//		return null;
//		if(id == id) {
//			ArrayList<Ticket> allTripsByDriverId = new ArrayList<>();
//			for (Ticket temp : allTrips) {
//				if (temp.getId() == id) {
//					allTripsByDriverId.add(temp);
//		
//				}
//			}
//			return allTripsByDriverId;
//		}
//		else
//		{
//			throw new Exception("Wrong trip id");
//		}
//	}
//
//	@Override
//	public List<Trip> selectAllTripsByCityTitle(City title) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Trip selectAllTripsByToday() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Trip changeTripDriverByDriverId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ArrayList<Trip> allTrips() {
//		// TODO Auto-generated method stub
//		return tripRepo.findAll();
//	}
//
//	@Override
//	public Trip findByCityTitle(City title) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Trip addNewTrip(LocalDateTime now, City city, Driver driver, int durationHours, Ticket ticket) {
//		return null;
//		// TODO Auto-generated method stub
//	}
//
//}
