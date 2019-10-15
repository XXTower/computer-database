package fr.excilys.databasecomputer.Mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateMapper {
	
	public static LocalDate changeToLocalDate (String dateString) {
		LocalDate date = null;
		date =  LocalDate.parse(dateString,DateTimeFormatter.ISO_LOCAL_DATE);
		return date;
	}
	
	public static Date changeToDateSQL(LocalDate date) {
		if(date != null) {
			return Date.valueOf(date);
		}
		return null;
	}
}
