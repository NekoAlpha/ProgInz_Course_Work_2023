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
import venta.lv.models.DriverRatings;
import venta.lv.services.impl.DriverRatingsServiceImplWithDB;

@Controller
public class DriverRatingsController {
	
	@Autowired
	private DriverRatingsServiceImplWithDB driverRatingsService;

	@GetMapping("/driverRatings/showAll")
	public String showAll(org.springframework.ui.Model driverRatings, @PageableDefault(size = 3) Pageable pageable) {
		Page<DriverRatings> driverRating = driverRatingsService.getAllDriverRatings(pageable);
		driverRatings.addAttribute("driverRatings", driverRating);
		return "driverRatings-all-page";
	}
	
	@GetMapping("/driverRatings/update/{idr}")
	public String updateCalendarByID(@PathVariable("idr") long idr, org.springframework.ui.Model driverRatings) throws Exception {
		DriverRatings updatedRatings = driverRatingsService.selectDriverRatingsByID(idr);

		driverRatings.addAttribute("updatedRatings", updatedRatings); 

		return "driverRatings-update-page";
	}
	
	@PostMapping("/driverRatings/update/{idr}")
	public String showupdateCalendarByIDPost(@PathVariable("idr") String idr, @ModelAttribute("DriverRatings") @Valid venta.lv.models.@Valid DriverRatings driverRatings, BindingResult result) {
	    System.out.println("Received idcal: " + idr);
	    System.out.println("Updated values: " + driverRatings); // Print the updated values

	    try {
	        long ratingsId = Long.parseLong(idr);
	        System.out.println("Parsed ratingsId: " + ratingsId);

	        if (result.hasErrors()) {
	            System.out.println("Validation errors: " + result.getAllErrors());
	            return "driverRatings-update-page";
	        } else {
	        	driverRatingsService.updateRatings(ratingsId, driverRatings);
	            System.out.println("Update successful");
	            return "redirect:/driverRatings/showAll";
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
