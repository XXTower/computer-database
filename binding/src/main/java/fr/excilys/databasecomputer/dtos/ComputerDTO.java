package fr.excilys.databasecomputer.dtos;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ComputerDTO {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO companyDTO;

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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
