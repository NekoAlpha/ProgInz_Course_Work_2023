package venta.lv.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import venta.lv.models.City;
import venta.lv.models.Trip;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Ticket;
import venta.lv.repos.ITripRepo;
import venta.lv.repos.users.IDriverRepo;
import venta.lv.services.ITripCRUDService;

@Service
public class TripServiceImplWithDB implements ITripCRUDService{
	
	
	@Autowired
	private ITripRepo tripRepo;
	
	@Autowired
	private IDriverRepo driverRepo;
	
	@Override
	public List<Trip> selectAllTripsByCityTitle(City title) {
		List<Trip> tripsbycity = new ArrayList<>();
		for(Trip trip : allTrips(null)) {
			for(City city : trip.getCities()) {
				if(city.getTitle().equals(title)) {
					tripsbycity.add(trip);
					break;
				}
			}
		}
		return tripsbycity;
    }
	
	@Override
	 public List<Trip> selectAllTripsByDriverId(long idd) throws Exception {
		List<Trip> tripsByDriverID = new ArrayList<>();
		for(Trip trip : allTrips(null)) {
			if(trip.getDriver().getIdd() == idd) {
				tripsByDriverID.add(trip);
			}
		}
		return tripsByDriverID;
		}
	
	@Override
	public List<Trip> selectAllTripsByToday() {
		return (List<Trip>) tripRepo.findByDate(LocalDateTime.now());
	}
	
	@Override
	public void changeTripDriverByDriverId(long idtr, long idd) {
		Trip trip2 = new Trip();
		Driver driver2 = new Driver();
		List<Driver> selectAllDrivers = (List<Driver>) driverRepo.findAll();
		for(Trip trip : allTrips(null)) {
			if(trip.getIdtr() == idtr) {
				trip2 = trip;
				break;	
			}
		}
		
		for(Driver driver : selectAllDrivers) {
			if(driver.getIdd() == idd) {
				driver2 = driver;
				break;
			}
		}
		trip2.setDriver(driver2);
		tripRepo.save(trip2);
	}

	@Override
	public Page<Trip> allTrips(Pageable pageable) {
		return tripRepo.findAll(pageable);
	}

//	@Override
//	public List<Trip> findByCityTitle(City title) {
//		List<Trip> tripsByCityTitle = new ArrayList<>();
//		for(Trip trip : allTrips()) {
//			if(trip.getCity().getTitle() == title) {
//				tripsByCityTitle.add(trip);
//			}
//		}
//		return tripsByCityTitle;
//	}

	@Override
	public Trip addNewTrip(LocalDateTime now, Collection<City> city, Driver driver, int durationHours) {
		Trip temp = tripRepo.save(new Trip(now, durationHours, driver, city));
		return temp;
	}

	@Override
	public Trip findByCityTitle(City title) throws Exception {
//		List<Trip> tripsByCityTitle = new ArrayList<>();
//		for(Trip trip : allTrips()) {
//			if(((Trip) trip.getCities()).getTitle() == title) {
//				tripsByCityTitle.add(trip);
//			}
//		}
//		return (Trip) tripsByCityTitle;
//	}
		return null;
	}

	@Override
	public List<Trip> showAllByCityTitle(City title) throws Exception {
		if(tripRepo.findAllByCitiesTitle(title) != null)
		{
			return tripRepo.findAllByCitiesTitle(title);
		}
		else
		{
			throw new Exception("Wrong title");
		}
	}

	@Override
	public void deleteTripById(long idtr) throws Exception {
		if(tripRepo.existsById(idtr)) {
			tripRepo.deleteById(idtr);
		}
		else
		{
			throw new Exception("Wrong id");
		}
		
	}

	@Override
	public Trip findByTripId(long idtr) throws Exception {
		if(tripRepo.existsById(idtr))
		{
			return tripRepo.findById(idtr).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	
	
}
