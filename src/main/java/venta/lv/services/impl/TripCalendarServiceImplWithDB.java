package venta.lv.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import venta.lv.models.TripCalendar;
import venta.lv.repos.ITripCalendar;
import venta.lv.services.ITripCalendarCRUDService;

@Service
public class TripCalendarServiceImplWithDB implements ITripCalendarCRUDService{
	
	@Autowired
	private ITripCalendar tripCalendarRepo;
	
	public Page<TripCalendar> getAllTripCalendar(Pageable pageable) {
		return (Page<TripCalendar>)tripCalendarRepo.findAll(pageable);
	}

	public TripCalendar selectTripCalendarById(long idcal) throws Exception {
		if(tripCalendarRepo.existsById(idcal))
		{
			return tripCalendarRepo.findById(idcal).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	public void updateCalendar(long idcal, @Valid TripCalendar updatedTripCalendar) {
	    tripCalendarRepo.findById(idcal).ifPresent(tripCalendar -> {
	        tripCalendar.setDayName(updatedTripCalendar.getDayName());
	        tripCalendar.setDate(updatedTripCalendar.getDate());
	        tripCalendar.setDepartureTime(updatedTripCalendar.getDepartureTime());
	        tripCalendar.setTrip(updatedTripCalendar.getTrip());
	        tripCalendarRepo.save(tripCalendar);
	    });
	}
	
	
	
	
}
