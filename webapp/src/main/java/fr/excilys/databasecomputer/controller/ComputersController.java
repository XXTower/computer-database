package fr.excilys.databasecomputer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.dtos.PageDTO;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.DateFormatExeption;
import fr.excilys.databasecomputer.exception.DateIntevaleExecption;
import fr.excilys.databasecomputer.exception.FailSaveComputer;
import fr.excilys.databasecomputer.exception.NameCheckException;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.pageable.Page;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

@RestController
@RequestMapping(value = "/computers")
public class ComputersController {

	private ComputerService computerService;
	private ComputerMapper computerMapper;
	private Validator validator;

	public ComputersController(ComputerService computerService, ComputerMapper computerMapper, Validator validator,
			Page page) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
		this.validator = validator;
	}

	@GetMapping
	protected PageDTO getComputers(Page page, @RequestParam(value = "order", defaultValue = "ASC") String order) {

		return new PageDTO(computerService.nbComputerCompanyFindByName(page.getSearch()),
				computerService.findComputerCompanyByName(page.getSearch(), page.getLimite(), page.calculeNewOffset(), order));
	}

	@DeleteMapping
	protected void deleteComputer(@RequestBody List<Integer> listComputer) {
		for (Integer idComputer : listComputer) {
			computerService.deleteComputer(idComputer);
		}
	}

	@PostMapping
	public String createComputer(@RequestBody ComputerDTO computerDto) {
		Map<String, String> errors = new HashMap<String, String>();
		Computer computer = null;
		try {
			computer = computerMapper.toComputer(computerDto);
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
				return "addComputer";
			}
		} else {
			return "addComputer";
		}
	}

	@GetMapping("/{id}")
	protected ResponseEntity<ComputerDTO> getComputerBiId(@PathVariable Integer id) {
		Computer computer = null;
		try {
			computer = computerService.displayOneComputeur(id);
		} catch (SQLExceptionComputerNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(computerMapper.toComputerDto(computer));
	}

	@DeleteMapping("/{id}")
	protected void deleteComputerBiId(@PathVariable Integer id) {
		computerService.deleteComputer(id);
	}

	@PutMapping
	public ResponseEntity<String> update(@RequestBody ComputerDTO computerDto) {
		Map<String, String> errors = new HashMap<String, String>();
		Computer computer = null;
		try {
			computer = computerMapper.toComputer(computerDto);
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
				return ResponseEntity.ok("Ordinateur modifié");
			} catch (FailSaveComputer e) {
				return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
	}
}
