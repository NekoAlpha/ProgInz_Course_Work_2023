package venta.lv.services.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import venta.lv.models.Trip;
import venta.lv.models.users.Buscategory;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Person;
import venta.lv.repos.users.IDriverRepo;
import venta.lv.services.IDriverCRUDService;

@Service
public class DriverServiceImplWithDB implements IDriverCRUDService{
		
	@Autowired
	private IDriverRepo driverRepo;
	
	
	@Override
	public Page<Driver> selectAllDrivers(Pageable pageable) {
		return (Page<Driver>)driverRepo.findAll(pageable);
	}	
	
	@Override
	public Driver selectDriverById(long idd) throws Exception {
		if(driverRepo.existsById(idd))
		{
			return driverRepo.findById(idd).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}
	
	@Override
	public void deleteDriverById(long idd) throws Exception {
		if(driverRepo.existsById(idd)) {
			driverRepo.deleteById(idd);
		}
		else
		{
			throw new Exception("Wrong id");
		}
		
	}   
	
	@Override
	public void insertNewDriver(Driver driver) {
	    if (!driverRepo.existsByNameAndSurnameAndBuscategory(driver.getName(), driver.getSurname(), driver.getBuscategory())) {
	        driverRepo.save(driver);
	    }
	}
	
	@Override
	public void updateDriverById(long idd, Driver updatedDriver) {

	    driverRepo.findById(idd).ifPresent(driver -> {

	        driver.setName(updatedDriver.getName());
	        driver.setSurname(updatedDriver.getSurname());
	        driver.setBuscategory(updatedDriver.getBuscategory());

	        driverRepo.save(driver);
	    });
	}

	@Override
	public Driver findById(long idd) throws Exception {
		if(driverRepo.existsById(idd))
		{
			return driverRepo.findById(idd).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	@Override
	public void updateDriver(long idd, @Valid Driver updatedDriver) {
		driverRepo.findById(idd).ifPresent(driver -> {
			driver.setName(updatedDriver.getName());
			driver.setSurname(updatedDriver.getSurname());
			driver.setBuscategory(updatedDriver.getBuscategory());
			driverRepo.save(driver);
		});
	}
	
	@Override
	public int getTotalPages(int pageSize) {
	    long totalDrivers = driverRepo.count();
	    return (int) Math.ceil((double) totalDrivers / pageSize);
	}
	
	@Override
	public Workbook DriverToExelFile() {
		return null;
//		List<Driver> driver = retrieveActiveDriver();
//		
//		Workbook workbook = new XSSFWorkbook();
//		
//		Sheet sheet = workbook.createSheet("Drivers");
//		
//		String[] headers = {"Vārds", "Uzvārds", "Buskategorija"};
//		
//		Row headerRow = sheet.createRow(0);
//		for (int i = 0; i < headers.length; i++) {
//			headerRow.createCell(i).setCellValue(headers[i]);
//		}
//		
//		int rowNumber = 1;
//		
//		for (Driver drivers : driver) {
//			Row dataRow = sheet.createRow(rowNumber++);
//			dataRow.createCell(0).setCellValue(drivers.getName());
//			dataRow.createCell(1).setCellValue(drivers.getSurname());
//			dataRow.createCell(2).setCellValue(drivers.getBuscategory());
//		}
//		
//		for (int i = 0; i < 3; i ++) {
//			sheet.setColumnWidth(i, 8000);
//		}
//		return workbook;
//		
//		
//		
	}

	public List<Driver> retrieveActiveDrivers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void importDriversFromExcel(InputStream inputStream) {
		// TODO Auto-generated method stub
		
	}
	
}
