package venta.lv.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import venta.lv.models.City;
import venta.lv.models.Trip;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Ticket;

public interface ITripCRUDService {

	List<Trip> selectAllTripsByDriverId(long idd) throws Exception;

	List<Trip> selectAllTripsByCityTitle(City title);

	List<Trip> selectAllTripsByToday();

	Page<Trip> allTrips(Pageable pageable);

	Trip findByCityTitle(City title) throws Exception;

	Trip addNewTrip(LocalDateTime now, Collection<City> city, Driver driver, int durationHours);

	void changeTripDriverByDriverId(long idtr, long idd);

	List<Trip> showAllByCityTitle(City title) throws Exception;

	void deleteTripById(long id) throws Exception;

	Trip findByTripId(long id) throws Exception;

}
