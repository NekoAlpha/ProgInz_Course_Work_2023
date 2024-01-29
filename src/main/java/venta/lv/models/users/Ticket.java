package venta.lv.models.users;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import venta.lv.models.Trip;

@Table(name = "ticket_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//TODO pielikt @ToString, ja nepieciešams
public class Ticket {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idt")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idt;
	
	@Column(name ="PurchaseDateTime")
	private LocalDateTime purchaseDateTime;
	
	@Min(value = 1)
	@Max(value = 25)
	@Column(name = "Price")
	@NotNull
	private float price;
	
	@Getter
	@Setter
	@Column(name = "IsChild")
	private boolean isChild;
	
	
	@ManyToOne
	@JoinColumn(name = "Idc")
	private Cashier cashier;
	
	@ManyToOne
	@JoinColumn(name = "Idtr")
	private Trip trip;

	public Ticket(LocalDateTime purchaseDateTime, @Min(1) @Max(25) @NotNull float price, boolean isChild,
			Cashier cashier, Trip trip) {
		super();
		this.purchaseDateTime = purchaseDateTime;
		this.price = price;
		this.isChild = isChild;
		this.cashier = cashier;
		this.trip = trip;
	}

	
	
	
	
}
