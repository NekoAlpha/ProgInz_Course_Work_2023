package venta.lv.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import venta.lv.models.Trip;
import venta.lv.models.users.Buscategory;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Person;

public interface IDriverCRUDService {

	void deleteDriverById(long idd) throws Exception;

	void insertNewDriver(Driver driver) throws Exception;

	void updateDriverById(long idd, Driver driver)
			throws Exception;

	Driver findById(long idd) throws Exception;

	void updateDriver(long idd, @Valid Driver driver);

	Driver selectDriverById(long idd) throws Exception;

	Page<Driver> selectAllDrivers(Pageable pageable);

	int getTotalPages(int pageSize);

	Workbook DriverToExelFile();

	
	
	
}
