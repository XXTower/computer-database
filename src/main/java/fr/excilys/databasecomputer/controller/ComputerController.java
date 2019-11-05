package fr.excilys.databasecomputer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/computer")
public class ComputerController {

	@RequestMapping
	public ModelAndView getComputer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.print("oui");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboard");
		return mv;
	}
}
