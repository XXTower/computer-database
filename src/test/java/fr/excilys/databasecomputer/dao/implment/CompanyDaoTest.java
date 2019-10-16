package fr.excilys.databasecomputer.dao.implment;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.dao.implement.CompanyDAO;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.entity.Company;
import junit.framework.TestCase;

public class CompanyDaoTest extends TestCase {
//jdbc:h2:mem:computer-database-db;INIT=RUNSCRIPT FROM '~/resources/1-SCHEMA.sql'
	ConnextionDB connextion;
	CompanyDAO companyDAO;
	
	@Before
	public void setUp() {
		connextion = ConnextionDB.getInstance();
		connextion.testURL();
		companyDAO = CompanyDAO.getInstance();
	}
	
	@After
	public void tearDown() {
		connextion=null;
		companyDAO = null;
	}
	
	@Test
	public final void testDisplayAll() {
		ArrayList<Company> companies = new ArrayList<>();
		companies.add(new CompanyBuilder().id(1).name("Apple Inc.").build());
		companies.add(new CompanyBuilder().id(2).name("Thinking Machines").build());
		companies.add(new CompanyBuilder().id(3).name("RCA").build());
		companies.add(new CompanyBuilder().id(4).name("Netronics").build());
		companies.add(new CompanyBuilder().id(5).name("Tandy Corporation").build());
		assertEquals(companies, companyDAO.findAll());
	}
	
	@Test
	public final void testDisplayAllWithLimit() {
		ArrayList<Company> companies = new ArrayList<>();
		companies.add(new CompanyBuilder().id(1).name("Apple Inc.").build());
		companies.add(new CompanyBuilder().id(2).name("Thinking Machines").build());
		assertEquals(companies, companyDAO.findAll(2,0));
	}
	
	@Test
	public final void testNbCompany() {
		int number = companyDAO.nbCompany();
		assertEquals(5, number);
	}
}
