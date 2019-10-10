package fr.excilys.databasecomputer.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.entity.Company;

public class CompanyDAO {

	private Connection conn;
	
	public CompanyDAO(){
		 this.conn = ConnextionDB.getInstance().getConnection();
	}
	
	public ArrayList<Company> findAll() throws SQLException {
		ArrayList<Company> companys = new ArrayList<>();
		try {
			PreparedStatement stm = this.conn.prepareStatement("Select * from company Order by id");
			ResultSet result = stm.executeQuery();
			
			while(result.next()) {
				Company company =new Company();
				company.setId(result.getInt("id"));
				company.setName(result.getString("name"));
				companys.add(company);
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		
		return companys;
	}

	public ArrayList<Company> findAll(int limite, int offset) throws SQLException {
		ArrayList<Company> companys = new ArrayList<>();
		try {
			PreparedStatement stm = this.conn.prepareStatement("Select * from company Order by id Limit ? offset ?");
			stm.setInt(1, limite);
			stm.setInt(2, offset);
			ResultSet result = stm.executeQuery();
			while(result.next()) {
				Company company =new Company();
				company.setId(result.getInt("id"));
				company.setName(result.getString("name"));
				companys.add(company);
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		
		return companys;
	}

	public int nbCompany() throws SQLException {
		try {
			PreparedStatement stm = this.conn.prepareStatement("Select count(*) as nbCompany from company");
			ResultSet result = stm.executeQuery();
			if(result.first()) {
				return result.getInt("nbCompany");
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		
		return 0;
	}
}
