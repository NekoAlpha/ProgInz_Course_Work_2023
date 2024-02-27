package venta.lv.repos.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import venta.lv.models.users.Buscategory;
import venta.lv.models.users.Driver;

public interface IDriverRepo extends CrudRepository<Driver, Long>{

	Driver save(Driver driver);

	Page<Driver> findAll(Pageable pageable);

	boolean existsByNameAndSurnameAndBuscategory(String name, String surname, Buscategory buscategory);

	boolean existsByIdd(Long idd);
	
	Driver findByIdd(Long idd);


}
