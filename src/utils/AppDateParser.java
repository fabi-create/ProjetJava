package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AppDateParser {
	private static AppDateParser instance = null;
	public static AppDateParser getInstance() { 
		if(instance == null) {
			instance = new AppDateParser();
		}
		return instance; 
	}
	
	public AppDateParser() {}

	
	public Date getDateFromString(String dateString) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        return sdf.parse(dateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public LocalDate getDate_FromString(String dateString) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	    return LocalDate.parse(dateString, formatter);
	}

	
	
	
	/*
	public String getDayFromDate(String dateString) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        Date date = sdf.parse(dateString);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        int day = calendar.get(Calendar.DAY_OF_MONTH);
	        int month = calendar.get(Calendar.MONTH) + 1; // Adding 1 since Calendar.MONTH is 0-based
	        int year = calendar.get(Calendar.YEAR);
	        return day + "/" + month + "/" + year;
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return "Invalid Date";
	    }
	}
	*/
	
	
	
	public int getDayFromDate(String dateString) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    System.out.print(dateString);
	    try {
	        Date date = sdf.parse(dateString);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.DAY_OF_MONTH);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return -1; // Invalid Date
	    }
	}
	
	
	public int getWeekFromDate(String dateString) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        Date date = sdf.parse(dateString);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.WEEK_OF_YEAR);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return -1; // Invalid Date
	    }
	}
	
	public int getMonthFromDate(String dateString) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        Date date = sdf.parse(dateString);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.MONTH) + 1; // Adding 1 since Calendar.MONTH is 0-based
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return -1; // Invalid Date
	    }
	}
	
	public String calculatePeriod(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date date = sdf.parse(dateString);
            
            // Implémentez votre logique de calcul de période ici
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1; // Adding 1 since Calendar.MONTH is 0-based
            int year = calendar.get(Calendar.YEAR);
            
            // Exemple de logique de calcul de période pour afficher le mois et l'année
            return String.format("%02d/%02d/%d", day, month, year);
            
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }
}
