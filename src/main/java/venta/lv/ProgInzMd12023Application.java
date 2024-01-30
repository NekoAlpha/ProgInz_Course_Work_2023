package venta.lv;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import venta.lv.models.City;
import venta.lv.models.Translate;
import venta.lv.models.Trip;
import venta.lv.models.TripCalendar;
import venta.lv.models.security.MyAuthority;
import venta.lv.models.security.MyUser;
import venta.lv.models.users.Buscategory;
import venta.lv.models.users.Cashier;
import venta.lv.models.users.Driver;
import venta.lv.models.users.Person;
import venta.lv.models.users.Ticket;
import venta.lv.repos.ICityRepo;
import venta.lv.repos.ITripCalendar;
import venta.lv.repos.ITripRepo;
import venta.lv.repos.security.IMyAuthorityRepo;
import venta.lv.repos.security.IMyUserRepo;
import venta.lv.repos.users.ICasheirRepo;
import venta.lv.repos.users.IDriverRepo;
import venta.lv.repos.users.ITicketRepo;

@SpringBootApplication
public class ProgInzMd12023Application {

	public static void main(String[] args) {
		SpringApplication.run(ProgInzMd12023Application.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoderSimple() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public CommandLineRunner testModelLayer(
			ICityRepo cityRepo, ITripRepo tripRepo, ICasheirRepo casheirRepo, 
			IDriverRepo driverRepo, ITicketRepo ticketRepo, IMyUserRepo userRepo, 
			IMyAuthorityRepo authorityRepo, ITripCalendar tripCalendarRepo) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				
				MyUser user1 = new MyUser("Edgars", "Kastanis", passwordEncoderSimple().encode("123"));
				userRepo.save(user1);
				
				MyUser user2 = new MyUser("Janis", "Berzins", passwordEncoderSimple().encode("321"));
				userRepo.save(user2);
				
				
				MyAuthority auth1 = new MyAuthority("ADMIN");
				MyAuthority auth2 = new MyAuthority("USER");
				
				auth1.addUser(user1); //Edgars kā ADMIN
				auth2.addUser(user2); //Janis kā USER
				auth2.addUser(user1); //Edgars kā USER
				authorityRepo.save(auth1);
				authorityRepo.save(auth2);
				
				user1.addAuthority(auth1);
				user1.addAuthority(auth2);
				user2.addAuthority(auth2);
				userRepo.save(user1);
				userRepo.save(user2);
				
				
				Person p1 = new Person("Renārs", "Geriksons");
				Person p2 = new Person("Jānis", "Avotiņš");
				//personRepo.save(p1);
				//personRepo.save(p2);
				
				Driver d1 = new Driver("Renārs", "Geriksons", Buscategory.minibus);
				Driver d2 = new Driver("Jānis", "Avotiņš", Buscategory.largebus);
				driverRepo.save(d2);
				driverRepo.save(d1);
				
				Cashier c1 = new Cashier("Renārs", "Geriksons");
				Cashier c2 = new Cashier("Frederiks", "Tomsons");
				casheirRepo.save(c1);
				casheirRepo.save(c2);

					
//				User us2 = new User("123", "karlis.immers@venta.lv");//pasniedzējs
//				User us3 = new User("123", "janis.berzins@venta.lv");//stundents
//				User us4 = new User("123", "baiba.kalnina@venta.lv");//students
			
				
				City cit1 = new City("Rīga", "Upes iela");
				City cit2 = new City("Talsi", "Tirgus iela");
				City cit3 = new City("Ventspils", "Ostas iela");
				City cit4 = new City("Tukums", "Bērzu iela");
				City cit5 = new City("Spāre", "Mazā iela");
				cityRepo.save(cit1);
				cityRepo.save(cit2);
				cityRepo.save(cit3);
				cityRepo.save(cit4);
				cityRepo.save(cit5);
				
				ArrayList<City> Rei1 = new ArrayList<>();
				ArrayList<City> Rei2 = new ArrayList<>();
				Rei1.add(cit1);
				Rei1.add(cit2);
				Rei2.add(cit1);
				Rei2.add(cit5);
				Rei2.add(cit3);
				
				
				Trip tr1 = new Trip(LocalDateTime.now(), 3, d2, Rei1);
				Trip tr2 = new Trip(LocalDateTime.now(), 2, d2, Rei1);
				Trip tr3 = new Trip(LocalDateTime.now(), 4, d1, Rei1);
				Trip tr4 = new Trip(LocalDateTime.now(), 6, d1, Rei2);
//				Trip tr4 = new Trip(LocalDateTime.now(), 5, d1, Rei1);
				tripRepo.save(tr1);
				tripRepo.save(tr2);
				tripRepo.save(tr3);
				tripRepo.save(tr4);
				

				
				Ticket t1 = new Ticket(LocalDateTime.now(), 10, false, c1, tr1);
				Ticket t2 = new Ticket(LocalDateTime.now(), 5, true, c1, tr3);
				Ticket t3 = new Ticket(LocalDateTime.now(), 7, true, c2, tr2);
				Ticket t4 = new Ticket(LocalDateTime.now(), 3, false, c2, tr4);
//				Course c1 = new Course("Javaa", 4);
//				Course c2 = new Course("Datastr", 2);
//				courseRepo.save(c1);
//				courseRepo.save(c2);
				ticketRepo.save(t1);
				ticketRepo.save(t2);
				ticketRepo.save(t3);
				ticketRepo.save(t4);

				String langFrom = "lv";
				String langTo = "en";
				String textToTranslate = "Tulkošana strādā";
				
				try{
					String translatedText = Translate.translate(langFrom, langTo, textToTranslate);
					System.out.println("Tulkojums: " + translatedText);
				}catch (IOException e){
					System.err.println("Kļūda veicot tulkojumu: " + e.getMessage());
				}
				
				TripCalendar cal1 = new TripCalendar("Pirmdiena", LocalDate.now(), LocalTime.now(), tr4);
				TripCalendar cal2 = new TripCalendar("Otrdiena", LocalDate.now(), LocalTime.now(), tr3);
				TripCalendar cal3 = new TripCalendar("Ceturtdiena", LocalDate.now(), LocalTime.now(), tr4);
				TripCalendar cal4 = new TripCalendar("Pirmdiena", LocalDate.now(), LocalTime.now(), tr1);
				tripCalendarRepo.save(cal1);
				tripCalendarRepo.save(cal2);
				tripCalendarRepo.save(cal3);
				tripCalendarRepo.save(cal4);
				
				
//				AcademicPersonel ac1 = new AcademicPersonel("Karina", "Skirmante", 
//						"121212-12121", us1, Degree.mg);
//				AcademicPersonel ac2 = new AcademicPersonel("Karlis", "Immers", 
//						"121212-12123", us2, Degree.mg);
//				personalRepo.save(ac1);
//				personalRepo.save(ac2);
//				
//				Student s1 = new Student("Janis", "Berzins", 
//						"211221-34567", us3, "12345678", false);
//				Student s2 = new Student("Baiba", "Kalnina", 
//						"121256-98765", us4, "12899876", true);
//				s2.addDebtCourse(c1);
//				s2.addDebtCourse(c2);
//				studentRepo.save(s1);
//				studentRepo.save(s2);
//				c1.addStudent(s2);
//				c2.addStudent(s2);
//				courseRepo.save(c1);
//				courseRepo.save(c2);
//				
//				Thesis th1 = new Thesis("Sistēmas izstrāde", "Development of System",
//						"Development", "1...2.3..4", s1, ac1);
//				Thesis th2 = new Thesis("Pētījums", "Research",
//						"Research", "1...2.3..4", s2, ac2);
//				
//				th1.addReviewer(ac2);
//				th2.addReviewer(ac1);
//				thesisRepo.save(th1);
//				thesisRepo.save(th2);
//				ac1.addThesisForReviews(th2);
//				ac2.addThesisForReviews(th1);
//				personalRepo.save(ac1);
//				personalRepo.save(ac2);
//				
//				
//				Comment com1 = new Comment("Neprecīzs nosaukums", ac2, th1);
//				Comment com2 = new Comment("Mērķi nav atbilstoši", ac1, th1);
//				commentRepo.save(com1);
//				commentRepo.save(com2);
				
			}
		};
	}

}
