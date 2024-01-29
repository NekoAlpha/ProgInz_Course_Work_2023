package venta.lv.repos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import venta.lv.models.City;
import venta.lv.models.Trip;
import venta.lv.models.users.Driver;

public interface ITripRepo extends CrudRepository<Trip, Long>{

	List<Trip> findAllByCitiesTitle(City title);

	List<Trip> findByDate(LocalDateTime date);
	
	//List<Trip> findByDate(@Param("date") LocalDate date);
	
	Page<Trip> findAll(Pageable pageable);


}
