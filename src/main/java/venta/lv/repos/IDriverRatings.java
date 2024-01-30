package venta.lv.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import venta.lv.models.DriverRatings;

public interface IDriverRatings extends CrudRepository<DriverRatings, Long>{

	Page<DriverRatings> findAll(Pageable pageable);
	
}
