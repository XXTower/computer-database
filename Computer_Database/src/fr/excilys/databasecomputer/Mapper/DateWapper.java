package fr.excilys.databasecomputer.Mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateWapper {

	public static Date changeToSQLDate (LocalDate date) {
		Date dateSQL=null;
		if(date != null) {
			return dateSQL= Date.valueOf(date);
		}

		return dateSQL;
	}
	
	public static LocalDateTime changeToLocalDateTime (String dateString) {
		LocalDateTime date = null;
		date =  LocalDateTime.parse(dateString);
		return date;
	}
}
