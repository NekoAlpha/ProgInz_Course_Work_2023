package venta.lv.services.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import venta.lv.models.security.MyUser;
import venta.lv.repos.security.IMyUserRepo;
import venta.lv.utils.MyUserDetails;

public class MyUserDetailsManagerImpl implements UserDetailsManager{

	@Autowired
	private IMyUserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser user = userRepo.findByUsername(username);
		if(user != null) { //ir atrasts datubāzē
			MyUserDetails details = new MyUserDetails(user);
			return details;
		}
		else
		{
			throw new UsernameNotFoundException(username + " nav atrasts datubāze");	
		}
		
	}

	@Override
	public void createUser(UserDetails user) {
		MyUserDetails myDetails = (MyUserDetails) user;
		MyUser myUser = myDetails.getUser();
		
		userRepo.save(myUser);
	
	}

	@Override
	public void updateUser(UserDetails user) {
		MyUserDetails myDetails = (MyUserDetails) user;
		MyUser myUser = myDetails.getUser();
		
		userRepo.save(myUser);
		
	}

	@Override
	public void deleteUser(String username) {
		MyUser user = userRepo.findByUsername(username);
		if(user !=null) {//eksistē tāds lietotājs datubāze pēc norādīt lietotājvārda
			userRepo.delete(user);
		}
		
	}

	//TODO implementēt, kad tas ir nepieciešams
	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		MyUser user = userRepo.findByUsername(username);
		
		if(user != null) {
			return true;
		}
		else
		{
			return false;
		}
		
		/*
		return  (user != null) ? true :false;
		
		return (user != null);
		*/
	}

}