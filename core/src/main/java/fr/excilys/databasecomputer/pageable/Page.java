package fr.excilys.databasecomputer.pageable;

import org.springframework.stereotype.Component;

@Component
public class Page {
	private int limite = 10;
	private int actPage = 1;
	private String search = "";

	private Page() { }

	public int getActPage() {
		return actPage;
	}

	public void setActPage(int actPage) {
		this.actPage = actPage;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int calculeNewOffset() {
		return actPage * limite - limite;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
