package venta.lv.confs;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import venta.lv.services.impl.security.MyUserDetailsManagerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
	}

	// norošināt lietotajus un to lomas
	// userDetailsManager
	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsManager() {
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(User.withDefaultPasswordEncoder()
	 * .username("karina.krinkele").password("123").authorities("ADMIN").build());
	 * manager.createUser(User.withDefaultPasswordEncoder()
	 * .username("janis.berzins").password("321").authorities("USER").build());
	 * manager.createUser(User.withDefaultPasswordEncoder()
	 * .username("liga.jauka").password("987").authorities("USER",
	 * "ADMIN").build());
	 * 
	 * return manager; }
	 */

	@Bean
	public MyUserDetailsManagerImpl userDetailsManager() {
		MyUserDetailsManagerImpl manager = new MyUserDetailsManagerImpl();
		return manager;
	}

	@Bean
	public PasswordEncoder passwordEncoderSimple2() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		
		
		authenticationManagerBuilder.
		userDetailsService(userDetailsManager()).passwordEncoder(passwordEncoderSimple2());
		return authenticationManagerBuilder.build();
	}

	
	// nodorošināt piekļuvi konkrētiem endpointien
	// SecurityFilterChain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.requestMatchers("/home").permitAll()
		.requestMatchers("/driver/showAll").permitAll()
		.requestMatchers("/ticket/showAll").permitAll()
		.requestMatchers("/trip/showAll").permitAll()
		.requestMatchers("/tripCalendar/showAll").permitAll()
		.requestMatchers("/driver/addNew").hasAnyAuthority("ADMIN")
		.requestMatchers("/ticket/addNew").hasAnyAuthority("ADMIN")
		.requestMatchers("/trip/addNew").hasAnyAuthority("ADMIN")
		.requestMatchers("/driver/showOne/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/trip/showOne/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/ticket/delete/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/driver/delete/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/trip/delete/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/tripCalendar/update/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/driver/update/**").hasAnyAuthority("ADMIN")
		//.requestMatchers("/ticket-one-page").hasAnyAuthority("ADMIN")
		//.requestMatchers("/trip-one-page").hasAnyAuthority("ADMIN")
		//.requestMatchers("/driver-one-page").hasAnyAuthority("ADMIN")
		.requestMatchers("/ticket/showOne/**").hasAnyAuthority("ADMIN")
//		.requestMatchers("/product/**").hasAnyAuthority("ADMIN", "USER")
//		.requestMatchers("/allproducts/**").permitAll()
//		.requestMatchers("/insert").hasAnyAuthority("ADMIN")
//		.requestMatchers("/update/**").hasAnyAuthority("ADMIN")
//		.requestMatchers("/delete/**").hasAnyAuthority("ADMIN")
//		.requestMatchers("/filter/quantity/**").permitAll()
		.requestMatchers("/error").permitAll()
		.requestMatchers("/h2-console").permitAll()
		.requestMatchers("/h2-console/**").permitAll()
		.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
		.and()
		.formLogin().permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/my-access-denied");
		return http.build();

	}

}