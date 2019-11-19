package fr.excilys.databasecomputer.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

@Controller
@RequestMapping(value = "/computers")
public class ComputersController {

	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;
	private Validator validator;
	private Page page;

	@Autowired
	public ComputersController(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, Validator validator, Page page) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.validator = validator;
		this.page = page;
	}

	@GetMapping
	protected ModelAndView getComputers(@RequestParam(value = "limite", defaultValue = "0") Integer limite,
			@RequestParam(value = "page", defaultValue = "1") Integer actpage, @RequestParam(value = "order", defaultValue = "ASC") String order,
			@RequestParam(value = "search", defaultValue = "") String search, Model model) {
		ModelAndView mv = new ModelAndView();
		if (limite == 10 || limite == 50 || limite == 100) {
			page.setLimite(limite);
		}

		mv.addObject("search", search);
		long nbComputer = computerService.nbComputerCompanyFindByName(search);
		mv.addObject("listComputer", computerService.findComputerCompanyByName(search, page.getLimite(), page.calculeNewOffset(actpage), order));

		mv.addObject("limite", page.getLimite());
		mv.addObject("nbPage", page.nbPageMax(nbComputer));
		mv.addObject("nbcomputer", nbComputer);
		mv.addObject("actPage", actpage);
		mv.addObject("order", order);
		mv.setViewName("dashboard");
		return mv;
	}

	@PostMapping("/delete")
	protected String deleteComputer(HttpServletRequest request) {
		String deletecomputer = request.getParameter("selection");
		String[] listComputer = deletecomputer.split(",");
		for (String id : listComputer) {
			int idComputer = Integer.parseInt(id);
			if (!computerService.deleteComputer(idComputer)) {
				request.setAttribute("response", "Errors whith the delete");
				return "redirect:/computers";
			}
		}
		return "redirect:/computers";
	}

	@GetMapping("/addComputer")
	public String displayAddComputer(Model model) {
		model.addAttribute("listCompany", companyService.displayAllCompany());
		model.addAttribute("computer", new ComputerDTO());
		return "addComputer";
	}

	@PostMapping("/addComputer")
	public String addcomputer(@ModelAttribute("computer") ComputerDTO computerDto, Model model) {
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

	@GetMapping("/editComputer")
	protected String displayEditComputer(@RequestParam(value = "computer") Integer idComputer, Model model) {
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
	protected String editComputer(@ModelAttribute("computer") ComputerDTO computerDto, Model model) {
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
			try {
				computerService.updateComputer(computer);
				return "redirect:/computers";
			} catch (FailSaveComputer e) {
				model.addAttribute("listCompany", companyService.displayAllCompany());
				model.addAttribute("response", e);
				return "editComputer";
			}
		} else {
			model.addAttribute("listCompany", companyService.displayAllCompany());
			model.addAttribute("errors", errors);
			return "editComputer";
		}
	}
}
