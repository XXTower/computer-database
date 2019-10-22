package fr.excilys.databasecomputer.dtos;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;

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
	
	public class CompanyDTOBuilder{
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyDTO other = (CompanyDTO) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}
