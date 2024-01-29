package venta.lv.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tripcalendar_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TripCalendar {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idcal")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idcal;
    
	@Column(name = "DayName")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Size(min = 8, max = 11)
    private String dayName;
    
	@Column(name = "Date")
    private LocalDate date;
    
	@Column(name = "DepartureTime")
    private LocalTime departureTime;

	@ManyToOne
	@JoinColumn(name = "Idtr")
	private Trip trip;

	public TripCalendar(
			@NotNull @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 8, max = 11) String dayName,
			LocalDate date, LocalTime departureTime, Trip trip) {
		super();
		this.dayName = dayName;
		this.date = date;
		this.departureTime = departureTime;
		this.trip = trip;
	}
	
	
	
}