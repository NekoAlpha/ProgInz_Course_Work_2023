//package venta.lv.services.impl;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//
//import org.springframework.stereotype.Service;
//
//import jakarta.validation.Valid;
//import venta.lv.models.Trip;
//import venta.lv.models.users.Buscategory;
//import venta.lv.models.users.Driver;
//import venta.lv.models.users.Person;
//import venta.lv.services.IDriverCRUDService;
//
//@Service
//public class DriverServiceImpl implements IDriverCRUDService{
//
//	private ArrayList<Driver> allDrivers = new ArrayList<>(Arrays.asList(
//		    new Driver(new Person(), new Person(), Buscategory.minibus, Arrays.asList(new Trip(), new Trip())),
//		    new Driver(new Person(), new Person(), Buscategory.schoolbus, Arrays.asList(new Trip(), new Trip()))
//		));
//	
//	@Override
//	public ArrayList<Driver> selectAllDrivers() {
//		return allDrivers;
//	}
//
//	@Override
//	public Driver selectDriverById(String id) throws Exception {
//		for (Driver temp : allDrivers) {
//			if (temp.getIdd() == id) {
//				return temp;
//			}
//		}
//		throw new Exception("Wrong id");
//	}
//
//	@Override
//	public void deleteDriverById(long idd) throws Exception {
//		boolean isFound = false;
//		for (Driver temp : allDrivers) {
//			if (temp.getIdd() == idd) {
//				allDrivers.remove(temp);
//				isFound = true;
//				break;
//			}
//		}
//		if(!isFound)
//		{
//			throw new Exception("Wrong id");
//		}
//		
//	}
//
//	@Override
//	public Driver insertNewDriver(Person name) {
//		for (Driver temp : allDrivers) {
//			if (temp.getPerson().equals(name) && temp.getPerson().equals(surname)
//					&& temp.getBuscategory().equals(buscategory) && temp.getTrip().equals(trip)) {
//				return temp;
//			}
//		}
//		
//		Driver newDriver = new Driver(name, surname, buscategory, trip);
//		allDrivers.add(newDriver);
//		return newDriver;
//	}
//
//	@Override
//	public Driver updateDriverById(long idd, Person name, Person surname, Buscategory buscategory,
//			Collection<Trip> trip) throws Exception {
//		for (Driver temp : allDrivers) {
//			if (temp.getIdd() == idd) {
//				temp.setPerson(name);
//				temp.setPerson(surname);
//				temp.setBuscategory(buscategory);
//				temp.setTrip(trip);
//				return temp;
//			}
//		}
//		throw new Exception("Wrong id");
//	}
//	
//	@Override
//	public Driver findById(long idd) throws Exception {
//		for (Driver temp : allDrivers) {
//			if (temp.getIdd() == idd) {
//				return temp;
//			}
//		}
//		throw new Exception("Wrong id");
//	}
//	
//	@Override
//	public void updateDriver(long idd, @Valid Driver driver) {
//		// TODO Auto-generated method stub
//	}
//
//
//		
//}
