package fr.excilys.databasecomputer.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateMapper {
	
	public static LocalDate changeToLocalDateTime (String dateString) {
		LocalDate date = null;
		date =  LocalDate.parse(dateString,DateTimeFormatter.ISO_LOCAL_DATE);
		return date;
	}
}
