package venta.lv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilsController {
	
	@GetMapping("/my-access-denied")
	public String accessDeniedGetFunc() {
		return "access-denied-page";
	}

}
