package venta.lv.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		for(Driver driver2 : selectAllDrivers(null)) {
			if(driver2.getName().equals(driver.getName()) && driver2.getSurname().equals(driver.getSurname()) && driver2.getBuscategory() == driver.getBuscategory()) {
				return;
			}
		}
		selectAllDrivers(null).and(driver);
		driverRepo.save(driver);
	}
	
	@Override
	public void updateDriverById(long idd, Driver Driver) {
		for(Driver driver : selectAllDrivers(null)) {
			if(driver.getIdd()==idd) {
				if(driver != null) {
					driver.setName(Driver.getName());
					driver.setSurname(Driver.getSurname());
					driver.setBuscategory(Driver.getBuscategory());
					
					selectAllDrivers(null).and(driver);
					driverRepo.save(driver);
				}
			}
		}
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
	
	
	
	
}
