package venta.lv.repos.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import venta.lv.models.users.Driver;

public interface IDriverRepo extends CrudRepository<Driver, Long>{

	Driver save(Driver driver);

	Page<Driver> findAll(Pageable pageable);


}
