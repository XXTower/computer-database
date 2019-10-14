package fr.excilys.databasecomputer.pageable;

public class Page {
	private static Page instance;
	private int limite =10;

	private Page() {}
	
	public static Page getInstance() {
		if(instance==null) {
			instance = new Page();
		}
		return instance;
	}
	
	public int getLimite() {
		return limite;
	}
	
	public int nbPageMax(int nbobject) {		
		return (int)Math.ceil(((double)nbobject/(double)this.limite));
	}
	
	public int calculeNewOffset(int page) {
		return page* this.limite - this.limite;
		
	}
	
}
