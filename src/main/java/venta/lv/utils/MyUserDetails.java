package venta.lv.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import venta.lv.models.security.MyAuthority;
import venta.lv.models.security.MyUser;

public class MyUserDetails implements UserDetails {

	private MyUser user;
	
	public MyUser getUser() {
		return user;
	}

	
	public MyUserDetails(MyUser user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Iterator<MyAuthority> iter = user.getAuthorities().iterator();
		
		while(iter.hasNext()) {
			SimpleGrantedAuthority temp = new SimpleGrantedAuthority(iter.next().getTitle());
			authorities.add(temp);
		}
	
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}