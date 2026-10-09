package model;

import java.time.LocalDate;

public class RecurringBill {

    private final String name;

    private double amount;

    private int dueDay;

    private String category;

    public RecurringBill(
            String name,
            double amount,
            int dueDay,
            String category) {

        this.name = name;
        this.amount = amount;
        this.dueDay = dueDay;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getDueDay() {
        return dueDay;
    }

    public String getCategory() {
        return category;
    }

    public void setAmount(
            double amount) {

        this.amount = amount;
    }

    public void setDueDay(
            int dueDay) {

        this.dueDay = dueDay;
    }

    public void setCategory(
            String category) {

        this.category = category;
    }

    public LocalDate getNextDueDate() {

        LocalDate today =
                LocalDate.now();

        int safeDay =
                Math.min(
                        dueDay,
                        today.lengthOfMonth()
                );

        LocalDate candidate =
                today.withDayOfMonth(
                        safeDay
                );

        if (candidate.isBefore(today)) {

            LocalDate next =
                    today.plusMonths(1);

            return next.withDayOfMonth(
                    Math.min(
                            dueDay,
                            next.lengthOfMonth()
                    )
            );
        }

        return candidate;
    }
}