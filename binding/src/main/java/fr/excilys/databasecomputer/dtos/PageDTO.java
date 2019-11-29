package fr.excilys.databasecomputer.dtos;

import java.util.List;

public class PageDTO {

	private long nbComputer;
	private List<ComputerDTO> listComputer;

	public PageDTO() {	}

	public PageDTO(long nbComputer, List<ComputerDTO> listComputer) {
		super();
		this.nbComputer = nbComputer;
		this.listComputer = listComputer;
	}

	public long getNbComputer() {
		return nbComputer;
	}

	public void setNbComputer(long nbComputer) {
		this.nbComputer = nbComputer;
	}

	public List<ComputerDTO> getListComputer() {
		return listComputer;
	}

	public void setListComputer(List<ComputerDTO> listComputer) {
		this.listComputer = listComputer;
	}

}
