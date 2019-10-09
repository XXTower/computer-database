package fr.excilys.databasecomputer.warpper;

import java.sql.Date;
import java.time.LocalDate;

public class DateWapper {

	public static Date changeToSQLDate (LocalDate date) {
		Date dateSQL=null;
		if(date != null) {
			return dateSQL= Date.valueOf(date);
		}

		return dateSQL;
	}
	
	public static LocalDate changeToLocalDate (String dateString) {
		LocalDate date = null;
		date =  LocalDate.parse(dateString);
		return date;
	}
}
