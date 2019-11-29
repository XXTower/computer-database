package fr.excilys.databasecomputer.dtos;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CompanyDTO {
	private int id;
	private String name;

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

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
