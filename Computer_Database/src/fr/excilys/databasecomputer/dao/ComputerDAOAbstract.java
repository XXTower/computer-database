package fr.excilys.databasecomputer.dao;

import java.sql.Connection;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.entity.Computer;

public abstract class ComputerDAOAbstract {

	protected Connection connet = null;
	
	public ComputerDAOAbstract (Connection conn) {
		this.connet =conn;
	}
	
	public abstract boolean addComputer(Computer computer);
	
	public abstract Computer find(int id);
	
	public abstract boolean update(Computer computer);
	
	public abstract boolean delete(int id);
	
	public abstract ArrayList<Computer> findAll();
	
	public abstract ArrayList<Computer> findAll(int limite, int offset);
	
	public abstract int nbComputer();
}
