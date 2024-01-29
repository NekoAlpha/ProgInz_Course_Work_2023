package venta.lv.models.security;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Table(name = "MyUser")
@Entity
public class MyUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MyUserId")
	private int myUserId;
	
	@Column(name = "Name")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\\\ ]+")
	private String name;
	
	
	@NotNull
	@Column(name = "Surname")
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\\\ ]+")
	private String surname;
	
	@NotNull
	@Column(name = "Username")
	private String username;
	
	@NotNull
	@Column(name = "Password")
	private String password;

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Collection<MyAuthority> authorities = new ArrayList<>();
	
		
	
	public Collection<MyAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<MyAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMyUserId() {
		return myUserId;
	}

	public String getUsername() {
		return username;
	}
	
	
	public MyUser() {}
	
	public MyUser(String name, String surname, String password) {
		setName(name);
		setSurname(surname);
		setPassword(password);
		username = name.toLowerCase() + "." + surname.toLowerCase();
	}
	
	public void addAuthority(MyAuthority authority) {
		if(!authorities.contains(authority)) {
			authorities.add(authority);
		}
	}
	 public void removeAuthority(MyAuthority authority) {
		 if(authorities.contains(authority)) {
			 authorities.remove(authority);
		 }
	 }

}