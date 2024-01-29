package venta.lv.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import venta.lv.models.TripCalendar;

public interface ITripCalendar extends CrudRepository<TripCalendar, Long>{

	Page<TripCalendar> findAll(Pageable pageable);

}
