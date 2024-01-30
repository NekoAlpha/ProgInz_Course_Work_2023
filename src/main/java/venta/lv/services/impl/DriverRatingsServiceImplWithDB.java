package venta.lv.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import venta.lv.models.DriverRatings;
import venta.lv.repos.IDriverRatings;
import venta.lv.services.IDriverRatingsCRUDService;

@Service
public class DriverRatingsServiceImplWithDB implements IDriverRatingsCRUDService{
	
	@Autowired
	private IDriverRatings driverRatingsRepo;
	
	public Page<DriverRatings> getAllDriverRatings(Pageable pageable) {
		return (Page<DriverRatings>)driverRatingsRepo.findAll(pageable);
	}

	public DriverRatings selectDriverRatingsByID(long idr) throws Exception {
		if(driverRatingsRepo.existsById(idr))
		{
			return driverRatingsRepo.findById(idr).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	public void updateRatings(long idr, @Valid DriverRatings updatedDriverRatings) {
		driverRatingsRepo.findById(idr).ifPresent(driverRatings -> {
			driverRatings.setDriver(updatedDriverRatings.getDriver());
			driverRatings.setRating(updatedDriverRatings.getRating());
			driverRatings.setComment(updatedDriverRatings.getComment());
			driverRatingsRepo.save(driverRatings);
	    });
	}

}
