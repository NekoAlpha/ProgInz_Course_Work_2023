package venta.lv.models.users;

import java.util.ArrayList;
import java.util.Collection;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import venta.lv.models.Trip;


@Table(name = "cashier_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//TODO pielikt @ToString, ja nepieciešams
public class Cashier extends Person{

	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idc")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idc;
	
	
	//TODO Data JPA anotācijas
	//TODO validāciju anotācijas

	
//	@OneToOne
//	@JoinColumn(name = "name")
//	private Person person;
//	
//	@OneToOne
//	@JoinColumn(name = "surname")
//	private Person person2;
	
	@OneToMany(mappedBy = "cashier")
	private Collection<Ticket> ticket = new ArrayList<>();

	public Cashier(
			@NotNull @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 3, max = 15) String name,
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") String surname) {
		super(name, surname);
	}
	
	public void addTicket(Ticket inputTicket) {
		if(!ticket.contains(inputTicket)) {
			ticket.add(inputTicket);
		}
	}
	
	
	
}
