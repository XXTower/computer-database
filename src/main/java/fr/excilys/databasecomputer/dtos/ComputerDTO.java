package fr.excilys.databasecomputer.dtos;


public class ComputerDTO {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyId;

	private ComputerDTO(ComputerDTOBuilder builder) {	
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.company;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getCompany() {
		return companyId;
	}

	public static class ComputerDTOBuilder {
		private int id;
		private String name;
		private String introduced;
		private String discontinued;
		private String company;

		public ComputerDTOBuilder() { }

		public ComputerDTOBuilder id(int id) {
			this.id = id;
			return this;
		}

		public ComputerDTOBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ComputerDTOBuilder introduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerDTOBuilder discontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerDTOBuilder company(String company) {
			this.company = company;
			return this;
		}

		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}
	
	
}
