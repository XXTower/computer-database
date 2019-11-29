package fr.excilys.databasecomputer.pageable;

import org.springframework.stereotype.Component;

@Component
public class Page {
	private int limite = 10;
	private int actPage = 1;
	private long nbComputer = 0;
	private int maxPage = 0;

	private Page() { }

//	public int getMaxPage() {
//		return maxPage;
//	}

	public long getNbComputer() {
		return nbComputer;
	}

	public void setNbComputer(long l) {
		this.nbComputer = l;
	}

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

//	public void nbPageMax() {
//		maxPage = (int) Math.ceil(((double) nbComputer / (double) limite));
//	}

	public int calculeNewOffset() {
		return actPage * limite - limite;
	}

}
