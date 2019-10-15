package fr.excilys.databasecomputer.entity;

public class Company {
	private int id;
	private String name;
	
	
	private Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static class CompanyBuilder {
		private int id;
		private String name;
		
		public CompanyBuilder() {}
		
		public CompanyBuilder id(int id) {
			this.id=id;
			return this;
		}
		
		public CompanyBuilder name(String name) {
			this.name=name;
			return this;
		}
		
		public Company build() {
			return new Company(this);
		}
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
}

