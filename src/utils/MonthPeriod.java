package utils;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class MonthPeriod {
    private double total;
    private YearMonth yearMonth;

    public MonthPeriod(double total, YearMonth yearMonth) {
        this.total = total;
        this.yearMonth = yearMonth;
    }

    public void updateTotal(double amount) {
        total += amount;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double amount) {
        total = amount;
    }

    public String getFormattedPeriod() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return yearMonth.format(formatter);
    }
}

