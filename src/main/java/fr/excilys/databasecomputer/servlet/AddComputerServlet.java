package fr.excilys.databasecomputer.servlet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.DateFormatExeption;
import fr.excilys.databasecomputer.exception.DateIntevaleExecption;
import fr.excilys.databasecomputer.exception.NameCheckException;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

@Controller
public class AddComputerServlet {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private Validator validator;


	@GetMapping("/addComputer")
	public ModelAndView doGet() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addComputer");
		mv.addObject("listCompany", companyService.displayAllCompany());
		mv.addObject("computer", new ComputerDTO());
		return mv;
	}
	
	@PostMapping("/addComputer")
	public ModelAndView doPost(@ModelAttribute("computer") ComputerDTO computerDto) {
		Map<String, String> errors = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView();
		Computer computer = null;		
		try {
			computer = computerMapper.computerDtoToComputer(computerDto);
			validator.validationComputer(computer);
		} catch (NameCheckException e) {
				errors.put("computerName", e.getMessage());
		} catch (DateIntevaleExecption e) {
				errors.put("discontinued", e.getMessage());
		} catch (DateFormatExeption e) {
			errors.put("introduced", e.getMessage());
			errors.put("discontinued", e.getMessage());
		}

		if (errors.isEmpty()) {
			if (computerService.addComputer(computer)) {
				mv.setViewName("redirect:dashboard");
				return mv;
			} else {
				mv.addObject("response", "Errors whith the save");
				mv.setViewName("addComputer");
				return mv;
			}
		} else {
			mv.addObject("errors", errors);
			mv.setViewName("addComputer");
			return mv;
		}
	}
}
