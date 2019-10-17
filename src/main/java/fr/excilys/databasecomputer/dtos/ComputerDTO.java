package fr.excilys.databasecomputer.dtos;

import java.time.LocalDate;

import fr.excilys.databasecomputer.entity.Company;

public class ComputerDTO {
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	private ComputerDTO() {	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
