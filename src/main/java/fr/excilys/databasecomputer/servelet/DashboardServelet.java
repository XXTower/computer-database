package fr.excilys.databasecomputer.servelet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.databasecomputer.pageable.Page;
import fr.excilys.databasecomputer.service.ComputerService;


@WebServlet(name = "Dashboard", urlPatterns = "/dashboard")
public class DashboardServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ComputerService computerService;
	private static Page page;

    public DashboardServelet() {
    	page = Page.getInstance();
    	computerService = ComputerService.getInstance();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int offset = 0;
		int actpage = 1;
		int nbComputer = 0;
		String order = "ASC";
		if (request.getParameter("limite") != null) {
			try {
				int limite = Integer.parseInt(request.getParameter("limite"));
				if (limite == 10 || limite == 50 || limite == 100) {
					page.setLimite(limite);
				}
			} catch (NumberFormatException e) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
			}
		}

		if (request.getParameter("page") != null) {
			try {
				actpage = Integer.parseInt(request.getParameter("page"));
				offset = page.calculeNewOffset(actpage);
			} catch (NumberFormatException e) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
			}
		} else {
			offset = page.calculeNewOffset(actpage);
		}
		
		if (request.getParameter("order") != null && (request.getParameter("order").equals("ASC") || request.getParameter("order").equals("DESC"))) {
			order = request.getParameter("order");
		}

		if (request.getParameter("search") != null) {
			request.setAttribute("search", request.getParameter("search"));
			nbComputer = computerService.nbComputerCompanyFindByName(request.getParameter("search"));
			request.setAttribute("listComputer", computerService.findComputerCompanyByName(request.getParameter("search"), page.getLimite(), offset, order));
		} else {
			nbComputer = computerService.nbComputer();
			request.setAttribute("listComputer", computerService.displayAllComputer(page.getLimite(), offset, order));
		}

		request.setAttribute("limite", page.getLimite());
		request.setAttribute("nbPage", page.nbPageMax(nbComputer));
		request.setAttribute("nbcomputer", nbComputer);
		request.setAttribute("actPage", actpage);
		request.setAttribute("order", order);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String deletecomputer = request.getParameter("selection");
		String[] listComputer = deletecomputer.split(",");
		for (String id : listComputer) {
			int idComputer = Integer.parseInt(id);
			if (!computerService.deleteComputer(idComputer)) {
				request.setAttribute("response", "Errors whith the delete");
				doGet(request, response);
			}
		}
		doGet(request, response);
	}

}
