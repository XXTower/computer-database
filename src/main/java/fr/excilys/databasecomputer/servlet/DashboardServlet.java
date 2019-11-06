package fr.excilys.databasecomputer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.databasecomputer.pageable.Page;
import fr.excilys.databasecomputer.service.ComputerService;

@Controller
public class DashboardServlet {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private Page page;

    @GetMapping("/dashboard")
	protected ModelAndView doGet(@RequestParam(value = "limite", defaultValue = "10") Integer limite,
			@RequestParam(value = "page", defaultValue = "1") Integer actpage, @RequestParam(value = "order", defaultValue = "ASC") String order,
			@RequestParam(value = "search", defaultValue = "") String search) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboard");
		if (limite == 10 || limite == 50 || limite == 100) {
			page.setLimite(limite);
		}

		mv.addObject("search", search);
		int nbComputer = computerService.nbComputerCompanyFindByName(search);
		mv.addObject("listComputer", computerService.findComputerCompanyByName(search, page.getLimite(), page.calculeNewOffset(actpage), order));

		mv.addObject("limite", page.getLimite());
		mv.addObject("nbPage", page.nbPageMax(nbComputer));
		mv.addObject("nbcomputer", nbComputer);
		mv.addObject("actPage", actpage);
		mv.addObject("order", order);

		return mv;
	}

    @PostMapping("/dashboard")
	protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String deletecomputer = request.getParameter("selection");
		String[] listComputer = deletecomputer.split(",");
		for (String id : listComputer) {
			int idComputer = Integer.parseInt(id);
			if (!computerService.deleteComputer(idComputer)) {
				request.setAttribute("response", "Errors whith the delete");
				return "redirect:dashboard";
			}
		}
		return "redirect:dashboard";
	}

}
