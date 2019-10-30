package fr.excilys.databasecomputer.pageable;

import org.springframework.stereotype.Component;

@Component
public class Page {
	private int limite = 10;

	private Page() { }

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int nbPageMax(int nbobject) {
		return (int) Math.ceil(((double) nbobject / (double) this.limite));
	}

	public int calculeNewOffset(int page) {
		return page * this.limite - this.limite;
	}

}
