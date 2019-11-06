package fr.excilys.databasecomputer.servlet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.DateFormatExeption;
import fr.excilys.databasecomputer.exception.DateIntevaleExecption;
import fr.excilys.databasecomputer.exception.NameCheckException;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

@Controller
public class EditComputerServlet {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private Validator validator;

	@GetMapping("/editComputer")
	protected ModelAndView doGet(@RequestParam(value = "computer") Integer idComputer) {
		ModelAndView mv = new ModelAndView();
		Computer computer = null;
		try {
			computer = computerService.displayOneComputeur(idComputer);
			mv.setViewName("editComputer");
		} catch (SQLExceptionComputerNotFound e) {
			mv.addObject("message", e.getMessage());
			mv.setViewName("500");
		}
		mv.addObject("computer", computerMapper.computerToComputerDto(computer));
		mv.getModel().put("listCompany", companyService.displayAllCompany());
		return mv;
	}

	@PostMapping("/editComputer")
	protected ModelAndView doPost(@ModelAttribute("computer") ComputerDTO computerDto) {
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
			if (computerService.updateComputer(computer)) {
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
