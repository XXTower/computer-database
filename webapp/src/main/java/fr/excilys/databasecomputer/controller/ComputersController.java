package fr.excilys.databasecomputer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.databasecomputer.dtos.CompanyDTO;
import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.DateFormatExeption;
import fr.excilys.databasecomputer.exception.DateIntevaleExecption;
import fr.excilys.databasecomputer.exception.FailSaveComputer;
import fr.excilys.databasecomputer.exception.NameCheckException;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.pageable.Page;
import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

@RestController
@RequestMapping(value = "/computers")
public class ComputersController {

	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;
	private Validator validator;

	@Autowired
	public ComputersController(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, Validator validator, Page page) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.validator = validator;
	}

	@GetMapping
	protected List<ComputerDTO> getComputers(Page page, @RequestParam(value = "order", defaultValue = "ASC") String order,
			@RequestParam(value = "search", defaultValue = "") String search) {
		ModelAndView mv = new ModelAndView();

		mv.addObject("search", search);
		page.setNbComputer(computerService.nbComputerCompanyFindByName(search));
		mv.addObject("listComputer", computerService.findComputerCompanyByName(search, page.getLimite(), page.calculeNewOffset(), order));

		mv.addObject("page", page);
		return computerService.findComputerCompanyByName(search, page.getLimite(), page.calculeNewOffset(), order);
	}

	@DeleteMapping("/delete")
	protected String deleteComputer(@RequestBody List<Integer> listComputer) {
		for (Integer idComputer : listComputer) {
			if (!computerService.deleteComputer(idComputer)) {
//				request.setAttribute("response", "Errors whith the delete");
				return "redirect:/computers";
			}
		}
		return "redirect:/computers";
	}

	@GetMapping("/addComputer")
	public List<CompanyDTO> displayAddComputer() {
		return companyService.displayAllCompany();
	}

	@PostMapping("/addComputer")
	public String addcomputer(@RequestBody ComputerDTO computerDto, Model model) {
		Map<String, String> errors = new HashMap<String, String>();
		Computer computer = null;
		try {
			computer = computerMapper.ToComputer(computerDto);
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
			try {
				computerService.addComputer(computer);
				return "redirect:/computers";
			} catch (FailSaveComputer e) {
				model.addAttribute("response", e);
				model.addAttribute("computer", computerDto);
				model.addAttribute("listCompany", companyService.displayAllCompany());
				return "addComputer";
			}
		} else {
			model.addAttribute("listCompany", companyService.displayAllCompany());
			model.addAttribute("computer", computerDto);
			model.addAttribute("errors", errors);
			return "addComputer";
		}
	}

	@GetMapping("/editComputer/{id}")
	protected ComputerDTO displayEditComputer(@PathVariable Integer id, Model model) {
		ModelAndView mv = new ModelAndView();
		Computer computer = null;
		try {
			computer = computerService.displayOneComputeur(id);
		} catch (SQLExceptionComputerNotFound e) {
			mv.addObject("message", e.getMessage());
			mv.setViewName("500");
//			return mv;
		}
		mv.addObject("computer", computerMapper.ToComputerDto(computer));
		mv.addObject("listCompany", companyService.displayAllCompany());
		mv.setViewName("editComputer");
		return computerMapper.ToComputerDto(computer);
	}

	@PutMapping("/editComputer")
	protected void editComputer(@RequestBody ComputerDTO computerDto) {
		Map<String, String> errors = new HashMap<String, String>();
		Computer computer = null;
		try {
			computer = computerMapper.ToComputer(computerDto);
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
			try {
				computerService.updateComputer(computer);
				
			} catch (FailSaveComputer e) {
//				model.addAttribute("listCompany", companyService.displayAllCompany());
//				model.addAttribute("response", e);
				
			}
		} else {
//			model.addAttribute("listCompany", companyService.displayAllCompany());
//			model.addAttribute("errors", errors);
		}
	}
}
