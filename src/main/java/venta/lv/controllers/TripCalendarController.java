package venta.lv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import venta.lv.models.TripCalendar;
import venta.lv.services.impl.TripCalendarServiceImplWithDB;

@Controller
public class TripCalendarController {
	
	@Autowired
	private TripCalendarServiceImplWithDB tripCalendarService;
	
	@GetMapping("/tripCalendar/showAll")
	public String showAll(org.springframework.ui.Model tripCalendar, @PageableDefault(size = 3) Pageable pageable) {
		Page<TripCalendar> tripCalendars = tripCalendarService.getAllTripCalendar(pageable);
		tripCalendar.addAttribute("tripCalendar", tripCalendars);
		return "tripCalendar-all-page";
	}
	
	@GetMapping("/tripCalendar/update/{idcal}")
	public String updateCalendarByID(@PathVariable("idcal") long idcal, org.springframework.ui.Model tripCalendar) throws Exception {
		TripCalendar updatedCalendar = tripCalendarService.selectTripCalendarById(idcal);

		tripCalendar.addAttribute("updatedCalendar", updatedCalendar); 

		return "tripCalendar-update-page";
	}
	
	@PostMapping("/tripCalendar/update/{idcal}")
	public String showupdateCalendarByIDPost(@PathVariable("idcal") String idcal, @ModelAttribute("TripCalendar") @Valid TripCalendar tripCalendar, BindingResult result) {
	    System.out.println("Received idcal: " + idcal);
	    System.out.println("Updated values: " + tripCalendar); // Print the updated values

	    try {
	        long calendarId = Long.parseLong(idcal);
	        System.out.println("Parsed calendarId: " + calendarId);

	        if (result.hasErrors()) {
	            System.out.println("Validation errors: " + result.getAllErrors());
	            return "tripCalendar-update-page";
	        } else {
	            tripCalendarService.updateCalendar(calendarId, tripCalendar);
	            System.out.println("Update successful");
	            return "redirect:/tripCalendar/showAll";
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
