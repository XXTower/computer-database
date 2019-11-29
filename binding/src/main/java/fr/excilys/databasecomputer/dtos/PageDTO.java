package fr.excilys.databasecomputer.dtos;

import java.util.List;

public class PageDTO {

	private long nb_computer;
	private List<ComputerDTO> list_computer;

	public PageDTO() {	}

	public PageDTO(long nb_computer, List<ComputerDTO> list_computer) {
		super();
		this.nb_computer = nb_computer;
		this.list_computer = list_computer;
	}

	public long getNb_computer() {
		return nb_computer;
	}

	public void setNb_computer(long nb_computer) {
		this.nb_computer = nb_computer;
	}

	public List<ComputerDTO> getList_computer() {
		return list_computer;
	}

	public void setList_computer(List<ComputerDTO> list_computer) {
		this.list_computer = list_computer;
	}

}
