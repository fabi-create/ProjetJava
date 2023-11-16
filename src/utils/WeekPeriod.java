package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeekPeriod {
    private double total;
    private LocalDate startDate;
    private LocalDate endDate;

    public WeekPeriod(double total, LocalDate startDate, LocalDate endDate) {
        this.total = total;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateTotal(double amount) {
    	// total = amount;
        total += amount;
    }

    public void updateDateRange(LocalDate newStartDate, LocalDate newEndDate) {
        startDate = newStartDate.isBefore(startDate) ? newStartDate : startDate;
        endDate = newEndDate.isAfter(endDate) ? newEndDate : endDate;
    }

    public double getTotal() {
        return total;
    }
    
    public void setTotal(double amount) {
    	total = amount;
    }

    public String getFormattedPeriod() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return startDate.format(formatter) + " - " + endDate.format(formatter);
    }
}

