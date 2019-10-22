package fr.excilys.databasecomputer.servelet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.dtos.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.DateIntevaleExecption;
import fr.excilys.databasecomputer.exception.NameCheckException;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

@WebServlet("/addComputer")
public class AddComputerServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ComputerService computerService;
	private static CompanyService companyService;
	private static ComputerMapper computerMapper;
	private static Validator validator;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServelet() {
        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
        computerMapper = ComputerMapper.getInstance();
        validator = Validator.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listCompany", companyService.displayAllCompany());
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> errors = new HashMap<String, String>();
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company = request.getParameter("company");

		ComputerDTO computerDto = new ComputerDTOBuilder().name(name).introduced(introduced).discontinued(discontinued).company(company).build();
		Computer computer = computerMapper.computerDtoToComputer(computerDto);
		
		try {
			validator.checkNameComputer(computer.getName());
		} catch (NameCheckException e) {
			errors.put("computerName",e.getMessage());
		}
		
		try {
			validator.checkDateIntervale(computer.getDiscontinued(), computer.getIntroduced());
		} catch (DateIntevaleExecption e) {
			errors.put("discontinued",e.getMessage());
		}
		
		
		if (errors.isEmpty()) {
			computerService.addComputer(computer);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
		} else {
			request.setAttribute("errors", errors);
			doGet(request, response);
		}

		
	}

}
