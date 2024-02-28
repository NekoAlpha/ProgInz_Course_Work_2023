package venta.lv.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import jakarta.servlet.http.HttpServletResponse;
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
	
	@GetMapping("/driver/export")
	public void DriverToExelFile(HttpServletResponse response, Pageable pageable) throws IOException {
	        Page<Driver> driver = driverService.selectAllDrivers(pageable);

	        // Create a new workbook
	        Workbook workbook = new XSSFWorkbook();

	        // Create a sheet
	        Sheet sheet = workbook.createSheet("Drivers");

	        // Create a header row
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("Id");
	        headerRow.createCell(1).setCellValue("Name");
	        headerRow.createCell(2).setCellValue("Surname");
	        headerRow.createCell(3).setCellValue("Buscategory");

	        // Create data rows
	        //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        int rowNum = 1;
	        for (Driver drivers : driver) {
	            Row dataRow = sheet.createRow(rowNum++);
	            dataRow.createCell(0).setCellValue(drivers.getIdd());
	            dataRow.createCell(1).setCellValue(drivers.getName());
	            dataRow.createCell(2).setCellValue(drivers.getSurname());
	            dataRow.createCell(3).setCellValue(drivers.getBuscategory().toString());	           
	        }

	        // Set column widths (optional)
	        sheet.setColumnWidth(0, 8000);
	        sheet.setColumnWidth(1, 8000);
	        sheet.setColumnWidth(2, 8000);	
	        sheet.setColumnWidth(3, 8000);

	        // Set the response headers
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=drivers.xlsx");

	        // Write the workbook to the response output stream
	        workbook.write(response.getOutputStream());
	        workbook.close();
	    }
	 
	@PostMapping("/driver/import")
	public ResponseEntity<String> importDrivers(@RequestParam("file") MultipartFile file) throws IOException {
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body("Please select an Excel file to upload.");
	    }

	    try {
	        driverService.importDriversFromExcel(file.getInputStream());
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Location", "/driver/showAll");

	        return new ResponseEntity<>(headers, HttpStatus.FOUND);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during import.");
	    }
	}
	 	
	@GetMapping("driver/export/word")
	public ResponseEntity<InputStreamResource> exportDriversToWord(Pageable pageable) throws IOException {
	        XWPFDocument document = driverService.exportDriversToWord(pageable);

	        File tempFile = File.createTempFile("drivers", ".docx");

	        FileOutputStream fos = new FileOutputStream(tempFile);
	        document.write(fos);
	        fos.close();

	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=drivers.docx");

	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(org.springframework.http.MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
	                .body(new InputStreamResource(new FileInputStream(tempFile)));
	    }
	 	
	@PostMapping("/driver/import/docx")
	public ResponseEntity<String> importDriversFromWordDoc(@RequestParam("file") MultipartFile file) throws IOException {
	        if (file.isEmpty()) {
	            return ResponseEntity.badRequest().body("Please select a DOCX file to upload.");
	        }

	        try {
	            driverService.importDriversFromWord(file.getInputStream());
	            HttpHeaders headers = new HttpHeaders();
	            headers.add("Location", "/driver/showAll");

	            return new ResponseEntity<>(headers, HttpStatus.FOUND);
	        } catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during import.");
			}
			
	    }
	 	
	
}
