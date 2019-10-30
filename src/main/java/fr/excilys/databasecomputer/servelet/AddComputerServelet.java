package fr.excilys.databasecomputer.servelet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.dtos.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.DateFormatExeption;
import fr.excilys.databasecomputer.exception.DateIntevaleExecption;
import fr.excilys.databasecomputer.exception.NameCheckException;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

@WebServlet("/addComputer")
@Controller
public class AddComputerServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private Validator validator;

    public AddComputerServelet() { }

    @Override
    public void init(ServletConfig config) throws ServletException {
      super.init(config);
      SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listCompany", companyService.displayAllCompany());
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> errors = new HashMap<String, String>();
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company = request.getParameter("company");

		ComputerDTO computerDto = new ComputerDTOBuilder().name(name).introduced(introduced).discontinued(discontinued).company(company).build();
		Computer computer = null;
		try {
			computer = computerMapper.computerDtoToComputer(computerDto);
			try {
				validator.checkNameComputer(computer.getName());
			} catch (NameCheckException e) {
				errors.put("computerName", e.getMessage());
			}

			try {
				validator.checkDateIntervale(computer.getDiscontinued(), computer.getIntroduced());
			} catch (DateIntevaleExecption e) {
				errors.put("discontinued", e.getMessage());
			}
		} catch (DateFormatExeption e) {
			errors.put("introduced", e.getMessage());
			errors.put("discontinued", e.getMessage());
		}

		if (errors.isEmpty()) {
			if (computerService.addComputer(computer)) {
				response.sendRedirect("dashboard");
			} else {
				request.setAttribute("response", "Errors whith the save");
				doGet(request, response);
			}
		} else {
			request.setAttribute("errors", errors);
			doGet(request, response);
		}
	}
}
