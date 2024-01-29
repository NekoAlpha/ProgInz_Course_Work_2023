package venta.lv.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import venta.lv.models.users.Buscategory;
import venta.lv.models.users.Cashier;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Ticket;

@Table(name = "trip_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Trip {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idtr")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idtr;
	
//	@Column(name = "City")
//	@NotNull
//	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
//	@Size(min = 3, max = 15)
//	private String  city;
	
//	@Column(name = "Driver")
//	private Driver driver;
	
	@Column(name ="Date")
	private LocalDateTime date;
	
	@Min(value = 1)
	@Max(value = 25)
	@Column(name = "DurationHours")
	private int durationHours;
	
	@ManyToOne
	@JoinColumn(name = "Idd")
	private Driver driver;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "city_trip_table", 
	joinColumns = @JoinColumn(name = "Idtr"), 
	inverseJoinColumns = @JoinColumn(name = "Idci"))
	private Collection<City> cities = new ArrayList<>();
	
	@OneToMany(mappedBy = "trip")
	private Collection<Ticket> ticket;
	
	@OneToMany(mappedBy = "trip")
	private Collection<TripCalendar> tripCalendar;

	public Trip(LocalDateTime localDateTime, @Min(1) @Max(25) int durationHours, Driver driver, Collection<City> cities) {
		super();
		this.date = localDateTime;
		this.durationHours = durationHours;
		this.driver = driver;
		this.cities = cities;
	}
	
	public void addCity(City inputCity) {
		if (!cities.contains(inputCity)) {
			cities.add(inputCity);
		}
	}
	
	
}
