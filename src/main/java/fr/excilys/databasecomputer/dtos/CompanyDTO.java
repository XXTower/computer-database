package fr.excilys.databasecomputer.dtos;

public class CompanyDTO {
	private int id;
	private String name;

	private CompanyDTO(CompanyDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
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

	public class CompanyDTOBuilder {
		private int id;
		private String name;

		public CompanyDTOBuilder() { }

		public CompanyDTOBuilder id(int id) {
			this.id = id;
			return this;
		}

		public CompanyDTOBuilder name(String name) {
			this.name = name;
			return this;
		}

		public CompanyDTO build() {
			return new CompanyDTO(this);
		}
	}
}
