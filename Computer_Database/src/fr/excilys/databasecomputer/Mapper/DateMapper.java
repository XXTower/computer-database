package fr.excilys.databasecomputer.Mapper;

import java.time.LocalDateTime;

public class DateMapper {
	
	public static LocalDateTime changeToLocalDateTime (String dateString) {
		LocalDateTime date = null;
		date =  LocalDateTime.parse(dateString);
		return date;
	}
}
