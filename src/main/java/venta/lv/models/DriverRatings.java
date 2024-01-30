package venta.lv.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import venta.lv.models.users.Driver;

@Entity
@Table(name = "driver_rtings")
@Getter
@Setter
@NoArgsConstructor
public class DriverRatings {

	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idr")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idr;

    @ManyToOne
    @JoinColumn(name = "Idd")
    private Driver driver;

    @Column(name = "rating")
    @Min(value = 1, message = "Novērtējumam jābūt vismaz 1")
    @Max(value = 10, message = "Novērtējumam jābūt ne vairāk kā 10")
    private int rating;

    @Column(name = "comment")
    @NotBlank(message = "Komentārs nedrīkst būt tukšs")
    private String comment;

	public DriverRatings(Driver driver,
			@Min(value = 1, message = "Novērtējumam jābūt vismaz 1") @Max(value = 10, message = "Novērtējumam jābūt ne vairāk kā 10") int rating,
			@NotBlank(message = "Komentārs nedrīkst būt tukšs") String comment) {
		super();
		this.driver = driver;
		this.rating = rating;
		this.comment = comment;
	}
    
    
    
    
}

