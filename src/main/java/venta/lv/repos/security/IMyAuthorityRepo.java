package venta.lv.repos.security;

import org.springframework.data.repository.CrudRepository;

import venta.lv.models.security.MyAuthority;

public interface IMyAuthorityRepo extends CrudRepository<MyAuthority, Integer>{

}