package fr.excilys.databasecomputer.servlet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;
	private Validator validator;
	
	@Autowired
	public EditComputerServlet(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, Validator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.validator = validator;
	}

	@GetMapping("/editComputer")
	protected String doGet(@RequestParam(value = "computer") Integer idComputer, Model model) {
		Computer computer = null;
		try {
			computer = computerService.displayOneComputeur(idComputer);
		} catch (SQLExceptionComputerNotFound e) {
			model.addAttribute("message", e.getMessage());
			return "500";
		}
		model.addAttribute("computer", computerMapper.computerToComputerDto(computer));
		model.addAttribute("listCompany", companyService.displayAllCompany());
		return "editComputer";
	}

	@PostMapping("/editComputer")
	protected String doPost(@ModelAttribute("computer") ComputerDTO computerDto, Model model) {
		Map<String, String> errors = new HashMap<String, String>();
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
				return "redirect:dashboard";
			} else {
				model.addAttribute("response", "Errors whith the save");
				return "addComputer";
			}
		} else {
			model.addAttribute("errors", errors);
			return "addComputer";
		}
	}
}
