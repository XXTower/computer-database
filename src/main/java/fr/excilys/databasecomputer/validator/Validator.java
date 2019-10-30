package fr.excilys.databasecomputer.validator;

import java.time.LocalDate;

import fr.excilys.databasecomputer.exception.DateIntevaleExecption;
import fr.excilys.databasecomputer.exception.NameCheckException;

public class Validator {
	private static Validator instance;

	private Validator() { }

	public static Validator getInstance() {
		if (instance == null) {
			instance = new Validator();
		}
		return instance;
	}

	public void checkDateIntervale(LocalDate discontinuedDate, LocalDate dateinterruption) throws DateIntevaleExecption {
		if (discontinuedDate != null && dateinterruption != null && dateinterruption.isAfter(discontinuedDate)) {
			throw new DateIntevaleExecption("The date " + dateinterruption.toString() + " must be after " + dateinterruption.toString());
		}
	}

	public void checkNameComputer(String name) throws NameCheckException {
		if ("".equals(name) || name == null) {
			throw new NameCheckException("Name must not be empty");
		}
	}
}
