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
		if(request.getParameter("limite")!=null) {
			try {
				int limite = Integer.parseInt(request.getParameter("limite"));
				if (limite == 10 || limite == 50 || limite == 100) {
					page.setLimite(limite);
				}
			} catch (NumberFormatException e) {
				
			}					
		}
		
		if (request.getParameter("page") != null) {
			try {
				offset = page.calculeNewOffset(Integer.parseInt(request.getParameter("page")));
			} catch (NumberFormatException e) {
				this.getServletContext().getRequestDispatcher("/views/500.jsp").forward(request, response);
			}
			
		}

		int nbComputer = computerService.nbComputer();
		request.setAttribute("nbPage", page.nbPageMax(nbComputer));
		request.setAttribute("listComputer", computerService.displayAllComputer(page.getLimite(), offset));
		request.setAttribute("nbcomputer", nbComputer);
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
