package venta.lv.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import venta.lv.models.users.Driver;
import venta.lv.services.impl.DriverServiceImplWithDB;

@Controller
public class DriverController {
	
	@Autowired
	private DriverServiceImplWithDB driverService;
	
	@GetMapping("/driver/showAll")
    public String showAll(org.springframework.ui.Model driver, @PageableDefault(size = 2) Pageable pageable) {
		Page<Driver> drivers = driverService.selectAllDrivers(pageable);
		driver.addAttribute("drivers", drivers);
		//return ResponseEntity.ok(driverService.selectAllDrivers(pageable));
        return "driver-all-page";
    }
	
	@GetMapping("/driver/showOne/{id}")
    public String showById(@PathVariable("id") long id, org.springframework.ui.Model driver) {
        
		try {
			
			Driver temp = new Driver();
			
			temp = driverService.findById(id);
			
			driver.addAttribute("Driver", temp);
			
			return "driver-one-page";

		}catch (Exception e) {
			// TODO: handle exception
		}

		return "error-page";
		
//		Driver driver = Driver.getDriverById(id); 
//        model.addAttribute("myAllDrivers"); 
//        return "driver_one_page"; 
    }
	
	@GetMapping("/driver/delete/{id}")
    public String removeById(@PathVariable("id") long id, org.springframework.ui.Model driver, @PageableDefault(size = 10) Pageable pageable) {
		try {
			driverService.deleteDriverById(id);
			driver.addAttribute("Driver", driverService.selectAllDrivers(pageable));
					
			return "redirect:/driver/showAll";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "error-page";
	}
    
	
	@GetMapping("/driver/addNew")
    public String addNewDriver(Driver drivers, org.springframework.ui.Model driver, @PageableDefault(size = 10) Pageable pageable) {
		Page<Driver> Driver = driverService.selectAllDrivers(pageable); 
		driver.addAttribute("Driver", Driver);
		driver.addAttribute("Driver", new Driver());

		return "driver-add-page";
    }
	
	@PostMapping("/driver/addNew")
	public String addNewDriverPost(@Valid @ModelAttribute("driver") Driver driver, BindingResult bindingResult, @PageableDefault(size = 10) Pageable pageable) {
	    if (bindingResult.hasErrors()) {
	        return "driver-add-page";
	    }

	    Driver newDriver = new Driver(driver.getName(), driver.getSurname(), driver.getBuscategory());
	    driverService.insertNewDriver(newDriver);

	    //int totalPages = driverService.getTotalPages(3); 
	    return "redirect:/driver/showAll";
	}
	
	@GetMapping("/driver/update/{idd}")
	public String updateDriverByID(@PathVariable("idd") long idd, org.springframework.ui.Model driver) throws Exception {
	    Driver updatedDriver = driverService.selectDriverById(idd);

	    driver.addAttribute("updatedDriver", updatedDriver); 

	    return "driver-update-page"; 
	}
	
	@PostMapping("/driver/update/{idd}")
	public String updateDriverByIDPost(@PathVariable("idd") String idd, @ModelAttribute("Driver") @Valid Driver driver, BindingResult result) {
		System.out.println("Received idd: " + idd);
		System.out.println("Updated values: " + driver); // Print the updated values

		try {
		    long driverId = Long.parseLong(idd);
		    System.out.println("Parsed driverId: " + driverId);

		    if (result.hasErrors()) {
		        System.out.println("Validation errors: " + result.getAllErrors());
		        return "driver-update-page";
		    } else {
		        driverService.updateDriver(driverId, driver);
		        System.out.println("Update successful");
		        return "redirect:/driver/showAll";
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
