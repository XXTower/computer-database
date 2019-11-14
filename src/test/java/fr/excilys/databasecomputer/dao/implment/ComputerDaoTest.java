package fr.excilys.databasecomputer.dao.implment;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import fr.excilys.databasecomputer.configuration.CLIConfiguration;
import fr.excilys.databasecomputer.dao.implement.ComputerDAO;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CLIConfiguration.class})
public class ComputerDaoTest extends TestCase {

	@Autowired
	ComputerDAO computerDAO;
	
	@Before
	public void setUp() {

	}
	
	@After
	public void tearDown() {
		computerDAO = null;
	}
	
	public final void testDisplayAll() {
		ArrayList<Computer> computers = new ArrayList<>();
		computers.add(new ComputerBuilder().id(1).name("MacBook Pro 15.4 inch").introduced(null).discontinued(null).company(new CompanyBuilder().id(1).name("Apple Inc.").build()).build());
		computers.add(new ComputerBuilder().id(2).name("CM-2a").introduced(null).discontinued(null).company(new CompanyBuilder().id(2).name("Thinking Machines").build()).build());
		computers.add(new ComputerBuilder().id(3).name("CM-200").introduced(null).discontinued(null).company(new CompanyBuilder().id(2).name("Thinking Machines").build()).build());
		computers.add(new ComputerBuilder().id(4).name("CM-5e").introduced(null).discontinued(null).company(new CompanyBuilder().id(2).name("Thinking Machines").build()).build());
		computers.add(new ComputerBuilder().id(5).name("CM-5").introduced(LocalDate.parse("1991-01-01")).discontinued(null).company(new CompanyBuilder().id(2).name("Thinking Machines").build()).build());
		assertEquals(computers, computerDAO.findAll());
	}
	
	@Test
	public final void testDisplayAllWithLimit() {
		ArrayList<Computer> computers = new ArrayList<>();
		computers.add(new ComputerBuilder().id(3).name("CM-200").introduced(null).discontinued(null).company(new CompanyBuilder().id(2).name("Thinking Machines").build()).build());
		computers.add(new ComputerBuilder().id(2).name("CM-2a").introduced(null).discontinued(null).company(new CompanyBuilder().id(2).name("Thinking Machines").build()).build());

		assertEquals(computers, computerDAO.findAll(2,0,"ASC"));
	}
	
	@Test
	public final void testNbComputer() {
		long number = computerDAO.nbComputer();
		assertEquals(5, number);
	}

	@Test
	public final void testFindOne() {
		Computer computers =new ComputerBuilder().id(1).name("MacBook Pro 15.4 inch").introduced(null).discontinued(null).company(new CompanyBuilder().id(1).name("Apple Inc.").build()).build();
		try {
			assertEquals(computers, computerDAO.find(1));
		} catch (SQLExceptionComputerNotFound e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testDelete() {
		assertEquals(true, computerDAO.delete(2));
	}

}
