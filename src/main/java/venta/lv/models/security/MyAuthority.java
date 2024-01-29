package venta.lv.models.security;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Table(name = "MyAuthority")
@Entity
public class MyAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MyAuthorityId")
	private int myAuthorityId;
	
	
	@Column(name="Title")
	@NotNull
	@Pattern(regexp = "[A-Z]{3,8}") //lielie burti 3, 4, 5, 6, 7 vai 8
	private String title;

	@ManyToMany
	@JoinTable(name = "Users_Authorities",
	joinColumns = @JoinColumn(name="MyAuthorityId"),
	inverseJoinColumns = @JoinColumn(name="MyUserId"))
	private Collection<MyUser> users = new ArrayList<>();
	
		
	
	public Collection<MyUser> getUsers() {
		return users;
	}


	public void setUsers(Collection<MyUser> users) {
		this.users = users;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getMyAuthorityId() {
		return myAuthorityId;
	}
	
	
	public MyAuthority() {}
	
	public MyAuthority(String title) {
		setTitle(title);
	}
	
	public void addUser(MyUser user) {
		if(!users.contains(user)) {
			users.add(user);
		}
	}
	 public void removeUser(MyUser user) {
		 if(users.contains(user)) {
			 users.remove(user);
		 }
	 }
	
	
	
	
}