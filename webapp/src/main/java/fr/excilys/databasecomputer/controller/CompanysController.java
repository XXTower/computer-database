package fr.excilys.databasecomputer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.databasecomputer.dtos.CompanyDTO;
import fr.excilys.databasecomputer.exception.FailSaveComputer;
import fr.excilys.databasecomputer.service.CompanyService;

@RestController
@RequestMapping(value = "/companys")
public class CompanysController {

	private CompanyService companyService;

	public CompanysController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping
	public List<CompanyDTO> getAll() {
		return companyService.displayAllCompany();
	}
	
	@PostMapping
	public ResponseEntity<String> createCompany(@RequestBody String name){
		try {
			companyService.addCompany(name);
			return ResponseEntity.ok("Ordinateur modifié");
		} catch (FailSaveComputer e) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}
}
