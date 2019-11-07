package fr.excilys.databasecomputer.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.excilys.databasecomputer.pageable.Page;
import fr.excilys.databasecomputer.service.ComputerService;

@Controller
public class DashboardServlet {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private Page page;

    @GetMapping("/dashboard")
	protected String doGet(@RequestParam(value = "limite", defaultValue = "0") Integer limite,
			@RequestParam(value = "page", defaultValue = "1") Integer actpage, @RequestParam(value = "order", defaultValue = "ASC") String order,
			@RequestParam(value = "search", defaultValue = "") String search, Model model) {
		if (limite == 10 || limite == 50 || limite == 100) {
			page.setLimite(limite);
		}

		model.addAttribute("search", search);
		int nbComputer = computerService.nbComputerCompanyFindByName(search);
		model.addAttribute("listComputer", computerService.findComputerCompanyByName(search, page.getLimite(), page.calculeNewOffset(actpage), order));

		model.addAttribute("limite", page.getLimite());
		model.addAttribute("nbPage", page.nbPageMax(nbComputer));
		model.addAttribute("nbcomputer", nbComputer);
		model.addAttribute("actPage", actpage);
		model.addAttribute("order", order);

		return "dashboard";
	}

    @PostMapping("/dashboard")
	protected String doPost(HttpServletRequest request, HttpServletResponse response) {

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
