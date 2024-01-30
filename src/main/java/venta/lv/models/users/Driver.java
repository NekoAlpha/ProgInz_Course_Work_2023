package venta.lv.models.users;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import venta.lv.models.Trip;
import venta.lv.models.users.Buscategory;
import venta.lv.models.users.Driver;


@Table(name = "driver_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//TODO pielikt @ToString, ja nepieciešams
public class Driver extends Person{

	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idd")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idd;
	
	
	//TODO Data JPA anotācijas
	//TODO validāciju anotācijas
	
	
	
//	@OneToOne
//	@JoinColumn(name = "person_id")
//	private Person person;
	
//	@OneToOne
//	@JoinColumn(name = "surname")
//	private Person surname;
	
	@Column(name = "Buscategory")
	private Buscategory buscategory;
	
	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Trip> trips = new ArrayList<>();

	public Driver(
			@NotNull @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 3, max = 15) String name,
			@NotNull @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 3, max = 15) String surname,
			@NotBlank Buscategory buscategory) {
		super(name, surname);
		this.buscategory = buscategory;
	}

	public void addTrip(Trip inputTrip) {
		if(!trips.contains(inputTrip)) {
			trips.add(inputTrip);
		}
	}
	
	
	
	
	
	
}
