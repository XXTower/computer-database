package fr.excilys.databasecomputer.servelet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;

@WebServlet("/addComputer")
public class AddComputerServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ComputerService computerService;
	private static CompanyService companyService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServelet() {
        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listCompany", companyService.displayAllCompany());
		this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
