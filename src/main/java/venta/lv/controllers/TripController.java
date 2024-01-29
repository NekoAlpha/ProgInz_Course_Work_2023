package venta.lv.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import venta.lv.models.City;
import venta.lv.models.Trip;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Ticket;
import venta.lv.services.ITripCRUDService;

@Controller
public class TripController {
	
	
	@Autowired
	private ITripCRUDService triService;
	
	@GetMapping("/trip/showAll")
    public String showAll(org.springframework.ui.Model trip, @PageableDefault(size = 2) Pageable pageable) {
		Page<Trip> trips = triService.allTrips(pageable);
		trip.addAttribute("Trip", trips);
		return "trip-all-page";
    }
	
	@GetMapping("/trip/{title}")
    public String findByCityTitle(City title, org.springframework.ui.Model trip) {
		try {
			
			Trip temp = new Trip();
			
			temp = triService.findByCityTitle(title);
			
			trip.addAttribute("Trip", temp);
			
			return "trip-one-page";

		}catch (Exception e) {
			// TODO: handle exception
		}

		return "error-page";
		
		
	}
	
	@GetMapping("/trip/showAll/city/{title}")
	public String showAllByCityTitle(@PathVariable("title") City title, org.springframework.ui.Model trip) {
		
		List<Trip> trips = triService.selectAllTripsByCityTitle(title); 
	    trip.addAttribute("Trip", trips);
		
		return "trip-all-page";
		
	}
	
	@GetMapping("/trip/showAll/driver/{idd}")
	public String showAllByDriverId(@PathVariable("idd") Long idd, org.springframework.ui.Model trip) throws Exception {
		
		List<Trip> trips = triService.selectAllTripsByDriverId(idd); 
	    trip.addAttribute("Trip", trips);
		
		return "trip-all-page";
		
	}
	
	@GetMapping("/trip/showAll/today")
	public String showAllTripsToday(org.springframework.ui.Model trip) {
		List<Trip> tripsToday = triService.selectAllTripsByToday();
		trip.addAttribute("Trip", tripsToday);
		return "trip-all-page";
		
	}
	
	@GetMapping("/trip/changeDriver/{idtr}/{idd}")
	public String changeDriverOnTripByTripIdOrDriverId(@PathVariable("idd") Driver idd, @PathVariable("idtr") Trip idtr, org.springframework.ui.Model trip) {
		
		return "trip-add-page";
		
	}
	
	@GetMapping("/trip/addNew")
	public String addTrip(Trip trip) {
//		Iterable<Trip> Trip = triService.allTrips(); 
//        trip.addAttribute("Trip", Trip);
//        trip.addAttribute("Trip", new Trip());
//		String dateString = "2023-10-19";
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

		return "trip-add-page";
	}
	
	@PostMapping("/trip/addNew")
	public String addTripPost(@Valid Trip Trip, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
	        
        	return "trip-add-page";
    	}
	
		Trip tr1 = new Trip(
				Trip.getDate(), 
				Trip.getDurationHours(),
				Trip.getDriver(),
				Trip.getCities());
	
		Trip.getDate();
		triService.addNewTrip(LocalDateTime.now(), Trip.getCities(), Trip.getDriver(), Trip.getDurationHours());
		
		return "redirect:/trip/showAll";
	}
	
	@GetMapping("/trip/delete/{id}")
    public String removeById(@PathVariable("id") long id, org.springframework.ui.Model trip, @PageableDefault(size = 10) Pageable pageable) {
		try {
			triService.deleteTripById(id);
			trip.addAttribute("Ticket", triService.allTrips(pageable));
					
			return "redirect:/trip/showAll";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "error-page";
	}
	
	@GetMapping("/trip/showOne/{id}")
    public String showById(@PathVariable("id") long id, org.springframework.ui.Model trip) {
        
		try {
			
			Trip temp = new Trip();
			temp = triService.findByTripId(id);			
			trip.addAttribute("Trip", temp);			
			return "trip-one-page";

		}catch (Exception e) {
			// TODO: handle exception
		}

		return "error-page";
	}
	
}
