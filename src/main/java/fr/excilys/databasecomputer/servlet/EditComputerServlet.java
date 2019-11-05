package fr.excilys.databasecomputer.servlet;

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
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

@WebServlet("/editComputer")
@Controller
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private Validator validator;

    public EditComputerServlet() { }

    @Override
    public void init(ServletConfig config) throws ServletException {
      super.init(config);
      SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("computer") != null) {
			try {
				int id = Integer.parseInt(request.getParameter("computer"));
				Computer computer = computerService.displayOneComputeur(id);
				request.setAttribute("computer", computer);
			} catch (NumberFormatException | SQLExceptionComputerNotFound e) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("message", "Ordinateur non renseigner");
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
		}

		request.setAttribute("listCompany", companyService.displayAllCompany());
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> errors = new HashMap<String, String>();
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company = request.getParameter("company");

		ComputerDTO computerDto = new ComputerDTOBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued).company(company).build();
		Computer computer = null;
		try {
			computer = computerMapper.computerDtoToComputer(computerDto);
			validator.validationComputer(computer);
		} catch (NameCheckException e) {
			errors.put("computerName", e.getMessage());
		} catch (DateIntevaleExecption e) {
			errors.put("discontinued", e.getMessage());
		} catch (DateFormatExeption e) {
			errors.put("introduced", e.getMessage());
			errors.put("discontinued", e.getMessage());
		}

		if (errors.isEmpty()) {
			if (computerService.updateComputer(computer)) {
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
