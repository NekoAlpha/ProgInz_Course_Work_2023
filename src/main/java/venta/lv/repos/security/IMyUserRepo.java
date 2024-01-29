package venta.lv.repos.security;

import org.springframework.data.repository.CrudRepository;

import venta.lv.models.security.MyUser;

public interface IMyUserRepo extends CrudRepository<MyUser, Integer>{

	MyUser findByUsername(String username);

}