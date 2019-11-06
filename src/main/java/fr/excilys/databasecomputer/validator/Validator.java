package fr.excilys.databasecomputer.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.DateIntevaleExecption;
import fr.excilys.databasecomputer.exception.NameCheckException;

@Component
public class Validator {
	
	public void validationComputer(Computer computer) throws DateIntevaleExecption, NameCheckException {
		checkNameComputer(computer.getName());
		checkDateIntervale(computer.getIntroduced(), computer.getDiscontinued());
	}

	private void checkDateIntervale(LocalDate dateinterruption, LocalDate discontinuedDate) throws DateIntevaleExecption {
		if (discontinuedDate != null && dateinterruption != null && dateinterruption.isAfter(discontinuedDate)) {
			throw new DateIntevaleExecption("The date " + dateinterruption.toString() + " must be after " + discontinuedDate.toString());
		}
	}

	private void checkNameComputer(String name) throws NameCheckException {
		if ("".equals(name) || name == null) {
			throw new NameCheckException("Name must not be empty");
		}
	}
}
