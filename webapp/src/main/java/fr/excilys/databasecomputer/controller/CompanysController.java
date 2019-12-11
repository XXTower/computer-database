package fr.excilys.databasecomputer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@CrossOrigin
	@GetMapping
	public List<CompanyDTO> getAll() {
		return companyService.displayAllCompany();
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<String> createCompany(@RequestBody String name) {
		name = name.substring(0,name.length()-1);
		try {
			companyService.addCompany(name);
			return ResponseEntity.ok("Ordinateur modifié");
		} catch (FailSaveComputer e) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}

	@CrossOrigin
	@PutMapping
	public ResponseEntity<String> updateCompany(@RequestBody CompanyDTO companyDTO) {
		try {
			companyService.update(companyDTO);
			return ResponseEntity.ok("Ordinateur modifié");
		} catch (FailSaveComputer e) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}

	@CrossOrigin
	@DeleteMapping
	public void deleteCompanys(@RequestBody CompanyDTO companyDTO) {
		companyService.deleteCompany(companyDTO);
	}

}
