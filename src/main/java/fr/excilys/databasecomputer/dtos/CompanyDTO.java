package fr.excilys.databasecomputer.dtos;

public class CompanyDTO {
	private int id;
	private String name;

	private CompanyDTO() {
		super();
	}

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

}
